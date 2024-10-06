package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import za.ac.cput.domain.*;
import za.ac.cput.domain.Address;
import za.ac.cput.dto.EmployeeStorage;
import za.ac.cput.dto.TokenStorage;
import za.ac.cput.factory.*;
import za.ac.cput.util.ImeiStorage;
import za.ac.cput.util.LocalDateTimeTypeAdapter;
import za.ac.cput.util.LocalDateTypeAdapter;
import za.ac.cput.util.LocalTimeTypeAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class SalesPersonSellPage extends JFrame {
    private final OkHttpClient client = new OkHttpClient();
    private Request request;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
            .create();
    private Phone phone;
    private JTextField identityNumberField, firstNameField, middleNameField, lastNameField, emailField, phoneNumberField, alternativeNumberField;
    private JTextField streetNumberField, streetNameField, suburbField, cityField, postalCodeField;

    public SalesPersonSellPage() throws IOException {

        phone = findPhone("http://localhost:8080/phone-trader/phones/read/" + ImeiStorage.getInstance().getImei());

        // Create the main container panel
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout(20, 20));
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create the main content panel (left and right sections)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 2, 20, 0));

        // Create the Phone Details panel
        JPanel phoneDetailsPanel = new JPanel();
        phoneDetailsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        phoneDetailsPanel.setBorder(BorderFactory.createTitledBorder("Phone Details"));

        // Add fields for phone details
        phoneDetailsPanel.add(createLabel("IMEI Number:"));
        phoneDetailsPanel.add(createTextField(String.valueOf(phone.getImei())));
        phoneDetailsPanel.add(createLabel("Brand:"));
        phoneDetailsPanel.add(createTextField(phone.getBrand()));
        phoneDetailsPanel.add(createLabel("Model:"));
        phoneDetailsPanel.add(createTextField(phone.getModel()));
        phoneDetailsPanel.add(createLabel("Color:"));
        phoneDetailsPanel.add(createTextField(phone.getColor()));
        phoneDetailsPanel.add(createLabel("Price:"));
        phoneDetailsPanel.add(createTextField(String.valueOf(phone.getPrice())));
        phoneDetailsPanel.add(createLabel("Status:"));
        phoneDetailsPanel.add(createTextField(phone.getStatus()));

        // Add fields for phone specifications
        phoneDetailsPanel.setBorder(BorderFactory.createTitledBorder("Specifications"));
        phoneDetailsPanel.add(createLabel("Screen size:"));
        phoneDetailsPanel.add(createTextField(String.valueOf(phone.getSpecification().getScreenSize())));
        phoneDetailsPanel.add(createLabel("Storage:"));
        phoneDetailsPanel.add(createTextField(phone.getSpecification().getStorage()));
        phoneDetailsPanel.add(createLabel("Ram:"));
        phoneDetailsPanel.add(createTextField(phone.getSpecification().getRam()));
        phoneDetailsPanel.add(createLabel("Operating System:"));
        phoneDetailsPanel.add(createTextField(phone.getSpecification().getOperatingSystem()));
        phoneDetailsPanel.add(createLabel("Camera:"));
        phoneDetailsPanel.add(createTextField(phone.getSpecification().getCamera()));
        phoneDetailsPanel.add(createLabel("Number of sims:"));
        phoneDetailsPanel.add(createTextField(String.valueOf(phone.getSpecification().getNumOfSims())));
        phoneDetailsPanel.add(createLabel("MicroSD:"));
        phoneDetailsPanel.add(createTextField(phone.getSpecification().getMicroSD()));
        phoneDetailsPanel.add(createLabel("Fingerprint:"));
        phoneDetailsPanel.add(createTextField(phone.getSpecification().getFingerPrint()));
        phoneDetailsPanel.add(createLabel("Water resistance:"));
        phoneDetailsPanel.add(createTextField(phone.getSpecification().getWaterResistance()));
        phoneDetailsPanel.add(createLabel("Wireless charging:"));
        phoneDetailsPanel.add(createTextField(phone.getSpecification().getWirelessCharging()));

        // Add Phone Details panel to content panel
        contentPanel.add(phoneDetailsPanel);

        // Create the Customer Details panel
        JPanel customerDetailsPanel = new JPanel();
        customerDetailsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        customerDetailsPanel.setBorder(BorderFactory.createTitledBorder("Customer Details"));

        customerDetailsPanel.add(createLabel("Identity Number:"));
        identityNumberField = new JTextField();
        customerDetailsPanel.add(identityNumberField);

        customerDetailsPanel.add(createLabel("First Name:"));
        firstNameField = new JTextField();
        customerDetailsPanel.add(firstNameField);

        customerDetailsPanel.add(createLabel("Middle Name:"));
        middleNameField = new JTextField();
        customerDetailsPanel.add(middleNameField);

        customerDetailsPanel.add(createLabel("Last Name:"));
        lastNameField = new JTextField();
        customerDetailsPanel.add(lastNameField);

        customerDetailsPanel.add(createLabel("Email:"));
        emailField = new JTextField();
        customerDetailsPanel.add(emailField);

        customerDetailsPanel.add(createLabel("Phone Number:"));
        phoneNumberField = new JTextField();
        customerDetailsPanel.add(phoneNumberField);

        customerDetailsPanel.add(createLabel("Alternative Number:"));
        alternativeNumberField = new JTextField();
        customerDetailsPanel.add(alternativeNumberField);

        customerDetailsPanel.add(createLabel("Street Number:"));
        streetNumberField = new JTextField();
        customerDetailsPanel.add(streetNumberField);

        customerDetailsPanel.add(createLabel("Street Name:"));
        streetNameField = new JTextField();
        customerDetailsPanel.add(streetNameField);

        customerDetailsPanel.add(createLabel("Suburb:"));
        suburbField = new JTextField();
        customerDetailsPanel.add(suburbField);

        customerDetailsPanel.add(createLabel("City:"));
        cityField = new JTextField();
        customerDetailsPanel.add(cityField);

        customerDetailsPanel.add(createLabel("Postal Code:"));
        postalCodeField = new JTextField();
        customerDetailsPanel.add(postalCodeField);
        // Add Customer Details panel to content panel
        contentPanel.add(customerDetailsPanel);

        // Add content panel to main container
        mainContainer.add(contentPanel, BorderLayout.CENTER);

        // Create the Sell button and add it to the bottom of the main container
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("BACK");
        backButton.setBackground(new Color(192, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(backButton);
        JButton sellButton = new JButton("SELL");
        sellButton.setBackground(new Color(192, 0, 0));
        sellButton.setForeground(Color.WHITE);
        sellButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(sellButton);

        // Add button panel to the main container
        mainContainer.add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {

            // ActionListener for the back button
            @Override
            public void actionPerformed(ActionEvent e) {
                openSalesPersonDashboard();
                dispose();

            }});

        // ActionListener for the Sell button
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == sellButton) {

                    String idNumber =  identityNumberField.getText();
                    String firstName = firstNameField.getText();
                    String middleName = middleNameField.getText();
                    String lastName = lastNameField.getText();
                    String email = emailField.getText();
                    String phoneNumber = phoneNumberField.getText();
                    String city = cityField.getText();
                    String postalCode = postalCodeField.getText();
                    String suburb = suburbField.getText();
                    String houseNumber = streetNumberField.getText();
                    String streetNumber = streetNameField.getText();

                    Address address = AddressFactory.buildAddress(houseNumber, streetNumber, suburb, city, postalCode);

                    System.out.println(address);

                    Contact contact = ContactFactory.createContact(phoneNumber, email, address);

                    System.out.println(contact);

                    double amount = phone.getPrice();
                    Sale sale = SaleFactory.createSale(LocalDate.now(), LocalTime.now(), EmployeeStorage.getInstance().getEmployee(), phone, "cash",amount);
                    ArrayList<Sale> saleList = new ArrayList<>();
                    saleList.add(sale);
                    System.out.println(sale);

                    Buyer buyer = BuyerFactory.createBuyer(idNumber,firstName, middleName, lastName,contact, saleList);
                    System.out.println(buyer);

                    String response = null;
                    try {
                        response = createBuyer("http://localhost:8080/phone-trader/buyer/save", buyer);
                        System.out.println(response);
                        JOptionPane.showMessageDialog(sellButton, "Phone sold!");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(sellButton, "Transaction unsuccessful!");
                    }

                }
            }
        });

        // Add main container to the frame
        add(mainContainer);
        setTitle("Phone Details");
        setVisible(true);
        setPreferredSize(new Dimension(1000, 700));
        pack();
        setLocationRelativeTo(null);
    }
    private void openSalesPersonDashboard() {
        SalesPersonDashboard board = new SalesPersonDashboard();
        setVisible(true);

    }
    private JLabel createLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 25)); // Set preferred size
        return label;
    }

    private JTextField createTextField(String initialValue) {
        JTextField textField = new JTextField(initialValue, 20); // Set the initial value and number of columns
        textField.setPreferredSize(new Dimension(200, 25)); // Set preferred size
        return textField;
    }

    public Phone findPhone(String url) throws IOException {
        String token = TokenStorage.getInstance().getToken();
        request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(response.body().string(), Phone.class);
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    public String createBuyer(String url, Buyer buyer) throws IOException {
        String json = gson.toJson(buyer);
        return post(url, json);
    }

    public String post(String url, String json) throws IOException {
        String token = TokenStorage.getInstance().getToken();
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception ex){
            System.out.println("Failed");
        }
        return token;
    }


}


