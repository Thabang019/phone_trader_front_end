package za.ac.cput.views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {


    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {

        loginPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(176, 16, 26));
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel welcomeLabel = new JLabel("Welcome to Phone-Trader");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.WHITE);
        leftPanel.add(welcomeLabel, gbc);
        loginPanel.add(leftPanel);


        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(10, 10, 10, 10);
        rightGbc.fill = GridBagConstraints.HORIZONTAL;


        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.gridwidth = 2;
        rightGbc.anchor = GridBagConstraints.CENTER;
        JLabel userIcon = new JLabel(new ImageIcon("C:\\Users\\Okuhle.G\\OneDrive\\Pictures\\login"));  // Replace with the correct path to your user icon
        rightPanel.add(userIcon, rightGbc);

        rightGbc.gridy++;
        rightGbc.gridwidth = 1;
        rightGbc.anchor = GridBagConstraints.EAST;
        JLabel usernameLabel = new JLabel("Username");
        rightPanel.add(usernameLabel, rightGbc);
        usernameField = new JTextField(50);
        rightGbc.gridx = 1;
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(usernameField, rightGbc);


        rightGbc.gridy++;
        rightGbc.gridx = 0;
        rightGbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password");
        rightPanel.add(passwordLabel, rightGbc);
        passwordField = new JPasswordField(50);
        rightGbc.gridx = 1;
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(passwordField, rightGbc);


        rightGbc.gridy++;
        rightGbc.gridx = 1;
        rightGbc.gridwidth = 1;
        rightGbc.anchor = GridBagConstraints.WEST;
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(176, 16, 26));
        loginButton.setForeground(Color.WHITE);
        rightPanel.add(loginButton, rightGbc);
        loginPanel.add(rightPanel);





        loginPanel.add(leftPanel, BorderLayout.WEST);
        loginPanel.add(rightPanel, BorderLayout.CENTER);

    }

    public JPanel getPanel() {
        return loginPanel;
    }

}