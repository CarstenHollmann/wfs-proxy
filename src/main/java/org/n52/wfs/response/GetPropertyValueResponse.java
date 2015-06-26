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
import org.n52.ogc.wfs.ValueCollection;
import org.n52.ogc.wfs.WfsConstants;

/**
 * Class represents a WFS GetPropertyValue response
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetPropertyValueResponse extends AbstractServiceResponse {

    private ValueCollection valueCollection;

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.GetPropertyValue.name();
    }

    /**
     * Get value collection
     *
     * @return Value collection
     */
    public ValueCollection getValueCollection() {
        return valueCollection;
    }

    /**
     * Set value collection
     *
     * @param valueCollection
     *            Value collection to set
     */
    public void setValueCollection(ValueCollection valueCollection) {
        this.valueCollection = valueCollection;
    }

}