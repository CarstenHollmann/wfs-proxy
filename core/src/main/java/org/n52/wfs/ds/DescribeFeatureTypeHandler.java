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
package org.n52.wfs.ds;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;

import org.n52.iceland.exception.CodedException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.om.OmConstants;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.ogc.om.features.SfConstants;
import org.n52.wfs.exception.wfs.OperationProcessingFailedException;
import org.n52.wfs.request.DescribeFeatureTypeRequest;
import org.n52.wfs.response.DescribeFeatureTypeResponse;

import com.google.common.annotations.VisibleForTesting;

/**
 * WFS DAO class for DescribeFeatureType operation
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class DescribeFeatureTypeHandler extends AbstractDescribeFeatureTypeHandler {

    public DescribeFeatureTypeHandler() {
        super(WfsConstants.WFS);
    }

    @Override
    public DescribeFeatureTypeResponse describeFeatureType(DescribeFeatureTypeRequest request) throws OwsExceptionReport {
        DescribeFeatureTypeResponse response = new DescribeFeatureTypeResponse();
        response.setService(request.getService());
        response.setVersion(request.getVersion());
        response.setFeatureTypeDescription(getFeatureTypeDescription(request.getTypeNames()));
        return response;
    }

    /**
     * Get the featureType description for typeNames
     *
     * @param typeNames
     *            TypeNames to get featureType description for
     * @return FeatureType description
     * @throws CodedException
     *             If an error occurs during description creation
     */
    private String getFeatureTypeDescription(Set<QName> typeNames)
            throws OwsExceptionReport {
        String link = getTypeNameSchemaLink(typeNames);
        try (InputStream inputStream = getClass().getResourceAsStream(link)) {
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer);
            return writer.toString();
        } catch (IOException ioe) {
            throw new OperationProcessingFailedException().causedBy(ioe)
                    .withMessage("Error while reading featureType description!");
        }
    }

    @VisibleForTesting
    protected String getTypeNameSchemaLink(Set<QName> typeNames) {
        for (QName qName : typeNames) {
            if (checkQNameOfType(qName, OmConstants.QN_OM_20_OBSERVATION)) {
                return "/observation.xsd";
            } else if (checkQNameOfType(qName, SfConstants.QN_SAMS_20_SPATIAL_SAMPLING_FEATURE)) {
                return "/spatialSamplingFeature.xsd";
            } else {
                return "/feature.xsd";
            }
        }
        return null;
    }
    
    private boolean checkQNameOfType(QName toCheck, QName against) {
        if (toCheck != null) {
            if (against.equals(toCheck)) {
                return true;
            } else {
                return against.getLocalPart().equals(toCheck.getLocalPart());
            }
        }
        return false;
    }

}
