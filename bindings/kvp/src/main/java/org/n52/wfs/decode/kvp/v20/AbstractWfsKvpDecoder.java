/**
 * Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * This program is free software; you can redistribute and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the
 * Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; even without the implied
 * WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program (see gnu-gpl v2.txt). If not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or
 * visit the Free Software Foundation web page, http://www.fsf.org.
 */
package org.n52.wfs.decode.kvp.v20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

import javax.xml.namespace.QName;

import org.n52.ogc.wfs.WfsConstants.AdditionalCommonKeywordsParams;
import org.n52.sos.config.annotation.Configurable;
import org.n52.sos.config.annotation.Setting;
import org.n52.sos.decode.Decoder;
import org.n52.sos.ds.FeatureQuerySettingsProvider;
import org.n52.sos.exception.CodedException;
import org.n52.sos.exception.ConfigurationException;
import org.n52.sos.exception.ows.InvalidParameterValueException;
import org.n52.sos.exception.ows.MissingParameterValueException;
import org.n52.sos.exception.ows.OptionNotSupportedException;
import org.n52.sos.ogc.filter.FesSortBy;
import org.n52.sos.ogc.filter.Filter;
import org.n52.sos.ogc.filter.FilterConstants;
import org.n52.sos.ogc.filter.FilterConstants.SpatialOperator;
import org.n52.sos.ogc.filter.SpatialFilter;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.request.AbstractServiceRequest;
import org.n52.sos.service.ServiceConfiguration;
import org.n52.sos.service.ServiceConstants;
import org.n52.sos.util.CodingHelper;
import org.n52.sos.util.CollectionHelper;
import org.n52.sos.util.Constants;
import org.n52.sos.util.JTSHelper;
import org.n52.sos.util.SosHelper;
import org.n52.sos.util.StringHelper;
import org.n52.sos.util.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Abstract WFS request encoder for KVP binding
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 *
 */
