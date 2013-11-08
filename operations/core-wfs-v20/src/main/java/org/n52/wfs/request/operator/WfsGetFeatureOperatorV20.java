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

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.n52.ogc.wfs.OmObservationMember;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsFeatureCollection;
import org.n52.ogc.wfs.WfsQuery;
import org.n52.sos.exception.ows.InvalidParameterValueException;
import org.n52.sos.exception.ows.OptionNotSupportedException;
import org.n52.sos.ogc.filter.AbstractSelectionClause;
import org.n52.sos.ogc.filter.BinaryLogicFilter;
import org.n52.sos.ogc.filter.ComparisonFilter;
import org.n52.sos.ogc.filter.Filter;
import org.n52.sos.ogc.filter.TemporalFilter;
import org.n52.sos.ogc.filter.FilterConstants.BinaryLogicOperator;
import org.n52.sos.ogc.om.OmConstants;
import org.n52.sos.ogc.om.OmObservation;
import org.n52.sos.ogc.ows.CompositeOwsException;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.ogc.sos.Sos2Constants;
import org.n52.sos.ogc.sos.SosConstants;
import org.n52.sos.request.GetObservationRequest;
import org.n52.sos.request.operator.RequestOperator;
import org.n52.sos.request.operator.RequestOperatorRepository;
import org.n52.sos.response.GetObservationResponse;
import org.n52.sos.service.operator.ServiceOperatorKey;
import org.n52.wfs.ds.AbstractGetFeatureDAO;
import org.n52.wfs.exception.wfs.concrete.MissingTypnameParameterException;
import org.n52.wfs.request.GetFeatureRequest;
import org.n52.wfs.response.GetFeatureResponse;

