package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.domain.Employee;
import za.ac.cput.dto.TokenStorage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DisplayEmployee {
    private JPanel mainPanel;
    private static DefaultTableModel model;
    private static final OkHttpClient client = new OkHttpClient();
    public DisplayEmployee(){

        mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("EMPLOYEES", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(192, 0, 0));
        topPanel.add(title, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Find Employee By Employee ID");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(192, 0, 0));
        searchButton.setForeground(Color.WHITE);
        searchPanel.add(label);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);


        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"Employee_ID", "First_Name", "Last_Name", "Role", "Email", "Phone_Number"};

        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(192, 0, 0));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));


        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("BACK");
        JButton addButton = new JButton("ADD EMPLOYEE");
        backButton.setBackground(new Color(192, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(192, 0, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButtonPanel.add(backButton);
        addButtonPanel.add(addButton);


        centerPanel.add(searchPanel);
        centerPanel.add(tablePanel);
        centerPanel.add(addButtonPanel);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManagerDashboard();

            }});

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistration();
            }
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel);

        
        getAll();
    }

    public JPanel getDisplayEmployee(){
        return mainPanel;
    }



    private void openRegistration() {

        Registration registrationForm = new Registration();
        registrationForm.Registration();

    }
    private void openManagerDashboard() {
        ManagerDashboard dashboard = new ManagerDashboard();
        dashboard.ManagerDashboard();

    }


    public static void getAll() {
        try {
            final String url = "http://localhost:8080/phone-trader/employee/getAll";

            String responseBody = read(url);

            if (responseBody.startsWith("[")) {
                JSONArray employees = new JSONArray(responseBody);
                Gson g = new GsonBuilder()
                        .create();
                for (int i = 0; i < employees.length(); i++) {
                    JSONObject employeesJSONObject = employees.getJSONObject(i);
                    Employee emp = g.fromJson(employeesJSONObject.toString(), Employee.class);

                    model.addRow(new Object[]{emp.getEmployeeID(),
                            emp.getFirstName(), emp.getLastName(),
                            emp.getRole(), emp.getContact().getEmail(),
                            emp.getContact().getPhoneNumber()});
                }
            } else {
                System.err.println("Expected a JSON array but got: " + responseBody);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch employees", e);
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