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

import static java.util.Collections.unmodifiableSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.n52.iceland.ogc.OGCConstants;
import org.n52.iceland.ogc.filter.FilterConstants;
import org.n52.iceland.ogc.gml.GmlConstants;
import org.n52.iceland.ogc.ows.OWSConstants;
import org.n52.iceland.util.http.MediaTypes;
import org.n52.iceland.w3c.SchemaLocation;

import com.google.common.collect.Sets;

/**
 * Interface for WFS constants and enumeration
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public interface WfsConstants {

    String WFS = "WFS";

    String VERSION = "2.0.0";

    Set<String> ACCEPT_FORMATS = unmodifiableSet(Sets.newHashSet(MediaTypes.APPLICATION_XML.toString()));

    String NS_WFS_20 = "http://www.opengis.net/wfs/2.0";

    String NS_WFS_PREFIX = "wfs";

    String SCHEMA_LOCATION_URL_WFS = "schemas.opengis.net/wfs/2.0/wfs.xsd";

    SchemaLocation WFS_SCHEMA_LOCATION = new SchemaLocation(NS_WFS_20, SCHEMA_LOCATION_URL_WFS);

    String FILTER_LANGUAGE_WFS_PREFIX = OGCConstants.QUERY_LANGUAGE_PREFIX + "OGC-WFS::";

    String NUMBER_MATCHED_UNKNOWN = "unknown";

    /**
     * default namespaces for WFS requests
     */
    public static Map<String, String> defaultNamespaces = new HashMap<String, String>() {
        private static final long serialVersionUID = -6752466686707257087L;
        {
            put(WfsConstants.NS_WFS_PREFIX, WfsConstants.NS_WFS_20);
            put(GmlConstants.NS_GML_PREFIX, GmlConstants.NS_GML_32);
            put(FilterConstants.NS_FES_2_PREFIX, FilterConstants.NS_FES_2);
            put(OWSConstants.NS_OWS_PREFIX, OWSConstants.NS_OWS);
        }

    };

    /**
     * Enum for WFS operations
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    enum Operations {
        GetCapabilities, DescribeFeatureType, DescribeStoredQueries, GetFeature, ListStoredQueries, GetPropertyValue
    }

    /**
     * Enum for WFS capabilities sections
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    enum CapabilitiesSections {
        ServiceIdentification, ServiceProvider, OperationsMetadata, All, Filter_Capabilities, FeatureTypeList
    }

    /**
     * Enum for WFS GetCapabilities parameter
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    enum GetCapabilitiesParams {
        Sections, AcceptFormats, AcceptVersions, updateSequence, Section
    }

    /**
     * Enum for WFS DescribeFeatureType parameter
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    enum DescribeFeatureTypeParams {
        TypeName
    }

    /**
     * Enum for WFS AdditionalCommonKeywords parameter, WFS 2.0 spec. table 7
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    enum AdditionalCommonKeywordsParams {
        Namespaces, VSPs
    }

    /**
     * Enum for WFS StandartPresentation parameter, WFS 2.0 spec. table 5
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    enum StandardPresentationParams {
        StartIndex, Count, OutputFormat, ResultType
    }

    /**
     * Enum for WFS StandardREsolve parameter, WFS 2.0 spec. table 6
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    enum StandardResolveParams {
        Resolve, ResolveDepth, ResolveTimeout
    }

    /**
     * Enum for WFS AdHocQuery parameter, WFS 2.0 spec. table 8. TypeNames
     * mandatory if ResourceId is defined.
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    enum AdHocQueryParams {
        TypeNames, Aliases, SrsName, Filter, Filter_Language, ResourceId, BBox, SortBy
    }

    /**
     * Enum for WFS ProjectionClause parameter, WFS 2.0 spec. table 9
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    enum ProjectionClauseParams {
        PropertyName
    }

    /**
     * Enum for WFS StoredQuery parameter, WFS 2.0 spec. table 10
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    enum StoredQueryParams {
        StoredQuery_Id
    }

    /**
     * Enum for WFS State
     *
     * @author Carsten Hollmann <c.hollmann@52north.org>
     *
     * @since 1.0.0
     *
     */
    public enum State {
        valid, superseded, retired, future
    }
}
