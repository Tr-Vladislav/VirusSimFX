package virus;

// Материнский класс для представления вируса
public abstract class Virus {
    public String[] symptoms = {
            "Fever",
            "Cough",
            "Shortness of breath",
            "Fatigue",
            "Muscle or body aches",
            "Headache",
            "Loss of taste or smell",
            "Sore throat",
            "Congestion or runny nose",
            "Nausea or vomiting",
            "Diarrhea"
    };
    private String type;
    private double incubationPeriod;
    private double infectionProbability;
    private double mortalityRate;

    // Конструктор
    public Virus(String type, double incubationPeriod, double infectionProbability, double mortalityRate) {
        this.type = type;
        this.incubationPeriod = incubationPeriod;
        this.infectionProbability = infectionProbability;
        this.mortalityRate = mortalityRate;
    }

    // Метод для настройки характеристик вируса
    public void setCharacteristics(double incubationPeriod, double infectionProbability, double mortalityRate) {
        this.incubationPeriod = incubationPeriod;
        this.infectionProbability = infectionProbability;
        this.mortalityRate = mortalityRate;
    }

    // Переопределение метода для получения пути передачи вируса
    public abstract String getTransmissionRoute();
    public abstract void mutation();
    public abstract double getInfectionProbability();

    public boolean isIncubationPeriod() {
        return true;
    }
}

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

// Дочерний класс для других типов вирусов
// Можно создать дополнительные классы для других видов вируса по аналогии с вышеуказанными классами

