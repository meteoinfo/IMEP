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

import org.meteothink.imep.global.Globals;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.meteoinfo.data.GridData;
import org.meteoinfo.data.StationData;
import org.meteoinfo.data.mapdata.MapDataManage;
import org.meteoinfo.data.meteodata.GridDataSetting;
import org.meteoinfo.geoprocess.analysis.InterpolationMethods;
import org.meteoinfo.geoprocess.analysis.InterpolationSetting;
import org.meteoinfo.data.meteodata.MeteoDataInfo;
import org.meteoinfo.data.meteodata.Variable;
import org.meteoinfo.data.meteodata.grads.GrADSDataInfo;
import org.meteoinfo.layer.VectorLayer;
import org.meteoinfo.shape.PolygonShape;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author yaqiang
 */
public class VerifyGroup implements Cloneable {
    // <editor-fold desc="Variables">

    private String _name;
    private Dataset _obsDataset = new Dataset("Observation");
    private Dataset _fcstDataset = new Dataset("Forecast");
    //private double _threshold;
    //private boolean _isBigger = true;
    private VerifyMethod _verifyMethod = new DichotomousMethod();
    //private VerifyTable _verifyTable;
    private VerifyCategory _verifyCategory = VerifyCategory.Spatial;
    private StatType _statType = StatType.StationStat;
    private TimeSelect _timeSelect = TimeSelect.All;
    private boolean _isAllTime = true;
    private List<Integer> _timeIndices = new ArrayList<>();
    private List<LocalDateTime> _times = new ArrayList<>();
    private String _outFilePath;
    private MeteoDataInfo _obsDataInfo;
    private MeteoDataInfo _fcstDataInfo;
    private int _timeZone = 0;
    private String stFilterFile = "";
    private String _dataMaskFile = "";
    private String _statRegionFile = "";
    private boolean _isDatasetOpened = false;
    private boolean outputCateData = false;

    // </editor-fold>
    // <editor-fold desc="Constructor">
    /**
     * Constructor
     *
     * @param name Group name
     */
    public VerifyGroup(String name) {
        this._name = name;
        this._obsDataset.setDataSourceType(DataSourceType.Observation);
        this._fcstDataset.setDataSourceType(DataSourceType.Forecast);
    }
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">

    /**
     * Get name
     *
     * @return Name
     */
    public String getName() {
        return _name;
    }

    /**
     * Set name
     *
     * @param name Name
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Get observation dataset
     *
     * @return Observation dataset
     */
    public Dataset getObsDataset() {
        return this._obsDataset;
    }

    /**
     * Set observation dataset
     *
     * @param value Dataset
     */
    public void setObsDataset(Dataset value) {
        this._obsDataset = value;
        this._isDatasetOpened = false;
    }

    /**
     * Get forecast dataset
     *
     * @return Forecast dataset
     */
    public Dataset getFcstDataset() {
        return this._fcstDataset;
    }

    /**
     * Set forecast dataset
     *
     * @param value Dataset
     */
    public void setFcstDataset(Dataset value) {
        this._fcstDataset = value;
        this._isDatasetOpened = false;
    }

    /**
     * Get verification method
     *
     * @return Verification method
     */
    public VerifyMethod getVerifyMethod() {
        return this._verifyMethod;
    }

    /**
     * Set verification method
     *
     * @param value Verification method
     */
    public void setVerifyMethod(VerifyMethod value) {
        this._verifyMethod = value;
    }

    /**
     * Get verification category
     *
     * @return Verification category
     */
    public VerifyCategory getVerifyCategory() {
        return this._verifyCategory;
    }

    /**
     * Set verification category
     *
     * @param value Verification category
     */
    public void setVerifyCategory(VerifyCategory value) {
        this._verifyCategory = value;
    }

    /**
     * Get statistic type
     *
     * @return StatType
     */
    public StatType getStatType() {
        return this._statType;
    }

    /**
     * Get time selection type
     *
     * @return Time selection type
     */
    public TimeSelect getTimeSelect() {
        return this._timeSelect;
    }

    /**
     * Set time selection type
     *
     * @param value Time selection type
     */
    public void setTimeSelect(TimeSelect value) {
        this._timeSelect = value;
    }

    /**
     * Get if is verify all time
     *
     * @return Boolean
     */
    public boolean isAllTime() {
        return this._isAllTime;
    }

    /**
     * Set if is verify all time
     *
     * @param value Boolean
     */
    public void setAllTime(boolean value) {
        this._isAllTime = value;
    }

    /**
     * Get time indices
     *
     * @return Time indices
     */
    public List<Integer> getTimeIndices() {
        return this._timeIndices;
    }

    /**
     * Set time indices
     *
     * @param value Time indices
     */
    public void setTimeIndices(List<Integer> value) {
        this._timeIndices = value;
    }

    /**
     * Set statistic type
     *
     * @param value StatType
     */
    public void setStatType(StatType value) {
        this._statType = value;
    }

    /**
     * Get times
     *
     * @return Times
     */
    public List<LocalDateTime> getTimes() {
        return this._times;
    }

    /**
     * Set times
     *
     * @param value Times
     */
    public void setTimes(List<LocalDateTime> value) {
        this._times = value;
    }

    /**
     * Get start time
     *
     * @return Start time
     */
    public LocalDateTime getStartTime() {
        return this._times.get(0);
    }

    /**
     * Get end time
     *
     * @return End time
     */
    public LocalDateTime getEndTime() {
        return this._times.get(this._times.size() - 1);
    }

