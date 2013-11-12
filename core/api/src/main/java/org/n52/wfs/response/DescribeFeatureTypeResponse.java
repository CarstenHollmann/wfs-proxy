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
package org.n52.wfs.response;

import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.response.AbstractServiceResponse;
import org.n52.sos.util.StringHelper;

import com.google.common.base.Strings;

/**
 * Class represents a WFS DescribeFeatureType response
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class DescribeFeatureTypeResponse extends AbstractServiceResponse {

    private String featureTypeDescription;

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.DescribeFeatureType.name();
    }

    /**
     * Get feature type description
     * 
     * @return The feature type description
     */
    public String getFeatureTypeDescription() {
        return featureTypeDescription;
    }

    /**
     * Set feature type description
     * 
     * @param featureTypeDescription
     *            the feature type description to set
     * @return DescribeFeatureTypeResponse
     */
    public DescribeFeatureTypeResponse setFeatureTypeDescription(String featureTypeDescription) {
        this.featureTypeDescription = featureTypeDescription;
        return this;
    }

    /**
     * Check if a feature type description is set
     * 
     * @return <code>true</code>, if a feature type description is set
     */
    public boolean isSetFeatureTypeDescription() {
        return StringHelper.isNotEmpty(getFeatureTypeDescription());
    }
}
