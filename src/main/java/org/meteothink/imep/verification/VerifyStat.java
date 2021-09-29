/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.meteothink.imep.verification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.meteoinfo.data.GridData;
import org.meteoinfo.data.StationData;
import org.meteoinfo.common.MIMath;
import org.meteoinfo.ndarray.Array;
import org.meteoinfo.ndarray.DataType;

/**
 *
 * @author yaqiang
 */
public class VerifyStat {

    /**
     * Categorical calculation - Grid data
     *
     * @param obsData Observation grid data
     * @param fcstData Forecast grid data
     * @param method Verify method
     * @return Result grid data
     */
    public static GridData categorical(GridData obsData, GridData fcstData, VerifyMethod method) {
        GridData veriData = new GridData(obsData);
        int xnum = obsData.getXNum();
        int ynum = obsData.getYNum();
        double value = 0;
        boolean bobs, bfcst;
        switch (method.getMethodType()) {
            case DICHOTOMOUS:
                DichotomousMethod dMethod = (DichotomousMethod) method;
                DataRange dataRange = dMethod.getDataRange();
                for (int i = 0; i < ynum; i++) {
                    for (int j = 0; j < xnum; j++) {
                        if (MIMath.doubleEquals(obsData.getDoubleValue(i, j), obsData.getDoubleMissingValue())) {
                            value = 0;
                        } else if (MIMath.doubleEquals(fcstData.getDoubleValue(i, j), value)) {
                            value = 0;
                        } else {
                            bobs = dataRange.isValueIn(obsData.getDoubleValue(i, j));
                            bfcst = dataRange.isValueIn(fcstData.getDoubleValue(i, j));
                            if (bobs && bfcst) {
                                value = 1;
                            } else if (bobs == true && bfcst == false) {
                                value = 2;
                            } else if (bobs == false && bfcst == true) {
                                value = 3;
                            } else {
                                value = 4;
                            }
                            veriData.setValue(i, j, value);
                        }
                    }
                }
                break;
        }

        return veriData;
    }

    /**
     * Categorical calculation - Grid data
     *
     * @param obsData Observation grid data
     * @param fcstData Forecast grid data
     * @param threshold Threshold
     * @param isBigger Is bigger or not - for threshold
     * @return Result grid data
     */
    public static GridData categorical(GridData obsData, GridData fcstData, double threshold, boolean isBigger) {
        GridData veriData = new GridData(obsData);
        int xnum = obsData.getXNum();
        int ynum = obsData.getYNum();
        double value = 0;
        boolean bobs, bfcst;
        for (int i = 0; i < ynum; i++) {
            for (int j = 0; j < xnum; j++) {
                if (MIMath.doubleEquals(obsData.getDoubleValue(i, j), obsData.getDoubleMissingValue())) {
                    value = 0;
                } else if (MIMath.doubleEquals(fcstData.getDoubleValue(i, j), value)) {
                    value = 0;
                } else {
                    if (isBigger) {
                        bobs = obsData.getDoubleValue(i, j) >= threshold;
                        bfcst = fcstData.getDoubleValue(i, j) >= threshold;
                    } else {
                        bobs = obsData.getDoubleValue(i, j) <= threshold;
                        bfcst = fcstData.getDoubleValue(i, j) <= threshold;
                    }

                    if (bobs && bfcst) {
                        value = 1;
                    } else if (bobs == true && bfcst == false) {
                        value = 2;
                    } else if (bobs == false && bfcst == true) {
                        value = 3;
                    } else {
                        value = 4;
                    }
                    veriData.setValue(i, j, value);
                }
            }
        }

        return veriData;
    }

    /**
     * Categorical calculation
     *
     * @param obsData Observation data
     * @param fcstData Forecast data
     * @param method Verify method
     * @return Categorical result data
     */
    public static Array categorical(Array obsData, Array fcstData, VerifyMethod method) {
        obsData = obsData.copyIfView();
        fcstData = fcstData.copyIfView();

        DichotomousMethod dMethod = (DichotomousMethod) method;
        DataRange dataRange = dMethod.getDataRange();
        return categorical(obsData, fcstData, dataRange);
    }

