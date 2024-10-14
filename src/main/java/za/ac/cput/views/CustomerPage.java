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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CustomerPage extends JFrame {

    private final JLabel customersLabel, buyersLabel, sellersLabel;
    private JTextField sellersSearchField, buyersSearchField;
    private JButton backBTN, sellerSearchBTN, buyerSearchBTN;

    private JPanel customerLabelPanel, buyersLabelPanel, sellersLabelPanel,
            sellerSearchPanel,buyerSearchButtonPanel, buyerSearchPanel,backBTNPanel,
            sellerSearchButtonPanel, buyerScrollPanePanel, sellerScrollPanePanel;

    private DefaultTableModel buyersTableModel, sellersTableModel;
    private JTable buyersTable, sellersTable;
    private JScrollPane buyerScrollPane, sellerScrollPane;
    private static final OkHttpClient client = new OkHttpClient();

    public CustomerPage() {

        customerLabelPanel = new JPanel();
        backBTNPanel = new JPanel();

        buyersLabelPanel = new JPanel();
        buyerSearchButtonPanel = new JPanel();
        buyerSearchPanel = new JPanel();
        buyerScrollPanePanel = new JPanel(new BorderLayout());
        buyersSearchField = new JTextField("buyer ID here..");

        sellersLabelPanel = new JPanel();
        sellerSearchPanel = new JPanel();
        sellerSearchButtonPanel = new JPanel();
        sellerScrollPanePanel = new JPanel(new BorderLayout());
        sellersSearchField = new JTextField("seller ID here..");

        sellerSearchBTN = new JButton("Search");
        buyerSearchBTN = new JButton("Search");
        backBTN = new JButton("Back");
        customersLabel = new JLabel("Customers");

        buyersLabel = new JLabel("Buyers:", JLabel.LEFT);
        String[] buyerColumns = {"ID Number", "First Name", "Middle Name", "Last Name", "Email", "Sale ID"};
        buyersTableModel = new DefaultTableModel(buyerColumns, 0);
        buyersTable = new JTable(buyersTableModel);
        buyerScrollPane = new JScrollPane(buyersTable);
        buyersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        buyersTable.getTableHeader().setBackground(new Color(192,0,0));
        buyersTable.getTableHeader().setForeground(Color.WHITE);

        sellersLabel = new JLabel("Sellers:", JLabel.LEFT);
        String[] sellerColumns = {"ID Number", "First Name", "Middle Name", "Last Name", "Email", "Purchase ID"};
        sellersTableModel = new DefaultTableModel(sellerColumns, 0);
        sellersTable = new JTable(sellersTableModel);
        sellerScrollPane = new JScrollPane(sellersTable);
        sellersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        sellersTable.getTableHeader().setBackground(new Color(192,0,0));
        sellersTable.getTableHeader().setForeground(Color.WHITE);
    }

    public void setCustomerPage(){

        setTitle("Customer Information");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setBackground(Color.RED);


        customersLabel.setFont(new Font("Arial", Font.BOLD, 36));
        customersLabel.setOpaque(true);
        customersLabel.setForeground(new Color(0, 102, 204));
        customersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        customersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        customersLabel.setPreferredSize(new Dimension(400, 50));

        customerLabelPanel.setBounds(3, 3, 1360, 60);
        customerLabelPanel.add(customersLabel);


        buyerSearchBTN.setBackground(new Color(192,0,0));
        buyerSearchBTN.setForeground(Color.WHITE);
        buyerSearchBTN.setFocusable(false);
        buyerSearchBTN.setFont(new Font("Arial", Font.BOLD, 14));

        buyerSearchBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTable(buyersTable, buyersSearchField.getText(), "Buyer");
            }
        });


        buyersSearchField.setFont(new Font("Arial", Font.ITALIC, 20));
        buyersSearchField.setColumns(15);
        buyersSearchField.setForeground(Color.GRAY);
        buyersSearchField.setText("buyer ID here..");
        buyersSearchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (buyersSearchField.getText().equals("buyer ID here..")) {
                    buyersSearchField.setText("");
                    buyersSearchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (buyersSearchField.getText().isEmpty()) {
                    buyersSearchField.setForeground(Color.GRAY);
                    buyersSearchField.setText("buyer ID here..");
                }
            }
        });

        buyerSearchButtonPanel.setBounds(415,120,100,40);
        buyerSearchPanel.setBounds(105,120,300,40);
        buyerSearchButtonPanel.add(buyerSearchBTN);
        buyerSearchPanel.add(buyersSearchField);

        buyersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        buyersLabel.setForeground(new Color(0,102,204));
        buyersLabelPanel.setBounds(20,180,250,50);
        buyersLabelPanel.add(buyersLabel);

        buyerScrollPanePanel.setBounds(10,235,650,370);
        buyerScrollPane.setSize(645,360);
        buyerScrollPanePanel.add(buyerScrollPane);


        sellerSearchBTN.setBackground(new Color(192,0,0));
        sellerSearchBTN.setForeground(Color.WHITE);
        sellerSearchBTN.setFocusable(false);
        sellerSearchBTN.setFont(new Font("Arial", Font.BOLD, 14));

        sellerSearchBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTable(sellersTable, sellersSearchField.getText(), "Seller");
            }
        });

        sellersSearchField.setFont(new Font("Arial", Font.ITALIC, 20));
        sellersSearchField.setColumns(15);
        sellersSearchField.setForeground(Color.GRAY);
        sellersSearchField.setText("seller ID here..");
        sellersSearchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (sellersSearchField.getText().equals("seller ID here..")) {
                    sellersSearchField.setText("");
                    sellersSearchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (sellersSearchField.getText().isEmpty()) {
                    sellersSearchField.setForeground(Color.GRAY);
                    sellersSearchField.setText("seller ID here..");
                }
            }
        });

        sellerSearchButtonPanel.setBounds(1145,120,100,40);
        sellerSearchPanel.setBounds(835,120,300,40);
        sellerSearchButtonPanel.add(sellerSearchBTN);
        sellerSearchPanel.add(sellersSearchField);

        sellersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        sellersLabel.setForeground(new Color(0, 102, 204));
        sellersLabelPanel.setBounds(700,180,250,50);
        sellersLabelPanel.add(sellersLabel);

        sellerScrollPanePanel.setBounds(700,235,650,370);
        sellerScrollPane.setSize(645,360);
        sellerScrollPanePanel.add(sellerScrollPane);

        backBTN.setBackground(new Color(192,0,0));
        backBTN.setForeground(Color.WHITE);
        backBTN.setFocusable(false);
        backBTN.setFont(new Font("Arial", Font.BOLD, 14));
        backBTNPanel.setBounds(625,625,100,40);
        backBTNPanel.add(backBTN);
        backBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManagerDashboard();
                dispose();
            }});

        add(customerLabelPanel);

        add(buyerSearchPanel);
        add(buyerSearchButtonPanel);
        add(buyersLabelPanel);
        add(buyerScrollPanePanel);

        add(sellerSearchPanel);
        add(sellerSearchButtonPanel);
        add(sellersLabelPanel);
        add(sellerScrollPanePanel);

        add(backBTNPanel);

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

    private void openManagerDashboard() {
        ManagerDashboard dashboard = new ManagerDashboard();
        dashboard.ManagerDashboard();

    }

    private void searchTable(JTable table, String searchTerm, String type) {
        boolean found = false;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.clearSelection();

        for (int i = 0; i < model.getRowCount(); i++) {
            String idNumber = model.getValueAt(i, 0).toString();
            if (idNumber.equals(searchTerm)) {
                table.addRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, type + " with ID " + searchTerm + " not found.", "Contents Not Available", JOptionPane.ERROR_MESSAGE);
        }
    }
}
