package za.ac.cput.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome {
    private JPanel welcomePanel;
    public Welcome(){

        welcomePanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(192, 0, 0));
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(300, 450));

        JLabel titleLabel = new JLabel("Phone-Trader");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel descriptionLabel = new JLabel("<html>An application that will make <br> your gift-sending experience <br> even more memorable</html>");
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        leftPanel.add(descriptionLabel, gbc);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("Welcome Back");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.BLACK);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(192, 0, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setPreferredSize(new Dimension(120, 40));

        gbc.gridy = 0;
        rightPanel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        rightPanel.add(loginButton, gbc);

        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.add(leftPanel, BorderLayout.WEST);
        welcomePanel.add(rightPanel, BorderLayout.CENTER);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLogin();
                //welcomePanel.setVisible(false);
            }
        });
    }
    private void openLogin() {
        JFrame frame = new JFrame("Phone Trader Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Login loginForm = new Login();
        frame.add(loginForm.getPanel());
        frame.setPreferredSize(new Dimension(1000,800));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public JPanel getPanel() {
        return welcomePanel;
    }
}
