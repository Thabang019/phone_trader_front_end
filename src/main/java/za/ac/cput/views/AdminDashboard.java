package za.ac.cput.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftNavPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftNavPanel.setBackground(Color.WHITE);

        JLabel manageEmployeesLabel = new JLabel("Manage Employees");
        manageEmployeesLabel.setForeground(Color.BLACK);
        manageEmployeesLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel logoutLabel = new JLabel("Logout");
        logoutLabel.setForeground(Color.BLACK);
        logoutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel rightNavPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightNavPanel.setBackground(Color.WHITE);

        JLabel initialsLabel = new JLabel("TA");
        initialsLabel.setOpaque(true);
        initialsLabel.setBackground(Color.CYAN);
        initialsLabel.setForeground(Color.WHITE);
        initialsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        initialsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        initialsLabel.setPreferredSize(new Dimension(30, 30));

        ImageIcon logo = new ImageIcon("pic/logo.png");
        Image logoImage = logo.getImage();
        Image resizedLogo = logoImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogo);
        JLabel logoLabel = new JLabel(resizedLogoIcon);

        leftNavPanel.add(logoLabel);
        leftNavPanel.add(manageEmployeesLabel);
        rightNavPanel.add(initialsLabel);
        rightNavPanel.add(logoutLabel);

        navPanel.add(leftNavPanel, BorderLayout.WEST);
        navPanel.add(rightNavPanel, BorderLayout.EAST);

        add(navPanel, BorderLayout.NORTH);

        JPanel yellowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        yellowPanel.setBackground(Color.YELLOW);
        yellowPanel.setPreferredSize(new Dimension(900, 40));
        yellowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel dateLabel = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        dateLabel.setForeground(Color.BLACK);

        JLabel welcomeLabel = new JLabel("Welcome: Timothy");
        welcomeLabel.setForeground(Color.BLACK);

        yellowPanel.add(dateLabel);
        yellowPanel.add(welcomeLabel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(navPanel, BorderLayout.NORTH);
        topPanel.add(yellowPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        String[] buttonTexts = {"Employees", "Cellphone Sales", "Manage User Profile", "Device Inventory", "Customers", "Password Updates", "Sellers", "Settings", "Weekly Reports"};
        Dimension smallButtonSize = new Dimension(250, 200);
        Dimension largeButtonSize = new Dimension(510, 200);

        for (int i = 0; i < 9; i++) {
            JButton button = new JButton(buttonTexts[i]);
            button.setBackground(Color.RED.darker());
            button.setForeground(Color.WHITE);
            button.setFocusable(false);

            if (i % 3 == 2) {
                gbc.gridwidth = 2;
                button.setPreferredSize(largeButtonSize);
            } else {
                gbc.gridwidth = 1;
                button.setPreferredSize(smallButtonSize);
            }
            gbc.gridx = i % 3;
            gbc.gridy = i / 3;

            switch (i) {
                case 0 -> button.addActionListener(e -> {
                    System.out.println("Employees button clicked!");
                    DisplayEmployee employees = new DisplayEmployee();
                    employees.DisplayEmployee();
                });
                case 1 -> button.addActionListener(e -> {
                    System.out.println("Cellphone Sales button clicked!");

                });
                case 2 -> button.addActionListener(e -> {
                    System.out.println("Manage User Profile button clicked!");
                    JFrame frame = new JFrame("Phone Trader Application");
                    frame.add(new EmployeeProfile());
                    frame.pack();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                });
                case 3 -> button.addActionListener(e -> {
                    System.out.println("Device Inventory button clicked!");
                });
                case 4 -> button.addActionListener(e -> {
                    System.out.println("Customers button clicked!");
                });
                case 5 -> button.addActionListener(e -> {
                    System.out.println("Password Updates button clicked!");
                });
                case 6 -> button.addActionListener(e -> {
                    System.out.println("Sellers button clicked!");
                });
                case 7 -> button.addActionListener(e -> {
                    System.out.println("Settings button clicked!");
                });
                case 8 -> button.addActionListener(e -> {
                    System.out.println("Weekly Reports button clicked!");
                });
            }

            mainPanel.add(button, gbc);
        }

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminDashboard::new);
    }
}
