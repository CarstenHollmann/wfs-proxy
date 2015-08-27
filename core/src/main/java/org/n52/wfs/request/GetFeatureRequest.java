/*
 * Copyright 2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * License version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.wfs.request;

import java.util.Collection;
import java.util.Set;

import org.n52.iceland.util.CollectionHelper;
import org.n52.iceland.util.StringHelper;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsQuery;
import org.n52.sos.ogc.filter.SpatialFilter;
import org.n52.wfs.response.GetFeatureResponse;

import com.google.common.collect.Sets;

/**
 * WFS GetFeature service request
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetFeatureRequest extends AbstractWfsServiceRequest<GetFeatureResponse> {

    private Set<WfsQuery> queries = Sets.newHashSet();

    private String filterLanguage;

    private String outputFormat;

    // resourceId
    private Set<String> resourceIds = Sets.newHashSet();

    // spatial filter
    private SpatialFilter bbox;

    private GetFeatureResponse response;

    public void setResponse(GetFeatureResponse response) {
        this.response = response;
    }

    @Override
    public GetFeatureResponse getResponse() {
        return response;
    }

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.GetFeature.name();
    }

    /**
     * Get the queries
     *
     * @return the queries
     */
    public Set<WfsQuery> getQueries() {
        return queries;
    }

    /**
     * Add a query
     *
     * @param query
     *              the query to add
     *
     * @return GetFeatureRequest
     */
    public GetFeatureRequest addQuery(WfsQuery query) {
        getQueries().add(query);
        return this;
    }

    /**
     * Add queries
     *
     * @param queries
     *                the queries to add
     *
     * @return GetFeatureRequest
     */
    public GetFeatureRequest addQueries(Set<WfsQuery> queries) {
        getQueries().addAll(queries);
        return this;
    }

    /**
     * Check if queries are set
     *
     * @return <code>true</code>, if queries are set
     */
    public boolean isSetQueries() {
        return CollectionHelper.isNotEmpty(getQueries());
    }

    /**
     * Get filter language
     *
     * @return the filter language
     */
    public String getFilterLanguage() {
        return filterLanguage;
    }

    /**
     * Set filter language
     *
     * @param filterLanguage
     *                       the filter language to set
     *
     * @return GetFeatureRequest
     */
    public GetFeatureRequest setFilterLanguage(String filterLanguage) {
        this.filterLanguage = filterLanguage;
        return this;
    }

    /**
     * Check if filter language is set
     *
     * @return <code>true</code>, if filter language is set
     */
    public boolean isSetFilterLanguage() {
        return StringHelper.isNotEmpty(getFilterLanguage());
    }

    /**
     * Get resource ids
     *
     * @return the resource ids
     */
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    /**
     * Add a resource id
     *
     * @param resourceIds
     *                    the resource id to add
     *
     * @return GetFeatureRequest
     */
    public GetFeatureRequest addResourceIds(String resourceIds) {
        getResourceIds().add(resourceIds);
        return this;
    }

    /**
     * Add resource ids
     *
     * @param resourceIds
     *                    The resource ids to add
     *
     * @return GetFeatureRequest
     */
    public GetFeatureRequest addResourceIds(Collection<String> resourceIds) {
        getResourceIds().addAll(resourceIds);
        return this;
    }

    /**
     * Set resource id
     *
     * @param resourceIds
     *                    the resource ids to set
     *
     * @return GetFeatureRequest
     */
    public GetFeatureRequest setResourceIds(Collection<String> resourceIds) {
        this.resourceIds = Sets.newHashSet(resourceIds);
        return this;
    }

    /**
     * Check if resource id are set
     *
     * @return <code>true</code>, if resource ids are set
     */
    public boolean isSetResourceIds() {
        return CollectionHelper.isNotEmpty(getResourceIds());
    }

    /**
     * Get bbox filter
     *
     * @return the bbox filter
     */
    public SpatialFilter getBBox() {
        return bbox;
    }

    /**
     * Set bbox filter
     *
     * @param bbox
     *             the bbox filter to set
     *
     * @return GetFeatureRequest
     */
    public GetFeatureRequest setBBox(SpatialFilter bbox) {
        this.bbox = bbox;
        return this;
    }

    /**
     * Check if bbox filter is set
     *
     * @return <code>true</code>, if bbox filter is set
     */
    public boolean isSetBBox() {
        return getBBox() != null;
    }

    /**
     * Set output format
     *
     * @param outputFormat
     *                     the output format to set
     */
    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     * Get output format
     *
     * @return the output format
     */
    public String getOutputFormat() {
        return outputFormat;
    }

}
