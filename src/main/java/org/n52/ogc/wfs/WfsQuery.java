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
