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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.ogc.filter.Filter;
import org.n52.sos.ogc.filter.SpatialFilter;
import org.n52.sos.util.CollectionHelper;
import org.n52.sos.util.StringHelper;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class GetFeatureRequest extends AbstractWfsServiceRequest {
    
    private Set<QName> typeNames = Sets.newHashSet();
    
    private Set<String> aliases = Sets.newHashSet();
    
    private String srsName;
    
    private Set<String> propertyNames = Sets.newHashSet();
    
    private Filter<?> filter;
    
    private String filterLanguage;
    
    private Set<String> resourceIds = Sets.newHashSet();
    
    private SpatialFilter bbox;
    
    private Map<String, WfsConstants.SortOrder> sortBy = Maps.newHashMap();

    @Override
    public String getOperationName() {
        return WfsConstants.Operations.GetFeature.name();
    }

    public Set<QName> getTypeNames() {
        return typeNames;
    }
    
    public GetFeatureRequest addTypeNames(QName typeNames) {
        getTypeNames().add(typeNames);
        return this;
    }
    
    public GetFeatureRequest addTypeNames(Collection<QName> typeNames) {
        getTypeNames().addAll(typeNames);
        return this;
    }

    public GetFeatureRequest setTypeNames(Collection<QName> typeNames) {
        this.typeNames = Sets.newHashSet(typeNames); 
        return this;
    }
    
    public boolean isSetTypeNames() {
        return CollectionHelper.isNotEmpty(getTypeNames());
    }
    
    public Set<String> getAliases() {
        return aliases;
    }
    
    public GetFeatureRequest addAliases(String aliases) {
        getAliases().add(aliases);
        return this;
    }
    
    public GetFeatureRequest addAliases(Collection<String> aliases) {
        getAliases().addAll(aliases);
        return this;
    }

    public GetFeatureRequest setAliases(Collection<String> aliases) {
        this.aliases = Sets.newHashSet(aliases); 
        return this;
    }
    
    public boolean isSetAliases() {
        return CollectionHelper.isNotEmpty(getAliases());
    }
    
    public String getSrsName() {
        return srsName;
    }
    
    public GetFeatureRequest setSrsName(String srsName) {
        this.srsName = srsName;
        return this;
    }
    
    public boolean isSetSrsName() {
        return StringHelper.isNotEmpty(getSrsName());
    }
    
    public Set<String> getPropertyNames() {
        return propertyNames;
    }
    
    public GetFeatureRequest addPropertyNames(String propertyNames) {
        getPropertyNames().add(propertyNames);
        return this;
    }
    
    public GetFeatureRequest addPropertyNames(Collection<String> propertyNames) {
        getPropertyNames().addAll(propertyNames);
        return this;
    }

    public GetFeatureRequest setPropertyNames(Collection<String> propertyNames) {
        this.propertyNames = Sets.newHashSet(propertyNames); 
        return this;
    }
    
    public boolean isSetPropertyNames() {
        return CollectionHelper.isNotEmpty(getPropertyNames());
    }
    
    public Filter<?> getFilter() {
        return filter;
    }
    
    public GetFeatureRequest setFilter(Filter<?> filter) {
        this.filter = filter;
        return this;
    }
    
    public boolean isSetFilter() {
        return getFilter() != null;
    }
    
    public String getFilterLanguage() {
        return filterLanguage;
    }
    
    public GetFeatureRequest setFilterLanguage(String filterLanguage) {
        this.filterLanguage = filterLanguage;
        return this;
    }
    
    public boolean isSetFilterLanguage() {
        return StringHelper.isNotEmpty(getFilterLanguage());
    }
    
    public Set<String> getResourceIds() {
        return resourceIds;
    }
    
    public GetFeatureRequest addResourceIds(String resourceIds) {
        getResourceIds().add(resourceIds);
        return this;
    }
    
    public GetFeatureRequest addResourceIds(Collection<String> resourceIds) {
        getResourceIds().addAll(resourceIds);
        return this;
    }

    public GetFeatureRequest setResourceIds(Collection<String> resourceIds) {
        this.resourceIds = Sets.newHashSet(resourceIds); 
        return this;
    }
    
    public boolean isSetResourceIds() {
        return CollectionHelper.isNotEmpty(getResourceIds());
    }
    
    public Map<String, WfsConstants.SortOrder> getSortBy() {
        return sortBy;
    }
    
    public GetFeatureRequest addSortBy(String sortByKey, WfsConstants.SortOrder value) {
        getSortBy().put(sortByKey, value);
        return this;
    }
    
    public GetFeatureRequest addSortBy(Map<String, WfsConstants.SortOrder> sortBy) {
        getSortBy().putAll(sortBy);
        return this;
    }

    public GetFeatureRequest setSortBy(Map<String, WfsConstants.SortOrder> sortBy) {
        this.sortBy = sortBy; 
        return this;
    }
    
    public boolean isSetSortBy() {
        return CollectionHelper.isNotEmpty(getSortBy());
    }

    public SpatialFilter getBBox() {
        return bbox;
    }
    public GetFeatureRequest setBBox(SpatialFilter bbox) {
        this.bbox = bbox;
        return this;
    }
    
    public boolean isSetBBox() {
        return getBBox() != null;
    }

}
