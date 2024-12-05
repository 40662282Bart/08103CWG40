package com.mycompany.app;

// all the countries in a continent with largest to smallest population

import java.sql.*;
import java.util.Scanner;

public class Report_9 {
    public static void main(String[] args) {
        // Initialize connection
        Connection con = connectToDatabase(args);

        if (con != null) {
            try {
                // Get user input for the continent
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the continent name: ");
                String continent = scanner.nextLine();

                // Query to fetch all countries in a continent sorted by population in descending order
                String query = "SELECT " +
                        "country.Name, " +
                        "country.Population, " +
                        "ROUND(SUM(city.Population), 0) AS CityPopulation, " +
                        "ROUND((SUM(city.Population) / country.Population) * 100, 2) AS CityPercentage, " +
                        "ROUND((country.Population - SUM(city.Population)), 0) AS NonCityPopulation, " +
                        "ROUND(((country.Population - SUM(city.Population)) / country.Population) * 100, 2) AS NonCityPercentage " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Code = city.CountryCode " +
                        "WHERE country.Continent = ? " +
                        "GROUP BY country.Name, country.Population " +
                        "ORDER BY country.Population DESC";

                // Prepare statement
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, continent);

                // Execute query
                ResultSet rs = stmt.executeQuery();

                // Display the results
                System.out.printf("%-30s %-15s %-15s %-15s %-15s %-15s%n",
                        "Country", "Total Pop", "City Pop", "City %", "Non-City Pop", "Non-City %");
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
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Error generating report.");
                System.out.println(e.getMessage());
            } finally {
                try {
                    // Close connection
                    con.close();
                    System.out.println("Database connection closed.");
                } catch (SQLException e) {
                    System.out.println("Error closing database connection.");
                }
            }
        }
    }

    public static Connection connectToDatabase(String[] args) {
        Connection con = null;

        try {
            // Load the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("SQL driver loaded successfully.");

            // Define the database URL, username, and password
            String url;
            String username = "root";
            String password = "group40password";

            if (args.length != 0 && args[0].equals("DEBUG")) {
                // Debug environment (local database)
                url = "jdbc:mysql://localhost:33060/world?useSSL=false";
            } else {
                // Docker
                url = "jdbc:mysql://db:3306/world?useSSL=false";
            }

            // Attempt to connect to the database
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully connected to the database.");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver. Please ensure the driver is added to the project.");
            System.exit(-1);
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        return con;
    }
}
