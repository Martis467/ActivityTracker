package com.models;

import com.exception.UIException;

import java.util.HashMap;
import java.util.Map;

public class GoalActivityRelation {
    public int id;
    public int activityId;
    public int goalId;
    public int weight;

    public GoalActivityRelation() {}

    public GoalActivityRelation(int activityId, int goalId, int weight){
        this.activityId = activityId;
        this.goalId = goalId;
        this.weight = weight;
    }

    public GoalActivityRelation(int id, int activityId, int goalId, int weight) {
        this.id = id;
        this.activityId = activityId;
        this.goalId = goalId;
        this.weight = weight;
    }

    /**
     * Returns all zero values goal activity, denoting relation does not exists
     * @return
     */
    public static GoalActivityRelation GetDefault(){
        return new GoalActivityRelation(0,0,0,0);
    }

    public void validate() throws UIException {
        if (weight < 1)
            throw new UIException("Weight should be at least 1, no point in doing the activity otherwise", "Shame");

        if (weight > 5)
            throw new UIException("Weight is to high, no such activity exists", "Overkill");
    }

    /**
     * Construct a dictionary consisting of {fieldName : fieldValue}
     * @return
     */
    public Map<String, String> getFieldMap(){
        HashMap<String, String> fields = new HashMap<>();

        fields.put("ActivityId", String.valueOf(this.activityId));
        fields.put("GoalId", String.valueOf(this.goalId));
        fields.put("Weight", String.valueOf(this.weight));

        return fields;
    }
}
