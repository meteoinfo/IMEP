/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.meteothink.imep.verification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.meteoinfo.data.StationData;

/**
 *
 * @author yaqiang
 */
public class ContingencyTable extends VerifyTable {

    // <editor-fold desc="Variables">

    public int hit = 0;
    public int miss = 0;
    public int falseAlarm = 0;
    public int correctNegative = 0;

    // </editor-fold>
    // <editor-fold desc="Constructor">

    /**
     * Constructor
     */
    public ContingencyTable() {

    }

    /**
     * Constructor
     *
     * @param obsData Observation data list
     * @param fcstData Forecast data list
     * @param min Minimum threshold
     * @param max Maximum threshold
     */
    public ContingencyTable(List<Number> obsData, List<Number> fcstData, Double min, Double max) {
        DichotomousMethod method = new DichotomousMethod(min, max);
        ContingencyTable table = (ContingencyTable) VerifyStat.getVerifyTable(obsData, fcstData, method);
        this.hit = table.hit;
        this.miss = table.miss;
        this.falseAlarm = table.falseAlarm;
        this.correctNegative = table.correctNegative;
    }
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">
    // </editor-fold>
    // <editor-fold desc="Methods">

    /**
     * Get total number
     *
     * @return Total number
     */
    public int getTotal() {
        return hit + miss + falseAlarm + correctNegative;
    }

    /**
     * Get observation yes number
     *
     * @return Observation yes number
     */
    public int getObsYes() {
        return hit + miss;
    }

    /**
     * Get observation no number
     *
     * @return Observation no number
     */
    public int getObsNo() {
        return falseAlarm + correctNegative;
    }

    /**
     * Get forecast yes number
     *
     * @return Forecast yes number
     */
    public int getForecastYes() {
        return hit + falseAlarm;
    }

    /**
     * Get forecast no number
     *
     * @return Forecast no number
     */
    public int getForecastNo() {
        return miss + correctNegative;
    }

    /**
     * Get accuracy value Overall, what fraction of the forecast were correct?
     * Range: 0 to 1. Perfect score: 1.
     *
     * @return Accuracy value
     */
    public float getAccuracy() {
        return (hit + correctNegative) / (float) this.getTotal();
    }

    /**
     * Get bias score How did the forecast frequency of "yes" events compare to
     * the observed frequency of "yes" events? Range: 0 to ∞. Perfect score: 1.
     *
     * @return Bias score
     */
    public float getBias() {
        return (hit + falseAlarm) / (float) (hit + miss);
    }

    /**
     * Get probability of detection (hit rate) What fraction of the observed
     * "yes" events were correctly forecast? Range: 0 to 1. Perfect score: 1.
     *
     * @return Probability of detection
     */
    public float getPOD() {
        return hit / (float) (hit + miss);
    }

    /**
     * Get false alarm ratio What fraction of the predicted "yes" events
     * actually did not occur? Range: 0 to 1. Perfect score: 0.
     *
     * @return
     */
    public float getFAR() {
        return falseAlarm / (float) (hit + falseAlarm);
    }

    /**
     * Get probability of false detection (false alarm rate) What fraction of
     * the observed "no" event were incorrectly forecast as "yes"? Range: 0 to
     * 1. Perfect score: 0.
     *
     * @return Probability of false detection
     */
    public float getPOFD() {
        return falseAlarm / (float) (correctNegative + falseAlarm);
    }

    /**
     * Get success ratio What fraction of the forecast "yes" events were
     * correctly observed? Range: 0 to 1. Perfect score: 1.
     *
     * @return Success ratio
     */
    public float getSuccessRatio() {
        return hit / (float) (hit + falseAlarm);
    }

    /**
     * Get threat score (critical success index) How well did the forecast "yes"
     * events correspond to the observed "yes" events? Range: 0 to 1, 0
     * indicates no skill. Perfect score: 1.
     *
     * @return
     */
    public float getThreatScore() {
        return hit / (float) (hit + miss + falseAlarm);
    }

    /**
     * Get equitable threat score (Gilbert skill score) How well did the
     * forecast "yes" events correspond to the observed "yes" events (accounting
     * for hits due to chance)? Range: -1/3 to 1, 0 indicates no skill. Perfect
     * score: 1.
     *
     * @return
     */
    public float getETS() {
        float hitRandom = (hit + miss) * (hit + falseAlarm) / (float) this.getTotal();
        return (hit - hitRandom) / (float) (hit + miss + falseAlarm - hitRandom);
    }

    /**
     * Get Hanssen and Kuipers discriminant (true skill statistic, Peirce's
     * skill score How well did the forecast separate the "yes" events from the
     * "no" events? Range: -1 to 1, 0 indicates no skill. Perfect score: 1.
     *
     * @return
     */
    public float getHK() {
        return hit / (float) (hit + miss) - falseAlarm / (float) (falseAlarm + correctNegative);
    }

