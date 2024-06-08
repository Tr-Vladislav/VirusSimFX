package virus;

import java.util.ArrayList;

// Материнский класс для представления вируса
public abstract class Virus {
    private int cntSymptoms=0;
    private String[] symptoms;
    private String[] symptomsHard;
    public ArrayList<String> activeSymptoms;

    private String type;
    private double incubationPeriod;
    private double infectionProbability;
    private double mortalityRate;
    private boolean mutation;
    private int mutationSpeed;

    // Конструктор
    public Virus(String type, double incubationPeriod, double infectionProbability, double mortalityRate, int mutationSpeed) {
        this.type = type;
        this.incubationPeriod = incubationPeriod;
        this.infectionProbability = infectionProbability;
        this.mortalityRate = mortalityRate;
        this.mutationSpeed = mutationSpeed;
        mutation = false;
        activeSymptoms = new ArrayList<>();
    }

    // Метод для настройки характеристик вируса
    public void setCharacteristics(double incubationPeriod, double infectionProbability, double mortalityRate, int mutationSpeed) {
        this.incubationPeriod = incubationPeriod;
        this.infectionProbability = infectionProbability;
        this.mortalityRate = mortalityRate;
        this.mutationSpeed = mutationSpeed;
    }

    // Переопределение метода для получения пути передачи вируса
    public abstract String getTransmissionRoute();
    public abstract void mutation();
    public abstract double calculateMortality();
    public abstract double calculateInfectivity();

    public double getIncubationPeriod() {
        return incubationPeriod;
    }
    public void setSymptoms(String[] symptoms, String[] symptomsHard){
        this.symptoms = symptoms;
        this.symptomsHard = symptomsHard;
    }
    public ArrayList<String> getActiveSymptoms(){
        return activeSymptoms;
    }
    public void addActiveSymptom(){
        if(cntSymptoms<symptoms.length-1){

            activeSymptoms.add(symptoms[cntSymptoms]);
            cntSymptoms+=1;
        }
    }
    public int getMutationSpeed(){
        return mutationSpeed;
    }
    public void setInfectionProbability( double infectionProbability){
        this.infectionProbability = infectionProbability;

    }
    public double getInfectionProbability(){
        return infectionProbability;
    }

}

// Дочерний класс для других типов вирусов
// Можно создать дополнительные классы для других видов вируса по аналогии с вышеуказанными классами

