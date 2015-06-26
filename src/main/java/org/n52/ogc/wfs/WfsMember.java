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

import org.n52.ogc.wfs.WfsConstants.State;

/**
 * Interface for WFS member element
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 * @param <T>
 *            element type
 */
public interface WfsMember<T> {

    /**
     * Get the state
     *
     * @return the state
     */
    State getState();

    /**
     * Set the state
     *
     * @param state
     *              the state to set
     */
    void setState(State state);

    /**
     * Get element
     *
     * @return the element
     */
    T getElement();

    /**
     * Set element
     *
     * @param element
     *                the element to set
     */
    void setElement(T element);

}
