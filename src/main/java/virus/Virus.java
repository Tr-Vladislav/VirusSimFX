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

    public double getIncubationPeriod() {
        return incubationPeriod;
    }
}

// Дочерний класс для других типов вирусов
// Можно создать дополнительные классы для других видов вируса по аналогии с вышеуказанными классами

