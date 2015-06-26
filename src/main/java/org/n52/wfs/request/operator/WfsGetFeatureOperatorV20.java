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
package org.n52.wfs.request.operator;

import static org.n52.iceland.exception.ows.CompositeOwsException.toCompositeException;

import java.util.Collection;
import java.util.stream.Stream;

import javax.xml.namespace.QName;

import org.n52.iceland.exception.ows.CompositeOwsException;
import org.n52.iceland.exception.ows.InvalidParameterValueException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.om.OmConstants;
import org.n52.iceland.util.ThrowingConsumer;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsQuery;
import org.n52.sos.request.operator.AbstractRequestOperator;
import org.n52.wfs.ds.AbstractGetFeatureDAO;
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
        AbstractRequestOperator<AbstractGetFeatureDAO, GetFeatureRequest, GetFeatureResponse> {
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
        } else if (!OmConstants.QN_OM_20_OBSERVATION.equals(typeName)) {
            throw new InvalidParameterValueException(parameterName, typeName.toString());
        }
    }
}
