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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.n52.iceland.coding.decode.DecoderKey;
import org.n52.iceland.coding.decode.OperationDecoderKey;
import org.n52.iceland.exception.CodedException;
import org.n52.iceland.exception.ows.CompositeOwsException;
import org.n52.iceland.exception.ows.MissingParameterValueException;
import org.n52.iceland.exception.ows.OptionNotSupportedException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.exception.ows.concrete.MissingServiceParameterException;
import org.n52.iceland.exception.ows.concrete.MissingVersionParameterException;
import org.n52.iceland.exception.ows.concrete.ParameterNotSupportedException;
import org.n52.iceland.exception.ows.concrete.UnsupportedDecoderInputException;
import org.n52.iceland.ogc.ows.OWSConstants;
import org.n52.iceland.request.AbstractServiceRequest;
import org.n52.iceland.util.CollectionHelper;
import org.n52.iceland.util.KvpHelper;
import org.n52.iceland.util.StringHelper;
import org.n52.iceland.util.http.MediaTypes;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsQuery;
import org.n52.sos.ogc.filter.AbstractProjectionClause;
import org.n52.sos.ogc.filter.FesSortBy;
import org.n52.sos.ogc.filter.SpatialFilter;
import org.n52.wfs.exception.wfs.OperationProcessingFailedException;
import org.n52.wfs.request.GetFeatureRequest;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * WFS 2.0 GetFeature request encoder for KVP binding
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetFeatureKvpDecoder extends AbstractWfsKvpDecoder {

    private static final DecoderKey KVP_DECODER_KEY_TYPE = new OperationDecoderKey(WfsConstants.WFS,
            WfsConstants.VERSION, WfsConstants.Operations.GetFeature, MediaTypes.APPLICATION_KVP);

    @Override
    public Set<DecoderKey> getKeys() {
        return Collections.singleton(KVP_DECODER_KEY_TYPE);
    }

    @Override
    public GetFeatureRequest decode(Map<String, String> element) throws OwsExceptionReport,
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
                    namespaces = parseNamespaces(parameterValues);
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
                    spatialFilter =
                            parseSpatialFilter(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName),
                                    parameterName);
                }
                // SortBy (optional)
                else if (parameterName.equalsIgnoreCase(WfsConstants.AdHocQueryParams.SortBy.name())) {
                    sortBy = parseSortBy(KvpHelper.checkParameterMultipleValues(parameterValues, parameterName));
                } else {
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
                    exceptions.add(new OptionNotSupportedException()
                            .withMessage("Joined queries are not yet supported by this service!"));
                }
                for (String singleFilterString : parseJoinQueries) {
                    WfsQuery query =
                            new WfsQuery(createQNames(typeNames, namespaces,
                                    WfsConstants.AdHocQueryParams.TypeNames.name()));
                    query.setSelectionClause(parseFilter(singleFilterString, filterLanguage));
                    request.addQuery(query);
                }
            } else {
                request.addQuery(new WfsQuery(createQNames(typeNames, namespaces,
                        WfsConstants.AdHocQueryParams.TypeNames.name())));
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
            exceptions.add(new MissingParameterValueException(WfsConstants.AdHocQueryParams.TypeNames));
        }

        exceptions.throwIfNotEmpty();

        return request;
    }

    /**
     * Parse requested propertyNames
     *
     * @param propertyNames
     *            Property names to parse
     * @return Abstract projection clause/filter
     * @throws CodedException
     *             If an error occurs
     */
    private Set<AbstractProjectionClause> parsePropertyNames(List<String> propertyNames) throws CodedException {
        throw new OptionNotSupportedException().withMessage("The parameter '{}' is not supported",
                WfsConstants.ProjectionClauseParams.PropertyName.name());
    }

    /**
     * Check if parameter Filter, ResourceId and BBox are mutually exclusive
     *
     * @param filterString
     *            Filter parameter value
     * @param resourceIds
     *            ResourceId parameter value
     * @param spatialFilter
     *            BBox parameter value
     * @throws CodedException
     *             If the parameter are not mutually exclusive
     */
    private void checkFilterResourceIdBBox(String filterString, Collection<String> resourceIds,
            SpatialFilter spatialFilter) throws CodedException {
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
            throw new OperationProcessingFailedException().withMessage(
                    "The parameter '{}','{}' and '{}' are mutually exclusive!", WfsConstants.AdHocQueryParams.Filter,
                    WfsConstants.AdHocQueryParams.ResourceId, WfsConstants.AdHocQueryParams.BBox);
        }

    }
}
