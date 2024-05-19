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
    private int corpse; //died
    private double stability;
    private double averageTemperature;
    private boolean borders;
    private double medicalLevel;



    // Конструктор
    public Population(String country, double populationDensity, int population, double stability, double averageTemperature, boolean borders, double medicalLevel) {
        worldPopulation+=population;
        this.country = country;
        this.populationDensity = populationDensity;
        this.population = population;
        this.stability = stability;
        this.averageTemperature = averageTemperature;
        this.borders = borders;
        this.medicalLevel = medicalLevel;
        this.infected = 0;
        this.stepSick = 0;
        this.infections = 0.1;
    }
    public Population(String country){
        this.country = country;
    }

    public double getInfections(){
        return infections;
    }
    // Метод для расчета одного шага симуляции заражения
    public void simulateInfectionStep(Virus virus) {
        Random random = new Random();

        // Рассчитываем вероятность передачи вируса текущему человеку
        //double transmissionProbability = virus.infectionProbability - medicalLevel - stability ;

        // Генерируем случайное число от 0 до 1
        double randomNumber = random.nextDouble();

    }


    public void medicalDevelopment() {

    }

    public void setInfected(int infected) {
        this.stepSick=infected-this.infected;
        worldInfected+=infected-this.infected;
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
