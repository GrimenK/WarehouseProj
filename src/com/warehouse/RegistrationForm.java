package com.warehouse;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrationForm extends JFrame {

    private JButton signUpButton;
    private JCheckBox administratorCheckBox;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPanel panel1;

    public boolean checkLogin(){
        String currentUser = textField1.getText();
        boolean userExist = false;
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sklad_Database","postgres", "1");
            String query = "SELECT * FROM public.\"Users\" WHERE user_login=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,currentUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userExist = true;
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        return userExist;
    }

   public void registrateUser(){
       String currentUser = textField1.getText();
       char[] currentPassword = passwordField1.getPassword();
       boolean currentStatus = administratorCheckBox.isSelected();

       if(currentUser != "" && currentPassword.length > 0) {
           if (!checkLogin()) {
               try {
                   Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sklad_Database", "postgres", "1");
                   String query = "INSERT INTO public.\"Users\"(user_login,user_password,user_status) VALUES (?,?,?)";
                   PreparedStatement preparedStatement = connection.prepareStatement(query);

                   preparedStatement.setString(1,currentUser);
                   preparedStatement.setString(2, String.valueOf(currentPassword));
                   preparedStatement.setBoolean(3,currentStatus);

                   int resultSet = preparedStatement.executeUpdate();
               } catch (Exception e) {
                   JOptionPane.showMessageDialog(null, e);
               }
           }
           else {
               JOptionPane.showMessageDialog(null, "User with this name already exests!");
           }
       }

       else {
           JOptionPane.showMessageDialog(null, "User login or password empty!");
       }

    }



    public void closeS(){
        this.dispose();
    }


    public RegistrationForm(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        this.setSize(500,300);
        this.setTitle("Registration");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            registrateUser();
            CustomersRepresent cust = new CustomersRepresent();
            cust.setSize(750,400);
            cust.setVisible(true);
            closeS();
            }
        });
    }

    public static void main(String[] args) {
        JFrame registerFrame = new RegistrationForm();
        registerFrame.setVisible(true);
    }
}
