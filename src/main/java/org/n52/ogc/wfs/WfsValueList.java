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
