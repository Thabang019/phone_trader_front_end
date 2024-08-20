package za.ac.cput;

import za.ac.cput.views.Login;
import za.ac.cput.views.Registration;
import za.ac.cput.views.Welcome;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Run {
    private JFrame frame;
    private Welcome welcome;
    private Login login;
    private Registration registration;

    public Run() {
        frame = new JFrame("Phone Trader Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        welcome = new Welcome();
        login =  new Login();
        registration = new Registration();
        frame.add(registration.getPanel());
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Run();
        });

    }
}
