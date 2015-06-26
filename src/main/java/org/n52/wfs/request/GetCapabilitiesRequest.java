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
package org.n52.wfs.request;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.request.AbstractServiceRequest;
import org.n52.iceland.service.operator.ServiceOperatorKey;
import org.n52.iceland.service.operator.ServiceOperatorRepository;
import org.n52.iceland.util.CollectionHelper;
import org.n52.iceland.util.StringHelper;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.wfs.response.GetCapabilitiesResponse;

/**
 * WFS GetCapabilities service request
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetCapabilitiesRequest extends AbstractServiceRequest<GetCapabilitiesResponse> {

    private String updateSequence;
    private final List<String> acceptVersions = new LinkedList<>();
    private final List<String> sections = new LinkedList<>();
    private final List<String> acceptFormats = new LinkedList<>();
    private List<ServiceOperatorKey> serviceOperatorKeyTypes;
    private GetCapabilitiesResponse response;

    public GetCapabilitiesRequest() {
        setService(WfsConstants.WFS);
    }

    @Override
    public GetCapabilitiesResponse getResponse()
            throws OwsExceptionReport {
        return this.response;
    }

    public void setResponse(GetCapabilitiesResponse response) {
        this.response = response;
    }

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.GetCapabilities.name();
    }

    @Override
    public List<ServiceOperatorKey> getServiceOperatorKeys() {
        if (serviceOperatorKeyTypes == null) {
            if (isSetAcceptVersions()) {
                serviceOperatorKeyTypes = new ArrayList<>(acceptVersions.size());
                for (String acceptVersion : acceptVersions) {
                    serviceOperatorKeyTypes.add(new ServiceOperatorKey(getService(), acceptVersion));
                }
            } else {
                Set<String> supportedVersions =
                        ServiceOperatorRepository.getInstance().getSupportedVersions(getService());
                if (CollectionHelper.isNotEmpty(supportedVersions)) {
                    setVersion(Collections.max(supportedVersions));
                }
                serviceOperatorKeyTypes =
                        Collections.singletonList(new ServiceOperatorKey(getService(), getVersion()));
            }
        }
        return Collections.unmodifiableList(serviceOperatorKeyTypes);
    }

    /**
     * Get accept Formats
     *
     * @return accept Formats
     */
    public List<String> getAcceptFormats() {
        return acceptFormats == null ? null : Collections.unmodifiableList(acceptFormats);
    }

    /**
     * Set accept Formats
     *
     * @param acceptFormats
     *            accept Formats
     */
    public void setAcceptFormats(List<String> acceptFormats) {
        this.acceptFormats.clear();
        if (acceptFormats != null) {
            this.acceptFormats.addAll(acceptFormats);
        }
    }

    /**
     * Get accept versions
     *
     * @return accept versions
     */
    public List<String> getAcceptVersions() {
        return acceptVersions == null ? null : Collections.unmodifiableList(acceptVersions);
    }

    /**
     * Add an accept version
     *
     * @param acceptVersion
     *            the accept version to add
     */
    public void addAcceptVersion(String acceptVersion) {
        if (acceptVersion != null) {
            acceptVersions.add(acceptVersion);
        }
    }

    /**
     * Set accept version
     *
     * @param acceptVersions
     *            the accept versions to add
     */
    public void setAcceptVersions(List<String> acceptVersions) {
        this.acceptVersions.clear();
        if (acceptVersions != null) {
            this.acceptVersions.addAll(acceptVersions);
        }
    }

    /**
     * Get sections
     *
     * @return sections
     */
    public List<String> getSections() {
        return sections == null ? null : Collections.unmodifiableList(sections);
    }

    /**
     * Set sections
     *
     * @param sections
     *            sections
     */
    public void setSections(List<String> sections) {
        this.sections.clear();
        if (sections != null) {
            this.sections.addAll(sections);
        }
    }

    /**
     * Get update sequence
     *
     * @return update sequence
     */
    public String getUpdateSequence() {
        return updateSequence;
    }

    /**
     * Set update sequence
     *
     * @param updateSequence
     *            update sequence
     */
    public void setUpdateSequence(String updateSequence) {
        this.updateSequence = updateSequence;
    }

    /**
     * Check if accept formats are set
     *
     * @return <code>true</code>, if accept formats are set
     */
    public boolean isSetAcceptFormats() {
        return CollectionHelper.isNotEmpty(getAcceptFormats());
    }

    /**
     * Check if accept version are set
     *
     * @return <code>true</code>, if accept versions are set
     */
    public boolean isSetAcceptVersions() {
        return CollectionHelper.isNotEmpty(getAcceptVersions());
    }

    /**
     * Check if sections are set
     *
     * @return <code>true</code>, if accept sections are set
     */
    public boolean isSetSections() {
        return CollectionHelper.isNotEmpty(getSections());
    }

    /**
     * Check if update sequence is set
     *
     * @return <code>true</code>, if update sequence is set
     */
    public boolean isSetUpdateSequence() {
        return StringHelper.isNotEmpty(getUpdateSequence());
    }

}
