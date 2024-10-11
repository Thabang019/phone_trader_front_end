package za.ac.cput.views;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONObject;
import za.ac.cput.domain.Returns;
import za.ac.cput.domain.Sale;
import za.ac.cput.factory.ReturnFactory;
import za.ac.cput.dto.TokenStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

public class AddReturnForm {
    private JFrame addReturnFrame;
    private JPanel panel;
    private JTextField saleIdField, reasonField;

    private static final OkHttpClient client = new OkHttpClient();

    public void showAddReturn() {
        addReturnFrame = new JFrame("Add New Return");
        addReturnFrame.setSize(400, 300);
        addReturnFrame.setLayout(new BorderLayout());

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

        addReturnFrame.add(panel, BorderLayout.CENTER);
        addReturnFrame.setVisible(true);

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
                JOptionPane.showMessageDialog(addReturnFrame, "Sale ID not found", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(addReturnFrame, "Failed to create return: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Sale getSale(Long saleId) throws IOException {
        String token = TokenStorage.getInstance().getToken();
        String url = "http://localhost:8080/phone-trader/sale/read/" + saleId;  // Adjust this to match your backend API

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(jsonData, Sale.class);
            } else {
                System.err.println("Failed to retrieve Sale with ID: " + saleId);
                return null;
            }
        }
    }

    private void sendReturnToBackend(Returns newReturn) throws IOException {
        String token = TokenStorage.getInstance().getToken();
        String url = "http://localhost:8080/phone-trader/returns/save";  // Adjust this to match your backend API

        Gson gson = new Gson();
        String json = gson.toJson(newReturn);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JOptionPane.showMessageDialog(addReturnFrame, "Return added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.err.println("Failed to add return");
                JOptionPane.showMessageDialog(addReturnFrame, "Failed to add return", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
