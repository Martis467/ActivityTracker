package com.models;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.exception.UIException;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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
        if(weight <= 0)
            throw new UIException("Activity weight can only be positive", "Wrong fields");

        if (weight < 1)
            throw new UIException("Weight should be at least 1, no point in doing the activity otherwise", "Shame");

        if (weight > 5)
            throw new UIException("Weight is to high, no such activity exists", "Overkill");
    }

    /**
     * @return display text with the activity name, type and description
     */
    public String getText(){
        return name + System.lineSeparator()
                + type + System.lineSeparator()
                + description;
    }


    /**
     * Construct a dictionary consisting of {fieldName : fieldValue}
     * @return
     */
    public Map<String, String> getFieldMap(){
        HashMap<String, String> fields = new HashMap<>();

        fields.put("Name", "\'" + this.name + "\'");
        fields.put("ExpectedDurations", "\'" + ActivityDuration.getStatusValue(this.expectedDurations) + "\'");
        fields.put("Weight", String.valueOf(this.weight));
        fields.put("Description", "\'" + this.description + "\'");
        fields.put("Type", String.valueOf(this.type.getId()));
        fields.put("CreatedAt", String.valueOf(this.createdAt));
        fields.put("HexColor", String.valueOf(this.hexColor));

        return fields;
    }
}
