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
package org.n52.wfs.ds;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;

import org.n52.iceland.exception.CodedException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.wfs.exception.wfs.OperationProcessingFailedException;
import org.n52.wfs.request.DescribeFeatureTypeRequest;
import org.n52.wfs.response.DescribeFeatureTypeResponse;

/**
 * WFS DAO class for DescribeFeatureType operation
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class DescribeFeatureTypeDAO extends AbstractDescribeFeatureTypeDAO {

    public DescribeFeatureTypeDAO() {
        super(WfsConstants.WFS);
    }

    @Override
    public DescribeFeatureTypeResponse describeFeatureType(DescribeFeatureTypeRequest request) throws OwsExceptionReport {
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
    private String getFeatureTypeDescription(Set<QName> typeNames)
            throws OwsExceptionReport {
        try (InputStream inputStream = getClass().getResourceAsStream("/observation.xsd")) {
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer);
            return writer.toString();
        } catch (IOException ioe) {
            throw new OperationProcessingFailedException().causedBy(ioe)
                    .withMessage("Error while reading featureType description!");
        }
    }

}
