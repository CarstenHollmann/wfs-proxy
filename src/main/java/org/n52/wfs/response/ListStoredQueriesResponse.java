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
