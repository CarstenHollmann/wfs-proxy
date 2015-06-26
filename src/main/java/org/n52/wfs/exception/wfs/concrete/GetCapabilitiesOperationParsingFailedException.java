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

import org.n52.ogc.wfs.WfsConstants;
import org.n52.wfs.exception.wfs.OperationParsingFailedException;

/**
 * Concrete exception class of OperationParsingFailed exception for GetCapabilities operation
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 *
 */
public class GetCapabilitiesOperationParsingFailedException extends OperationParsingFailedException {

    private static final long serialVersionUID = -7531990762157000457L;

    /**
     * constructor
     */
    public GetCapabilitiesOperationParsingFailedException() {
        super();
        at(WfsConstants.Operations.GetCapabilities);
    }

}