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
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsFeatureCollection;

/**
 * Class represents a WFS GetFeature response
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetFeatureResponse extends AbstractServiceResponse {

    private WfsFeatureCollection featureCollection;

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.GetFeature.name();
    }

    /**
     * Get feature collection
     *
     * @return the featureCollection
     */
    public WfsFeatureCollection getFeatureCollection() {
        return featureCollection;
    }

    /**
     * Set feature collection
     *
     * @param featureCollection
     *            the featureCollection to set
     */
    public void setFeatureCollection(WfsFeatureCollection featureCollection) {
        this.featureCollection = featureCollection;
    }

}
