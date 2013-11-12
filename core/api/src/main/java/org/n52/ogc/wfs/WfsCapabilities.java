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
package org.n52.ogc.wfs;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import org.n52.sos.ogc.filter.FilterCapabilities;
import org.n52.sos.ogc.ows.OwsCapabilities;
import org.n52.sos.util.CollectionHelper;

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
