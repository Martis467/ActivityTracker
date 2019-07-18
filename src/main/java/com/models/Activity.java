package com.models;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.exception.UIException;

import java.util.EnumSet;

public class Activity {
    public int id;
    public String name;
    public EnumSet<ActivityDuration> expectedDurations;
    public int weight;
    public String description;
    public ActivityType type;
    public long createdAt;
    public int hexColor;

    public Activity() {}

    public Activity(int id, String name, int expectedDurations, int weight, String description, int type, long createdAt, int hexColor) {
        this.id = id;
        this.name = name;
        this.expectedDurations = ActivityDuration.getFlags(expectedDurations);
        this.weight = weight;
        this.description = description;
        this.type = ActivityType.parseFromInt(type);
        this.createdAt = createdAt;
        this.hexColor = hexColor;
    }

    public void validate() throws UIException {
        if (weight < 1 || weight > 5)
            throw new UIException("Weight can only be between 1 and 5", "Wrong fields");
    }

    public String getText(){
        return name + System.lineSeparator()
                + type + System.lineSeparator()
                + description;
    }
}
