package com.bismih.server_chat_app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sqlite.*;



public class Db_helper {
    private final String DB_URL = "jdbc:sqlite:src/main/java/com/bismih/server_chat_app/data/server.db";
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

    public void add_row(String table, String[] columns, String[][] values) {

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
        try {
            connection.createStatement().executeUpdate(query);
            System.out.println("Row added to " + table);
        } catch (SQLiteException e) {
            //e.printStackTrace();
            if (e.getErrorCode() == 19) {
                System.out.println("Row already exists in " + table);
            }
            System.out.println(e.getMessage()+" "+ e.getErrorCode());
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void add_row(String table, String[] columns, String[] values) {

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
        try {
            connection.createStatement().executeUpdate(query);
            System.out.println("Row added to " + table);
        } catch (SQLiteException e) {
            //e.printStackTrace();
            if (e.getErrorCode() == 19) {
                System.out.println("Row already exists in " + table);
            }
            System.out.println(e.getMessage()+" "+ e.getErrorCode());
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
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
