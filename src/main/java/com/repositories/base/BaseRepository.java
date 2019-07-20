package com.repositories.base;

import jdk.jshell.spi.ExecutionControl;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class BaseRepository extends AbstractBaseRepository {
    private Connection con;

    @Override
    protected void openConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ResultSet executeSql(String sql) throws SQLException {
        if (con == null)
            openConnection();

        return con.createStatement().executeQuery(sql);
    }

    @Override
    protected void executeUpdateSql(String sql) throws SQLException {
        if (con == null)
            openConnection();

        con.createStatement().executeUpdate(sql);
    }

    @Override
    protected String constructInsertSql(Map<String, String> fields, String table) {
        String fieldSql = "INSERT INTO " + table + " (";
        String valueSql = "VALUES (";

        for (Map.Entry<String, String> entry :
                fields.entrySet()) {
            fieldSql += entry.getKey() + ",";
            valueSql += entry.getValue() + ",";
        }

        // Doing this to remove the last unneeded comma at the end of the string
        fieldSql = fieldSql.substring(0, fieldSql.length() - 1) + ")";
        valueSql = valueSql.substring(0, valueSql.length() - 1) + ")";

        return fieldSql + " " + valueSql;
    }

    @Override
    protected String constructUpdateSql(Map<String, String> fields, int id, String table) {
        String sql = "UPDATE " + table + " SET ";

        for (Map.Entry<String, String> entry :
                fields.entrySet()) {
            sql += entry.getKey() + " = " + entry.getValue() + ",";
        }

        sql = sql.substring(0, sql.length() - 1) + " WHERE Id = " + id;
        return sql;
    }

    @Override
    protected <T> List<T> parseResult(ResultSet rs) throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    @Override
    public <T> List<T> getAll() throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    @Override
    public <T> T getById(int id) throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    @Override
    public <T> void insert(T entity) throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    @Override
    public <T> void update(T entity, int id) throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    @Override
    public void delete(int id) throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    /**
     * Checking database connection
     */
    public void testConnection() {
        if (con == null)
            openConnection();

        try {
            Statement statement = con.createStatement();
            statement.execute("SELECT 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
