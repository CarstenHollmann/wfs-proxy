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


import org.n52.iceland.exception.ows.OperationNotSupportedException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.request.operator.AbstractRequestOperator;
import org.n52.wfs.ds.AbstractGetPropertyValueHandler;
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
public class WfsGetPropertyValueOperatorV20
        extends AbstractRequestOperator<AbstractGetPropertyValueHandler, GetPropertyValueRequest, GetPropertyValueResponse> {

    private static final String OPERATION_NAME = WfsConstants.Operations.GetPropertyValue.name();

    public WfsGetPropertyValueOperatorV20() {
        super(WfsConstants.WFS,
              WfsConstants.VERSION,
              OPERATION_NAME,
              GetPropertyValueRequest.class);
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
       return getOperationHandler().getPropertyValue(request);
    }

}
