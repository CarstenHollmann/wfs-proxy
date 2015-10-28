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
package org.n52.wfs.encode.pilot;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.apache.xmlbeans.XmlObject;
import org.n52.iceland.coding.CodingRepository;
import org.n52.iceland.coding.encode.Encoder;
import org.n52.iceland.coding.encode.EncoderKey;
import org.n52.iceland.exception.ows.NoApplicableCodeException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.exception.ows.concrete.UnsupportedEncoderInputException;
import org.n52.iceland.ogc.gml.AbstractFeature;
import org.n52.iceland.ogc.gml.GmlConstants;
import org.n52.iceland.ogc.ows.OWSConstants.HelperValues;
import org.n52.iceland.service.ServiceConstants.SupportedType;
import org.n52.iceland.util.CollectionHelper;
import org.n52.iceland.util.JavaHelper;
import org.n52.iceland.w3c.SchemaLocation;
import org.n52.ogc.pilot.PilotConstants;
import org.n52.ogc.pilot.PilotFeature;
import org.n52.sos.coding.encode.AbstractXmlEncoder;
import org.n52.sos.util.CodingHelper;
import org.n52.sos.util.XmlOptionsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.x52North.pilot.PilotFeatureDocument;
import org.x52North.pilot.PilotFeatureType;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.vividsolutions.jts.geom.Geometry;

import net.opengis.gml.x32.PointDocument;
import net.opengis.gml.x32.PointPropertyType;
import net.opengis.gml.x32.PointType;

public class PilotFeatureEncoder extends AbstractXmlEncoder<AbstractFeature> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PilotFeatureEncoder.class);
    
    private static final Set<EncoderKey> ENCODER_KEYS =
            CodingHelper.encoderKeysForElements(PilotConstants.NS_PILOT, AbstractFeature.class);


    public PilotFeatureEncoder() {
        LOGGER.debug("Encoder for the following keys initialized successfully: {}!",
                Joiner.on(", ").join(ENCODER_KEYS));
    }

    @Override
    public Set<EncoderKey> getKeys() {
        return Collections.unmodifiableSet(ENCODER_KEYS);
    }

    @Override
    public Set<SupportedType> getSupportedTypes() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getConformanceClasses(String service, String version) {
        return Collections.emptySet();
    }

    @Override
    public void addNamespacePrefixToMap(final Map<String, String> nameSpacePrefixMap) {
        nameSpacePrefixMap.put(PilotConstants.NS_PILOT, PilotConstants.NS_PILOT_PREFIX);
    }

    @Override
    public Set<SchemaLocation> getSchemaLocations() {
        return Sets.newHashSet(PilotConstants.PILOT_SCHEMA_LOCATION);
    }

    @Override
    public XmlObject encode(final AbstractFeature abstractFeature, final Map<HelperValues, String> additionalValues)
            throws OwsExceptionReport {
        final XmlObject encodedObject = createFeature(abstractFeature);
        // LOGGER.debug("Encoded object {} is valid: {}",
        // encodedObject.schemaType().toString(),
        // XmlHelper.validateDocument(encodedObject));
        return encodedObject;
    }

    private XmlObject createFeature(AbstractFeature absFeature) throws OwsExceptionReport {
        if (absFeature instanceof PilotFeature) {
            final PilotFeature pilotFeat = (PilotFeature) absFeature;
            final StringBuilder builder = new StringBuilder();
            if (!pilotFeat.isSetGmlID()) {
                builder.append("pf_");
                builder.append(JavaHelper.generateID(absFeature.getIdentifierCodeWithAuthority().getValue()));
                absFeature.setGmlId(builder.toString());
            }

            final PilotFeatureDocument xbSampFeatDoc = PilotFeatureDocument.Factory
                    .newInstance(XmlOptionsHelper.getInstance().getXmlOptions());
            PilotFeatureType pft = xbSampFeatDoc.addNewPilotFeature();
            // TODO: CHECK for all fields set gml:id
            pft.setId(absFeature.getGmlId());

            if (pilotFeat.isSetIdentifier()) {
                pft.setFeatureId(pilotFeat.getIdentifier());
            }

            addNameDescription(pft, pilotFeat);

            // set position
            encodeLocation(pft, pilotFeat);
            return xbSampFeatDoc;
        }
        throw new UnsupportedEncoderInputException(this, absFeature);
    }
    
    private void encodeLocation(final PilotFeatureType pft, final PilotFeature pilotFeat) throws OwsExceptionReport {
        final Encoder<XmlObject, Geometry> encoder = CodingRepository.getInstance()
                .getEncoder(CodingHelper.getEncoderKey(GmlConstants.NS_GML_32, pilotFeat.getGeometry()));
        if (encoder != null) {
            final Map<HelperValues, String> gmlAdditionalValues =
                    new EnumMap<HelperValues, String>(HelperValues.class);
            gmlAdditionalValues.put(HelperValues.GMLID, pilotFeat.getGmlId());
            final XmlObject xmlObject = encoder.encode(pilotFeat.getGeometry(), gmlAdditionalValues);
            if (xmlObject instanceof PointType) {
                pft.addNewFeatureLocation().setPoint((PointType) xmlObject);
            } else if (xmlObject instanceof PointPropertyType) {
                pft.setFeatureLocation((PointPropertyType)xmlObject);
            } else if (xmlObject instanceof PointDocument) {
                pft.addNewFeatureLocation().setPoint(((PointDocument) xmlObject).getPoint());
            }
        } else {
            throw new NoApplicableCodeException()
                    .withMessage("Error while encoding geometry for feature, needed encoder is missing!");
        }
    }
    
    private void addNameDescription(PilotFeatureType pft, PilotFeature pilotFeature)
            throws OwsExceptionReport {
        if (pft != null) {
            if (pilotFeature.isSetName()) {
                removeExitingNames(pft);
                pft.setFeatureName(pilotFeature.getFirstName().getValue());
            }
        }
    }
    
    private void removeExitingNames(PilotFeatureType pft) {
        if (CollectionHelper.isNotNullOrEmpty(pft.getNameArray())) {
            for (int i = 0; i < pft.getNameArray().length; i++) {
                pft.removeName(i);
            }
        }
    }

}
