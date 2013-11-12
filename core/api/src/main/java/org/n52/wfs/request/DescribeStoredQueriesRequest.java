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
package org.n52.wfs.request;

import java.util.Collection;
import java.util.Set;

import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.util.CollectionHelper;

import com.google.common.collect.Sets;

/**
 * WFS DescribeStoredQueries service request
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class DescribeStoredQueriesRequest extends AbstractWfsServiceRequest {

    /* 0..* */
    private Set<String> storedQueryIds = Sets.newHashSet();

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
