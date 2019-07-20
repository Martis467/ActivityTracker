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
        ResultSet resultSet = this.executeSql(sql);
        return parseResult(resultSet);
    }

    @Override
    public Activity getById(int id) throws SQLException {
        String sql = "SELECT * FROM " + Activity.class.getSimpleName() + " WHERE Id = " + id;
        ResultSet resultSet = this.executeSql(sql);
        return parseResult(resultSet).stream().findFirst().orElse(null);
    }

    @Override
    public <T> void insert(T entity) throws SQLException {
        Activity model = (Activity) entity;
        String sql = this.constructInsertSql(model.getFieldMap(), Activity.class.getSimpleName());
        this.executeUpdateSql(sql);
    }

    @Override
    public <T> void update(T entity, int id) throws SQLException {
        Activity model = (Activity) entity;
        String sql = this.constructUpdateSql(model.getFieldMap(), id, Activity.class.getSimpleName());
        this.executeUpdateSql(sql);
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM TABLE " + Activity.class.getSimpleName() + " WHERE Id = " + id;
        this.executeUpdateSql(sql);
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
