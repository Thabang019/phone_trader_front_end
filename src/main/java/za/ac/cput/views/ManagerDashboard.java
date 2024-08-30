package za.ac.cput.views;

import za.ac.cput.dto.EmployeeStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ManagerDashboard {
    private JFrame jFrame;
    private JPanel managerDashboard;

    public JFrame ManagerDashboard() {
        jFrame = new JFrame();
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
        jFrame.add(managerDashboard);
        jFrame.setPreferredSize(new Dimension(1000, 800));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);


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


        String[] buttonNames = {"EMPLOYEES", "PHONE SALES", "PHONES INVENTORY", "PURCHASED PHONES", "ACCESSORIES", "RETURNS", "CUSTOMERS", "LOGOUT"};

        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setPreferredSize(new Dimension(170, 100));
            button.setFont(new Font("Arial", Font.BOLD, 13));
            button.setBackground(new Color(70, 130, 180));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setOpaque(true);

            if (buttonNames[i].equals("EMPLOYEES")) {
                button.setForeground(Color.YELLOW);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        displayEmployees();
                        jFrame.dispose();
                    }
                });

            } else if (buttonNames[i].equals("PHONE SALES")) {
                button.setForeground(Color.WHITE);

            } else if (buttonNames[i].equals("PHONES INVENTORY")) {
                button.setForeground(Color.WHITE);

            } else if (buttonNames[i].equals("PURCHASED PHONES")) {
                button.setForeground(Color.WHITE);

            } else if (buttonNames[i].equals("ACCESSORIES")) {
                button.setForeground(Color.WHITE);

            } else if (buttonNames[i].equals("RETURNS")) {
                button.setForeground(Color.WHITE);

            } else if (buttonNames[i].equals("CUSTOMERS")) {
                button.setForeground(Color.WHITE);

            } else if (buttonNames[i].equals("LOGOUT")) {
                button.setForeground(Color.RED);
            }

            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            centerPanel.add(button, gbc);

            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    button.setBackground(new Color(41, 128, 185));
                }

                public void mouseExited(MouseEvent evt) {
                    button.setBackground(new Color(52, 152, 219));
                }
            });
        }

        managerDashboard.add(centerPanel, BorderLayout.CENTER);

        return jFrame;
    }

    private void displayProfile() {
        JFrame frame = new JFrame("Phone Trader Application");
        frame.add(new EmployeeProfile());
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void displayEmployees() {
        DisplayEmployee displayEmployee = new DisplayEmployee();
        displayEmployee.DisplayEmployee();

    }


}

