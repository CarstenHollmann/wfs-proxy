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

import java.util.Set;

import org.n52.sos.util.CollectionHelper;
import org.n52.sos.util.StringHelper;

import com.google.common.collect.Sets;

/**
 * Class for WFS capabilities crs element
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class WfsCapabilitiesCrs {

    /* 1..1 */
    private String defaultCrs;

    /* 0..* */
    private Set<String> otherCrs = Sets.newHashSet();

    /**
     * constructor
     * 
     * @param defaultCrs
     *            Required default CRS
     */
    public WfsCapabilitiesCrs(String defaultCrs) {
        setDefaultCrs(defaultCrs);
    }

    /**
     * Get default CRS
     * 
     * @return the default CRS
     */
    public String getDefaultCrs() {
        return defaultCrs;
    }

    /**
     * Set the default CRS
     * 
     * @param defaultCrs
     *            the default CRS to set
     */
    private void setDefaultCrs(String defaultCrs) {
        this.defaultCrs = defaultCrs;
    }

    /**
     * Get other CRSes
     * 
     * @return the other CRSes
     */
    public Set<String> getOtherCrs() {
        return otherCrs;
    }

    /**
     * Add another CRS
     * 
     * @param otherCrs
     *            the CRS to set
     * @return WfsCapabilitiesCrs
     */
    public WfsCapabilitiesCrs addOtherCrs(String otherCrs) {
        getOtherCrs().add(otherCrs);
        return this;
    }

    /**
     * Set other CRSes
     * 
     * @param otherCrs
     *            the CRSes to set
     * @return WfsCapabilitiesCrs
     */
    public WfsCapabilitiesCrs setOtherCrs(Set<String> otherCrs) {
        getOtherCrs().addAll(otherCrs);
        return this;
    }

    /**
     * Check if default CRS is set
     * 
     * @return <code>true</code>, if default CRS is set
     */
    public boolean isSetCrs() {
        return StringHelper.isNotEmpty(getDefaultCrs());
    }

    /**
     * Check if other CRSes are set
     * 
     * @return <code>true</code>, if other CRSes are set
     */
    public boolean isSetOtherCrs() {
        return CollectionHelper.isNotEmpty(getOtherCrs());
    }

}
