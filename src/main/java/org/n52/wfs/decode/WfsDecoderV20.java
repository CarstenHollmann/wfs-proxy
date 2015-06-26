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
package org.n52.wfs.decode;

import java.util.Collections;
import java.util.Set;

import net.opengis.wfs.x20.GetCapabilitiesDocument;

import org.apache.xmlbeans.XmlObject;

import org.n52.sos.util.XmlHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.n52.iceland.coding.decode.Decoder;
import org.n52.iceland.coding.decode.DecoderKey;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.exception.ows.concrete.UnsupportedDecoderInputException;
import org.n52.iceland.service.AbstractServiceCommunicationObject;

/**
 * WFS 2.0 XML decoder class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class WfsDecoderV20 implements Decoder<AbstractServiceCommunicationObject, XmlObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WfsDecoderV20.class);

    @Override
    public Set<DecoderKey> getKeys() {
        // TODO Auto-generated method stub
        return Collections.emptySet();
    }

    @Override
    public AbstractServiceCommunicationObject decode(XmlObject xml) throws OwsExceptionReport,
            UnsupportedDecoderInputException {
        LOGGER.debug("REQUESTTYPE:" + xml.getClass());
        // validate document
        XmlHelper.validateDocument(xml);
        if (xml instanceof GetCapabilitiesDocument) {
            return parseGetCapabilities((GetCapabilitiesDocument) xml);
        } else {
            throw new UnsupportedDecoderInputException(this, xml);
        }
    }

    /**
     * Parse XML GetCapabilities request
     *
     * @param xml
     *            XML GetCapabilities request to parse
     * @return Service GetCapabilities request
     */
    private AbstractServiceCommunicationObject parseGetCapabilities(GetCapabilitiesDocument xml) {
        // TODO Auto-generated method stub
        return null;
    }

}
