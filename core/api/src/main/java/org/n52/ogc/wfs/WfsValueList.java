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

import java.util.Collection;
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

    private Set<Object> values = Sets.newHashSet();

    /**
     * constructor
     * 
     * @param values
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
        return values;
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
