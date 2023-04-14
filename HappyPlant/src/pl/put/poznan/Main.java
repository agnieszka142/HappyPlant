package pl.put.poznan;

public class Main {

    public static void main(String[] args) {
        FileHandling handler = new FileHandling("database.txt");
        PlantsRepository repository = new PlantsRepository(handler);
        Interface HappyPlant = new Interface(repository);
    }
}
