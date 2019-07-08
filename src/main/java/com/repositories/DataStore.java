package com.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataStore {
    private Connection con;

    private void getConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void testConnection(){
        if (con == null)
            getConnection();

        try {
            Statement statement = con.createStatement();
            statement.execute("SELECT 1 FROM Goal");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
