package com.repositories.base;

import jdk.jshell.spi.ExecutionControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

abstract class AbstractBaseRepository {
    /**
     * If no connection is opened, opens a new one
     */
    abstract void openConnection();

    /**
     * Executes sql query and returns the result set
     * @param sql
     * @return
     * @throws SQLException
     */
    abstract ResultSet executeSql(String sql) throws SQLException;

    /**
     * Parse result set to an entity of type T
     * @param rs
     * @param <T>
     * @return
     */
    abstract <T> List<T> parseResult(ResultSet rs) throws ExecutionControl.NotImplementedException, SQLException;

    /**
     * Get all table T entities
     * @param <T>
     * @return
     * @throws ExecutionControl.NotImplementedException
     * @throws SQLException
     */
    abstract <T> List<T> getAll() throws ExecutionControl.NotImplementedException, SQLException;

    /**
     * Insert new entity of type T to database
     * @param entity
     * @param <T>
     * @return
     * @throws ExecutionControl.NotImplementedException
     * @throws SQLException
     */
    abstract <T> T insert(T entity) throws ExecutionControl.NotImplementedException, SQLException;

    /**
     * Update an entity of type T in database
     * @param entity
     * @param <T>
     * @return
     * @throws ExecutionControl.NotImplementedException
     * @throws SQLException
     */
    abstract <T> T update(T entity) throws ExecutionControl.NotImplementedException, SQLException;

    /**
     * Delete an entity of type T from the database
     * @param entity
     * @param <T>
     * @throws ExecutionControl.NotImplementedException
     * @throws SQLException
     */
    abstract <T> void delete(T entity) throws ExecutionControl.NotImplementedException, SQLException;
}
