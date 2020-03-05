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

/**
 *
 * @author yaqiang
 */
public class DichotomousMethod extends VerifyMethod implements Cloneable{
    // <editor-fold desc="Variables">
    private DataRange dataRange;
    // </editor-fold>
    // <editor-fold desc="Constructor">
    /**
     * Constructor
     */
    public DichotomousMethod(){
        this.setMethodType(MethodType.DICHOTOMOUS);
        this.dataRange = new DataRange();
    }
    
    /**
     * Constructor
     * @param min Minimum threshold
     * @param max Maximum threshold
     */
    public DichotomousMethod(Double min, Double max){
        this.setMethodType(MethodType.DICHOTOMOUS);
        this.dataRange = new DataRange(min, max);
    }
    
    /**
     * Constructor
     * @param drange DataRange 
     */
    public DichotomousMethod(DataRange drange){
        this.setMethodType(MethodType.DICHOTOMOUS);
        this.dataRange = drange;
    }
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">
    /**
     * Get data range
     *
     * @return Data range
     */
    public DataRange getDataRange() {
        return this.dataRange;
    }

    /**
     * Set data range
     * @param value Data range
     */
    public void setDataRange(DataRange value){
        this.dataRange = value;
    }
    // </editor-fold>
    // <editor-fold desc="Methods">
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }    
    
    // </editor-fold>
}
