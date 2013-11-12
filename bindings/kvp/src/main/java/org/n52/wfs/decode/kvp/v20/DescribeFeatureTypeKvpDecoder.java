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
import org.n52.sos.exception.ows.concrete.MissingVersionParameterException;
import org.n52.sos.exception.ows.concrete.ParameterNotSupportedException;
import org.n52.sos.exception.ows.concrete.UnsupportedDecoderInputException;
import org.n52.sos.ogc.ows.CompositeOwsException;
import org.n52.sos.ogc.ows.OWSConstants;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.request.AbstractServiceRequest;
import org.n52.sos.util.CollectionHelper;
import org.n52.sos.util.KvpHelper;
import org.n52.sos.util.http.MediaTypes;
import org.n52.wfs.request.DescribeFeatureTypeRequest;

/**
 * WFS 2.0 DescribeFeatureType request encoder for KVP binding
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 *
 */
public class DescribeFeatureTypeKvpDecoder extends AbstractWfsKvpDecoder {

    private static final DecoderKey KVP_DECODER_KEY_TYPE = new OperationDecoderKey(WfsConstants.WFS,
            WfsConstants.VERSION, WfsConstants.Operations.DescribeFeatureType, MediaTypes.APPLICATION_KVP);

    @Override
    public Set<DecoderKey> getDecoderKeyTypes() {
        return Collections.singleton(KVP_DECODER_KEY_TYPE);
    }

    @Override
    public AbstractServiceRequest decode(Map<String, String> element) throws OwsExceptionReport,
            UnsupportedDecoderInputException {
        DescribeFeatureTypeRequest request = new DescribeFeatureTypeRequest();
        CompositeOwsException exceptions = new CompositeOwsException();

        boolean foundService = false;
        boolean foundVersion = false;
        List<String> qNameStringsForTypeName = null;

        for (String parameterName : element.keySet()) {
            String parameterValues = element.get(parameterName);
            try {
                // service (mandatory)
                if (parameterName.equalsIgnoreCase(OWSConstants.RequestParams.service.name())) {
                    request.setService(KvpHelper.checkParameterSingleValue(parameterValues, parameterName));
                    foundService = true;
                } // version (mandatory)
                else if (parameterName.equalsIgnoreCase(OWSConstants.RequestParams.version.name())) {
                    request.setVersion(KvpHelper.checkParameterSingleValue(parameterValues, parameterName));
                    foundVersion = true;
                } // request (mandatory)
                else if (parameterName.equalsIgnoreCase(OWSConstants.RequestParams.request.name())) {
                    KvpHelper.checkParameterSingleValue(parameterValues, parameterName);
                }
                // namespaces (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdditionalCommonKeywordsParams.Namespaces.name())) {
                    request.setNamespaces(parseNamespaces(parameterValues));
                }
                // typeName (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.DescribeFeatureTypeParams.TypeName.name())) {
                    qNameStringsForTypeName = KvpHelper.checkParameterMultipleValues(parameterValues, parameterName);
                    // outputFormat (optional)
                } else if (parameterName.equalsIgnoreCase(WfsConstants.StandardPresentationParams.OutputFormat.name())) {
                    KvpHelper.checkParameterSingleValue(parameterValues, parameterName);
                } else {
                    exceptions.add(new ParameterNotSupportedException(parameterName));
                }
            } catch (OwsExceptionReport owse) {
                exceptions.add(owse);
            }
        }

        if (!foundService) {
            exceptions.add(new MissingServiceParameterException());
        }

        if (!foundVersion) {
            exceptions.add(new MissingVersionParameterException());
        }

        if (CollectionHelper.isNotEmpty(qNameStringsForTypeName)) {
            request.setTypeNames(createQNames(qNameStringsForTypeName, request.getNamespaces(),
                    WfsConstants.AdHocQueryParams.TypeNames.name()));
        }

        exceptions.throwIfNotEmpty();

        return request;
    }
}
