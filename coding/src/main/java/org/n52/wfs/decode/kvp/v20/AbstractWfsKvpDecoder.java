/*
 * Copyright 2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * License version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.wfs.decode.kvp.v20;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.naming.ConfigurationException;
import javax.xml.namespace.QName;

import org.n52.ogc.wfs.WfsConstants.AdditionalCommonKeywordsParams;
import org.n52.sos.ds.FeatureQuerySettingsProvider;
import org.n52.sos.ogc.filter.FesSortBy;
import org.n52.sos.ogc.filter.Filter;
import org.n52.sos.ogc.filter.SpatialFilter;
import org.n52.sos.util.CodingHelper;
import org.n52.sos.util.JTSHelper;
import org.n52.sos.util.SosHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.n52.iceland.coding.decode.Decoder;
import org.n52.iceland.config.annotation.Configurable;
import org.n52.iceland.config.annotation.Setting;
import org.n52.iceland.exception.CodedException;
import org.n52.iceland.exception.ows.InvalidParameterValueException;
import org.n52.iceland.exception.ows.MissingParameterValueException;
import org.n52.iceland.exception.ows.OptionNotSupportedException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.filter.FilterConstants;
import org.n52.iceland.ogc.filter.FilterConstants.SpatialOperator;
import org.n52.iceland.request.AbstractServiceRequest;
import org.n52.iceland.service.MiscSettings;
import org.n52.iceland.util.CRSHelper;
import org.n52.iceland.util.CollectionHelper;
import org.n52.iceland.util.Constants;
import org.n52.iceland.util.StringHelper;
import org.n52.iceland.util.Validation;
import org.n52.sos.util.GeometryHandler;

/**
 * Abstract WFS request encoder for KVP binding
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
@Configurable
public abstract class AbstractWfsKvpDecoder implements
        Decoder<AbstractServiceRequest<?>, Map<String, String>> {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractWfsKvpDecoder.class);

    protected static final int VALID_COORDINATE_SIZE = 4;

    @Inject
    private GeometryHandler geometryHandler;

    private String srsNamePrevixV1;
    private String srsNamePrefixV2;


    /**
     * @return
     */
    public int getDefaultEPSG() {
        return this.geometryHandler.getDefaultResponseEPSG();
    }

    /**
     * @return
     */
    public int getDefault3DEPSG() {
        return this.geometryHandler.getDefaultResponse3DEPSG();
    }

    /**
     * @param qNameStrings
     * @param namespaces
     * @param parameterName
     *
     * @return
     *
     * @throws OwsExceptionReport
     */
    protected Set<QName> createQNames(List<String> qNameStrings,
                                      Map<String, String> namespaces,
                                      String parameterName)
            throws OwsExceptionReport {
        Set<QName> qNames = new HashSet<>(qNameStrings.size());
        for (String qNameString : qNameStrings) {
            qNames.add(createQName(qNameString, namespaces, parameterName));
        }
        return qNames;
    }

    /**
     * @param qnameString
     * @param namespaces
     * @param parameterName
     *
     * @return
     *
     * @throws OwsExceptionReport
     */
    protected QName createQName(String qnameString,
                                Map<String, String> namespaces,
                                String parameterName)
            throws OwsExceptionReport {
        if (CollectionHelper.isEmpty(namespaces)) {
            throw new MissingParameterValueException(AdditionalCommonKeywordsParams.Namespaces);
        }
        String[] split = qnameString.split(Constants.COLON_STRING);
        if (split.length != 2) {
            throw new InvalidParameterValueException(parameterName, qnameString);
        }
        return new QName(namespaces.get(split[0]), split[1], split[0]);
    }

    /**
     * @param parameterValues
     *
     * @return
     */
    protected Map<String, String> parseNamespaces(String parameterValues) {
        return Arrays.stream(parameterValues.replaceAll("\\),", "").replaceAll("\\)", "").split("xmlns\\("))
                .filter(s -> s != null && !s.isEmpty())
                .map(s -> s.split(","))
                .filter(s -> s.length == 2)
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }

    /**
     * @param sortByList
     *
     * @return
     *
     * @throws CodedException
     */
    protected FesSortBy parseSortBy(List<String> sortByList)
            throws CodedException {
        throw new OptionNotSupportedException()
                .withMessage("This service does not support result sorting!");
    }

    /**
     * @param parameterValues
     * @param parameterName
     *
     * @return
     *
     * @throws OwsExceptionReport
     */
    protected SpatialFilter parseSpatialFilter(List<String> parameterValues,
                                               String parameterName)
            throws OwsExceptionReport {
        if (!parameterValues.isEmpty()) {
            if (!(parameterValues instanceof RandomAccess)) {
                parameterValues = new ArrayList<>(parameterValues);
            }
            SpatialFilter spatialFilter = new SpatialFilter();

            boolean hasSrid = false;

//            spatialFilter.setValueReference(parameterValues.get(0));

            int srid = getDefaultEPSG();
            if (parameterValues.get(parameterValues.size() - 1)
                    .startsWith(getSrsNamePrefixSosV2()) ||
                     parameterValues.get(parameterValues.size() - 1)
                .startsWith(getSrsNamePrefix())) {
                hasSrid = true;
                srid = SosHelper.parseSrsName(parameterValues
                        .get(parameterValues.size() - 1));
            }

            List<String> coordinates;
            if (hasSrid) {
                coordinates = parameterValues
                        .subList(0, parameterValues.size() - 1);
            } else {
                coordinates = parameterValues.subList(0, parameterValues.size());
            }

            if (coordinates.size() != VALID_COORDINATE_SIZE) {
                throw new InvalidParameterValueException().at(parameterName)
                        .withMessage(
                                "The parameter value is not valid!");
            }
            String lowerCorner = String
                    .format(Locale.US, "%f %f", new Float(coordinates
                                    .get(Constants.INT_0)), new Float(
                                    coordinates.get(Constants.INT_1)));
            String upperCorner = String
                    .format(Locale.US, "%f %f", new Float(coordinates
                                    .get(Constants.INT_2)), new Float(
                                    coordinates.get(Constants.INT_3)));
            spatialFilter.setGeometry(JTSHelper.createGeometryFromWKT(
                    JTSHelper
                    .createWKTPolygonFromEnvelope(lowerCorner, upperCorner), srid));
            spatialFilter.setOperator(SpatialOperator.BBOX);
            return spatialFilter;
        }
        return null;
    }


    @Setting(MiscSettings.SRS_NAME_PREFIX_SOS_V2)
    public void setSrsNamePrefixV2(String srsNamePrefixV2) {
        this.srsNamePrefixV2 = CRSHelper.asHttpPrefix(srsNamePrefixV2);
    }

    @Setting(MiscSettings.SRS_NAME_PREFIX_SOS_V1)
    public void setSrsNamePrevixV1(String srsNamePrevixV1) {
        this.srsNamePrevixV1 = CRSHelper.asUrnPrefix(srsNamePrefixV2);
    }


    /**
     * @return
     */
    protected String getSrsNamePrefix() {
        return this.srsNamePrevixV1;
    }

    /**
     * @return
     */
    protected String getSrsNamePrefixSosV2() {
        return this.srsNamePrefixV2;
    }

    /**
     * @param filterString
     * @param filterLanguage
     *
     * @return
     *
     * @throws OwsExceptionReport
     */
    protected Filter<?> parseFilter(String filterString, String filterLanguage)
            throws OwsExceptionReport {
        if (StringHelper.isNotEmpty(filterLanguage) &&
                 !FilterConstants.FILTER_LANGUAGE_FES_FILTER
            .equals(filterLanguage)) {
            // TODO throw not supported language
        }
        // TODO does this work or call explicit FES decoder
        return (Filter<?>) CodingHelper.decodeXmlObject(filterString);
    }

    /**
     * @param value
     *
     * @return
     */
    protected List<String> parseJoinQueries(String value) {
        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }
        if (!value.contains(")(")) {
            return Collections.singletonList(value);
        }
        return Arrays.stream(value.split("\\)\\("))
                .map(s -> s.replace("(", "").replace(")", ""))
                .collect(Collectors.toList());
    }
}