    /**
     * Categorical calculation
     *
     * @param obsData Observation data
     * @param fcstData Forecast data
     * @param dataRange Data range
     * @return Categorical result data
     */
    public static Array categorical(Array obsData, Array fcstData, DataRange dataRange) {
        obsData = obsData.copyIfView();
        fcstData = fcstData.copyIfView();

        Array cateData = Array.factory(DataType.INT, obsData.getShape());
        long stnum = obsData.getSize();
        double v_obs, v_fcst;
        int value;
        boolean bobs,
                bfcst;
        for (int i = 0; i < stnum; i++) {
            v_obs = obsData.getDouble(i);
            v_fcst = fcstData.getDouble(i);
            if (Double.isNaN(v_obs) || Double.isNaN(v_fcst)) {
                value = 0;
            } else {
                bobs = dataRange.isValueIn(v_obs);
                bfcst = dataRange.isValueIn(v_fcst);
                if (bobs && bfcst) {
                    value = 1;
                } else if (bobs == true && bfcst == false) {
                    value = 2;
                } else if (bobs == false && bfcst == true) {
                    value = 3;
                } else {
                    value = 4;
                }
            }
            cateData.setInt(i, value);
        }
        return cateData;
    }

    /**
     * Categorical calculation - Station data
     *
     * @param obsData Observation station data
     * @param fcstData Forecast station data
     * @param method Verify method
     * @return Result station data
     */
    public static StationData categorical(StationData obsData, StationData fcstData, VerifyMethod method) {
        StationData cateData = null;
        switch (method.getMethodType()) {
            case DICHOTOMOUS:
                cateData = new StationData(obsData);
                DichotomousMethod dMethod = (DichotomousMethod) method;
                DataRange dataRange = dMethod.getDataRange();
                int stnum = obsData.getStNum();
                double value;
                boolean bobs,
                 bfcst;
                for (int i = 0; i < stnum; i++) {
                    if (MIMath.doubleEquals(obsData.getValue(i), obsData.missingValue)) {
                        value = 0;
                    } else if (MIMath.doubleEquals(fcstData.getValue(i), fcstData.missingValue)) {
                        value = 0;
                    } else {
                        bobs = dataRange.isValueIn(obsData.getValue(i));
                        bfcst = dataRange.isValueIn(fcstData.getValue(i));
                        if (bobs && bfcst) {
                            value = 1;
                        } else if (bobs == true && bfcst == false) {
                            value = 2;
                        } else if (bobs == false && bfcst == true) {
                            value = 3;
                        } else {
                            value = 4;
                        }
                    }
                    cateData.setValue(i, value);
                }
                break;
            case MULTICATEGORY:
                cateData = new StationData(obsData);
                MultiCategoryMethod mMethod = (MultiCategoryMethod) method;
                stnum = obsData.getStNum();
                double[] values = mMethod.getCategoryValues();
                int vnum = mMethod.getCategoryNum();
                int i = 0,
                 j = 0;
                double obsValue;
                double fcstValue;
                for (int s = 0; s < stnum; s++) {
                    obsValue = obsData.getValue(s);
                    fcstValue = fcstData.getValue(s);
                    if (MIMath.doubleEquals(obsValue, obsData.missingValue)) {
                        continue;
                    } else if (MIMath.doubleEquals(fcstValue, fcstData.missingValue)) {
                        continue;
                    } else {
                        for (int k = 0; k < vnum; k++) {
                            if (k < vnum - 1) {
                                if (obsValue < values[k]) {
                                    j = k;
                                    break;
                                }
                            } else {
                                j = k;
                                break;
                            }
                        }
                        for (int k = 0; k < vnum; k++) {
                            if (k < vnum - 1) {
                                if (fcstValue < values[k]) {
                                    i = k;
                                    break;
                                }
                            } else {
                                i = k;
                                break;
                            }
                        }
                        i += 1;
                        j += 1;
                        cateData.setValue(s, i * 10 + j);
                    }
                }
                break;
        }

        return cateData;
    }

