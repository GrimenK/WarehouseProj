package com.warehouse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainForm extends JFrame {

    private JPanel panel1;
    private JButton button2;
    private JButton button1;
    private JLabel titleLable;
    private JTextField textField1;
    private JPasswordField passwordField1;

    public void closeS(){

        this.dispose();
    }

    public MainForm(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        this.setSize(500,300);
        this.setTitle("Log In or Register");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm regForm = new RegistrationForm();
                regForm.setSize(600,500);
                regForm.setVisible(true);
                closeS();
            }
        });


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            loginUser();
            }
        });
    }

    public void loginUser(){
        char[] currentPassword = passwordField1.getPassword();
        String currentUser = textField1.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sklad_Database","postgres", "1");
            String query = "SELECT * FROM public.\"Users\" WHERE user_login=? AND user_password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,currentUser);
            preparedStatement.setString(2, String.valueOf(currentPassword));
            ResultSet resultSet = preparedStatement.executeQuery();

            int flag = 0;
            while (resultSet.next()){
                flag = flag +1;
            }

            if(flag == 1){
                CustomersRepresent custForm = new CustomersRepresent();
                custForm.setSize(750,400);
                custForm.setVisible(true);
                closeS();
            }
            else{
                JOptionPane.showMessageDialog(null,"Try again!");
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }

    }

    public static void main(String[] args) {
        JFrame frameMain = new MainForm();
        frameMain.setVisible(true);
    }
}
