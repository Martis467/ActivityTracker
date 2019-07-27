package com.repositories;

import com.models.GoalActivityRelation;
import com.repositories.base.BaseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GoalActivityRelationRepository extends BaseRepository {

    @Override
    public List<GoalActivityRelation> getAll() throws SQLException {
        String sql = "SELECT * FROM " + GoalActivityRelation.class.getSimpleName();
        ResultSet resultSet = this.executeSql(sql);
        return parseResult(resultSet);
    }

    @Override
    public GoalActivityRelation getById(int id) throws SQLException {
        String sql = "SELECT * FROM " + GoalActivityRelation.class.getSimpleName() + " WHERE Id = " + id;
        ResultSet resultSet = this.executeSql(sql);
        return parseResult(resultSet).stream().findFirst().orElse(null);
    }

    @Override
    public <T> int insert(T entity) throws SQLException {
        GoalActivityRelation model = (GoalActivityRelation) entity;
        String sql = this.constructInsertSql(model.getFieldMap(), GoalActivityRelation.class.getSimpleName());
        return this.executeUpdateSql(sql);
    }

    @Override
    public <T> void update(T entity, int id) throws SQLException {
        GoalActivityRelation model = (GoalActivityRelation) entity;
        String sql = this.constructUpdateSql(model.getFieldMap(), id, GoalActivityRelation.class.getSimpleName());
        this.executeUpdateSql(sql);
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM " + GoalActivityRelation.class.getSimpleName() + " WHERE Id = " + id;
        this.executeUpdateSql(sql);
    }

    @Override
    protected List<GoalActivityRelation> parseResult(ResultSet rs) throws SQLException {
        List<GoalActivityRelation> activities = new LinkedList<>();

        while (rs.next()){
            activities.add(new GoalActivityRelation(
                    rs.getInt("Id"),
                    rs.getInt("ActivityId"),
                    rs.getInt("GoalId"),
                    rs.getInt("Weight")
            ));
        }
        return activities;
    }
}
