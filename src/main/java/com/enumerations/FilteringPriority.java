package com.enumerations;


public enum FilteringPriority {

    GoalCloseToCompletion("Goals closest to completion"),
    MaximumWeight("Activities that maximise total weight"),
    SeldomGoals("Goals that are seldom worked"),
    SeldomActivities("Activities that are seldom done"),
    GoalsCloseToDeadline("Goals close to deadline");

    private final String displayName;

    FilteringPriority(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
