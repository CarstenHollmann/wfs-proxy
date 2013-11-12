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
package org.n52.wfs.ds;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.sos.exception.CodedException;
import org.n52.sos.service.Configurator;
import org.n52.wfs.exception.wfs.OperationProcessingFailedException;
import org.n52.wfs.request.DescribeFeatureTypeRequest;
import org.n52.wfs.response.DescribeFeatureTypeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WFS DAO class for DescribeFeatureType operation
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class DescribeFeatureTypeDAO extends AbstractDescribeFeatureTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DescribeFeatureTypeDAO.class);

    /**
     * constructor
     */
    public DescribeFeatureTypeDAO() {
        super(WfsConstants.WFS);
    }

    @Override
    public DescribeFeatureTypeResponse describeFeatureType(DescribeFeatureTypeRequest request) throws CodedException {
        DescribeFeatureTypeResponse response = new DescribeFeatureTypeResponse();
        response.setService(request.getService());
        response.setVersion(request.getVersion());
        response.setFeatureTypeDescription(getFeatureTypeDescription(request.getTypeNames()));
        return response;
    }

    /**
     * Get the featureType description for typeNames
     * 
     * @param typeNames
     *            TypeNames to get featureType description for
     * @return FeatureType description
     * @throws CodedException
     *             If an error occurs during description creation
     */
    private String getFeatureTypeDescription(Set<QName> typeNames) throws CodedException {
        InputStream inputStream = null;
        try {
            inputStream = Configurator.getInstance().getClass().getResourceAsStream("/observation.xsd");
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer);
            return writer.toString();
        } catch (IOException ioe) {
            throw new OperationProcessingFailedException().causedBy(ioe).withMessage(
                    "Error while reading featureType description!");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    LOGGER.error("Error while closing InputStream!", ioe);
                }
            }
        }
    }

}
