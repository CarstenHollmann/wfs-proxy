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
package org.n52.wfs.request.operator;

import java.util.Collections;
import java.util.Set;

import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.exception.ows.InvalidParameterValueException;
import org.n52.sos.ogc.ows.CompositeOwsException;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.util.CollectionHelper;
import org.n52.wfs.ds.AbstractDescribeStoredQueriesDAO;
import org.n52.wfs.request.DescribeStoredQueriesRequest;
import org.n52.wfs.response.DescribeStoredQueriesResponse;

/**
 * WFS DescribeStoredQueries operation operator class
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class WfsDescribeStoredQueriesOperatorV20
        extends
        AbstractV2RequestOperator<AbstractDescribeStoredQueriesDAO, DescribeStoredQueriesRequest, DescribeStoredQueriesResponse> {

    private static final String OPERATION_NAME = WfsConstants.Operations.DescribeStoredQueries.name();

    /**
     * constructor
     */
    public WfsDescribeStoredQueriesOperatorV20() {
        super(OPERATION_NAME, DescribeStoredQueriesRequest.class);
    }

    @Override
    public Set<String> getConformanceClasses() {
        return Collections.emptySet();
    }

    @Override
    protected void checkParameters(DescribeStoredQueriesRequest request) throws OwsExceptionReport {
        final CompositeOwsException exceptions = new CompositeOwsException();
        try {
            checkServiceParameter(request.getService());
        } catch (OwsExceptionReport owse) {
            exceptions.add(owse);
        }
        try {
            checkSingleVersionParameter(request);
        } catch (OwsExceptionReport owse) {
            exceptions.add(owse);
        }
        try {
            checkStoredQueryIds(request);
        } catch (OwsExceptionReport owse) {
            exceptions.add(owse);
        }

        exceptions.throwIfNotEmpty();
    }

    @Override
    protected DescribeStoredQueriesResponse receive(DescribeStoredQueriesRequest request) throws OwsExceptionReport {
        return getDao().describeStoredQueries(request);
    }

    /**
     * Check if stored query ids are requested
     * 
     * @param request
     *            Request to check
     * @throws OwsExceptionReport
     *             If the requested stored query id is not provided
     */
    private void checkStoredQueryIds(DescribeStoredQueriesRequest request) throws OwsExceptionReport {
        if (CollectionHelper.isNotEmpty(request.getStoredQueryIds())) {
            throw new InvalidParameterValueException(WfsConstants.StoredQueryParams.StoredQuery_Id, request
                    .getStoredQueryIds().toString());
        }

    }

}
