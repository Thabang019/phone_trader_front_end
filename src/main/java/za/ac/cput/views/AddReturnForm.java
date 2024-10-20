package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.json.JSONObject;
import za.ac.cput.domain.Returns;
import za.ac.cput.domain.Sale;
import za.ac.cput.factory.ReturnFactory;
import za.ac.cput.dto.TokenStorage;
import za.ac.cput.util.LocalDateTimeTypeAdapter;
import za.ac.cput.util.LocalDateTypeAdapter;
import za.ac.cput.util.LocalTimeTypeAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddReturnForm {
    private JPanel panel;
    private JTextField saleIdField, reasonField;
    private static final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
            .create();

    public AddReturnForm() {

        panel = new JPanel(new GridLayout(3, 2));

        JLabel saleIdLabel = new JLabel("Sale ID:");
        saleIdField = new JTextField();
        panel.add(saleIdLabel);
        panel.add(saleIdField);

        JLabel reasonLabel = new JLabel("Reason for Return:");
        reasonField = new JTextField();
        panel.add(reasonLabel);
        panel.add(reasonField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(192, 0, 0));
        submitButton.setForeground(Color.WHITE);
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addReturn();
            }
        });
    }

    private void addReturn() {
        Long saleId = Long.parseLong(saleIdField.getText());
        String reason = reasonField.getText();

        try {

            Sale sale = getSale(saleId);

            if (sale != null) {

                Returns newReturn = ReturnFactory.createReturn(0, reason, sale, LocalDate.now());


                sendReturnToBackend(newReturn);
            } else {
                JOptionPane.showMessageDialog(panel, "Sale ID not found", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Failed to create return: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Sale getSale(Long saleId) throws IOException {
        String token = TokenStorage.getInstance().getToken();
        String url = "http://localhost:8080/phone-trader/Sale/read/" + saleId;  // Adjust this to match your backend API

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                return gson.fromJson(jsonData, Sale.class);
            } else {
                System.err.println("Failed to retrieve Sale with ID: " + saleId);
                return null;
            }
        }
    }

    private void sendReturnToBackend(Returns newReturn) throws IOException {
        String token = TokenStorage.getInstance().getToken();
        String url = "http://localhost:8080/phone-trader/Return/create";  // Adjust this to match your backend API

        String json = gson.toJson(newReturn);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JOptionPane.showMessageDialog(panel, "Return added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.err.println("Failed to add return");
                JOptionPane.showMessageDialog(panel, "Failed to add return", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public JPanel addForm(){
        return panel;
    }
}
