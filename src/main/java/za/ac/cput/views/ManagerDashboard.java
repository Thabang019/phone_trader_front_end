package za.ac.cput.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ManagerDashboard {

    private JPanel managerDashboard;
    public ManagerDashboard() {

        managerDashboard = new JPanel(new BorderLayout());

        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(new Color(255, 255, 228));
        JLabel logoLabel = new JLabel("Phone-Trader", SwingConstants.LEFT);
        logoLabel.setForeground(Color.RED);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 22));
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(255, 255, 228));
        logoPanel.add(logoLabel);


        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profilePanel.setBackground(new Color(255, 255, 228));
        JLabel profileLabel = new JLabel("Me  ");
        profileLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        ImageIcon profileIcon = new ImageIcon("path/to/profile/pic.png");
        profileLabel.setIcon(profileIcon);
        profilePanel.add(profileLabel);


        navBar.add(logoPanel, BorderLayout.WEST);
        navBar.add(profilePanel, BorderLayout.EAST);


        managerDashboard.add(navBar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);


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
                        displayEmployees();
                        //managerDashboard.setVisible(false);
                    }
                });

            } else if (buttonNames[i].equals("Cellphone Sales")) {
                button.setBackground(Color.LIGHT_GRAY);
            } else if (buttonNames[i].equals("Device Inventory")) {
                button.setBackground(Color.LIGHT_GRAY);
            } else if (buttonNames[i].equals("Customers")) {
                button.setBackground(Color.LIGHT_GRAY);
            } else if (buttonNames[i].equals("Sellers")) {
                button.setBackground(new Color(144, 238, 144));
            } else if (buttonNames[i].equals("Logged queries")) {
                button.setBackground(Color.LIGHT_GRAY);
            } else if (buttonNames[i].equals("Weekly Reports")) {
                button.setBackground(Color.LIGHT_GRAY);
            } else if (buttonNames[i].equals("Profit")) {
                button.setBackground(Color.LIGHT_GRAY);
            }


            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            centerPanel.add(button, gbc);
        }


        managerDashboard.add(centerPanel, BorderLayout.CENTER);

    }


    private void displayEmployees() {
       JFrame frame = new JFrame("Phone Trader Application");
        DisplayEmployee displayEmployee = new DisplayEmployee();
        frame.add(displayEmployee.getPanel());
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public JPanel getPanel() {
        return managerDashboard;
    }
}

