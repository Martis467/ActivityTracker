package com.enumerations;

import java.util.Arrays;

public enum ActivityType {
    Workout(0),
    Read(1),
    Program(2),
    Game(3),
    PlayMusic(4),
    Educate(5);

    private final int id;

    ActivityType(int id){
        this.id = id;
    }

    public static ActivityType parseFromInt(int id){
        return Arrays.stream(values())
                .filter(e -> e.id == id).findFirst().orElseThrow();
    }
}
