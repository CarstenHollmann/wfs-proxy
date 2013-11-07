/**
 * Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * This program is free software; you can redistribute and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the
 * Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; even without the implied
 * WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program (see gnu-gpl v2.txt). If not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or
 * visit the Free Software Foundation web page, http://www.fsf.org.
 */
package org.n52.wfs.decode;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import net.opengis.wfs.x20.GetCapabilitiesDocument;

import org.apache.xmlbeans.XmlObject;
import org.n52.sos.decode.Decoder;
import org.n52.sos.decode.DecoderKey;
import org.n52.sos.exception.ows.concrete.UnsupportedDecoderInputException;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.service.AbstractServiceCommunicationObject;
import org.n52.sos.service.ServiceConstants.SupportedTypeKey;
import org.n52.sos.util.XmlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WfsDecoderV20 implements Decoder<AbstractServiceCommunicationObject, XmlObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WfsDecoderV20.class);
    
    @Override
    public Set<String> getConformanceClasses() {
        // TODO Auto-generated method stub
        return Collections.emptySet();
    }

    @Override
    public Set<DecoderKey> getDecoderKeyTypes() {
        // TODO Auto-generated method stub
        return Collections.emptySet();
    }

    @Override
    public Map<SupportedTypeKey, Set<String>> getSupportedTypes() {
        // TODO Auto-generated method stub
        return Collections.emptyMap();
    }

    @Override
    public AbstractServiceCommunicationObject decode(XmlObject xml) throws OwsExceptionReport,
            UnsupportedDecoderInputException {
        LOGGER.debug("REQUESTTYPE:" + xml.getClass());
        // validate document
        XmlHelper.validateDocument(xml);
        if (xml instanceof GetCapabilitiesDocument) {
            return parseGetCapabilities((GetCapabilitiesDocument)xml);
        } else {
                throw new UnsupportedDecoderInputException(this, xml);
            }
    }

    private AbstractServiceCommunicationObject parseGetCapabilities(GetCapabilitiesDocument xml) {
        // TODO Auto-generated method stub
        return null;
    }

}
