package za.ac.cput.views;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.domain.Employee;
import za.ac.cput.dto.EmployeeStorage;
import za.ac.cput.dto.JwtAuthenticationResponse;
import za.ac.cput.dto.SignInRequest;
import za.ac.cput.dto.TokenStorage;
import za.ac.cput.factory.SignInRequestFactory;
import za.ac.cput.util.BackgroundImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Login {
    private final OkHttpClient client = new OkHttpClient();
    private Request requestSignIn, requestEmployee;
    private final Gson gson = new Gson();
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox role;
    private JButton loginButton;
    private JFrame loginFrame;
    public Frame Login() {
        loginFrame = new JFrame();
        loginPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(176, 16, 26));
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel welcomeLabel = new JLabel("<html><center<h1>Welcome<br>to<br>Phone Trader</h1></center></html>");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.WHITE);
        leftPanel.add(welcomeLabel, gbc);
        loginPanel.add(leftPanel);

        JPanel rightPanel = new BackgroundImage("pic/welcome.png");;
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(10, 10, 10, 10);
        rightGbc.fill = GridBagConstraints.HORIZONTAL;

        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.gridwidth = 2;
        rightGbc.anchor = GridBagConstraints.CENTER;
        ImageIcon logo = new ImageIcon("pic/logo.png");
        Image logoImage = logo.getImage();
        Image resizedLogo = logoImage.getScaledInstance(350, 210, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogo);
        JLabel logoLabel = new JLabel(resizedLogoIcon);
        rightPanel.add(logoLabel, rightGbc);


        rightGbc.gridy = 1;
        rightGbc.gridwidth = 2;
        rightGbc.anchor = GridBagConstraints.CENTER;
        JLabel loginLabel = new JLabel("LOGIN", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rightPanel.add(loginLabel, rightGbc);

        rightGbc.gridy = 2;
        rightGbc.gridwidth = 1;
        rightGbc.gridx = 0;
        rightGbc.anchor = GridBagConstraints.EAST;
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        rightPanel.add(usernameLabel, rightGbc);
        usernameField = new JTextField(20);
        rightGbc.gridx = 1;
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(usernameField, rightGbc);

        rightGbc.gridy++;
        rightGbc.gridx = 0;
        rightGbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
        rightPanel.add(passwordLabel, rightGbc);
        passwordField = new JPasswordField(20);
        rightGbc.gridx = 1;
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(passwordField, rightGbc);

        rightGbc.gridy++;
        rightGbc.gridx = 0;
        rightGbc.gridwidth = 2;
        role = new JComboBox<>(new String[]{"SALESPERSON", "MANAGER"});
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(role, rightGbc);

        rightGbc.gridy++;
        rightGbc.gridx = 0;
        rightGbc.gridwidth = 2;
        rightGbc.anchor = GridBagConstraints.WEST;
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(176, 16, 26));
        loginButton.setForeground(Color.WHITE);
        rightPanel.add(loginButton, rightGbc);
        loginPanel.add(rightPanel);

        role.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SignInRequest signInRequest = SignInRequestFactory.createSignInRequest(Long.valueOf(usernameField.getText()), new String(passwordField.getPassword()));

                try {
                    JwtAuthenticationResponse response = sendPostRequest("http://localhost:8080/phone-trader/authentication/signin", signInRequest);
                    String token = response.getToken();
                    System.out.println("Token Recieved");
                    TokenStorage.getInstance().setToken(token);

                    Employee employee = readEmployee("http://localhost:8080/phone-trader/employee/read/" + Long.valueOf(usernameField.getText()));

                    String password = new String(passwordField.getPassword());
                    Employee newEmp = new Employee.Builder().copy(employee).setPassword(password).build();
                    EmployeeStorage.getInstance().setEmployee(newEmp);

                   if(role.getSelectedItem().toString().equalsIgnoreCase("SALESPERSON")){
                        openSalesPersonDashboard();
                        loginFrame.dispose();

                    }else if(role.getSelectedItem().toString().equalsIgnoreCase("MANAGER") && employee.getRole().equals(Employee.Role.Manager)) {
                        openManagerDashboard();
                        loginFrame.dispose();
                    } else {
                       JOptionPane.showMessageDialog(null, "Permission Denied !");
                   }
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Verify Your Credentials and Try Again !");
                    usernameField.setText("");
                    passwordField.setText("");
                    exception.printStackTrace();
                }
            }
        });

        loginPanel.add(rightPanel, BorderLayout.CENTER);
        loginFrame.add(loginPanel);
        loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loginFrame.setVisible(true);

        return loginFrame;
    }

    public JwtAuthenticationResponse sendPostRequest(String url, SignInRequest signInRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInputString = objectMapper.writeValueAsString(signInRequest);

        RequestBody body = RequestBody.create(jsonInputString, MediaType.get("application/json; charset=utf-8"));
        requestSignIn = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(requestSignIn).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, JwtAuthenticationResponse.class);
        }
    }
    public Employee readEmployee(String url) throws IOException {
        String token = TokenStorage.getInstance().getToken();
        requestEmployee = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(requestEmployee).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(response.body().string(), Employee.class);
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    private void openSalesPersonDashboard() {
        SalesPersonDashboard salesPersonDashboard = new SalesPersonDashboard();
        salesPersonDashboard.SalesPersonDashboard();
    }

    private void openManagerDashboard() {
        ManagerDashboard managerDashboard = new ManagerDashboard();
        managerDashboard.ManagerDashboard();
    }
}


