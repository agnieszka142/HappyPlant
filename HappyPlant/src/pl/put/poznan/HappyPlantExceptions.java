package pl.put.poznan;

abstract public class HappyPlantExceptions extends Exception {}

class RepeatedNameException extends HappyPlantExceptions {
    @Override
    public String toString() {
        return "Roślina o podanej nazwie znajduje się już w bazie!";
    }
}

class EmptyNameBarException extends HappyPlantExceptions {
    @Override
    public String toString() {
        return "Wprowadź nazwę rośliny!";
    }
}

class InvalidWateringFormatException extends HappyPlantExceptions {
    @Override
    public String toString() { return "Wprowadź liczbę całkowitą!"; }
}

class InvalidWateringValueException extends HappyPlantExceptions {
    @Override
    public String toString() {
        return "Wprowadź wartość od 1 do 30!";
    }
}

class EmptyDateChooserException extends HappyPlantExceptions {
    @Override
    public String toString() {
        return "Podaj datę ostatniego podlania!";
    }
}

class CannotSaveToFileException extends HappyPlantExceptions {
    @Override
    public String toString() { return "Nie udało sie zapisać do pliku."; }
}

