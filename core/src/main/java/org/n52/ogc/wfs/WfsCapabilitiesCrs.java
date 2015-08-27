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
