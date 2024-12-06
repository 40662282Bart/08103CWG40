package com.mycompany.app;
/**
 * The Top N Populated countries in the world where N is provided by the user
 */

import java.sql.*;
import java.util.Scanner;

public class Report_1 {
    public static void main(String[] args) {
        // Initialize connection
        Connection con = connectToDatabase(args);

        if (con != null) {
            try {
                // Get user input for the number of top countries
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the number of top populated countries to display (N): ");
                int n = scanner.nextInt();

                // Query to fetch the top N populated countries with city and non-city population data
                String query = "SELECT " +
                        "country.Name, " +
                        "country.Population, " +
                        "ROUND(SUM(city.Population), 0) AS CityPopulation, " +
                        "ROUND((SUM(city.Population) / country.Population) * 100, 2) AS CityPercentage, " +
                        "ROUND((country.Population - SUM(city.Population)), 0) AS NonCityPopulation, " +
                        "ROUND(((country.Population - SUM(city.Population)) / country.Population) * 100, 2) AS NonCityPercentage " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Code = city.CountryCode " +
                        "GROUP BY country.Name, country.Population " +
                        "ORDER BY country.Population DESC LIMIT ?";

                // Prepare statement
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, n);

                // Execute query
                ResultSet rs = stmt.executeQuery();

                // Display the results
                System.out.printf("%-30s %-15s %-15s %-15s %-15s %-15s%n", //this is likw padding in html for spacing out the output make it look all neat
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
                System.out.println("error getting report");
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
                // Production/Docker environment
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

