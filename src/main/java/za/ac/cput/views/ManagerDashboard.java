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

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayProfile();
                jFrame.dispose();
            }
        });

        navBar.add(logoPanel, BorderLayout.WEST);
        navBar.add(profileButton, BorderLayout.EAST);

        managerDashboard.add(navBar, BorderLayout.NORTH);
        jFrame.add(managerDashboard);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFrame.setVisible(true);

        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setBackground(new Color(247, 247, 247));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        String[] buttonNames = {"PHONES", "CUSTOMERS", "EMPLOYEES", "PURCHASES & SALES", "RETURNS", "LOG OUT"};
        int buttonCount = buttonNames.length;

        for (int i = 0; i < buttonCount; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setPreferredSize(new Dimension(170, 50));
            button.setFont(new Font("Arial", Font.BOLD, 13));
            button.setBorder(BorderFactory.createEmptyBorder());

            if (i == buttonCount - 1) {
                gbc.weighty = 1;
                gbc.anchor = GridBagConstraints.SOUTH;
            } else {
                gbc.weighty = 0;
                gbc.anchor = GridBagConstraints.NORTH;
            }

            gbc.gridy = i;
            sidebar.add(button, gbc);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    centerPanel.removeAll();
                    if (button.getText().equals("PHONES")) {
                        displayInventory();
                    } else if (button.getText().equals("CUSTOMERS")) {
                        displayCustomers();
                    } else if (button.getText().equals("EMPLOYEES")) {
                        displayEmployees();
                    } else if (button.getText().equals("PURCHASES & SALES")) {
                        displayPurchasesAndSales();
                    }  else if (button.getText().equals("RETURNS")) {
                        displayReturns();
                    } else if (button.getText().equals("LOG OUT")) {
                        int confirm = JOptionPane.showConfirmDialog(managerDashboard, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            jFrame.dispose();
                            Welcome welcome = new Welcome();
                            welcome.Welcome();
                        }
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

    private void displayPurchasesAndSales() {
        PhoneStatisticsView statisticsView = new PhoneStatisticsView();
        centerPanel.removeAll();
        centerPanel.add(statisticsView.getStatistics());
    }

    private void displayInventory() {
        PhoneInventory phoneInventory = new PhoneInventory();
        centerPanel.removeAll();
        centerPanel.add(phoneInventory.getPhoneInventory());
    }

    private void displayReturns() {
        ReturnInventory returnInventory = new ReturnInventory();
        centerPanel.removeAll();
        centerPanel.add(returnInventory.getReturn());
    }

    private void displayEmployees() {
        DisplayEmployee displayEmployee = new DisplayEmployee();
        centerPanel.removeAll();
        centerPanel.add(displayEmployee.getDisplayEmployee());
    }

    private void displayProfile() {
        JFrame frame = new JFrame("Employee Profile");
        frame.add(new EmployeeProfile());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        jFrame.dispose();
    }
    private void displayCustomers() {
        CustomerPage customers = new CustomerPage();
        centerPanel.removeAll();
        centerPanel.add(customers.getCustomers());
    }

}

