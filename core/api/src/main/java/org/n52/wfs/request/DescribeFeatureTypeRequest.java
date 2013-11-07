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
package org.n52.wfs.request;

import java.util.Set;

import javax.xml.namespace.QName;

import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.util.CollectionHelper;
import org.n52.sos.util.http.MediaType;
import org.n52.sos.util.http.MediaTypes;

import com.google.common.collect.Sets;

public class DescribeFeatureTypeRequest extends AbstractWfsServiceRequest {
    
    /* 0..* */
    private Set<QName> typeNames = Sets.newHashSet();
    
    /* 1..1 */
    private MediaType outputFormat = MediaTypes.APPLICTION_GML_32;
    
    @Override
    public String getOperationName() {
        return WfsConstants.Operations.DescribeFeatureType.name();
    }

    public Set<QName> getTypeNames() {
        return typeNames;
    }
    
    public DescribeFeatureTypeRequest addTypeNames(QName typeNames) {
        getTypeNames().add(typeNames);
        return this;
    }
    
    public DescribeFeatureTypeRequest addTypeNames(Set<QName> typeNames) {
        getTypeNames().addAll(typeNames);
        return this;
    }

    public DescribeFeatureTypeRequest setTypeNames(Set<QName> typeNames) {
        this.typeNames = typeNames; 
        return this;
    }
    
    public boolean isSetTypeNames() {
        return CollectionHelper.isNotEmpty(getTypeNames());
    }

    public MediaType getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(MediaType outputFormat) {
        this.outputFormat = outputFormat;
    }

}
