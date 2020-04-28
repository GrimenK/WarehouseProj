package com.warehouse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomersRepresent extends JFrame {
    private JPanel basePanel;
    private JButton button1;
    private JTable table1;
    private JButton searchButton;
    private JTextField NameTextField;

    //DISPLAY SEARCHED CUSTOMERS

    public ArrayList<Customer> searchedCustomers(){
        String searchName = NameTextField.getText();

        ArrayList<Customer> customersList = new ArrayList<Customer>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sklad_Database","postgres", "1");
            String query = "SELECT * FROM public.\"Customer\" WHERE name_cust = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,searchName);
            ResultSet resultSet = preparedStatement.executeQuery();
            Customer customer;

            while (resultSet.next()){
                customer = new Customer(resultSet.getInt("id_customer"),resultSet.getString("cust_phone")
                        ,resultSet.getString("name_cust"));
                customersList.add(customer);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        return customersList;
    }

    public void showSearchedUsers(){
        ArrayList<Customer> list =  searchedCustomers();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[3];
        for(int i=0;i<list.size();i++){
            row[0]=list.get(i).getId_customer();
            row[1]=list.get(i).getCust_phone();
            row[2]=list.get(i).getName_cust();
            model.addRow(row);

        }
    }

    //DISPLAY CUSTOMERS

    public ArrayList<Customer> customersList(){
        ArrayList<Customer> customersList = new ArrayList<Customer>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sklad_Database","postgres", "1");
            String query = "SELECT * FROM public.\"Customer\"";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            Customer customer;

            while (resultSet.next()){
                customer = new Customer(resultSet.getInt("id_customer"),resultSet.getString("cust_phone")
                        ,resultSet.getString("name_cust"));
                customersList.add(customer);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

        return customersList;
    }


    public void showUsers(){
        ArrayList<Customer> list =  customersList();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.addColumn("Customer ID");
        model.addColumn("Customer Phone");
        model.addColumn("Customer Name");
        Object[] row = new Object[3];
        for(int i=0;i<list.size();i++){
            row[0]=list.get(i).getId_customer();
            row[1]=list.get(i).getCust_phone();
            row[2]=list.get(i).getName_cust();
            model.addRow(row);

        }
    }

    //UTILS

    public void closeS(){
        this.dispose();
    }

    public CustomersRepresent() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(basePanel);
        this.pack();
        this.setSize(750,400);
        this.setTitle("All Customers");
        showUsers();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCustomer addCustomer = new AddCustomer();
                addCustomer.setSize(450,250);
                addCustomer.setVisible(true);
                closeS();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchedUsers();
            }
        });
    }




    public static void main(String[] args) {
        JFrame frameCustomers = new CustomersRepresent();
        frameCustomers.setVisible(true);
    }
}
