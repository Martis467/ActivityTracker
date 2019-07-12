package com.models;

import com.enumerations.ActivityType;

public class Activity {
    public int id;
    public String name;
    public int expectedDuration;
    public int weight;
    public String description;
    public ActivityType type;
    public long createdAt;
    public long hexColor;

    public Activity(int id, String name, int expectedDuration, int weight, String description, int type, long createdAt, long hexColor) {
        this.id = id;
        this.name = name;
        this.expectedDuration = expectedDuration;
        this.weight = weight;
        this.description = description;
        this.type = ActivityType.parseFromInt(type);
        this.createdAt = createdAt;
        this.hexColor = hexColor;
    }
}
