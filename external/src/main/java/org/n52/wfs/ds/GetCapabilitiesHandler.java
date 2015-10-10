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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.n52.iceland.cache.ContentCache;
import org.n52.iceland.cache.ContentCacheController;
import org.n52.iceland.exception.ows.NoApplicableCodeException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.lifecycle.Constructable;
import org.n52.iceland.lifecycle.Destroyable;
import org.n52.iceland.ogc.gml.GmlConstants;
import org.n52.iceland.ogc.gml.time.TimePeriod;
import org.n52.iceland.ogc.om.OmConstants;
import org.n52.iceland.ogc.ows.OWSConstants;
import org.n52.iceland.ogc.ows.OwsCapabilities;
import org.n52.iceland.ogc.ows.OwsMetadata;
import org.n52.iceland.ogc.ows.OwsOperation;
import org.n52.iceland.ogc.ows.OwsOperationPredicates;
import org.n52.iceland.ogc.ows.OwsParameterValue;
import org.n52.iceland.ogc.ows.OwsParameterValueRange;
import org.n52.iceland.ogc.sos.Sos2Constants;
import org.n52.iceland.ogc.sos.SosConstants;
import org.n52.iceland.response.GetCapabilitiesResponse;
import org.n52.iceland.util.Constants;
import org.n52.iceland.util.DateTimeHelper;
import org.n52.iceland.util.http.MediaType;
import org.n52.iceland.util.http.MediaTypes;
import org.n52.ogc.wfs.WfsElement;
import org.n52.ogc.wfs.WfsFeatureType;
import org.n52.ogc.wfs.WfsValueList;
import org.n52.sos.cache.SosContentCache;
import org.n52.sos.ogc.om.features.SfConstants;
import org.n52.sos.ogc.sos.SosEnvelope;
import org.n52.sos.util.CodingHelper;
import org.n52.sos.util.XmlHelper;
import org.n52.wfs.cache.InMemoryCacheImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vividsolutions.jts.geom.Envelope;

/**
 * WFS 2.0 GetCapabilities DAO class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetCapabilitiesHandler extends AbstractWfsGetCapabilitiesHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GetCapabilitiesHandler.class);

    public GetCapabilitiesHandler() {
        super();
    }

    protected SosEnvelope wgs84BoundingBoxes(String featureType) throws OwsExceptionReport {
        Optional<OwsOperation> operation = getOperationGetObservation(getWfsCache().getCapabilities());
        if (operation.isPresent()) {
            SortedMap<String, List<OwsParameterValue>> parameterValues = operation.get().getParameterValues();
            if (parameterValues.containsKey(Sos2Constants.GetObservationParams.spatialFilter.name())) {
                List<OwsParameterValue> list =
                        parameterValues.get(Sos2Constants.GetObservationParams.spatialFilter.name());
                for (OwsParameterValue value : list) {
                    if (value instanceof OwsParameterValueRange) {
                        OwsParameterValueRange valueRange = (OwsParameterValueRange) value;
                        String[] min = valueRange.getMinValue().split(Constants.SPACE_STRING);
                        String[] max = valueRange.getMaxValue().split(Constants.SPACE_STRING);
                        return new SosEnvelope().setEnvelope(new Envelope(Double.parseDouble(min[0]),
                                Double.parseDouble(max[0]), Double.parseDouble(min[1]), Double.parseDouble(max[1])));
                    }
                }
            }
        }
        return null;
    }

    protected WfsElement getPhenomenonTimeElement(String featureType) throws OwsExceptionReport {
        Optional<OwsOperation> operation = getOperationGetObservation(getWfsCache().getCapabilities());
        if (operation.isPresent()) {
            SortedMap<String, List<OwsParameterValue>> parameterValues = operation.get().getParameterValues();
            if (parameterValues.containsKey(Sos2Constants.GetObservationParams.temporalFilter.name())) {
                List<OwsParameterValue> list =
                        parameterValues.get(Sos2Constants.GetObservationParams.temporalFilter.name());
                for (OwsParameterValue value : list) {
                    if (value instanceof OwsParameterValueRange) {
                        OwsParameterValueRange valueRange = (OwsParameterValueRange) value;
                        DateTime start = DateTimeHelper.makeDateTime(valueRange.getMinValue());
                        DateTime end = DateTimeHelper.makeDateTime(valueRange.getMaxValue());
                        OwsMetadata metadata = new OwsMetadata();
                        metadata.setTitle("Time for which observations are available");
                        return new WfsElement("TemporalExtend", GmlConstants.QN_TIME_PERIOD_32, metadata,
                                new WfsValueList(new TimePeriod(start, end)));
                    }
                }
            }
        }
        TimePeriod timePeriod = new TimePeriod(getCache().getMinPhenomenonTime(), getCache().getMaxPhenomenonTime());
        if (!timePeriod.isEmpty()) {
            OwsMetadata metadata = new OwsMetadata();
            metadata.setTitle("Time for which observations are available");
            return new WfsElement("TemporalExtend", GmlConstants.QN_TIME_PERIOD_32, metadata,
                    new WfsValueList(timePeriod));
        }
        return null;
    }

    private Optional<OwsOperation> getOperationGetObservation(OwsCapabilities capabilities) {
        return capabilities.getOperationsMetadata()
                .findOperation(OwsOperationPredicates.name(SosConstants.Operations.GetObservation.name()));
    }

    private InMemoryCacheImpl getWfsCache() {
        return (InMemoryCacheImpl) getCacheController().getCache();
    }
    
    @Override
    protected Collection<WfsFeatureType> getFeatureTypeList() throws OwsExceptionReport {
        Collection<WfsFeatureType> featureTypeList = super.getFeatureTypeList();
        String featureType = "observatons";
        WfsFeatureType wfsFeatureType = new WfsFeatureType(SfConstants.QN_SAMS_20_SPATIAL_SAMPLING_FEATURE, getWfsCapabilitiesCrs(featureType));
        wfsFeatureType.setTitles(Sets.newHashSet("Features for IMIS-IoT"));
        wfsFeatureType.setAbstracts(Sets.newHashSet(""));
        wfsFeatureType.setKeywords(Sets.newHashSet("features"));
//        wfsFeatureType.setOutputFormats(Sets.newHashSet(MediaTypes.APPLICATION_GML_32.toString(), new MediaType("application", "samplingSpatial+xml", "version", "2.0").toString()));
        wfsFeatureType.setOutputFormats(Sets.newHashSet(MediaTypes.APPLICATION_GML_32.toString()));
        wfsFeatureType.addWgs84BoundingBoxes(wgs84BoundingBoxes(featureType));
        featureTypeList.add(wfsFeatureType);
        return featureTypeList;
    }
}
