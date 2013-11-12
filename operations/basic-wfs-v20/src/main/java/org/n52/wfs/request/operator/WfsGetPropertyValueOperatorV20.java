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
import org.n52.sos.exception.ows.OperationNotSupportedException;
import org.n52.sos.ogc.ows.CompositeOwsException;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.wfs.ds.AbstractGetPropertyValueDAO;
import org.n52.wfs.request.GetPropertyValueRequest;
import org.n52.wfs.response.GetPropertyValueResponse;

/**
 * WFS GetPropertyValue operation operator class
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 *
 */
public class WfsGetPropertyValueOperatorV20 extends
AbstractV2RequestOperator<AbstractGetPropertyValueDAO, GetPropertyValueRequest, GetPropertyValueResponse> {
    
    private static final String OPERATION_NAME = WfsConstants.Operations.GetPropertyValue.name();

    public WfsGetPropertyValueOperatorV20() {
        super(OPERATION_NAME, GetPropertyValueRequest.class);
    }

    @Override
    public Set<String> getConformanceClasses() {
        return Collections.emptySet();
    }

    @Override
    protected void checkParameters(GetPropertyValueRequest request) throws OwsExceptionReport {
        throw new OperationNotSupportedException(OPERATION_NAME);
//        final CompositeOwsException exceptions = new CompositeOwsException();
//        try {
//            checkServiceParameter(request.getService());
//        } catch (OwsExceptionReport owse) {
//            exceptions.add(owse);
//        }
//        try {
//            checkSingleVersionParameter(request);
//        } catch (OwsExceptionReport owse) {
//            exceptions.add(owse);
//        }
//
//        exceptions.throwIfNotEmpty();
    }

    @Override
    protected GetPropertyValueResponse receive(GetPropertyValueRequest request) throws OwsExceptionReport {
       return getDao().getPropertyValue(request);
    }

}
