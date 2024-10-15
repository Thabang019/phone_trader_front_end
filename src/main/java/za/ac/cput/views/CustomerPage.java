package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.domain.Buyer;
import za.ac.cput.domain.Purchase;
import za.ac.cput.domain.Sale;
import za.ac.cput.domain.Seller;
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

public class CustomerPage {

    private JLabel customersLabel, buyersLabel, sellersLabel;
    private JPanel mainPanel, buyersLabelPanel, sellersLabelPanel,customersLabelPanel;
    private DefaultTableModel buyersTableModel, sellersTableModel;
    private JTable buyersTable, sellersTable;
    private JScrollPane buyerScrollPane, sellerScrollPane;
    private static final OkHttpClient client = new OkHttpClient();

    public CustomerPage() {

        mainPanel = new JPanel();

        customersLabel = new JLabel("Customers");
        customersLabelPanel = new JPanel();

        buyersLabelPanel = new JPanel(new BorderLayout());
        buyersLabel = new JLabel("Buyers:", JLabel.LEFT);
        String[] buyerColumns = {"ID Number", "First Name", "Middle Name", "Last Name", "Email", "Sale ID"};
        buyersTableModel = new DefaultTableModel(buyerColumns, 0);
        buyersTable = new JTable(buyersTableModel);
        buyerScrollPane = new JScrollPane(buyersTable);

        sellersLabelPanel = new JPanel(new BorderLayout());
        sellersLabel = new JLabel("Sellers:", JLabel.LEFT);
        String[] sellerColumns = {"ID Number", "First Name", "Middle Name", "Last Name", "Email", "Purchase ID"};
        sellersTableModel = new DefaultTableModel(sellerColumns, 0);
        sellersTable = new JTable(sellersTableModel);
        sellerScrollPane = new JScrollPane(sellersTable);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        customersLabelPanel.setSize(mainPanel.getX(),28);
        customersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        customersLabel.setOpaque(true);
        customersLabel.setForeground(new Color(192,0,0));
        customersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        customersLabelPanel.add(customersLabel);
        mainPanel.add(customersLabelPanel);

        buyersLabelPanel.setSize(mainPanel.getX(),24);
        buyersLabel.setFont(new Font("Arial", Font.BOLD, 20));
        buyersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buyersLabel.setHorizontalAlignment(SwingConstants.LEFT);
        buyersLabelPanel.add(buyersLabel);
        mainPanel.add(buyersLabelPanel);

        buyersTable.setRowHeight(24);
        buyersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        buyersTable.getTableHeader().setBackground(new Color(192, 0, 0));
        buyersTable.getTableHeader().setForeground(Color.WHITE);

        mainPanel.add(buyerScrollPane);

        sellersLabelPanel.setSize(mainPanel.getX(),24);
        sellersLabel.setFont(new Font("Arial", Font.BOLD, 20));
        sellersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sellersLabel.setHorizontalAlignment(SwingConstants.LEFT);
        sellersLabelPanel.add(sellersLabel);
        mainPanel.add(sellersLabelPanel);

        sellersTable.setRowHeight(24);
        sellersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        sellersTable.getTableHeader().setBackground(new Color(192, 0, 0));
        sellersTable.getTableHeader().setForeground(Color.WHITE);

        mainPanel.add(sellerScrollPane);

        loadSellers();
        loadBuyers();
    }

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
            .create();

    private void loadSellers() {
        sellersTableModel.setRowCount(0); // Clear the table

        try {
            final String url = "http://localhost:8080/phone-trader/seller/getAll";
            String responseBody = sendRequest(url);

            if (responseBody.startsWith("[")) {
                JSONArray sellers = new JSONArray(responseBody);
                for (int i = 0; i < sellers.length(); i++) {
                    JSONObject sellerObject = sellers.getJSONObject(i);
                    Seller seller = gson.fromJson(sellerObject.toString(), Seller.class);

                    if (seller.getPurchases() != null && !seller.getPurchases().isEmpty()) {
                        for (Purchase purchaseId : seller.getPurchases()) {
                            sellersTableModel.addRow(new Object[]{
                                    seller.getIdentityNumber(),
                                    seller.getFirstName(),
                                    seller.getMiddleName(),
                                    seller.getLastName(),
                                    seller.getContact().getEmail(),
                                    purchaseId.getPurchaseID().toString()
                            });
                        }
                    } else {
                        sellersTableModel.addRow(new Object[]{
                                seller.getIdentityNumber(),
                                seller.getFirstName(),
                                seller.getMiddleName(),
                                seller.getLastName(),
                                seller.getContact().getEmail(),
                                "null"
                        });
                    }
                }
            } else {
                System.err.println("Expected a JSON array but got: " + responseBody);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch sellers", e);
        }
    }


    private void loadBuyers() {
        buyersTableModel.setRowCount(0);

        try {
            final String url = "http://localhost:8080/phone-trader/buyer/getAll";
            String responseBody = sendRequest(url);

            if (responseBody.startsWith("[")) {
                JSONArray buyers = new JSONArray(responseBody);
                for (int i = 0; i < buyers.length(); i++) {
                    JSONObject buyerObject = buyers.getJSONObject(i);
                    Buyer buyer = gson.fromJson(buyerObject.toString(), Buyer.class);

                    if (buyer.getSales() != null && !buyer.getSales().isEmpty()) {
                        for (Sale saleId : buyer.getSales()) {
                            buyersTableModel.addRow(new Object[]{
                                    buyer.getIdentityNumber(),
                                    buyer.getFirstName(),
                                    buyer.getMiddleName(),
                                    buyer.getLastName(),
                                    buyer.getContact().getEmail(),
                                    saleId.getSalesID().toString()
                            });
                        }
                    } else {
                        buyersTableModel.addRow(new Object[]{
                                buyer.getIdentityNumber(),
                                buyer.getFirstName(),
                                buyer.getMiddleName(),
                                buyer.getLastName(),
                                buyer.getContact().getEmail(),
                                "null"
                        });
                    }
                }
            } else {
                System.err.println("Expected a JSON array but got: " + responseBody);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch buyers", e);
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

    public JPanel getCustomers(){
        return mainPanel;
    }
}