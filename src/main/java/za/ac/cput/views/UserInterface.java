package za.ac.cput.views;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserInterface extends JFrame implements ActionListener {
    private JPanel panelCenter, panelEast, panelEast1, panelEast2, panelEast3, panelSouth, panelWest;
    private JLabel labelSbjCode, labelSbjDescription;
    private JTextField textField1, textField2;
    private JComboBox<String> cbo;
    private JButton btnSave, btnCreateTable, btnRead, btnFillCbo;

    private Object[][] data;//data model for the table
    private String[] columns;//column names
    private DefaultTableModel tableModel;//
    private JTable table;
    private JScrollPane scrollPane;


    public UserInterface() {
        super("Add A Subject");

        panelCenter = new JPanel();
        panelEast = new JPanel();
        panelEast1 = new JPanel();
        panelEast2 = new JPanel();
        panelEast3 = new JPanel();
        panelSouth = new JPanel();
        panelWest = new JPanel();

        labelSbjCode = new JLabel("Subject Code");
        labelSbjDescription = new JLabel("Subject Description");

        textField1 = new JTextField(30);
        textField2 = new JTextField(30);

        cbo = new JComboBox();
        cbo.addItem("Select");

        btnSave = new JButton("Save");
        btnCreateTable = new JButton("Cancel");
        btnRead = new JButton("Read");
        btnFillCbo = new JButton("Fill Combo");

        data = new Object[][]{{"ADP", "262S"}, {"INM", "262S"}};
        columns = new String[]{"SUBJECT NAME", "SUBJECT CODE"};

        tableModel = new DefaultTableModel(data, columns);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
    }

    public void setGui() {
        panelCenter.setLayout(new BorderLayout());
        panelCenter.setPreferredSize(new Dimension(350, 100));

        panelEast.setLayout(new GridLayout(3, 1));
        panelSouth.setLayout(new GridLayout(1, 4));
        panelWest.setLayout(new GridLayout(2, 2));
        panelWest.setPreferredSize(new Dimension(250, 100));
        this.setLayout(new BorderLayout());

        panelCenter.add(scrollPane, BorderLayout.NORTH);

        panelEast2.add(cbo);

        panelEast.add(panelEast1);
        panelEast.add(panelEast2);
        panelEast.add(panelEast3);

        panelSouth.add(btnSave);
        panelSouth.add(btnCreateTable);
        panelSouth.add(btnRead);
        panelSouth.add(btnFillCbo);

        panelWest.add(labelSbjCode);
        panelWest.add(textField1);
        panelWest.add(labelSbjDescription);
        panelWest.add(textField2);

        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelEast, BorderLayout.EAST);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.add(panelWest, BorderLayout.WEST);

        //Action Listeners
        btnSave.addActionListener(ActionEvent -> {});
        btnCreateTable.addActionListener(ActionEvent -> {

        });
        btnRead.addActionListener(ActionEvent -> {});
        btnFillCbo.addActionListener(ActionEvent -> {});

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
