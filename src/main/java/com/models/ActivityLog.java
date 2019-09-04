package com.models;

import com.enumerations.ActivityRating;

import java.util.HashMap;
import java.util.Map;

public class ActivityLog {
    public int id;
    public int duration;
    public ActivityRating rating;
    public long createdAt;
    public long completedAt;
    public int activityId;

    public Activity activity;

    public ActivityLog(int id, int duration, int rating, long createdAt, long completedAt, int activityId) {
        this.id = id;
        this.duration = duration;
        this.rating = ActivityRating.parseFromInt(rating);
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.activityId = activityId;
    }

    public ActivityLog(int id, int duration, int rating, long createdAt, long completedAt, int activityId,
                       Activity activity) {
        this.id = id;
        this.duration = duration;
        this.rating = ActivityRating.parseFromInt(rating);
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.activityId = activityId;
        this.activity = activity;
    }

    @Override
    public String toString(){
        return this.activity.name + " - " + this.duration + " min. " + this.activity.description;
    }

    /**
     * Construct a dictionary consisting of {fieldName : fieldValue}
     * @return
     */
    public Map<String, String> getFieldMap(){
        HashMap<String, String> fields = new HashMap<>();

        fields.put("Duration", String.valueOf(this.duration));
        fields.put("Rating", String.valueOf(this.rating.getRating()));
        fields.put("CreatedAt", String.valueOf(this.completedAt));
        fields.put("CompletedAt", String.valueOf(this.completedAt));
        fields.put("ActivityId", String.valueOf(this.activityId));

        return fields;
    }
}
