package za.ac.cput.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.google.gson.Gson;
import kotlin.coroutines.jvm.internal.Boxing;
import okhttp3.*;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.Employee;
import za.ac.cput.domain.Address;
import za.ac.cput.dto.TokenStorage;
import za.ac.cput.factory.AddressFactory;
import za.ac.cput.factory.ContactFactory;
import za.ac.cput.factory.EmployeeFactory;


import javax.swing.*;
import java.io.IOException;

public class Registration {
    private JFrame newFrame;
    private JPanel registration;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public JFrame Registration() {

        newFrame = new JFrame();

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

        personalPanel.add(new JLabel("Middle Name:"));
        JTextField middleNameField = new JTextField();
        personalPanel.add(middleNameField);

        personalPanel.add(new JLabel("Last Name*:"));
        JTextField lastNameField = new JTextField();
        personalPanel.add(lastNameField);

        personalPanel.add(new JLabel("Role*:"));
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton sellerButton = new JRadioButton("Salseperson");
        JRadioButton managerButton = new JRadioButton("Manager");

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(sellerButton);
        roleGroup.add(managerButton);
        rolePanel.add(sellerButton);
        rolePanel.add(managerButton);
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
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel);

        registration.add(mainPanel, BorderLayout.CENTER);
        newFrame.add(registration);
        newFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        newFrame.setVisible(true);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String employeeId = empIdField.getText();
                    String firstName = firstNameField.getText();
                    String middleName = middleNameField.getText();
                    String lastName = lastNameField.getText();
                    String email = emailField.getText();
                    String phone = phoneField.getText();
                    String city = cityField.getText();
                    String postalCode = postalCodeField.getText();
                    String suburb = suburbField.getText();
                    String streetNumber = streetNumberField.getText();
                    String houseNumber = houseNumberField.getText();

                    Address address = AddressFactory.buildAddress(houseNumber, streetNumber, suburb, city, postalCode);

                    System.out.println(address);

                    Contact contact = ContactFactory.createContact(phone, email, address);

                    System.out.println(contact);

                    Employee.Role role = null;

                    if (sellerButton.isSelected()) {
                        role = Employee.Role.Salesperson;

                    } else if (managerButton.isSelected()) {
                        role = Employee.Role.Manager;
                    }

                    if (role == null) {
                        JOptionPane.showMessageDialog(null, "Please select a role for the employee.");
                        return;
                    }
                    Employee employee = EmployeeFactory.buildEmployee(Integer.parseInt(employeeId), firstName, middleName, lastName, "defaultPassword", contact, role);

                    System.out.println(employee);

                    String response = createEmployee("http://localhost:8080/phone-trader/employee/save", employee);

                    System.out.println(response);

                    JOptionPane.showMessageDialog(null, "Registration Successful!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Registration Failed: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                managerDashboard();
                newFrame.dispose();

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
        return newFrame;
    }

    public String createEmployee(String url, Employee employee) throws IOException {
        String json = gson.toJson(employee);
        return post(url, json);
    }

    private String post(String url, String json) throws IOException {
        String token = TokenStorage.getInstance().getToken();
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    private void managerDashboard() {
        ManagerDashboard displayManagerDashboard = new ManagerDashboard();
        displayManagerDashboard.ManagerDashboard();

    }
}