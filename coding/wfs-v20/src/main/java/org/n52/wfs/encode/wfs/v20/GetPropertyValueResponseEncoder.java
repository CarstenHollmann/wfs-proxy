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
package org.n52.wfs.encode.wfs.v20;

import net.opengis.wfs.x20.ValueCollectionDocument;
import net.opengis.wfs.x20.ValueCollectionType;

import org.apache.xmlbeans.XmlObject;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.util.XmlOptionsHelper;
import org.n52.wfs.response.GetPropertyValueResponse;

/**
 * WFS 2.0 GetPropertyValue response encoder class
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 *
 */
public class GetPropertyValueResponseEncoder extends AbstractWfsResponseEncoder<GetPropertyValueResponse> {

    /**
     * constructor
     */
    public GetPropertyValueResponseEncoder(){
        super(WfsConstants.Operations.GetPropertyValue.name(), GetPropertyValueResponse.class);
    }

    @Override
    protected XmlObject create(GetPropertyValueResponse arg0) throws OwsExceptionReport {
        ValueCollectionDocument valueCollectionDoc = ValueCollectionDocument.Factory.newInstance(XmlOptionsHelper.getInstance().getXmlOptions());
        ValueCollectionType valueCollectionType = valueCollectionDoc.addNewValueCollection();
        return valueCollectionDoc;
    }

}
