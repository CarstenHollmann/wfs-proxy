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
package org.n52.wfs.request;

import java.util.Set;

import javax.xml.namespace.QName;

import org.n52.iceland.util.CollectionHelper;
import org.n52.iceland.util.http.MediaType;
import org.n52.iceland.util.http.MediaTypes;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.wfs.response.DescribeFeatureTypeResponse;

import com.google.common.collect.Sets;

/**
 * WFS DescribeFeatureType service request
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class DescribeFeatureTypeRequest extends AbstractWfsServiceRequest<DescribeFeatureTypeResponse> {

    /* 0..* */
    private Set<QName> typeNames = Sets.newHashSet();

    /* 1..1 */
    private MediaType outputFormat = MediaTypes.APPLICATION_GML_32;

    private DescribeFeatureTypeResponse response;

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.DescribeFeatureType.name();
    }

    @Override
    public DescribeFeatureTypeResponse getResponse() {
        return response;
    }

    public void setResponse(DescribeFeatureTypeResponse response) {
        this.response = response;
    }

    /**
     * Get type names
     *
     * @return the type names
     */
    public Set<QName> getTypeNames() {
        return typeNames;
    }

    /**
     * Add a type name
     *
     * @param typeNames
     *            the type name to add
     * @return DescribeFeatureTypeRequest
     */
    public DescribeFeatureTypeRequest addTypeNames(QName typeNames) {
        getTypeNames().add(typeNames);
        return this;
    }

    /**
     * Add type names
     *
     * @param typeNames
     *            the type names to add
     * @return DescribeFeatureTypeRequest
     */
    public DescribeFeatureTypeRequest addTypeNames(Set<QName> typeNames) {
        getTypeNames().addAll(typeNames);
        return this;
    }

    /**
     * Set type names
     *
     * @param typeNames
     *            type names to set
     * @return DescribeFeatureTypeRequest
     */
    public DescribeFeatureTypeRequest setTypeNames(Set<QName> typeNames) {
        this.typeNames = typeNames;
        return this;
    }

    /**
     * Check if type names are set
     *
     * @return <code>true</code>, if type names are set
     */
    public boolean isSetTypeNames() {
        return CollectionHelper.isNotEmpty(getTypeNames());
    }

    /**
     * Get output format
     *
     * @return the output format
     */
    public MediaType getOutputFormat() {
        return outputFormat;
    }

    /**
     * Set output format
     *
     * @param outputFormat
     *            the output format to set
     */
    public void setOutputFormat(MediaType outputFormat) {
        this.outputFormat = outputFormat;
    }

}
