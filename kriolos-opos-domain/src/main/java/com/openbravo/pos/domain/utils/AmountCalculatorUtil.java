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

import com.openbravo.pos.ticket.TaxInfo;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A utility class for calculating prices with and without tax.
 * It uses BigDecimal for precision in financial calculations and handles tax rates as decimals
 * (e.g., 0.2 for 20%).
 * 
 * @author poolborges
 */
public abstract class AmountCalculatorUtil {

    // Define the scale (number of decimal places) for financial calculations
    private static final int SCALE = 4;
    // Define the rounding mode
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    

    
    /**
     * Round Double up to 4 decimal, using HALF_UP Rounding Mode
     *
     * Use BigDecimal. Formula (value/1).scale(4).rounding(HALF_UP)
     *
     * @param value value to be round up to 4 decimal, .
     * @return value rounded.
     */
    public static double roundValue(double value) {
        BigDecimal baseValue = new BigDecimal(String.valueOf(value));

        // Calculate rounded = baseValue / 1
        BigDecimal netPrice = baseValue.divide(BigDecimal.ONE, SCALE, ROUNDING_MODE);

        return netPrice.doubleValue();
    }

    /**
     * Calculates the price without tax (net price) given the tax-inclusive price and the tax rate.
     *
     * Formula: Net Price = Gross Price / (1 + Tax Rate)
     *
     * @param priceWithTaxInclusive The total price including tax.
     * @param taxRate The tax rate as a decimal (e.g., 0.2 for 20%).
     * @return The price without tax.
     */
    public static double calcPriceWithoutTax(double priceWithTaxInclusive, double taxRate) {
        BigDecimal grossPrice = new BigDecimal(String.valueOf(priceWithTaxInclusive));
        BigDecimal rate = new BigDecimal(String.valueOf(taxRate));

        // Calculate (1 + Tax Rate)
        BigDecimal taxInclusive = rate.add(BigDecimal.ONE);

        // Calculate Net Price = Gross Price / taxMultiplier
        BigDecimal netPrice = grossPrice.divide(taxInclusive, SCALE, ROUNDING_MODE);

        return netPrice.doubleValue();
    }
    
    private static double getTaxRate(TaxInfo taxInfo){
        return taxInfo == null ? 0.0 : taxInfo.getRate();
    }
    
    /**
     * {@link #calcPriceWithoutTax(double, double)}
     * 
     * @param priceWithTaxInclusive
     * @param taxInfo
     * @return The price without tax
     */
    public static double calcPriceWithoutTax(double priceWithTaxInclusive, TaxInfo taxInfo) {
        double taxRate = getTaxRate(taxInfo);
        return calcPriceWithoutTax(priceWithTaxInclusive, taxRate);
    }

    /**
     * Calculates the price with tax inclusive (gross price) given the price without tax and the tax rate.
     *
     * Formula: Gross Price = Net Price * (1 + Tax Rate)
     *
     * @param priceWithoutTax The price before tax is added.
     * @param taxRate The tax rate as a decimal (e.g., 0.2 for 20%).
     * @return The total price including tax.
     */
    public static double calcPriceWithTaxInclusive(double priceWithoutTax, double taxRate) {
        BigDecimal netPrice = new BigDecimal(String.valueOf(priceWithoutTax));
        BigDecimal rate = new BigDecimal(String.valueOf(taxRate));

        // Calculate (1 + Tax Rate)
        BigDecimal taxMultiplier = rate.add(BigDecimal.ONE);

        // Calculate Gross Price = Net Price * taxMultiplier
        BigDecimal grossPrice = netPrice.multiply(taxMultiplier);

        return grossPrice.doubleValue();
    }
    
    /**
     * {@link #calcPriceWithTaxInclusive(double, double)}
     * 
     * @param priceWithoutTax
     * @param taxInfo
     * @return The total price including tax
     */
    public static double calcPriceWithTaxInclusive(double priceWithoutTax, TaxInfo taxInfo) {
        double taxRate = getTaxRate(taxInfo);
        return calcPriceWithTaxInclusive(priceWithoutTax, taxRate);
    }
    
    /**
     * Calculates the tax amount in currency value given the price without tax and the tax rate.
     *
     * Formula: Tax Amount = Net Price * Tax Rate
     *
     * @param price The price.
     * @param multiply multiply (can be quantity: number of items, weight) 
     * @return The monetary subtotal.
     */
    public static double calcSubTotalAmount(double price, double multiply) {
        BigDecimal netPrice = new BigDecimal(String.valueOf(price));
        BigDecimal multiplyBD = new BigDecimal(String.valueOf(multiply));

        // Calculate Tax Amount = Net Price * Tax Rate
        BigDecimal taxAmount = netPrice.multiply(multiplyBD);

        return taxAmount.doubleValue();
    }

