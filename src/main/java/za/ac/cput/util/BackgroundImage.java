package za.ac.cput.util;

import javax.swing.*;
import java.awt.*;

public class BackgroundImage extends JPanel {
    private Image backgroundImage;

    public BackgroundImage(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}