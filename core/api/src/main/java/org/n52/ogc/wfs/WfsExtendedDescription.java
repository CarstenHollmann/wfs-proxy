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

import java.util.Set;

import org.n52.sos.util.CollectionHelper;

import com.google.common.collect.Sets;

public class WfsExtendedDescription {
    
    private Set<WfsElement> elements = Sets.newHashSet();
    
    public WfsExtendedDescription(WfsElement element) {
        getElements().add(element);
    }
    
    public Set<WfsElement> getElements() {
        return elements;
    }
    
    public WfsExtendedDescription addElement(WfsElement element) {
        getElements().add(element);
        return this;
    }
    
    public WfsExtendedDescription addElements(Set<WfsElement> elements) {
        getElements().addAll(elements);
        return this;
    }
    
    public boolean isSetElements() {
        return CollectionHelper.isNotEmpty(getElements());
    }

}
