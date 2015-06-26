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
package org.n52.wfs.ds;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.ows.OwsOperation;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.ds.AbstractOperationHandler;
import org.n52.wfs.request.ListStoredQueriesRequest;
import org.n52.wfs.response.ListStoredQueriesResponse;

/**
 * Abstract WFS ListStoredQueries DAO class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public abstract class AbstractListStoredQueriesDAO extends AbstractOperationHandler {

    /**
     * constructor
     *
     * @param service
     *            Service name
     */
    public AbstractListStoredQueriesDAO(String service) {
        super(service, WfsConstants.Operations.ListStoredQueries.name());
    }

    @Override
    protected void setOperationsMetadata(OwsOperation opsMeta, String service, String version)
            throws OwsExceptionReport {
        // TODO Auto-generated method stub
    }

    /**
     * Query stored query
     *
     * @param request
     *            ListStoredQueries request
     * @return ListStoredQueries response
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    public abstract ListStoredQueriesResponse listStoredQueries(ListStoredQueriesRequest request)
            throws OwsExceptionReport;

}
