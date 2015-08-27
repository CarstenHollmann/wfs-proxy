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
