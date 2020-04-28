package com.warehouse;

import javafx.geometry.Pos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSql {
    public static void main(String[] args) {
        PostgreSql baseConnection = new PostgreSql();
        baseConnection.getCustomers();
    }


    public static void getCustomers(){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sklad_Database", "postgres", "1")) {
            System.out.println("Java JDBC PostgreSQL Example");
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
            System.out.println("Reading records...");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.\"Customer\"");
            while (resultSet.next()) {
                System.out.printf("%-30.30s  %-30.30s  %-30.30s%n", resultSet.getString( "id_customer"), resultSet.getString("cust_phone"),
                        resultSet.getString("name_cust"));
            }
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }


    public static Connection connectDb(){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sklad_Database", "postgres", "1")) {
            System.out.println("Connected to PostgreSQL database!");
            return connection;
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
            return null;
        }
    }


}
