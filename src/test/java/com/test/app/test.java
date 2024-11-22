package com.test.app;

import java.sql.*;
import java.util.ArrayList;

public class Test {
    private Connection con;

    public void connect(String location, int delay) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver.");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected.");
                break;
            } catch (SQLException ex) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(ex.getMessage());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    System.out.println("Thread interrupted.");
                }
            }
        }
    }

    public void disconnect() {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error closing database connection.");
        }
    }

    public ArrayList<String> getCountriesByContinent(String continent) {
        ArrayList<String> countries = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT name FROM country WHERE continent = '" + continent + "'";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                countries.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching countries: " + e.getMessage());
        }
        return countries;
    }

    public static void main(String[] args) {
        App app = new App();
        String location = args.length > 0 ? args[0] : "localhost:33060";
        int delay = args.length > 1 ? Integer.parseInt(args[1]) : 10000;

        app.connect(location, delay);

        ArrayList<String> countries = app.getCountriesByContinent("Asia");
        for (String country : countries) {
            System.out.println(country);
        }

        app.disconnect();
    }
}