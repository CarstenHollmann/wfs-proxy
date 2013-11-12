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
