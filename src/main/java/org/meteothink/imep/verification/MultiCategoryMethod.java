/* Copyright 2013 Yaqiang Wang,
 * yaqiang.wang@gmail.com
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 */
package org.meteothink.imep.verification;

import java.util.List;

/**
 *
 * @author yaqiang
 */
public class MultiCategoryMethod extends VerifyMethod implements Cloneable{
    // <editor-fold desc="Variables">
    private double[] _categoryValues;
    // </editor-fold>
    // <editor-fold desc="Constructor">
    /**
     * Constructor
     */
    public MultiCategoryMethod(){
        this.setMethodType(MethodType.MULTICATEGORY);
    }
    
    /**
     * Constructor
     * @param value Values
     */
    public MultiCategoryMethod(List<Number> value){
        this.setMethodType(MethodType.MULTICATEGORY);
        this._categoryValues = new double[value.size()];
        for (int i = 0; i < value.size(); i++){
            this._categoryValues[i] = value.get(i).doubleValue();
        }
    }
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">
    /**
     * Get category values
     * @return Category values
     */
    public double[] getCategoryValues(){
        return this._categoryValues;
    }
    
    /**
     * Set category values
     * @param value Category values
     */
    public void setCategoryValues(double[] value){
        this._categoryValues = value;
    }
    
    /**
     * Set category values
     * @param value Category values
     */
    public void setCategoryValues(List<Number> value){
        this._categoryValues = new double[value.size()];
        for (int i = 0; i < value.size(); i++){
            this._categoryValues[i] = value.get(i).doubleValue();
        }
    }
    
    /**
     * Get category number
     * @return Category number
     */
    public int getCategoryNum(){
        return this._categoryValues.length + 1;
    }
    // </editor-fold>
    // <editor-fold desc="Methods">
    /**
     * Convert values to string with semicolon dilimiter
     * @return String
     */
    public String valuesToString(){
        String str = String.valueOf(this._categoryValues[0]);
        for (int i = 1; i < this._categoryValues.length; i++){
            str = str + ";" + String.valueOf(this._categoryValues[i]);
        }
        return str;
    }
    
    /**
     * Set values by a string with semicolon dilimiter
     * @param str value string
     */
    public void setValues(String str){
        str = str.trim();
        String[] vstrs = str.split(";");
        this._categoryValues = new double[vstrs.length];
        for (int i = 0; i < vstrs.length; i++){
            this._categoryValues[i] = Double.parseDouble(vstrs[i]);
        }        
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    // </editor-fold>
}
