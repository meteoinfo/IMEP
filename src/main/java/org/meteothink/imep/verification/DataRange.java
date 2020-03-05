/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.meteothink.imep.verification;

import java.util.List;

/**
 *
 * @author yaqiang
 */
public class DataRange implements Cloneable {
    // <editor-fold desc="Variables">

    private Double minThreshold = null;
    private Double maxThreshold = null;
    private boolean minEqual = true;
    private boolean maxEqual = true;
    private boolean minMax = true;
    private List<Number> values = null;

    // </editor-fold>
    // <editor-fold desc="Constructor">    
    /**
     * Constructor
     */
    public DataRange() {

    }

    /**
     * Constructor
     *
     * @param min Minimum threshold
     * @param max Maximum threshold
     */
    public DataRange(Double min, Double max) {
        this.minThreshold = min;
        this.maxThreshold = max;
    }
    
    /**
     * Constructor
     *
     * @param min Minimum threshold
     * @param max Maximum threshold
     * @param minEqual Mininum equal
     * @param maxEqual Maximum equal
     */
    public DataRange(Double min, Double max, boolean minEqual, boolean maxEqual) {
        this.minThreshold = min;
        this.maxThreshold = max;
        this.minEqual = minEqual;
        this.maxEqual = maxEqual;
    }

    /**
     * Constructor
     *
     * @param values Data values
     */
    public DataRange(List<Number> values) {
        this.values = values;
        this.minMax = false;
    }

    // </editor-fold>
    // <editor-fold desc="Get Set Methods">
    /**
     * If is single threshold
     *
     * @return Boolean
     */
    public boolean isSingleThreshold() {
        if (this.minThreshold == null || this.maxThreshold == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get minimum threshold
     *
     * @return Minimum threshold
     */
    public Double getMinThreshold() {
        return this.minThreshold;
    }

    /**
     * Set minimum threshold
     *
     * @param value Minimum threshold
     */
    public void setMinThreshold(Double value) {
        this.minThreshold = value;
        this.minMax = true;
    }

    /**
     * Get maximum threshold
     *
     * @return Maximum threshold
     */
    public Double getMaxThreshold() {
        return this.maxThreshold;
    }

    /**
     * Set maximum threshold
     *
     * @param value Maximum threshold
     */
    public void setMaxThreshold(Double value) {
        this.maxThreshold = value;
        this.minMax = true;
    }
    
    /**
     * Get if min equal
     * @return Boolean
     */
    public boolean isMinEqual(){
        return this.minEqual;
    }
    
    /**
     * Set if min equal
     * @param value Boolean
     */
    public void setMinEqual(boolean value){
        this.minEqual = value;
    }
    
    /**
     * Get if max equal
     * @return Boolean
     */
    public boolean isMaxEqual(){
        return this.maxEqual;
    }
    
    /**
     * Set if max equal
     * @param value Boolean
     */
    public void setMaxEqual(boolean value){
        this.maxEqual = value;
    }

    /**
     * Get if is Minimum and Maximum
     *
     * @return Boolean
     */
    public boolean isMinMax() {
        return this.minMax;
    }

    /**
     * Set if is Minimum and Maximum
     *
     * @param value Boolean
     */
    public void setMinMax(boolean value) {
        this.minMax = value;
    }

    /**
     * Get values
     *
     * @return Values
     */
    public List<Number> getValues() {
        return this.values;
    }

    /**
     * Set values
     *
     * @param value Values
     */
    public void setValues(List<Number> value) {
        this.values = value;
        this.minMax = false;
    }
    // </editor-fold>
    // <editor-fold desc="Methods">

    /**
     * If is a value in the data range
     *
     * @param value The value
     * @return Boolean
     */
    public boolean isValueIn(double value) {
        boolean valueIn = false;
        if (this.minMax) {
            if (this.minThreshold == null) {                
                if (value < this.maxThreshold) {
                    valueIn = true;
                } else {
                    if (this.maxEqual){
                        if (value == this.maxThreshold)
                            valueIn = true;
                    }
                }                
            } else if (this.maxThreshold == null) {
                if (value > this.minThreshold) {
                    valueIn = true;
                } else {
                    if (this.minEqual){
                        if (value == this.minThreshold)
                            valueIn = true;
                    }
                }
            } else {
                if (value > this.minThreshold && value < this.maxThreshold) {
                    valueIn = true;
                } else {
                    if (this.minEqual){
                        if (value == this.minThreshold)
                            valueIn = true;
                    }
                    if (this.maxEqual){
                        if (value == this.maxThreshold)
                            valueIn = true;
                    }
                }
            }
        } else {
            valueIn = this.values.contains(value);
        }

        return valueIn;
    }

    /**
     * To string
     *
     * @return The string
     */
    @Override
    public String toString() {
        String str = null;
        if (this.minMax) {
            if (this.maxThreshold == null) {
                str = ">" + String.valueOf(this.minThreshold);
            } else if (this.minThreshold == null) {
                str = "<" + String.valueOf(this.maxThreshold);
            } else {
                str = String.valueOf(this.minThreshold) + "-" + String.valueOf(this.maxThreshold);
            }
        } else {
            str = "in";
            for (Number v : this.values){
                str = str + " " + v.toString();
            }
        }

        return str;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    // </editor-fold>
}
