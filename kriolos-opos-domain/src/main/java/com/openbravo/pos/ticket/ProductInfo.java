//    KrOS POS
//    Copyright (c) 2019-2023 KriolOS
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
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializerRead;

/**
 *
 * @author Jack
 * @version
 */
public class ProductInfo implements IKeyed {

    private static final long serialVersionUID = 8712449444103L;
    private String id;
    private String ref;
    private String code;
    private String codetype;
    private String name;
    private double priceBuy;
    private double priceSell;
    private String categoryId;
    private String taxCategoryId; //Tax Category ID

    /**
     * Creates new ProductInfo
     *
     * @param id
     * @param ref
     * @param code
     * @param name
     */
    public ProductInfo(String id, String ref, String code, String name) {
        this.id = id;
        this.ref = ref;
        this.code = code;
        this.name = name;
    }

    /**
     *
     * @return
     */
    @Override
    public Object getKey() {
        return id;
    }

    /**
     *
     * @param sID
     */
    public void setID(String sID) {
        id = sID;
    }

    public String getID() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getRef() {
        return ref;
    }

    public void setRef(String sRef) {
        ref = sRef;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    public void setCode(String sCode) {
        code = sCode;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String sName) {
        name = sName;
    }

    public double getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(double m_dPriceBuy) {
        this.priceBuy = m_dPriceBuy;
    }
    
    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String m_sCodetype) {
        this.codetype = m_sCodetype;
    }

    public double getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(double m_dPriceSell) {
        this.priceSell = m_dPriceSell;
    }

    public String getCategoryID() {
        return categoryId;
    }

    public void setCategoryID(String categoryid) {
        this.categoryId = categoryid;
    }

    public String getTaxID() {
        return taxCategoryId;
    }

    public void setTaxID(String taxcategoryid) {
        this.taxCategoryId = taxcategoryid;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     *
     * @return
     */
    public static SerializerRead<ProductInfo> getSerializerRead() {
        return new SerializerRead<ProductInfo>() {
            @Override
            public ProductInfo readValues(DataRead dr) throws BasicException {
                ProductInfo prod = new ProductInfo(
                        dr.getString(1),
                        dr.getString(2),
                        dr.getString(3),
                        dr.getString(5));

                prod.setCodetype(dr.getString(4));
                prod.setPriceBuy(dr.getDouble(6));
                prod.setPriceSell(dr.getDouble(7));
                prod.setCategoryID(dr.getString(8));
                prod.setTaxID(dr.getString(9));

                return prod;
            }
        };
    }
}
