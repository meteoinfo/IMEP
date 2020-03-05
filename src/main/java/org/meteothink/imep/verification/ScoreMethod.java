/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.meteothink.imep.verification;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wyq
 */
public class ScoreMethod extends VerifyMethod{
    // <editor-fold desc="Variables">
    List<DataRange> obsRanges;
    List<List<DataRange>> fcstRanges;
    List<List<Integer>> scores;
    // </editor-fold>
    // <editor-fold desc="Constructor">
    /**
     * Constructor
     */
    public ScoreMethod(){
        super();
        this.obsRanges = new ArrayList<>();
        this.fcstRanges = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.setMethodType(MethodType.SCORE);
    }
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">
    /**
     * Get observation data ranges
     * @return Observation data ranges
     */
    public List<DataRange> getObsRanges(){
        return this.obsRanges;
    }
    
    /**
     * Set observation data ranges
     * @param value Observation data ranges
     */
    public void setObsRanges(List<DataRange> value){
        this.obsRanges = value;
    }
    
    /**
     * Get forecast data ranges
     * @return Forecast data ranges
     */
    public List<List<DataRange>> getFcstRanges(){
        return this.fcstRanges;
    }
    
    /**
     * Set forecast data ranges
     * @param value Forecast data ranges
     */
    public void setFcstRanges(List<List<DataRange>> value){
        this.fcstRanges = value;
    }
    
    /**
     * Get scores
     * @return Scores
     */
    public List<List<Integer>> getScores(){
        return this.scores;
    }
    
    /**
     * Set scores
     * @param value Scores 
     */
    public void setScores(List<List<Integer>> value){
        this.scores = value;
    }
    // </editor-fold>
    // <editor-fold desc="Methods">
    /**
     * Get forecast data ranges
     * @param i Index i
     * @return Forecast data ranges of index i
     */
    public List<DataRange> getFcstRanges(int i){
        return this.fcstRanges.get(i);
    }
    
    /**
     * Get forecast data range
     * @param i Index i
     * @param j Index j
     * @return Forecast data range
     */
    public DataRange getFcstRange(int i, int j){
        return this.fcstRanges.get(i).get(j);
    }
    
    /**
     * Get scores
     * @param i Index i
     * @return Scores of index i
     */
    public List<Integer> getScores(int i){
        return this.scores.get(i);
    }
    
    /**
     * Get score
     * @param i Index i
     * @param j Index j
     * @return Score
     */
    public int getScore(int i, int j){
        return this.scores.get(i).get(j);
    }
    
    /**
     * Add score
     * @param i Index i
     * @param range Forecast data range
     * @param score Score
     */
    public void setScore(int i, DataRange range, int score){
        this.fcstRanges.get(i).add(range);
        this.scores.get(i).add(score);
    }
    
    /**
     * Add score
     * @param obsdr Observation data range
     * @param fcstdrs Forecast data ranges
     * @param scores Scores
     */
    public void addScore(DataRange obsdr, List<DataRange> fcstdrs, List<Integer> scores){
        this.obsRanges.add(obsdr);
        this.fcstRanges.add(fcstdrs);
        this.scores.add(scores);
    }
    
    /**
     * Add observation data range
     * @param obsdr Observation data range
     */
    public void addObsRange(DataRange obsdr){
        this.obsRanges.add(obsdr);
    }
    
    /**
     * Get observation data range index
     * @param ov Observation value
     * @return Index
     */
    public int getObsRangeIdx(double ov){
        int idx = -1;
        for (int i = 0; i < this.obsRanges.size(); i++){
            if (this.obsRanges.get(i).isValueIn(ov)){
                idx = i;
                break;
            }
        }
        return idx;
    }
    
    /**
     * Get score
     * @param idx Observation data range index
     * @param fv Forecast value
     * @return Score
     */
    public int getScore(int idx, double fv){
        List<DataRange> drs = this.getFcstRanges(idx);
        List<Integer> ss = this.getScores(idx);
        int score = 0;
        for (int i = 0; i < drs.size(); i++){
            if (drs.get(i).isValueIn(fv)){
                score = ss.get(i);
                break;
            }
        }
        
        return score;
    }
    // </editor-fold>
}
