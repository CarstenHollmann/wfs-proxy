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
package org.n52.wfs.request.operator;

import org.n52.iceland.exception.ows.CompositeOwsException;
import org.n52.iceland.exception.ows.InvalidParameterValueException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.util.CollectionHelper;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.request.operator.AbstractRequestOperator;
import org.n52.wfs.ds.AbstractDescribeStoredQueriesHandler;
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
        extends AbstractRequestOperator<AbstractDescribeStoredQueriesHandler, DescribeStoredQueriesRequest, DescribeStoredQueriesResponse> {
    private static final String OPERATION_NAME = WfsConstants.Operations.DescribeStoredQueries.name();

    /**
     * constructor
     */
    public WfsDescribeStoredQueriesOperatorV20() {
        super(WfsConstants.WFS,
              WfsConstants.VERSION,
              OPERATION_NAME,
              DescribeStoredQueriesRequest.class);
    }

    @Override
    protected void checkParameters(DescribeStoredQueriesRequest request) throws OwsExceptionReport {
        CompositeOwsException exceptions = new CompositeOwsException();
        exceptions.wrap(() -> checkServiceParameter(request.getService()));
        exceptions.wrap(() -> checkSingleVersionParameter(request));
        exceptions.wrap(() -> checkStoredQueryIds(request));
        exceptions.throwIfNotEmpty();
    }

    @Override
    protected DescribeStoredQueriesResponse receive(DescribeStoredQueriesRequest request) throws OwsExceptionReport {
        return getOperationHandler().describeStoredQueries(request);
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
            throw new InvalidParameterValueException(WfsConstants.StoredQueryParams.StoredQuery_Id,
                    request.getStoredQueryIds().toString());
        }

    }

}
