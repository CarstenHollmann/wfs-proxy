/*
 * Copyright 2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * License version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.wfs.encode.wfs.v20;

import java.math.BigInteger;
import java.util.Map;

import net.opengis.wfs.x20.FeatureCollectionDocument;
import net.opengis.wfs.x20.FeatureCollectionType;

import org.apache.xmlbeans.XmlObject;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.gml.AbstractFeature;
import org.n52.iceland.ogc.gml.GmlConstants;
import org.n52.iceland.ogc.om.OmConstants;
import org.n52.iceland.ogc.ows.OWSConstants.HelperValues;
import org.n52.ogc.wfs.OmObservationMember;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsFeatureCollection;
import org.n52.ogc.wfs.WfsMember;
import org.n52.sos.ogc.om.OmObservation;
import org.n52.sos.util.CodingHelper;
import org.n52.sos.util.XmlHelper;
import org.n52.wfs.response.GetFeatureResponse;

import com.google.common.collect.Maps;

/**
 * WFS 2.0 GetFeature response encoder class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetFeatureResponseEncoder extends AbstractWfsResponseEncoder<GetFeatureResponse> {

    /**
     * constructor
     */
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
            for (WfsMember<?> member : featureCollection.getMember()) {
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

    /**
     * Get namespace for AbstractFeature
     *
     * @param abstractFeature
     *            AbstractFeature to get namespace for
     * @return Namespace
     */
    private String getNamespace(AbstractFeature abstractFeature) {
        if (abstractFeature instanceof OmObservation) {
            return OmConstants.NS_OM_2;
        }
        return GmlConstants.NS_GML_32;
    }

}
