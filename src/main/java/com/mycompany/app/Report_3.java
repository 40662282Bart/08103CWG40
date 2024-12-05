package com.mycompany.app;
/**
 * The top N populated countries in a continent where N is provided by the user and you pick the continent
 */

import java.sql.*;
import java.util.Scanner;

public class Report_3 {
    public static void main(String[] args) {
        // Load Database driver
        try {
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
                // Get continent and number of countries from user input
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the region (e.g., South America, Polynesia, Western Europe, Middle East): ");
                String continent = scanner.nextLine();
                System.out.print("Enter the number of top countries you want to retrieve: ");
                int topN = scanner.nextInt();

                // SQL query to fetch top N countries based on population within the given continent
                String query = "SELECT " +
                        "country.Name, " +
                        "country.Population, " +
                        "ROUND(SUM(city.Population), 0) AS CityPopulation, " +
                        "ROUND((SUM(city.Population) / country.Population) * 100, 2) AS CityPercentage, " +
                        "ROUND((country.Population - SUM(city.Population)), 0) AS NonCityPopulation, " +
                        "ROUND(((country.Population - SUM(city.Population)) / country.Population) * 100, 2) AS NonCityPercentage " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Code = city.CountryCode " +
                        "WHERE country.Region = ? " +  // Filter by continent
                        "GROUP BY country.Name, country.Population " +
                        "ORDER BY country.Population DESC " +
                        "LIMIT ?";  // Limit to top N countries

                // Prepare and execute the query with user inputs
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, continent);  // Set continent parameter
                pstmt.setInt(2, topN);  // Set top N parameter

                ResultSet rs = pstmt.executeQuery();

                // Display the results
                System.out.printf("%-30s %-15s %-15s %-15s %-15s %-15s%n",
                        "Name", "Total Pop", "City Pop", "City %", "Non-City Pop", "Non-City %");
                System.out.println("---------------------------------------------------------------------------------------------------");

                while (rs.next()) {
                    String name = rs.getString("Name");
                    int totalPopulation = rs.getInt("Population");
                    int cityPopulation = rs.getInt("CityPopulation");
                    double cityPercentage = rs.getDouble("CityPercentage");
                    int nonCityPopulation = rs.getInt("NonCityPopulation");
                    double nonCityPercentage = rs.getDouble("NonCityPercentage");

                    System.out.printf("%-30s %-15d %-15d %-15.2f %-15d %-15.2f%n",
                            name, totalPopulation, cityPopulation, cityPercentage, nonCityPopulation, nonCityPercentage);
                }

                // Close resources
                rs.close();
                pstmt.close();
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

