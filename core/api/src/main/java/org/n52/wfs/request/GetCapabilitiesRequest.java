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
package org.n52.wfs.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.request.AbstractServiceRequest;
import org.n52.sos.service.operator.ServiceOperatorKey;
import org.n52.sos.service.operator.ServiceOperatorRepository;
import org.n52.sos.util.CollectionHelper;
import org.n52.sos.util.StringHelper;

/**
 * WFS GetCapabilities service request
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class GetCapabilitiesRequest extends AbstractServiceRequest {

    private String updateSequence;

    private final List<String> acceptVersions = new LinkedList<String>();

    private final List<String> sections = new LinkedList<String>();

    private final List<String> acceptFormats = new LinkedList<String>();

    private List<ServiceOperatorKey> serviceOperatorKeyTypes;

    public GetCapabilitiesRequest() {
        setService(WfsConstants.WFS);
    }

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.GetCapabilities.name();
    }

    @Override
    public List<ServiceOperatorKey> getServiceOperatorKeyType() {
        if (serviceOperatorKeyTypes == null) {
            if (isSetAcceptVersions()) {
                serviceOperatorKeyTypes = new ArrayList<ServiceOperatorKey>(acceptVersions.size());
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