    /**
     * Calculates the tax amount in currency value given the price without tax and the tax rate.
     *
     * Formula: Tax Amount = Net Price * Tax Rate
     *
     * @param priceWithoutTax The price before tax is added.
     * @param taxRate The tax rate as a decimal (e.g., 0.2 for 20%).
     * @return The monetary value of the tax amount.
     */
    public static double calcTaxAmount(double priceWithoutTax, double taxRate) {
        BigDecimal netPrice = new BigDecimal(String.valueOf(priceWithoutTax));
        BigDecimal rate = new BigDecimal(String.valueOf(taxRate));

        // Calculate Tax Amount = Net Price * Tax Rate
        BigDecimal taxAmount = netPrice.multiply(rate);

        return taxAmount.doubleValue();
    }
    
    /**
     * {@link #calcTaxAmount(double, double)}
     * 
     * @param priceWithoutTax
     * @param taxInfo
     * @return The monetary value of the tax amount.
     */
    public static double calcTaxAmount(double priceWithoutTax, TaxInfo taxInfo) {
        double taxRate = getTaxRate(taxInfo);
        return calcTaxAmount(priceWithoutTax, taxRate);
    }
    
    /**
     * Calculates the price margin percentage (e.g 0.2 = 20%).
     *
     * Formula: (Sell Price / Buy Price) - 1.0
     *
     * @param sellPrice The sell price
     * @param buyPrice The buy price
     * @return The margin percentage in decimal.
     */
    public static double calcMarginPercentage(double sellPrice, double buyPrice) {
        BigDecimal sellPriceBD = new BigDecimal(String.valueOf(sellPrice));
        BigDecimal buyPriceBD = new BigDecimal(String.valueOf(buyPrice));
        
         //AVOID Division by zero Exception
        if(buyPrice == 0.0){
            buyPriceBD = BigDecimal.ONE;
        }

        // (sellPrice / buyPrice) - 1
        BigDecimal marginBD = sellPriceBD.divide(buyPriceBD, SCALE, ROUNDING_MODE).subtract(BigDecimal.ONE);
        


        return marginBD.doubleValue();
    }
    
    /**
     * Calculates the Gross Profit (e.g 0.2 = 20%).
     *
     * Formula: (sellPrice - buyPrice) / sellPrice
     *
     * @param sellPrice The sell price
     * @param buyPrice The buy price
     * @return The margin percentage in decimal.
     */
    public static double calcGrossProfit(double sellPrice, double buyPrice) {
        BigDecimal sellPriceBD = new BigDecimal(String.valueOf(sellPrice));
        BigDecimal buyPriceBD = new BigDecimal(String.valueOf(buyPrice));

        // (sellPrice - buyPrice) / sellPrice
        BigDecimal grossPercentBD = sellPriceBD.subtract(buyPriceBD);

         //AVOID Division by zero Exception
        if(sellPrice != 0.0){
            grossPercentBD = grossPercentBD.divide(sellPriceBD, SCALE, ROUNDING_MODE);
        }

        return grossPercentBD.doubleValue();
    }
    
    /**
     * Calculates the Sell Price with margin (percentage)
     *
     * Formula: Sell Price = Buy Price * (1 + Margin Rate)
     *
     * @param buyPrice The Buy price.
     * @param marginRate The margin rate as a decimal (e.g., 0.2 for 20%).
     * @return The sell price.
     */
    public static double calcSellPriceWithMargin(double buyPrice, double marginRate) {
        BigDecimal buyPriceBD = new BigDecimal(String.valueOf(buyPrice));
        BigDecimal rate = new BigDecimal(String.valueOf(marginRate));

        // Calculate (1 + Margin Rate)
        BigDecimal marginMultiplier = rate.add(BigDecimal.ONE);

        // Calculate Sell Price = Buy Price * marginMultiplier
        BigDecimal sellPrice = buyPriceBD.multiply(marginMultiplier);

        return sellPrice.doubleValue();
    }
    
    public static BigDecimal divide(double quotient, double divisor) {
        BigDecimal quotientBD = new BigDecimal(String.valueOf(quotient));
        BigDecimal divisorBD = new BigDecimal(String.valueOf(divisor));
        
        //AVOID Division by zero Exception
        if(divisor == 0.0){
            divisorBD = BigDecimal.ONE;
        }


        BigDecimal res = quotientBD.divide(divisorBD, SCALE, ROUNDING_MODE);

        return res;
    }
    
    public static BigDecimal subtract(double num1, double num2) {
        BigDecimal num1BD = new BigDecimal(String.valueOf(num1));
        BigDecimal num2BD = new BigDecimal(String.valueOf(num2));

        BigDecimal res = num1BD.subtract(num2BD);

        return res;
    }
}

