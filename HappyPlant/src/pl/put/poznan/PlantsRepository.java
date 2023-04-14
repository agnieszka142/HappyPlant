package pl.put.poznan;

import java.io.*;
import java.util.ArrayList;

public class PlantsRepository {
    final FileHandling handler;

    public PlantsRepository(FileHandling handler) {
        this.handler = handler;
    }

    void saveToFile(ArrayList<Plant> plants) throws CannotSaveToFileException {
        try {
            handler.saveToFile(plants);
        } catch (IOException e) {
            throw new CannotSaveToFileException();
        }
    }

    ArrayList<Plant> readFromFile() {
        try {
            return handler.readFromFile();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<Plant>();
        }
    }
}
