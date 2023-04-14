package pl.put.poznan;

import java.io.Serializable;
import java.time.LocalDate;

public class Plant implements Serializable {
    String name;
    Integer watering;
    LocalDate lastWater;
    LocalDate nextWater;

    public Plant(String name, Integer watering, LocalDate lastWater) {
        this.name = name;
        this.watering = watering;
        this.lastWater = lastWater;
        nextWater = lastWater.plusDays(watering);
    }

    public Object[] getAsRow() {
        return new Object[] {name, lastWater, nextWater, false};
    }
}
