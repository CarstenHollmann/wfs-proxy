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
package org.n52.wfs.request.operator;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;

import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsQuery;
import org.n52.sos.exception.ows.InvalidParameterValueException;
import org.n52.sos.ogc.om.OmConstants;
import org.n52.sos.ogc.ows.CompositeOwsException;
import org.n52.sos.ogc.ows.OwsExceptionReport;
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
        AbstractV2RequestOperator<AbstractGetFeatureDAO, GetFeatureRequest, GetFeatureResponse> {

    private static final String OPERATION_NAME = WfsConstants.Operations.GetFeature.name();

    /**
     * constructor
     */
    public WfsGetFeatureOperatorV20() {
        super(OPERATION_NAME, GetFeatureRequest.class);
    }

    @Override
    public Set<String> getConformanceClasses() {
        return Collections.emptySet();
    }

    @Override
    protected void checkParameters(GetFeatureRequest request) throws OwsExceptionReport {
        final CompositeOwsException exceptions = new CompositeOwsException();
        try {
            checkServiceParameter(request.getService());
        } catch (OwsExceptionReport owse) {
            exceptions.add(owse);
        }
        try {
            checkSingleVersionParameter(request);
        } catch (OwsExceptionReport owse) {
            exceptions.add(owse);
        }
        try {
            if (!request.isSetResourceIds()) {
                if (request.isSetQueries()) {
                    for (WfsQuery query : request.getQueries()) {
                        checkTypenames(query.getTypeNames(), WfsConstants.AdHocQueryParams.TypeNames.name());
                    }
                }
            }
        } catch (OwsExceptionReport owse) {
            exceptions.add(owse);
        }

        exceptions.throwIfNotEmpty();
    }

    @Override
    protected GetFeatureResponse receive(GetFeatureRequest request) throws OwsExceptionReport {
        return getDao().getFeatures(request);
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
    private void checkTypename(final QName typeName, String parameterName) throws OwsExceptionReport {
        if (typeName == null) {
            throw new MissingTypnameParameterException();
        } else if (!OmConstants.QN_OM_20_OBSERVATION.equals(typeName)) {
            throw new InvalidParameterValueException(parameterName, typeName.toString());
        }
    }

    /**
     * Check if requested typeNames are supported
     * 
     * @param typeNames
     *            TypeNames to check
     * @param parameterName
     *            Parameter name
     * @throws OwsExceptionReport
     *             If the requested typeNames are not supported
     */
    protected void checkTypenames(final Collection<QName> typeNames, final String parameterName)
            throws OwsExceptionReport {
        if (typeNames != null) {
            final CompositeOwsException exceptions = new CompositeOwsException();
            for (final QName typeName : typeNames) {
                try {
                    checkTypename(typeName, parameterName);
                } catch (final OwsExceptionReport owse) {
                    exceptions.add(owse);
                }
            }
            exceptions.throwIfNotEmpty();
        }
    }

}
