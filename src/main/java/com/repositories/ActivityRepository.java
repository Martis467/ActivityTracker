package com.repositories;

import com.enumerations.ActivityDuration;
import com.models.Activity;
import com.repositories.base.BaseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ActivityRepository extends BaseRepository {

    @Override
    public List<Activity> getAll() throws SQLException {
        String sql = "SELECT * FROM " + Activity.class.getSimpleName();
        ResultSet resultSet = this.executeSql(sql);
        return parseResult(resultSet);
    }

    @Override
    public <T> void insert(T entity) throws SQLException {
        Activity model = (Activity) entity;
        HashMap<String, String> fields = new HashMap<>();

        fields.put("Name", "\'" + model.name + "\'");
        fields.put("ExpectedDurations", "\'" + ActivityDuration.getStatusValue(model.expectedDurations) + "\'");
        fields.put("Weight", String.valueOf(model.weight));
        fields.put("Description", "\'" + model.description + "\'");
        fields.put("Type", String.valueOf(model.type.getId()));
        fields.put("CreatedAt", String.valueOf(model.createdAt));
        fields.put("HexColor", String.valueOf(model.hexColor));

        String sql = this.constructInsertSql(fields, Activity.class.getSimpleName());
        this.executeUpdateSql(sql);
    }

    @Override
    public <T> void update(T entity) throws SQLException {
        Activity model = (Activity) entity;

    }

    @Override
    public <T> void delete(T entity) throws SQLException {
        Activity model = (Activity) entity;
    }

    @Override
    protected List<Activity> parseResult(ResultSet rs) throws SQLException {
        List<Activity> activities = new LinkedList<>();

        while (rs.next()){
            activities.add(new Activity(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getInt("ExpectedDurations"),
                    rs.getInt("Weight"),
                    rs.getString("Description"),
                    rs.getInt("Type"),
                    rs.getLong("CreatedAt"),
                    rs.getInt("HexColor")
            ));
        }
        return activities;
    }
}
