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

import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.ds.OperationDAO;
import org.n52.sos.exception.ows.concrete.InvalidServiceParameterException;
import org.n52.sos.exception.ows.concrete.MissingServiceParameterException;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.request.AbstractServiceRequest;
import org.n52.sos.request.operator.AbstractRequestOperator;
import org.n52.sos.response.AbstractServiceResponse;

/**
 * Abstract WFS request operator class
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 4.0.0
 * 
 * @param <D>
 *            The OperationDAO implementation class
 * @param <Q>
 *            the request type
 * @param <A>
 *            the response type
 */
public abstract class AbstractV2RequestOperator<D extends OperationDAO, Q extends AbstractServiceRequest, A extends AbstractServiceResponse>
        extends AbstractRequestOperator<D, Q, A> {
    /**
     * constructor
     * 
     * @param operationName
     *            Operation name
     * @param requestType
     *            Implemented request class
     */
    public AbstractV2RequestOperator(String operationName, Class<Q> requestType) {
        super(WfsConstants.WFS, WfsConstants.VERSION, operationName, requestType);
    }

    @Override
    protected void checkServiceParameter(String service) throws OwsExceptionReport {
        if (service == null || service.equalsIgnoreCase("NOT_SET")) {
            throw new MissingServiceParameterException();
        } else if (!service.equals(WfsConstants.WFS)) {
            throw new InvalidServiceParameterException(service);
        }
    }
}
