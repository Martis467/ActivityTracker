package com.models;

import com.enumerations.ActivityType;

public class Goal {
    public int id;
    public String name;
    public String description;
    public long createdAt;
    public long expectedFinish;
    public long finished;
    public ActivityType type;
    public float percentage;
    public long hexColor;
}
