package com.mycompany.app;

// All the cities in a region organised by largest population to smallest.

import java.sql.*;
import java.util.Scanner;

public class Report_13 {
    public static void main(String[] args) {
        // Initialize connection
        Connection con = connectToDatabase(args);

        if (con != null) {
            try {
                // Get user input for the region
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the region name: ");
                String region = scanner.nextLine();

                // Query to fetch all cities in a region sorted by population in descending order
                String query = "SELECT " +
                        "city.Name AS CityName, " +
                        "city.Population, " +
                        "country.Name AS CountryName " +
                        "FROM city " +
                        "JOIN country ON city.CountryCode = country.Code " +
                        "WHERE country.Region = ? " +
                        "ORDER BY city.Population DESC";

                // Prepare statement
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, region);

                // Execute query
                ResultSet rs = stmt.executeQuery();

                // Display the results
                System.out.printf("%-30s %-15s %-30s%n", "City", "Population", "Country");
                System.out.println("------------------------------------------------------------------");
                while (rs.next()) {
                    String cityName = rs.getString("CityName");
                    int population = rs.getInt("Population");
                    String countryName = rs.getString("CountryName");

                    System.out.printf("%-30s %-15d %-30s%n", cityName, population, countryName);
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
                // Docker environment
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
