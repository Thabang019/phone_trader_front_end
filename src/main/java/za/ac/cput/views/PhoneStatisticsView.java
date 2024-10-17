package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.domain.Sale;
import za.ac.cput.domain.Purchase;
import za.ac.cput.dto.TokenStorage;
import za.ac.cput.util.LocalDateTimeTypeAdapter;
import za.ac.cput.util.LocalDateTypeAdapter;
import za.ac.cput.util.LocalTimeTypeAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PhoneStatisticsView {
    private JPanel mainPanel;
    private static DefaultTableModel salesModel;
    private static DefaultTableModel purchasesModel;
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
            .create();

    public PhoneStatisticsView() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(247, 247, 247));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(247, 247, 247));
        JLabel title = new JLabel("PHONE SALES & PURCHASES STATISTICS", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(192, 0, 0));
        topPanel.add(title, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(247, 247, 247));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        JPanel salesTablePanel = new JPanel(new BorderLayout());
        salesTablePanel.setBackground(new Color(247, 247, 247));
        JLabel salesLabel = new JLabel("Phone Sales Statistics");
        salesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        String[] salesColumns = {"Sale_ID", "Phone Model", "Sale Price", "Date"};
        salesModel = new DefaultTableModel(salesColumns, 0);
        JTable salesTable = new JTable(salesModel);
        salesTable.setRowHeight(24);
        salesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        salesTable.getTableHeader().setBackground(new Color(192, 0, 0));
        salesTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane salesScrollPane = new JScrollPane(salesTable);
        salesTablePanel.add(salesLabel, BorderLayout.NORTH);
        salesTablePanel.add(salesScrollPane, BorderLayout.CENTER);
        salesTablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));


        JPanel purchasesTablePanel = new JPanel(new BorderLayout());
        purchasesTablePanel.setBackground(new Color(247, 247, 247));
        JLabel purchasesLabel = new JLabel("Phone Purchase Statistics");
        purchasesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        String[] purchasesColumns = {"Purchase_ID", "Phone Model", "Purchase Price", "Date"};
        purchasesModel = new DefaultTableModel(purchasesColumns, 0);
        JTable purchasesTable = new JTable(purchasesModel);
        purchasesTable.setRowHeight(24);
        purchasesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        purchasesTable.getTableHeader().setBackground(new Color(192, 0, 0));
        purchasesTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane purchasesScrollPane = new JScrollPane(purchasesTable);
        purchasesTablePanel.add(purchasesLabel, BorderLayout.NORTH);
        purchasesTablePanel.add(purchasesScrollPane, BorderLayout.CENTER);
        purchasesTablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));


        centerPanel.add(salesTablePanel);
        centerPanel.add(purchasesTablePanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        getSalesStatistics();
        getPurchasesStatistics();
    }


    private static void getSalesStatistics() {
        try {
            final String url = "http://localhost:8080/phone-trader/Sale/getAll";
            String responseBody = read(url);

            if (responseBody.startsWith("[")) {
                JSONArray sales = new JSONArray(responseBody);

                for (int i = 0; i < sales.length(); i++) {
                    JSONObject saleJSONObject = sales.getJSONObject(i);
                    Sale sale = gson.fromJson(saleJSONObject.toString(), Sale.class);

                    salesModel.addRow(new Object[]{
                            sale.getSalesID(),
                            sale.getPhone().getModel(),
                            sale.getTime(),
                            sale.getDate()
                    });
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch phone sales", e);
        }
    }


    private static void getPurchasesStatistics() {
        try {
            final String url = "http://localhost:8080/phone-trader/purchase/getall";
            String responseBody = read(url);

            if (responseBody.startsWith("[")) {
                JSONArray purchases = new JSONArray(responseBody);

                for (int i = 0; i < purchases.length(); i++) {
                    JSONObject purchaseJSONObject = purchases.getJSONObject(i);
                    Purchase purchase = gson.fromJson(purchaseJSONObject.toString(), Purchase.class);

                    purchasesModel.addRow(new Object[]{
                            purchase.getPurchaseID(),
                            purchase.getPhone().getModel(),
                            purchase.getBuyingPrice(),
                            purchase.getDate()
                    });
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch phone purchases", e);
        }
    }

    private static String read(String url) throws IOException {
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
    public JPanel getStatistics(){
        return mainPanel;
    }
}
