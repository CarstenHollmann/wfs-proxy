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
package org.n52.wfs.request.operator;


import org.n52.iceland.exception.ows.CompositeOwsException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.request.operator.AbstractRequestOperator;
import org.n52.wfs.ds.AbstractListStoredQueriesDAO;
import org.n52.wfs.request.ListStoredQueriesRequest;
import org.n52.wfs.response.ListStoredQueriesResponse;

/**
 * WFS ListStoredQueries operation operator class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class WfsListStoredQueriesOperatorV20
        extends AbstractRequestOperator<AbstractListStoredQueriesDAO, ListStoredQueriesRequest, ListStoredQueriesResponse> {

    private static final String OPERATION_NAME = WfsConstants.Operations.ListStoredQueries.name();

    public WfsListStoredQueriesOperatorV20() {
        super(WfsConstants.WFS,
              WfsConstants.VERSION,
              OPERATION_NAME,
              ListStoredQueriesRequest.class);
    }

    @Override
    protected void checkParameters(ListStoredQueriesRequest request) throws OwsExceptionReport {
        CompositeOwsException exceptions = new CompositeOwsException();
        exceptions.wrap(() -> checkServiceParameter(request.getService()));
        exceptions.wrap(() -> checkSingleVersionParameter(request));
        exceptions.throwIfNotEmpty();
    }

    @Override
    protected ListStoredQueriesResponse receive(ListStoredQueriesRequest request) throws OwsExceptionReport {
        return getOperationHandler().listStoredQueries(request);
    }


}
