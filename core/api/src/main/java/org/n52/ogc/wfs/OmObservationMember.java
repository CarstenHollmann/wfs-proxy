/**
 * Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * This program is free software; you can redistribute and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the
 * Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; even without the implied
 * WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program (see gnu-gpl v2.txt). If not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or
 * visit the Free Software Foundation web page, http://www.fsf.org.
 */
package org.n52.ogc.wfs;

import org.n52.ogc.wfs.WfsConstants.State;
import org.n52.sos.ogc.om.OmObservation;

public class OmObservationMember implements WfsMember<OmObservation> {
    
    private State state;
    
    private OmObservation observation;
    
    public OmObservationMember(OmObservation observattion) {
        this.observation = observattion;
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
    public void setEelement(OmObservation element) {
       this.observation = element;
    }

}
