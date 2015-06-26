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
 * Class for WFS result depth attribute
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class ResultDepth {

    private int depthValue = 0;

    /**
     * Set depth value
     * 
     * @param depthValue
     *            the depth value to set
     * @return ResultDepth
     */
    public ResultDepth setDepthValue(int depthValue) {
        this.depthValue = depthValue;
        return this;
    }

    /**
     * Get depth value
     * 
     * @return the depth value
     */
    public int getDepthValue() {
        return depthValue;
    }

    /**
     * Check if depth value is set
     * 
     * @return <code>true</code>, if depth value is set
     */
    public boolean isSetDepthValue() {
        return depthValue > 0;
    }
}
