package virus;

// Дочерний класс для пищевого вируса
public class FoodborneVirus extends Virus {
    // Конструктор
    public FoodborneVirus(double incubationPeriod, double infectionProbability, double mortalityRate, int mutationSpeed) {
        super("Foodborne", incubationPeriod, infectionProbability, mortalityRate, mutationSpeed);
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
    public double calculateMortality() {
        return 0;
    }

    @Override
    public double calculateInfectivity() {
        return 0;
    }

    @Override
    public double getInfectionProbability() {
        return 0;
    }
}
