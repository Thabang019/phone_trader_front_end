package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.domain.Phone;
import za.ac.cput.domain.Spec;
import za.ac.cput.dto.TokenStorage;
import za.ac.cput.util.ImeiStorage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SalesPersonDashboard extends JFrame {
    private JTextField searchField;
    private JTable phoneTable;
    private DefaultTableModel tableModel;
    private static final OkHttpClient client = new OkHttpClient();

    public SalesPersonDashboard() {
        // Set up the frame
        setTitle("Phone Trader");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create the main container
        JPanel mainContainer = new JPanel(new BorderLayout(20, 20));
        mainContainer.setBackground(new Color(247, 247, 247));
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Search bar panel
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchBarPanel.setBackground(new Color(247, 247, 247));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(192, 0, 0));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        searchButton.setFont(new Font("Arial", Font.BOLD, 15));
        searchButton.setPreferredSize(new Dimension(120, 35));
        searchBarPanel.add(new JLabel("Find Phone"));
        searchBarPanel.add(searchField);
        searchBarPanel.add(searchButton);


        // Filter buttons panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(247, 247, 247));
        JPanel centerButton = new JPanel();
        centerButton.setLayout(new BoxLayout(centerButton, BoxLayout.Y_AXIS));
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

        JButton collectionButton = new JButton("Collection");
        collectionButton.setBackground(new Color(192, 0, 0));
        collectionButton.setForeground(Color.WHITE);
        collectionButton.setBorderPainted(false);
        collectionButton.setFocusPainted(false);
        collectionButton.setFont(new Font("Arial", Font.BOLD, 15));
        collectionButton.setPreferredSize(new Dimension(120, 35));

        centerButton.add(Box.createRigidArea(new Dimension(0, 30)));
        centerButton.add(iphoneButton);
        centerButton.add(Box.createRigidArea(new Dimension(0, 15)));
        centerButton.add(androidButton);
        centerButton.add(Box.createRigidArea(new Dimension(0, 15)));
        centerButton.add(collectionButton);
        centerButton.add(Box.createRigidArea(new Dimension(0, 15)));

        mainContainer.add(searchBarPanel, BorderLayout.NORTH);
        buttonPanel.add(centerButton, BorderLayout.CENTER);
        mainContainer.add(buttonPanel, BorderLayout.WEST);

        // Iphone button actionListener
        iphoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findByBrand("iPhone");
            }
            private void findByBrand(String brand) {
                try {
                    final String url = "http://localhost:8080/phone-trader/phones/getall"; // Or use an appropriate endpoint
                    String responseBody = read(url);

                    if (responseBody.startsWith("[")) {
                        JSONArray phonesArray = new JSONArray(responseBody);
                        tableModel.setRowCount(0); // Clear existing table content
                        Gson g = new GsonBuilder().create();

                        for (int i = 0; i < phonesArray.length(); i++) {
                            JSONObject phoneJSONObject = phonesArray.getJSONObject(i);
                            Phone phone = g.fromJson(phoneJSONObject.toString(), Phone.class);

                            // Filter by brand (or operating system)
                            if ("iPhone".equalsIgnoreCase(phone.getBrand()) ||
                                    "ios".equalsIgnoreCase(phone.getSpecification().getOperatingSystem())) {
                                tableModel.addRow(new Object[]{
                                        phone.getImei(),  // Button text
                                        phone.getBrand(),
                                        phone.getModel(),
                                        phone.getPrice()
                                });
                            }
                        }
                    } else {
                        System.err.println("Expected a JSON array but got: " + responseBody);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("Failed to fetch iPhones", ex);
                }
            }
        });

        // Android button actionListener
        androidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findByOS("Android");
            }
            private void findByOS(String os) {
                try {
                    final String url = "http://localhost:8080/phone-trader/phones/getall"; // Or use an appropriate endpoint
                    String responseBody = read(url);

                    if (responseBody.startsWith("[")) {
                        JSONArray phonesArray = new JSONArray(responseBody);
                        tableModel.setRowCount(0); // Clear existing table content
                        Gson g = new GsonBuilder().create();

                        for (int i = 0; i < phonesArray.length(); i++) {
                            JSONObject phoneJSONObject = phonesArray.getJSONObject(i);
                            Phone phone = g.fromJson(phoneJSONObject.toString(), Phone.class);


                            // Filter by operating system
                            if ("Android".equalsIgnoreCase(phone.getSpecification().getOperatingSystem())) {
                                tableModel.addRow(new Object[]{
                                        phone.getImei(),  // Button text
                                        phone.getBrand(),
                                        phone.getModel(),
                                        phone.getPrice()
                                });
                            }
                        }
                    } else {
                        System.err.println("Expected a JSON array but got: " + responseBody);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("Failed to fetch Android phones", ex);
                }
            }
        });

        // Search Button Action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText().trim();

                // Check if searchQuery is not empty and is a valid number (assuming ID is numeric)
                if (!searchQuery.isEmpty()) {
                    findPhoneById(searchQuery);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid phone ID.");
                }
            }

            private void findPhoneById(String id) {
                try {
                    final String url = "http://localhost:8080/phone-trader/phones/read/" + id; // Assuming an endpoint for fetching phone by ID
                    String responseBody = read(url);

                    // Handle response (assuming response contains a single phone object)
                    if (!responseBody.startsWith("[")) {
                        JSONObject phoneJSONObject = new JSONObject(responseBody);
                        Gson g = new GsonBuilder().create();
                        Phone phone = g.fromJson(phoneJSONObject.toString(), Phone.class);

                            tableModel.setRowCount(0);
                            tableModel.addRow(new Object[]{
                                    phone.getImei(),  // Button text
                                    phone.getBrand(),
                                    phone.getModel(),
                                    phone.getPrice()
                            });

                    } else {
                        JOptionPane.showMessageDialog(null, "No phone found with the entered ID.");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("Failed to fetch phone by ID", ex);
                }
            }
        });

        // Collection button actionListener
        collectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAllPhones(tableModel);
            }
        });

        // Create table with buttons in the "NO" column
        String[] columnNames = {"NO", "BRAND", "MODEL", "PRICE"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Only the "NO" column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return JButton.class; // The "NO" column contains buttons
                }
                return super.getColumnClass(columnIndex);
            }
        };

        phoneTable = new JTable(tableModel);
        phoneTable.getColumn("NO").setCellRenderer(new ButtonRenderer());
        phoneTable.getColumn("NO").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(phoneTable);
        mainContainer.add(scrollPane, BorderLayout.CENTER);
        add(mainContainer);

        // Fetch and display phones
        getAllPhones(tableModel);
    }

    public static void getAllPhones(DefaultTableModel model) {
        try {
            final String url = "http://localhost:8080/phone-trader/phones/getall";
            String responseBody = read(url);

            if (responseBody.startsWith("[")) {
                JSONArray phonesArray = new JSONArray(responseBody);
                model.setRowCount(0);
                Gson g = new GsonBuilder().create();

                for (int i = 0; i < phonesArray.length(); i++) {
                    JSONObject phoneJSONObject = phonesArray.getJSONObject(i);
                    Phone phone = g.fromJson(phoneJSONObject.toString(), Phone.class);
                    model.addRow(new Object[]{
                            phone.getImei(),  // Button text
                            phone.getBrand(),
                            phone.getModel(),
                            phone.getPrice()
                    });
                }
            } else {
                System.err.println("Expected a JSON array but got: " + responseBody);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch phones", e);
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

    // ButtonRenderer for the "NO" column
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            return this;
        }
    }

    // ButtonEditor for the "NO" column
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String imei;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            imei = (value != null) ? value.toString() : "";
            button.setText(value != null ? value.toString() : "");
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Navigate to SalesPersonSellPage when the button is clicked
                try {
                    ImeiStorage.getInstance().setImei(imei);
                    SalesPersonSellPage sellPage = new SalesPersonSellPage();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            isPushed = false;
            return imei;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

}
