//    KrOS POS
//    Copyright (c) 2009-2017
//    
//
//     
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with KrOS POS.  If not, see <http://www.gnu.org/licenses/>.
//*****************************************************************************

package com.openbravo.pos.ticket;

import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.format.Formats;

/**
 *
 * @author  Adrian
 * @version 
 */
public class SalesDetailInfo implements IKeyed {

    private static final long serialVersionUID = 8612449444103L;
    private String productName;
    private int lineNO;
    private double price;

    public int getLineNO() {
        return lineNO;
    }

    public void setLineNO(int lineNO) {
        this.lineNO = lineNO;
    }

    public double getPrice() {
        return price;
    }

    public String printPrice() {
        return Formats.CURRENCY.formatValue(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public SalesDetailInfo(int lineNo, String productName, double price) {
        this.lineNO = lineNo;
        this.productName = productName;
        this.price = price;
    }

    public static SerializerRead<SalesDetailInfo> getSerializerRead() {
        return (DataRead dr) -> new SalesDetailInfo(
                dr.getInt(1),
                dr.getString(2),
                dr.getDouble(3));
    }

    @Override
    public Object getKey() {
       return getLineNO();
    }
}