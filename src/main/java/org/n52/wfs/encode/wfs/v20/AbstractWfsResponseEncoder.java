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

import static org.n52.sos.util.CodingHelper.encodeObjectToXml;

import java.util.Map;
import java.util.Set;

import org.apache.xmlbeans.XmlObject;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.filter.FilterConstants;
import org.n52.iceland.ogc.gml.GmlConstants;
import org.n52.iceland.ogc.ows.OWSConstants;
import org.n52.iceland.ogc.ows.OWSConstants.HelperValues;
import org.n52.iceland.ogc.swe.SweConstants;
import org.n52.iceland.response.AbstractServiceResponse;
import org.n52.iceland.w3c.SchemaLocation;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.coding.encode.AbstractResponseEncoder;

import com.google.common.collect.Sets;

/**
 * WFS 2.0 abstract response encoder class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public abstract class AbstractWfsResponseEncoder<T extends AbstractServiceResponse> extends AbstractResponseEncoder<T> {

    /**
     * constructor
     *
     * @param operation
     *            Operation name
     * @param responseType
     *            Implemented response class
     */
    public AbstractWfsResponseEncoder(String operation, Class<T> responseType) {
        super(WfsConstants.WFS, WfsConstants.VERSION, operation, WfsConstants.NS_WFS_20, WfsConstants.NS_WFS_PREFIX,
                responseType);
    }

    @Override
    protected Set<SchemaLocation> getConcreteSchemaLocations() {
        return Sets.newHashSet(WfsConstants.WFS_SCHEMA_LOCATION);
    }

    /**
     * Encode service object to GML 3.2 XML object
     *
     * @param o
     *            Service object to encode
     * @return XML encoded object
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    protected XmlObject encodeGml(Object o) throws OwsExceptionReport {
        return encodeObjectToXml(GmlConstants.NS_GML_32, o);
    }

    /**
     * Encode service object to GML 3.2 XML object
     *
     * @param helperValues
     *            Additional values, e.g. to indicate to encode as Document or
     *            PropertyType
     * @param o
     *            Service object to encode
     * @return XML encoded object
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    protected XmlObject encodeGml(Map<HelperValues, String> helperValues, Object o) throws OwsExceptionReport {
        return encodeObjectToXml(GmlConstants.NS_GML_32, o, helperValues);
    }

    /**
     * Encode service object to OWS 1.1.0 XML object
     *
     * @param o
     *            Service object to encode
     * @return XML encoded object
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    protected XmlObject encodeOws(Object o) throws OwsExceptionReport {
        return encodeObjectToXml(OWSConstants.NS_OWS, o);
    }

    /**
     * Encode service object to OWS 1.1.0 XML object
     *
     * @param helperValues
     *            Additional values, e.g. to indicate to encode as Document or
     *            PropertyType
     * @param o
     *            Service object to encode
     * @return XML encoded object
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    protected XmlObject encodeOws(Map<HelperValues, String> helperValues, Object o) throws OwsExceptionReport {
        return encodeObjectToXml(OWSConstants.NS_OWS, o, helperValues);
    }

    /**
     * Encode service object to FES 2.0 XML object
     *
     * @param o
     *            Service object to encode
     * @return XML encoded object
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    protected XmlObject encodeFes(Object o) throws OwsExceptionReport {
        return encodeObjectToXml(FilterConstants.NS_FES_2, o);
    }

    /**
     * Encode service object to FES 2.0 XML object
     *
     * @param helperValues
     *            Additional values, e.g. to indicate to encode as Document or
     *            PropertyType
     * @param o
     *            Service object to encode
     * @return XML encoded object
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    protected XmlObject encodeFes(Map<HelperValues, String> helperValues, Object o) throws OwsExceptionReport {
        return encodeObjectToXml(FilterConstants.NS_FES_2, o, helperValues);
    }

    /**
     * Encode service object to SWE Common 2.0 XML object
     *
     * @param o
     *            Service object to encode
     * @return XML encoded object
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    protected XmlObject encodeSwe(Object o) throws OwsExceptionReport {
        return encodeObjectToXml(SweConstants.NS_SWE_20, o);
    }

    /**
     * Encode service object to SWE Common 2.0 XML object
     *
     * @param helperValues
     *            Additional values, e.g. to indicate to encode as Document or
     *            PropertyType
     * @param o
     *            Service object to encode
     * @return XML encoded object
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    protected XmlObject encodeSwe(Map<HelperValues, String> helperValues, Object o) throws OwsExceptionReport {
        return encodeObjectToXml(SweConstants.NS_SWE_20, o, helperValues);
    }

}
