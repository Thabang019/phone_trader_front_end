package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.domain.Phone;
import za.ac.cput.dto.TokenStorage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PhoneInventory {
    private JPanel mainPanel;
    private static DefaultTableModel tableModel;
    private JTextField searchField;
    private static final OkHttpClient client = new OkHttpClient();

    public PhoneInventory() {

        mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Device Inventory", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Find Phone by IMEI:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(192, 0, 0));
        searchButton.setForeground(Color.WHITE);
        searchPanel.add(label);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel centerButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton collectionButton = new JButton("Collection");
        collectionButton.setBackground(new Color(192, 0, 0));
        collectionButton.setForeground(Color.WHITE);
        collectionButton.setBorderPainted(false);
        collectionButton.setFocusPainted(false);
        collectionButton.setFont(new Font("Arial", Font.BOLD, 15));
        collectionButton.setPreferredSize(new Dimension(120, 35));

        JButton iphoneButton = new JButton("iPhone");
        iphoneButton.setBackground(new Color(192, 0, 0));
        iphoneButton.setForeground(Color.WHITE);
        iphoneButton.setBorderPainted(false);
        iphoneButton.setFocusPainted(false);
        iphoneButton.setFont(new Font("Arial", Font.BOLD, 15));
        iphoneButton.setPreferredSize(new Dimension(120, 35));

        JButton androidButton = new JButton("Android");
        androidButton.setBackground(new Color(192, 0, 0));
        androidButton.setForeground(Color.WHITE);
        androidButton.setBorderPainted(false);
        androidButton.setFocusPainted(false);
        androidButton.setFont(new Font("Arial", Font.BOLD, 15));
        androidButton.setPreferredSize(new Dimension(120, 35));

        centerButton.add(Box.createRigidArea(new Dimension(0, 30)));
        centerButton.add(iphoneButton);
        centerButton.add(Box.createRigidArea(new Dimension(0, 15)));
        centerButton.add(androidButton);
        centerButton.add(Box.createRigidArea(new Dimension(0, 15)));
        centerButton.add(collectionButton);
        centerButton.add(Box.createRigidArea(new Dimension(0, 15)));

        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(centerButton, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"IMEI", "Brand", "Model", "Color", "Price", "Status","Condition"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(192, 0, 0));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("BACK");
        JButton addButton = new JButton("ADD PHONE");
        backButton.setBackground(new Color(192, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(192, 0, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(addButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadAllPhones();
        searchButton.addActionListener(e -> searchByImei());

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManagerDashboard();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPhoneRegistration();
            }
        });
    }

    private void openManagerDashboard() {
        ManagerDashboard dashboard = new ManagerDashboard();
        dashboard.ManagerDashboard();
    }

    private void openPhoneRegistration() {
        PhoneRegistration registrationForm = new PhoneRegistration();
        registrationForm.showRegistration();
    }

    private void loadAllPhones() {

        tableModel.setRowCount(0);

        try {
            final String url = "http://localhost:8080/phone-trader/phones/getall";
            String responseBody = sendRequest(url);

            if (responseBody.startsWith("[")) {
                JSONArray phones = new JSONArray(responseBody);
                Gson gson = new Gson();
                for (int i = 0; i < phones.length(); i++) {
                    JSONObject phoneObject = phones.getJSONObject(i);
                    Phone phone = gson.fromJson(phoneObject.toString(), Phone.class);

                    String specification = phone.getSpecification() != null ? phone.getSpecification().toString() : "null";
                    String condition = phone.getCondition() != null ? phone.getCondition().toString() : "null";

                    tableModel.addRow(new Object[]{
                            phone.getImei(),
                            phone.getBrand(),
                            phone.getModel(),
                            phone.getColor(),
                            phone.getPrice(),
                            phone.getStatus(),
                            condition
                    });
                }
            } else {
                System.err.println("Expected a JSON array but got: " + responseBody);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch phones", e);
        }
    }


    private void searchByImei() {
        String imei = searchField.getText().trim();

        if (imei.isEmpty()) {
            loadAllPhones();
            return;
        }

        try {
            final String url = "http://localhost:8080/phone-trader/phones/read/" + imei;

            String responseBody = sendRequest(url);

            if (responseBody != null && !responseBody.isEmpty()) {
                Gson gson = new GsonBuilder().create();
                Phone phone = gson.fromJson(responseBody, Phone.class);

                String specification = phone.getSpecification() != null ? phone.getSpecification().toString() : "null";
                String condition = phone.getCondition() != null ? phone.getCondition().toString() : "null";

                tableModel.setRowCount(0);

                if (phone != null) {
                    tableModel.addRow(new Object[]{
                            phone.getImei(),
                            phone.getBrand(),
                            phone.getModel(),
                            phone.getColor(),
                            phone.getPrice(),
                            phone.getStatus(),
                            condition
                    });
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "No phone found with IMEI: " + imei, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Failed to fetch data from server", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch phone", e);
        }
    }

    private String sendRequest(String url) throws IOException {
        String token = TokenStorage.getInstance().getToken();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
    public JPanel getPhoneInventory(){
        return mainPanel;
    }
}

