package com.mycompany.app;
/**
 * top n cities where region is chosen
 */

import java.sql.*;
import java.util.Scanner;

public class Report_6 {
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
                // Get user input for continent and top N cities
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the Region: ");
                String region = scanner.nextLine();
                System.out.print("Enter the number of top cities you want to retrieve: ");
                int topN = scanner.nextInt();

                // SQL query to fetch top N cities in the specified continent based on population
                String query = "SELECT " +
                        "city.Name AS CityName, " +
                        "country.Name AS CountryName, " +
                        "city.District, " +
                        "city.Population " +
                        "FROM city " +
                        "JOIN country ON city.CountryCode = country.Code " +
                        "WHERE country.Region = ? " +
                        "ORDER BY city.Population DESC " +
                        "LIMIT ?";  // Limit to top N cities

                // Prepare and execute the query with user input
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, region); // Set continent parameter
                pstmt.setInt(2, topN);         // Set top N parameter

                ResultSet rs = pstmt.executeQuery();

                // Display the results
                System.out.printf("%-30s %-30s %-20s %-15s%n",
                        "City Name", "Country Name", "District", "Population");
                System.out.println("---------------------------------------------------------------------------------------------------");

                while (rs.next()) {
                    String cityName = rs.getString("CityName");
                    String countryName = rs.getString("CountryName");
                    String district = rs.getString("District");
                    int population = rs.getInt("Population");

                    System.out.printf("%-30s %-30s %-20s %-15d%n",
                            cityName, countryName, district, population);
                }

                // Close resources
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception occurred while fetching city population report.");
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
