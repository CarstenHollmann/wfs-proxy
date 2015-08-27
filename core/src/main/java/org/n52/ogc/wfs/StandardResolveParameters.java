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

/**
 * Class for WFS standard resolve paramenters
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class StandardResolveParameters {

    private static final int DEFAULT_RESOLVE_TIMEOUT = 300;

    /**
     * Enum for resolve value type
     * 
     * @author Carsten Hollmann <c.hollmann@52north.org>
     * @since 1.0.0
     * 
     */
    public enum ResolveValueType {
        local, remote, all, none
    }

    private ResolveValueType resolveValueType = ResolveValueType.none;

    private ResultDepth resultDepth = new ResultDepth();

    private int resolveTimeout = DEFAULT_RESOLVE_TIMEOUT;

    /**
     * Get resolve value type
     * 
     * @return the resolveValueType
     */
    public ResolveValueType getResolveValueType() {
        return resolveValueType;
    }

    /**
     * Set resolve value type
     * 
     * @param resolveValueType
     *            the resolveValueType to set
     */
    public void setResolveValueType(ResolveValueType resolveValueType) {
        this.resolveValueType = resolveValueType;
    }

    /**
     * Get result depth
     * 
     * @return the resultDepth
     */
    public ResultDepth getResultDepth() {
        return resultDepth;
    }

    /**
     * Set result depth
     * 
     * @param resultDepth
     *            the resultDepth to set
     */
    public void setResultDepth(ResultDepth resultDepth) {
        this.resultDepth = resultDepth;
    }

    /**
     * Get resolve timeout
     * 
     * @return the resolveTimeout
     */
    public int getResolveTimeout() {
        return resolveTimeout;
    }

    /**
     * Set resolve timeout
     * 
     * @param resolveTimeout
     *            the resolveTimeout to set
     */
    public void setResolveTimeout(int resolveTimeout) {
        this.resolveTimeout = resolveTimeout;
    }

}
