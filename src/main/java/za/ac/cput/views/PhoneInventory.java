package za.ac.cput.views;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import za.ac.cput.domain.Phone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PhoneInventory extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private OkHttpClient client;
    private Gson gson;

    public PhoneInventory() {
        setTitle("Device Inventory");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        client = new OkHttpClient();
        gson = new Gson();

        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Find Sale");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        navPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(400, 30));
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(Color.RED.darker());
        searchButton.setForeground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchPanel.add(searchButton, BorderLayout.EAST);

        navPanel.add(searchPanel, BorderLayout.CENTER);

        JLabel dateTimeLabel = new JLabel(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")));
        navPanel.add(dateTimeLabel, BorderLayout.EAST);

        add(navPanel, BorderLayout.NORTH);

        JLabel headingLabel = new JLabel("Device Inventory");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(headingLabel, BorderLayout.CENTER);

        String[] columnNames = {"IMEI", "Brand", "Model", "Color", "Price", "Status", "Specification", "Condition"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);

        tableContents();

        searchButton.addActionListener(e -> searchByImei());
    }

    private void tableContents() {
        String url = "http://localhost:8080/phone-trader/phone/getall";
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<List<Phone>>() {}.getType();
                List<Phone> phones = gson.fromJson(jsonData, listType);

                tableModel.setRowCount(0);

                for (Phone phone : phones) {
                    Object[] rowData = {
                            phone.getImei(),
                            phone.getBrand(),
                            phone.getModel(),
                            phone.getColor(),
                            phone.getPrice(),
                            phone.getStatus(),
                            phone.getSpecification().toString(),
                            phone.getCondition().toString()
                    };
                    tableModel.addRow(rowData);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch data from server", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchByImei() {
        String imei = searchField.getText().trim();

        if (imei.isEmpty()) {
            tableContents();
            return;
        }

        String url = "http://localhost:8080/phone-trader/phone/read/" + imei;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                Phone phone = gson.fromJson(jsonData, Phone.class);

                tableModel.setRowCount(0);

                if (phone != null) {
                    Object[] rowData = {
                            phone.getImei(),
                            phone.getBrand(),
                            phone.getModel(),
                            phone.getColor(),
                            phone.getPrice(),
                            phone.getStatus(),
                            phone.getSpecification().toString(),
                            phone.getCondition().toString()
                    };
                    tableModel.addRow(rowData);
                } else {
                    JOptionPane.showMessageDialog(this, "No phone found with IMEI: " + imei, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch data from server", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PhoneInventory::new);
    }
}
