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

import javax.xml.namespace.QName;

import org.n52.iceland.ogc.ows.OwsMetadata;

/**
 * Class for WFS element element
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class WfsElement {

    private final String name;
    private final QName type;
    private final OwsMetadata metadata;
    private final WfsValueList valueList;

    /**
     * @param name
     *            Require name
     * @param type
     *            Required type
     * @param metadata
     *            Required metadata
     * @param valueList
     *            Required value list
     */
    public WfsElement(String name, QName type, OwsMetadata metadata, WfsValueList valueList) {
        super();
        this.name = name;
        this.type = type;
        this.metadata = metadata;
        this.valueList = valueList;
    }

    /**
     * Get the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the type
     *
     * @return the type
     */
    public QName getType() {
        return type;
    }

    /**
     * Get the metadata
     *
     * @return the metadata
     */
    public OwsMetadata getMetadata() {
        return metadata;
    }

    /**
     * Get the value list
     *
     * @return the valueList
     */
    public WfsValueList getValueList() {
        return valueList;
    }

}
