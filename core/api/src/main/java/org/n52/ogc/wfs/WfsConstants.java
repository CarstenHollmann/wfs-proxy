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

import static java.util.Collections.unmodifiableSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.n52.sos.ogc.OGCConstants;
import org.n52.sos.ogc.filter.FilterConstants;
import org.n52.sos.ogc.gml.GmlConstants;
import org.n52.sos.ogc.ows.OWSConstants;
import org.n52.sos.util.http.MediaTypes;
import org.n52.sos.w3c.SchemaLocation;

import com.google.common.collect.Sets;

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

    public static Map<String, String> defaultNamespaces = new HashMap<String, String>() {
        private static final long serialVersionUID = -6752466686707257087L;

        {
            put(WfsConstants.NS_WFS_PREFIX, WfsConstants.NS_WFS_20);
            put(GmlConstants.NS_GML_PREFIX, GmlConstants.NS_GML_32);
            put(FilterConstants.NS_FES_2_PREFIX, FilterConstants.NS_FES_2);
            put(OWSConstants.NS_OWS_PREFIX, OWSConstants.NS_OWS);
        }

    };

    enum Operations {
        GetCapabilities, DescribeFeatureType, DescribeStoredQueries, GetFeature, ListStoredQueries, GetPropertyValue
    }

    enum CapabilitiesSections {
        ServiceIdentification, ServiceProvider, OperationsMetadata, All, Filter_Capabilities, FeatureTypeList
    }

    enum GetCapabilitiesParams {
        Sections, AcceptFormats, AcceptVersions, updateSequence, Section
    }

    enum DescribeFeatureTypeParams {
        TypeName
    }

    // WFS 2.0, table 7
    enum AdditionalCommonKeywordsParams {
        Namespaces, VSPs
    }
    
    // WFS 2.0, table 5
    enum StandardPresentationParams {
        StartIndex, Count, OutputFormat, ResultType
    }
    
    // WFS 2.0, table 6
    enum StandardResolveParams {
        Resolve, ResolveDepth, ResolveTimeout
    }
    
    // WFS 2.0, table 8
    // TypeNames mandatory if ResourceId is defined.
    enum AdHocQueryParams {
        TypeNames, Aliases, SrsName, Filter, Filter_Language, ResourceId, BBox, SortBy
    }
    
    // WFS 2.0, table 9
    enum ProjectionClauseParams {
        PropertyName
    }
    
    // WFS 2.0, table 10
    enum StoredQueryParams {
        StoredQuery_Id
    }
    
    enum SortOrder {
        ASC, DESC
    }

    public enum State {
        valid, superseded, retired, future
    }
}
