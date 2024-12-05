package com.mycompany.app;

import java.sql.*;

public class CountryPopulation {
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
            try {
                // Wait a bit for the database to start
                Thread.sleep(3000);

                // Connect to the database (debug vs Docker environment)
                if (args.length != 0 && args[0].equals("DEBUG")) {
                    con = DriverManager.getConnection("jdbc:mysql://localhost:33060/world?useSSL=false", "root", "group40password");
                } else {
                    con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "group40password");
                }

                System.out.println("Successfully connected");
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
                // Query to fetch population report data for continents, regions, and countries
                String query = "SELECT country.Name AS CountryName, country.Population AS TotalPopulation, " +
                        "ROUND(SUM(city.Population), 0) AS CityPopulation, " +
                        "ROUND((SUM(city.Population) / country.Population) * 100, 2) AS CityPercentage, " +
                        "ROUND((country.Population - SUM(city.Population)), 0) AS NonCityPopulation, " +
                        "ROUND(((country.Population - SUM(city.Population)) / country.Population) * 100, 2) AS NonCityPercentage " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Code = city.CountryCode " +
                        "GROUP BY country.Name, country.Population " +
                        "ORDER BY country.Population DESC;";

                // Execute query
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                // Display the results
                System.out.printf("%-30s %-15s %-20s %-15s %-20s %-15s%n",
                        "Country Name", "Total Population", "City Population", "City %", "Non-City Population", "Non-City %");
                System.out.println("---------------------------------------------------------------------------------------------------");

                while (rs.next()) {
                    String countryName = rs.getString("CountryName");
                    int totalPopulation = rs.getInt("TotalPopulation");
                    int cityPopulation = rs.getInt("CityPopulation");
                    double cityPercentage = rs.getDouble("CityPercentage");
                    int nonCityPopulation = rs.getInt("NonCityPopulation");
                    double nonCityPercentage = rs.getDouble("NonCityPercentage");

                    System.out.printf("%-30s %-15d %-20d %-15.2f %-20d %-15.2f%n",
                            countryName, totalPopulation, cityPopulation, cityPercentage, nonCityPopulation, nonCityPercentage);
                }

                // Close resources
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception occurred while fetching population report.");
                System.out.println(e.getMessage());
            } finally {
                try {
                    // Close connection
                    con.close();
                } catch (Exception e) {
                    System.out.println("Error closing connection to database");
                }
            }
        }
    }
}
