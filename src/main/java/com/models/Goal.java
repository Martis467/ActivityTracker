package com.models;

import com.enumerations.GoalType;
import com.exception.UIException;
import com.utilities.Extensions;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class Goal {
    public int id;
    public String name;
    public int completionWeight;
    public String description;
    public long createdAt;
    public long expectedFinish;
    public long finished;
    public GoalType type;
    public float percentage;
    public long hexColor;
    public boolean completed;

    public Goal() {}

    public Goal(int id, String name, int completionWeight, String description, long createdAt, long expectedFinish,
                long finished, int type, float percentage, long hexColor) {
        this.id = id;
        this.name = name;
        this.completionWeight = completionWeight;
        this.description = description;
        this.createdAt = createdAt;
        this.expectedFinish = expectedFinish;
        this.finished = finished;
        this.type = GoalType.parseFromInt(type);
        this.percentage = percentage;
        this.hexColor = hexColor;
    }

    public void validate() throws UIException {
        if (this.completionWeight <= 0 )
            throw new UIException("Goal weight can only be positive", "Wrong fields");

        if(this.completionWeight > 30000)
            throw new UIException("Goal weight is too heavy, split it up", "Overkill");

        if(this.completionWeight < 300)
            throw new UIException("Goal weight is too light, not worthy of being a goal", "Shame");

        LocalDate expectedFinishDate = Extensions.getDate(this.expectedFinish);
        LocalDate now = LocalDate.now();

        // days * hours * minutes * seconds * milliseconds
        var threeDayInterval = 3 * 24 * 60 * 60 * 1000;

        if(!expectedFinishDate.isAfter(now) && this.id == 0)
            throw new UIException("Expected finish should be further at least 3 days from now", "Wrong fields");

        if(Extensions.getUnixTimeStamp(expectedFinishDate) - Extensions.getUnixTimeStamp(now) < threeDayInterval)
            throw new UIException("Expected finish should be further at least 3 days from now", "Wrong fields");
    }

    /**
     * Construct a dictionary consisting of {fieldName : fieldValue}
     * @return
     */
    public Map<String, String> getFieldMap(){
        HashMap<String, String> fields = new HashMap<>();

        fields.put("Name", "\'" + this.name + "\'");
        fields.put("CompletionWeight", String.valueOf(this.completionWeight));
        fields.put("Description", "\'" + this.description + "\'");
        fields.put("CreatedAt", String.valueOf(this.createdAt));
        fields.put("ExpectedFinish", String.valueOf(this.expectedFinish));
        fields.put("Finished", String.valueOf(this.finished));
        fields.put("Type", String.valueOf(this.type.getId()));
        fields.put("Percentage", String.valueOf(this.percentage));
        fields.put("HexColor", String.valueOf(this.hexColor));
        fields.put("Completed", String.valueOf(Extensions.boolToInt(this.completed)));

        return fields;
    }
}
