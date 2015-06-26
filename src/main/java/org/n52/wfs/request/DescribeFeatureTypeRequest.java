/*
 * Copyright 2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    private MediaType outputFormat = MediaTypes.APPLICTION_GML_32;

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