    /**
     * Get time number
     *
     * @return Time number
     */
    public int getTimeNum() {
        return this._times.size();
    }

    /**
     * Get output file path
     *
     * @return Output file path
     */
    public String getOutFilePath() {
        return this._outFilePath;
    }

    /**
     * Set output file path
     *
     * @param value Output file path
     */
    public void setOutFilePath(String value) {
        this._outFilePath = value;
    }

    /**
     * Get time zone
     *
     * @return Time zone
     */
    public int getTimeZone() {
        return this._timeZone;
    }

    /**
     * Set time zone
     *
     * @param value Time zone
     */
    public void setTimeZone(int value) {
        this._timeZone = value;
    }
    
    /**
     * Get station filter file
     * @return Station filter file
     */
    public String getStFilterFile(){
        return this.stFilterFile;
    }
    
    /**
     * Set station filter file
     * @param value Station filter file
     */
    public void setStFilterFile(String value){
        this.stFilterFile = value;
    }

    /**
     * Get data mask file
     *
     * @return Data mask file
     */
    public String getDataMaskFile() {
        return this._dataMaskFile;
    }

    /**
     * Set data mask file
     *
     * @param value Data mask file
     */
    public void setDataMaskFile(String value) {
        this._dataMaskFile = value;
    }

    /**
     * Get statictics regions file
     *
     * @return File name
     */
    public String getStatRegionFile() {
        return this._statRegionFile;
    }

    /**
     * Set statistics regions file
     *
     * @param value File name
     */
    public void setStatRegionFile(String value) {
        this._statRegionFile = value;
    }

    /**
     * Get if the datasets are opened
     *
     * @return Boolean
     */
    public boolean isDatasetOpened() {
        return this._isDatasetOpened;
    }

    /**
     * Get forecast times
     *
     * @return Times
     */
    public List<LocalDateTime> getFcstTimes() {
        if (!this._isDatasetOpened) {
            this.openDatasets();
        }
        return this._fcstDataInfo.getDataInfo().getTimes();
    }

    /**
     * Get observation data info
     *
     * @return Observation data info
     */
    public MeteoDataInfo getObsDataInfo() {
        return this._obsDataInfo;
    }

    /**
     * Get forecast data info
     *
     * @return Forecast data info
     */
    public MeteoDataInfo getFcstDataInfo() {
        return this._fcstDataInfo;
    }

    // </editor-fold>
    // <editor-fold desc="Methods">
    /**
     * Set times by forecast data
     *
     * @param fcstDataInfo Forecast data info
     */
    public void setTimesByFcstData(MeteoDataInfo fcstDataInfo) {
        this.setTimesByFcstData(fcstDataInfo, 0);
    }

    /**
     * Set times by forecast data
     *
     * @param fcstDataInfo Forecast data info
     * @param sIdx Start time index
     */
    public void setTimesByFcstData(MeteoDataInfo fcstDataInfo, int sIdx) {
        this._times.clear();
        List<LocalDateTime> times = fcstDataInfo.getDataInfo().getTimes();
        for (int i = sIdx; i < times.size(); i++) {
            this._times.add(this.getVeriyTime(times.get(i), this._fcstDataset.getTimeZone()));
        }
    }

    /**
     * Set times by forecast data
     *
     * @param fcstDataInfo Forecast data info
     * @param sIdx Start time index
     * @param eIdx End time index
     */
    public void setTimesByFcstData(MeteoDataInfo fcstDataInfo, int sIdx, int eIdx) {
        this._times.clear();
        List<LocalDateTime> times = fcstDataInfo.getDataInfo().getTimes();
        for (int i = sIdx; i < eIdx; i++) {
            this._times.add(this.getVeriyTime(times.get(i), this._fcstDataset.getTimeZone()));
        }
    }

    private LocalDateTime getVeriyTime(LocalDateTime time, int timeZone) {
        if (timeZone == this._timeZone) {
            return time;
        }

        return time.plusHours(this._timeZone - timeZone);
    }

    private LocalDateTime getTime(LocalDateTime time, int fromTimeZone, int toTimeZone) {
        if (fromTimeZone == toTimeZone) {
            return time;
        }

        return time.plusHours(toTimeZone - fromTimeZone);
    }

    /**
     * Open observation and forecast datasets
     */
    public void openDatasets() {
        this._obsDataInfo = this._obsDataset.openData();
        this._fcstDataInfo = this._fcstDataset.openData();
        //this.setTimesByFcstData(_fcstDataInfo, 1);
        this._isDatasetOpened = true;
    }

    /**
     * Get verify times
     *
     * @return Verify times
     */
    public List<LocalDateTime> getVerifyTimes() {
        List<LocalDateTime> times = new ArrayList<>();
        switch (this._timeSelect) {
            case All:
                times = this._fcstDataInfo.getDataInfo().getTimes();
                break;
            case TimeIndex:
                for (int i : this._timeIndices) {
                    LocalDateTime time = this._fcstDataInfo.getDataInfo().getTimes().get(i);
                    times.add(time);
                }
                break;
            case TimeValue:
                times = this._times;
                break;
        }

        if (this._timeZone != this._fcstDataset.getTimeZone()) {
            List<LocalDateTime> ntimes = new ArrayList<>();
            for (LocalDateTime time : times) {
                ntimes.add(this.getTime(time, this._fcstDataset.getTimeZone(), this._timeZone));
            }

            return ntimes;
        }

        return times;
    }

