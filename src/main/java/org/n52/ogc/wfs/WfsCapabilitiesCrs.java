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

import java.util.Set;

import org.n52.iceland.util.CollectionHelper;
import org.n52.iceland.util.StringHelper;

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
