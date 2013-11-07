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

import java.util.Set;

import javax.xml.namespace.QName;

import org.n52.sos.ogc.gml.ReferenceType;
import org.n52.sos.ogc.sos.SosEnvelope;
import org.n52.sos.util.CollectionHelper;

import com.google.common.collect.Sets;

public class WfsFeatureType {
    
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
    
    
    public WfsFeatureType(QName name, WfsCapabilitiesCrs crs) {
        this.name = name;
        this.crs = crs;
    }

    public QName getName() {
        return name;
    }

    public Set<String> getTitles() {
        return titles;
    }

    public WfsFeatureType addTitle(String titles) {
        getTitles().add(titles);
        return this;
    }
    
    public WfsFeatureType setTitles(Set<String> titles) {
        getTitles().addAll(titles);
        return this;
    }
    
    public boolean isSetTitles() {
        return CollectionHelper.isNotEmpty(getTitles());
    }

    public Set<String> getAbstracts() {
        return abstracts;
    }

    public WfsFeatureType addAbstract(String abstracts) {
        getAbstracts().add(abstracts);
        return this;
    }
    
    public WfsFeatureType setAbstracts(Set<String> abstracts) {
        getAbstracts().addAll(abstracts);
        return this;
    }
    
    public boolean isSetAbstracts() {
        return CollectionHelper.isNotEmpty(getAbstracts());
    }

    public Set<String> getKeywords() {
        return keywords;
    }
    
    public WfsFeatureType addKeywords(String keywords) {
        getKeywords().add(keywords);
         return this;
     }

    public WfsFeatureType setKeywords(Set<String> keywords) {
       getKeywords().addAll(keywords);
        return this;
    }
    
    public boolean isSetKeywords() {
        return CollectionHelper.isNotEmpty(getKeywords());
    }

    public WfsCapabilitiesCrs getCrs() {
        return crs;
    }

    public Set<String> getOutputFormats() {
        return outputFormats;
    }

    public WfsFeatureType addOutputFormats(String outputFormats) {
        getOutputFormats().add(outputFormats);
        return this;
    }
    
    public WfsFeatureType setOutputFormats(Set<String> outputFormats) {
        getOutputFormats().addAll(outputFormats);
        return this;
    }
    
    public boolean isSetOutputFormats() {
        return CollectionHelper.isNotEmpty(getOutputFormats());
    }

    public Set<SosEnvelope> getWgs84BoundingBoxes() {
        return wgs84BoundingBoxes;
    }

    public WfsFeatureType addWgs84BoundingBoxes(SosEnvelope wgs84BoundingBoxes) {
        getWgs84BoundingBoxes().add(wgs84BoundingBoxes);
        return this;
    }
    
    public WfsFeatureType setWgs84BoundingBoxes(Set<SosEnvelope> wgs84BoundingBoxes) {
        getWgs84BoundingBoxes().addAll(wgs84BoundingBoxes);
        return this;
    }
    
    public boolean isSetWgs84BoundingBoxes() {
        return CollectionHelper.isNotEmpty(getWgs84BoundingBoxes());
    }

    public Set<ReferenceType> getMetadataUrls() {
        return metadataUrls;
    }
    
    public WfsFeatureType addMetadataUrls(ReferenceType metadataUrls) {
        getMetadataUrls().add(metadataUrls);
        return this;
    }

    public WfsFeatureType setMetadataUrls(Set<ReferenceType> metadataUrls) {
        getMetadataUrls().addAll(metadataUrls);
        return this;
    }
    
    public boolean isSetMetadataUrls() {
        return CollectionHelper.isNotEmpty(getMetadataUrls());
    }

    public WfsExtendedDescription getExtendedDescription() {
        return extendedDescription;
    }

    public WfsFeatureType setExtendedDescription(WfsExtendedDescription extendedDescription) {
        this.extendedDescription = extendedDescription;
        return this;
    }
    
    public boolean isSetExtendenDescription() {
        return getExtendedDescription() != null;
    }

}
