package com.enumerations;

import java.util.Arrays;

public enum GoalType {

    Linguistic(0, "Learning a new language"),
    Skill(1, "Learn a new skill"),
    Project(2, "Realize a project"),
    Science(3, "Learn a new science subject/topic"),
    Fitness(4, "Fitness"),
    Exploring(5, "Explore a new activity");

    private final int id;
    private final String displayName;

    GoalType(int id, String displayName){
        this.id = id;
        this.displayName = displayName;
    }

    public static GoalType parseFromInt(int id){
        return Arrays.stream(values())
                .filter(e -> e.id == id).findFirst().orElseThrow();
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getId() {return id;}
}
