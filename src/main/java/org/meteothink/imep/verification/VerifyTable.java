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

import java.util.Map;
import java.util.Set;

/**
 *
 * @author yaqiang
 */
public abstract class VerifyTable {

    // <editor-fold desc="Variables">
    private String _name = "Default";

    // </editor-fold>
    // <editor-fold desc="Constructor">
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">
    /**
     * Get name
     *
     * @return Name
     */
    public String getName() {
        return this._name;
    }

    /**
     * Set name
     *
     * @param value Name
     */
    public void setName(String value) {
        this._name = value;
    }

    // </editor-fold>
    // <editor-fold desc="Methods">
    /**
     * Get verification result
     *
     * @return Verification result
     */
    public abstract Map getVerifyResult();

    /**
     * Get verify method type
     *
     * @return Verify method type
     */
    public abstract MethodType getMethodType();

    /**
     * Get verify score names
     *
     * @return Score names string
     */
    public abstract String getScoreNames();

    /**
     * To string
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String line;
        sb.append("Score\tValue\n");
        Map map = this.getVerifyResult();
        Set<String> key = map.keySet();
        for (String s : key) {
            line = s + "\t" + String.format("%.2f", map.get(s)) + "\n";
            sb.append(line);
        }

        return sb.toString();
    }

    // </editor-fold>
}
