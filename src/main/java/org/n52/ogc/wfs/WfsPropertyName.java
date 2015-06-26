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
