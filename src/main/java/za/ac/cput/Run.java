package za.ac.cput;

import za.ac.cput.views.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Run {
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
