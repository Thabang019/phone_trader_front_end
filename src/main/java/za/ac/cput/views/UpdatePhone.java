package za.ac.cput.views;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.domain.Phone;
import za.ac.cput.dto.TokenStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UpdatePhone {
    private JPanel panel;
    private JTextField imeiField, brandField, modelField, colorField, priceField, statusField;
    private JComboBox<String> conditionField;
    private static final OkHttpClient client = new OkHttpClient();

    public JPanel updateCondition(String imei) {

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);


        int fieldWidth = 200;

        // Labels and text fields for phone details
        JLabel imeiLabel = new JLabel("IMEI:");
        imeiField = new JTextField();
        imeiField.setEditable(false);
        imeiField.setPreferredSize(new Dimension(fieldWidth, 30));

        JLabel brandLabel = new JLabel("Brand:");
        brandField = new JTextField();
        brandField.setEditable(false);
        brandField.setPreferredSize(new Dimension(fieldWidth, 30));

        JLabel modelLabel = new JLabel("Model:");
        modelField = new JTextField();
        modelField.setEditable(false);
        modelField.setPreferredSize(new Dimension(fieldWidth, 30));

        JLabel colorLabel = new JLabel("Color:");
        colorField = new JTextField();
        colorField.setEditable(false);
        colorField.setPreferredSize(new Dimension(fieldWidth, 30));

        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(fieldWidth, 30));

        JLabel statusLabel = new JLabel("Status:");
        statusField = new JTextField();
        statusField.setPreferredSize(new Dimension(fieldWidth, 30));

        // Dropdown for condition update
        JLabel conditionLabel = new JLabel("Condition:");
        conditionField = new JComboBox<>(new String[]{"New", "Used", "Refurbished"});
        conditionField.setPreferredSize(new Dimension(fieldWidth, 30));

        JButton updateButton = new JButton("Update Phone");
        updateButton.setBackground(new Color(192, 0, 0));
        updateButton.setForeground(Color.WHITE); // White text
        JButton backButton = new JButton("Back");

        // Adding components to the panel
        gbc.gridx = 0; gbc.gridy = 0; panel.add(imeiLabel, gbc);
        gbc.gridx = 1; panel.add(imeiField, gbc);

        gbc.gridx = 0; gbc.gridy++; panel.add(brandLabel, gbc);
        gbc.gridx = 1; panel.add(brandField, gbc);

        gbc.gridx = 0; gbc.gridy++; panel.add(modelLabel, gbc);
        gbc.gridx = 1; panel.add(modelField, gbc);

        gbc.gridx = 0; gbc.gridy++; panel.add(colorLabel, gbc);
        gbc.gridx = 1; panel.add(colorField, gbc);

        gbc.gridx = 0; gbc.gridy++; panel.add(priceLabel, gbc);
        gbc.gridx = 1; panel.add(priceField, gbc);

        gbc.gridx = 0; gbc.gridy++; panel.add(statusLabel, gbc);
        gbc.gridx = 1; panel.add(statusField, gbc);

        gbc.gridx = 0; gbc.gridy++; panel.add(conditionLabel, gbc);
        gbc.gridx = 1; panel.add(conditionField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(updateButton, gbc);

        gbc.gridy++;
        panel.add(backButton, gbc);

        loadPhoneDetails(imei);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePhoneDetails(imei);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPhoneInventory();
            }
        });

        return panel;
    }

    private void loadPhoneDetails(String imei) {
        try {
            final String url = "http://localhost:8080/phone-trader/phones/read/" + imei;
            String responseBody = sendRequest(url);

            Gson gson = new Gson();
            Phone phone = gson.fromJson(responseBody, Phone.class);

            imeiField.setText(phone.getImei());
            brandField.setText(phone.getBrand());
            modelField.setText(phone.getModel());
            colorField.setText(phone.getColor());
            priceField.setText(String.valueOf(phone.getPrice()));
            statusField.setText(phone.getStatus());

            if (phone.getCondition() != null) {
                conditionField.setSelectedItem(phone.getCondition().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updatePhoneDetails(String imei) {
        String selectedCondition = conditionField.getSelectedItem().toString();
        String newPrice = priceField.getText();
        String newStatus = statusField.getText();

        try {
            final String url = "http://localhost:8080/phone-trader/phones/updateDetails/" + imei; // Updated endpoint
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            String jsonRequestBody = String.format("{\"condition\":\"%s\", \"price\":%s, \"status\":\"%s\"}", selectedCondition, newPrice, newStatus);
            RequestBody body = RequestBody.create(JSON, jsonRequestBody);

            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .header("Authorization", "Bearer " + TokenStorage.getInstance().getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(panel, "Phone details updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(panel, "Failed to update phone details: " + response.message());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openPhoneInventory() {
        PhoneInventory inventory = new PhoneInventory();
        inventory.getPhoneInventory();
    }

    private String sendRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + TokenStorage.getInstance().getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
}
