package pl.put.poznan;

import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class MyTableModel extends DefaultTableModel {
    String[] columnNames = {"Nazwa", "Ostatnie podlanie", "Następne podlanie", "Podlane dzisiaj"};

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Class getColumnClass(int column) {
        return switch (column) {
            case 0 -> String.class;
            case 1, 2 -> LocalDate.class;
            default -> Boolean.class;
        };
    }

    public boolean isCellEditable(int row, int column) {
        return column == 3;
    }
}
