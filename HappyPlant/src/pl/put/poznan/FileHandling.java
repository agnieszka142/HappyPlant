package pl.put.poznan;

import java.io.*;
import java.util.ArrayList;

public class FileHandling {
    final String path;

    public FileHandling(String path) {
        this.path = path;
    }

    void saveToFile(ArrayList<Plant> plants) throws IOException {
        ObjectOutputStream writer = null;
        try {
            writer = new ObjectOutputStream(new FileOutputStream(path));
            writer.writeObject(plants);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    ArrayList<Plant> readFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream reader = null;
        try {
            reader = new ObjectInputStream(new FileInputStream(path));
            ArrayList<Plant> plants = (ArrayList<Plant>) reader.readObject();
            return plants;
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
