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

import java.util.List;

import org.joda.time.DateTime;

import org.n52.iceland.util.CollectionHelper;

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
