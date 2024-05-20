package population;





import virus.Virus;

import java.util.Random;

// Класс для представления популяции
public class Population {

    private static int worldPopulation=0;
    private static int worldInfected=0;
    private static int worldCorpse = 0;
    private double infections;
    private String country;
    private double populationDensity;
    private int population;
    private int infected;
    private int stepSick;
    private int stepCorpse;
    private int corpse; //died
    private double stability;
    private double averageTemperature;
    private boolean borders;
    private double medicalLevel;
    private static final int COUNTRY_AREA = 10;
    Random random = new Random();



    // Конструктор
    public Population(String country, double populationDensity, int population, double stability, double averageTemperature, boolean borders, double medicalLevel) {
        worldPopulation+=population;
        this.country = country;
        this.population = random.nextInt() & Integer.MAX_VALUE;
        this.populationDensity = (double) population/COUNTRY_AREA < 1000 ? 0.1 : (population/COUNTRY_AREA < 10000 ? 0.15 : 0.30);
        this.stability = random.nextDouble();
        this.averageTemperature = averageTemperature;
        this.medicalLevel = stability > 0.80 ? 1 : (stability > 50 ? 0.8 : stability > 20 ? 0.5 : 0.1);
        this.infected = 0;
        this.corpse = 0;
        this.stepSick = 0;
        this.borders = false;
    }

    public Population(String country) {
        this.country = country;
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

    public void setInfected(int infected) {
        this.stepSick=infected-this.infected;
        this.infected = infected;
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
    public int getWorldPopulation(){
        return worldPopulation;
    }
    public int getWorldInfected(){
        return worldInfected;
    }
    public int getWorldCorpse(){
        return worldCorpse;
    }
    public String getCountryName(){
        return  country;
    }
    public void redactWorldPopulation(boolean isCountry){
        if(isCountry){
            worldPopulation+=population;
        }else{
            worldPopulation-=population;
        }
    }

}
