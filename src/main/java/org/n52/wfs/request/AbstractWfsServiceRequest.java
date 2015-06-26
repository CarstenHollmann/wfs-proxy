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
package org.n52.wfs.request;

import java.util.Map;

import org.n52.iceland.request.AbstractServiceRequest;
import org.n52.iceland.response.AbstractServiceResponse;
import org.n52.iceland.util.CollectionHelper;
import org.n52.ogc.wfs.WfsConstants;

/**
 * Abstract WFS service request
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * @param <T> the response type
 *
 * @since 1.0.0
 *
 */
public abstract class AbstractWfsServiceRequest<T extends AbstractServiceResponse> extends AbstractServiceRequest<T> {

    /* Attribute for XML, not defined for KVP binding */
    private String handle;

    private Map<String, String> namespaces = WfsConstants.defaultNamespaces;

    /**
     * Get handle
     * @return the handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Set handle
     * @param handle the handle to set
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Get namespaces
     * @return the namespaces
     */
    public Map<String, String> getNamespaces() {
        return namespaces;
    }

    /**
     * Set namespaces
     * @param namespaces the namespaces to set
     */
    public void setNamespaces(Map<String, String> namespaces) {
        getNamespaces().putAll(namespaces);
    }

    /**
     * Check if namespaces are set
     * @return <code>true</code>, if namespaces are set
     */
    public boolean isSetNamespaces() {
        return CollectionHelper.isNotEmpty(getNamespaces());
    }

}
