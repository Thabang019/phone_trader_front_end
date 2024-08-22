package za.ac.cput.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ManagerDashboard extends JFrame {

    public ManagerDashboard() {
        // Set the title and default close operation
        setTitle("Phone-Trader Manager Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Navigation Bar
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(new Color(255, 255, 228));  // Cream background color

        // Logo Section
        JLabel logoLabel = new JLabel("Phone-Trader", SwingConstants.LEFT);
        logoLabel.setForeground(Color.RED);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 22));
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(255, 255, 228));  // Same as navBar color
        logoPanel.add(logoLabel);

        // Navigation Links
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        linkPanel.setBackground(new Color(255, 255, 228));  // Same as navBar color
        String[] navLinks = {"Home", "Manage Employees", "Logout", "FAQ"};
        for (String link : navLinks) {
            JLabel navLink = new JLabel(link);
            navLink.setFont(new Font("Arial", Font.PLAIN, 16));
            linkPanel.add(navLink);
        }

        // Profile Section
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profilePanel.setBackground(new Color(255, 255, 228));  // Same as navBar color
        JLabel profileLabel = new JLabel("Me  ");
        profileLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        ImageIcon profileIcon = new ImageIcon("path/to/profile/pic.png");  // Replace with actual profile picture path
        profileLabel.setIcon(profileIcon);
        profilePanel.add(profileLabel);

        // Add all to navBar
        navBar.add(logoPanel, BorderLayout.WEST);
        navBar.add(linkPanel, BorderLayout.CENTER);
        navBar.add(profilePanel, BorderLayout.EAST);

        // Add navBar to the top of the frame
        add(navBar, BorderLayout.NORTH);

        // Center Panel for Dashboard Content
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(240, 248, 255));  // Light cyan background color
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);  // Margin around buttons

        // Define buttons for the dashboard
        String[] buttonNames = {"Employees", "Cellphone Sales", "Device Inventory", "Customers", "Sellers", "Logged queries", "Weekly Reports", "Profit"};
        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setPreferredSize(new Dimension(160, 100));
            button.setFont(new Font("Arial", Font.PLAIN, 14));

            if (buttonNames[i].equals("Employees")) {
                button.setBackground(Color.YELLOW);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openRegistration();
                    }
                });

            } else if (buttonNames[i].equals("Cellphone Sales")) {
                button.setBackground(Color.LIGHT_GRAY);
            } else if (buttonNames[i].equals("Device Inventory")) {
                button.setBackground(Color.LIGHT_GRAY);
            } else if (buttonNames[i].equals("Customers")) {
                button.setBackground(Color.LIGHT_GRAY);
            } else if (buttonNames[i].equals("Sellers")) {
                button.setBackground(new Color(144, 238, 144));  // Light green
            } else if (buttonNames[i].equals("Logged queries")) {
                button.setBackground(Color.LIGHT_GRAY);
            } else if (buttonNames[i].equals("Weekly Reports")) {
                button.setBackground(Color.LIGHT_GRAY);
            } else if (buttonNames[i].equals("Profit")) {
                button.setBackground(Color.LIGHT_GRAY);
            }

            // Set button position in the grid
            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            centerPanel.add(button, gbc);
        }

        // Add centerPanel to the center of the frame
        add(centerPanel, BorderLayout.CENTER);

        // Set the size and location of the window
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    private void openRegistration() {
       JFrame frame = new JFrame("Phone Trader Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Registration registrationForm = new Registration();
        frame.add(registrationForm.getPanel());
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManagerDashboard managerDashboard = new ManagerDashboard();
            managerDashboard.setVisible(true);
        });
    }
}

