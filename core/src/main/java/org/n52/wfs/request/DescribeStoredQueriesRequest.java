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
import org.n52.ogc.wfs.WfsConstants;
import org.n52.wfs.response.DescribeStoredQueriesResponse;

import com.google.common.collect.Sets;

/**
 * WFS DescribeStoredQueries service request
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class DescribeStoredQueriesRequest extends AbstractWfsServiceRequest<DescribeStoredQueriesResponse> {

    /* 0..* */
    private Set<String> storedQueryIds = Sets.newHashSet();

    private DescribeStoredQueriesResponse response;

    @Override
    public DescribeStoredQueriesResponse getResponse() {
        return response;
    }

    public void setResponse(DescribeStoredQueriesResponse response) {
        this.response = response;
    }

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.DescribeStoredQueries.name();
    }

    /**
     * Get stored query ids
     *
     * @return the stored query ids
     */
    public Set<String> getStoredQueryIds() {
        return storedQueryIds;
    }

    /**
     * Add a stored query id
     *
     * @param storedQueryId
     *            the stored query id to add
     * @return DescribeStoredQueriesRequest
     */
    public DescribeStoredQueriesRequest addStoredQueryIds(String storedQueryId) {
        getStoredQueryIds().add(storedQueryId);
        return this;
    }

    /**
     * Add a stored query ids
     *
     * @param storedQueryIds
     *            the stored query ids to add
     * @return DescribeStoredQueriesRequest
     */
    public DescribeStoredQueriesRequest addStoredQueryIds(Set<String> storedQueryIds) {
        getStoredQueryIds().addAll(storedQueryIds);
        return this;
    }

    /**
     * Set a stored query ids
     *
     * @param storedQueryIds
     *            the stored query ids to set
     * @return DescribeStoredQueriesRequest
     */
    public DescribeStoredQueriesRequest setStoredQueryIds(Collection<String> storedQueryIds) {
        this.storedQueryIds = Sets.newHashSet(storedQueryIds);
        return this;
    }

    /**
     * Check if stored query ids are set
     *
     * @return <code>true</code>, if stored query ids are set
     */
    public boolean isSetStoredQueryIds() {
        return CollectionHelper.isNotEmpty(getStoredQueryIds());
    }

}
