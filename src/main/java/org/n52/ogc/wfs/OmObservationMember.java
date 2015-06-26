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
import org.n52.sos.ogc.om.OmObservation;

/**
 * Concrete class of WFS member for OmObservation
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class OmObservationMember implements WfsMember<OmObservation> {

    private State state;

    private OmObservation observation;

    /**
     * constructor
     * 
     * @param observation
     *            Required observation
     */
    public OmObservationMember(OmObservation observation) {
        this.observation = observation;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public OmObservation getElement() {
        return observation;
    }

    @Override
    public void setElement(OmObservation element) {
        this.observation = element;
    }

}
