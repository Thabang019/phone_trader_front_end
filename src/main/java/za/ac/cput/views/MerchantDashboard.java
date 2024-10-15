package za.ac.cput.views;

import javax.swing.*;
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


public class MerchantDashboard {

    private JFrame frame;
    private JPanel dashboardPanel;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private JTextField imeiField, modelField, priceField, screenSizeField,cameraField, idField, firstNameField, middleNameField, lastNameField, emailField, phoneNumberField, altPhoneField, streetNumberField, streetNameField, suburbField, cityField, postalCodeField;
    private JComboBox brandDropdown, colorDropdown, conditionDropdown, storageDropdown, ramDropdown, osDropdown, simDropdown, microsdDropdown, fingerprintDropdown, waterResistanceDropdown, wirelessChargingDropdown;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
            .create();



    public MerchantDashboard() {
        initialize();
    }


    private void initialize() {

        frame = new JFrame("Merchant Dashboard");

        dashboardPanel = new JPanel(new BorderLayout());

        setupMenuBar();
        setupContentPanel();

        frame.add(dashboardPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JLabel logoLabel = new JLabel("Merchant Dashboard");
        menuBar.add(logoLabel);

        menuBar.add(Box.createHorizontalGlue());

        JButton logOutButton = new JButton("Log Out");
        menuBar.add(logOutButton);

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    frame.dispose();
                   Welcome welcome = new Welcome();
                    welcome.Welcome();
                }
            }
        });

        frame.setJMenuBar(menuBar);


            JButton purchaseButton = new JButton("Purchase");
            purchaseButton.setBackground(Color.BLUE);
            purchaseButton.setForeground(Color.WHITE);

            purchaseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {



                    if (validateForm()) {
                        try {
                            double screenSize = Double.parseDouble(screenSizeField.getText());
                            Spec spec = SpecificationFactory.createSpecification(screenSize, storageDropdown.getSelectedItem().toString(), ramDropdown.getSelectedItem().toString(), osDropdown.getSelectedItem().toString(), cameraField.getText(), Integer.parseInt(simDropdown.getSelectedItem().toString()),
                                    microsdDropdown.getSelectedItem().toString(), fingerprintDropdown.getSelectedItem().toString(), waterResistanceDropdown.getSelectedItem().toString(), wirelessChargingDropdown.getSelectedItem().toString());
                            System.out.println(spec);

                            double price = Double.parseDouble(priceField.getText());

                            Phone.Condition condition1 = Phone.Condition.valueOf(conditionDropdown.getSelectedItem().toString());
                            Phone phone = PhoneFactory.createPhone(imeiField.getText(), brandDropdown.getSelectedItem().toString(), modelField.getText(), colorDropdown.getSelectedItem().toString(), price, "available", spec, Phone.Condition.NEW);
                            System.out.println(phone);

                            Address address = AddressFactory.buildAddress( streetNumberField.getText(), streetNameField.getText(), suburbField.getText(), cityField.getText(), postalCodeField.getText());
                            Contact contact = ContactFactory.createContact(emailField.getText(), phoneNumberField.getText(), altPhoneField.getText(),address);

                            Purchase purchase = PurchaseFactory.createPurchase(LocalDate.now(), LocalTime.now(), EmployeeStorage.getInstance().getEmployee(), 1200, "cash", phone);
                            ArrayList<Purchase> purchaseList = new ArrayList<>();
                            purchaseList.add(purchase);
                            System.out.println(purchase);

                            Seller seller = SellerFactory.createSeller(idField.getText(), firstNameField.getText(), middleNameField.getText(), lastNameField.getText(),contact, purchaseList);
                            System.out.println(seller);

                            String response = createSeller("http://localhost:8080/phone-trader/seller/save", seller );
                            System.out.println(response);

                            System.out.println(gson);

                            JOptionPane.showMessageDialog(frame, "Purchase successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Purchase Failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    }
                }
            });
            frame.setVisible(true);
            dashboardPanel.add(purchaseButton, BorderLayout.SOUTH);

    }

    private void setupContentPanel() {
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));

        JPanel phoneDetailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        phoneDetailsPanel.setBorder(BorderFactory.createTitledBorder("Phone Details"));
        addPhoneDetails(phoneDetailsPanel);

        JPanel customerDetailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        customerDetailsPanel.setBorder(BorderFactory.createTitledBorder("Customer Details"));
        addCustomerDetails(customerDetailsPanel);

        contentPanel.add(phoneDetailsPanel);
        contentPanel.add(customerDetailsPanel);

        dashboardPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void addPhoneDetails(JPanel phoneDetailsPanel) {
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

    public void addCustomerDetails(JPanel customerDetailsPanel) {
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
    public boolean validateForm() {
       boolean isValid = true;

       Component[] components = ((Container) dashboardPanel.getComponent(0)).getComponents();
       isValid = isValid((JPanel) components[0], true);
       isValid = isValid((JPanel) components[1], isValid);

       if (!isValid) {
           JOptionPane.showMessageDialog(frame, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
       }

       return isValid;
   }

    public boolean isValid(JPanel panel, boolean isValid) {
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

    public String createSeller(String url, Seller seller) throws IOException {
        String json = gson.toJson(seller);
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

    public void showDashboard() {
        frame.setVisible(true);
    }
}
