package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.domain.Returns;
import za.ac.cput.dto.TokenStorage;
import za.ac.cput.util.LocalDateTimeTypeAdapter;
import za.ac.cput.util.LocalDateTypeAdapter;
import za.ac.cput.util.LocalTimeTypeAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReturnInventory {
    private JPanel mainPanel;
    private static DefaultTableModel tableModel;
    private static final OkHttpClient client = new OkHttpClient();

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
            .create();
    public ReturnInventory() {

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(247, 247, 247));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(247, 247, 247));
        JLabel titleLabel = new JLabel("Returns Inventory", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(247, 247, 247));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String[] columnNames = {"Return ID", "Reason", "Associated Sale ID", "Return Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(192, 0, 0));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(247, 247, 247));
        JButton backButton = new JButton("BACK");
        JButton addReturnButton = new JButton("ADD RETURN");

        backButton.setBackground(new Color(192, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        addReturnButton.setBackground(new Color(192, 0, 0));
        addReturnButton.setForeground(Color.WHITE);
        addReturnButton.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(addReturnButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadAllReturns();

        backButton.addActionListener(e -> {
            openManagerDashboard();
        });

        addReturnButton.addActionListener(e -> {
            openAddReturn();
        });

    }

    private void openManagerDashboard() {
        ManagerDashboard dashboard = new ManagerDashboard();
        dashboard.ManagerDashboard();
    }

    private void openAddReturn() {
        AddReturnForm addReturnForm = new AddReturnForm();
        addReturnForm.addForm();
    }

    private void loadAllReturns() {
        tableModel.setRowCount(0);

        try {
            final String url = "http://localhost:8080/phone-trader/Return/getAll";
            String responseBody = sendRequest(url);

            if (responseBody.startsWith("[")) {
                JSONArray returns = new JSONArray(responseBody);

                for (int i = 0; i < returns.length(); i++) {
                    JSONObject returnObject = returns.getJSONObject(i);
                    Returns returnItem = gson.fromJson(returnObject.toString(), Returns.class);

                    Long saleID = returnItem.getSales().getSalesID();
                    tableModel.addRow(new Object[]{
                            returnItem.getReturnID(),
                            returnItem.getReasonForReturn(),
                            saleID,
                            returnItem.getReturnDate()
                    });
                }
            } else {
                System.err.println("Expected a JSON array but got: " + responseBody);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch returns", e);
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
    public JPanel getReturn(){
        return mainPanel;
    }

}