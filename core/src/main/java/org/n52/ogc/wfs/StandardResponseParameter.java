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
package org.n52.ogc.wfs;

import org.joda.time.DateTime;

/**
 * Abstract class for WFS standard response parameter
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public abstract class StandardResponseParameter {

    private DateTime timeStamp;

    private String numberMatched;

    /**
     * constructor
     * 
     * @param timeStamp
     *            Required time stamp attribute
     * @param numberMatched
     *            Required number matched attribute
     */
    public StandardResponseParameter(DateTime timeStamp, String numberMatched) {
        super();
        setTimeStamp(timeStamp);
        setNumberMatched(numberMatched);
    }

    /**
     * Get the time stamp
     * 
     * @return the time stamp
     */
    public DateTime getTimeStamp() {
        return timeStamp;
    }

    /**
     * Set the time stamp
     * 
     * @param timeStamp
     *            the time stamp to set
     */
    private void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Get the matched number
     * 
     * @return the matched number
     */
    public String getNumberMatched() {
        return numberMatched;
    }

    /**
     * Set the matched number
     * 
     * @param numberMatched
     *            the matched number
     */
    private void setNumberMatched(String numberMatched) {
        this.numberMatched = numberMatched;
    }

    /**
     * Get the returned number
     * 
     * @return the returned number
     */
    public abstract int getNumberReturned();

}
