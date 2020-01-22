package com.enumerations;

import java.util.Arrays;

public enum ActivityType {
    Workout(0, "Working out"),
    Read(1, "Reading"),
    Program(2, "Programming"),
    Game(3, "Playing video games"),
    Music(4, "Playing music"),
    Educate(5, "Educating");

    private final int id;
    private final String displayName;

    ActivityType(int id, String displayName){
        this.id = id;
        this.displayName = displayName;
    }

    public static ActivityType parseFromInt(int id){
        return Arrays.stream(values())
                .filter(e -> e.id == id).findFirst().orElseThrow();
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getId() {return id;}
}
