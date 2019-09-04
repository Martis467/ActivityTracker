package com.enumerations;

import java.util.Arrays;

public enum ActivityRating {

    Terrible(1, "Terrible"),
    Bad(2, "Bad"),
    Average(3, "Average"),
    Good(4, "Good"),
    VeryGood(5, "Very good");

    private final int rating;
    private final String displayName;

    ActivityRating(int rating, String displayName){
        this.rating = rating;
        this.displayName = displayName;
    }

    public static ActivityRating parseFromInt(int rating){
        return Arrays.stream(values())
                .filter(e -> e.rating == rating).findFirst().orElseThrow();
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getRating() {return rating;}
}
