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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.ows.OwsOperation;
import org.n52.iceland.service.operator.ServiceOperatorRepository;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.ds.AbstractOperationHandler;
import org.n52.wfs.request.GetCapabilitiesRequest;
import org.n52.wfs.response.GetCapabilitiesResponse;

/**
 * Abstract WFS GetCapabilities DAO class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public abstract class AbstractGetCapabilitiesHandler extends AbstractOperationHandler  {

    protected static final String FALSE = String.valueOf(false);
    protected static final String TRUE = String.valueOf(true);

    private ServiceOperatorRepository serviceOperatorRepository;

    public AbstractGetCapabilitiesHandler(String service) {
        super(service, WfsConstants.Operations.GetCapabilities.name());
    }

    @Inject
    public void setServiceOperatorRepository(ServiceOperatorRepository repo) {
        this.serviceOperatorRepository = repo;
    }

    @Override
    protected void setOperationsMetadata(OwsOperation opsMeta, String service, String version)
            throws OwsExceptionReport {
        // set param Sections
        List<String> sectionsValues = new LinkedList<>();
        Set<String> supportedVersions = this.serviceOperatorRepository.getSupportedVersions(service);
        /* common sections */
        sectionsValues.add(WfsConstants.CapabilitiesSections.ServiceIdentification.name());
        sectionsValues.add(WfsConstants.CapabilitiesSections.ServiceProvider.name());
        sectionsValues.add(WfsConstants.CapabilitiesSections.OperationsMetadata.name());
        sectionsValues.add(WfsConstants.CapabilitiesSections.Filter_Capabilities.name());
        sectionsValues.add(WfsConstants.CapabilitiesSections.FeatureTypeList.name());
        sectionsValues.add(WfsConstants.CapabilitiesSections.All.name());
        opsMeta.addPossibleValuesParameter(WfsConstants.GetCapabilitiesParams.Sections, sectionsValues);
        opsMeta.addPossibleValuesParameter(WfsConstants.GetCapabilitiesParams.AcceptFormats, WfsConstants.ACCEPT_FORMATS);
        opsMeta.addPossibleValuesParameter(WfsConstants.GetCapabilitiesParams.AcceptVersions, supportedVersions);
        opsMeta.addAnyParameterValue(WfsConstants.GetCapabilitiesParams.updateSequence);
    }

    protected abstract Set<String> getExtensionSections(final String service, final String version)
            throws OwsExceptionReport;

    /**
     * Get the SOS capabilities
     *
     * @param request
     *            GetCapabilities request
     * @return internal SOS capabilities representation
     *
     * @throws OwsExceptionReport
     *             If an error occurs.
     */
    public abstract GetCapabilitiesResponse getCapabilities(GetCapabilitiesRequest request) throws OwsExceptionReport;
}
