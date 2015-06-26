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
