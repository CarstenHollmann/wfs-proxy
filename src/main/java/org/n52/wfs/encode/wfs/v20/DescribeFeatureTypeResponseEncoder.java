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
package org.n52.wfs.encode.wfs.v20;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.wfs.exception.wfs.OperationProcessingFailedException;
import org.n52.wfs.response.DescribeFeatureTypeResponse;

/**
 * WFS 2.0 DescribeFeatureType response encoder class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class DescribeFeatureTypeResponseEncoder extends AbstractWfsResponseEncoder<DescribeFeatureTypeResponse> {

    /**
     * constructor
     */
    public DescribeFeatureTypeResponseEncoder() {
        super(WfsConstants.Operations.DescribeFeatureType.name(), DescribeFeatureTypeResponse.class);
    }

    @Override
    protected XmlObject create(DescribeFeatureTypeResponse response) throws OwsExceptionReport {
        if (response.isSetFeatureTypeDescription()) {
            try {
                return XmlObject.Factory.parse(response.getFeatureTypeDescription());
            } catch (XmlException e) {
                throw new OperationProcessingFailedException().causedBy(e).withMessage(
                        "Error while encoding featureType description!");
            }
        }
        return XmlObject.Factory.newInstance();
    }

}
