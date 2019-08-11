package com.utilities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Static class that works kind of like C# extensions
 */
public class Extensions {

    /**
     * converts this bool to int
     * @param b
     * @return 1 : 0
     */
    public static int boolToInt(boolean b) {return b? 1 : 0;}

    /**
     * Returns LocalDate from a unix timestamp in millis
     * @param u unix time stamp
     * @return LocalDate
     */
    public static LocalDate getDate(long u){
        return Instant.ofEpochMilli(u).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Returns unix time stamp from LocalDate
     * @param d LocalDate
     * @return unix time stamp in ms
     */
    public static long getUnixTimeStamp(LocalDate d) {
        return d.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * Now in unix time stamp
     * @return unix time stamp in ms
     */
    public static long getNowInUnixTime(){return new Date().getTime()/1000;}
}
