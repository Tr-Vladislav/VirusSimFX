package virus;

import java.util.ArrayList;

// Дочерний класс для вируса дыхательных путей
public class RespiratoryVirus extends Virus {
    // Конструктор
    public RespiratoryVirus(double incubationPeriod, double infectionProbability, double mortalityRate) {
        super("Respiratory", incubationPeriod, infectionProbability, mortalityRate);
    }

    // Переопределение метода для получения пути передачи вируса
    @Override
    public String getTransmissionRoute() {
        return "Airborne";
    }

    @Override
    public void mutation() {

    }

    @Override
    public double getInfectionProbability() {
        return 0;
    }

    public ArrayList<String> getSymptoms(){
        ArrayList<String> mas = new ArrayList<>();
        for(String sick: this.symptoms){
            mas.add(sick);
        }
        return mas;
    }
}