    /**
     * Categorical calculation - Station data
     *
     * @param obsData Observation station data
     * @param fcstData Forecast station data
     * @param threshold Threshold
     * @param isBigger Is bigger or not - for threshold
     * @return Result station data
     */
    public static StationData categorical(StationData obsData, StationData fcstData, double threshold, boolean isBigger) {
        StationData veriData = new StationData(obsData);
        int stnum = obsData.getStNum();
        double value;
        boolean bobs, bfcst;
        for (int i = 0; i < stnum; i++) {
            if (MIMath.doubleEquals(obsData.getValue(i), obsData.missingValue)) {
                value = 0;
            } else if (MIMath.doubleEquals(fcstData.getValue(i), fcstData.missingValue)) {
                value = 0;
            } else {
                if (isBigger) {
                    bobs = obsData.getValue(i) >= threshold;
                    bfcst = fcstData.getValue(i) >= threshold;
                } else {
                    bobs = obsData.getValue(i) <= threshold;
                    bfcst = fcstData.getValue(i) <= threshold;
                }
                if (bobs && bfcst) {
                    value = 1;
                } else if (bobs == true && bfcst == false) {
                    value = 2;
                } else if (bobs == false && bfcst == true) {
                    value = 3;
                } else {
                    value = 4;
                }
            }
            veriData.setValue(i, value);
        }

        return veriData;
    }

    /**
     * Aggregate calculation - Grid data
     *
     * @param veriData Categorical grid data
     * @return Contingency table
     */
    public static ContingencyTable aggregate(GridData veriData) {
        ContingencyTable outData = new ContingencyTable();
        for (int i = 0; i < veriData.getYNum(); i++) {
            for (int j = 0; j < veriData.getXNum(); j++) {
                switch (veriData.getValue(i, j).intValue()) {
                    case 1:
                        outData.hit += 1;
                        break;
                    case 2:
                        outData.miss += 1;
                        break;
                    case 3:
                        outData.falseAlarm += 1;
                        break;
                    case 4:
                        outData.correctNegative += 1;
                        break;
                }
            }
        }

        return outData;
    }

    /**
     * Aggregate calculation
     *
     * @param cateData Categorical data
     * @return Contingency table
     */
    public static ContingencyTable aggregate(Array cateData) {
        ContingencyTable cTable = new ContingencyTable();
        for (int i = 0; i < cateData.getSize(); i++) {
            switch (cateData.getInt(i)) {
                case 1:
                    cTable.hit += 1;
                    break;
                case 2:
                    cTable.miss += 1;
                    break;
                case 3:
                    cTable.falseAlarm += 1;
                    break;
                case 4:
                    cTable.correctNegative += 1;
                    break;
            }
        }
        return cTable;
    }

    /**
     * Aggregate calculation - Station data
     *
     * @param veriData Categorical station data
     * @param method Verify method
     * @return Contingency table
     */
    public static VerifyTable aggregate(StationData veriData, VerifyMethod method) {
        switch (method.getMethodType()) {
            case DICHOTOMOUS:
                ContingencyTable cTable = new ContingencyTable();
                for (int i = 0; i < veriData.getStNum(); i++) {
                    switch ((int) veriData.getValue(i)) {
                        case 1:
                            cTable.hit += 1;
                            break;
                        case 2:
                            cTable.miss += 1;
                            break;
                        case 3:
                            cTable.falseAlarm += 1;
                            break;
                        case 4:
                            cTable.correctNegative += 1;
                            break;
                    }
                }
                return cTable;
            case MULTICATEGORY:
                MultiCategoryTable mtable = new MultiCategoryTable();
                int vnum = ((MultiCategoryMethod) method).getCategoryNum();
                int[][] mvalues = new int[vnum][vnum];
                int i,
                 j;
                for (int s = 0; s < veriData.getStNum(); s++) {
                    String value = String.valueOf((int) veriData.getValue(s));
                    i = Integer.parseInt(value.substring(0, 1)) - 1;
                    j = Integer.parseInt(value.substring(1)) - 1;
                    mvalues[i][j] += 1;
                }
                mtable.setValues(mvalues);
                break;
        }

        return null;
    }

