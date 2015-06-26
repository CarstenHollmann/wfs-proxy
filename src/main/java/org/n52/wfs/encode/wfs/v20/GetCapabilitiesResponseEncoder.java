/*
 * Copyright 2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.n52.wfs.encode.wfs.v20;

import java.util.Map;
import java.util.Set;

import net.opengis.ows.x11.KeywordsType;
import net.opengis.ows.x11.WGS84BoundingBoxType;
import net.opengis.wfs.x20.ElementType;
import net.opengis.wfs.x20.ExtendedDescriptionType;
import net.opengis.wfs.x20.FeatureTypeListType;
import net.opengis.wfs.x20.FeatureTypeType;
import net.opengis.wfs.x20.MetadataURLType;
import net.opengis.wfs.x20.ValueListType;
import net.opengis.wfs.x20.WFSCapabilitiesDocument;
import net.opengis.wfs.x20.WFSCapabilitiesType;

import org.apache.xmlbeans.XmlObject;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.gml.GmlConstants;
import org.n52.iceland.ogc.gml.time.Time;
import org.n52.iceland.ogc.ows.OWSConstants;
import org.n52.iceland.ogc.ows.OWSConstants.HelperValues;
import org.n52.ogc.wfs.WfsCapabilities;
import org.n52.ogc.wfs.WfsCapabilitiesCrs;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsElement;
import org.n52.ogc.wfs.WfsExtendedDescription;
import org.n52.ogc.wfs.WfsFeatureType;
import org.n52.ogc.wfs.WfsValueList;
import org.n52.sos.ogc.gml.ReferenceType;
import org.n52.sos.ogc.sos.SosEnvelope;
import org.n52.sos.util.CodingHelper;
import org.n52.sos.util.SosHelper;
import org.n52.wfs.response.GetCapabilitiesResponse;

import com.google.common.collect.Maps;

/**
 * WFS 2.0 GetCapabilities response encoder class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class GetCapabilitiesResponseEncoder extends AbstractWfsResponseEncoder<GetCapabilitiesResponse> {

    /**
     * constructor
     */
    public GetCapabilitiesResponseEncoder() {
        super(WfsConstants.Operations.GetCapabilities.name(), GetCapabilitiesResponse.class);
    }

    @Override
    protected XmlObject create(GetCapabilitiesResponse response) throws OwsExceptionReport {
        WFSCapabilitiesDocument doc = WFSCapabilitiesDocument.Factory.newInstance(getXmlOptions());
        WFSCapabilitiesType xbCaps = doc.addNewWFSCapabilities();

        xbCaps.setVersion(response.getVersion());
        WfsCapabilities caps = response.getCapabilities();
        encodeServiceIdentification(caps, xbCaps);
        encodeServiceProvider(caps, xbCaps);
        encodeOperationsMetadata(caps, xbCaps);
        encodeFilterCapabilities(caps, xbCaps);
        encodeFeatureTypeList(caps, xbCaps, response.getVersion());
        return doc;
    }

    /**
     * Encode the service identification section
     *
     * @param caps
     *            Service WFS capabilties
     * @param xbCaps
     *            XML WFS capabilities
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private void encodeServiceIdentification(WfsCapabilities caps, WFSCapabilitiesType xbCaps)
            throws OwsExceptionReport {
        if (caps.isSetServiceIdentification()) {
            xbCaps.addNewServiceIdentification().set(encodeOws(caps.getServiceIdentification()));
        }
    }

    /**
     * Encode the service provider section
     *
     * @param caps
     *            Service WFS capabilties
     * @param xbCaps
     *            XML WFS capabilities
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private void encodeServiceProvider(WfsCapabilities caps, WFSCapabilitiesType xbCaps) throws OwsExceptionReport {
        if (caps.isSetServiceProvider()) {
            xbCaps.addNewServiceProvider().set(encodeOws(caps.getServiceProvider()));
        }
    }

    /**
     * Encode the operations metadata section
     *
     * @param caps
     *            Service WFS capabilties
     * @param xbCaps
     *            XML WFS capabilities
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private void encodeOperationsMetadata(WfsCapabilities caps, WFSCapabilitiesType xbCaps) throws OwsExceptionReport {
        if (caps.isSetOperationsMetadata() && caps.getOperationsMetadata().isSetOperations()) {
            xbCaps.addNewOperationsMetadata().set(encodeOws(caps.getOperationsMetadata()));
        }
    }

    /**
     * Encode the filter capabilities section
     *
     * @param caps
     *            Service WFS capabilties
     * @param xbCaps
     *            XML WFS capabilities
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private void encodeFilterCapabilities(WfsCapabilities caps, WFSCapabilitiesType xbCaps) throws OwsExceptionReport {
        if (caps.isSetFilterCapabilities()) {
            xbCaps.addNewFilterCapabilities().set(encodeFes(caps.getFilterCapabilities()));
        }
    }

    /**
     * Encode the WFS feature type list section
     *
     * @param wfsCapabilities
     *            Service WFS capabilties
     * @param xbCaps
     *            XML WFS capabilities
     * @param version
     *            Capabilities version
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private void encodeFeatureTypeList(WfsCapabilities wfsCapabilities, WFSCapabilitiesType xbCaps, String version)
            throws OwsExceptionReport {
        if (wfsCapabilities.isSetFeatureTypeList()) {
            FeatureTypeListType xbFeatureTypeList = xbCaps.addNewFeatureTypeList();
            for (WfsFeatureType wfsFeatureType : wfsCapabilities.getFeatureTypeList()) {
                encodeFeatureType(xbFeatureTypeList.addNewFeatureType(), wfsFeatureType);
            }
        }
    }

    /**
     * Encode the featureType
     *
     * @param xbFeatureType
     *            XML WFS featureType
     * @param wfsFeatureType
     *            Service WFS featureType
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private void encodeFeatureType(FeatureTypeType xbFeatureType, WfsFeatureType wfsFeatureType)
            throws OwsExceptionReport {
        xbFeatureType.setName(wfsFeatureType.getName());
        if (wfsFeatureType.isSetTitles()) {
            encodeTitles(xbFeatureType, wfsFeatureType.getTitles());
        }
        if (wfsFeatureType.isSetAbstracts()) {
            encodeAbstracts(xbFeatureType, wfsFeatureType.getAbstracts());
        }
        if (wfsFeatureType.isSetKeywords()) {
            encodeKeywords(xbFeatureType, wfsFeatureType.getKeywords());
        }
        encodeCrs(xbFeatureType, wfsFeatureType.getCrs());
        if (wfsFeatureType.isSetOutputFormats()) {
            encodeOutputFormats(xbFeatureType, wfsFeatureType.getOutputFormats());
        }
        if (wfsFeatureType.isSetWgs84BoundingBoxes()) {
            encodeWgs84BoundingBoxes(xbFeatureType, wfsFeatureType.getWgs84BoundingBoxes());
        }
        if (wfsFeatureType.isSetMetadataUrls()) {
            encodeMetadataUrls(xbFeatureType, wfsFeatureType.getMetadataUrls());
        }
        if (wfsFeatureType.isSetExtendenDescription()) {
            encodeExtendedDescription(xbFeatureType, wfsFeatureType.getExtendedDescription());
        }
    }

    /**
     * Encode the featureType titles
     *
     * @param xbFeatureType
     *            XML WFS featureType
     * @param titles
     *            Service WFS titles
     */
    private void encodeTitles(FeatureTypeType xbFeatureType, Set<String> titles) {
        titles.forEach(s -> xbFeatureType.addNewTitle().setStringValue(s));
    }

    /**
     * Encode the featureType abstracts
     *
     * @param xbFeatureType
     *            XML WFS featureType
     * @param abstracts
     *            Service WFS abstracts
     */
    private void encodeAbstracts(FeatureTypeType xbFeatureType, Set<String> abstracts) {
        abstracts.forEach(s -> xbFeatureType.addNewAbstract().setStringValue(s));
    }

    /**
     * Encode the featureType keywords
     *
     * @param xbFeatureType
     *            XML WFS featureType
     * @param keywords
     *            Service WFS keywords
     */
    private void encodeKeywords(FeatureTypeType xbFeatureType, Set<String> keywords) {
        KeywordsType xbKeywords = xbFeatureType.addNewKeywords();
        keywords.forEach(s -> xbKeywords.addNewKeyword().setStringValue(s));
    }

    /**
     * Encode the featureType capabilities CRS
     *
     * @param xbFeatureType
     *            XML WFS featureType
     * @param crs
     *            Service WFS capabilities CRS
     */
    private void encodeCrs(FeatureTypeType xbFeatureType, WfsCapabilitiesCrs crs) {

        if (crs != null && crs.isSetCrs()) {
            xbFeatureType.setDefaultCRS(crs.getDefaultCrs());
            if (crs.isSetOtherCrs()) {
                crs.getOtherCrs().forEach(other -> xbFeatureType.addNewOtherCRS().setStringValue(other));
            }
        } else {
            xbFeatureType.addNewNoCRS();
        }

    }

    /**
     * Encode the featureType outputFormats
     *
     * @param xbFeatureType
     *            XML WFS featureType
     * @param outputFormats
     *            Service WFS outputFormats
     */
    private void encodeOutputFormats(FeatureTypeType xbFeatureType, Set<String> outputFormats) {
        outputFormats.forEach(xbFeatureType.addNewOutputFormats()::addFormat);
    }

    /**
     * Encode the featureType WGS84 bounding boxes
     *
     * @param xbFeatureType
     *            XML WFS featureType
     * @param wgs84BoundingBoxes
     *            Service WFS WGS84 bounding boxes
     */
    private void encodeWgs84BoundingBoxes(FeatureTypeType xbFeatureType, Set<SosEnvelope> wgs84BoundingBoxes) {
        wgs84BoundingBoxes.stream()
                .filter(SosEnvelope::isSetEnvelope)
                .map(SosEnvelope::getEnvelope)
                .map(SosHelper::getMinMaxFromEnvelopeAsList)
                .forEach(minmax -> {
                    WGS84BoundingBoxType bbox = xbFeatureType.addNewWGS84BoundingBox();
                    bbox.setLowerCorner(minmax.getMinimum());
                    bbox.setUpperCorner(minmax.getMaximum());
                });
    }

    /**
     * Encode the featureType metadataUrls
     *
     * @param xbFeatureType
     *            XML WFS featureType
     * @param metadataUrls
     *            Service WFS metadataUrls
     */
    private void encodeMetadataUrls(FeatureTypeType xbFeatureType, Set<ReferenceType> metadataUrls) {
        metadataUrls.stream().forEach(url -> encodeMetadataUrl(xbFeatureType.addNewMetadataURL(), url));
    }

    /**
     * Encode a metadataURL element
     *
     * @param xbMetadataURL
     *            XML WFS metadataURL
     * @param metadataUrl
     *            Service WFS metadataURL
     */
    private void encodeMetadataUrl(MetadataURLType xbMetadataURL, ReferenceType metadataUrl) {
        xbMetadataURL.setHref(metadataUrl.getHref());
        if (metadataUrl.isSetTitle()) {
            xbMetadataURL.setTitle(metadataUrl.getTitle());
        }
        if (metadataUrl.isSetRole()) {
            xbMetadataURL.setRole(metadataUrl.getRole());
        }
    }

    /**
     * Encode the featureType extendedDescription
     *
     * @param xbFeatureType
     *            XML WFS featureType
     * @param extendedDescription
     *            Service WFS extendedDescription
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private void encodeExtendedDescription(FeatureTypeType xbFeatureType, WfsExtendedDescription extendedDescription)
            throws OwsExceptionReport {
        if (extendedDescription.isSetElements()) {
            ExtendedDescriptionType xbExtendedDescription = xbFeatureType.addNewExtendedDescription();
            for (WfsElement element : extendedDescription.getElements()) {
                encodeElement(xbExtendedDescription.addNewElement(), element);
            }
        }
    }

    /**
     * Encode extendedDescription element element
     *
     * @param elementType
     *            XML WFS ExtendedDescription element element
     * @param element
     *            Servcie WFS ExtendedDescription element element
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private void encodeElement(ElementType elementType, WfsElement element) throws OwsExceptionReport {
        elementType.setName(element.getName());
        elementType.setType(element.getType());
        elementType.addNewMetadata().set(CodingHelper.encodeObjectToXml(OWSConstants.NS_OWS, element.getMetadata()));
        encodeValueList(elementType.addNewValueList(), element.getValueList());
    }

    /**
     * Encode WFS value list
     *
     * @param xbValueList
     *            XML WFS value list
     * @param valueList
     *            Service WFS value list
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private void encodeValueList(ValueListType xbValueList, WfsValueList valueList) throws OwsExceptionReport {
        for (Object value : valueList.getValues()) {
            if (value instanceof Time) {
                Map<HelperValues, String> additionalValues = Maps.newHashMap();
                additionalValues.put(HelperValues.PROPERTY_TYPE, null);
                xbValueList.addNewValue().set(
                        CodingHelper.encodeObjectToXml(GmlConstants.NS_GML_32, value, additionalValues));
            } else {
                // TODO throw not supported exception
            }
        }

    }

}
