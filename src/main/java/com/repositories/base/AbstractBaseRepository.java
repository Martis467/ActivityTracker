package com.repositories.base;

import jdk.jshell.spi.ExecutionControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
     * Execute sql query which edits the table T entities
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
     * Get a single table T entity
     * @param <T>
     * @return
     * @throws ExecutionControl.NotImplementedException
     * @throws SQLException
     */
    abstract <T> T getById(int id) throws ExecutionControl.NotImplementedException, SQLException;

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
    abstract String constructInsertSql(Map<String, String> fields, String table);

    /**
     * Update an entity of type T in database
     * @param entity
     * @param <T>
     * @return
     * @throws ExecutionControl.NotImplementedException
     * @throws SQLException
     */
    abstract <T> void update(T entity, int id) throws ExecutionControl.NotImplementedException, SQLException;

    /**
     * Constructs an update statement from a given dictionary
     * @param fields
     * @param table
     * @return
     */
    abstract String constructUpdateSql(Map<String, String> fields, int id, String table);

    /**
     * Delete an entity of type T from the database
     * @param id
     * @throws ExecutionControl.NotImplementedException
     * @throws SQLException
     */
    abstract void delete(int id) throws ExecutionControl.NotImplementedException, SQLException;
}
