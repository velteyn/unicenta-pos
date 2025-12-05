/*
 * Copyright (C) 2025 KriolOS
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
package com.openbravo.pos.domain.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author poolborges
 */
public class AmountCalculatorUtilTest {
    
    @ParameterizedTest(name = "Test calcule Price MARKUP with: sell price {0}, buy price {1}, expected markup {2}")
    @CsvSource({
        //SellPrice, BuyPrice, Markup
        "50.0, 20.0, 1.5",     
        "50.0, 0.0, 1.0",         
        "0.0, 0.0, 0.0",          
        "0.0, 20.0, -1.0",        
    })
    void testCalculateMarkup(double priceShell, double priceBuy, double markup){
        assertEquals(markup, AmountCalculatorUtil.calcMarkupPercentage(priceShell, priceBuy));
    }

    
    @ParameterizedTest(name = "Test calcule Profit Margin(Gross Margin) with: sell price {0}, buy price {1}, expected profit margin {2}")
    @CsvSource({
        //SellPrice, BuyPrice, profit margin
        "50.0, 20.0, 0.6",     
        "50.0, 0.0, 1.0",         
        "0.0, 0.0, 0.0",          
        "0.0, 20.0, -1.0",         
        "10.0, 20.0, -1.0",          
        "15.0, 20.0, -0.3333",             
        "30.0, 20.0, 0.3333",     
    })
    void testCalculateProfitMargin(double priceShell, double priceBuy, double markup){
        assertEquals(markup, AmountCalculatorUtil.calcProfitMarginPercentage(priceShell, priceBuy));
    }    
}