@Configurable
public abstract class AbstractWfsKvpDecoder implements Decoder<AbstractServiceRequest, Map<String, String>> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractWfsKvpDecoder.class);

    protected static final int VALID_COORDINATE_SIZE = 4;

    private int defaultEPSG;

    private int default3DEPSG;

    private static final String PARANTHESES = Constants.CLOSE_BRACE_STRING + Constants.OPEN_BRACE_STRING;

    @Override
    public Set<String> getConformanceClasses() {
        return Collections.emptySet();
    }

    @Override
    public Map<ServiceConstants.SupportedTypeKey, Set<String>> getSupportedTypes() {
        return Collections.emptyMap();
    }

    /**
     * @return
     */
    public int getDefaultEPSG() {
        return defaultEPSG;
    }

    /**
     * @return
     */
    public int getDefault3DEPSG() {
        return default3DEPSG;
    }

    /**
     * @param epsgCode
     * @throws ConfigurationException
     */
    @Setting(FeatureQuerySettingsProvider.DEFAULT_EPSG)
    public void setDefaultEpsg(final int epsgCode) throws ConfigurationException {
        Validation.greaterZero("Default EPSG Code", epsgCode);
        defaultEPSG = epsgCode;
    }

    /**
     * @param epsgCode3D
     * @throws ConfigurationException
     */
    @Setting(FeatureQuerySettingsProvider.DEFAULT_3D_EPSG)
    public void setDefault3DEpsg(final int epsgCode3D) throws ConfigurationException {
        Validation.greaterZero("Default 3D EPSG Code", epsgCode3D);
        default3DEPSG = epsgCode3D;
    }

    /**
     * @param qNameStrings
     * @param namespaces
     * @param parameterName
     * @return
     * @throws OwsExceptionReport
     */
    protected Set<QName> createQNames(List<String> qNameStrings, Map<String, String> namespaces, String parameterName) throws OwsExceptionReport {
        Set<QName> qNames = Sets.newHashSet();
        for (String qNameString : qNameStrings) {
            qNames.add(createQName(qNameString, namespaces, parameterName));
        }
        return qNames;
    }

    /**
     * @param qnameString
     * @param namespaces
     * @param parameterName
     * @return
     * @throws OwsExceptionReport
     */
    protected QName createQName(String qnameString, Map<String, String> namespaces, String parameterName) throws OwsExceptionReport {
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
     * @return
     */
    protected Map<String, String> parseNamespaces(String parameterValues) {
        List<String> array =
                Arrays.asList(parameterValues.replaceAll("\\),", "").replaceAll("\\)", "").split("xmlns\\("));
        Map<String, String> namespaces = new HashMap<String, String>(array.size());
        for (String string : array) {
            if (string != null && !string.isEmpty()) {
                String[] s = string.split(",");
                namespaces.put(s[0], s[1]);
            }
        }
        return namespaces;
    }

    /**
     * @param sortByList
     * @return
     * @throws CodedException 
     */
    protected FesSortBy parseSortBy(List<String> sortByList) throws CodedException {
        throw new OptionNotSupportedException().withMessage("This service does not support result sorting!");
    }

    /**
     * @param parameterValues
     * @param parameterName
     * @return
     * @throws OwsExceptionReport
     */
    protected SpatialFilter parseSpatialFilter(List<String> parameterValues, String parameterName)
            throws OwsExceptionReport {
        if (!parameterValues.isEmpty()) {
            if (!(parameterValues instanceof RandomAccess)) {
                parameterValues = new ArrayList<String>(parameterValues);
            }
            SpatialFilter spatialFilter = new SpatialFilter();

            boolean hasSrid = false;

            spatialFilter.setValueReference(parameterValues.get(0));

            int srid = getDefaultEPSG();
            if (parameterValues.get(parameterValues.size() - 1).startsWith(getSrsNamePrefixSosV2())
                    || parameterValues.get(parameterValues.size() - 1).startsWith(getSrsNamePrefix())) {
                hasSrid = true;
                srid = SosHelper.parseSrsName(parameterValues.get(parameterValues.size() - 1));
            }

            List<String> coordinates;
            if (hasSrid) {
                coordinates = parameterValues.subList(1, parameterValues.size() - 1);
            } else {
                coordinates = parameterValues.subList(1, parameterValues.size());
            }

            if (coordinates.size() != VALID_COORDINATE_SIZE) {
                throw new InvalidParameterValueException().at(parameterName).withMessage(
                        "The parameter value is not valid!");
            }
            String lowerCorner =
                    String.format(Locale.US, "%f %f", new Float(coordinates.get(Constants.INT_0)), new Float(
                            coordinates.get(Constants.INT_1)));
            String upperCorner =
                    String.format(Locale.US, "%f %f", new Float(coordinates.get(Constants.INT_2)), new Float(
                            coordinates.get(Constants.INT_3)));
            spatialFilter.setGeometry(JTSHelper.createGeometryFromWKT(
                    JTSHelper.createWKTPolygonFromEnvelope(lowerCorner, upperCorner), srid));
            spatialFilter.setOperator(SpatialOperator.BBOX);
            return spatialFilter;
        }
        return null;
    }

    /**
     * @return
     */
    protected String getSrsNamePrefix() {
        return ServiceConfiguration.getInstance().getSrsNamePrefix();
    }

    /**
     * @return
     */
    protected String getSrsNamePrefixSosV2() {
        return ServiceConfiguration.getInstance().getSrsNamePrefixSosV2();
    }

    /**
     * @param filterString
     * @param filterLanguage
     * @return
     * @throws OwsExceptionReport
     */
    protected Filter<?> parseFilter(String filterString, String filterLanguage) throws OwsExceptionReport {
        if (StringHelper.isNotEmpty(filterLanguage)
                && !FilterConstants.FILTER_LANGUAGE_FES_FILTER.equals(filterLanguage)) {
            // TODO throw not supported language
        }
        // TODO does this work or call explicit FES decoder
        return (Filter<?>) CodingHelper.decodeXmlObject(filterString);
    }

    /**
     * @param value
     * @return
     */
    protected List<String> parseJoinQueries(String value) {
        if (StringHelper.isNotEmpty(value)) {
            if (value.contains(PARANTHESES)) {
                List<String> list = Lists.newArrayList();
                for (String split : value.split(PARANTHESES)) {
                    list.add(split.replace(Constants.OPEN_BRACE_STRING, Constants.EMPTY_STRING).replace(
                            Constants.CLOSE_BRACE_STRING, Constants.EMPTY_STRING));
                }
                return list;
            }
            return Lists.newArrayList(value);
        }
        return Lists.newArrayList();
    }
}
