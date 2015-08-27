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
