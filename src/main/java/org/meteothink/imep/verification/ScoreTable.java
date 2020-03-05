/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.meteothink.imep.verification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wyq
 */
public class ScoreTable extends VerifyTable{

    // <editor-fold desc="Variables">
    List<DataRange> obsRanges;
    List<List<Integer>> scores;
    // </editor-fold>
    // <editor-fold desc="Constructor">
    /**
     * Constructor
     * @param obsRanges Observation data range list
     */
    public ScoreTable(List<DataRange> obsRanges){
        this.obsRanges = obsRanges;
        this.scores = new ArrayList<>();
        for (int i = 0; i < obsRanges.size(); i++)
            this.scores.add(new ArrayList<Integer>());
    }
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">
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
     * Add a score
     * @param idx Index
     * @param score Score value
     */
    public void addScore(int idx, int score){
        this.scores.get(idx).add(score);
    }
    
    /**
     * Get mean score
     * @param idx Index
     * @return Mean score
     */
    public float getMeanScore(int idx){
        float ms = 0.0f;        
        for (int score : this.scores.get(idx)){
            ms += score;
        }
        ms = ms / this.scores.get(idx).size();
        return ms;
    }
    
    /**
     * Get mean score
     * @return Mean score
     */
    public float getMeanScore(){
        float ms = 0.0f;        
        int n = 0;
        for (List<Integer> score : this.scores){
            for (int s : score){
                ms += s;
                n += 1;
            }
        }
        ms = ms / n;
        return ms;
    }
    
    @Override
    public Map getVerifyResult() {
        Map map = new HashMap();
        map.put("Summary", this.getMeanScore());
        int i = 0;
        for (DataRange dr : this.obsRanges){
            map.put(dr.toString(), this.getMeanScore(i));
            map.put(dr.toString() + "_n", this.scores.get(i).size());
            i++;
        }
        return map;
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.SCORE;
    }
    
    @Override
    public String getScoreNames(){
        StringBuilder names = new StringBuilder();
        names.append("Summary");
        for (DataRange dr : this.obsRanges){
            names.append(",");
            names.append(dr.toString());
            names.append(",");
            names.append(dr.toString() + "_n");
        }
        return names.toString();
    }
    // </editor-fold>       
}
