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

import org.n52.iceland.exception.ows.NoApplicableCodeException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.gml.AbstractFeature;
import org.n52.iceland.ogc.sos.Sos2Constants;
import org.n52.iceland.util.http.MediaTypes;
import org.n52.sos.ogc.om.features.FeatureCollection;
import org.n52.sos.ogc.om.features.samplingFeatures.SamplingFeature;
import org.n52.sos.request.GetFeatureOfInterestRequest;
import org.n52.sos.response.GetFeatureOfInterestResponse;
import org.n52.sos.util.CodingHelper;
import org.n52.sos.util.XmlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

public class GetFeatureOfInterestQuerier {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetFeatureOfInterestQuerier.class);

    @Inject
    private HttpClientHandler httpClientHandler;

    public GetFeatureOfInterestResponse getGetFeatureOfInterestRequestResponse(GetFeatureOfInterestRequest sosRequest)
            throws OwsExceptionReport {
        String sosResponse =
                httpClientHandler.doPost(CodingHelper.encodeObjectToXml(Sos2Constants.NS_SOS_20, sosRequest).xmlText(),
                        MediaTypes.APPLICATION_XML);
        if (!Strings.isNullOrEmpty(sosResponse)) {
            LOGGER.debug("SOS response: {}", sosResponse);
            Object object = CodingHelper.decodeXmlElement(XmlHelper.parseXmlString(sosResponse));
            if (object instanceof GetFeatureOfInterestResponse) {
                return (GetFeatureOfInterestResponse) object;
            } else if (object instanceof OwsExceptionReport) {
                throw new NoApplicableCodeException().causedBy((OwsExceptionReport) object).withMessage("error");
            }
            throw new NoApplicableCodeException().withMessage("Error while processing GetFeature!");
        }
        throw new NoApplicableCodeException()
                .withMessage("Error while querying GetCapabilities from SOS! Response is null!");
    }

    public Set<AbstractFeature> queryAndGetFeatures(GetFeatureOfInterestRequest sosRequest) throws OwsExceptionReport {
        GetFeatureOfInterestResponse sosResponse = getGetFeatureOfInterestRequestResponse(sosRequest);
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
}