    /**
     * Run virification calculation
     * @throws IOException
     */
    public void run() throws IOException {
        switch (this._verifyCategory) {
            case Spatial:
                switch (this._statType) {
                    case StationStat:
                        this.run_Spatial_Station();
                        break;
                    case GridStat:
                        this.run_Spatial_Grid();
                        break;
                }
                break;
        }
    }

    /**
     * Run virification calculation
     *
     * @throws IOException
     */
    public void run_Spatial_Station() throws IOException {
//        if (!this._isDatasetOpened) {
//            this.openDatasets();
//        }
        this.openDatasets();

        String verifyfn = this._outFilePath + File.separator + "Verify_" + this._name + "_Station_"
                + this._verifyMethod.getMethodType().toString() + ".csv";
        List<LocalDateTime> times = this.getVerifyTimes();

        switch (this._verifyMethod.getMethodType()) {
            case DICHOTOMOUS:
            case MULTICATEGORY:                
                //Verification
                try {
                    BufferedWriter sw = new BufferedWriter(new FileWriter(new File(verifyfn)));
                    String[] sNames = VerifyMethod.getScoreNames(this._verifyMethod.getMethodType());
                    String sName = sNames[0];
                    for (int i = 1; i < sNames.length; i++) {
                        sName += "," + sNames[i];
                    }
                    String title = "Group,Time,Region," + sName;
                    sw.write(title);
                    sw.newLine();
                    List<Object> results;
                    for (LocalDateTime t : times) {
                        results = run(t);
                        if (results != null) {
                            this.appendVerifyFile(sw, (List<VerifyTable>) results.get(0), t, sNames);
                        }
                    }

                    sw.flush();
                    sw.close();
                } catch (IOException ex) {
                    Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                //Verification
                try {
                    BufferedWriter sw = new BufferedWriter(new FileWriter(new File(verifyfn)));
                    String[] sNames = VerifyMethod.getScoreNames(this._verifyMethod.getMethodType());
                    String sName = sNames[0];
                    for (int i = 1; i < sNames.length; i++) {
                        sName += "," + sNames[i];
                    }
                    String title = "Group,Time,Region," + sName;
                    sw.write(title);
                    sw.newLine();
                    List<Object> results;

                    for (LocalDateTime t : times) {
                        results = run(t);
                        if (results != null) {
                            this.appendVerifyFile(sw, (List<VerifyTable>) results.get(0), t, sNames);
                        }
                    }

                    sw.flush();
                    sw.close();
                } catch (IOException ex) {
                    Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }
    
    /**
     * Run virification calculation
     *
     * @throws IOException
     */
    public void run_Spatial_Station_saveCateData() throws IOException {
//        if (!this._isDatasetOpened) {
//            this.openDatasets();
//        }
        this.openDatasets();

        String verifyfn = this._outFilePath + File.separator + "Verify_" + this._name + "_Station_"
                + this._verifyMethod.getMethodType().toString() + ".csv";
        List<LocalDateTime> times = this.getVerifyTimes();

        switch (this._verifyMethod.getMethodType()) {
            case DICHOTOMOUS:
            case MULTICATEGORY:
                //Spatial output file
                String spctlfn = this._outFilePath + File.separator + "Spatial_" + this._name + "_Station_"
                        + this._verifyMethod.getMethodType().toString() + ".ctl";
                String spdatafn = spctlfn.replace(".ctl", ".dat");
                GrADSDataInfo aDataInfo = new GrADSDataInfo();
                aDataInfo.setFileName(spctlfn);
                aDataInfo.DSET = spdatafn;
                aDataInfo.TITLE = "Spatial data";
                aDataInfo.DTYPE = "station";
                Variable var = new Variable();
                var.setName("Var");
                var.setUnits("n");
                var.setDescription("Spatial value");
                aDataInfo.VARDEF.addVar(var);
                aDataInfo.TDEF.Type = "LEVELS";
                aDataInfo.createDataFile(spdatafn);

                //Verification
                try {
                    BufferedWriter sw = new BufferedWriter(new FileWriter(new File(verifyfn)));
                    String[] sNames = VerifyMethod.getScoreNames(this._verifyMethod.getMethodType());
                    String sName = sNames[0];
                    for (int i = 1; i < sNames.length; i++) {
                        sName += "," + sNames[i];
                    }
                    String title = "Group,Time,Region," + sName;
                    sw.write(title);
                    sw.newLine();
                    List<Object> results;

                    aDataInfo.TDEF.times = times;
                    for (LocalDateTime t : times) {
                        results = run(t);
                        if (results != null) {
                            this.appendVerifyFile(sw, (List<VerifyTable>) results.get(0), t, sNames);
                            if (results.size() > 1) {
                                aDataInfo.writeStationData((StationData) results.get(1));
                            }
                        }
                    }
                    aDataInfo.closeDataFile();
                    aDataInfo.writeGrADSCTLFile();

                    sw.flush();
                    sw.close();
                } catch (IOException ex) {
                    Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                //Verification
                try {
                    BufferedWriter sw = new BufferedWriter(new FileWriter(new File(verifyfn)));
                    String[] sNames = VerifyMethod.getScoreNames(this._verifyMethod.getMethodType());
                    String sName = sNames[0];
                    for (int i = 1; i < sNames.length; i++) {
                        sName += "," + sNames[i];
                    }
                    String title = "Group,Time,Region," + sName;
                    sw.write(title);
                    sw.newLine();
                    List<Object> results;

                    for (LocalDateTime t : times) {
                        results = run(t);
                        if (results != null) {
                            this.appendVerifyFile(sw, (List<VerifyTable>) results.get(0), t, sNames);
                        }
                    }

                    sw.flush();
                    sw.close();
                } catch (IOException ex) {
                    Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    /**
     * Run virification calculation
     */
    public void run_Spatial_Grid() throws IOException {
//        if (!this._isDatasetOpened) {
//            this.openDatasets();
//        }
        this.openDatasets();

        String verifyfn = this._outFilePath + File.separator + "Verify_" + this._name + "_Station_"
                + this._verifyMethod.getMethodType().toString() + ".csv";
        List<LocalDateTime> times = this.getVerifyTimes();

        switch (this._verifyMethod.getMethodType()) {
            case DICHOTOMOUS:
                //Spatial output file
                String spctlfn = this._outFilePath + File.separator + "Spatial_" + this._name + "_Station_"
                        + this._verifyMethod.getMethodType().toString() + ".ctl";
                String spdatafn = spctlfn.replace(".ctl", ".dat");
                GrADSDataInfo aDataInfo = new GrADSDataInfo();
                aDataInfo.setFileName(spctlfn);
                aDataInfo.DSET = spdatafn;
                aDataInfo.TITLE = "Spatial data";
                aDataInfo.DTYPE = "GRIDDED";
                Variable var = new Variable();
                var.setName("Var");
                var.setUnits("n");
                var.setDescription("Spatial value");
                aDataInfo.VARDEF.addVar(var);
                aDataInfo.TDEF.Type = "LEVELS";
                aDataInfo.createDataFile(spdatafn);

                //Verification
                try {
                    BufferedWriter sw = new BufferedWriter(new FileWriter(new File(verifyfn)));
                    String[] sNames = VerifyMethod.getScoreNames(this._verifyMethod.getMethodType());
                    String sName = sNames[0];
                    for (int i = 1; i < sNames.length; i++) {
                        sName += "," + sNames[i];
                    }
                    String title = "Group,Time,Region," + sName;
                    sw.write(title);
                    sw.newLine();
                    List<Object> results;

                    aDataInfo.TDEF.times = times;
                    int n = 0;
                    for (LocalDateTime t : times) {
                        results = run(t);
                        if (results == null) {
                            continue;
                        }

                        this.appendVerifyFile(sw, (List<VerifyTable>) results.get(0), t, sNames);
                        if (results.size() > 1) {
                            GridData gData = (GridData) results.get(1);
                            if (n == 0) {
                                aDataInfo.XDEF.Type = "LINEAR";
                                aDataInfo.XDEF.XNum = gData.getXNum();
                                aDataInfo.XDEF.XMin = (float) gData.getExtent().minX;
                                aDataInfo.XDEF.XDelt = (float) gData.getXDelt();
                                aDataInfo.YDEF.Type = "LINEAR";
                                aDataInfo.YDEF.YNum = gData.getYNum();
                                aDataInfo.YDEF.YMin = (float) gData.getExtent().minY;
                                aDataInfo.YDEF.YDelt = (float) gData.getYDelt();
                            }
                            aDataInfo.writeGridData(gData);
                            n += 1;
                        }
                    }
                    aDataInfo.closeDataFile();
                    aDataInfo.writeGrADSCTLFile();

                    sw.flush();
                    sw.close();
                } catch (IOException ex) {
                    Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                //Verification
                try {
                    BufferedWriter sw = new BufferedWriter(new FileWriter(new File(verifyfn)));
                    String[] sNames = VerifyMethod.getScoreNames(this._verifyMethod.getMethodType());
                    String sName = sNames[0];
                    for (int i = 1; i < sNames.length; i++) {
                        sName += "," + sNames[i];
                    }
                    String title = "Group,Time,Region," + sName;
                    sw.write(title);
                    sw.newLine();
                    List<Object> results;

                    for (LocalDateTime t : times) {
                        results = run(t);
                        this.appendVerifyFile(sw, (List<VerifyTable>) results.get(0), t, sNames);
                    }

                    sw.flush();
                    sw.close();
                } catch (IOException ex) {
                    Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    /**
     * Run verification calculation at one time
     *
     * @param timeIndex Time index
     * @param times Times
     * @return Verify results
     */
    public List<Object> run(int timeIndex, List<LocalDateTime> times) {
        LocalDateTime time = times.get(timeIndex);
        return this.run(time);
    }

    /**
     * Run verification calculation at one time
     *
     * @param timeIndex Time index
     * @return Verify results
     */
    public List<Object> run(int timeIndex) {
        LocalDateTime time = this.getVerifyTimes().get(timeIndex);
        return this.run(time);
    }

    /**
     * Run verification calculation at one time
     *
     * @param time Time
     * @return Verify results
     */
    public List<Object> run(LocalDateTime time) {
        LocalDateTime obsTime = this.getTime(time, this._timeZone, this._obsDataset.getTimeZone());
        LocalDateTime fcstTime = this.getTime(time, _timeZone, this._fcstDataset.getTimeZone());
        Object obsData = this.getObsData(obsTime);
        if (obsData == null) {
            return null;
        }

        Object fcstData = this.getFcstData(fcstTime);
        if (fcstData == null) {
            return null;
        }

        return run((StationData) obsData, (GridData) fcstData, this._statType);
    }

    /**
     * Get verification observation and forecast dataset
     *
     * @param time Verify time
     * @return Observation and forecast data array
     */
    public StationData[] getVerifyData_Station(LocalDateTime time) {
        LocalDateTime obsTime = this.getTime(time, this._timeZone, this._obsDataset.getTimeZone());
        LocalDateTime fcstTime = this.getTime(time, _timeZone, this._fcstDataset.getTimeZone());
        StationData od = (StationData) this.getObsData(obsTime);
        if (new File(this._dataMaskFile).exists()) {
            VectorLayer maskLayer;
            try {
                maskLayer = (VectorLayer) MapDataManage.loadLayer(this._dataMaskFile);
                od = od.maskout((PolygonShape) maskLayer.getShapes().get(0));
            } catch (IOException ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        GridData fd = (GridData) this.getFcstData(fcstTime);
        StationData fds = fd.toStation(od);

        return new StationData[]{od, fds};
    }

    /**
     * Get verification observation and forecast dataset
     *
     * @param time Verify time
     * @param region Statistics region
     * @return Observation and forecast data array
     */
    public StationData[] getVerifyData_Station(LocalDateTime time, String region) {
        LocalDateTime obsTime = this.getTime(time, this._timeZone, this._obsDataset.getTimeZone());
        LocalDateTime fcstTime = this.getTime(time, _timeZone, this._fcstDataset.getTimeZone());
        StationData od = (StationData) this.getObsData(obsTime);
        if (new File(this._dataMaskFile).exists()) {
            VectorLayer maskLayer;
            try {
                maskLayer = (VectorLayer) MapDataManage.loadLayer(this._dataMaskFile);
                od = od.maskout((PolygonShape) maskLayer.getShapes().get(0));
            } catch (IOException ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        GridData fd = (GridData) this.getFcstData(fcstTime);
        StationData fds = fd.toStation(od);

        if (new File(this._statRegionFile).exists()) {
            try {
                VectorLayer maskLayer = (VectorLayer) MapDataManage.loadLayer(this._statRegionFile);
                for (int i = 0; i < maskLayer.getShapeNum(); i++) {
                    String tName = maskLayer.getCellValue("NAME", i).toString();
                    if (tName.equals(region)) {
                        PolygonShape polygonShape = (PolygonShape) maskLayer.getShapes().get(i);
                        StationData maskObsData = od.maskout(polygonShape);
                        StationData maskFcstData = fds.maskout(polygonShape);
                        return new StationData[]{maskObsData, maskFcstData};
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return new StationData[]{od, fds};
    }

    private List<Object> run(StationData obsData, GridData fcstData, StatType statType) {
        //Filter observation station data
        if (new File(this.stFilterFile).exists()){
            try {
                List<String> stations = new ArrayList<>();
                BufferedReader sr = new BufferedReader(new InputStreamReader(new FileInputStream(this.stFilterFile), "utf-8"));
                String line = sr.readLine();
                String[] dataArray;
                while (line != null){
                    line = line.trim();
                    dataArray = line.split("\\s+");
                    stations.addAll(Arrays.asList(dataArray));                    
                    line = sr.readLine();
                }
                sr.close();
                
                obsData = obsData.filter(stations);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Mask observation station data
        if (new File(this._dataMaskFile).exists()) {
            VectorLayer maskLayer;
            try {
                maskLayer = (VectorLayer) MapDataManage.loadLayer(this._dataMaskFile);
                obsData = obsData.maskout((PolygonShape) maskLayer.getShapes().get(0));
            } catch (IOException ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<Object> results = new ArrayList<>();
        List<VerifyTable> tables = new ArrayList<>();
        switch (statType) {
            case StationStat:
                //Interpolate grid data to station data with projection transform
                StationData fstData = fcstData.toStation(obsData);
                //StationData fstData = fcstData.project(this._fcstDataInfo.getProjectionInfo(),
                //this._obsDataInfo.getProjectionInfo(), obsData, ResampleMethods.Bilinear);

                //Get verification table                
                VerifyTable table = VerifyStat.getVerifyTable(obsData, fstData, _verifyMethod);
                table.setName("Whole Region");
                tables.add(table);
                if (new File(this._statRegionFile).exists()) {
                    try {
                        VectorLayer regionLayer = (VectorLayer) MapDataManage.loadLayer(this._statRegionFile);
                        for (int i = 0; i < regionLayer.getShapeNum(); i++) {
                            PolygonShape polygonShape = (PolygonShape) regionLayer.getShapes().get(i);
                            StationData maskObsData = obsData.maskout(polygonShape);
                            StationData maskFcstData = fstData.maskout(polygonShape);
                            table = VerifyStat.getVerifyTable(maskObsData, maskFcstData, _verifyMethod);
                            String tName = regionLayer.getCellValue("NAME", i).toString();
                            table.setName(tName);
                            tables.add(table);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                results.add(tables);

                //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
//                String verifyfn = this._outFilePath + File.separator + "Verify_" + this._name + "_Station_"
//                        + format.format(time) + ".txt";
//                this.saveVerifyFile(verifyfn, tables);
                //Output categorical data
                switch (this._verifyMethod.getMethodType()) {
                    case DICHOTOMOUS:
                    case MULTICATEGORY:
                        StationData cateData = VerifyStat.categorical(obsData, fstData, this._verifyMethod);
                        results.add(cateData);
//                        //Save categorical data to file                
//                        String catefn = this._outFilePath + File.separator + "Categorical_" + this._name + "_Station_"
//                                + format.format(time) + ".txt";
//                        cateData.saveAsCSVFile(catefn, "Categorical");
                        break;
                    case CONTINUOUS:
                        results.add(obsData);
                        results.add(fstData);
                        break;
                }
                break;
            case GridStat:
                //Project observation data
                obsData = obsData.project(this._obsDataInfo.getProjectionInfo(), this._fcstDataInfo.getProjectionInfo());

                //Interpolate observation station data to grid data
                GridDataSetting gDataSet = fcstData.getGridDataSetting();
                InterpolationSetting interSet = new InterpolationSetting();
                interSet.setGridDataSetting(gDataSet);
                interSet.setInterpolationMethod(InterpolationMethods.IDW_Neighbors);
                interSet.setMinPointNum(9);
                interSet.setMissingValue(obsData.missingValue);
                GridData ogridData = obsData.interpolateData(interSet);

                //Get verify table
                tables = new ArrayList<>();
                table = VerifyStat.getVerifyTable(ogridData, fcstData, _verifyMethod);
                table.setName("Whole Region");
                tables.add(table);
                if (new File(this._statRegionFile).exists()) {
                    try {
                        VectorLayer maskLayer = (VectorLayer) MapDataManage.loadLayer(this._statRegionFile);
                        for (int i = 0; i < maskLayer.getShapeNum(); i++) {
                            PolygonShape polygonShape = (PolygonShape) maskLayer.getShapes().get(i);
                            GridData maskObsData = ogridData.maskout(polygonShape);
                            GridData maskFcstData = fcstData.maskout(polygonShape);
                            table = VerifyStat.getVerifyTable(maskObsData, maskFcstData, _verifyMethod);
                            table.setName("Mask_" + String.valueOf(i));
                            tables.add(table);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(VerifyGroup.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                results.add(tables);

                //format = new SimpleDateFormat("yyyy-MM-dd-HH");
//                verifyfn = this._outFilePath + File.separator + "Verify_" + this._name + "_Grid_"
//                        + format.format(time) + ".txt";
//                this.saveVerifyFile(verifyfn, tables);
                //Output categorical data
                switch (this._verifyMethod.getMethodType()) {
                    case DICHOTOMOUS:
                    case MULTICATEGORY:
                        DichotomousMethod dMethod = (DichotomousMethod) this._verifyMethod;
                        GridData categData = VerifyStat.categorical(ogridData, fcstData, this._verifyMethod);
                        results.add(categData);
//                        //Save categorical data to file                
//                        String catefn = this._outFilePath + File.separator + "Categorical_" + this._name + "_Grid_"
//                                + format.format(time) + ".txt";
//                        categData.saveAsSurferASCIIFile(catefn);
                        break;
                }
                break;
        }

        return results;
    }

    /**
     * Save verify file from the verify tables
     *
     * @param fileName The verify file name
     * @param tables The verify tables
     */
    public static void saveVerifyFile(String fileName, List<VerifyTable> tables) {
        BufferedWriter sw;
        try {
            sw = new BufferedWriter(new FileWriter(new File(fileName)));
            for (int i = 0; i < tables.size(); i++) {
                VerifyTable table = tables.get(i);
                sw.write("***************************");
                sw.newLine();
                sw.write(table.getName());
                sw.newLine();
                String aStr = "Statistics,Score";
                sw.write(aStr);
                Map map = table.getVerifyResult();
                Set<String> key = map.keySet();
                for (String s : key) {
                    aStr = s + "," + map.get(s).toString();
                    sw.newLine();
                    sw.write(aStr);
                }
                if (i < tables.size() - 1) {
                    sw.newLine();
                }
            }
            sw.flush();
            sw.close();
        } catch (IOException ex) {
            Logger.getLogger(StationData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Create verify file
     *
     * @param fileName The verify file name
     * @return BufferedWriter
     * @throws IOException
     */
    public BufferedWriter createVerifyFile(String fileName) throws IOException {
        BufferedWriter sw = new BufferedWriter(new FileWriter(new File(fileName)));
        String[] sNames = VerifyMethod.getScoreNames(this._verifyMethod.getMethodType());
        String sName = sNames[0];
        for (int i = 1; i < sNames.length; i++) {
            sName += "," + sNames[i];
        }
        String title = "Group,Time,Region," + sName;
        sw.write(title);
        sw.newLine();

        return sw;
    }

    private void appendVerifyFile(BufferedWriter sw, List<VerifyTable> tables, LocalDateTime time, String[] sNames) {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < tables.size(); i++) {
                VerifyTable table = tables.get(i);
                String line = this._name + "," + format.format(time) + "," + table.getName();
                Map map = table.getVerifyResult();
                for (String s : sNames) {
                    line = line + "," + String.format("%1$.2f", map.get(s));
                }
                sw.write(line);
                sw.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(StationData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Append verify file
     *
     * @param sw Bufferd writer
     * @param tables The tables
     * @param time The date time
     */
    public void appendVerifyFile(BufferedWriter sw, List<VerifyTable> tables, LocalDateTime time) {
        String[] sNames = VerifyMethod.getScoreNames(tables.get(0).getMethodType());
        appendVerifyFile(sw, tables, time, sNames);
    }

    /**
     * Close verify file
     *
     * @param sw BufferedWriter
     * @throws IOException
     */
    public void closeVerifyFile(BufferedWriter sw) throws IOException {
        sw.close();
    }

    /**
     * Create spatial data file - GrADS data file
     *
     * @param spctlfn GrADS ctl file name
     * @return The GrADS data info
     * @throws IOException
     */
    public static GrADSDataInfo createSpatialFile(String spctlfn) throws IOException {
        String spdatafn = spctlfn.replace(".ctl", ".dat");
        GrADSDataInfo aDataInfo = new GrADSDataInfo();
        aDataInfo.setFileName(spctlfn);
        aDataInfo.DSET = spdatafn;
        aDataInfo.TITLE = "Spatial data";
        aDataInfo.DTYPE = "station";
        Variable var = new Variable();
        var.setName("Var");
        var.setUnits("n");
        var.setDescription("Spatial value");
        aDataInfo.VARDEF.addVar(var);
        aDataInfo.TDEF.Type = "LEVELS";
        aDataInfo.createDataFile(spdatafn);

        return aDataInfo;
    }

    /**
     * Get observation data
     *
     * @param time Time
     * @return Observation data
     */
    public Object getObsData(LocalDateTime time) {
        switch (this._obsDataset.getMeteoDataType()) {
            case MICAPS_1:
            case MICAPS_120:
                String mfn = this._obsDataset.getFileName(time);
                if (!new File(mfn).exists()) {
                    return null;
                }

                this._obsDataInfo = new MeteoDataInfo();
                this._obsDataInfo.openMICAPSData(mfn);
                StationData stData = this._obsDataInfo.getStationData(this._obsDataset.getVariableName());
                return stData;
            default:
                //Date otime = this.getOriginTime(time, this._fcstDataset.getTimeZone());
                int timeIndex = this._obsDataInfo.getDataInfo().getTimes().indexOf(time);
                if (timeIndex < 0) {
                    return null;
                }

                this._obsDataInfo.setTimeIndex(timeIndex);
                this._obsDataInfo.setLevelIndex(this._obsDataset.getLevelIndex());
                if (this._obsDataInfo.isGridData()) {
                    GridData gData = this._obsDataInfo.getGridData(this._obsDataset.getVariableName());
                    return gData;
                } else if (this._obsDataInfo.isStationData()) {
                    stData = this._obsDataInfo.getStationData(this._obsDataset.getVariableName());
                    return stData;
                }
        }

        return null;
    }

    /**
     * Get forecast data
     *
     * @param time Time
     * @return Forecast data
     */
    public Object getFcstData(LocalDateTime time) {
        //Date otime = this.getOriginTime(time, this._fcstDataset.getTimeZone());
        int timeIndex = this._fcstDataInfo.getDataInfo().getTimes().indexOf(time);
        if (timeIndex < 0) {
            return null;
        }

        this._fcstDataInfo.setTimeIndex(timeIndex);
        this._fcstDataInfo.setLevelIndex(this._fcstDataset.getLevelIndex());
        if (this._fcstDataInfo.isGridData()) {
            GridData gData = this._fcstDataInfo.getGridData(this._fcstDataset.getVariableName());
            return gData;
        } else if (this._fcstDataInfo.isStationData()) {
            StationData stData = this._fcstDataInfo.getStationData(this._fcstDataset.getVariableName());
            return stData;
        }

        return null;
    }

    /**
     * Export to xml document
     *
     * @param doc xml document
     * @param parent Parent xml element
     */
    public void exportToXML(Document doc, Element parent) {
        Element group = doc.createElement("Group");
        Attr nameAttr = doc.createAttribute("Name");
        Attr verifyCategoryAttr = doc.createAttribute("VerifyCategory");
        Attr statTypeAttr = doc.createAttribute("StatisticType");
        Attr timeZoneAttr = doc.createAttribute("TimeZone");
        Attr outputDirAttr = doc.createAttribute("OutputDirectory");
        Attr dataMaskFileAttr = doc.createAttribute("DataMaskFile");
        Attr statRegionFileAttr = doc.createAttribute("StatRegionFile");

        nameAttr.setValue(this._name);
        verifyCategoryAttr.setValue(this._verifyCategory.toString());
        statTypeAttr.setValue(this._statType.toString());
        timeZoneAttr.setValue(Globals.getTimeZoneString(this._timeZone));
        outputDirAttr.setValue(this._outFilePath);
        dataMaskFileAttr.setValue(this._dataMaskFile);
        statRegionFileAttr.setValue(this._statRegionFile);

        group.setAttributeNode(nameAttr);
        group.setAttributeNode(verifyCategoryAttr);
        group.setAttributeNode(statTypeAttr);
        group.setAttributeNode(timeZoneAttr);
        group.setAttributeNode(outputDirAttr);
        group.setAttributeNode(dataMaskFileAttr);
        group.setAttributeNode(statRegionFileAttr);

        //Verify method
        Element method = doc.createElement("Method");
        Attr methodType = doc.createAttribute("MethodType");
        methodType.setValue(this._verifyMethod.getMethodType().toString());
        method.setAttributeNode(methodType);
        Element parameter = doc.createElement("Parameter");
        switch (this._verifyMethod.getMethodType()) {
            case DICHOTOMOUS:
                Attr minTthresholdAttr = doc.createAttribute("MinThreshold");
                Attr maxTthresholdAttr = doc.createAttribute("MaxThreshold");
                DichotomousMethod dMethod = (DichotomousMethod) this._verifyMethod;
                minTthresholdAttr.setValue(String.valueOf(dMethod.getDataRange().getMinThreshold()));
                maxTthresholdAttr.setValue(String.valueOf(dMethod.getDataRange().getMaxThreshold()));
                parameter.setAttributeNode(minTthresholdAttr);
                parameter.setAttributeNode(maxTthresholdAttr);
                break;
            case MULTICATEGORY:
                Attr values = doc.createAttribute("Values");
                MultiCategoryMethod mMethod = (MultiCategoryMethod) this._verifyMethod;
                values.setValue(mMethod.valuesToString());
                parameter.setAttributeNode(values);
                break;
            case CONTINUOUS:

                break;
        }
        method.appendChild(parameter);
        group.appendChild(method);

        //Times
        Element times = doc.createElement("Times");
        Attr timeSelAttr = doc.createAttribute("TimeSelect");
        timeSelAttr.setValue(this._timeSelect.toString());
        times.setAttributeNode(timeSelAttr);
        switch (this._timeSelect) {
            case TimeIndex:
                for (int i : this._timeIndices) {
                    Element time = doc.createElement("Time");
                    Attr idxAttr = doc.createAttribute("Index");
                    idxAttr.setValue(String.valueOf(i));
                    time.setAttributeNode(idxAttr);
                    times.appendChild(time);
                }
                break;
            case TimeValue:
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                for (LocalDateTime t : this._times) {
                    Element time = doc.createElement("Time");
                    Attr tvalueAttr = doc.createAttribute("Value");
                    tvalueAttr.setValue(format.format(t));
                    time.setAttributeNode(tvalueAttr);
                    times.appendChild(time);
                }
                break;
        }
        group.appendChild(times);

        //Observation dataset
        this._obsDataset.exportToXML(doc, group);

        //Forecast dataset
        this._fcstDataset.exportToXML(doc, group);

        parent.appendChild(group);
    }

    /**
     * Import from xml node
     *
     * @param node xml node
     */
    public void importFromXML(Node node) {
        this._name = node.getAttributes().getNamedItem("Name").getNodeValue();
        this._verifyCategory = VerifyCategory.valueOf(node.getAttributes().getNamedItem("VerifyCategory").getNodeValue());
        this._statType = StatType.valueOf(node.getAttributes().getNamedItem("StatisticType").getNodeValue());
        this._outFilePath = node.getAttributes().getNamedItem("OutputDirectory").getNodeValue();
        this._dataMaskFile = node.getAttributes().getNamedItem("DataMaskFile").getNodeValue();
        this._statRegionFile = node.getAttributes().getNamedItem("StatRegionFile").getNodeValue();
        this._timeZone = Globals.getTimeZone(node.getAttributes().getNamedItem("TimeZone").getNodeValue());

        //Method
        Node methodNode = ((Element) node).getElementsByTagName("Method").item(0);
        MethodType methodType = MethodType.valueOf(methodNode.getAttributes().getNamedItem("MethodType").getNodeValue());
        Node parameterNode = ((Element) methodNode).getElementsByTagName("Parameter").item(0);
        switch (methodType) {
            case DICHOTOMOUS:
                DichotomousMethod dMethod = new DichotomousMethod();
                Node minTNode = parameterNode.getAttributes().getNamedItem("MinThreshold");
                Node maxTNode = parameterNode.getAttributes().getNamedItem("MaxThreshold");
                if (minTNode != null && maxTNode != null) {
                    String minTstr = parameterNode.getAttributes().getNamedItem("MinThreshold").getNodeValue();
                    String maxTstr = parameterNode.getAttributes().getNamedItem("MaxThreshold").getNodeValue();
                    if (!minTstr.equalsIgnoreCase("null")) {
                        dMethod.getDataRange().setMinThreshold(Double.parseDouble(minTstr));
                    }
                    if (!maxTstr.equalsIgnoreCase("null")) {
                        dMethod.getDataRange().setMaxThreshold(Double.parseDouble(maxTstr));
                    }
                }
                this._verifyMethod = dMethod;
                break;
            case MULTICATEGORY:
                MultiCategoryMethod mMethod = new MultiCategoryMethod();
                mMethod.setValues(parameterNode.getAttributes().getNamedItem("Values").getNodeValue());
                this._verifyMethod = mMethod;
                break;
            case CONTINUOUS:
                this._verifyMethod = new ContinuousMethod();
                break;
        }

        //Times
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Node timesNode = ((Element) node).getElementsByTagName("Times").item(0);
        this._timeSelect = TimeSelect.valueOf(timesNode.getAttributes().getNamedItem("TimeSelect").getNodeValue());
        switch (this._timeSelect) {
            case TimeIndex:
                NodeList timeNodeList = ((Element) timesNode).getElementsByTagName("Time");
                this._timeIndices.clear();
                for (int i = 0; i < timeNodeList.getLength(); i++) {
                    Node timeNode = timeNodeList.item(i);
                    this._timeIndices.add(Integer.parseInt(timeNode.getAttributes().getNamedItem("Index").getNodeValue()));
                }
                break;
            case TimeValue:
                timeNodeList = ((Element) timesNode).getElementsByTagName("Time");
                this._times.clear();
                for (int i = 0; i < timeNodeList.getLength(); i++) {
                    Node timeNode = timeNodeList.item(i);
                    this._times.add(LocalDateTime.parse(timeNode.getAttributes().
                            getNamedItem("Value").getNodeValue(), format));
                }
                break;
        }
        if (!this._isAllTime) {
        }

        //Datasets
        this._obsDataset = new Dataset("Temp");
        this._obsDataset.setDataSourceType(DataSourceType.Observation);
        Node obsNode = ((Element) node).getElementsByTagName("ObservationDataset").item(0);
        this._obsDataset.importFromXML(obsNode);

        this._fcstDataset = new Dataset("Temp");
        this._fcstDataset.setDataSourceType(DataSourceType.Forecast);
        Node fcstNode = ((Element) node).getElementsByTagName("ForecastDataset").item(0);
        this._fcstDataset.importFromXML(fcstNode);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    // </editor-fold>
}
