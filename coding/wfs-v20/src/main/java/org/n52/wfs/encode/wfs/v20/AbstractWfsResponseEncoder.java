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
package org.n52.wfs.encode.wfs.v20;

import static org.n52.sos.util.CodingHelper.encodeObjectToXml;
import java.util.Map;
import java.util.Set;

import org.apache.xmlbeans.XmlObject;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.encode.AbstractResponseEncoder;
import org.n52.sos.ogc.filter.FilterConstants;
import org.n52.sos.ogc.gml.GmlConstants;
import org.n52.sos.ogc.ows.OWSConstants;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.ogc.sos.SosConstants.HelperValues;
import org.n52.sos.ogc.swe.SweConstants;
import org.n52.sos.response.AbstractServiceResponse;
import org.n52.sos.w3c.SchemaLocation;

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
