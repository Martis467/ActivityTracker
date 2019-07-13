package com.enumerations;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Stream;

public enum ActivityDuration {

    // 15 min interval
    fifteenMinutes("15min", 15),
    thirtyMinutes("30min", 30),
    fortyFiveMinutes("45min", 45),
    seventyFiveMinutes("75min", 75),
    ninety("90min", 90),

    // 20 min interval
    twentyMinutes("20min", 20),
    fortyMinutes("40min", 40),
    eightyMinutes("80min", 80),
    hundredMinutes("100min", 100),

    // Extra intervals
    twentyFiveMinutes("25min", 25),
    sixtyMinutes("60min", 60),
    hundredTwentyMinutes("120min", 120);

    private final String displayName;
    private final int duration;

    ActivityDuration(String displayName, int duration) {
        this.displayName = displayName;
        this.duration = duration;
    }

    public static Stream getValues() {
        return Arrays.stream(ActivityDuration.values())
                .sorted((d1, d2) -> ((Integer) d1.duration).compareTo(d2.duration));
    }

    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Translates an integer code into a EnumSet of enum flags
     * @param statusValue
     * @return Enum flags
     */
    public static EnumSet<ActivityDuration> getFlags(int statusValue) {
        EnumSet flags = EnumSet.noneOf(ActivityDuration.class);
        Arrays.stream(ActivityDuration.values()).forEach(flag -> {
                    int flagValue = flag.duration;
                    if ((flagValue & statusValue) == flagValue)
                        flags.add(flag);
                }
        );
        return flags;
    }

    /**
     * Translates a set of enum flags to into a numeric status code
     * @param flags
     * @return integer code representing EnumSet
     */
    public static int getStatusValue(EnumSet<ActivityDuration> flags) {
        return flags.stream().mapToInt(flag -> flag.duration).reduce(0, (a, b) -> a | b);
    }
}
