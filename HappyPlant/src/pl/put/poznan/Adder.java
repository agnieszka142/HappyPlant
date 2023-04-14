package pl.put.poznan;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

public class Adder extends JDialog { // Okno do wprowadzania informacji o roślinach.

    private final JPanel mainAddPanel = new JPanel();
    private final JLabel infoLabel = new JLabel("Wprowadź informacje o roślinie");
    private final JLabel nameInfo = new JLabel("Nazwa:");
    public JTextField nameTextField = new JTextField();
    private final JLabel wateringInfo = new JLabel("Częstotliwość podlewania w dniach");
    public JTextField wateringTextField = new JTextField();
    private final JLabel dateInfo = new JLabel("Data ostatniego podlania:");
    public JPanel JPanelDateChooser = new JPanel();
    public JDateChooser calendar = new JDateChooser();
    public JButton addButton = new JButton("Dodaj");
    public JLabel nameErrorLabel = new JLabel("");
    public JLabel waterErrorLabel = new JLabel("");
    public JLabel dateErrorLabel = new JLabel("");
    private final String title;

    public Adder(String title) {
        this.title = title;
        dialogSetup();

        nameTextField.addActionListener(e -> wateringTextField.grabFocus());
    }

    public void dialogSetup() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - 200, dim.height / 2 - 180);
        setSize(400, 320);
        setTitle(title);
        waterErrorLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        waterErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        nameErrorLabel.setForeground(Color.RED);
        dateErrorLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        dateErrorLabel.setForeground(Color.RED);
        setVisible(true);

        mainAddPanel.setBackground(Color.WHITE);
        add(mainAddPanel);
        JPanelDateChooser.setBackground(Color.WHITE);
        calendar.setPreferredSize(new Dimension(100, 20));
        calendar.setDateFormatString("dd/MM/yyyy");
        JPanelDateChooser.add(calendar, BorderLayout.CENTER);

        GroupLayout layout = new GroupLayout(mainAddPanel); // Okno napisane za pomocą GroupLayout.
        mainAddPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Określamy wzajemne położenie obiektów w płaszczyźnie pionowej i poziomej.
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(infoLabel)
                        .addComponent(nameInfo)
                        .addComponent(nameTextField, 20, 20, 20)
                        .addComponent(nameErrorLabel)
                        .addComponent(wateringInfo)
                        .addComponent(wateringTextField, 20, 20, 20)
                        .addComponent(waterErrorLabel)
                        .addComponent(dateInfo)
                        .addComponent(JPanelDateChooser, 30, 30, 30)
                        .addComponent(dateErrorLabel)
                        .addComponent(addButton)
        );
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(infoLabel)
                                .addComponent(nameInfo)
                                .addComponent(nameTextField)
                                .addComponent(nameErrorLabel)
                                .addComponent(wateringInfo)
                                .addComponent(wateringTextField)
                                .addComponent(waterErrorLabel)
                                .addComponent(dateInfo)
                                .addComponent(JPanelDateChooser))
                        .addComponent(dateErrorLabel, GroupLayout.Alignment.CENTER)
                        .addComponent(addButton, GroupLayout.Alignment.TRAILING)
        );
    }


}
