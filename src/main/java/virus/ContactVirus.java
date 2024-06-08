package virus;

// Дочерний класс для контактного вируса
public class ContactVirus extends Virus {
    // Конструктор
    public ContactVirus(double incubationPeriod, double infectionProbability, double mortalityRate, int mutationSpeed) {
        super("Contact", incubationPeriod, infectionProbability, mortalityRate, mutationSpeed);
    }

    // Переопределение метода для получения пути передачи вируса
    @Override
    public String getTransmissionRoute() {
        return "Contact";
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
