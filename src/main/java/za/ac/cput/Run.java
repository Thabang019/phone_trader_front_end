package za.ac.cput;

import za.ac.cput.views.UserInterface;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Run {
        public static void main(String[] args) {
            UserInterface guiObject = new UserInterface();
            guiObject.pack();
            guiObject.setSize(400, 400);
            guiObject.setVisible(true);
            guiObject.setDefaultCloseOperation(EXIT_ON_CLOSE);
            guiObject.setGui();
        }
}
