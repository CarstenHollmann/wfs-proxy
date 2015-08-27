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

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.gml.GmlConstants;
import org.n52.iceland.ogc.gml.time.TimePeriod;
import org.n52.iceland.ogc.ows.OwsMetadata;
import org.n52.ogc.wfs.WfsElement;
import org.n52.ogc.wfs.WfsValueList;
import org.n52.sos.ogc.sos.SosEnvelope;

public class GetCapabilitiesHandler extends AbstractWfsGetCapabilitiesHandler {

    public GetCapabilitiesHandler() {
        super();
    }

    protected SosEnvelope wgs84BoundingBoxes(String featureType) throws OwsExceptionReport {
        return getCache().getGlobalEnvelope();
    }

    protected WfsElement getPhenomenonTimeElement(String featureType) throws OwsExceptionReport {
        TimePeriod timePeriod = new TimePeriod(getCache().getMinPhenomenonTime(), getCache().getMaxPhenomenonTime());
        if (!timePeriod.isEmpty()) {
            OwsMetadata metadata = new OwsMetadata();
            metadata.setTitle("Time for which observations are available");
            return new WfsElement("TemporalExtend", GmlConstants.QN_TIME_PERIOD_32, metadata, new WfsValueList(timePeriod));
        }
        return null;
    }
    
}
