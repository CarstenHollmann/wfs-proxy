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
import org.n52.ogc.wfs.StoredQueryDescription;
import org.n52.ogc.wfs.WfsConstants;

import com.google.common.collect.Sets;

/**
 * Class represents a WFS DescribeStoredQueries response
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class DescribeStoredQueriesResponse extends AbstractServiceResponse {

    /* 0..* */
    private Set<StoredQueryDescription> storedQueryDescriptions = Sets.newHashSet();

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.DescribeStoredQueries.name();
    }

    /**
     * Get stored queries descriptions
     *
     * @return the stored queries descriptions
     */
    public Set<StoredQueryDescription> getStoredQueryDescriptions() {
        return storedQueryDescriptions;
    }

    /**
     * Add a stored query description
     *
     * @param storedQueryDescription
     *            Stored query description to add
     * @return DescribeStoredQueriesResponse
     */
    public DescribeStoredQueriesResponse addStoredQueryDescriptions(StoredQueryDescription storedQueryDescription) {
        getStoredQueryDescriptions().add(storedQueryDescription);
        return this;
    }

    /**
     * Add a stored query descriptions
     *
     * @param storedQueryDescriptions
     *            Stored query descriptions to add
     * @return DescribeStoredQueriesResponse
     */
    public DescribeStoredQueriesResponse setStoredQueryDescriptions(Set<StoredQueryDescription> storedQueryDescriptions) {
        getStoredQueryDescriptions().addAll(storedQueryDescriptions);
        return this;
    }

    /**
     * Check if stored query description are set
     *
     * @return <code>true</code>, if stored query description are set
     */
    public boolean isSetStoredQueryDescriptions() {
        return CollectionHelper.isNotEmpty(getStoredQueryDescriptions());
    }

}
