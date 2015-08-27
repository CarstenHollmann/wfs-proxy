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

import org.n52.sos.ogc.filter.AbstractProjectionClause;

/**
 * Class for WFS propertyName element
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class WfsPropertyName extends StandardResolveParameters implements AbstractProjectionClause {

    private QName qName;

    private String resolvePath;

    /**
     * Get QName
     * 
     * @return the qName
     */
    public QName getqName() {
        return qName;
    }

    /**
     * Set QName
     * 
     * @param qName
     *            the qName to set
     * @return WfsPropertyName
     */
    public WfsPropertyName setqName(QName qName) {
        this.qName = qName;
        return this;
    }

    /**
     * Get resolve path
     * 
     * @return the resolvePath
     */
    public String getResolvePath() {
        return resolvePath;
    }

    /**
     * Set resolve path
     * 
     * @param resolvePath
     *            the resolvePath to set
     * @return WfsPropertyName
     */
    public WfsPropertyName setResolvePath(String resolvePath) {
        this.resolvePath = resolvePath;
        return this;
    }
}
