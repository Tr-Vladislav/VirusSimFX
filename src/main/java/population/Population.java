package population;

import virus.Virus;

import java.util.Random;

// Класс для представления популяции
public class Population {
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
    private int corpse; //died
    private double stability;
    private double averageTemperature;
    private boolean borders;
    private double medicalLevel;
    private static final int COUNTRY_AREA = 10;
    private double transmissionProbability;
    Random random = new Random();



    // Конструктор
    public Population(String country, int population, double averageTemperature) {
        worldPopulation+=population;
        worldHealthy+=population;
        this.country = country;
        //this.population = random.nextInt() & Integer.MAX_VALUE;
        this.population = population;
        this.populationDensity = (double) population/COUNTRY_AREA < 1000 ? 0.1 : (population/COUNTRY_AREA < 10000 ? 0.15 : 0.30);
        this.stability = random.nextDouble();
        this.averageTemperature = averageTemperature;
        this.medicalLevel = stability > 0.80 ? 1 : (stability > 0.50 ? 0.8 : stability > 0.20 ? 0.5 : 0.1);
        this.infected = 0;
        this.healthy=population;
        this.corpse = 0;
        this.stepSick = 0;
        this.stepCorpse = 0;
        this.borders = false;
        this.infections = 0.1;
        transmissionProbability = 0;
    }

    public Population(String country) {
        this.country = country;
    }

    public void closeBorders(){
        double bordersProbability = (infected > 0 ? 1 : 0) * medicalLevel ;

    }

    // Метод для расчета одного шага симуляции заражения
    public void simulateInfectionStep(Virus virus) {
        population = population - stepCorpse;
        populationDensity = (double) population/COUNTRY_AREA < 1000 ? 0.1 : (population/COUNTRY_AREA < 10000 ? 0.15 : 0.30);
        medicalLevel = medicalLevel - corpse / population > 0.8 ? medicalLevel : (corpse / population > 50 ? medicalLevel * 0.7 : (corpse / population > 25 ? medicalLevel * 0.4 : corpse / population > 10 ? medicalLevel * 0.15 : 0));
        double calculateTransmissionProbability = virus.getInfectionProbability() + populationDensity - medicalLevel - stability - (borders ? 0.05 : 0);


        // Рассчитываем вероятность передачи вируса текущему человеку
        double transmissionProbability = calculateTransmissionProbability > 1 ? 1 : (calculateTransmissionProbability < 0 ? 0 : calculateTransmissionProbability);




    }

    public double getInfections(){
        return infections;
    }

    public void medicalDevelopment() {

    }
    public void setCorpse(int corpse) {
        this.stepCorpse=corpse-this.corpse;
        worldCorpse+=stepCorpse;
        worldInfected-=stepCorpse;
        this.infected-=stepCorpse;
        this.corpse = corpse;
    }
    public void setInfected(int infected) {
        this.stepSick=infected-this.infected;
        worldStepSick+=stepSick;
        worldInfected+=stepSick;
        worldHealthy-=stepSick;
        this.healthy-=stepSick;
        this.infected = infected;
    }
    public int getStepCorpse(){
        return stepCorpse;
    }
    public void setWorldInfected(long infected){
        this.worldInfected = infected;
    }
    public void setWorldCorpse(long corpse){
        this.worldCorpse = corpse;
    }
    public int getStepSick(){
        return stepSick;
    }
    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }
    public int getPopulation(){
        return population;
    }
    public int getInfected(){
        return infected;
    }
    public int getCorpse(){
        return corpse;
    }

    public void setBorders(boolean borders) {
        this.borders = borders;
    }
    public long getWorldPopulation(){
        return worldPopulation;
    }
    public long getWorldInfected(){
        return worldInfected;
    }
    public long getWorldCorpse(){
        return worldCorpse;
    }
    public String getCountryName(){
        return  country;
    }
    public int getHealthy(){return healthy;}
    public long getWorldHealthy(){return worldHealthy;}
    public int getWorldStepSick(){
        return worldStepSick;
    }
    public void zeroWorldStepSick(){
        worldStepSick=0;
    }
    public void redactWorldPopulation(boolean isCountry){
        if(isCountry){
            worldPopulation+=population;
            worldHealthy+=population;
        }else{
            worldPopulation-=population;
            worldHealthy-=population;
        }
    }
    public void setZeroValues(){
        worldPopulation = 0;
        worldInfected = 0;
        worldCorpse = 0;
    }

    @Override
    public String toString() {
        return "Population{" +
                "\ninfections=" + infections +
                ", \ncountry='" + country + '\'' +
                ", \npopulationDensity=" + populationDensity +
                ", \npopulation=" + population +
                ", \ninfected=" + infected +
                ", \nstepSick=" + stepSick +
                ", \nstepCorpse=" + stepCorpse +
                ", \ncorpse=" + corpse +
                ", \nstability=" + stability +
                ", \nborders=" + borders +
                ", \nmedicalLevel=" + medicalLevel +
                '}';
    }
}
