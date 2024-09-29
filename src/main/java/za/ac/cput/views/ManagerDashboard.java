package za.ac.cput.views;

import okhttp3.OkHttpClient;
import za.ac.cput.dto.EmployeeStorage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerDashboard {
    private JFrame jFrame;
    private JPanel managerDashboard;
    private JPanel centerPanel;
    private static DefaultTableModel model;
    private JTextField searchField;
    private static final OkHttpClient client = new OkHttpClient();

    private static int currentPage = 1;
    private static int pageSize = 20;

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

        JPanel logOutPanel = new JPanel(new FlowLayout((FlowLayout.RIGHT)));
        logOutPanel.setBackground(new Color(247, 247, 247));
        JButton logOutButton = new JButton("Log Out");
        logOutPanel.add(logOutButton);

        navBar.add(logoPanel, BorderLayout.WEST);
        navBar.add(logOutPanel, BorderLayout.EAST);

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(managerDashboard, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    jFrame.dispose();
                    Welcome welcome = new Welcome();
                    welcome.Welcome();
                }
            }
        });

        managerDashboard.add(navBar, BorderLayout.NORTH);
        jFrame.add(managerDashboard);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFrame.setVisible(true);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(247, 247, 247));

        JButton profileButton = new JButton(EmployeeStorage.getInstance().getEmployee().getFirstName());
        profileButton.setFont(new Font("Arial", Font.PLAIN, 12));
        ImageIcon originalIcon = new ImageIcon("pic/profile.png");
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        profileButton.setIcon(resizedIcon);
        profileButton.setHorizontalAlignment(SwingConstants.LEFT);
        profileButton.setPreferredSize(new Dimension(130, 40));
        profileButton.setIconTextGap(5);
        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);
        profileButton.setFocusPainted(false);
        sidebar.add(profileButton);

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayProfile();
                jFrame.dispose();
            }
        });

        String[] buttonNames = {"EMPLOYEES", "CUSTOMERS", "PURCHASES & SALES", "INVENTORY", "RETURNS"};
        for (String buttonName : buttonNames) {
            JButton button = new JButton(buttonName);
            button.setPreferredSize(new Dimension(170, 50));
            button.setFont(new Font("Arial", Font.BOLD, 13));
            button.setBorder(BorderFactory.createEmptyBorder());

            sidebar.add(button);
            sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    centerPanel.removeAll();

                    if (buttonName.equals("EMPLOYEES")) {
                        displayEmployees();
                    } else if (buttonName.equals("CUSTOMERS")) {
                        displayCustomers();
                    } else if (buttonName.equals("PURCHASES & SALES")) {
                        displayPurchasesAndSales();
                    } else if (buttonName.equals("INVENTORY")) {
                        displayInventory();
                    } else if (buttonName.equals("RETURNS")) {
                        displayReturns();
                    }

                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            });
        }

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(247, 247, 247));
        managerDashboard.add(sidebar, BorderLayout.WEST);
        managerDashboard.add(centerPanel, BorderLayout.CENTER);

        jFrame.add(managerDashboard);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFrame.setVisible(true);

        return jFrame;
    }

    private void displayCustomers() {
        JLabel label = new JLabel("CUSTOMERS section is under construction");
        centerPanel.add(label, BorderLayout.CENTER);
    }

    private void displayPurchasesAndSales() {
        JLabel label = new JLabel("PURCHASES & SALES section is under construction");
        centerPanel.add(label, BorderLayout.CENTER);
    }

    private void displayInventory() {
        PhoneInventory phoneInventory = new PhoneInventory();
        centerPanel.removeAll();
        centerPanel.add(phoneInventory.getPhoneInventory());
    }

    private void displayReturns() {
        JLabel label = new JLabel("RETURNS section is under construction");
        centerPanel.add(label, BorderLayout.CENTER);
    }

    private void displayProfile() {
        JFrame frame = new JFrame("Employee Profile");
        frame.add(new EmployeeProfile());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        jFrame.dispose();

    }

    private void displayEmployees() {
        DisplayEmployee displayEmployee = new DisplayEmployee();
        centerPanel.removeAll();
        centerPanel.add(displayEmployee.getDisplayEmployee());
    }
}

