package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.domain.Buyer;
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

public class CustomerPage extends JFrame {

    private JLabel customersLabel, buyersLabel, sellersLabel;
    private JPanel mainPanel;
    private DefaultTableModel buyersTableModel, sellersTableModel;
    private JTable buyersTable, sellersTable;
    private JScrollPane buyerScrollPane, sellerScrollPane;
    private static final OkHttpClient client = new OkHttpClient();

    public CustomerPage() {

        mainPanel = new JPanel();
        customersLabel = new JLabel("Customers");

        buyersLabel = new JLabel("Buyers:", JLabel.LEFT);
        String[] buyerColumns = {"ID Number", "First Name", "Middle Name", "Last Name", "Email", "Sale ID"};
        buyersTableModel = new DefaultTableModel(buyerColumns, 0);
        buyersTable = new JTable(buyersTableModel);
        buyerScrollPane = new JScrollPane(buyersTable);

        sellersLabel = new JLabel("Sellers:", JLabel.LEFT);
        String[] sellerColumns = {"ID Number", "First Name", "Middle Name", "Last Name", "Email", "Purchase ID"};
        sellersTableModel = new DefaultTableModel(sellerColumns, 0);
        sellersTable = new JTable(sellersTableModel);
        sellerScrollPane = new JScrollPane(sellersTable);
    }

    public void setCustomerPage(){

        setTitle("Customer Page View");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        customersLabel.setFont(new Font("Arial", Font.BOLD, 36));
        customersLabel.setOpaque(true);
        customersLabel.setForeground(new Color(0, 102, 204));
        customersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        customersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        customersLabel.setPreferredSize(new Dimension(100, 50));
        mainPanel.add(customersLabel);

        buyersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        buyersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(buyersLabel);

        buyersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        buyersTable.getTableHeader().setBackground(Color.BLUE);
        buyersTable.getTableHeader().setForeground(Color.WHITE);

        mainPanel.add(buyerScrollPane);

        sellersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        sellersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(sellersLabel);

        sellersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        sellersTable.getTableHeader().setBackground(Color.BLUE);
        sellersTable.getTableHeader().setForeground(Color.WHITE);

        mainPanel.add(sellerScrollPane);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);

        loadSellers();
        loadBuyers();
    }

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
            .create();

    private void loadSellers() {

        sellersTableModel.setRowCount(0);

        try {
            final String url = "http://localhost:8080/phone-trader/seller/getAll";
            String responseBody = sendRequest(url);

            if (responseBody.startsWith("[")) {
                JSONArray sellers = new JSONArray(responseBody);
                for (int i = 0; i < sellers.length(); i++) {
                    JSONObject sellerObject = sellers.getJSONObject(i);
                    Seller seller = gson.fromJson(sellerObject.toString(), Seller.class);

                    //"ID Number", "First Name", "Middle Name", "Last Name", "Email", "Purchase ID"
                    String email = seller.getContact() != null ? seller.getContact().toString() : "null";
                    String purchase = seller.getPurchases() != null ? seller.getPurchases().toString() : "null";

                    sellersTableModel.addRow(new Object[]{
                            seller.getIdentityNumber(),
                            seller.getFirstName(),
                            seller.getMiddleName(),
                            seller.getLastName(),
                            email,
                            purchase
                    });
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

                    //"ID Number", "First Name", "Middle Name", "Last Name", "Email", "Sale ID"
                    String email = buyer.getContact() != null ? buyer.getContact().toString() : "null";
                    String sale = buyer.getSales() != null ? buyer.getSales().toString() : "null";

                    buyersTableModel.addRow(new Object[]{
                            buyer.getIdentityNumber(),
                            buyer.getFirstName(),
                            buyer.getMiddleName(),
                            buyer.getLastName(),
                            email,
                            sale
                    });
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
}
