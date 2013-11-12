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

import javax.xml.namespace.QName;

import org.n52.sos.ogc.filter.AbstractAdHocQueryExpression;
import org.n52.sos.util.StringHelper;

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
