package population;

import virus.Virus;
import java.util.Random;

/**
 * Class representing a population.
 * This class handles the attributes and behaviors of a population, including
 * infection simulation, border control, and medical development.
 */
public class Population {
    private int stepCounter = 0;
    private static int worldStepSick = 0;

    private static long worldPopulation = 0;
    private static long worldHealthy = 0;
    private static long worldInfected = 0;
    private static long worldCorpse = 0;
    private double infections;
    private String country;
    private double populationDensity;
    private int population;
    private int infected;
    private int healthy;
    private int stepSick;
    private int stepCorpse;
    private int corpse; // died
    private double stability;
    private double averageTemperature;
    private boolean borders;
    private double medicalLevel;
    private static final int COUNTRY_AREA = 10;
    private double transmissionProbability;
    private Random random = new Random();

    /**
     * Constructor initializes the population for a specified country with its population size and average temperature.
     *
     * @param country the name of the country
     * @param population the size of the population
     * @param averageTemperature the average temperature of the country
     */
    public Population(String country, int population, double averageTemperature) {
        worldPopulation += population;
        worldHealthy += population;
        this.country = country;
        this.population = population;
        this.populationDensity = (double) population / COUNTRY_AREA < 1000 ? 0.1 : (population / COUNTRY_AREA < 10000 ? 0.15 : 0.30);
        this.stability = random.nextDouble();
        this.averageTemperature = averageTemperature;
        this.medicalLevel = stability > 0.80 ? 1 : (stability > 0.50 ? 0.8 : stability > 0.20 ? 0.5 : 0.1);
        this.infected = 0;
        this.healthy = population;
        this.corpse = 0;
        this.stepSick = 0;
        this.stepCorpse = 0;
        this.borders = false;
        this.infections = 0.1;
        transmissionProbability = 0;
    }

    /**
     * Constructor initializes the population for a specified country.
     *
     * @param country the name of the country
     */
    public Population(String country) {
        this.country = country;
    }

    /**
     * Method to close the borders of the country.
     */
    private void closeBorders() {
        double bordersProbability = (worldInfected > 0 ? 1 : 0) * medicalLevel * stability - 0.20;
        bordersProbability = Math.max(Math.min(bordersProbability, 1), 0);
        double bordersRand = random.nextDouble();
        borders = bordersRand < bordersProbability;
    }

    /**
     * Method to simulate the infection spread for a single step.
     *
     * @param virus the virus affecting the population
     */
    public void simulateInfectionStep(Virus virus) {
        stepCounter += 1;
        if (stepCounter % 100 == 0 && !borders) {
            closeBorders();
        }
        if (infected > 0) {
            populationDensity = (double) (healthy + infected) / COUNTRY_AREA;
        }
        transmissionProbability = virus.getInfectionProbability() * 0.5 * populationDensity - medicalLevel - stability - (borders ? 0.05 : 0);
        transmissionProbability = Math.max(0, Math.min(transmissionProbability, 1));
    }

    /**
     * Gets the number of infections.
     *
     * @return the number of infections
     */
    public double getInfections() {
        return infections;
    }

    /**
     * Checks if the borders are closed.
     *
     * @return true if the borders are closed, false otherwise
     */
    public boolean isBorders() {
        return borders;
    }

    /**
     * Method for medical development (implementation not provided).
     */
    public void medicalDevelopment() {
        // Method implementation
    }

    /**
     * Sets the number of corpses.
     *
     * @param corpse the number of corpses
     */
    public void setCorpse(int corpse) {
        this.stepCorpse = corpse - this.corpse;
        worldCorpse += stepCorpse;
        worldInfected -= stepCorpse;
        this.infected -= stepCorpse;
        this.corpse = corpse;
    }

    /**
     * Sets the number of infected individuals.
     *
     * @param infected the number of infected individuals
     */
    public void setInfected(int infected) {
        this.stepSick = infected - this.infected;
        worldStepSick += stepSick;
        worldInfected += stepSick;
        worldHealthy -= stepSick;
        this.healthy -= stepSick;
        this.infected = infected;
    }

