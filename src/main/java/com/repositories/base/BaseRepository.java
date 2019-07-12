package com.repositories.base;

import jdk.jshell.spi.ExecutionControl;

import java.sql.*;
import java.util.List;

public class BaseRepository extends AbstractBaseRepository {
    private Connection con;

    @Override
    protected void openConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ResultSet executeSql(String sql) throws SQLException {
        if(con == null)
            openConnection();

        return con.createStatement().executeQuery(sql);
    }

    @Override
    protected <T> List<T> parseResult(ResultSet rs) throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    @Override
    public  <T> List<T> getAll() throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    @Override
    public <T> T insert(T entity) throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    @Override
    public <T> T update(T entity) throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    @Override
    public <T> void delete(T entity) throws ExecutionControl.NotImplementedException, SQLException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

    /**
     * Checking database connection
     */
    public void testConnection(){
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
