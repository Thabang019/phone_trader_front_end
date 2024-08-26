package za.ac.cput.views;

import za.ac.cput.dto.EmployeeStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ManagerDashboard {

    private JPanel managerDashboard;
    public ManagerDashboard() {

        managerDashboard = new JPanel(new BorderLayout());

        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(new Color(247, 247, 247));
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(247, 247, 247));
        ImageIcon logo = new ImageIcon("pic/logo.png");
        Image logoImage = logo.getImage();
        Image resizedLogo = logoImage.getScaledInstance(150, 80, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogo);
        JLabel logoLabel = new JLabel(resizedLogoIcon);
        logoPanel.add(logoLabel);


        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profilePanel.setBackground(new Color(247, 247, 247));
        JButton profileButton = new JButton(EmployeeStorage.getInstance().getEmployee().getFirstName());
        profileButton.setFont(new Font("Arial", Font.PLAIN, 12));
        ImageIcon originalIcon = new ImageIcon("pic/profile.png");
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        profileButton.setIcon(resizedIcon);
        profileButton.setHorizontalAlignment(SwingConstants.RIGHT);
        profileButton.setPreferredSize(new Dimension(130, 40));
        profileButton.setIconTextGap(5);
        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);
        profileButton.setFocusPainted(false);
        profilePanel.add(profileButton);


        navBar.add(logoPanel, BorderLayout.WEST);
        navBar.add(profilePanel, BorderLayout.EAST);


        managerDashboard.add(navBar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(247, 247, 247));
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayProfile();
            }
        });


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

    private void displayProfile() {
        JFrame frame = new JFrame("Phone Trader Application");
        frame.add(new EmployeeProfile());
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
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

