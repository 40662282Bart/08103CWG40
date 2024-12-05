package com.mycompany.app;

import java.sql.*;

public class World_database {
    public static void main(String[] args) {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        Connection con = null;

        try {
            // Connect to the database
            con = DriverManager.getConnection("jdbc:mysql://localhost:33060/world?useSSL=false", "root", "group40password");

            // Query to fetch all columns and their details
            String query = "SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_KEY, COLUMN_TYPE " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA = 'world' " +
                    "ORDER BY TABLE_NAME, ORDINAL_POSITION;";

            // Execute the query
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Display the results
            System.out.printf("%-20s %-20s %-15s %-10s %-10s %-20s%n", "Table Name", "Column Name", "Data Type", "Nullable", "Key", "Column Type");
            System.out.println("-----------------------------------------------------------------------------------------------");

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                String isNullable = rs.getString("IS_NULLABLE");
                String columnKey = rs.getString("COLUMN_KEY");
                String columnType = rs.getString("COLUMN_TYPE");

                System.out.printf("%-20s %-20s %-15s %-10s %-10s %-20s%n", tableName, columnName, dataType, isNullable, columnKey, columnType);
            }

            // Close resources
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}

