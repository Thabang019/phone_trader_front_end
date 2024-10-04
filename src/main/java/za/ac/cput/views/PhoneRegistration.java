package za.ac.cput.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import za.ac.cput.domain.*;
import za.ac.cput.dto.TokenStorage;
import za.ac.cput.factory.PhoneFactory;
import za.ac.cput.factory.SpecificationFactory;
import za.ac.cput.util.LocalDateTimeTypeAdapter;
import za.ac.cput.util.LocalDateTypeAdapter;
import za.ac.cput.util.LocalTimeTypeAdapter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PhoneRegistration {

    private JFrame frame;
    private JPanel registrationPanel;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
            .create();

    private JTextField imeiField, modelField, priceField, screenSizeField, cameraField;
    private JComboBox<String> brandDropdown, colorDropdown, conditionDropdown, storageDropdown,
            ramDropdown, osDropdown, simDropdown, microsdDropdown, fingerprintDropdown,
            waterResistanceDropdown, wirelessChargingDropdown;

    public PhoneRegistration() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Phone Registration");
        registrationPanel = new JPanel(new BorderLayout());

        setupContentPanel();
        setupButtonPanel();

        frame.add(registrationPanel);
        frame.setPreferredSize(new Dimension(1360, 800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupContentPanel() {
        JPanel contentPanel = new JPanel(new GridLayout(1, 1, 20, 20));
        JPanel phoneDetailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        phoneDetailsPanel.setBorder(BorderFactory.createTitledBorder("Phone Details"));

        addPhoneDetails(phoneDetailsPanel);

        contentPanel.add(phoneDetailsPanel);
        registrationPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void setupButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add Phone");
        addButton.setBackground(Color.decode("#ADD8E6"));
        addButton.setForeground(Color.BLACK);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPhoneToDb();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelButtonPressed();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        registrationPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addPhoneDetails(JPanel phoneDetailsPanel) {
        String[] labels = {
                "IMEI Number:", "Brand:", "Model:", "Color:", "Price:", "Condition:", "Screen size:",
                "Storage:", "RAM:", "Operating System:", "Camera:", "Number of sims:", "MicroSD:",
                "Fingerprint:", "Water resistance:", "Wireless charging:"
        };

        imeiField = new JTextField();
        brandDropdown = new JComboBox<>(new String[]{"Apple", "Samsung", "Huawei", "Nokia", "Xiaomi", "Honor", "Oppo", "Vivo", "Hisense", "Tecno"});
        modelField = new JTextField();
        colorDropdown = new JComboBox<>(new String[]{"Black", "White", "Silver", "Gold"});
        priceField = new JTextField();
        conditionDropdown = new JComboBox<>(new String[]{"NEW", "USED", "REFURBISHED"});
        screenSizeField = new JTextField();
        storageDropdown = new JComboBox<>(new String[]{"32GB", "64GB", "128GB", "256GB"});
        ramDropdown = new JComboBox<>(new String[]{"2GB", "4GB", "6GB", "8GB", "12GB"});
        osDropdown = new JComboBox<>(new String[]{"Android", "iOS"});
        cameraField = new JTextField();
        simDropdown = new JComboBox<>(new String[]{"1", "2"});
        microsdDropdown = new JComboBox<>(new String[]{"Supported", "Not Supported"});
        fingerprintDropdown = new JComboBox<>(new String[]{"Yes", "No"});
        waterResistanceDropdown = new JComboBox<>(new String[]{"Yes", "No"});
        wirelessChargingDropdown = new JComboBox<>(new String[]{"Yes", "No"});

        JComponent[] fields = {
                imeiField, brandDropdown, modelField, colorDropdown, priceField, conditionDropdown,
                screenSizeField, storageDropdown, ramDropdown, osDropdown, cameraField, simDropdown,
                microsdDropdown, fingerprintDropdown, waterResistanceDropdown, wirelessChargingDropdown
        };

        for (int i = 0; i < labels.length; i++) {
            phoneDetailsPanel.add(new JLabel(labels[i]));
            phoneDetailsPanel.add(fields[i]);
        }
    }

    private void addPhoneToDb() {
        if (validateForm()) {
            try {
                double screenSize = Double.parseDouble(screenSizeField.getText());
                Spec spec = SpecificationFactory.createSpecification(screenSize, storageDropdown.getSelectedItem().toString(),
                        ramDropdown.getSelectedItem().toString(), osDropdown.getSelectedItem().toString(),
                        cameraField.getText(), Integer.parseInt(simDropdown.getSelectedItem().toString()),
                        microsdDropdown.getSelectedItem().toString(), fingerprintDropdown.getSelectedItem().toString(),
                        waterResistanceDropdown.getSelectedItem().toString(), wirelessChargingDropdown.getSelectedItem().toString());
                double price = Double.parseDouble(priceField.getText());
                Phone.Condition condition = Phone.Condition.valueOf(conditionDropdown.getSelectedItem().toString());

                Phone phone = PhoneFactory.createPhone(imeiField.getText(), brandDropdown.getSelectedItem().toString(),
                        modelField.getText(), colorDropdown.getSelectedItem().toString(), price, "available", spec, condition);

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                        .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                        .create();
                String json = gson.toJson(phone);

                final String url = "http://localhost:8080/phone-trader/phones";
                String response = postRequest(url, json);

                if (response != null && !response.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Phone added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to add phone", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }   catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private String postRequest(String url, String json) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception ex) {
            System.out.println("Failed to send POST request");
        }
        return null;
    }
    private void cancelButtonPressed() {
        ManagerDashboard inventory = new ManagerDashboard();
        inventory.ManagerDashboard();
        frame.dispose();
    }

    private boolean validateForm() {
        boolean isValid = true;

        Component[] components = ((Container) registrationPanel.getComponent(0)).getComponents();
        if (!isValid((JPanel) components[0], true)) {
            JOptionPane.showMessageDialog(frame, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }

        return isValid;
    }

    private boolean isValid(JPanel panel, boolean isValid) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JTextField textField) {
                if (textField.getText().isEmpty()) {
                    textField.setBorder(BorderFactory.createLineBorder(Color.RED));
                    isValid = false;
                } else {
                    textField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                }
            }
        }
        return isValid;
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

    public void showRegistration() {
        frame.setVisible(true);
    }
}
