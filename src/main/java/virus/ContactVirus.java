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
                    mortality += 0.1;
                    break;
                case "Nausea":
                    mortality += 0.1;
                    break;
                case "Vomiting":
                    mortality += 0.1;
                    break;
                case "Rash":
                    mortality += 0.05;
                    break;
                case "Abdominal pain":
                    mortality += 0.05;
                    break;
                case "*Paralysis":
                    mortality += 0.2;
                    break;
                case "*Coma":
                    mortality += 0.2;
                    break;
                case "*Sepsis":
                    mortality += 0.2;
                    break;
                // Добавьте дополнительные случаи для других симптомов, если это необходимо
            }
        }

        return Math.min(mortality, 1.0);
    }

}
