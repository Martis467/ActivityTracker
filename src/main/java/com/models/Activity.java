package com.models;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.utilities.Extensions;
import com.utilities.JFXUtilities;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class Activity {
    public int id;
    public String name;
    public EnumSet<ActivityDuration> expectedDurations;
    public String description;
    public ActivityType type;
    public long createdAt;
    public int hexColor;
    public boolean disabled;

    public Activity() {}

    public Activity(int id, String name, int expectedDurations, String description, int type, long createdAt,
                    int hexColor) {
        this.id = id;
        this.name = name;
        this.expectedDurations = ActivityDuration.getFlags(expectedDurations);
        this.description = description;
        this.type = ActivityType.parseFromInt(type);
        this.createdAt = createdAt;
        this.hexColor = hexColor;
    }

    /**
     * @return display text with the activity name, type and description
     */
    public String getShortText(){
        return name + System.lineSeparator()
                + type + System.lineSeparator()
                + description;
    }

    public String getLongText(){
        return name + " (" + type + ")" + System.lineSeparator()
                + description + System.lineSeparator()
                + Extensions.getDate(createdAt) + System.lineSeparator();
    }
    /**
     * Construct a dictionary consisting of {fieldName : fieldValue}
     * @return
     */
    public Map<String, String> getFieldMap(){
        HashMap<String, String> fields = new HashMap<>();

        fields.put("Name", "\'" + this.name + "\'");
        fields.put("ExpectedDurations", "\'" + ActivityDuration.getStatusValue(this.expectedDurations) + "\'");
        fields.put("Description", "\'" + this.description + "\'");
        fields.put("Type", String.valueOf(this.type.getId()));
        fields.put("CreatedAt", String.valueOf(this.createdAt));
        fields.put("HexColor", String.valueOf(this.hexColor));
        fields.put("Disabled", String.valueOf(Extensions.boolToInt(this.disabled)));

        return fields;
    }
}
