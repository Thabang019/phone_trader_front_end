package za.ac.cput.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import za.ac.cput.domain.*;
import za.ac.cput.domain.Address;
import za.ac.cput.dto.EmployeeStorage;
import za.ac.cput.dto.TokenStorage;
import za.ac.cput.factory.*;
import za.ac.cput.util.LocalDateTimeTypeAdapter;
import za.ac.cput.util.LocalDateTypeAdapter;
import za.ac.cput.util.LocalTimeTypeAdapter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


public class Purchase {

    private JPanel mainContainer;
    private static DefaultTableModel model;
    private JPanel dashboardPanel;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private JTextField imeiField, modelField, priceField, screenSizeField, cameraField, idField, firstNameField, middleNameField, lastNameField, emailField, phoneNumberField, altPhoneField, streetNumberField, streetNameField, suburbField, cityField, postalCodeField;
    private JComboBox brandDropdown, colorDropdown, conditionDropdown, storageDropdown, ramDropdown, osDropdown, simDropdown, microsdDropdown, fingerprintDropdown, waterResistanceDropdown, wirelessChargingDropdown;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
            .create();

    public Purchase() {

        mainContainer = new JPanel(new BorderLayout(20, 20));
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //dashboardPanel = new JPanel(new BorderLayout()) ;

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        JPanel phoneDetailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        phoneDetailsPanel.setBorder(BorderFactory.createTitledBorder("Phone Details"));
        addPhoneDetails(phoneDetailsPanel);


        JPanel customerDetailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        customerDetailsPanel.setBorder(BorderFactory.createTitledBorder("Customer Details"));
        addCustomerDetails(customerDetailsPanel);
        contentPanel.add(phoneDetailsPanel);
        contentPanel.add(customerDetailsPanel);

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        //dashboardPanel.add(mainContainer, BorderLayout.CENTER);
        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.setBackground(Color.BLUE);
        purchaseButton.setForeground(Color.WHITE);
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePurchase();
            }
        });
        dashboardPanel.add(purchaseButton, BorderLayout.SOUTH);
        mainContainer.setVisible(true);
    }


        private void addPhoneDetails (JPanel phoneDetailsPanel){
            String[] labels = {
                    "IMEI Number:", "Brand:", "Model:", "Color:", "Price:", "Condition:", "Screen size:",
                    "Storage:", "RAM:", "Operating System:", "Camera:", "Number of sims:", "MicroSD:",
                    "Fingerprint:", "Water resistance:", "Wireless charging:", "Accessories Name:",
                    "Accessories Description:"
            };

            imeiField = new JTextField();
            brandDropdown = new JComboBox<>(new String[]{"Apple", "Samsung", "Huawei", "Nokia", "Xiaomi", "Honor", "Oppo", "Vivo", "Hisense", "Tecno"});
            modelField = new JTextField();
            colorDropdown = new JComboBox<>(new String[]{"Black", "White", "Silver", "Gold"});
            priceField = new JTextField();
            conditionDropdown = new JComboBox<>(new String[]{"NEW", "USED", "REFURBISHED"});
            screenSizeField = new JTextField();
            storageDropdown = new JComboBox<>(new String[]{"32GB", "64GB", "128GB", "256GB"});
            ramDropdown = new JComboBox<>(new String[]{"2GB", "4GB", "6GB", "8GB", "12GB"});
            osDropdown = new JComboBox<>(new String[]{"Android", "iOS"});
            cameraField = new JTextField();
            simDropdown = new JComboBox<Integer>(new Integer[]{1, 2});
            microsdDropdown = new JComboBox<>(new String[]{"Supported", "Not Supported"});
            fingerprintDropdown = new JComboBox<>(new String[]{"Yes", "No"});
            waterResistanceDropdown = new JComboBox<>(new String[]{"Yes", "No"});
            wirelessChargingDropdown = new JComboBox<>(new String[]{"Yes", "No"});

            JComponent[] fields = {
                    imeiField, brandDropdown, modelField, colorDropdown, priceField, conditionDropdown,
                    screenSizeField, storageDropdown, ramDropdown, osDropdown, cameraField, simDropdown,
                    microsdDropdown, fingerprintDropdown, waterResistanceDropdown, wirelessChargingDropdown,

            };

            for (int i = 0; i < labels.length; i++) {
                phoneDetailsPanel.add(new JLabel(labels[i]));
                phoneDetailsPanel.add(fields[i]);
            }
        }

    private void handlePurchase() {
        if (validateForm()) {
            try {
                double screenSize = Double.parseDouble(screenSizeField.getText());
                double price = Double.parseDouble(priceField.getText());

                // Create the Phone object
                Phone.Condition condition = Phone.Condition.valueOf(conditionDropdown.getSelectedItem().toString());
                Phone phone = PhoneFactory.createPhone(imeiField.getText(), brandDropdown.getSelectedItem().toString(),
                        modelField.getText(), colorDropdown.getSelectedItem().toString(), price, "available",
                        SpecificationFactory.createSpecification(screenSize, storageDropdown.getSelectedItem().toString(),
                                ramDropdown.getSelectedItem().toString(), osDropdown.getSelectedItem().toString(),
                                cameraField.getText(), Integer.parseInt(simDropdown.getSelectedItem().toString()),
                                microsdDropdown.getSelectedItem().toString(), fingerprintDropdown.getSelectedItem().toString(),
                                waterResistanceDropdown.getSelectedItem().toString(), wirelessChargingDropdown.getSelectedItem().toString()), condition);

                // Create Address and Contact
                Address address = AddressFactory.buildAddress(streetNumberField.getText(), streetNameField.getText(), suburbField.getText(), cityField.getText(), postalCodeField.getText());
                Contact contact = ContactFactory.createContact(emailField.getText(), phoneNumberField.getText(), altPhoneField.getText(), address);

                // Create Purchase
                za.ac.cput.domain.Purchase purchase = PurchaseFactory.createPurchase(LocalDate.now(), LocalTime.now(), EmployeeStorage.getInstance().getEmployee(), price, "cash", phone);
                ArrayList<za.ac.cput.domain.Purchase> purchaseList = new ArrayList<>();
                purchaseList.add(purchase);

                // Create Seller
                Seller seller = SellerFactory.createSeller(idField.getText(), firstNameField.getText(), middleNameField.getText(), lastNameField.getText(), contact, purchaseList);

                // Save Seller
                String response = createSeller("http://localhost:8080/laptop-trader/seller/save", seller);
                JOptionPane.showMessageDialog(mainContainer, "Purchase successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainContainer, "Purchase Failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

        public void addCustomerDetails (JPanel customerDetailsPanel){
            String[] labels = {
                    "Identity Number:", "First Name:", "Middle Name:", "Last Name:", "Email:", "Phone Number:",
                    "Alternative Number:", "Street Number:", "Street Name:", "Suburb:", "City:", "Postal Code:"
            };

            idField = new JTextField();
            firstNameField = new JTextField();
            middleNameField = new JTextField();
            lastNameField = new JTextField();
            emailField = new JTextField();
            phoneNumberField = new JTextField();
            altPhoneField = new JTextField();
            streetNumberField = new JTextField();
            streetNameField = new JTextField();
            suburbField = new JTextField();
            cityField = new JTextField();
            postalCodeField = new JTextField();

            JComponent[] fields = {idField, firstNameField, middleNameField, lastNameField, emailField, phoneNumberField,
                    altPhoneField, streetNumberField, streetNameField, suburbField, cityField, postalCodeField};

            for (int i = 0; i < labels.length; i++) {
                customerDetailsPanel.add(new JLabel(labels[i]));
                customerDetailsPanel.add(fields[i]);
            }
        }
        public boolean validateForm () {
            boolean isValid = true;

            Component[] components = ((Container) dashboardPanel.getComponent(0)).getComponents();
            isValid = isValid((JPanel) components[0], true);
            isValid = isValid((JPanel) components[1], isValid);

            if (!isValid) {
                JOptionPane.showMessageDialog(mainContainer, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            return isValid;
        }

        public boolean isValid (JPanel panel,boolean isValid){
            for (Component component : panel.getComponents()) {
                if (component instanceof JTextField textField) {
                    if (textField.getText().isEmpty()) {
                        textField.setBorder(BorderFactory.createLineBorder(Color.RED));
                        isValid = false;
                    } else {
                        textField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    }
                }
            }

            return isValid;
        }

        public String createSeller (String url, Seller seller) throws IOException {
            String json = gson.toJson(seller);
            return post(url, json);
        }

        public String post (String url, String json) throws IOException {
            String token = TokenStorage.getInstance().getToken();
            RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .header("Authorization", "Bearer " + token)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            } catch (Exception ex) {
                System.out.println("Failed");
            }
            return token;
        }

        public JPanel getPurchase () {
            return mainContainer;
        }


    }
