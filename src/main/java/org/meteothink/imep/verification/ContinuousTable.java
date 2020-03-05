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
import java.util.List;
import java.util.Map;

/**
 *
 * @author yaqiang
 */
public class ContinuousTable extends VerifyTable{
    // <editor-fold desc="Variables">
    private double[] _fcstValues;
    private double[] _obsValues;
    // </editor-fold>
    // <editor-fold desc="Constructor">
    /**
     * Constructor
     */
    public ContinuousTable(){
        
    }
    
    /**
     * Constructor
     * @param obs Observation data
     * @param fcst Forecast data
     */
    public ContinuousTable(double[] obs, double[] fcst){
        this._obsValues = obs;
        this._fcstValues = fcst;
    }
    
   /**
     * Constructor
     * @param obs Observation data
     * @param fcst Forecast data
     */
    public ContinuousTable(List<Number> obs, List<Number> fcst){
        int n = obs.size();
        this._obsValues = new double[n];
        this._fcstValues = new double[n];
        for (int i = 0; i < n; i++){
            this._obsValues[i] = obs.get(i).doubleValue();
            this._fcstValues[i] = fcst.get(i).doubleValue();
        }
    }
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">
    /**
     * Get forecast values
     * @return Forecast values
     */
    public double[] getFcstValues(){
        return this._fcstValues;
    }
    
    /**
     * Set forecast values
     * @param value Forecast values
     */
    public void setFcstValues(double[] value){
        this._fcstValues = value;
    }
    
    /**
     * Get observation values
     * @return Observation values
     */
    public double[] getObsValues(){
        return this._obsValues;
    }
    
    /**
     * Set observation values
     * @param value Observation values
     */
    public void setObsValues(double[] value){
        this._obsValues = value;
    }
    
    /**
     * Get data number
     * @return Data number
     */
    public int getDataNum(){
        return this._fcstValues.length;
    }
    // </editor-fold>
    // <editor-fold desc="Methods">
    /**
     * Get sum of forecast value
     * @return Sum forecast value
     */
    public double getFsctSum(){
        int n = this.getDataNum();
        double sum = 0.0;
        for (double v : this._fcstValues)
            sum += v;
        
        return sum;
    }
    
    /**
     * Get sum of observation value
     * @return Sum observation value
     */
    public double getObsSum(){
        int n = this.getDataNum();
        double sum = 0.0;
        for (double v : this._obsValues)
            sum += v;
        
        return sum;
    }
    
    /**
     * Get mean forecast value
     * @return Mean forecast value
     */
    public double getFcstMean(){
        int n = this._fcstValues.length;
        double mean = 0.0;
        for (double v : this._fcstValues)
            mean += v;
        mean = mean / n;
        
        return mean;
    }
    
     /**
     * Get mean observation value
     * @return Mean observation value
     */
    public double getObsMean(){
        int n = this._fcstValues.length;
        double mean = 0.0;
        for (double v : this._obsValues)
            mean += v;
        mean = mean / n;
        
        return mean;
    }
    
    /**
     * Get mean error
     * What is the average forecast error?
     * Range: -∞ to ∞. Perfect score: 0.
     * @return Mean error
     */
    public float getMeanError(){
        int n = this.getDataNum();
        double sum = 0.0;
        for (int i = 0; i < n; i++){
            sum += this._fcstValues[i] - this._obsValues[i];
        }
        
        return (float)sum / n;
    }
    
    /**
     * Get bias
     * How does the average forecast magnitude compare to the average observed magnitude?
     * Range: -∞ to ∞. Perfect score: 1.
     * @return Bias
     */
    public float getBias(){
        int n = this.getDataNum();
        double sumf = 0.0;
        double sumo = 0.0;
        for (int i = 0; i < n; i++){
            sumf += this._fcstValues[i];
            sumo += this._obsValues[i];
        }
        sumf = sumf / n;
        sumo = sumo / n;
        
        return (float)(sumf / sumo);
    }
    
