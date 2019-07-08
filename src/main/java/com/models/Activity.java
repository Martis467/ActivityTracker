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
}
