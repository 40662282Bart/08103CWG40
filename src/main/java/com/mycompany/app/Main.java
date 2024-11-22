package com.mycompany.app;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database
        Connection con = null;
        int retries = 100;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            System.out.println("Hello");
            try {
                // Wait a bit for db to start
                Thread.sleep(3000);
                // Connect to the database (use 'world_db' in the connection string)
                if (args.length!=0 && args[0].equals("DEBUG")) {
                    con = DriverManager.getConnection("jdbc:mysql://localhost:33060/world?useSSL=false", "root", "group40password");
                }
                else {
                    con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "group40password");
                }
                System.out.println("Successfully connected");
                // Exit for loop
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }
}

