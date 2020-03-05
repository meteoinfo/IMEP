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
public abstract class VerifyMethod implements Cloneable {
    // <editor-fold desc="Variables">

    private MethodType _methodType;
    // </editor-fold>
    // <editor-fold desc="Constructor">
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">

    /**
     * Get method type
     *
     * @return Verification method type
     */
    public MethodType getMethodType() {
        return this._methodType;
    }

    /**
     * Set method type
     *
     * @param value Method type
     */
    public void setMethodType(MethodType value) {
        this._methodType = value;
    }
    // </editor-fold>
    // <editor-fold desc="Methods"> 
    
    /**
     * Get score names by method type
     * @param methodType The method type
     * @return The score name array
     */
    public static String[] getScoreNames(MethodType methodType){
        String name = null;
        switch (methodType){
            case DICHOTOMOUS:
                name = "Accuracy,Bias,ETS,FAR,HK,HSS,ORSS,OddsRatio,POD,POFD,SuccessRatio,ThreatScore";
                break;
            case MULTICATEGORY:
                name = "Accuracy,HSS,HK";
                break;
            case CONTINUOUS:
                name = "MeanError,Bias,MAE,MSE,RMSE,R";
                break;
        }
        
        if (name != null){
            return name.split(",");
        } else {
            return null;
        }
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    // </editor-fold>
}
