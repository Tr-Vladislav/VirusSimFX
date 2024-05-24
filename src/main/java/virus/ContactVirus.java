package virus;

// Дочерний класс для контактного вируса
class ContactVirus extends Virus {
    // Конструктор
    public ContactVirus(double incubationPeriod, double infectionProbability, double mortalityRate) {
        super("Contact", incubationPeriod, infectionProbability, mortalityRate);
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
    public double getInfectionProbability() {
        return 0;
    }

}
