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

import org.joda.time.DateTime;
import org.n52.ogc.wfs.OmObservationMember;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsFeatureCollection;
import org.n52.ogc.wfs.WfsMember;
import org.n52.sos.ds.AbstractGetObservationDAO;
import org.n52.sos.ds.OperationDAORepository;
import org.n52.sos.ogc.om.OmObservation;
import org.n52.sos.ogc.ows.CompositeOwsException;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.ogc.sos.Sos2Constants;
import org.n52.sos.ogc.sos.SosConstants;
import org.n52.sos.request.GetObservationRequest;
import org.n52.sos.response.GetObservationResponse;
import org.n52.wfs.ds.AbstractGetFeatureDAO;
import org.n52.wfs.request.GetFeatureRequest;
import org.n52.wfs.response.GetFeatureResponse;

public class WfsGetFeatureOperatorV20 extends
        AbstractV2RequestOperator<AbstractGetFeatureDAO, GetFeatureRequest, GetFeatureResponse> {

    private static final String OPERATION_NAME = WfsConstants.Operations.GetFeature.name();

    private AbstractGetObservationDAO sosGetObservationDAO;

    public WfsGetFeatureOperatorV20() {
        super(OPERATION_NAME, GetFeatureRequest.class);
        sosGetObservationDAO =
                (AbstractGetObservationDAO) OperationDAORepository.getInstance().getOperationDAO(SosConstants.SOS,
                        SosConstants.Operations.GetObservation.name());
    }

    @Override
    public Set<String> getConformanceClasses() {
        return Collections.emptySet();
    }

    @Override
    protected void checkParameters(GetFeatureRequest request) throws OwsExceptionReport {
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
        exceptions.throwIfNotEmpty();
    }

    @Override
    protected GetFeatureResponse receive(GetFeatureRequest request) throws OwsExceptionReport {
        GetObservationRequest sosRequest = convertWfsGetFeatureToSosGetObservation(request);
        return convertSosGetObservationToWfsGetFeature(sosGetObservationDAO.getObservation(sosRequest));
    }

    private GetObservationRequest convertWfsGetFeatureToSosGetObservation(GetFeatureRequest request) {
        GetObservationRequest sosRequest = new GetObservationRequest();
        sosRequest.setService(SosConstants.SOS);
        sosRequest.setVersion(Sos2Constants.SERVICEVERSION);
        if (request.isSetBBox()) {
            sosRequest.setSpatialFilter(request.getBBox());
        }

        // TODO filter,...
        return sosRequest;
    }

    private GetFeatureResponse convertSosGetObservationToWfsGetFeature(GetObservationResponse sosResponse) {
        GetFeatureResponse response = new GetFeatureResponse();
        response.setService(WfsConstants.WFS);
        response.setVersion(WfsConstants.VERSION);
        WfsFeatureCollection featureCollection =
                new WfsFeatureCollection(new DateTime(), WfsConstants.NUMBER_MATCHED_UNKNOWN);
        for (OmObservation omObservation : sosResponse.getObservationCollection()) {
            featureCollection.addMember(new OmObservationMember(omObservation));
        }
        response.setFeatureCollection(featureCollection);
        return response;
    }

}