    /**
     * Get verify table
     *
     * @param obsData Observation station data
     * @param fcstData Forecast station data
     * @param method Verify method
     * @return Verify table
     */
    public static VerifyTable getVerifyTable(StationData obsData, StationData fcstData, VerifyMethod method) {
        VerifyTable table = null;
        switch (method.getMethodType()) {
            case DICHOTOMOUS:
            case MULTICATEGORY:
                StationData cateData = categorical(obsData, fcstData, method);
                table = aggregate(cateData, method);
                break;
            case CONTINUOUS:
                //ContinuousMethod cMethod = (ContinuousMethod) method;
                int stnum = obsData.getStNum();
                List<Double> obsValues = new ArrayList<>();
                List<Double> fcstValues = new ArrayList<>();
                double obsValue,
                 fcstValue;
                for (int s = 0; s < stnum; s++) {
                    obsValue = obsData.getValue(s);
                    fcstValue = fcstData.getValue(s);
                    if (MIMath.doubleEquals(obsValue, obsData.missingValue)) {
                    } else if (MIMath.doubleEquals(fcstValue, fcstData.missingValue)) {
                    } else {
                        obsValues.add(obsValue);
                        fcstValues.add(fcstValue);
                    }
                }
                int n = obsValues.size();
                double[] ovalues = new double[n];
                double[] fvalues = new double[n];
                for (int s = 0; s < n; s++) {
                    ovalues[s] = obsValues.get(s);
                    fvalues[s] = fcstValues.get(s);
                }
                ContinuousTable ctable = new ContinuousTable();
                ctable.setFcstValues(fvalues);
                ctable.setObsValues(ovalues);
                table = ctable;
                break;
        }

        return table;
    }

    /**
     * Get verify table
     *
     * @param obsData Observation station data
     * @param fcstData Forecast station data
     * @param method Verify method
     * @return Verify table
     */
    public static VerifyTable getVerifyTable(Array obsData, Array fcstData, VerifyMethod method) {
        obsData = obsData.copyIfView();
        fcstData = fcstData.copyIfView();

        VerifyTable table = null;
        switch (method.getMethodType()) {
            case DICHOTOMOUS:
            case MULTICATEGORY:
                Array cateData = categorical(obsData, fcstData, method);
                table = aggregate(cateData);
                break;
            case SCORE:
                long stnum = obsData.getSize();
                double obsValue, fcstValue;
                ScoreMethod scoreMethod = (ScoreMethod)method;
                ScoreTable scoreTable = new ScoreTable(scoreMethod.getObsRanges());                
                int idx, score;
                for (int s = 0; s < stnum; s++) {
                    obsValue = obsData.getDouble(s);
                    fcstValue = fcstData.getDouble(s);                    
                    if (!Double.isNaN(obsValue) && !Double.isNaN(fcstValue)) {
                        idx = scoreMethod.getObsRangeIdx(obsValue);
                        if (idx >= 0){
                            score = scoreMethod.getScore(idx, fcstValue);
                            scoreTable.addScore(idx, score);
                        }
                    }
                }
                table = scoreTable;
                break;
            case CONTINUOUS:
                stnum = obsData.getSize();
                List<Double> obsValues = new ArrayList<>();
                List<Double> fcstValues = new ArrayList<>();
                for (int s = 0; s < stnum; s++) {
                    obsValue = obsData.getDouble(s);
                    fcstValue = fcstData.getDouble(s);
                    if (!Double.isNaN(obsValue) && !Double.isNaN(fcstValue)) {
                        obsValues.add(obsValue);
                        fcstValues.add(fcstValue);
                    }
                }
                int n = obsValues.size();
                double[] ovalues = new double[n];
                double[] fvalues = new double[n];
                for (int s = 0; s < n; s++) {
                    ovalues[s] = obsValues.get(s);
                    fvalues[s] = fcstValues.get(s);
                }
                ContinuousTable ctable = new ContinuousTable();
                ctable.setFcstValues(fvalues);
                ctable.setObsValues(ovalues);
                table = ctable;
                break;
        }

        return table;
    }

