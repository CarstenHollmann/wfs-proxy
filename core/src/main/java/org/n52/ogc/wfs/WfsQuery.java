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

import javax.xml.namespace.QName;

import org.n52.iceland.util.StringHelper;
import org.n52.sos.ogc.filter.AbstractAdHocQueryExpression;

/**
 * Class for WFS query element
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class WfsQuery extends AbstractAdHocQueryExpression {

    private String srsName;

    private String featureVersion;

    /**
     * constructor
     *
     * @param typeNames
     *            Required type names
     */
    public WfsQuery(Collection<QName> typeNames) {
        super(typeNames);
    }

    /**
     * Get srs name
     *
     * @return the srsName
     */
    public String getSrsName() {
        return srsName;
    }

    /**
     * Set srs name
     *
     * @param srsName
     *            the srsName to set
     * @return WfsQuery
     */
    public WfsQuery setSrsName(String srsName) {
        this.srsName = srsName;
        return this;
    }

    /**
     * Check if srs name is set
     *
     * @return <code>true</code>, if srs name is set
     */
    public boolean isSetSrsName() {
        return StringHelper.isNotEmpty(getSrsName());
    }

    /**
     * Get feature version
     *
     * @return the featureVersion
     */
    public String getFeatureVersion() {
        return featureVersion;
    }

    /**
     * Set feature version
     *
     * @param featureVersion
     *            the featureVersion to set
     * @return WfsQuery
     */
    public WfsQuery setFeatureVersion(String featureVersion) {
        this.featureVersion = featureVersion;
        return this;
    }

    /**
     * Check if feature version is set
     *
     * @return <code>true</code>, if feature version is set
     */
    public boolean isSetFeatureVersion() {
        return StringHelper.isNotEmpty(getFeatureVersion());
    }

}
