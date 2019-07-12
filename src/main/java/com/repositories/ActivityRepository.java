package com.repositories;

import com.models.Activity;
import com.repositories.base.BaseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ActivityRepository extends BaseRepository {

    @Override
    public List<Activity> getAll() throws SQLException {
        String sql = "SELECT * FROM " + Activity.class.getSimpleName();
        ResultSet resultSet = executeSql(sql);
        return parseResult(resultSet);
    }

    @Override
    public <T> T insert(T entity) throws SQLException {
        Activity model = (Activity) entity;

        return null;
    }

    @Override
    public <T> T update(T entity) throws SQLException {
        Activity model = (Activity) entity;

        return null;
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
                    rs.getInt("ExpectedDuration"),
                    rs.getInt("Weight"),
                    rs.getString("Description"),
                    rs.getInt("Type"),
                    rs.getLong("CreatedAt"),
                    rs.getLong("HexColor")
            ));
        }
        return activities;
    }
}
