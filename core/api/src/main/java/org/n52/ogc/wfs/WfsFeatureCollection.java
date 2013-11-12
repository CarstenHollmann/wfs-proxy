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

import java.util.List;

import org.joda.time.DateTime;
import org.n52.sos.util.CollectionHelper;

import com.google.common.collect.Lists;

/**
 * Class represents a WFS feature collection element
 * 
 * @author Carsten Hollmann <c.hollmann@52north.org>
 * 
 * @since 1.0.0
 * 
 */
public class WfsFeatureCollection extends StandardResponseParameter {

    @SuppressWarnings("rawtypes")
    private List<WfsMember> member = Lists.newArrayList();

    /**
     * constructor
     * 
     * @param timeStamp
     *            Required time stamp attribute
     * @param numberMatched
     *            Required number matched attribute
     */
    public WfsFeatureCollection(DateTime timeStamp, String numberMatched) {
        super(timeStamp, numberMatched);
    }

    /**
     * Get members
     * 
     * @return the members
     */
    @SuppressWarnings("rawtypes")
    public List<WfsMember> getMember() {
        return member;
    }

    /**
     * Add a new member
     * 
     * @param member
     *            the member to add
     */
    @SuppressWarnings("rawtypes")
    public void addMember(WfsMember member) {
        getMember().add(member);
    }

    /**
     * Add new members
     * 
     * @param member
     *            the members to add
     */
    @SuppressWarnings("rawtypes")
    public void addMember(List<WfsMember> member) {
        this.member.addAll(member);
    }

    /**
     * Set new members
     * 
     * @param member
     *            the members to set
     */
    @SuppressWarnings("rawtypes")
    public void setMember(List<WfsMember> member) {
        this.member = member;
    }

    @Override
    public int getNumberReturned() {
        return getMember().size();
    }

    /**
     * Check if members are set
     * 
     * @return <code>true</code>, if members are set
     */
    public boolean isSetMembers() {
        return CollectionHelper.isNotEmpty(getMember());
    }

}
