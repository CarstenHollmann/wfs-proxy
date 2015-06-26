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
