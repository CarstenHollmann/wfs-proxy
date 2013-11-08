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
 * @since 4.0.0
 *
 */
public class WfsQuery extends AbstractAdHocQueryExpression{
    
    public WfsQuery(Collection<QName> typeNames) {
        super(typeNames);
    }

    private String srsName;
    
    private String featureVersion;

    /**
     * @return the srsName
     */
    public String getSrsName() {
        return srsName;
    }

    /**
     * @param srsName the srsName to set
     */
    public WfsQuery setSrsName(String srsName) {
        this.srsName = srsName;
        return this;
    }
    
    public boolean isSetSrsName() {
        return StringHelper.isNotEmpty(getSrsName());
    }

    /**
     * @return the featureVersion
     */
    public String getFeatureVersion() {
        return featureVersion;
    }

    /**
     * @param featureVersion the featureVersion to set
     */
    public WfsQuery setFeatureVersion(String featureVersion) {
        this.featureVersion = featureVersion;
        return this;
    }
    
    public boolean isSetFeatureVersion() {
        return StringHelper.isNotEmpty(getFeatureVersion());
    }

}