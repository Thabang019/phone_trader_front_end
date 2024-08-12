package za.ac.cput.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {

    private JTabbedPane tabbedPane;
    private JPanel panel1, panel2, panel3, buttonPanel;
    private JButton btnTab1, btnTab2, btnTab3;


    public AdminDashboard(){
         tabbedPane = new JTabbedPane();
         panel1 = new JPanel();
         panel2 = new JPanel();
         panel3 = new JPanel();
         buttonPanel = new JPanel(new FlowLayout());


        btnTab1 = new JButton("Go to Tab 1");
        btnTab2 = new JButton("Go to Tab 2");
        btnTab3 = new JButton("Go to Tab 3");

    }

    public void setUp(){
        tabbedPane.addTab(" ",panel1);
        tabbedPane.addTab(" ", panel2);
        tabbedPane.addTab(" ", panel3);

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnTab1);
        buttonPanel.add(btnTab2);
        buttonPanel.add(btnTab3);

        btnTab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(0);
            }
        });

        btnTab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(1);
            }
        });

        btnTab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(2);
            }
        });

        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);
    }
}
