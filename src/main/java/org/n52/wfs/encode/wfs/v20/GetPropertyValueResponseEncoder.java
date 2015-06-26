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

import javax.inject.Inject;

import net.opengis.wfs.x20.ValueCollectionDocument;
import net.opengis.wfs.x20.ValueCollectionType;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.util.Producer;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.wfs.response.GetPropertyValueResponse;

/**
 * WFS 2.0 GetPropertyValue response encoder class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetPropertyValueResponseEncoder extends AbstractWfsResponseEncoder<GetPropertyValueResponse> {

    private Producer<XmlOptions> xmlOptions;

    public GetPropertyValueResponseEncoder(){
        super(WfsConstants.Operations.GetPropertyValue.name(), GetPropertyValueResponse.class);
    }

    @Inject
    public void setXmlOptions(Producer<XmlOptions> xmlOptions) {
        this.xmlOptions = xmlOptions;
    }

    @Override
    protected XmlObject create(GetPropertyValueResponse arg0)
            throws OwsExceptionReport {
        ValueCollectionDocument valueCollectionDoc
                = ValueCollectionDocument.Factory.newInstance(this.xmlOptions.get());
        ValueCollectionType valueCollectionType = valueCollectionDoc
                .addNewValueCollection();
        return valueCollectionDoc;
    }

}
