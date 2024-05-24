package virus;

// Дочерний класс для пищевого вируса
class FoodborneVirus extends Virus {
    // Конструктор
    public FoodborneVirus(double incubationPeriod, double infectionProbability, double mortalityRate) {
        super("Foodborne", incubationPeriod, infectionProbability, mortalityRate);
    }

    // Переопределение метода для получения пути передачи вируса
    @Override
    public String getTransmissionRoute() {
        return "Foodborne";
    }

    @Override
    public void mutation() {

    }

    @Override
    public double getInfectionProbability() {
        return 0;
    }
}
