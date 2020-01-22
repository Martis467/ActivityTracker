package com.enumerations;


public enum FilteringPriority {

    None("No priority"),
    GoalCloseToCompletion("Goals closest to completion"),
    GoalsCloseToDeadline("Goals close to deadline"),
    GoalsFarFromCompletion("Goals far from completion"),
    SeldomGoals("Goals that are seldom worked towards"),
    SeldomActivities("Activities that are seldom done");

    private final String displayName;

    FilteringPriority(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