    /**
     * Get mean absolute error
     * What is the average magnitude of the forecast error?
     * Range: 0 to ∞. Perfect score: 0.
     * @return Mean absolute error
     */
    public float getMAE(){
        int n = this.getDataNum();
        double sum = 0.0;
        for (int i = 0; i < n; i++){
            sum += Math.abs(this._fcstValues[i] - this._obsValues[i]);
        }
        
        return (float)sum / n;
    }
    
    /**
     * Get root mean square error
     * What is the average magnitude of the forecast errors?
     * 0 to ∞.  Perfect score: 0.
     * @return Root mean square error
     */
    public float getRMSE(){
        int n = this.getDataNum();
        double sum = 0.0;
        for (int i = 0; i < n; i++){
            sum += Math.pow(this._fcstValues[i] - this._obsValues[i], 2);
        }
        sum = sum / n;        
        
        return (float)Math.sqrt(sum);
    }
    
    /**
     * Get mean square error
     * Measures the mean squared difference between the forecasts and observations.
     * 0 to ∞.  Perfect score: 0.
     * @return Mean square error
     */
    public float getMSE(){
        int n = this.getDataNum();
        double sum = 0.0;
        for (int i = 0; i < n; i++){
            sum += Math.pow(this._fcstValues[i] - this._obsValues[i], 2);
        }
        sum = sum / n;        
        
        return (float)sum;
    }
    
    /**
     * Get correlation coefficient
     * How well did the forecast values correspond to the observed values?
     * Range: -1 to 1.  Perfect score: 1.
     * @return Correlation coefficent
     */
    public float getR_bak(){
        int n = this.getDataNum();
        double meanFsct = this.getFcstMean();
        double meanObs = this.getObsMean();
        double sum = 0.0;
        double sumf = 0.0;
        double sumo = 0.0;
        for (int i = 0; i < n; i++){
            sum += (this._fcstValues[i] - meanFsct) * (this._obsValues[i] - meanObs);
            sumf += Math.pow(this._fcstValues[i] - meanFsct, 2);
            sumo += Math.pow(this._obsValues[i] - meanObs, 2);
        }
        
        sumf = Math.sqrt(sumf);
        sumo = Math.sqrt(sumo);
        return (float)(sum / (sumf * sumo));
    } 
    
    /**
     * Get correlation coefficient
     * How well did the forecast values correspond to the observed values?
     * Range: -1 to 1.  Perfect score: 1.
     * @return Correlation coefficent
     */
    public float getR(){
        int n = this.getDataNum();
        double x_sum = this.getObsSum();
        double y_sum = this.getFsctSum();
        double sx_sum = 0.0;
        double sy_sum = 0.0;
        double xy_sum = 0.0;
        for (int i = 0; i < n; i++){
            sx_sum += _obsValues[i] * _obsValues[i];
            sy_sum += _fcstValues[i] * _fcstValues[i];
            xy_sum += _obsValues[i] * _fcstValues[i];
        }
        
        double r = (n * xy_sum - x_sum * y_sum) / (Math.sqrt(n * sx_sum - x_sum * x_sum) * Math.sqrt(n * sy_sum - y_sum * y_sum));
        return (float)r;
    } 

    /**
     * Get verify result
     * @return Verify result map
     */
    @Override
    public Map getVerifyResult() {
        Map map = new HashMap();
        map.put("MeanErr", this.getMeanError());
        map.put("Bias", this.getBias());
        map.put("MAE", this.getMAE());
        map.put("MSE", this.getMSE());
        map.put("RMSE", this.getRMSE());
        map.put("R", this.getR());        
        
        return map;
    }
    
    @Override
    public MethodType getMethodType(){
        return MethodType.CONTINUOUS;
    }
    
    @Override
    public String getScoreNames(){
        return "MeanErr,Bias,MAE,MSE,RMSE,R";
    }
    
    // </editor-fold>
}
