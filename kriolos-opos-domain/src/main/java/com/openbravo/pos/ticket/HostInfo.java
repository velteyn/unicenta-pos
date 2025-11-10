/*
 * Copyright (C) 2022 KriolOS
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openbravo.pos.ticket;

import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializerRead;

public class HostInfo implements IKeyed {

    //MONEY     HOST    HOSTSEQUENCE    DATESTART       DATEEND
    //private static final long serialVersionUID = 8612449444103L;
    private String money;
    private String host;
    private String hostsequence;

    /**
     * Creates new HostInfo
     *
     * @param money
     * @param host
     * @param hostsequence
     */
    public HostInfo(String money, String host, String hostsequence) {

        this.money = host; // hack to search by hostname
        this.host = host;
        this.hostsequence = hostsequence;
    }

    @Override
    public Object getKey() {
        return money;
    }

    public String getHostsequence() {
        return hostsequence;
    }

    public void setHostsequence(String m_Hostsequence) {
        this.hostsequence = m_Hostsequence;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String m_sHost) {
        this.host = m_sHost;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String m_sMoney) {
        this.money = m_sMoney;
    }

    @Override
    public String toString() {
        return host;
    }

    public static SerializerRead<HostInfo> getSerializerRead() {
        return (DataRead dr) -> new HostInfo(dr.getString(1), dr.getString(2), dr.getString(3));
    }
}
