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

import javax.inject.Inject;

import net.opengis.wfs.x20.DescribeStoredQueriesResponseDocument;
import net.opengis.wfs.x20.DescribeStoredQueriesResponseType;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.util.Producer;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.wfs.response.DescribeStoredQueriesResponse;

/**
 * WFS 2.0 DescribeStoredQueries response encoder class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class DescribeStoredQueriesResponseEncoder extends AbstractWfsResponseEncoder<DescribeStoredQueriesResponse> {

    private Producer<XmlOptions> xmlOptions;

    public DescribeStoredQueriesResponseEncoder() {
        super(WfsConstants.Operations.DescribeStoredQueries.name(), DescribeStoredQueriesResponse.class);
    }

    @Inject
    public void setXmlOptions(Producer<XmlOptions> xmlOptions) {
        this.xmlOptions = xmlOptions;
    }

    @Override
    protected XmlObject create(DescribeStoredQueriesResponse response)
            throws OwsExceptionReport {
        DescribeStoredQueriesResponseDocument describeStoredQueriesResponseDoc
                = DescribeStoredQueriesResponseDocument.Factory
                .newInstance(this.xmlOptions.get());
        DescribeStoredQueriesResponseType describeStoredQueriesResponseType
                = describeStoredQueriesResponseDoc
                .addNewDescribeStoredQueriesResponse();
        return describeStoredQueriesResponseDoc;
    }

}
