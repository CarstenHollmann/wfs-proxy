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

public class WfsFeatureCollection extends StandardResponseParameter {
    
    public WfsFeatureCollection(DateTime timeStamp, String numberMatched) {
        super(timeStamp, numberMatched);
    }

    private List<WfsMember> member = Lists.newArrayList();

    public List<WfsMember> getMember() {
        return member;
    }
    
    public void addMember(WfsMember member) {
        getMember().add(member);
    }
    
    public void addMember(List<WfsMember> member) {
        this.member.addAll(member);
    }

    public void setMember(List<WfsMember> member) {
        this.member = member;
    }

    @Override
    public int getNumberReturned() {
        return getMember().size();
    }

    public boolean isSetMembers() {
        return CollectionHelper.isNotEmpty(getMember());
    }
    
}