    /**
     * Gets the number of corpses in the current step.
     *
     * @return the number of corpses in the current step
     */
    public int getStepCorpse() {
        return stepCorpse;
    }

    /**
     * Sets the global number of infected individuals.
     *
     * @param infected the global number of infected individuals
     */
    public void setWorldInfected(long infected) {
        this.worldInfected = infected;
    }

    /**
     * Sets the global number of corpses.
     *
     * @param corpse the global number of corpses
     */
    public void setWorldCorpse(long corpse) {
        this.worldCorpse = corpse;
    }

    /**
     * Gets the number of infected individuals in the current step.
     *
     * @return the number of infected individuals in the current step
     */
    public int getStepSick() {
        return stepSick;
    }

    /**
     * Sets the average temperature of the country.
     *
     * @param averageTemperature the average temperature of the country
     */
    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    /**
     * Gets the total population.
     *
     * @return the total population
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Gets the number of infected individuals.
     *
     * @return the number of infected individuals
     */
    public int getInfected() {
        return infected;
    }

    /**
     * Gets the number of corpses.
     *
     * @return the number of corpses
     */
    public int getCorpse() {
        return corpse;
    }

    /**
     * Sets the border status of the country.
     *
     * @param borders the border status (true if closed, false otherwise)
     */
    public void setBorders(boolean borders) {
        this.borders = borders;
    }

    /**
     * Gets the global population.
     *
     * @return the global population
     */
    public long getWorldPopulation() {
        return worldPopulation;
    }

    /**
     * Gets the global number of infected individuals.
     *
     * @return the global number of infected individuals
     */
    public long getWorldInfected() {
        return worldInfected;
    }

    /**
     * Gets the global number of corpses.
     *
     * @return the global number of corpses
     */
    public long getWorldCorpse() {
        return worldCorpse;
    }

    /**
     * Gets the name of the country.
     *
     * @return the name of the country
     */
    public String getCountryName() {
        return country;
    }

    /**
     * Gets the number of healthy individuals.
     *
     * @return the number of healthy individuals
     */
    public int getHealthy() {
        return healthy;
    }

    /**
     * Gets the global number of healthy individuals.
     *
     * @return the global number of healthy individuals
     */
    public long getWorldHealthy() {
        return worldHealthy;
    }

    /**
     * Gets the global number of infected individuals in the current step.
     *
     * @return the global number of infected individuals in the current step
     */
    public int getWorldStepSick() {
        return worldStepSick;
    }

    /**
     * Resets the global step sick count to zero.
     */
    public void zeroWorldStepSick() {
        worldStepSick = 0;
    }
    /**
     * Retrieves the average temperature of the population.
     *
     * @return the average temperature
     */
    public double getAverageTemperature(){
        return averageTemperature;
    }

    /**
     * Redacts the global population based on the country status.
     *
     * @param isCountry true if adding population, false if subtracting population
     */
    public void redactWorldPopulation(boolean isCountry) {
        if (isCountry) {
            worldPopulation += population;
            worldHealthy += population;
        } else {
            worldPopulation -= population;
            worldHealthy -= population;
        }
    }

    /**
     * Resets all global population values to zero.
     */
    public void setZeroValues() {
        worldPopulation = 0;
        worldInfected = 0;
        worldCorpse = 0;
    }

    /**
     * Returns a string representation of the Population object.
     *
     * @return a string representation of the Population object
     */
    @Override
    public String toString() {
        return "Population{" +
                "infections=" + infections +
                ", country='" + country + '\'' +
                ", populationDensity=" + populationDensity +
                ", population=" + population +
                ", infected=" + infected +
                ", healthy=" + healthy +
                ", stepSick=" + stepSick +
                ", stepCorpse=" + stepCorpse +
                ", corpse=" + corpse +
                ", stability=" + stability +
                ", borders=" + borders +
                ", medicalLevel=" + medicalLevel +
                ", transmissionProbability=" + transmissionProbability +
                ", stepCounter=" + stepCounter +
                ", worldInfected=" + worldInfected +
                '}';
    }
}
