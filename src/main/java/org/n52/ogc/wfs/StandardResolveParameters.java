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
