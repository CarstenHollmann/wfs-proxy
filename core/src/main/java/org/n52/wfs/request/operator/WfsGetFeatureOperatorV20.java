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
package org.n52.wfs.request.operator;

import static org.n52.iceland.exception.ows.CompositeOwsException.toCompositeException;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.n52.iceland.exception.ows.CompositeOwsException;
import org.n52.iceland.exception.ows.InvalidParameterValueException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.om.OmConstants;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsQuery;
import org.n52.sos.ogc.om.features.SfConstants;
import org.n52.sos.request.operator.AbstractRequestOperator;
import org.n52.wfs.ds.AbstractGetFeatureHandler;
import org.n52.wfs.exception.wfs.concrete.MissingTypnameParameterException;
import org.n52.wfs.request.GetFeatureRequest;
import org.n52.wfs.response.GetFeatureResponse;

/**
 * WFS GetFeature operation operator class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class WfsGetFeatureOperatorV20 extends
        AbstractRequestOperator<AbstractGetFeatureHandler, GetFeatureRequest, GetFeatureResponse> {
    private static final String OPERATION_NAME = WfsConstants.Operations.GetFeature.name();
    private static final String PARAM_TYPE_NAME = WfsConstants.AdHocQueryParams.TypeNames.name();

    public WfsGetFeatureOperatorV20() {
        super(WfsConstants.WFS,
              WfsConstants.VERSION,
              OPERATION_NAME,
              GetFeatureRequest.class);
    }

    @Override
    protected void checkParameters(GetFeatureRequest request) throws OwsExceptionReport {
        final CompositeOwsException exceptions = new CompositeOwsException();
        exceptions.wrap(() -> checkServiceParameter(request.getService()));
        exceptions.wrap(() -> checkSingleVersionParameter(request));
        exceptions.wrap(() -> checkTypeNames(request));
        exceptions.throwIfNotEmpty();
    }

    @Override
    protected GetFeatureResponse receive(GetFeatureRequest request) throws OwsExceptionReport {
        return getOperationHandler().getFeatures(request);
    }


    /**
     * Check if requested typeNames are supported
     *
     * @param request the request
     * @throws OwsExceptionReport
     *             If the requested typeNames are not supported
     */
    private void checkTypeNames(GetFeatureRequest request)
            throws OwsExceptionReport {
        if (!request.isSetResourceIds() && request.isSetQueries()) {
            request.getQueries().stream()
                    .map(WfsQuery::getTypeNames)
                    .flatMap(Collection::stream)
                    .collect(toCompositeException(this::checkTypeName))
                    .throwIfNotEmpty();
        }
    }

    private void checkTypeName(QName typeName) throws OwsExceptionReport {
        checkTypename(typeName, PARAM_TYPE_NAME);
    }

    /**
     * Check if requested typeName is supported
     *
     * @param typeName
     *            TypeName to check
     * @param parameterName
     *            Parameter name
     * @throws OwsExceptionReport
     *             If the requested typeName is not supported
     */
    private void checkTypename(QName typeName, String parameterName) throws OwsExceptionReport {
        if (typeName == null) {
            throw new MissingTypnameParameterException();
        } else if (!checkQNameOfType(typeName, OmConstants.QN_OM_20_OBSERVATION) && !checkQNameOfType(typeName, SfConstants.QN_SAMS_20_SPATIAL_SAMPLING_FEATURE)) {
            throw new InvalidParameterValueException(parameterName, typeName.toString());
        }
    }
    
    
    private boolean checkQNameOfType(QName toCheck, QName against) {
        if (toCheck != null) {
            if (against.equals(toCheck)) {
                return true;
            } else {
                return against.getLocalPart().equals(toCheck.getLocalPart());
            }
        }
        return false;
    }
}
