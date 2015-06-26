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

import java.util.Set;

import org.n52.iceland.util.CollectionHelper;

import com.google.common.collect.Sets;

/**
 * Class represents a WFS extende description element
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class WfsExtendedDescription {

    private Set<WfsElement> elements = Sets.newHashSet();

    /**
     * constructor
     *
     * @param element
     *            Required element
     */
    public WfsExtendedDescription(WfsElement element) {
        getElements().add(element);
    }

    /**
     * Get elements
     *
     * @return the elements
     */
    public Set<WfsElement> getElements() {
        return elements;
    }

    /**
     * Add a new element
     *
     * @param element
     *            the element to add
     * @return WfsExtendedDescription
     */
    public WfsExtendedDescription addElement(WfsElement element) {
        getElements().add(element);
        return this;
    }

    /**
     * Add new elements
     *
     * @param elements
     *            the elements to add
     * @return WfsExtendedDescription
     */
    public WfsExtendedDescription addElements(Set<WfsElement> elements) {
        getElements().addAll(elements);
        return this;
    }

    /**
     * Check if elements are set
     *
     * @return <code>true</code>, if elements are set
     */
    public boolean isSetElements() {
        return CollectionHelper.isNotEmpty(getElements());
    }

}
