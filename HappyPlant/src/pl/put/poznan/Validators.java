package pl.put.poznan;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Validators {

    static Integer getWatering(String text) throws InvalidWateringFormatException, InvalidWateringValueException {
        try {
            Integer watering = Integer.parseInt(text);
            if (watering < 1 || watering > 30) {
                throw new InvalidWateringValueException();
            }
            return watering;

        } catch (NumberFormatException expection) {
            throw new InvalidWateringFormatException();
        }
    }

    static String getName(String text, ArrayList<Plant> plants) throws EmptyNameBarException, RepeatedNameException {
        if (text.length() == 0)
            throw new EmptyNameBarException();
        for (Plant plant : plants) {
            if (plant.name.equals(text)) {
                throw new RepeatedNameException();
            }
        }
        return text;
    }

    static String getName2(String text, ArrayList<Plant> plants) throws EmptyNameBarException { // Używany przy aktualizowaniu danych,
        if (text.length() == 0)                                                                 // pozwala na powtarzanie nazwy rośliny.
            throw new EmptyNameBarException();
        return text;
    }

    static LocalDate getDate(Date date) throws EmptyDateChooserException {
        try {
            LocalDate newDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return newDate;
        } catch (NullPointerException exception) {
            throw new EmptyDateChooserException();
        }
    }

}
