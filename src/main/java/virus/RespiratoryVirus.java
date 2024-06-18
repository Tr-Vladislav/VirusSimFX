package virus;

import java.util.ArrayList;

/**
 * Subclass for a respiratory virus.
 */
public class RespiratoryVirus extends Virus {

    /**
     * Constructor for RespiratoryVirus.
     *
     * @param incubationPeriod the incubation period of the virus
     * @param infectionProbability the probability of infection
     * @param mortalityRate the mortality rate
     * @param mutationSpeed the speed of mutation
     */
    public RespiratoryVirus(double incubationPeriod, double infectionProbability, double mortalityRate, int mutationSpeed) {
        super("Respiratory", incubationPeriod, infectionProbability, mortalityRate, mutationSpeed);
    }

    /**
     * Override method to get the transmission route of the virus.
     *
     * @return the transmission route as a String
     */
    @Override
    public String getTransmissionRoute() {
        return "Airborne";
    }



    /**
     * Method to set all parameters of the virus.
     *
     * @param incubationPeriod the incubation period of the virus
     * @param infectionProbability the probability of infection
     * @param mortalityRate the mortality rate
     * @param mutationSpeed the speed of mutation
     * @param symptoms the list of symptoms
     * @param symptomsHard the list of severe symptoms
     */
    public void setAllparam(double incubationPeriod, double infectionProbability, double mortalityRate, int mutationSpeed, String[] symptoms, String[] symptomsHard) {
        setCharacteristics(incubationPeriod, infectionProbability, mortalityRate, mutationSpeed);
        setSymptoms(symptoms, symptomsHard);
    }

    /**
     * Method to calculate the infectivity of the virus.
     *
     * @return the calculated infectivity
     */
    public double calculateInfectivity() {
        double infectivity = getInfectionProbability();

        // Adjust infectivity based on active symptoms
        /*for (String symptom : activeSymptoms) {
            switch (symptom) {
                case "Cough":
                    infectivity += 0.2;
                    break;
                case "Shortness of Breath":
                    infectivity += 0.1;
                    break;
                // Add additional cases for other symptoms if needed
            }
        }*/

        infectivity *= (getIncubationPeriod() / 14.0);
        return Math.min(infectivity / 10, 1.0);
    }

    /**
     * Method to calculate the mortality of the virus.
     *
     * @return the calculated mortality
     */
    public double calculateMortality() {
        double mortality = 0.0;

        // Adjust mortality based on active symptoms
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
                // Add additional cases for other symptoms if needed
            }
        }

        return mortality;
    }
}
