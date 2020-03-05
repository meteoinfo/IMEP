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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author yaqiang
 */
public class VerifyCase {
    // <editor-fold desc="Variables">

    private String _name;
    private List<VerifyGroup> _verifyGroups;
    // </editor-fold>
    // <editor-fold desc="Constructor">

    /**
     * Constructor
     *
     * @param name Verify case name
     */
    public VerifyCase(String name) {
        this._name = name;
        this._verifyGroups = new ArrayList<VerifyGroup>();
    }
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">

    /**
     * Get verify case name
     *
     * @return Verify case name
     */
    public String getName() {
        return this._name;
    }

    /**
     * Set verify case name
     *
     * @param name Name
     */
    public void setName(String name) {
        this._name = name;
    }

    /**
     * Get verify groups
     *
     * @return Verify groups
     */
    public List<VerifyGroup> getVerifyGroups() {
        return this._verifyGroups;
    }
    // </editor-fold>
    // <editor-fold desc="Methods">

    /**
     * Add a verify group
     *
     * @param group Verify group
     */
    public void addVerifyGroup(VerifyGroup group) {
        this._verifyGroups.add(group);
    }

    /**
     * Remove a verify group
     *
     * @param group Verify group
     */
    public void removeVerifyGroup(VerifyGroup group) {
        this._verifyGroups.remove(group);
    }

    /**
     * Find a verify group by name
     *
     * @param groupName Group name
     * @return Finded verify group
     */
    public VerifyGroup findVerifyGroup(String groupName) {
        for (VerifyGroup group : this._verifyGroups) {
            if (group.getName().equalsIgnoreCase(groupName)) {
                return group;
            }
        }

        return null;
    }

    /**
     * Get group names
     *
     * @return Group names
     */
    public String[] getGroupNames() {
        String[] groupNames = new String[this._verifyGroups.size()];
        for (int i = 0; i < this._verifyGroups.size(); i++) {
            groupNames[i] = this._verifyGroups.get(i).getName();
        }

        return groupNames;
    }

    /**
     * Run verification case
     */
    public void run() {
        for (VerifyGroup group : this._verifyGroups) {
            try {
                group.run();
            } catch (IOException ex) {
                Logger.getLogger(VerifyCase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //VerifyStat.plotData();
    }

    /**
     * Import from XML file
     *
     * @param fileName XML file name
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void importFromXMLFile(String fileName) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(fileName);
        Element root = doc.getDocumentElement();

        this._name = root.getAttributes().getNamedItem("Name").getNodeValue();

        Element groups = (Element) root.getElementsByTagName("Groups").item(0);
        NodeList groupNodeList = groups.getElementsByTagName("Group");
        this._verifyGroups.clear();
        for (int i = 0; i < groupNodeList.getLength(); i++) {
            Node groupNode = groupNodeList.item(i);
            VerifyGroup vgroup = new VerifyGroup("Temp");
            vgroup.importFromXML(groupNode);
            this._verifyGroups.add(vgroup);
        }
    }

    /**
     * Export to a XML file
     *
     * @param fileName XML file name
     * @throws ParserConfigurationException
     */
    public void exportToXMLFile(String fileName) throws ParserConfigurationException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("VerifyCase");
            Attr nameAttr = doc.createAttribute("Name");
            nameAttr.setValue(_name);
            root.setAttributeNode(nameAttr);
            doc.appendChild(root);

            Element groups = doc.createElement("Groups");
            for (VerifyGroup vgroup : this._verifyGroups) {
                vgroup.exportToXML(doc, groups);
            }
            root.appendChild(groups);

            //Save to file
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            //transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(VerifyCase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VerifyCase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(VerifyCase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>
}
