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
package org.n52.wfs.response;

import org.n52.iceland.response.AbstractServiceResponse;
import org.n52.iceland.util.StringHelper;
import org.n52.ogc.wfs.WfsConstants;


/**
 * Class represents a WFS DescribeFeatureType response
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 * 
 */
public class DescribeFeatureTypeResponse extends AbstractServiceResponse {

    private String featureTypeDescription;

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.DescribeFeatureType.name();
    }

    /**
     * Get feature type description
     *
     * @return The feature type description
     */
    public String getFeatureTypeDescription() {
        return featureTypeDescription;
    }

    /**
     * Set feature type description
     *
     * @param featureTypeDescription
     *            the feature type description to set
     * @return DescribeFeatureTypeResponse
     */
    public DescribeFeatureTypeResponse setFeatureTypeDescription(String featureTypeDescription) {
        this.featureTypeDescription = featureTypeDescription;
        return this;
    }

    /**
     * Check if a feature type description is set
     *
     * @return <code>true</code>, if a feature type description is set
     */
    public boolean isSetFeatureTypeDescription() {
        return StringHelper.isNotEmpty(getFeatureTypeDescription());
    }
}
