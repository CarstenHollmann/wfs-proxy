/*
 * Copyright 2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public abstract class AbstractGetCapabilitiesDAO extends AbstractOperationHandler  {

    protected static final String FALSE = String.valueOf(false);
    protected static final String TRUE = String.valueOf(true);

    private ServiceOperatorRepository serviceOperatorRepository;

    public AbstractGetCapabilitiesDAO(String service) {
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
