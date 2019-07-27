package com.repositories;

import com.models.Goal;
import com.repositories.base.BaseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GoalRepository extends BaseRepository {

    @Override
    public List<Goal> getAll() throws SQLException {
        String sql = "SELECT * FROM " + Goal.class.getSimpleName();
        ResultSet resultSet = this.executeSql(sql);
        return parseResult(resultSet);
    }

    @Override
    public Goal getById(int id) throws SQLException {
        String sql = "SELECT * FROM " + Goal.class.getSimpleName() + " WHERE Id = " + id;
        ResultSet resultSet = this.executeSql(sql);
        return parseResult(resultSet).stream().findFirst().orElse(null);
    }

    @Override
    public <T> int insert(T entity) throws SQLException {
        Goal model = (Goal) entity;
        String sql = this.constructInsertSql(model.getFieldMap(), Goal.class.getSimpleName());
        return this.executeUpdateSql(sql);
    }

    @Override
    public <T> void update(T entity, int id) throws SQLException {
        Goal model = (Goal) entity;
        String sql = this.constructUpdateSql(model.getFieldMap(), id, Goal.class.getSimpleName());
        this.executeUpdateSql(sql);
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM " + Goal.class.getSimpleName() + " WHERE Id = " + id;
        this.executeUpdateSql(sql);
    }

    @Override
    protected List<Goal> parseResult(ResultSet rs) throws SQLException {
        List<Goal> activities = new LinkedList<>();

        while (rs.next()){
            activities.add(new Goal(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getInt("CompletionWeight"),
                    rs.getString("Description"),
                    rs.getLong("CreatedAt"),
                    rs.getLong("ExpectedFinish"),
                    rs.getLong("Finished"),
                    rs.getInt("Type"),
                    rs.getFloat("Percentage"),
                    rs.getLong("HexColor")
            ));
        }
        return activities;
    }
}
