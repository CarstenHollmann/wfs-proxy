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
import org.n52.sos.ogc.filter.FilterConstants;
import org.n52.sos.ogc.ows.CompositeOwsException;
import org.n52.sos.ogc.ows.OWSConstants;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.request.AbstractServiceRequest;
import org.n52.sos.util.CollectionHelper;
import org.n52.sos.util.KvpHelper;
import org.n52.sos.util.StringHelper;
import org.n52.sos.util.http.MediaTypes;
import org.n52.wfs.request.GetFeatureRequest;

public class GetFeatureKvpDecoder extends AbstractWfsKvpDecoder {

    private static final DecoderKey KVP_DECODER_KEY_TYPE = new OperationDecoderKey(WfsConstants.WFS,
            WfsConstants.VERSION, WfsConstants.Operations.GetFeature, MediaTypes.APPLICATION_KVP);

    @Override
    public Set<DecoderKey> getDecoderKeyTypes() {
        return Collections.singleton(KVP_DECODER_KEY_TYPE);
    }

    @Override
    public AbstractServiceRequest decode(Map<String, String> element) throws OwsExceptionReport,
            UnsupportedDecoderInputException {
        GetFeatureRequest request = new GetFeatureRequest();
        CompositeOwsException exceptions = new CompositeOwsException();

        boolean foundService = false;
        boolean foundVersion = false;
        List<String> qNameStringsForTypeName = null;
        int  foundCountFilterResourceIdBBox = 0;
        String filterString = null;

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
                    request.setNamespaces(parseNamespaces(parameterName));
                // outputFormat (optional)
                } else if (parameterName.equalsIgnoreCase(WfsConstants.StandardPresentationParams.OutputFormat.name())) {
                    KvpHelper.checkParameterSingleValue(parameterValues, parameterName);
                }
                // typeNames (mandatory if ResourceId is defined, optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.TypeNames.name())) {
                    qNameStringsForTypeName = KvpHelper.checkParameterMultipleValues(parameterValues, parameterName);    
                }
                // aliases (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.Aliases.name())) {
                    request.setAliases(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName));    
                }
                // SrsName (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.SrsName.name())) {
                    request.setSrsName(KvpHelper.checkParameterSingleValue(parameterValues, parameterName));    
                }
                // PropertyName (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.ProjectionClauseParams.PropertyName.name())) {
                    request.setPropertyNames(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName));    
                }
                // Filter (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.Filter.name())) {
                    filterString = parameterValues;
                    foundCountFilterResourceIdBBox++;
                }
                // Filter_Language (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.Filter_Language.name())) {
                    request.setFilterLanguage(KvpHelper.checkParameterSingleValue(parameterValues, parameterName));   
                }
                // ResourceId (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.ResourceId.name())) {
                    request.setResourceIds(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName)); 
                    foundCountFilterResourceIdBBox++;
                }
                // BBox (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.BBox.name())) {
                    request.setBBox(parseSpatialFilter(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName), parameterName));
                    foundCountFilterResourceIdBBox++;
                }
                // SortBy (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.SortBy.name())) {
                    request.setSortBy(parseSortBy(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName)));
                }
                 else {
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
            request.setTypeNames(createQNames(qNameStringsForTypeName, request.getNamespaces()));
        }
        
        if (request.isSetResourceIds()  && CollectionHelper.isEmpty(qNameStringsForTypeName)) {
            // TODO throw exception
        }
        
        if (foundCountFilterResourceIdBBox > 1) {
            // TODO throw OperationNotSupported
        }
        
        if (StringHelper.isNotEmpty(filterString)) {
            request.setFilter(parseFilter(filterString, request.getFilterLanguage()));
        }

        exceptions.throwIfNotEmpty();

        return request;
    }
}
