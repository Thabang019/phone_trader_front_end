package za.ac.cput.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NewSale {

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Sales Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 600);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the left panel for the sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(230, 230, 230)); // Light grey background
        sidebar.setPreferredSize(new Dimension(150, 600));

        // Add icons or labels to the sidebar
        JLabel dashboardLabel = new JLabel("DASHBOARD");
        dashboardLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dashboardLabel.setIcon(new ImageIcon("dashboard_icon.png")); // Replace with your icon path

        JLabel logoutLabel = new JLabel("Log Out");
        logoutLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutLabel.setIcon(new ImageIcon("logout_icon.png")); // Replace with your icon path

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(dashboardLabel);
        sidebar.add(Box.createVerticalGlue()); // Push logout to the bottom
        sidebar.add(logoutLabel);

        // Top panel for date and employee selection
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(1024, 60));

        // Add employee selection combo box and date label
        JPanel employeePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel employeeLabel = new JLabel("Select Employee Number:");
        JComboBox<String> employeeComboBox = new JComboBox<>(new String[] {"163424267", "163424268", "163424269"});
        employeePanel.add(employeeLabel);
        employeePanel.add(employeeComboBox);

        JLabel dateLabel = new JLabel("03 April 2023, 15:00PM");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        topPanel.add(employeePanel, BorderLayout.WEST);
        topPanel.add(dateLabel, BorderLayout.EAST);

        // Create the center panel for search and tables
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // First search panel
        JPanel searchPanel1 = new JPanel(new BorderLayout());
        JTextField searchField1 = new JTextField("Enter Phone IMIE Number", 20);
        JButton searchButton1 = new JButton("Search");
        searchButton1.setBackground(new Color(192, 0, 0)); // Red background
        searchButton1.setForeground(Color.WHITE);
        searchPanel1.add(searchField1, BorderLayout.CENTER);
        searchPanel1.add(searchButton1, BorderLayout.EAST);

        // Table for the first search result
        JPanel tablePanel1 = new JPanel(new BorderLayout());
        String[] columns1 = {"NO", "DATE", "ITEM", "PRICE", "QTY", "SUBTOTAL"};
        Object[][] data1 = {
                {1, "2022-10-01", "Strawberry Pancake", "Rp.28.000", 1, "Rp.28.000"}
        };
        DefaultTableModel model1 = new DefaultTableModel(data1, columns1);
        JTable table1 = new JTable(model1);
        JScrollPane scrollPane1 = new JScrollPane(table1);
        tablePanel1.add(scrollPane1, BorderLayout.CENTER);

        // Second search panel
        JPanel searchPanel2 = new JPanel(new BorderLayout());
        JTextField searchField2 = new JTextField("Find Buyer By ID Number", 20);
        JButton searchButton2 = new JButton("Search");
        searchButton2.setBackground(new Color(192, 0, 0)); // Red background
        searchButton2.setForeground(Color.WHITE);
        searchPanel2.add(searchField2, BorderLayout.CENTER);
        searchPanel2.add(searchButton2, BorderLayout.EAST);

        // Table for the second search result
        JPanel tablePanel2 = new JPanel(new BorderLayout());
        String[] columns2 = {"NO", "DATE", "ITEM", "PRICE", "QTY", "SUBTOTAL"};
        Object[][] data2 = {
                {1, "2022-10-01", "Strawberry Pancake", "Rp.28.000", 1, "Rp.28.000"}
        };
        DefaultTableModel model2 = new DefaultTableModel(data2, columns2);
        JTable table2 = new JTable(model2);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        tablePanel2.add(scrollPane2, BorderLayout.CENTER);

        // Add Buyer Button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("ADD BUYER");
        addButton.setBackground(new Color(192, 0, 0)); // Red background
        addButton.setForeground(Color.WHITE);
        addButtonPanel.add(addButton);

        // Add all components to center panel
        centerPanel.add(searchPanel1);
        centerPanel.add(tablePanel1);
        centerPanel.add(searchPanel2);
        centerPanel.add(tablePanel2);
        centerPanel.add(addButtonPanel);

        // Add panels to main panel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add main panel to frame
        frame.add(mainPanel);

        // Set the frame to be visible
        frame.setVisible(true);
    }
}


