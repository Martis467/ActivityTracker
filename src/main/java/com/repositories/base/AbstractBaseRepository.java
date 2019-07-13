package com.repositories.base;

import jdk.jshell.spi.ExecutionControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
     * Execute sql query
     * @param sql
     * @throws SQLException
     */
    abstract void executeUpdateSql(String sql) throws SQLException;

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
    abstract <T> void insert(T entity) throws ExecutionControl.NotImplementedException, SQLException;

    /**
     * Constructs an insert statement from a given field dictionary
     * @param fields
     * @param table
     * @return
     */
    abstract String constructInsertSql(HashMap<String, String> fields, String table);

    /**
     * Update an entity of type T in database
     * @param entity
     * @param <T>
     * @return
     * @throws ExecutionControl.NotImplementedException
     * @throws SQLException
     */
    abstract <T> void update(T entity) throws ExecutionControl.NotImplementedException, SQLException;

    /**
     * Constructs an update statement from a given dictionary
     * @param fields
     * @param table
     * @return
     */
    abstract String constructUpdateSql(HashMap<String, String> fields, int id, String table);

    /**
     * Delete an entity of type T from the database
     * @param entity
     * @param <T>
     * @throws ExecutionControl.NotImplementedException
     * @throws SQLException
     */
    abstract <T> void delete(T entity) throws ExecutionControl.NotImplementedException, SQLException;
}
