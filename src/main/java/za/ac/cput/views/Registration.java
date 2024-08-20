package za.ac.cput.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration {

    private JPanel registration;

    public Registration() {


        registration = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel personalPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        personalPanel.setBorder(BorderFactory.createTitledBorder("Personal"));

        personalPanel.add(new JLabel("Employee Identity no*:"));
        JTextField empIdField = new JTextField();
        personalPanel.add(empIdField);

        personalPanel.add(new JLabel("First Name*:"));
        JTextField firstNameField = new JTextField();
        personalPanel.add(firstNameField);

        personalPanel.add(new JLabel("Middlename:"));
        JTextField middleNameField = new JTextField();
        personalPanel.add(middleNameField);

        personalPanel.add(new JLabel("Last Name*:"));
        JTextField lastNameField = new JTextField();
        personalPanel.add(lastNameField);

        personalPanel.add(new JLabel("Role*:"));
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton sellerButton = new JRadioButton("Seller");
        JRadioButton buyerButton = new JRadioButton("Buyer");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(sellerButton);
        roleGroup.add(buyerButton);
        rolePanel.add(sellerButton);
        rolePanel.add(buyerButton);
        personalPanel.add(rolePanel);

        mainPanel.add(personalPanel);

        JPanel contactPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        contactPanel.setBorder(BorderFactory.createTitledBorder("Contact*"));

        contactPanel.add(new JLabel("Email Address:"));
        JTextField emailField = new JTextField();
        contactPanel.add(emailField);

        contactPanel.add(new JLabel("Phone Number:"));
        JTextField phoneField = new JTextField();
        contactPanel.add(phoneField);

        mainPanel.add(contactPanel);

        JPanel addressPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        addressPanel.setBorder(BorderFactory.createTitledBorder("Address*"));

        addressPanel.add(new JLabel("City*:"));
        JTextField cityField = new JTextField();
        addressPanel.add(cityField);

        addressPanel.add(new JLabel("Postal Code*:"));
        JTextField postalCodeField = new JTextField();
        addressPanel.add(postalCodeField);

        addressPanel.add(new JLabel("Suburb*:"));
        JTextField suburbField = new JTextField();
        addressPanel.add(suburbField);

        addressPanel.add(new JLabel("Street Number*:"));
        JTextField streetNumberField = new JTextField();
        addressPanel.add(streetNumberField);

        addressPanel.add(new JLabel("House Number*:"));
        JTextField houseNumberField = new JTextField();
        addressPanel.add(houseNumberField);

        mainPanel.add(addressPanel);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton registerButton = new JButton("REGISTER");
        registerButton.setBackground(Color.RED);
        registerButton.setForeground(Color.WHITE);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel);


        registration.add(mainPanel, BorderLayout.CENTER);


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Registration Successful!");
            }
        });


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empIdField.setText("");
                firstNameField.setText("");
                middleNameField.setText("");
                lastNameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                cityField.setText("");
                postalCodeField.setText("");
                suburbField.setText("");
                streetNumberField.setText("");
                houseNumberField.setText("");
                roleGroup.clearSelection();
            }
        });


    }


    public JPanel getPanel () {
        return registration;
    }
}