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
package org.n52.wfs.ds;

import javax.inject.Inject;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.sos.Sos2Constants;
import org.n52.iceland.ogc.sos.SosConstants;
import org.n52.iceland.request.operator.RequestOperator;
import org.n52.iceland.request.operator.RequestOperatorRepository;
import org.n52.iceland.service.operator.ServiceOperatorKey;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.request.GetObservationRequest;
import org.n52.sos.response.GetObservationResponse;
import org.n52.wfs.request.GetFeatureRequest;
import org.n52.wfs.response.GetFeatureResponse;

/**
 * WFS DAO class for GetFeature operation
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetFeatureHandler extends AbstractConvertingGetFeatureHandler {
   
    private RequestOperatorRepository requestOperatorRepository;

    public GetFeatureHandler() {
        super(WfsConstants.WFS);
    }

    @Inject
    public void setRequestOperatorRepository(RequestOperatorRepository requestOperatorRepository) {
        this.requestOperatorRepository = requestOperatorRepository;
    }

    @Override
    public GetFeatureResponse getFeatures(GetFeatureRequest request) throws OwsExceptionReport {
        GetObservationRequest sosRequest = convertWfsGetFeatureToSosGetObservation(request);
        return convertSosGetObservationToWfsGetFeature((GetObservationResponse) getGetObservationRequestOperator()
                .receiveRequest(sosRequest));
    }

    /**
     * Get SOS GetObservation request operator to execute request
     *
     * @return SOS GetObservation request operator
     */
    private RequestOperator getGetObservationRequestOperator() {
        return requestOperatorRepository.getRequestOperator(
                new ServiceOperatorKey(SosConstants.SOS, Sos2Constants.SERVICEVERSION), SosConstants.Operations.GetObservation.name());
    }
}
