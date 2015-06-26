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
