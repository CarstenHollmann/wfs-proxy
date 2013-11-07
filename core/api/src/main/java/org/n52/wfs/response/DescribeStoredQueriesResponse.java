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
package org.n52.wfs.response;

import java.util.Set;

import org.n52.ogc.wfs.StoredQueryDescription;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.response.AbstractServiceResponse;
import org.n52.sos.util.CollectionHelper;

import com.google.common.collect.Sets;

public class DescribeStoredQueriesResponse extends AbstractServiceResponse {
    
    /* 0..* */
    private Set<StoredQueryDescription> storedQueryDescriptions = Sets.newHashSet();

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.DescribeStoredQueries.name();
    }

    public Set<StoredQueryDescription> getStoredQueryDescriptions() {
        return storedQueryDescriptions;
    }

    public DescribeStoredQueriesResponse addStoredQueryDescriptions(StoredQueryDescription storedQueryDescription) {
        getStoredQueryDescriptions().add(storedQueryDescription);
        return this;
    }
    
    public DescribeStoredQueriesResponse setStoredQueryDescriptions(Set<StoredQueryDescription> storedQueryDescriptions) {
        getStoredQueryDescriptions().addAll(storedQueryDescriptions);
        return this;
    }
    
    public boolean isSetStoredQueryDescriptions() {
        return CollectionHelper.isNotEmpty(getStoredQueryDescriptions());
    }

}
