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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsQuery;
import org.n52.sos.decode.DecoderKey;
import org.n52.sos.decode.OperationDecoderKey;
import org.n52.sos.exception.ows.concrete.MissingServiceParameterException;
import org.n52.sos.exception.ows.concrete.MissingVersionParameterException;
import org.n52.sos.exception.ows.concrete.ParameterNotSupportedException;
import org.n52.sos.exception.ows.concrete.UnsupportedDecoderInputException;
import org.n52.sos.ogc.filter.AbstractProjectionClause;
import org.n52.sos.ogc.filter.FesSortBy;
import org.n52.sos.ogc.filter.SpatialFilter;
import org.n52.sos.ogc.ows.CompositeOwsException;
import org.n52.sos.ogc.ows.OWSConstants;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.request.AbstractServiceRequest;
import org.n52.sos.util.CollectionHelper;
import org.n52.sos.util.KvpHelper;
import org.n52.sos.util.StringHelper;
import org.n52.sos.util.http.MediaTypes;
import org.n52.wfs.request.GetFeatureRequest;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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

        Map<String, String> namespaces = Maps.newHashMap();
        List<String> aliases = Lists.newArrayList();
        String srsName = null;
        List<String> propertyNames = Lists.newArrayList();
        List<String> typeNames = Lists.newArrayList();
        String filterString = null;
        String filterLanguage = null;
        SpatialFilter spatialFilter = null;
        FesSortBy sortBy = null;

        for (String parameterName : element.keySet()) {
            String parameterValues = element.get(parameterName);
            try {
                // service (mandatory)
                if (parameterName.equalsIgnoreCase(OWSConstants.RequestParams.service.name())) {
                    request.setService(KvpHelper.checkParameterSingleValue(parameterValues, parameterName));
                } 
                // version (mandatory)
                else if (parameterName.equalsIgnoreCase(OWSConstants.RequestParams.version.name())) {
                    request.setVersion(KvpHelper.checkParameterSingleValue(parameterValues, parameterName));
                } 
                // request (mandatory)
                else if (parameterName.equalsIgnoreCase(OWSConstants.RequestParams.request.name())) {
                    KvpHelper.checkParameterSingleValue(parameterValues, parameterName);
                }
                // namespaces (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdditionalCommonKeywordsParams.Namespaces.name())) {
                    namespaces = parseNamespaces(parameterName);
                // outputFormat (optional)
                } else if (parameterName.equalsIgnoreCase(WfsConstants.StandardPresentationParams.OutputFormat.name())) {
                   request.setOutputFormat(KvpHelper.checkParameterSingleValue(parameterValues, parameterName));
                }
                // typeNames (mandatory if ResourceId is not defined, optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.TypeNames.name())) {
                    typeNames = KvpHelper.checkParameterMultipleValues(parameterValues, parameterName);    
                }
                // aliases (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.Aliases.name())) {
                    aliases = KvpHelper.checkParameterMultipleValues(parameterValues, parameterName);    
                }
                // SrsName (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.SrsName.name())) {
                    srsName = KvpHelper.checkParameterSingleValue(parameterValues, parameterName);    
                }
                // PropertyName (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.ProjectionClauseParams.PropertyName.name())) {
                    propertyNames = KvpHelper.checkParameterMultipleValues(parameterValues, parameterName);    
                }
                // Filter (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.Filter.name())) {
                    filterString = parameterValues;
                }
                // Filter_Language (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.Filter_Language.name())) {
                    filterLanguage = KvpHelper.checkParameterSingleValue(parameterValues, parameterName);   
                }
                // ResourceId (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.ResourceId.name())) {
                    request.setResourceIds(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName)); 
                }
                // BBox (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.BBox.name())) {
                    spatialFilter = parseSpatialFilter(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName), parameterName);
                }
                // SortBy (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.SortBy.name())) {
                   sortBy = parseSortBy(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName));
                }
                 else {
                    exceptions.add(new ParameterNotSupportedException(parameterName));
                }
                
            } catch (OwsExceptionReport owse) {
                exceptions.add(owse);
            }
        }

        if (Strings.isNullOrEmpty(request.getService())) {
            exceptions.add(new MissingServiceParameterException());
        }

        if (Strings.isNullOrEmpty(request.getVersion())) {
            exceptions.add(new MissingVersionParameterException());
        }
        
        // filter, resourceId and bbox are mutually exclusive
        checkFilterResourceIdBBox(filterString, request.getResourceIds(), spatialFilter);
        
        if (spatialFilter != null) {
            request.setBBox(spatialFilter);
        }
        
        if (CollectionHelper.isNotEmpty(typeNames)) {
            if (StringHelper.isNotEmpty(filterString)) {
                List<String> parseJoinQueries = parseJoinQueries(filterString);
                if (parseJoinQueries.size() > 1) {
                    // TODO throw not supported exception
                }
                for (String singleFilterString : parseJoinQueries) {
                    WfsQuery query = new WfsQuery( createQNames(typeNames, namespaces));
                    query.setSelectionClause(parseFilter(singleFilterString, filterLanguage));
                    request.addQuery(query);
                }
            } else {
                request.addQuery(new WfsQuery( createQNames(typeNames, namespaces)));
            }
            for (WfsQuery query : request.getQueries()) {
                if (CollectionHelper.isNotEmpty(aliases)) {
                    query.setAliases(aliases);
                }
                if (CollectionHelper.isNotEmpty(propertyNames)) {
                    query.setProjectionClauses(parsePropertyNames(propertyNames));
                }
                if (sortBy != null) {
                    query.setSortingClause(sortBy);
                }
                if (StringHelper.isNotEmpty(srsName)) {
                    query.setSrsName(srsName);
                }
            }
        } else {
            // TODO typeNames is missing
        }
        
        exceptions.throwIfNotEmpty();

        return request;
    }

    private Set<AbstractProjectionClause> parsePropertyNames(List<String> propertyNames) {
        // TODO Auto-generated method stub
        return null;
    }

    private void checkFilterResourceIdBBox(String filterString, Collection<String> resourceIds, SpatialFilter spatialFilter) {
       int counter = 0;
       if (StringHelper.isNotEmpty(filterString)) {
           counter++;
       }
       if (CollectionHelper.isNotEmpty(resourceIds)) {
           counter++;
       }
       if (spatialFilter != null) {
           counter++;
       }
       if (counter > 1) {
           // TODO throw OperationNotSupported;
       }
        
    }
}
