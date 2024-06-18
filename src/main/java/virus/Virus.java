package virus;

import java.util.ArrayList;

/**
 * Base class representing a virus.
 * This class serves as an abstract base for different types of viruses,
 * managing common properties such as incubation period, infection probability,
 * mortality rate, and mutation characteristics.
 */
public abstract class Virus {
    private boolean heatResistance = false;
    private boolean coldResistance = false;
    private int cntSymptoms = 0;
    protected String[] symptoms;
    protected String[] symptomsHard;
    public ArrayList<String> activeSymptoms;

    private String type;
    private double incubationPeriod;
    private double infectionProbability;
    private double mortalityRate;
    private boolean mutation;
    private int mutationSpeed;

    /**
     * Constructor for Virus.
     *
     * @param type the type of the virus
     * @param incubationPeriod the incubation period of the virus
     * @param infectionProbability the probability of infection
     * @param mortalityRate the mortality rate
     * @param mutationSpeed the speed of mutation
     */
    public Virus(String type, double incubationPeriod, double infectionProbability, double mortalityRate, int mutationSpeed) {
        this.type = type;
        this.incubationPeriod = incubationPeriod;
        this.infectionProbability = infectionProbability;
        this.mortalityRate = mortalityRate;
        this.mutationSpeed = mutationSpeed;
        mutation = false;
        activeSymptoms = new ArrayList<>();
    }

    /**
     * Method to set the characteristics of the virus.
     *
     * @param incubationPeriod the incubation period of the virus
     * @param infectionProbability the probability of infection
     * @param mortalityRate the mortality rate
     * @param mutationSpeed the speed of mutation
     */
    public void setCharacteristics(double incubationPeriod, double infectionProbability, double mortalityRate, int mutationSpeed) {
        this.incubationPeriod = incubationPeriod;
        this.infectionProbability = infectionProbability;
        this.mortalityRate = mortalityRate;
        this.mutationSpeed = mutationSpeed;
    }

    /**
     * Abstract method to get the transmission route of the virus.
     *
     * @return the transmission route as a String
     */
    public abstract String getTransmissionRoute();

    /**
     * Abstract method to calculate the mortality of the virus.
     *
     * @return the calculated mortality
     */
    public abstract double calculateMortality();

    /**
     * Abstract method to calculate the infectivity of the virus.
     *
     * @return the calculated infectivity
     */
    public abstract double calculateInfectivity();

    /**
     * Clears the active symptoms of the virus.
     */
    public void clearActiveSymptoms() {
        activeSymptoms = new ArrayList<String>();
    }

    /**
     * Gets the incubation period of the virus.
     *
     * @return the incubation period
     */
    public double getIncubationPeriod() {
        return incubationPeriod;
    }

    /**
     * Method to set the symptoms of the virus.
     *
     * @param symptoms the list of symptoms
     * @param symptomsHard the list of severe symptoms
     */
    public void setSymptoms(String[] symptoms, String[] symptomsHard) {
        this.symptoms = symptoms;
        this.symptomsHard = symptomsHard;
    }

    /**
     * Method to get the active symptoms of the virus.
     *
     * @return the list of active symptoms
     */
    public ArrayList<String> getActiveSymptoms() {
        return activeSymptoms;
    }

    /**
     * Method to add an active symptom based on infection probability.
     *
     * @param inf the infection probability
     */
    public void addActiveSymptom(double inf) {
        if (cntSymptoms <= symptoms.length - 1) {
            System.out.println(String.valueOf(cntSymptoms) + " " + symptoms.length);
            if (inf > 0.40) {
                activeSymptoms.add(symptoms[cntSymptoms]);
                cntSymptoms++;
            }
        }
    }

    /**
     * Method to mutate the virus, making it more dangerous.
     */
    public void mutateVirus() {
        if (!mutation) {
            mutation = true;
            mortalityRate += 0.1;
            infectionProbability += 0.1;
        }
    }

    /**
     * Method to reset the mutation status of the virus.
     */
    public void resetMutation() {
        mutation = false;
    }

    /**
     * Checks if the virus has mutated.
     *
     * @return true if the virus has mutated, false otherwise
     */
    public boolean isMutated() {
        return mutation;
    }

    /**
     * Gets the type of the virus.
     *
     * @return the type of the virus
     */
    public String getVirusType() {
        return type;
    }

    /**
     * Gets the infection probability of the virus.
     *
     * @return the infection probability
     */
    public double getInfectionProbability() {
        return infectionProbability;
    }

    /**
     * Gets the mortality rate of the virus.
     *
     * @return the mortality rate
     */
    public double getMortalityRate() {
        return mortalityRate;
    }

    /**
     * Gets the mutation speed of the virus.
     *
     * @return the mutation speed
     */
    public int getMutationSpeed() {
        return mutationSpeed;
    }
    /**
     * Checks if the virus has resistance to cold temperatures.
     *
     * @return true if the virus is resistant to cold, false otherwise
     */
    public boolean getColdResistance() {
        return coldResistance;
    }

    /**
     * Checks if the virus has resistance to heat temperatures.
     *
     * @return true if the virus is resistant to heat, false otherwise
     */
    public boolean getHeatResistance() {
        return heatResistance;
    }

    /**
     * Sets the virus to be resistant to cold temperatures.
     */
    public void setColdResistance() {
        coldResistance = true;
    }

    /**
     * Sets the virus to be resistant to heat temperatures.
     */
    public void setHeatResistance() {
        heatResistance = true;
    }
}
