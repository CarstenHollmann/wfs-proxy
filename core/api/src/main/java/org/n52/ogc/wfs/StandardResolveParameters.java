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

/**
 * Class for WFS standard resolve paramenters
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class StandardResolveParameters {

    private static final int DEFAULT_RESOLVE_TIMEOUT = 300;

    /**
     * Enum for resolve value type
     * 
     * @author Carsten Hollmann <c.hollmann@52north.org>
     * @since 1.0.0
     * 
     */
    public enum ResolveValueType {
        local, remote, all, none
    }

    private ResolveValueType resolveValueType = ResolveValueType.none;

    private ResultDepth resultDepth = new ResultDepth();

    private int resolveTimeout = DEFAULT_RESOLVE_TIMEOUT;

    /**
     * Get resolve value type
     * 
     * @return the resolveValueType
     */
    public ResolveValueType getResolveValueType() {
        return resolveValueType;
    }

    /**
     * Set resolve value type
     * 
     * @param resolveValueType
     *            the resolveValueType to set
     */
    public void setResolveValueType(ResolveValueType resolveValueType) {
        this.resolveValueType = resolveValueType;
    }

    /**
     * Get result depth
     * 
     * @return the resultDepth
     */
    public ResultDepth getResultDepth() {
        return resultDepth;
    }

    /**
     * Set result depth
     * 
     * @param resultDepth
     *            the resultDepth to set
     */
    public void setResultDepth(ResultDepth resultDepth) {
        this.resultDepth = resultDepth;
    }

    /**
     * Get resolve timeout
     * 
     * @return the resolveTimeout
     */
    public int getResolveTimeout() {
        return resolveTimeout;
    }

    /**
     * Set resolve timeout
     * 
     * @param resolveTimeout
     *            the resolveTimeout to set
     */
    public void setResolveTimeout(int resolveTimeout) {
        this.resolveTimeout = resolveTimeout;
    }

}
