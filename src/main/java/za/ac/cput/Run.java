package za.ac.cput;

import za.ac.cput.views.DisplayEmployee;
import za.ac.cput.views.Login;
import za.ac.cput.views.Registration;
import za.ac.cput.views.Welcome;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Run {
    private JFrame frame;
    private Welcome welcome;

    public Run() {

        welcome = new Welcome();
        welcome.Welcome();

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            new Run();
        });
    }
}
