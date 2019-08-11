package com.repositories;

import com.models.Activity;
import com.models.Goal;
import com.models.GoalActivityRelation;

public class SqlViews {

    private static final String activity = Activity.class.getSimpleName();
    private static final String goal = Goal.class.getSimpleName();
    private static final String goalActivityRelation = GoalActivityRelation.class.getSimpleName();

    public static final String GoalActivityRelationView =
            "SELECT ga.Id, ga.ActivityId, ga.GoalId, ga.Weight," +
                    "g.Name AS GoalName, g.CompletionWeight, g.Description AS GoalDescription," +
                    "g.CreatedAt AS GoalCreatedAt, g.ExpectedFinish, g.Finished, g.Type AS GoalType," +
                    "g.Percentage, g.HexColor AS GoalHexColor," +
                    "a.Name AS ActivityName, a.ExpectedDurations, a.Description AS ActivityDescription," +
                    "a.Type AS ActivityType, a.CreatedAt AS ActivityCreatedAt, a.HexColor AS ActivityHexColor " +
                    "FROM " + goalActivityRelation + " ga " +
                    "LEFT JOIN " + activity + " a on ga.ActivityId = a.Id " +
                    "LEFT JOIN " + goal + " g on ga.GoalId = g.Id";
}
