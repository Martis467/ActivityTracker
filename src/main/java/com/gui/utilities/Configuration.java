package com.gui.utilities;

/**
 * Static class to easily switch between testing and prod environments.
 * A test.config.json would be a more formal solution, but for this case a class works fine.
 */
public class Configuration {
    private static boolean test = true;
    private static final String testConnectionString = "jdbc:sqlite:database.db";
    private static final String prodConnectionString = "jdbc:sqlite:prod.db";


    /**
     * Sets environment to production
     */
    public static void setProd(){test=false;}

    public static String getConnectionString(){
        if(test) return testConnectionString;
        else return prodConnectionString;
    }
}
