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
package org.n52.wfs.exception.wfs.concrete;

import org.n52.iceland.exception.ows.MissingParameterValueException;
import org.n52.ogc.wfs.WfsConstants;

/**
 * Concrete exception class of MissingParameterValue exception for typeName parameter
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public class MissingTypnameParameterException extends MissingParameterValueException {

    private static final long serialVersionUID = 4610386959061666748L;

    /**
     * constructor
     */
    public MissingTypnameParameterException() {
        super(WfsConstants.AdHocQueryParams.TypeNames.name());
    }

}