    /**
     * Get verify table
     *
     * @param obsData Observation grid data
     * @param fcstData Forecast grid data
     * @param method Verify method
     * @return Verify table
     */
    public static VerifyTable getVerifyTable(GridData obsData, GridData fcstData, VerifyMethod method) {
        VerifyTable table = null;
        switch (method.getMethodType()) {
            case DICHOTOMOUS:
                //DichotomousMethod dMethod = (DichotomousMethod) method;
                GridData cateData = categorical(obsData, fcstData, method);
                table = aggregate(cateData);
                break;
            case MULTICATEGORY:
                MultiCategoryMethod mMethod = (MultiCategoryMethod) method;
                int ynum = obsData.getYNum();
                int xnum = obsData.getXNum();
                double[] values = mMethod.getCategoryValues();
                int vnum = mMethod.getCategoryNum();
                int i = 0,
                 j = 0;
                double obsValue;
                double fcstValue;
                int[][] mvalues = new int[vnum][vnum];
                for (int m = 0; m < ynum; m++) {
                    for (int n = 0; n < xnum; n++) {
                        obsValue = obsData.getDoubleValue(m, n);
                        fcstValue = fcstData.getDoubleValue(m, n);
                        if (MIMath.doubleEquals(obsValue, obsData.getDoubleMissingValue())) {
                            continue;
                        }
                        if (MIMath.doubleEquals(fcstValue, fcstData.getDoubleMissingValue())) {
                            continue;
                        }
                        for (int k = 0; k < vnum; k++) {
                            if (k < vnum - 1) {
                                if (obsValue < values[k]) {
                                    j = k;
                                    break;
                                }
                            } else {
                                j = k;
                                break;
                            }
                        }
                        for (int k = 0; k < vnum; k++) {
                            if (k < vnum - 1) {
                                if (fcstValue < values[k]) {
                                    i = k;
                                    break;
                                }
                            } else {
                                i = k;
                                break;
                            }
                        }
                        mvalues[i][j] += 1;
                    }
                }
                MultiCategoryTable mtable = new MultiCategoryTable();
                mtable.setValues(mvalues);
                table = mtable;
                break;
            case CONTINUOUS:
                //ContinuousMethod cMethod = (ContinuousMethod) method;
                ynum = obsData.getYNum();
                xnum = obsData.getXNum();
                List<Double> obsValues = new ArrayList<>();
                List<Double> fcstValues = new ArrayList<>();
                for (int m = 0; m < ynum; m++) {
                    for (int n = 0; n < xnum; n++) {
                        obsValue = obsData.getDoubleValue(m, n);
                        fcstValue = fcstData.getDoubleValue(m, n);
                        if (MIMath.doubleEquals(obsValue, obsData.getDoubleMissingValue())) {
                            continue;
                        }
                        if (MIMath.doubleEquals(fcstValue, fcstData.getDoubleMissingValue())) {
                            continue;
                        }
                        obsValues.add(obsValue);
                        fcstValues.add(fcstValue);
                    }
                }
                int n = obsValues.size();
                double[] ovalues = new double[n];
                double[] fvalues = new double[n];
                for (int s = 0; s < n; s++) {
                    ovalues[s] = obsValues.get(s);
                    fvalues[s] = fcstValues.get(s);
                }
                ContinuousTable ctable = new ContinuousTable();
                ctable.setFcstValues(fvalues);
                ctable.setObsValues(ovalues);
                table = ctable;
                break;
        }

        return table;
    }