public class WfsGetFeatureOperatorV20 extends
        AbstractV2RequestOperator<AbstractGetFeatureDAO, GetFeatureRequest, GetFeatureResponse> {

    private static final String OPERATION_NAME = WfsConstants.Operations.GetFeature.name();

    private RequestOperator requestOperator;

    public WfsGetFeatureOperatorV20() {
        super(OPERATION_NAME, GetFeatureRequest.class);
        requestOperator =
                RequestOperatorRepository.getInstance().getRequestOperator(
                        new ServiceOperatorKey(SosConstants.SOS, Sos2Constants.SERVICEVERSION),
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
        try {
            if (!request.isSetResourceIds()) {
                if (request.isSetQueries()) {
                    for (WfsQuery query : request.getQueries()) {
                        checkTypenames(query.getTypeNames(), WfsConstants.AdHocQueryParams.TypeNames.name());
                    }
                }
            }
        } catch (OwsExceptionReport owse) {
            exceptions.add(owse);
        }

        exceptions.throwIfNotEmpty();
    }

    @Override
    protected GetFeatureResponse receive(GetFeatureRequest request) throws OwsExceptionReport {
        GetObservationRequest sosRequest = convertWfsGetFeatureToSosGetObservation(request);
        return convertSosGetObservationToWfsGetFeature((GetObservationResponse) requestOperator
                .receiveRequest(sosRequest));
    }

    private void checkTypename(final QName typeName, String parameterName) throws OwsExceptionReport {
        if (typeName == null) {
            throw new MissingTypnameParameterException();
        } else if (!OmConstants.QN_OM_20_OBSERVATION.equals(typeName)) {
            throw new InvalidParameterValueException(parameterName, typeName.toString());
        }
    }

    protected void checkTypenames(final Collection<QName> typeNames, final String parameterName)
            throws OwsExceptionReport {
        if (typeNames != null) {
            final CompositeOwsException exceptions = new CompositeOwsException();
            for (final QName typeName : typeNames) {
                try {
                    checkTypename(typeName, parameterName);
                } catch (final OwsExceptionReport owse) {
                    exceptions.add(owse);
                }
            }
            exceptions.throwIfNotEmpty();
        }
    }

    private GetObservationRequest convertWfsGetFeatureToSosGetObservation(GetFeatureRequest request) throws OwsExceptionReport {
        GetObservationRequest sosRequest = new GetObservationRequest();
        sosRequest.setService(SosConstants.SOS);
        sosRequest.setVersion(Sos2Constants.SERVICEVERSION);
        if (request.isSetBBox()) {
            sosRequest.setSpatialFilter(request.getBBox());
        }
        convertFilterForGetObservation(sosRequest, request);
        return sosRequest;
    }

    private void convertFilterForGetObservation(GetObservationRequest sosRequest, GetFeatureRequest request) throws OwsExceptionReport {
        if (request.isSetQueries()) {
            for (WfsQuery query : request.getQueries()) {
                AbstractSelectionClause filter = query.getSelectionClause();
                if (filter instanceof BinaryLogicFilter) {
                    BinaryLogicFilter binLogFilter = (BinaryLogicFilter) filter;
                    if (BinaryLogicOperator.Or.equals(binLogFilter.getOperator())) {
                        // TODO exception or not supported as initial filter
                    }
                    convertBinaryLogicFilter(binLogFilter, sosRequest, false);
                } else if (filter instanceof ComparisonFilter) {
                    convertComparisonFilter((ComparisonFilter) filter, sosRequest, null);
                } else if (filter instanceof TemporalFilter) {
                    addTemporalFilter((TemporalFilter) filter, sosRequest);
                } else {
                    // TODO throw not supported exeption
                }
            }
        }
    }

    private void convertBinaryLogicFilter(BinaryLogicFilter filter, GetObservationRequest sosRequest, boolean checkChild) throws OwsExceptionReport {
        ComparisonFilterEquality equalityCheck = null;
        for (Filter<?> filterPredicate : filter.getFilterPredicates()) {
            if (filterPredicate instanceof BinaryLogicFilter) {
                if (checkChild) {
                    // TODO exception BinaryLogicFilter not supported as 3. level subfilter
                }
                BinaryLogicFilter binLogFilter = (BinaryLogicFilter) filter;
                if (BinaryLogicOperator.And.equals(binLogFilter.getOperator())) {
                    // TODO exception and not supported as subfilter
                }
                convertBinaryLogicFilter(binLogFilter, sosRequest, true);
            } else if (filterPredicate instanceof ComparisonFilter) {
                    convertComparisonFilter((ComparisonFilter) filterPredicate, sosRequest, equalityCheck);
            } else if (filterPredicate instanceof TemporalFilter) {
                TemporalFilter temporalFilter = (TemporalFilter) filterPredicate;
                if (!isPhenomenonTimeFilter(temporalFilter)) {
                   // TODO throw exception
                } 
                addTemporalFilter((TemporalFilter) filterPredicate, sosRequest);
            } else {
                // TODO throw not supported exeption
            }
        }
    }

    private void convertComparisonFilter(ComparisonFilter filter, GetObservationRequest request,
            ComparisonFilterEquality equalityCheck) throws OptionNotSupportedException {
        ComparisonFilterEquality currentComparisonFilterEquality = ComparisonFilterEquality.valueOf(filter.getValueReference());
        if (equalityCheck == null) {
            equalityCheck = currentComparisonFilterEquality;
        }
        if (currentComparisonFilterEquality.equals(equalityCheck)) {
            switch (currentComparisonFilterEquality) {
            case Procedure:
                addProcedureIdentifierFromFilter(filter, request);
                break;
            case ObervedProperty:
                addObservedPropertyIdentifierFromFilter(filter, request);
                break;
            case FeatureOfInterest:
                addFeatureOfInterestIdentifierFromFilter(filter, request);
                break;
            default:
             // TODO exception
                throw new OptionNotSupportedException();
            }
        } else {
            // TODO throw exception not equal
        }
    }

    private void addProcedureIdentifierFromFilter(ComparisonFilter filter, GetObservationRequest request) {
        request.getProcedures().add(filter.getValue());
    }

    private void addObservedPropertyIdentifierFromFilter(ComparisonFilter filter, GetObservationRequest request) {
        request.getObservedProperties().add(filter.getValue());
    }

    private void addFeatureOfInterestIdentifierFromFilter(ComparisonFilter filter, GetObservationRequest request) {
        request.getFeatureIdentifiers().add(filter.getValue());
    }

    private boolean isPhenomenonTimeFilter(TemporalFilter filter) {
        return checkValueReference(filter, ComparisonFilterEquality.PhenomenonTime);
    }

    private void addTemporalFilter(TemporalFilter filter, GetObservationRequest request) {
        request.getTemporalFilters().add(filter);
    }

    private boolean checkValueReference(Filter<?> filter, ComparisonFilterEquality phenomenontime) {
        return phenomenontime.isValueReference(filter.getValueReference());
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
    
    enum ComparisonFilterEquality {
        Procedure("om:procedure"),
        ObervedProperty("om:observedProperty"),
        FeatureOfInterest("om:featureOfInterest"),
        PhenomenonTime("om:phenomenonTime");
        
        private String value;
        
        private ComparisonFilterEquality(String value) {
            this.value = value;
        }

        public boolean isValueReference(String valueReference) {
            return value.equals(valueReference);
        }
        
    }

}
