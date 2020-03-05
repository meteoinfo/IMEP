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

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yaqiang
 */
public class MultiCategoryTable extends VerifyTable{
    // <editor-fold desc="Variables">
    private int[][] _values;
    // </editor-fold>
    // <editor-fold desc="Constructor">
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">
    /**
     * Get values
     * @return Values
     */
    public int[][] getValues(){
        return this._values;
    }
    
    /**
     * Set values
     * @param value Values
     */
    public void setValues(int[][] value){
        this._values = value;
    }
    // </editor-fold>
    // <editor-fold desc="Methods">
    /**
     * Get category number
     * @return Category number
     */
    public int getCategoryNum(){
        return this._values.length;
    }
    
    /**
     * Get total number
     * @return Total number
     */
    public int getTotal(){
        int n = 0;
        int len = this.getCategoryNum();
        for (int i = 0; i < len; i++){
            for (int j = 0; j < len; j++){
                n += this._values[i][j];
            }
        }
        
        return n;
    }
    
    /**
     * Get fit number
     * @return Fit number
     */
    public int getFitNum(){
        int n = 0;
        int len = this.getCategoryNum();
        for (int i = 0; i < len; i++){
            n += this._values[i][i];
        }
        
        return n;
    }
    
    /**
     * Get forecast category number
     * @param idx Category index
     * @return Forecast category number
     */
    public int getFcstCategoryNum(int idx){
        int n = 0;
        int len = this.getCategoryNum();
        for (int i = 0; i < len; i++){
            n += this._values[idx][i];
        }
        
        return n;
    }
    
    /**
     * Get observation category number
     * @param idx Category index
     * @return Observation category number
     */
    public int getObsCategoryNum(int idx){
        int n = 0;
        int len = this.getCategoryNum();
        for (int i = 0; i < len; i++){
            n += this._values[i][idx];
        }
        
        return n;
    }
    
    /**
     * Get accuracy
     * Overall, what fraction of the forecasts were in the correct category?
     * Range: 0 to 1.  Perfect score: 1.
     * @return Accuracy
     */
    public float getAccuracy(){
        int nfit = this.getFitNum();
        int n = this.getTotal();
        
        return (float)nfit / n;
    }
    
    /**
     * Get Heidke skill score
     * What was the accuracy of the forecast in predicting the correct category, 
     * relative to that of random chance?
     * Range: -∞ to 1, 0 indicates no skill.  Perfect score: 1.
     * @return Heidke skill score
     */
    public float getHSS(){
        float accuracy = this.getAccuracy();
        int n = this.getTotal();
        int sum = 0;
        int len = this.getCategoryNum();
        for (int i = 0; i < len; i++){
            sum += this.getFcstCategoryNum(i) * this.getObsCategoryNum(i);
        }
        float tt = (float)sum / (n * n);
        
        return (accuracy - tt) / (1.0f - tt);
    }
    
    /**
     * Hanssen and Kuipers discriminant (true skill statistic, Peirce's skill score)
     * What was the accuracy of the forecast in predicting the correct category, relative to that of random chance?
     * Range: -1 to 1, 0 indicates no skill. Perfect score: 1
     * @return 
     */
    public float getHK(){
        float accuracy = this.getAccuracy();
        int n = this.getTotal();
        int sum = 0;
        int sum1 = 0;
        int len = this.getCategoryNum();
        for (int i = 0; i < len; i++){
            sum += this.getFcstCategoryNum(i) * this.getObsCategoryNum(i);
            sum1 += this.getObsCategoryNum(i) * this.getObsCategoryNum(i);
        }
        float tt = (float)sum / (n * n);
        float tt1 = (float)sum1 / (n * n);
        
        return (accuracy - tt) / (1.0f - tt1);
    }
    
//    public float getGS(){
//        
//    }
    
    /**
     * Get verify result
     * @return Verify result map
     */
    @Override
    public Map getVerifyResult() {
        Map map = new HashMap();
        map.put("Accuracy", this.getAccuracy());
        map.put("HSS", this.getHSS());
        map.put("HK", this.getHK());     
        
        return map;
    }
    
    @Override
    public MethodType getMethodType(){
        return MethodType.MULTICATEGORY;
    }
    
    @Override
    public String getScoreNames(){
        return "Accuracy,HSS,HK";
    }
    // </editor-fold>
}
