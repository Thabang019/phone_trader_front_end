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
        frame = new JFrame("Phone Trader Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 400);
        welcome = new Welcome();
        frame.add(welcome.getPanel());
        frame.setPreferredSize(new Dimension(1000,800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Run();
        });

    }
}
