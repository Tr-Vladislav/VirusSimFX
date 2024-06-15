package population;

import virus.Virus;
import java.util.Random;

/**
 * Class representing a population.
 */
public class Population {
    private int stepCounter = 0;
    private static int infectedCountry = 0;
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
    double bordersProbability;

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
        bordersProbability = (worldInfected > 0 ? 1 : 0) * medicalLevel * stability - 0.20;
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

    public double getInfections() {
        return infections;
    }

    public boolean isBorders() {
        return borders;
    }

    public void medicalDevelopment() {
        // Method implementation
    }

    /**
     * Method to set the number of corpses.
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
     * Method to set the number of infected individuals.
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

    public int getStepCorpse() {
        return stepCorpse;
    }

    public void setWorldInfected(long infected) {
        this.worldInfected = infected;
    }

    public void setWorldCorpse(long corpse) {
        this.worldCorpse = corpse;
    }

    public int getStepSick() {
        return stepSick;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public int getPopulation() {
        return population;
    }

    public int getInfected() {
        return infected;
    }

    public int getCorpse() {
        return corpse;
    }

    public void setBorders(boolean borders) {
        this.borders = borders;
    }

    public long getWorldPopulation() {
        return worldPopulation;
    }

    public long getWorldInfected() {
        return worldInfected;
    }

    public long getWorldCorpse() {
        return worldCorpse;
    }

    public String getCountryName() {
        return country;
    }

    public int getHealthy() {
        return healthy;
    }

    public long getWorldHealthy() {
        return worldHealthy;
    }

    public int getWorldStepSick() {
        return worldStepSick;
    }

    public void zeroWorldStepSick() {
        worldStepSick = 0;
    }

    public void redactWorldPopulation(boolean isCountry) {
        if (isCountry) {
            worldPopulation += population;
            worldHealthy += population;
        } else {
            worldPopulation -= population;
            worldHealthy -= population;
        }
    }

    public void setZeroValues() {
        worldPopulation = 0;
        worldInfected = 0;
        worldCorpse = 0;
    }

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
                ", boardersProbability" + bordersProbability +
                '}';
    }
}
