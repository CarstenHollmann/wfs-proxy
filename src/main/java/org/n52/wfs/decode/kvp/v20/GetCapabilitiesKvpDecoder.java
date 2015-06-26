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
package org.n52.wfs.decode.kvp.v20;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.n52.iceland.coding.decode.DecoderKey;
import org.n52.iceland.coding.decode.OperationDecoderKey;
import org.n52.iceland.exception.ows.CompositeOwsException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.exception.ows.concrete.MissingServiceParameterException;
import org.n52.iceland.exception.ows.concrete.ParameterNotSupportedException;
import org.n52.iceland.exception.ows.concrete.UnsupportedDecoderInputException;
import org.n52.iceland.ogc.ows.OWSConstants;
import org.n52.iceland.request.AbstractServiceRequest;
import org.n52.iceland.util.KvpHelper;
import org.n52.iceland.util.http.MediaTypes;
import org.n52.ogc.wfs.WfsConstants;
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
            new OperationDecoderKey(WfsConstants.WFS, null, WfsConstants.Operations.GetCapabilities.name(), MediaTypes.APPLICATION_KVP),
            new OperationDecoderKey(WfsConstants.WFS, WfsConstants.VERSION, WfsConstants.Operations.GetCapabilities .name(), MediaTypes.APPLICATION_KVP));

    @Override
    public Set<DecoderKey> getKeys() {
        return Collections.unmodifiableSet(KVP_DECODER_KEY_TYPE);
    }

    @Override
    public GetCapabilitiesRequest decode(Map<String, String> element) throws OwsExceptionReport,
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
