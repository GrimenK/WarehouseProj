package com.warehouse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddCustomer extends JFrame {
    private JButton addRecordButton;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel panel1;


    public void addCustomerDB(){
        String cust_phone = textField2.getText();
        String name_cust = textField1.getText();
            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sklad_Database", "postgres", "1");
                String query = "INSERT INTO public.\"Customer\"(cust_phone,name_cust) VALUES (?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, cust_phone);
                preparedStatement.setString(2, name_cust);
                int resultSet = preparedStatement.executeUpdate();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
    }


    public void closeS(){
        this.dispose();
    }


    public AddCustomer(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        this.setSize(500,300);
        this.setTitle("Add New Customer");

        addRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( textField1.getText().isEmpty() && textField2.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Empty fields!");
                }
                else {
                    addCustomerDB();

                }
                CustomersRepresent customersRepresent = new CustomersRepresent();
                customersRepresent.setSize(750,400);
                customersRepresent.setVisible(true);
                closeS();
            }
        });
    }

    public static void main(String[] args) {
        JFrame addCustFrame = new AddCustomer();
        addCustFrame.setVisible(true);
    }
}