    /**
     * Get verify table
     *
     * @param obsData Observation grid data
     * @param fcstData Forecast grid data
     * @param method Verify method
     * @return Verify table
     */
    public static VerifyTable getVerifyTable(List<Number> obsData, List<Number> fcstData, VerifyMethod method) {
        VerifyTable table = null;
        int n = obsData.size();
        switch (method.getMethodType()) {
            case DICHOTOMOUS:
                DichotomousMethod dMethod = (DichotomousMethod) method;
                DataRange dataRange = dMethod.getDataRange();
                boolean bobs,
                 bfcst;
                ContingencyTable cTable = new ContingencyTable();
                for (int i = 0; i < n; i++) {
                    bobs = dataRange.isValueIn(obsData.get(i).doubleValue());
                    bfcst = dataRange.isValueIn(fcstData.get(i).doubleValue());
                    if (bobs && bfcst) {
                        cTable.hit += 1;
                    } else if (bobs == true && bfcst == false) {
                        cTable.miss += 1;
                    } else if (bobs == false && bfcst == true) {
                        cTable.falseAlarm += 1;
                    } else {
                        cTable.correctNegative += 1;
                    }
                }
                table = cTable;
                break;
            case MULTICATEGORY:
                MultiCategoryMethod mMethod = (MultiCategoryMethod) method;
                MultiCategoryTable mtable = new MultiCategoryTable();
                int vnum = mMethod.getCategoryNum();
                int[][] mvalues = new int[vnum][vnum];
                double[] values = mMethod.getCategoryValues();
                int i = 0,
                 j = 0;
                double obsValue;
                double fcstValue;
                for (int s = 0; s < n; s++) {
                    obsValue = obsData.get(s).doubleValue();
                    fcstValue = fcstData.get(s).doubleValue();
                    for (int k = 0; k < vnum; k++) {
                        if (k < vnum - 1) {
                            if (obsValue < values[k]) {
                                j = k;
                                break;
                            }
                        } else {
                            j = k;
                            break;
                        }
                    }
                    for (int k = 0; k < vnum; k++) {
                        if (k < vnum - 1) {
                            if (fcstValue < values[k]) {
                                i = k;
                                break;
                            }
                        } else {
                            i = k;
                            break;
                        }
                    }
                    mvalues[i][j] += 1;
                }
                mtable.setValues(mvalues);
                table = mtable;
                break;
            case CONTINUOUS:
                ContinuousTable ctable = new ContinuousTable(obsData, fcstData);
                table = ctable;
                break;
        }

        return table;
    }
    
    /**
     * Write verify file
     *
     * @param tables The verify tables
     * @param fileName The verify file name
     */
    public static void writeVerifyFile(List<VerifyTable> tables, String fileName) {
        BufferedWriter sw = null;
        try {
            sw = new BufferedWriter(new FileWriter(new File(fileName)));
            String sName = tables.get(0).getScoreNames();
            String[] sNames = sName.split(",");
            String title = "ID," + sName;
            sw.write(title);
            sw.newLine();
            for (int i = 0; i < tables.size(); i++) {
                VerifyTable table = tables.get(i);
                String line = table.getName();
                Map map = table.getVerifyResult();
                for (String s : sNames) {
                    line = line + "," + String.format("%1$.2f", map.get(s));
                }
                sw.write(line);
                sw.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(VerifyStat.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                sw.flush();
                sw.close();
            } catch (IOException ex) {
                Logger.getLogger(VerifyStat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Write verify file
     *
     * @param tables The verify tables
     * @param times The verify times
     * @param fileName The verify file name
     */
    public static void writeVerifyFile(List<VerifyTable> tables, List<LocalDateTime> times, String fileName) {
        BufferedWriter sw = null;
        try {
            sw = new BufferedWriter(new FileWriter(new File(fileName)));
            String sName = tables.get(0).getScoreNames();
            String[] sNames = sName.split(",");
            String title = "Time,Region," + sName;
            sw.write(title);
            sw.newLine();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < tables.size(); i++) {
                VerifyTable table = tables.get(i);
                LocalDateTime time = times.get(i);
                String line = format.format(time) + "," + table.getName();
                Map map = table.getVerifyResult();
                for (String s : sNames) {
                    Object v = map.get(s);
                    if (v instanceof Float)
                        line = line + "," + String.format("%1$.2f", v);
                    else
                        line = line + "," + String.valueOf(v);
                }
                sw.write(line);
                sw.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(VerifyStat.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                sw.flush();
                sw.close();
            } catch (IOException ex) {
                Logger.getLogger(VerifyStat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
