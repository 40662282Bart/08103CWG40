package com.mycompany.app;

import java.sql.*;
import java.util.Scanner;

public class Report_15 {
    public static void main(String[] args) {
        // Initialize connection
        Connection con = connectToDatabase(args);

        if (con != null) {
            try {
                // Get user input for the district
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the district name: ");
                String district = scanner.nextLine();

                // Query to fetch all cities in a district sorted by population in descending order
                String query = "SELECT " +
                        "city.Name AS CityName, " +
                        "city.Population, " +
                        "city.District " +
                        "FROM city " +
                        "WHERE city.District = ? " +
                        "ORDER BY city.Population DESC";

                // Prepare statement
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, district);

                // Execute query
                ResultSet rs = stmt.executeQuery();

                // Display the results
                System.out.printf("%-30s %-15s %-30s%n", "City", "Population", "District");
                System.out.println("------------------------------------------------------------------");
                while (rs.next()) {
                    String cityName = rs.getString("CityName");
                    int population = rs.getInt("Population");
                    String districtName = rs.getString("District");

                    System.out.printf("%-30s %-15d %-30s%n", cityName, population, districtName);
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
