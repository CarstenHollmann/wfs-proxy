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
package org.n52.ogc.wfs;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import org.n52.iceland.ogc.ows.OwsCapabilities;
import org.n52.iceland.util.CollectionHelper;
import org.n52.sos.ogc.filter.FilterCapabilities;

/**
 * Class for WFS capabilities
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 * 
 */
public class WfsCapabilities extends OwsCapabilities {

    /**
     * Metadata for all supported filter
     */
    private FilterCapabilities filterCapabilities;

    // /**
    // * All ObservationOfferings provided by this SOS.
    // */
    // private SortedSet<SosObservationOffering> contents = new
    // TreeSet<SosObservationOffering>();

    private SortedSet<WfsFeatureType> featureTypeList = new TreeSet<WfsFeatureType>();

    // /**
    // * extensions
    // */
    // private List<CapabilitiesExtension> extensions = new
    // LinkedList<CapabilitiesExtension>();

    public WfsCapabilities(String version) {
        super(WfsConstants.WFS, version);
    }

    /**
     * Get filter capabilities
     *
     * @return filter capabilities
     */
    public FilterCapabilities getFilterCapabilities() {
        return filterCapabilities;
    }

    /**
     * Set filter capabilities
     *
     * @param filterCapabilities
     *            filter capabilities
     */
    public void setFilterCapabilities(FilterCapabilities filterCapabilities) {
        this.filterCapabilities = filterCapabilities;
    }

    /**
     * Check if filter capabilities are set
     *
     * @return <code>true</code>, if filter capabilities are set
     */
    public boolean isSetFilterCapabilities() {
        return getFilterCapabilities() != null;
    }

    /**
     * Get featureTypeList data
     *
     * @return featureTypeList data
     */
    public SortedSet<WfsFeatureType> getFeatureTypeList() {
        return Collections.unmodifiableSortedSet(featureTypeList);
    }

    /**
     * Set featureTypeList data
     *
     * @param featureTypeList
     *            featureTypeList data
     */
    public void setFeatureTypeList(Collection<WfsFeatureType> featureTypeList) {
        this.featureTypeList =
                featureTypeList == null ? new TreeSet<WfsFeatureType>() : new TreeSet<WfsFeatureType>(featureTypeList);
    }

    /**
     * Check if feature type list is set
     *
     * @return <code>true</code>, if feature type list is set
     */
    public boolean isSetFeatureTypeList() {
        return CollectionHelper.isNotEmpty(getFeatureTypeList());
    }

}
