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
