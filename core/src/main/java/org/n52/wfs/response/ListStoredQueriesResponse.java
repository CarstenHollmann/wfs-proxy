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
package org.n52.wfs.response;

import java.util.Set;

import org.n52.iceland.response.AbstractServiceResponse;
import org.n52.iceland.util.CollectionHelper;
import org.n52.ogc.wfs.StoredQueryListItem;
import org.n52.ogc.wfs.WfsConstants;

import com.google.common.collect.Sets;

/**
 * Class represents a WFS ListStoredQueries response
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class ListStoredQueriesResponse extends AbstractServiceResponse {

    /* 0..* */
    private Set<StoredQueryListItem> storedQueries = Sets.newHashSet();

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.ListStoredQueries.name();
    }

    /**
     * Get stored queries
     *
     * @return Stored queries
     */
    public Set<StoredQueryListItem> getStoredQueries() {
        return storedQueries;
    }

    /**
     * Add a stored query
     *
     * @param storedQuery
     *            Stored query to add
     * @return ListStoredQueriesResponse
     */
    public ListStoredQueriesResponse addStoredQueries(StoredQueryListItem storedQuery) {
        getStoredQueries().add(storedQuery);
        return this;
    }

    /**
     * Add stored queries
     *
     * @param storedQueries
     *            Stored Queries to add
     * @return ListStoredQueriesResponse
     */
    public ListStoredQueriesResponse setStoredQueries(Set<StoredQueryListItem> storedQueries) {
        getStoredQueries().addAll(storedQueries);
        return this;
    }

    /**
     * Check if stored queries are set
     *
     * @return <code>true</code>, if stored queries are set
     */
    public boolean isSetStoredQueries() {
        return CollectionHelper.isNotEmpty(getStoredQueries());
    }
}
