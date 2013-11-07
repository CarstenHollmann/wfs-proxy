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

import java.math.BigInteger;
import java.util.Map;

import net.opengis.wfs.x20.FeatureCollectionDocument;
import net.opengis.wfs.x20.FeatureCollectionType;

import org.apache.xmlbeans.XmlObject;
import org.n52.ogc.wfs.OmObservationMember;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsFeatureCollection;
import org.n52.ogc.wfs.WfsMember;
import org.n52.sos.ogc.gml.AbstractFeature;
import org.n52.sos.ogc.gml.GmlConstants;
import org.n52.sos.ogc.om.OmConstants;
import org.n52.sos.ogc.om.OmObservation;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.ogc.sos.SosConstants.HelperValues;
import org.n52.sos.util.CodingHelper;
import org.n52.sos.util.XmlHelper;
import org.n52.wfs.response.GetFeatureResponse;

import com.google.common.collect.Maps;

public class GetFeatureResponseEncoder extends AbstractWfsResponseEncoder<GetFeatureResponse> {

    public GetFeatureResponseEncoder() {
        super(WfsConstants.Operations.GetFeature.name(), GetFeatureResponse.class);
    }

    @Override
    protected XmlObject create(GetFeatureResponse response) throws OwsExceptionReport {
        FeatureCollectionDocument xbFeatureCollectionDoc =
                FeatureCollectionDocument.Factory.newInstance(getXmlOptions());
        FeatureCollectionType xbFeatureCollectionType = xbFeatureCollectionDoc.addNewFeatureCollection();
        WfsFeatureCollection featureCollection = response.getFeatureCollection();
        xbFeatureCollectionType.setTimeStamp(featureCollection.getTimeStamp().toGregorianCalendar());
        xbFeatureCollectionType.setNumberMatched(featureCollection.getNumberMatched());
        xbFeatureCollectionType.setNumberReturned(new BigInteger(Integer.toString(featureCollection
                .getNumberReturned())));
        Map<HelperValues, String> additionalValues = Maps.newHashMap();
        additionalValues.put(HelperValues.PROPERTY_TYPE, null);
        if (featureCollection.isSetMembers()) {
            for (WfsMember member : featureCollection.getMember()) {
                if (member instanceof OmObservationMember) {
                    OmObservationMember observationMember = (OmObservationMember) member;
                    XmlObject encodeObjectToXml =
                            CodingHelper.encodeObjectToXml(OmConstants.NS_OM_2, observationMember.getElement(),
                                    additionalValues);
                    xbFeatureCollectionType.addNewMember().set(encodeObjectToXml);
                }
            }
        }
        if (featureCollection.getMember().size() > 1) {
            XmlHelper.makeGmlIdsUnique(xbFeatureCollectionDoc.getDomNode());
        }
        return xbFeatureCollectionDoc;
    }

    private String getNamespace(AbstractFeature abstractFeature) {
        if (abstractFeature instanceof OmObservation) {
            return OmConstants.NS_OM_2;
        }
        return GmlConstants.NS_GML_32;
    }

}
