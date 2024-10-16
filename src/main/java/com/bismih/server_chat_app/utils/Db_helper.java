package com.bismih.server_chat_app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Db_helper {
    private final String DB_URL = "jdbc:sqlite:server.db";
    private Connection connection;

    public Db_helper() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connection established");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void start_connection() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connection established");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void add_row(String table, String[] columns, String[][] values) throws SQLException {

        String query = "INSERT INTO " + table + " (";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i];
            if (i < columns.length - 1) {
                query += ", ";
            }
        }
        query += ") VALUES (";
        for (int i = 0; i < values.length; i++) {
            if (values[i][1].equals("1")) {
                query += values[i][0];
            } else {
                query += "'" + values[i][0] + "'";
            }
            if (i < values.length - 1) {
                query += ", ";
            }
        }
        query += ")";

        connection.createStatement().executeUpdate(query);
        System.out.println("Row added to " + table);

    }

    public void add_row(String table, String[] columns, String[] values) throws SQLException {

        String query = "INSERT INTO " + table + " (";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i];
            if (i < columns.length - 1) {
                query += ", ";
            }
        }
        query += ") VALUES (";
        for (int i = 0; i < values.length; i++) {
            query += "'" + values[i] + "'";
            if (i < values.length - 1) {
                query += ", ";
            }
        }
        query += ")";

        connection.createStatement().executeUpdate(query);
        System.out.println("Row added to " + table);

    }

    public void delete_row(String table, String[] columns, String[] values) {
        String query = "DELETE FROM " + table + " WHERE ";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i] + " = '" + values[i] + "'";
            if (i < columns.length - 1) {
                query += " AND ";
            }
        }
        try {
            connection.createStatement().executeUpdate(query);
            System.out.println("Row deleted from " + table);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public int get_last_id(String table) {
        String query = "SELECT MAX(id) FROM " + table;
        try {
            return connection.createStatement().executeQuery(query).getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public ResultSet get_query(String query) {
        try {
            return connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
