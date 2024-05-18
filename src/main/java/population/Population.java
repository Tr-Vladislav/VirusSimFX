package population;





import virus.Virus;

import java.util.Random;

// Класс для представления популяции
public class Population {
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
        this.country = country;
        this.populationDensity = populationDensity;
        this.population = population;
        this.stability = stability;
        this.averageTemperature = averageTemperature;
        this.borders = borders;
        this.medicalLevel = medicalLevel;
        this.infected = 0;
        this.stepSick = 0;
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

}
