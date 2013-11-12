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
package org.n52.wfs.decode.kvp.v20;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.decode.DecoderKey;
import org.n52.sos.decode.OperationDecoderKey;
import org.n52.sos.exception.ows.concrete.MissingServiceParameterException;
import org.n52.sos.exception.ows.concrete.ParameterNotSupportedException;
import org.n52.sos.exception.ows.concrete.UnsupportedDecoderInputException;
import org.n52.sos.ogc.ows.CompositeOwsException;
import org.n52.sos.ogc.ows.OWSConstants;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.request.AbstractServiceRequest;
import org.n52.sos.util.KvpHelper;
import org.n52.sos.util.http.MediaTypes;
import org.n52.wfs.request.GetCapabilitiesRequest;

import com.google.common.collect.Sets;

/**
 * WFS 2.0 GetCapabilities request encoder for KVP binding
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 *
 */
public class GetCapabilitiesKvpDecoder extends AbstractWfsKvpDecoder {

    private static final Set<DecoderKey> KVP_DECODER_KEY_TYPE = Sets.<DecoderKey> newHashSet(
            new OperationDecoderKey(WfsConstants.WFS, null, WfsConstants.Operations.GetCapabilities.name(),
                    MediaTypes.APPLICATION_KVP),
            new OperationDecoderKey(WfsConstants.WFS, WfsConstants.VERSION, WfsConstants.Operations.GetCapabilities
                    .name(), MediaTypes.APPLICATION_KVP));

    @Override
    public Set<DecoderKey> getDecoderKeyTypes() {
        return Collections.unmodifiableSet(KVP_DECODER_KEY_TYPE);
    }

    @Override
    public AbstractServiceRequest decode(Map<String, String> element) throws OwsExceptionReport,
            UnsupportedDecoderInputException {
        GetCapabilitiesRequest request = new GetCapabilitiesRequest();
        CompositeOwsException exceptions = new CompositeOwsException();

        boolean foundService = false;

        for (String parameterName : element.keySet()) {
            String parameterValues = element.get(parameterName);
            try {
                // service (mandatory SOS 1.0.0, SOS 2.0 default)
                if (parameterName.equalsIgnoreCase(OWSConstants.RequestParams.service.name())) {
                    request.setService(KvpHelper.checkParameterSingleValue(parameterValues, parameterName));
                    foundService = true;
                } // request (mandatory)
                else if (parameterName.equalsIgnoreCase(OWSConstants.RequestParams.request.name())) {
                    KvpHelper.checkParameterSingleValue(parameterValues, parameterName);
                } // acceptVersions (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.GetCapabilitiesParams.AcceptVersions.name())) {
                    List<String> acceptVersions =
                            KvpHelper.checkParameterMultipleValues(parameterValues, parameterName);
                    request.setAcceptVersions(acceptVersions);
                } // acceptFormats (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.GetCapabilitiesParams.AcceptFormats.name())) {
                    request.setAcceptFormats(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName));
                } // updateSequence (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.GetCapabilitiesParams.updateSequence.name())) {
                    request.setUpdateSequence(KvpHelper.checkParameterSingleValue(parameterValues, parameterName));
                } // sections (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.GetCapabilitiesParams.Sections.name())) {
                    request.setSections(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName));
                } else {
                    exceptions.add(new ParameterNotSupportedException(parameterName));
                }
            } catch (OwsExceptionReport owse) {
                exceptions.add(owse);
            }
        }

        if (!foundService) {
            exceptions.add(new MissingServiceParameterException().at(OWSConstants.RequestParams.service));
        }

        exceptions.throwIfNotEmpty();

        return request;
    }

}
