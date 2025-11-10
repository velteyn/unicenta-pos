//    KriolOS POS
//    Copyright (c) 2019-2023 KriolOS
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
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.data.gui;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.Finder;
import java.util.regex.*;

/**
 *
 * @author JG uniCenta
 */
public class FindInfo<T> implements Finder<T> {
    
    /**
     * MATCH TYPE
     */
    public static final int MATCH_STARTFIELD = 0;
    public static final int MATCH_WHOLEFIELD = 1;
    public static final int MATCH_ANYPARTFIELD = 2;
    public static final int MATCH_REGEXP = 3;
    
    private String m_sTextCompare;
    private Pattern m_TextPattern;
    
    private String searchText; // Search Text/Keyword
    private int searchField;   // Search Field
    private int searchMatchType;   // MATCH TYPE
    private boolean searchIsCaseSensivite; // lowercase / uppercase
    
    private Vectorer vectorerData;
    
    /** Creates a new instance of FindInfo
     * @param vec
     * @param sText
     * @param iField
     * @param iMatch
     * @param bMatchCase */
    public FindInfo(Vectorer vec, String sText, int iField, boolean bMatchCase, int iMatch) {
        vectorerData = vec;
        searchText = sText;
        searchField = iField;
        searchIsCaseSensivite = bMatchCase;
        searchMatchType = iMatch;
        
        if (iMatch == MATCH_REGEXP) {          
            m_TextPattern = searchIsCaseSensivite 
                ? Pattern.compile(searchText) 
                : Pattern.compile(searchText, Pattern.CASE_INSENSITIVE);
        } else {
            m_sTextCompare = searchIsCaseSensivite
                ? searchText
                : searchText.toUpperCase();
        }
    }
    
    /** Creates a new instance of FindInfo
     * @param vec */
    public FindInfo(Vectorer vec) {
        this(vec,  "", 0, true, MATCH_ANYPARTFIELD);
    }
    
    /**
     *
     * @return
     */
    public Vectorer getVectorer() {
        return vectorerData;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return searchText;
    }

    /**
     *
     * @return
     */
    public int getField() {
        return searchField;
    }

    /**
     *
     * @return
     */
    public boolean isMatchCase() {
        return searchIsCaseSensivite;
    }

    /**
     *
     * @return
     */
    public int getMatch() {
        return searchMatchType;
    }
   
    /**
     *
     * @param obj
     * @return
     * @throws BasicException
     */
    @Override
    public boolean match(T obj) throws BasicException {
        
        String[] v = vectorerData.getValues(obj);
        
        String sField = searchIsCaseSensivite
            ? v[searchField]
            : v[searchField].toUpperCase();
        
        switch (searchMatchType) {
        case MATCH_STARTFIELD:
            return sField.startsWith(m_sTextCompare);
        case MATCH_WHOLEFIELD:
            return sField.equals(m_sTextCompare);
        case MATCH_ANYPARTFIELD:   
            return sField.contains(m_sTextCompare);
        case MATCH_REGEXP:
            return m_TextPattern.matcher(sField).matches();
        default:
            return false;
        }       
    }
    
}
