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
package org.n52.ogc.wfs;

import java.util.Set;

import javax.xml.namespace.QName;

import org.n52.iceland.util.CollectionHelper;
import org.n52.sos.ogc.gml.ReferenceType;
import org.n52.sos.ogc.sos.SosEnvelope;

import com.google.common.collect.Sets;

/**
 * Class for WFS feature type element
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class WfsFeatureType implements Comparable<WfsFeatureType> {

    /* 1..1 */
    private QName name;

    /* 0..* */
    private Set<String> titles = Sets.newHashSet();

    /* 0..* */
    private Set<String> abstracts = Sets.newHashSet();

    /* 0..* */
    private Set<String> keywords = Sets.newHashSet();

    /* 1..1 */
    private WfsCapabilitiesCrs crs;

    /* 0..1 */
    private Set<String> outputFormats = Sets.newHashSet();

    /* 0..* */
    private Set<SosEnvelope> wgs84BoundingBoxes = Sets.newHashSet();

    /* 0..1 */
    private Set<ReferenceType> metadataUrls = Sets.newHashSet();

    /* 0..1 */
    private WfsExtendedDescription extendedDescription;

    /**
     * constructor
     *
     * @param name
     *            Required name
     * @param crs
     *            Required capabilities CRS
     */
    public WfsFeatureType(QName name, WfsCapabilitiesCrs crs) {
        this.name = name;
        this.crs = crs;
    }

    /**
     * Get the name
     *
     * @return the name
     */
    public QName getName() {
        return name;
    }

    /**
     * Get titles
     *
     * @return the titles
     */
    public Set<String> getTitles() {
        return titles;
    }

    /**
     * Add a new title
     *
     * @param titles
     *            the title to add
     * @return WfsFeatureType
     */
    public WfsFeatureType addTitle(String titles) {
        getTitles().add(titles);
        return this;
    }

    /**
     * Set titles
     *
     * @param titles
     *            the titles to set
     * @return WfsFeatureType
     */
    public WfsFeatureType setTitles(Set<String> titles) {
        getTitles().addAll(titles);
        return this;
    }

    /**
     * Check if titles are set
     *
     * @return <code>true</code>, Check if titles are set
     */
    public boolean isSetTitles() {
        return CollectionHelper.isNotEmpty(getTitles());
    }

    /**
     * Get abstracts
     *
     * @return the abstracts
     */
    public Set<String> getAbstracts() {
        return abstracts;
    }

    /**
     * Add a new abstract
     *
     * @param abstracts
     *            the abstract to set
     * @return WfsFeatureType
     */
    public WfsFeatureType addAbstract(String abstracts) {
        getAbstracts().add(abstracts);
        return this;
    }

    /**
     * Set abstracts
     *
     * @param abstracts
     *            the abstracts to set
     * @return WfsFeatureType
     */
    public WfsFeatureType setAbstracts(Set<String> abstracts) {
        getAbstracts().addAll(abstracts);
        return this;
    }

    /**
     * Check if abstracts are set
     *
     * @return <code>true</code>, Check if abstracts are set
     */
    public boolean isSetAbstracts() {
        return CollectionHelper.isNotEmpty(getAbstracts());
    }

    /**
     * Get keywords
     *
     * @return the keywords
     */
    public Set<String> getKeywords() {
        return keywords;
    }

    /**
     * Add a new keyword
     *
     * @param keywords
     *            the keyword to add
     * @return WfsFeatureType
     */
    public WfsFeatureType addKeywords(String keywords) {
        getKeywords().add(keywords);
        return this;
    }

    /**
     * Set keywords
     *
     * @param keywords
     *            the keywords to set
     * @return WfsFeatureType
     */
    public WfsFeatureType setKeywords(Set<String> keywords) {
        getKeywords().addAll(keywords);
        return this;
    }

    /**
     * Check if keywords are set
     *
     * @return <code>true</code>, Check if keywords are set
     */
    public boolean isSetKeywords() {
        return CollectionHelper.isNotEmpty(getKeywords());
    }

    /**
     * Get capabilities CRS
     *
     * @return the capabilities CRS
     */
    public WfsCapabilitiesCrs getCrs() {
        return crs;
    }

    /**
     * Get output formats
     *
     * @return the output formats
     */
    public Set<String> getOutputFormats() {
        return outputFormats;
    }

    /**
     * Add a new output format
     *
     * @param outputFormats
     *            the output format to add
     * @return WfsFeatureType
     */
    public WfsFeatureType addOutputFormats(String outputFormats) {
        getOutputFormats().add(outputFormats);
        return this;
    }

    /**
     * Set output formats
     *
     * @param outputFormats
     *            the output formats to set
     * @return WfsFeatureType
     */
    public WfsFeatureType setOutputFormats(Set<String> outputFormats) {
        getOutputFormats().addAll(outputFormats);
        return this;
    }

    /**
     * Check if output formats are set
     *
     * @return <code>true</code>, Check if output formats are set
     */
    public boolean isSetOutputFormats() {
        return CollectionHelper.isNotEmpty(getOutputFormats());
    }

    /**
     * Get the WGS84 bounding boxes
     *
     * @return the WGS84 bounding boxes
     */
    public Set<SosEnvelope> getWgs84BoundingBoxes() {
        return wgs84BoundingBoxes;
    }

    /**
     * Add a new WGS84 bounding box
     *
     * @param wgs84BoundingBoxes
     *            the WGS84 bounding box to add
     * @return WfsFeatureType
     */
    public WfsFeatureType addWgs84BoundingBoxes(SosEnvelope wgs84BoundingBoxes) {
        getWgs84BoundingBoxes().add(wgs84BoundingBoxes);
        return this;
    }

    /**
     * Set WGS84 bounding boxes
     *
     * @param wgs84BoundingBoxes
     *            the WGS84 bounding boxes to set
     * @return WfsFeatureType
     */
    public WfsFeatureType setWgs84BoundingBoxes(Set<SosEnvelope> wgs84BoundingBoxes) {
        getWgs84BoundingBoxes().addAll(wgs84BoundingBoxes);
        return this;
    }

    /**
     * Check if WGS84 bounding boxes are set
     *
     * @return <code>true</code>, Check if WGS84 bounding boxes are set
     */
    public boolean isSetWgs84BoundingBoxes() {
        return CollectionHelper.isNotEmpty(getWgs84BoundingBoxes());
    }

    /**
     * Get the metadataURLs
     *
     * @return
     */
    public Set<ReferenceType> getMetadataUrls() {
        return metadataUrls;
    }

    /**
     * Add a new metadataURL
     *
     * @param metadataUrls
     *            the metadataURL to add
     * @return WfsFeatureType
     */
    public WfsFeatureType addMetadataUrls(ReferenceType metadataUrls) {
        getMetadataUrls().add(metadataUrls);
        return this;
    }

    /**
     * Set metadataURLs
     *
     * @param metadataUrls
     *            the metadataURLs to set
     * @return WfsFeatureType
     */
    public WfsFeatureType setMetadataUrls(Set<ReferenceType> metadataUrls) {
        getMetadataUrls().addAll(metadataUrls);
        return this;
    }

    /**
     * Check if metadataURLs are set
     *
     * @return <code>true</code>, Check if metadataURLs are set
     */
    public boolean isSetMetadataUrls() {
        return CollectionHelper.isNotEmpty(getMetadataUrls());
    }

    /**
     * Get the extended description
     *
     * @return the extended description
     */
    public WfsExtendedDescription getExtendedDescription() {
        return extendedDescription;
    }

    /**
     * Set the extended description
     *
     * @param extendedDescription
     *            the extended description to set
     * @return WfsFeatureType
     */
    public WfsFeatureType setExtendedDescription(WfsExtendedDescription extendedDescription) {
        this.extendedDescription = extendedDescription;
        return this;
    }

    /**
     * Check if extended description is set
     *
     * @return <code>true</code>, Check if extended description is set
     */
    public boolean isSetExtendenDescription() {
        return getExtendedDescription() != null;
    }

    @Override
    public int compareTo(WfsFeatureType arg0) {
        return getName().getLocalPart().compareTo(arg0.getName().getLocalPart());
    }

}
