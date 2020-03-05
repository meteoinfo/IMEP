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
package org.meteothink.imep.global;

/**
 *
 * @author yaqiang
 */
public class Globals {
    /**
     * Get time zone from time zone string
     * @param timeZoneStr Time zone string - GMT+8
     * @return Time zone
     */
    public static int getTimeZone(String timeZoneStr){
        int tz = 0;
        timeZoneStr = timeZoneStr.trim();
        String str = timeZoneStr.substring(3);
        if (str.substring(0, 1).equals("+"))
            str = str.substring(1);
        
        tz = Integer.parseInt(str);
        
        return tz;
    }
    
    /**
     * Get time zone string from time zone
     * @param timeZone Time zone
     * @return Time zone string
     */
    public static String getTimeZoneString(int timeZone){
        if (timeZone >= 0)
            return "GMT+" + String.valueOf(timeZone);
        else
            return "GMT" + String.valueOf(timeZone);
    }
}
