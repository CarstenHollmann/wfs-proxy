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
package org.n52.wfs.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.n52.iceland.cache.ContentCache;
import org.n52.iceland.cache.WritableContentCache;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.gml.AbstractFeature;
import org.n52.iceland.ogc.ows.OwsCapabilities;
import org.n52.iceland.util.CollectionHelper;

public class InMemoryCacheImpl implements ContentCache, WritableContentCache {

    private static final long serialVersionUID = 7357861778543588998L;
    
    private OwsCapabilities owsCapabilities;
    
    private Set<AbstractFeature> abstractFeatures = newSynchronizedSet();
    
    public OwsCapabilities getCapabilities() throws OwsExceptionReport {
        return owsCapabilities;
    }
    
    public void setOwsCapabilities(OwsCapabilities owsCapabilities) {
        this.owsCapabilities = owsCapabilities;
    }

    /**
     * @return the abstractFeatures
     */
    public Set<AbstractFeature> getAbstractFeatures() {
        return copyOf(abstractFeatures);
    }

    /**
     * @param abstractFeatures the abstractFeatures to set
     */
    public void setAbstractFeatures(Set<AbstractFeature> abstractFeatures) {
        if (CollectionHelper.isNotEmpty(abstractFeatures)) {
            this.abstractFeatures.clear();
            this.abstractFeatures.addAll(abstractFeatures);
        }
    }

    public boolean isSetAbstractFeatures() {
        return CollectionHelper.isNotEmpty(getAbstractFeatures());
    }
    
    protected static <T> Set<T> newSynchronizedSet() {
        return newSynchronizedSet(null);
    }
    
    protected static <T> Set<T> newSynchronizedSet(Iterable<T> elements) {
        if (elements == null) {
            return CollectionHelper.synchronizedSet(0);
        } else {
            if (elements instanceof Collection) {
                return Collections.synchronizedSet(new HashSet<T>((Collection<T>) elements));
            } else {
                HashSet<T> hashSet = new HashSet<T>();
                for (T t : elements) {
                    hashSet.add(t);
                }
                return Collections.synchronizedSet(hashSet);
            }
        }
    }
    
    protected static <T> Set<T> copyOf(Set<T> set) {
        if (set == null) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(new HashSet<T>(set));
        }
    }

}


