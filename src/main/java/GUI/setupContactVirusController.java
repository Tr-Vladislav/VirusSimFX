package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import virus.ContactVirus;
import virus.RespiratoryVirus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class setupContactVirusController {
    @FXML
    private CheckBox abdominalPainCheckBox;

    @FXML
    private CheckBox antiviralDrugsResistanceCheckBox;

    @FXML
    private CheckBox bodilyFluidsCheckBox;

    @FXML
    private CheckBox comaCheckBox;

    @FXML
    private CheckBox contaminatedObjectsCheckBox;

    @FXML
    private CheckBox disinfectantsResistanceCheckBox;

    @FXML
    private CheckBox feverCheckBox;

    @FXML
    private Slider incubationSlider;

    @FXML
    private ProgressBar infectivityBar;

    @FXML
    private ProgressBar mortalityBar;

    @FXML
    private CheckBox nauseaCheckBox;

    @FXML
    private Button nextButton;

    @FXML
    private CheckBox pHchangesResistanceCheckBox;

    @FXML
    private CheckBox paralysisCheckBox;

    @FXML
    private CheckBox physicalContactCheckBox;

    @FXML
    private ProgressBar resistanceBar;

    @FXML
    private CheckBox rushCheckBox;

    @FXML
    private CheckBox sepsisCheckBox;

    @FXML
    private CheckBox vomitingCheckBox;

    private String[] selectedSymptoms;
    ContactVirus virus = new ContactVirus(0,0,0,0);

    @FXML
    public void initialize() {
    }
    @FXML
    public void startSimulation(){
        virus.setAllparam(incubationSlider.getValue(),calculateInfectivity(), calculateMortality() ,30, getSelectedSymptoms(),getSelectedSymptoms());
        nextButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("main.fxml"));


        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainController mainController = loader.getController();
        mainController.setVirus(virus);
        if(mainController == null){
            System.out.println("Error");
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.showAndWait();
    }


    public ContactVirus getVirus(){
        return virus;
    }

    @FXML
    private void updateSimulation() {
        selectedSymptoms = getSelectedSymptoms();
        double infectivity = calculateInfectivity();
        double mortality = calculateMortality();
        double resistance = calculateResistance();

        infectivityBar.setProgress(infectivity);
        mortalityBar.setProgress(mortality);
        resistanceBar.setProgress(resistance);
    }

    private String[] getSelectedSymptoms() {
        List<String> symptoms = new ArrayList<>();
        if (feverCheckBox.isSelected()) symptoms.add("Fever");
        if (nauseaCheckBox.isSelected()) symptoms.add("Nausea");
        if (vomitingCheckBox.isSelected()) symptoms.add("Vomiting");
        if (rushCheckBox.isSelected()) symptoms.add("Rush");
        if (abdominalPainCheckBox.isSelected()) symptoms.add("Abdominal Pain");
        if (paralysisCheckBox.isSelected()) symptoms.add("*Paralysis");
        if (comaCheckBox.isSelected()) symptoms.add("*Coma");
        if (sepsisCheckBox.isSelected()) symptoms.add("*Sepsis");
        return symptoms.toArray(new String[0]);
    }

    private double calculateInfectivity() {
        double infectivity = 0.0;
        if (physicalContactCheckBox.isSelected()) infectivity += 0.4;
        if (bodilyFluidsCheckBox.isSelected()) infectivity += 0.3;
        if (contaminatedObjectsCheckBox.isSelected()) infectivity += 0.3;

        for (String symptom : selectedSymptoms) {
            switch (symptom) {
                case "Rash":
                    infectivity += 0.2;
                    break;
                case "Shortness of Breath":
                    infectivity += 0.1;
                    break;

            }
        }

        infectivity *= (incubationSlider.getValue() / 14.0);
        return Math.min(infectivity, 1.0);
    }

    private double calculateMortality() {
        double mortality = 0.0;

        for (String symptom : selectedSymptoms) {
            switch (symptom) {
                case "Fever":
                    mortality += 0.1;
                    break;
                case "Nausea":
                    mortality += 0.1;
                    break;
                case "Vomiting":
                    mortality += 0.1;
                    break;
                case "Rash":
                    mortality += 0.05;
                    break;
                case "Abdominal pain":
                    mortality += 0.05;
                    break;
                case "*Paralysis":
                    mortality += 0.2;
                    break;
                case "*Coma":
                    mortality += 0.2;
                    break;
                case "*Sepsis":
                    mortality += 0.2;
                    break;
                // Добавьте дополнительные случаи для других симптомов, если это необходимо
            }
        }

        return Math.min(mortality, 1.0);
    }

    private double calculateResistance() {
        double resistance = 0.0;
        if (antiviralDrugsResistanceCheckBox.isSelected()) resistance += 0.3;
        if (disinfectantsResistanceCheckBox.isSelected()) resistance += 0.3;
        if (pHchangesResistanceCheckBox.isSelected()) resistance += 0.3;

        return Math.min(resistance, 1.0);
    }
}
