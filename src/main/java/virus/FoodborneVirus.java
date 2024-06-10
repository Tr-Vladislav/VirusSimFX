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
                case "Nausea":
                    mortality += 0.1;
                    break;
                case "Vomiting":
                    mortality += 0.05;
                    break;
                case "Diarrhea":
                    mortality += 0.1;
                    break;
                case "Abdominal pain":
                    mortality += 0.05;
                    break;
                case "Dehydration":
                    mortality += 0.05;
                    break;
                case "*Sepsis":
                    mortality += 0.25;
                    break;
                case "*Toxic hepatitis":
                    mortality += 0.25;
                    break;
                // Добавьте дополнительные случаи для других симптомов, если это необходимо
            }
        }

        return Math.min(mortality, 1.0);
    }

}
