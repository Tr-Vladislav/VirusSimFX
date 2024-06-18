package virus;

import java.util.ArrayList;

/**
 * Base class representing a virus.
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
     * Abstract method to handle virus mutation.
     */
    public abstract void mutation();

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

    public void clearActiveSymptoms(){
        activeSymptoms = new ArrayList<String>();
    }
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

    public boolean isMutated() {
        return mutation;
    }

    public String getVirusType() {
        return type;
    }

    public double getInfectionProbability() {
        return infectionProbability;
    }

    public double getMortalityRate() {
        return mortalityRate;
    }

    public int getMutationSpeed() {
        return mutationSpeed;
    }
    public boolean getColdResistance(){
        return coldResistance;
    }
    public boolean getHeatResistance(){
        return heatResistance;
    }
    public void setColdResistance(){
        coldResistance = true;
    }
    public void setHeatResistance(){
        heatResistance = true;
    }
}
