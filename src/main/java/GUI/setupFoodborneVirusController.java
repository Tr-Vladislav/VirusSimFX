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
import virus.FoodborneVirus;
import virus.RespiratoryVirus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class setupFoodborneVirusController {
    @FXML
    private CheckBox abdominalPainCheckBox;

    @FXML
    private CheckBox antisepticsResistanceCheckBox1;

    @FXML
    private CheckBox contaminatedWaterCheckBox;

    @FXML
    private CheckBox dehydrationCheckBox;

    @FXML
    private CheckBox feverCheckBox;

    @FXML
    private CheckBox freezingResistanceCheckBox;

    @FXML
    private CheckBox diarheaCheckBox;

    @FXML
    private CheckBox improperFoodHandlingCheckBox;

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
    private CheckBox pasteurizationResistanceCheckBox;

    @FXML
    private ProgressBar resistanceBar;

    @FXML
    private CheckBox salivacheckBox;

    @FXML
    private CheckBox sepsisCheckBox;

    @FXML
    private CheckBox stomachAcidResistanceCheckBox;

    @FXML
    private CheckBox toxicHepatitisCheckBox;

    @FXML
    private CheckBox vomitingCheckBox;

    private String[] selectedSymptoms;
    FoodborneVirus virus = new FoodborneVirus(0,0,0,1);


    @FXML
    public void initialize() {
    }
    @FXML
    public void startSimulation(){
        nextButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("main.fxml"));


        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.showAndWait();
    }


    @FXML
    private void updateSimulation(ActionEvent event) {
        selectedSymptoms = getSelectedSymptoms();
        double infectivity = calculateInfectivity();
        double mortality = calculateMortality();
        double resistance = calculateResistance();

        infectivityBar.setProgress(infectivity);
        mortalityBar.setProgress(mortality);
        resistanceBar.setProgress(resistance);
    }

    public FoodborneVirus getVirus(){
        return virus;
    }
    private String[] getSelectedSymptoms() {
        List<String> symptoms = new ArrayList<>();
        if (feverCheckBox.isSelected()) symptoms.add("Fever");
        if (nauseaCheckBox.isSelected()) symptoms.add("Nausea");
        if (vomitingCheckBox.isSelected()) symptoms.add("Vomiting");
        if (diarheaCheckBox.isSelected()) symptoms.add("Diarrhea");
        if (abdominalPainCheckBox.isSelected()) symptoms.add("Abdominal pain");
        if (sepsisCheckBox.isSelected()) symptoms.add("Sepsis");
        if (dehydrationCheckBox.isSelected()) symptoms.add("Dehydration");
        if (toxicHepatitisCheckBox.isSelected()) symptoms.add("Toxic hepatitis");
        return symptoms.toArray(new String[0]);
    }

    private double calculateInfectivity() {
        double infectivity = 0.0;
        if (improperFoodHandlingCheckBox.isSelected()) infectivity += 0.4;
        if (contaminatedWaterCheckBox.isSelected()) infectivity += 0.3;
        if (salivacheckBox.isSelected()) infectivity += 0.3;

        for (String symptom : selectedSymptoms) {
            switch (symptom) {
                case "Cough":
                    infectivity += 0.2;
                    break;
                case "Shortness of Breath":
                    infectivity += 0.1;
                    break;
                // Добавьте дополнительные случаи для других симптомов, если это необходимо
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
                    mortality += 0.2;
                    break;
                case "Nausea":
                    mortality += 0.1;
                    break;
                case "Vomiting":
                    mortality += 0.05;
                    break;
                case "Diarrhea":
                    mortality += 0.05;
                    break;
                case "Abdominal pain":
                    mortality += 0.1;
                    break;
                case "Sepsis":
                    mortality += 0.25;
                    break;
                case "Toxic hepatitis":
                    mortality += 0.25;
                    break;
                // Добавьте дополнительные случаи для других симптомов, если это необходимо
            }
        }

        return Math.min(mortality, 1.0);
    }

    private double calculateResistance() {
        double resistance = 0.0;
        if (stomachAcidResistanceCheckBox.isSelected()) resistance += 0.25;
        if (pasteurizationResistanceCheckBox.isSelected()) resistance += 0.25;
        if (freezingResistanceCheckBox.isSelected()) resistance += 0.25;
        if (antisepticsResistanceCheckBox1.isSelected()) resistance += 0.25;

        return Math.min(resistance, 1.0);
    }
}
