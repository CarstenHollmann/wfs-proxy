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

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.n52.iceland.exception.ows.NoApplicableCodeException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.lifecycle.Constructable;
import org.n52.iceland.lifecycle.Destroyable;
import org.n52.iceland.ogc.gml.GmlConstants;
import org.n52.iceland.ogc.gml.time.TimePeriod;
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
import org.n52.ogc.wfs.WfsElement;
import org.n52.ogc.wfs.WfsValueList;
import org.n52.sos.ogc.sos.SosEnvelope;
import org.n52.sos.util.CodingHelper;
import org.n52.sos.util.XmlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vividsolutions.jts.geom.Envelope;

/**
 * WFS 2.0 GetCapabilities DAO class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetCapabilitiesHandler extends AbstractWfsGetCapabilitiesHandler implements Constructable, Destroyable{
    
    private static final Logger log = LoggerFactory.getLogger(GetCapabilitiesHandler.class);

    @Inject
    private HttpClientHandler httpClientHandler;

    private OwsCapabilities owsCapabilities;

    private int capabilitiesValidMinutes = 60;
    
    private final Timer timer = new Timer("52n-wfs-capabilities-updater", true);
    private TimerTask current = null;

    public GetCapabilitiesHandler() {
        super();
    }

    protected SosEnvelope wgs84BoundingBoxes(String featureType) throws OwsExceptionReport {
        Optional<OwsOperation> operation = getOperationGetObservation(getCapabilities());
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
        Optional<OwsOperation> operation = getOperationGetObservation(getCapabilities());
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

    private OwsCapabilities getCapabilities() throws OwsExceptionReport {
        if (owsCapabilities == null) {
            updateCapabilities();
        }
        return owsCapabilities;
    }

    private void updateCapabilities() throws OwsExceptionReport {
        Object object =
                CodingHelper.decodeXmlElement(XmlHelper.parseXmlString(httpClientHandler.doGet(getParameter())));
        if (object instanceof GetCapabilitiesResponse) {
            setOwsCapabilities(((GetCapabilitiesResponse) object).getCapabilities());
        } else if (object instanceof OwsCapabilities) {
            setOwsCapabilities((OwsCapabilities) object);
        } else if (object instanceof OwsExceptionReport) {
            throw new NoApplicableCodeException().causedBy((OwsExceptionReport)object).withMessage("error");
        }
        throw new NoApplicableCodeException().withMessage("error");
    }

    private void setOwsCapabilities(OwsCapabilities owsCapabilities) {
        this.owsCapabilities = owsCapabilities;
    }

    private Map<String, List<String>> getParameter() {
        Map<String, List<String>> parameter = Maps.newHashMap();
        parameter.put(OWSConstants.GetCapabilitiesParams.service.name(), Lists.newArrayList(SosConstants.SOS));
        parameter.put(OWSConstants.GetCapabilitiesParams.request.name(),
                Lists.newArrayList(OWSConstants.Operations.GetCapabilities.name()));
        parameter.put(OWSConstants.GetCapabilitiesParams.Sections.name(),
                Lists.newArrayList(SosConstants.CapabilitiesSections.OperationsMetadata.name()));
        return parameter;
    }
    
    private void cancelCurrent() {
        if (this.current != null) {
            this.current.cancel();
            log.debug("Current {} canceled", UpdateCapabilitiesTimerTask.class.getSimpleName());
        }
    }

    private void cancelTimer() {
        if (this.timer != null) {
            this.timer.cancel();
            log.debug("Capabilities Update timer canceled.");
        }
    }

    @Override
    public void init() {
        current = new UpdateCapabilitiesTimerTask();
        log.debug("Next CapabilitiesUpdate in {}m", capabilitiesValidMinutes);
        timer.schedule(current, 0, (capabilitiesValidMinutes*60000));
        
    }

    @Override
    public void destroy() {
        cancelCurrent();
        cancelTimer();
    }
    
    private class UpdateCapabilitiesTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                updateCapabilities();
                log.info("Timertask: capabilities update successful!");
            } catch (OwsExceptionReport e) {
                log.error("Fatal error: Timertask couldn't update capabilities! Switch log level to DEBUG to get more details.");
                log.debug("Exception thrown", e);
            }
        }
    }

}
