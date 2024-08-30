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
import za.ac.cput.factory.*;
import za.ac.cput.util.LocalDateTimeTypeAdapter;
import za.ac.cput.util.LocalDateTypeAdapter;

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

    private JTextField imeiField, modelField, priceField,
    screenSizeField,cameraField;
    private JComboBox brandDropdown, colorDropdown, conditionDropdown,
     storageDropdown, ramDropdown, osDropdown, simDropdown,
     microsdDropdown, fingerprintDropdown, waterResistanceDropdown, wirelessChargingDropdown;


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

        frame.setPreferredSize(new Dimension(1400, 750));
        frame.pack();
        frame.setLocationRelativeTo(null);
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
                            Long imei = Long.valueOf(imeiField.getText());
                            double price = Double.parseDouble(priceField.getText());

                            //Phone.Condition condition1 = Phone.Condition.NEW.valueOf(conditionDropdown.getSelectedItem().toString());
                            Phone.Condition condition1 = Phone.Condition.valueOf(conditionDropdown.getSelectedItem().toString());
                            Phone phone = PhoneFactory.createPhone(imei, brandDropdown.getSelectedItem().toString(), modelField.getText(), colorDropdown.getSelectedItem().toString(), price, "available", spec, Phone.Condition.NEW);
                            Address address = AddressFactory.buildAddress("123","New Market","Woodstock","Cape Town","7750");
                            System.out.println(phone);
                            Contact contact = ContactFactory.createContact("0789456123","email@gmail.com",address);
                            Purchase purchase = PurchaseFactory.createPurchase(LocalDate.now(), LocalTime.now(), EmployeeStorage.getInstance().getEmployee(), 1200, "cash", phone);
                            ArrayList<Purchase> purchaseList = new ArrayList<>();
                            purchaseList.add(purchase);
                            Seller buyer = SellerFactory.createSeller("3241","Okuhle", "Kwanele", "Gebashe",contact, purchaseList);
                            System.out.println(purchase);

                            String response = createPurchase("http://localhost:8080/phone-trader/purchase/save", purchase);
                            System.out.println(response + "response");

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
        JTextField accessoriesNameField = new JTextField();
        JTextField accessoriesDescriptionField = new JTextField();


        JComponent[] fields = {
                imeiField, brandDropdown, modelField, colorDropdown, priceField, conditionDropdown,
                screenSizeField, storageDropdown, ramDropdown, osDropdown, cameraField, simDropdown,
                microsdDropdown, fingerprintDropdown, waterResistanceDropdown, wirelessChargingDropdown,
                accessoriesNameField, accessoriesDescriptionField
        };

        for (int i = 0; i < labels.length; i++) {
            phoneDetailsPanel.add(new JLabel(labels[i]));
            phoneDetailsPanel.add(fields[i]);
        }
    }

    private void addCustomerDetails(JPanel customerDetailsPanel) {
        String[] labels = {
                "Identity Number:", "First Name:", "Middle Name:", "Last Name:", "Email:", "Phone Number:",
                "Alternative Number:", "Street Number:", "Street Name:", "Suburb:", "City:", "Postal Code:"
        };

        JTextField idField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField middleNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JTextField altPhoneField = new JTextField();
        JTextField streetNumberField = new JTextField();
        JTextField streetNameField = new JTextField();
        JTextField suburbField = new JTextField();
        JTextField cityField = new JTextField();
        JTextField postalCodeField = new JTextField();

        JComponent[] fields = {idField, firstNameField, middleNameField, lastNameField, emailField, phoneNumberField,
                altPhoneField, streetNumberField, streetNameField, suburbField, cityField, postalCodeField};

        for (int i = 0; i < labels.length; i++) {
            customerDetailsPanel.add(new JLabel(labels[i]));
            customerDetailsPanel.add(fields[i]);
        }
    }

   private boolean validateForm() {
       boolean isValid = true;

       Component[] components = ((Container) dashboardPanel.getComponent(0)).getComponents();
      // isValid = isValid((JPanel) components[0], true);
      // isValid = isValid((JPanel) components[1], isValid);

       if (!isValid) {
           JOptionPane.showMessageDialog(frame, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
       }

       return isValid;
   }

    private boolean isValid(JPanel panel, boolean isValid) {
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



    private String createPurchase(String url, Purchase purchase) throws IOException {
        String json = gson.toJson(purchase);
        return post(url, json);
    }

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)

                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public void showDashboard() {
        frame.setVisible(true);
    }
}
