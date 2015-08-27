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
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Class representing a WFS value list
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class WfsValueList {

    private final Set<Object> values = Sets.newHashSet();

    /**
     * constructor
     *
     * @param value
     *            Required value
     */
    public WfsValueList(Object value) {
        super();
        this.values.add(value);
    }

    /**
     * Get values
     *
     * @return the values
     */
    public Set<Object> getValues() {
        return Collections.unmodifiableSet(values);
    }

    /**
     * Add a value
     *
     * @param value
     *            the value to add
     * @return WfsValueList
     */
    public WfsValueList addValue(Object value) {
        getValues().add(value);
        return this;
    }

    /**
     * Add values
     *
     * @param values
     *            the values to add
     * @return WfsValueList
     */
    public WfsValueList addValues(Collection<Object> values) {
        getValues().addAll(values);
        return this;
    }
}
