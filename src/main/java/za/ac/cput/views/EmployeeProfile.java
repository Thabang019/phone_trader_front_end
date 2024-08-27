package za.ac.cput.views;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.Employee;
import za.ac.cput.dto.EmployeeStorage;
import za.ac.cput.dto.TokenStorage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class EmployeeProfile extends JPanel {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    final OkHttpClient client = new OkHttpClient();
    final Gson gson = new Gson();
    private JPasswordField newPasswordField;
    private JTextField firstName,lastName, middleName, employeeId, phoneNumber, email, houseNumber, mainStreet, suburb, city, postalCode;

    private JScrollPane scrollPane;
    public EmployeeProfile() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel personalInfoPanel = createSectionPanel("Personal Information");
        addPersonalInfoComponents(personalInfoPanel);

        JPanel contactInfoPanel = createSectionPanel("Contact Information");
        addContactInfoComponents(contactInfoPanel);

        JPanel addressInfoPanel = createSectionPanel("Address Information");
        addAddressInfoComponents(addressInfoPanel);

        JPanel passwordPanel = createSectionPanel("Update Password");
        addPasswordComponents(passwordPanel);

        add(personalInfoPanel);
        add(Box.createVerticalStrut(15));
        add(contactInfoPanel);
        add(Box.createVerticalStrut(15));
        add(addressInfoPanel);
        add(Box.createVerticalStrut(15));
        add(passwordPanel);
    }

    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    private void addPersonalInfoComponents(JPanel panel) {
        GridBagConstraints gbc = createGbc(0, 0);

        panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx++;
        firstName = new JTextField(EmployeeStorage.getInstance().getEmployee().getFirstName());
        panel.add(firstName, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Middle Name:"), gbc);
        gbc.gridx++;
         middleName = new JTextField(EmployeeStorage.getInstance().getEmployee().getMiddleName());
        panel.add(middleName, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx++;
         lastName = new JTextField(EmployeeStorage.getInstance().getEmployee().getLastName());
        panel.add(lastName, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Employee Id:"), gbc);
        gbc.gridx++;
         employeeId = new JTextField(String.valueOf(EmployeeStorage.getInstance().getEmployee().getEmployeeID()));
        panel.add(employeeId, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton updatePersonalInfo = new JButton("Personal Details");
        updatePersonalInfo.setBackground(new Color(192, 0, 0));
        updatePersonalInfo.setForeground(Color.WHITE);
        updatePersonalInfo.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(updatePersonalInfo, gbc);

        updatePersonalInfo.addActionListener(e -> {
            Long id = Long.valueOf(employeeId.getText());

            if(id != EmployeeStorage.getInstance().getEmployee().getEmployeeID()){
                JOptionPane.showMessageDialog(panel, "Employee Id Cannot be Update");

            } else if (!firstName.getText().equals(EmployeeStorage.getInstance().getEmployee().getFirstName())){
                updateFirstName();
                JOptionPane.showMessageDialog(panel, "Successfully Updated First Name");

            } else if (!lastName.getText().equals(EmployeeStorage.getInstance().getEmployee().getLastName())){
                updateLastName();
                JOptionPane.showMessageDialog(panel, "Successfully Updated last Name");

            } else if (!middleName.getText().equals(EmployeeStorage.getInstance().getEmployee().getMiddleName())){
                updateMiddleName();
                JOptionPane.showMessageDialog(panel, "Successfully Updated Middle Name");

            }
        });
    }

    private void addContactInfoComponents(JPanel panel) {
        GridBagConstraints gbc = createGbc(0, 0);

        panel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx++;
         phoneNumber = new JTextField(EmployeeStorage.getInstance().getEmployee().getContact().getPhoneNumber());
        panel.add(phoneNumber, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx++;
         email = new JTextField(EmployeeStorage.getInstance().getEmployee().getContact().getEmail());
        panel.add(email, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton updateContact = new JButton("Update Contact");
        updateContact.setBackground(new Color(192, 0, 0));
        updateContact.setForeground(Color.WHITE);
        updateContact.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(updateContact, gbc);

        updateContact.addActionListener(e -> {
            if(!phoneNumber.getText().equals(EmployeeStorage.getInstance().getEmployee().getContact().getPhoneNumber())){
                updatePhoneNumber();
                JOptionPane.showMessageDialog(panel, "Phone Number Updated Successfully!");
            } else if (!email.getText().equals(EmployeeStorage.getInstance().getEmployee().getContact().getEmail())) {
                updateEmail();
                JOptionPane.showMessageDialog(panel, "Email Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(panel, "Contact Update Unsuccessfully!");
            }

        });


    }

    private void addAddressInfoComponents(JPanel panel) {
        GridBagConstraints gbc = createGbc(0, 0);

        panel.add(new JLabel("House Number:"), gbc);
        gbc.gridx++;
         houseNumber = new JTextField(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getNumber());
        panel.add(houseNumber, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Main Street:"), gbc);
        gbc.gridx++;
         mainStreet = new JTextField(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getStreet());
        panel.add(mainStreet, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Suburb:"), gbc);
        gbc.gridx++;
         suburb = new JTextField(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getSuburb());
        panel.add(suburb, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("City:"), gbc);
        gbc.gridx++;
         city = new JTextField(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getCity());
        panel.add(city, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Postal Code:"), gbc);
        gbc.gridx++;
         postalCode = new JTextField(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getPostalCode());
        panel.add(new JTextField(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getPostalCode()), gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton updateAddress = new JButton("Update Address");
        updateAddress.setBackground(new Color(192, 0, 0));
        updateAddress.setForeground(Color.WHITE);
        updateAddress.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(updateAddress, gbc);

        updateAddress.addActionListener(e -> {

            if(!houseNumber.getText().equals(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getNumber())){
                updateHouseNumber();
                JOptionPane.showMessageDialog(panel, "House Number Updated Successfully!");

            } else if (!mainStreet.getText().equals(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getStreet())) {
                updateMainStreet();
                JOptionPane.showMessageDialog(panel, "Main Street Updated Successfully!");

            } else if (!suburb.getText().equals(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getSuburb())) {
                updateSuburb();
                JOptionPane.showMessageDialog(panel, "Suburb Updated Successfully!");

            } else if (!city.getText().equals(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getCity())) {
                JOptionPane.showMessageDialog(panel, "Address updated successfully!");

            } else if (!postalCode.getText().equals(EmployeeStorage.getInstance().getEmployee().getContact().getAddress().getPostalCode())) {
                JOptionPane.showMessageDialog(panel, "Address updated successfully!");

            }else {
                JOptionPane.showMessageDialog(panel, "Address Update Unsuccessfully!");
            }
        });

    }

    private void addPasswordComponents(JPanel panel) {
        GridBagConstraints gbc = createGbc(0, 0);

        panel.add(new JLabel("Current Password:"), gbc);
        gbc.gridx++;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("New Password:"), gbc);
        gbc.gridx++;
        newPasswordField = new JPasswordField(20);
        panel.add(newPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Confirm New Password:"), gbc);
        gbc.gridx++;
        JPasswordField confirmPasswordField = new JPasswordField(20);
        panel.add(confirmPasswordField, gbc);

        // Add Update Button
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton updatePasswordButton = new JButton("Update Password");
        updatePasswordButton.setBackground(new Color(192, 0, 0));
        updatePasswordButton.setForeground(Color.WHITE);
        updatePasswordButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(updatePasswordButton, gbc);

        updatePasswordButton.addActionListener(e -> {

            String password = EmployeeStorage.getInstance().getEmployee().getPassword();
            String currentPassword = new String(passwordField.getPassword());

            if(!currentPassword.equalsIgnoreCase(password)){
                JOptionPane.showMessageDialog(panel, "Current Password Incorrect");

            } else if (!new String(newPasswordField.getPassword()).equalsIgnoreCase(new String(confirmPasswordField.getPassword()))){
                JOptionPane.showMessageDialog(panel, "Password Does Not Match");

            } else {
                updateEmployeePassword();
                JOptionPane.showMessageDialog(panel, "Password Updated Successfully");
            }
        });
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    public String updateEmployee(String url, Employee employee) throws IOException {
        String json = gson.toJson(employee);
        return post(url, json);
    }

    public void updateFirstName(){
        Employee employee = new Employee.Builder().copy(EmployeeStorage.getInstance().getEmployee()).setFirstName(firstName.getText()).build();
        try {
            String response = updateEmployee("http://localhost:8080/phone-trader/employee/update",employee);
            System.out.println(response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateLastName(){
        Employee employee = new Employee.Builder().copy(EmployeeStorage.getInstance().getEmployee()).setLastName(lastName.getText()).build();
        try {
            String response = updateEmployee("http://localhost:8080/phone-trader/employee/update",employee);
            System.out.println(response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateMiddleName(){
        Employee employee = new Employee.Builder().copy(EmployeeStorage.getInstance().getEmployee()).setMiddleName(middleName.getText()).build();
        try {
            String response = updateEmployee("http://localhost:8080/phone-trader/employee/update",employee);
            System.out.println(response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updatePhoneNumber(){
        Contact contact = new Contact.Builder().copy(EmployeeStorage.getInstance().getEmployee().getContact()).setPhoneNumber(phoneNumber.getText()).build();
        Employee employee = new Employee.Builder().copy(EmployeeStorage.getInstance().getEmployee()).setContact(contact).build();
        try {
            String response = updateEmployee("http://localhost:8080/phone-trader/employee/update",employee);
            System.out.println(response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateEmail(){
        Contact contact = new Contact.Builder().copy(EmployeeStorage.getInstance().getEmployee().getContact()).setEmail(email.getText()).build();
        Employee employee = new Employee.Builder().copy(EmployeeStorage.getInstance().getEmployee()).setContact(contact).build();
        try {
            String response = updateEmployee("http://localhost:8080/phone-trader/employee/update",employee);
            System.out.println(response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateHouseNumber(){
        Address address = new Address.Builder().copy(EmployeeStorage.getInstance().getEmployee().getContact().getAddress()).setNumber(houseNumber.getText()).buildAddress();
        Contact contact = new Contact.Builder().copy(EmployeeStorage.getInstance().getEmployee().getContact()).setAddress(address).build();
        Employee employee = new Employee.Builder().copy(EmployeeStorage.getInstance().getEmployee()).setContact(contact).build();
        try {
            String response = updateEmployee("http://localhost:8080/phone-trader/employee/update",employee);
            System.out.println(response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateMainStreet(){
        Address address = new Address.Builder().copy(EmployeeStorage.getInstance().getEmployee().getContact().getAddress()).setStreet(mainStreet.getText()).buildAddress();
        Contact contact = new Contact.Builder().copy(EmployeeStorage.getInstance().getEmployee().getContact()).setAddress(address).build();
        Employee employee = new Employee.Builder().copy(EmployeeStorage.getInstance().getEmployee()).setContact(contact).build();
        try {
            String response = updateEmployee("http://localhost:8080/phone-trader/employee/update",employee);
            System.out.println(response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateSuburb(){
        Address address = new Address.Builder().copy(EmployeeStorage.getInstance().getEmployee().getContact().getAddress()).setSuburb(suburb.getText()).buildAddress();
        Contact contact = new Contact.Builder().copy(EmployeeStorage.getInstance().getEmployee().getContact()).setAddress(address).build();
        Employee employee = new Employee.Builder().copy(EmployeeStorage.getInstance().getEmployee()).setContact(contact).build();
        try {
            String response = updateEmployee("http://localhost:8080/phone-trader/employee/update",employee);
            System.out.println(response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateEmployeePassword(){
        String newPassword = new String(newPasswordField.getPassword());
        Employee employee = new Employee.Builder().copy(EmployeeStorage.getInstance().getEmployee()).setPassword(newPassword).build();
        try {
            String response = updateEmployee("http://localhost:8080/phone-trader/employee/update",employee);
            System.out.println(response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
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
}