    /**
     * Get Heidke skill score (Cohen's k) What was the accuracy of the forecast
     * relative to that of random chance? Range: -∞ to 1, 0 indicates no skill.
     * Perfect score: 1.
     *
     * @return
     */
    public float getHSS() {
        float ecRandom = ((hit + miss) * (hit + falseAlarm) + (correctNegative + miss) * (correctNegative + falseAlarm))
                / (float) this.getTotal();
        return (hit + correctNegative - ecRandom) / (float) (this.getTotal() - ecRandom);
    }

    /**
     * Get odds ratio What is the ratio of the odds of a "yes" forecast being
     * correct, to the odds of a "yes" forecast being wrong? Odds ratio - Range:
     * 0 to ∞, 1 indicates no skill. Perfect score: ∞ Log * Log odds ratio -
     * Range: -∞ to ∞, 0 indicates no skill. Perfect score: ∞
     *
     * @return
     */
    public float getOddsRatio() {
        return hit * correctNegative / (float) (miss * falseAlarm);
    }

    /**
     * Get odds ratio score (Yule's Q) What was the improvement of the forecast
     * over random chance? Range: -1 to 1, 0 indicates no skill. Perfect score:
     * 1
     *
     * @return
     */
    public float getORSS() {
        return (hit * correctNegative - miss * falseAlarm) / (float) (hit * correctNegative + miss * falseAlarm);
    }

    /**
     * Get verify result
     *
     * @return Verify result map
     */
    @Override
    public Map getVerifyResult() {
        Map map = new HashMap();
        map.put("Accuracy", this.getAccuracy());
        map.put("Bias", this.getBias());
        map.put("ETS", this.getETS());
        map.put("FAR", this.getFAR());
        map.put("HK", this.getHK());
        map.put("HSS", this.getHSS());
        map.put("ORSS", this.getORSS());
        map.put("OR", this.getOddsRatio());
        map.put("POD", this.getPOD());
        map.put("POFD", this.getPOFD());
        map.put("SR", this.getSuccessRatio());
        map.put("TS", this.getThreatScore());

        return map;
    }
    
    @Override
    public String getScoreNames(){
        return "Accuracy,Bias,ETS,FAR,HK,HSS,ORSS,OR,POD,POFD,SR,TS";
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.DICHOTOMOUS;
    }

    /**
     * Get verify result values string
     *
     * @return Value string
     */
    public String getVerifyValues() {
        String vstr = String.valueOf(this.getAccuracy());
        vstr = vstr + ',' + String.valueOf(this.getBias());
        vstr = vstr + ',' + String.valueOf(this.getETS());
        vstr = vstr + ',' + String.valueOf(this.getFAR());
        vstr = vstr + ',' + String.valueOf(this.getHK());
        vstr = vstr + ',' + String.valueOf(this.getHSS());
        vstr = vstr + ',' + String.valueOf(this.getORSS());
        vstr = vstr + ',' + String.valueOf(this.getOddsRatio());
        vstr = vstr + ',' + String.valueOf(this.getPOD());
        vstr = vstr + ',' + String.valueOf(this.getPOFD());
        vstr = vstr + ',' + String.valueOf(this.getSuccessRatio());
        vstr = vstr + ',' + String.valueOf(this.getThreatScore());

        return vstr;
    }

    /**
     * To string
     *
     * @return String
     */
    @Override
    public String toString() {
        String line;
        StringBuilder sb = new StringBuilder();
        line = "Hit: " + String.valueOf(this.hit) + "\n";
        sb.append(line);
        line = "Miss: " + String.valueOf(this.miss) + "\n";
        sb.append(line);
        line = "False alarm: " + String.valueOf(this.falseAlarm) + "\n";
        sb.append(line);
        line = "Correct negative: " + String.valueOf(this.correctNegative) + "\n";
        sb.append(line);
        sb.append("---------------------------\n");
        sb.append("Score\tValue\n");
        Map map = this.getVerifyResult();
        Set<String> key = map.keySet();
        for (String s : key) {
            line = s + "\t" + String.format("%.2f", map.get(s)) + "\n";
            sb.append(line);
        }

        return sb.toString();
    }

    /**
     * Save as a text file
     *
     * @param fileName File name
     */
    public void save(String fileName) {
        BufferedWriter sw = null;
        try {
            sw = new BufferedWriter(new FileWriter(new File(fileName)));
            String aStr = "Statistics,Score";
            sw.write(aStr);
            Map map = this.getVerifyResult();
            Set<String> key = map.keySet();
            for (Iterator it = key.iterator(); it.hasNext();) {
                String s = (String) it.next();
                aStr = s + "," + map.get(s).toString();
                sw.newLine();
                sw.write(aStr);
            }
            sw.flush();
            sw.close();
        } catch (IOException ex) {
            Logger.getLogger(StationData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>
}
