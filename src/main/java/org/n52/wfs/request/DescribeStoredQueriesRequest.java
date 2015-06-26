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
