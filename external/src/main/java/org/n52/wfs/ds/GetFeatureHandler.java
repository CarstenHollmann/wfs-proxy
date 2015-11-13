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

import java.util.Set;

import javax.inject.Inject;
import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.n52.iceland.exception.ows.NoApplicableCodeException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.exception.ows.concrete.InvalidSridException;
import org.n52.iceland.ogc.gml.AbstractFeature;
import org.n52.iceland.ogc.om.OmConstants;
import org.n52.iceland.ogc.sos.Sos2Constants;
import org.n52.iceland.util.http.MediaTypes;
import org.n52.ogc.pilot.PilotConstants;
import org.n52.ogc.pilot.PilotFeature;
import org.n52.ogc.wfs.AbstractFeatureMember;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsFeatureCollection;
import org.n52.ogc.wfs.WfsQuery;
import org.n52.sos.ogc.filter.SpatialFilter;
import org.n52.sos.ogc.om.features.FeatureCollection;
import org.n52.sos.ogc.om.features.SfConstants;
import org.n52.sos.ogc.om.features.samplingFeatures.SamplingFeature;
import org.n52.sos.request.GetFeatureOfInterestRequest;
import org.n52.sos.request.GetObservationRequest;
import org.n52.sos.response.GetFeatureOfInterestResponse;
import org.n52.sos.response.GetObservationResponse;
import org.n52.sos.util.CodingHelper;
import org.n52.sos.util.GeometryHandler;
import org.n52.sos.util.JTSHelper;
import org.n52.sos.util.XmlHelper;
import org.n52.wfs.cache.InMemoryCacheImpl;
import org.n52.wfs.request.GetFeatureRequest;
import org.n52.wfs.response.GetFeatureResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * WFS DAO class for GetFeature operation
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetFeatureHandler extends AbstractConvertingGetFeatureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetFeatureHandler.class);

    @Inject
    private HttpClientHandler httpClientHandler;
    
    @Inject
    private GetFeatureOfInterestQuerier getFeatureOfInterestQuerier;

    public GetFeatureHandler() {
        super(WfsConstants.WFS);
    }

    @Override
    public GetFeatureResponse getFeatures(GetFeatureRequest request) throws OwsExceptionReport {
        GetFeatureResponse response = new GetFeatureResponse();
        response.setService(WfsConstants.WFS);
        response.setVersion(WfsConstants.VERSION);
        WfsFeatureCollection featureCollection =
                new WfsFeatureCollection(new DateTime(), WfsConstants.NUMBER_MATCHED_UNKNOWN);
        response.setFeatureCollection(featureCollection);
        for (WfsQuery wfsQuery : request.getQueries()) {
            SpatialFilter spatialFilter = checkForSpatialFilter(request, wfsQuery);
            for (QName typeName : wfsQuery.getTypeNames()) {
                QName checkedTypeName = checkTypeName(typeName);
                if (checkedTypeName != null) {
                    if (PilotConstants.QN_PILOT_PILOT_FEATURE.equals(checkedTypeName)) {
                        Set<AbstractFeature> features = null;
                        if (getWfsCache().isSetAbstractFeatures()) {
                            features = getWfsCache().getAbstractFeatures();
                        } else {
                            GetFeatureOfInterestRequest sosRequest = convertWfsGetFeatureToSosGetFeatureOfInterestRequest(request);
                            GetFeatureOfInterestResponse getFeatureOfInterestRequestResponse = getFeatureOfInterestQuerier.getGetFeatureOfInterestRequestResponse(sosRequest);
                            features = getFeatures(getFeatureOfInterestRequestResponse);
                        }
                        convertSosGetFeatureOfInterestRequestToPilotFeaturesWfsGetFeature(features, featureCollection, request.getCount(), spatialFilter);
                    } else if (SfConstants.QN_SAMS_20_SPATIAL_SAMPLING_FEATURE.equals(checkedTypeName)) {
                        Set<AbstractFeature> features = null;
                        if (getWfsCache().isSetAbstractFeatures()) {
                            features = getWfsCache().getAbstractFeatures();
                        } else {
                            GetFeatureOfInterestRequest sosRequest = convertWfsGetFeatureToSosGetFeatureOfInterestRequest(request);
                            GetFeatureOfInterestResponse getFeatureOfInterestRequestResponse = getFeatureOfInterestQuerier.getGetFeatureOfInterestRequestResponse(sosRequest);
                            features = getFeatures(getFeatureOfInterestRequestResponse);
                        }
                        convertSosGetFeatureOfInterestRequestToWfsGetFeature(features, featureCollection);
                    } else if (OmConstants.QN_OM_20_OBSERVATION.equals(checkedTypeName)) {
                        GetObservationRequest sosRequest = convertWfsGetFeatureToSosGetObservation(request);
                        convertSosGetObservationToWfsGetFeature((GetObservationResponse) getGetObservationResponse(sosRequest), featureCollection);
                    }
                }
            }
        }
        return response;
    }

    private Set<AbstractFeature> getFeatures(GetFeatureOfInterestResponse sosResponse) {
        Set<AbstractFeature> features = Sets.newHashSet();
        if (sosResponse.getAbstractFeature() != null) {
            features.addAll(checkAndGet(sosResponse.getAbstractFeature()));
        }
        return features;
    }
    
    private Set<AbstractFeature> checkAndGet(AbstractFeature feature) {
        Set<AbstractFeature> features = Sets.newHashSet();
        if (feature != null) {
            if (feature instanceof FeatureCollection) {
                for (AbstractFeature abstractFeature : ((FeatureCollection)feature).getMembers().values()) {
                    features.addAll(checkAndGet(abstractFeature));
                }
            } else if (feature instanceof SamplingFeature) {
                features.add(feature);
            }
        }
        return features;
    }

    private void convertSosGetFeatureOfInterestRequestToPilotFeaturesWfsGetFeature(
            Set<AbstractFeature> features, WfsFeatureCollection featureCollection, int count, SpatialFilter spatialFilter) throws InvalidSridException {
        for (AbstractFeature abstractFeature : features) {
            if (abstractFeature instanceof SamplingFeature) {
                PilotFeature pilotFeature = convertToPilotFeature(checkGeometry(abstractFeature));
                if (pilotFeature != null) {
                    if (spatialFilter != null) {
                        if (GeometryHandler.getInstance().featureIsInFilter(pilotFeature.getGeometry(), Lists.newArrayList(spatialFilter.getGeometry()))) {
                            featureCollection.addMember(new AbstractFeatureMember(pilotFeature));
                        }
                    } else {
                        featureCollection.addMember(new AbstractFeatureMember(pilotFeature));
                    }
                }
            }
        }
    }

    private PilotFeature convertToPilotFeature(AbstractFeature abstractFeature) throws InvalidSridException {
        if (abstractFeature instanceof SamplingFeature) {
            PilotFeature pilotFeature =
                    new PilotFeature(abstractFeature.getIdentifierCodeWithAuthority(), abstractFeature.getGmlId());
            if (abstractFeature.isSetName()) {
                pilotFeature.setName(abstractFeature.getName());
            }
            if (((SamplingFeature) abstractFeature).isSetGeometry()) {
                try {
                    pilotFeature.setGeometry(JTSHelper.switchCoordinateAxisOrder(((SamplingFeature) abstractFeature).getGeometry()));
                } catch (OwsExceptionReport e) {
                    LOGGER.debug("Error while switching coordinate for feature: {}", abstractFeature.getIdentifier());
                }
            }
            return pilotFeature;
        }
        return null;
    }

    private GetObservationResponse getGetObservationResponse(GetObservationRequest sosRequest)
            throws OwsExceptionReport {
        String sosResponse = httpClientHandler.doPost(CodingHelper.encodeObjectToXml(Sos2Constants.NS_SOS_20, sosRequest).xmlText(),
                MediaTypes.APPLICATION_XML);
        if (!Strings.isNullOrEmpty(sosResponse)) {
            LOGGER.debug("SOS response: {}", sosResponse);
            Object object = CodingHelper.decodeXmlElement(XmlHelper.parseXmlString(sosResponse));
            if (object instanceof GetObservationResponse) {
                return (GetObservationResponse) object;
            } else if (object instanceof OwsExceptionReport) {
                throw new NoApplicableCodeException().causedBy((OwsExceptionReport)object).withMessage("error");
            }
            throw new NoApplicableCodeException().withMessage("Error while processing GetFeature!");
        }
        throw new NoApplicableCodeException().withMessage("Error while querying GetCapabilities from SOS! Response is null!");
    }
    
    protected QName checkTypeName(QName typeName) {
        if (checkQNameOfType(typeName, OmConstants.QN_OM_20_OBSERVATION)) {
            return OmConstants.QN_OM_20_OBSERVATION;
        } else if (checkQNameOfType(typeName, SfConstants.QN_SAMS_20_SPATIAL_SAMPLING_FEATURE)) {
            return SfConstants.QN_SAMS_20_SPATIAL_SAMPLING_FEATURE;
        } else if (checkQNameOfType(typeName, PilotConstants.QN_PILOT_PILOT_FEATURE)) {
            return PilotConstants.QN_PILOT_PILOT_FEATURE;
        }
        return null;
    }
    
    private boolean checkQNameOfType(QName toCheck, QName against) {
        if (toCheck != null) {
            if (against.equals(toCheck)) {
                return true;
            } else {
                return against.getLocalPart().equals(toCheck.getLocalPart());
            }
        }
        return false;
    }

    private InMemoryCacheImpl getWfsCache() {
        return (InMemoryCacheImpl) getCacheController().getCache();
    }
}
