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
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.meteoinfo.data.meteodata.MeteoDataInfo;
import org.meteoinfo.data.meteodata.MeteoDataType;
import org.meteoinfo.data.meteodata.grads.GrADSDataInfo;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author yaqiang
 */
public class Dataset implements Cloneable {
    // <editor-fold desc="Variables">

    private String _name;
    private DataSourceType _dataSource = DataSourceType.Observation;
    private MeteoDataType _meteoDataType = MeteoDataType.NETCDF;
    private String _varName;
    private int levelIndex = 0;
    private String _fileName;
    private int _timeZone = 0;
    private boolean bigEndian = false;

    // </editor-fold>
    // <editor-fold desc="Constructor">
    /**
     * Constructor
     *
     * @param name Dataset name
     */
    public Dataset(String name) {
        this._name = name;
    }

    /**
     * Constructor
     *
     * @param name Dataset name
     * @param dataSource Data source
     * @param meteoDataType MeteoDataType
     * @param varName Variable name
     * @param level The Level index
     */
    public Dataset(String name, DataSourceType dataSource, MeteoDataType meteoDataType, String varName, int level) {
        this(name);
        this._dataSource = dataSource;
        this._meteoDataType = meteoDataType;
        this._varName = varName;
    }

    /**
     * Constructor
     *
     * @param name Dataset name
     * @param dataSource Data source
     * @param meteoDataType MeteoDataType
     * @param varName Variable name
     * @param fileName File name
     * @param level The level index
     */
    public Dataset(String name, DataSourceType dataSource, MeteoDataType meteoDataType, String varName, String fileName, int level) {
        this(name, dataSource, meteoDataType, varName, level);
        this._fileName = fileName;
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
     * Get data source type
     *
     * @return Data source type
     */
    public DataSourceType getDataSourceType() {
        return this._dataSource;
    }

    /**
     * Set data source type
     *
     * @param value Data source type
     */
    public void setDataSourceType(DataSourceType value) {
        this._dataSource = value;
    }

    /**
     * Get MeteoDataType
     *
     * @return MeteoDataType
     */
    public MeteoDataType getMeteoDataType() {
        return this._meteoDataType;
    }

    /**
     * Set MeteoDataType
     *
     * @param value MeteoDataType
     */
    public void setMeteoDataType(MeteoDataType value) {
        this._meteoDataType = value;
    }

    /**
     * Get variable name
     *
     * @return Variable name
     */
    public String getVariableName() {
        return this._varName;
    }

    /**
     * Set variable name
     *
     * @param value Variable name
     */
    public void setVariableName(String value) {
        this._varName = value;
    }
    
    /**
     * Get level index
     * @return Level index
     */
    public int getLevelIndex(){
        return levelIndex;
    }
    
    /**
     * Set level index
     * @param value Level index
     */
    public void setLevelIndex(int value){
        levelIndex = value;
    }

    /**
     * Get file name
     *
     * @return File name
     */
    public String getFileName() {
        return this._fileName;
    }

    /**
     * Set file name
     *
     * @param value File name
     */
    public void setFileName(String value) {
        this._fileName = value;
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
     * Get if is big endian
     * @return Boolean
     */
    public boolean isBigEndian(){
        return this.bigEndian;
    }
    
    /**
     * Set if is big endian
     * @param value Boolean
     */
    public void setBigEndian(boolean value){
        this.bigEndian = value;
    }
    // </editor-fold>
    // <editor-fold desc="Methods">

    /**
     * Get file name by time
     *
     * @param time Time
     * @return File name
     */
    public String getFileName(LocalDateTime time) {
        switch (this._meteoDataType) {
            case MICAPS_1:
                String path = new File(this._fileName).getParent();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyMMddHH");
                String fileName = path + File.separator + format.format(time) + ".000";
                return fileName;
            case MICAPS_120:
                path = new File(this._fileName).getParent();
                format = DateTimeFormatter.ofPattern("yyyyMMddHH");
                fileName = path + File.separator + format.format(time) + ".000";
                return fileName;
            default:
                return this._fileName;
        }
    }

    /**
     * Open data file
     *
     * @return MeteoDataInfo
     */
    public MeteoDataInfo openData() {
        MeteoDataInfo mdi = new MeteoDataInfo();
        switch (this._meteoDataType) {
            case GRADS_GRID:
                mdi.openGrADSData(this._fileName);
                ((GrADSDataInfo)mdi.getDataInfo()).setBigEndian(bigEndian);
                return mdi;
            case NETCDF:
            case GRIB1:
            case GRIB2:
                mdi.openNetCDFData(_fileName);
                return mdi;
            default:
                if (this._meteoDataType.isMICAPS()) {
                    mdi.openMICAPSData(_fileName);
                    return mdi;
                }
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
        String elementName = "ObservationDataset";
        if (this._dataSource == DataSourceType.Forecast) {
            elementName = "ForecastDataset";
        }
        Element dataset = doc.createElement(elementName);
        Attr nameAttr = doc.createAttribute("Name");
        Attr dataSourceAttr = doc.createAttribute("DataSource");
        Attr dataTypeAttr = doc.createAttribute("DataType");
        Attr fileNameAttr = doc.createAttribute("FileName");
        Attr varNameAttr = doc.createAttribute("VariableName");
        Attr timeZoneAttr = doc.createAttribute("TimeZone");
        Attr bigEndianAttr = doc.createAttribute("BigEndian");
        Attr levIdxAttr = doc.createAttribute("LevelIndex");

        nameAttr.setValue(this._name);
        dataSourceAttr.setValue(this._dataSource.toString());
        dataTypeAttr.setValue(this._meteoDataType.toString());
        fileNameAttr.setValue(this._fileName);
        varNameAttr.setValue(this._varName);
        timeZoneAttr.setValue(Globals.getTimeZoneString(this._timeZone));
        bigEndianAttr.setValue(String.valueOf(this.bigEndian));
        levIdxAttr.setValue(String.valueOf(this.levelIndex));

        dataset.setAttributeNode(nameAttr);
        dataset.setAttributeNode(dataSourceAttr);
        dataset.setAttributeNode(dataTypeAttr);
        dataset.setAttributeNode(fileNameAttr);
        dataset.setAttributeNode(varNameAttr);
        dataset.setAttributeNode(timeZoneAttr);
        dataset.setAttributeNode(bigEndianAttr);
        dataset.setAttributeNode(levIdxAttr);

        parent.appendChild(dataset);
    }

    /**
     * Import from xml node
     *
     * @param node xml node
     */
    public void importFromXML(Node node) {
        try {
            this._name = node.getAttributes().getNamedItem("Name").getNodeValue();
            this._dataSource = DataSourceType.valueOf(node.getAttributes().getNamedItem("DataSource").getNodeValue());
            this._meteoDataType = MeteoDataType.valueOf(node.getAttributes().getNamedItem("DataType").getNodeValue());
            this._fileName = node.getAttributes().getNamedItem("FileName").getNodeValue();
            this._varName = node.getAttributes().getNamedItem("VariableName").getNodeValue();
            this._timeZone = Globals.getTimeZone(node.getAttributes().getNamedItem("TimeZone").getNodeValue());
            this.bigEndian = Boolean.parseBoolean(node.getAttributes().getNamedItem("BigEndian").getNodeValue());
            this.levelIndex = Integer.parseInt(node.getAttributes().getNamedItem("LevelIndex").getNodeValue());
        } catch (Exception e){
            
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    // </editor-fold>
}
