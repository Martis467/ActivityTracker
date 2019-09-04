package com.utilities;

import com.exception.UIException;
import com.exception.UIExceptionType;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Static class that works kind of like C# extensions
 */
public class Extensions {

    private static final String NUMERIC_REGEX = "[0-9]+";

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
    public static long getNowInUnixTime(){return new Date().getTime();}

    /**
     * Transforms given string time to minutes
     * 2h 15min = 135 min
     * 135 = 135 min
     * @param time string time stamp
     * @return minutes
     */
    public static int stringTimeToMinutes(String time) throws UIException {
        String[] array = time.split(" ");

        if (array.length > 2)
            throw new UIException("Time", "Wrong time format", UIExceptionType.InvalidFields);

        if(array.length == 1){
            boolean isHours = time.contains("h");

            return isHours?
                    Integer.parseInt(time.replace("h", "")) * 60 :
                    Integer.parseInt(time.replace("min", ""));
        }

        String hours = array[0].replace("h", "");

        if (hours.isEmpty() || !hours.matches(NUMERIC_REGEX))
            throw new UIException("Time", "Wrong time format", UIExceptionType.InvalidFields);

        String minutes = array[1].replace("min", "");

        if(!minutes.matches(NUMERIC_REGEX))
            throw new UIException("Time", "Wrong time format", UIExceptionType.InvalidFields);

        return Integer.parseInt(hours) * 60 + Integer.parseInt(minutes);
    }
}
