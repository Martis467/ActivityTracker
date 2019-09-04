package com.repositories;

import com.models.Activity;
import com.models.ActivityLog;
import com.repositories.base.BaseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ActivityLogRepository extends BaseRepository {

    @Override
    public List<ActivityLog> getAll() throws SQLException {
        String sql = "SELECT * FROM " + ActivityLog.class.getSimpleName();
        ResultSet resultSet = this.executeSql(sql);
        return parseResult(resultSet);
    }

    public List<ActivityLog> getAllMapped() throws SQLException {
        String sql = SqlViews.ActivityLogView;
        ResultSet resultSet = this.executeSql(sql);
        return parseMappedResult(resultSet);
    }

    @Override
    public ActivityLog getById(int id) throws SQLException {
        String sql = "SELECT * FROM " + ActivityLog.class.getSimpleName() + " WHERE Id = " + id;
        ResultSet resultSet = this.executeSql(sql);
        return parseResult(resultSet).stream().findFirst().orElse(null);
    }

    @Override
    public <T> int insert(T entity) throws SQLException {
        ActivityLog model = (ActivityLog) entity;
        String sql = this.constructInsertSql(model.getFieldMap(), ActivityLog.class.getSimpleName());
        return this.executeUpdateSql(sql);
    }

    @Override
    public <T> void update(T entity, int id) throws SQLException {
        ActivityLog model = (ActivityLog) entity;
        String sql = this.constructUpdateSql(model.getFieldMap(), id, ActivityLog.class.getSimpleName());
        this.executeUpdateSql(sql);
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM " + ActivityLog.class.getSimpleName() + " WHERE Id = " + id;
        this.executeUpdateSql(sql);
    }

    @Override
    protected List<ActivityLog> parseResult(ResultSet rs) throws SQLException {
        List<ActivityLog> activities = new LinkedList<>();

        while (rs.next()){
            activities.add(new ActivityLog(
                    rs.getInt("Id"),
                    rs.getInt("Duration"),
                    rs.getInt("Rating"),
                    rs.getLong("CreatedAt"),
                    rs.getLong("CompletedAt"),
                    rs.getInt("ActivityId")
            ));
        }
        return activities;
    }

    private List<ActivityLog> parseMappedResult(ResultSet rs) throws SQLException {
        List<ActivityLog> activities = new LinkedList<>();

        while (rs.next()){
            activities.add(new ActivityLog(
                    rs.getInt("Id"),
                    rs.getInt("Duration"),
                    rs.getInt("Rating"),
                    rs.getLong("LogCreatedAt"),
                    rs.getLong("CompletedAt"),
                    rs.getInt("ActivityId"),

                    new Activity(rs.getInt("ActivityId"),
                            rs.getString("Name"),
                            rs.getInt("ExpectedDurations"),
                            rs.getString("Description"),
                            rs.getInt("Type"),
                            rs.getLong("ActivityCreatedAt"),
                            rs.getInt("HexColor"))
            ));
        }
        return activities;
    }
}
