package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.domain.Phone;
import za.ac.cput.dto.TokenStorage;
import za.ac.cput.util.ImeiStorage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SalesPersonDashboard extends JFrame {
    private JTextField searchField;
    private JTable phoneTable;
    private DefaultTableModel tableModel;
    private static final OkHttpClient client = new OkHttpClient();
    private static DefaultTableModel model;

    public SalesPersonDashboard() {
        // Create the container panel
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout(20, 20));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout(20, 20));
        innerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Search Bar
        JPanel searchBar = new JPanel();
        searchBar.setLayout(new FlowLayout());
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(94, 168, 160));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setOpaque(true);
        searchBar.add(searchField);
        searchBar.add(searchButton);

        // Add Search Bar to container
        container.add(searchBar, BorderLayout.NORTH);

        // Filter Buttons Panel
        JPanel filterButtons = new JPanel();
        filterButtons.setLayout(new FlowLayout());
        filterButtons.setPreferredSize(new Dimension(1000, 10));
        JButton collectionButton = new JButton("Collection");
        collectionButton.setBackground(new Color(94, 168, 160));
        collectionButton.setForeground(Color.WHITE);
        collectionButton.setFocusPainted(false);
        collectionButton.setContentAreaFilled(false);
        collectionButton.setOpaque(true);

        JButton iphoneButton = new JButton("iPhone");
        iphoneButton.setBackground(new Color(94, 168, 160));
        iphoneButton.setForeground(Color.WHITE);
        iphoneButton.setFocusPainted(false);
        iphoneButton.setContentAreaFilled(false);
        iphoneButton.setOpaque(true);

        JButton androidButton = new JButton("Android");
        androidButton.setBackground(new Color(94, 168, 160));
        androidButton.setForeground(Color.WHITE);
        androidButton.setFocusPainted(false);
        androidButton.setContentAreaFilled(false);
        androidButton.setOpaque(true);

        JButton wirelessChargeButton = new JButton("Wireless Charge");
        wirelessChargeButton.setBackground(new Color(94, 168, 160));
        wirelessChargeButton.setForeground(Color.WHITE);
        wirelessChargeButton.setFocusPainted(false);
        wirelessChargeButton.setContentAreaFilled(false);
        wirelessChargeButton.setOpaque(true);

        JButton waterProofButton = new JButton("Water Resistant");
        waterProofButton.setBackground(new Color(94, 168, 160));
        waterProofButton.setForeground(Color.WHITE);
        waterProofButton.setFocusPainted(false);
        waterProofButton.setContentAreaFilled(false);
        waterProofButton.setOpaque(true);

        JButton accessoriesButton = new JButton("With Accessories");
        accessoriesButton.setBackground(new Color(94, 168, 160));
        accessoriesButton.setForeground(Color.WHITE);
        accessoriesButton.setFocusPainted(false);
        accessoriesButton.setContentAreaFilled(false);
        accessoriesButton.setOpaque(true);

        // Add the buttons to the filterButtons panel
        filterButtons.add(collectionButton);
        filterButtons.add(iphoneButton);
        filterButtons.add(androidButton);
        filterButtons.add(wirelessChargeButton);
        filterButtons.add(waterProofButton);
        filterButtons.add(accessoriesButton);

        // Add the filterButtons panel to the container
        container.add(filterButtons, BorderLayout.CENTER);
        setTitle("Phone Trader");
        setVisible(true);
        setPreferredSize(new Dimension(1000, 800));
        pack();
        setLocationRelativeTo(null);

        iphoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

            }
        });

            /*collectionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                if(e.getSource()==collectionButton) {
                    //  fetchPhonesByFilter("Collection");
                }
           /*

            androidButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fetchPhonesByFilter("Android");
                }
            });

            wirelessChargeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fetchPhonesByFilter("Wireless Charge");
                }
            });

            waterProofButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fetchPhonesByFilter("Water Proof");
                }
            });

            accessoriesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fetchPhonesByFilter("With Accessories");
                }
            });*/

        container.add(filterButtons, BorderLayout.CENTER);

        String[] columnNames = {"NO", "BRAND", "MODEL", "PRICE", "SELL"};
        tableModel = new DefaultTableModel(columnNames, 0);
        phoneTable = new JTable(tableModel);
        phoneTable.setRowHeight(24);
        phoneTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        phoneTable.getTableHeader().setBackground(new Color(192, 0, 0));
        phoneTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(phoneTable);
        getAllPhones(tableModel);

        phoneTable.getColumn("SELL").setCellRenderer(new ButtonRenderer());
        phoneTable.getColumn("SELL").setCellEditor(new ButtonEditor(new JCheckBox(), this, phoneTable));

        container.add(scrollPane, BorderLayout.SOUTH);
        add(container);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
            }
        });
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(Color.decode("#d9534f"));
            setForeground(Color.WHITE);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("SELL");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private String label = "SELL";;
        private boolean isPushed;
        private static  JButton button;
        private JFrame parentFrame;
        private JTable table;
        private int row;
        public ButtonEditor(JCheckBox checkBox, JFrame frame, JTable table) {
            super(checkBox);
            this.parentFrame = frame;
            this.table = table;
            button = new JButton(label);
            button.setOpaque(true);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();

                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        Object value = table.getValueAt(selectedRow, 0);
                        if (value instanceof String imeiNumber) {
                            System.out.println("Selected Phone IMEI: " + imeiNumber);
                            ImeiStorage.getInstance().setImei(imeiNumber);
                        }
                    } try {
                        SalesPersonSellPage salesPersonSellPage = new SalesPersonSellPage();
                    } catch (IOException exception) {
                        throw new RuntimeException(exception);
                    }
                    parentFrame.dispose();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            button.setText(label);
            button.setOpaque(true);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
            }
            isPushed = false;
            return label;
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

                    model.addRow(new Object[]{phone.getImei(),
                            phone.getBrand(), phone.getModel(), phone.getPrice(),
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


}

