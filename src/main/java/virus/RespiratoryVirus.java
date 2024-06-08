package virus;

import java.util.ArrayList;

// Дочерний класс для вируса дыхательных путей
public class RespiratoryVirus extends Virus {
    // Конструктор
    public RespiratoryVirus(double incubationPeriod, double infectionProbability, double mortalityRate, int mutationSpeed) {
        super("Respiratory", incubationPeriod, infectionProbability, mortalityRate, mutationSpeed);

    }
    // Переопределение метода для получения пути передачи вируса
    @Override
    public String getTransmissionRoute() {
        return "Airborne";
    }

    @Override
    public void mutation() {

    }



    public void setAllparam(double incubationPeriod, double infectionProbability, double mortalityRate,int mutationSpeed, String[] symptoms, String[] symptomsHard){
        setCharacteristics(incubationPeriod,infectionProbability,mortalityRate, mutationSpeed);
        setSymptoms(symptoms, symptomsHard);
    }
    public double calculateInfectivity() {
        double infectivity = getInfectionProbability();
        /*for (String symptom : activeSymptoms) {

            switch (symptom) {
                case "Cough":
                    infectivity += 0.2;
                    break;
                case "Shortness of Breath":
                    infectivity += 0.1;
                    break;
                // Добавьте дополнительные случаи для других симптомов, если это необходимо
            }
        }*/

        infectivity *= (getIncubationPeriod()/ 14.0);
        return Math.min(infectivity/10, 1.0);
    }

    public double calculateMortality() {
        double mortality = 0.0;

        for (String symptom : activeSymptoms) {
            switch (symptom) {
                case "Fever":
                    mortality += 0.15;
                    break;
                case "Fatigue":
                    mortality += 0.1;
                    break;
                case "Headache":
                    mortality += 0.05;
                    break;
                case "Sore Throat":
                    mortality += 0.1;
                    break;
                case "Shortness of Breath":
                    mortality += 0.1;
                    break;
                case "Pulmonary acidosis":
                    mortality += 0.25;
                    break;
                case "Cerebral edema":
                    mortality += 0.25;
                    break;
                // Добавьте дополнительные случаи для других симптомов, если это необходимо
            }
        }

        return Math.min(mortality, 1.0);
    }

}
