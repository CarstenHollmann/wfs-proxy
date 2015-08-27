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

import com.google.common.collect.Sets;

/**
 * Class represents a WFS extende description element
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class WfsExtendedDescription {

    private Set<WfsElement> elements = Sets.newHashSet();

    /**
     * constructor
     *
     * @param element
     *            Required element
     */
    public WfsExtendedDescription(WfsElement element) {
        getElements().add(element);
    }

    /**
     * Get elements
     *
     * @return the elements
     */
    public Set<WfsElement> getElements() {
        return elements;
    }

    /**
     * Add a new element
     *
     * @param element
     *            the element to add
     * @return WfsExtendedDescription
     */
    public WfsExtendedDescription addElement(WfsElement element) {
        getElements().add(element);
        return this;
    }

    /**
     * Add new elements
     *
     * @param elements
     *            the elements to add
     * @return WfsExtendedDescription
     */
    public WfsExtendedDescription addElements(Set<WfsElement> elements) {
        getElements().addAll(elements);
        return this;
    }

    /**
     * Check if elements are set
     *
     * @return <code>true</code>, if elements are set
     */
    public boolean isSetElements() {
        return CollectionHelper.isNotEmpty(getElements());
    }

}
