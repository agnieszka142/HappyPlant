package pl.put.poznan;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Interface extends JFrame {
    private final PlantsRepository repository;
    private JPanel mainPanel;
    private JLabel logo;
    private JButton addButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton waterButton;
    private JScrollPane scrollPane;
    private JTable collectionTable;
    private JLabel reminderLabel;
    private LocalDate today;
    ArrayList<Plant> plants = new ArrayList<>();
    public MyTableModel model = new MyTableModel();
    int allWatered = -1;
    int reads = 0;

    public Interface(PlantsRepository repository) {
        this.repository = repository;
        setupFrame();
        addButton.addActionListener(e -> addNewPlant());
        loadButton.addActionListener(e -> loadDatabase());
        saveButton.addActionListener(e -> saveChanges());
        waterButton.addActionListener(e -> waterSelected());
        deleteButton.addActionListener(e -> deleteSelected());
        updateButton.addActionListener(e -> updatePlant());
    }

    public void setupFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(mainPanel);
        setLocation(dim.width/2 - 350, dim.height/2 - 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700,500));
        setTitle("HappyPlant");
        setVisible(true);

        collectionTable.setModel(model);
        model.setColumnCount(4);
        JTableHeader header = collectionTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));

        today = LocalDate.now();
    }

    public void addNewPlant() { // Dodawanie rośliny, powoduje pojawienie się nowego okna, w którym wprowadzamy dane.
        Adder adder = new Adder("Dodawanie rośliny");
        adder.addButton.addActionListener(e -> getPlant(adder, -1));

    }

    public void getPlant(Adder adder, int updatedRow) { // Akcja na przycisku dodaj.
        String name = null;                              // Sprawdza poprawność wprowadzonych dannych i dodaje roślinę do bazy.
        Integer watering = null;                         // Zmienna updatedRow wskazuje, czy dodajemy nową roślinę, czy aktualizujemy już obecną,
        LocalDate lastWater = null;                         // co zmienia nieco działanie funkcji.

        try {
            if (updatedRow >= 0) {
                name = Validators.getName2(adder.nameTextField.getText(), plants);
            }
            else {
                name = Validators.getName(adder.nameTextField.getText(), plants);
            }
            adder.nameErrorLabel.setText("");
        } catch (HappyPlantExceptions e) {
            adder.nameErrorLabel.setText(e.toString());
        }

        try {
            watering = Validators.getWatering(adder.wateringTextField.getText());
            adder.waterErrorLabel.setText("");
        } catch (HappyPlantExceptions e) {
            adder.waterErrorLabel.setText(e.toString());
        }

        try {
            lastWater = Validators.getDate(adder.calendar.getDate());
            adder.dateErrorLabel.setText("");
        } catch (EmptyDateChooserException e) {
            adder.dateErrorLabel.setText(e.toString());
        }

        if (watering != null && name != null && lastWater != null) {
            Plant plant = new Plant(name, watering, lastWater);
            if (updatedRow >= 0) {
                deleteFromTable(updatedRow);
                addPlantToTable(plant, updatedRow);
            }
            else {
                addPlantToTable(plant, model.getRowCount());
            }
            inform();
            adder.dispose();
        }
    }

    public void loadDatabase() { // Pozwala na wczytanie bazy raz, lub gdy usuniemy wszystkie rośliny.
        if (reads == 0) {
            int duplicated = 0;
            int canAdd;
            for(Plant newPlant : repository.readFromFile()){
                canAdd = 1;
                for (Plant oldPlant : plants) {
                    if (newPlant.name.equals(oldPlant.name)) {   // Jeżeli rośilna o danej nazwie znajduje się w bazie, nie jest dodawana.
                        duplicated = 1;
                        canAdd = 0;
                        break;
                    }
                }
                if (canAdd == 1)
                    plants.add(newPlant);
            }

            int startingLength = model.getRowCount();
            int finalLength = plants.size();
            for (int i = startingLength; i < finalLength; i++) {
                model.addRow(plants.get(i).getAsRow());
            }

            if (duplicated == 1) {
                JOptionPane.showMessageDialog(this, "Zduplikowane rośliny nie zostały wczytane.");
            }

            reads += 1;
            inform();
        }
    }

    public void saveChanges() {
        try {
            repository.saveToFile(plants);
        } catch (CannotSaveToFileException e) {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }

    public void waterSelected() { // Zmienia datę ostatniego podlania wszystkich roślin, które mają zanaczone pole wyboru w ostatniej kolumnie.
        int rows = collectionTable.getRowCount();
        for (int i = 0; i < rows; i++) {
            boolean isChecked = Boolean.parseBoolean(collectionTable.getValueAt(i, 3).toString());
            if (isChecked) {
                Plant oldPlant = plants.get(i);
                Plant wateredPlant = new Plant(oldPlant.name, oldPlant.watering, today);
                deleteFromTable(i);
                addPlantToTable(wateredPlant, i);
            }
        }
        inform();
    }

    public void deleteSelected() {
        int selected = collectionTable.getSelectedRow();
        if (selected < 0) { return; }
        deleteFromTable(selected);
        inform();
    }

    public void updatePlant() { // Otwiera okno pozwalające na zmianę danych zaznaczonej rośliny.
        int selected = collectionTable.getSelectedRow();
        if (selected < 0) { return; }

        Plant oldPlant = plants.get(selected); // Uzupełnia formularz obecnymi danymi, żeby nie trzeba było na nowo wprowadzać informacji,
        Adder updater = new Adder("Aktualizowanie informacji o roślinie");                      // które mają pozostać takie same.
        updater.nameTextField.setText(oldPlant.name);
        updater.wateringTextField.setText(oldPlant.watering.toString());
        Date date = Date.from(oldPlant.lastWater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        updater.calendar.setDate(date);

        updater.addButton.addActionListener(e -> getPlant(updater, selected));
    }

    public void deleteFromTable(int row) {
        model.removeRow(row);
        plants.remove(row);
    }

    public void addPlantToTable(Plant plant, int index) {
        plants.add(index, plant);
        model.insertRow(index, plant.getAsRow());
    }

    public void inform() { // Informuje o konieczności podlania roślin.
        if (model.getRowCount() == 0) {
            reads = 0;
            allWatered = -1;
            reminderLabel.setText("");
        }
        if (model.getRowCount() != 0) {
            allWatered = 1;
        }
        for (Plant plant : plants) {
            if (plant.nextWater.isBefore(today) || plant.nextWater.isEqual(today)) {
                reminderLabel.setText("Niektóre rośliny wymagają podlania!");
                reminderLabel.setFont(new Font("Arial", Font.BOLD, 12));
                reminderLabel.setForeground(Color.RED);
                allWatered = 0;
            }
        }
        if (allWatered == 1) {
            reminderLabel.setText("Wszystkie rośliny są podlane!");
            reminderLabel.setFont(new Font("Arial", Font.BOLD, 12));
            reminderLabel.setForeground(Color.GREEN);
        }
    }

}
