package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import virus.RespiratoryVirus;
import virus.Virus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for setting up a respiratory virus in the simulation.
 * This class manages the user interface for configuring the properties of a respiratory virus,
 * including symptoms, transmission routes, and resistances.
 */
public class setupRespiratoryVirusController {

    @FXML
    private CheckBox feverCheckBox;

    @FXML
    private CheckBox coughCheckBox;

    @FXML
    private CheckBox fatigueCheckBox;

    @FXML
    private CheckBox headacheCheckBox;

    @FXML
    private CheckBox soreThroatCheckBox;

    @FXML
    private CheckBox shortnessOfBreathCheckBox;

    @FXML
    private CheckBox pulmonaryAcidosisCheckBox;

    @FXML
    private CheckBox cerebralEdemaCheckBox;

    @FXML
    private Slider incubationSlider;

    @FXML
    private CheckBox airborneCheckBox;

    @FXML
    private CheckBox contactCheckBox;

    @FXML
    private CheckBox dropletsCheckBox;

    @FXML
    private CheckBox antibioticResistanceCheckBox;

    @FXML
    private CheckBox heatResistanceCheckBox;

    @FXML
    private CheckBox coldResistanceCheckBox;

    @FXML
    private ProgressBar infectivityBar;

    @FXML
    private ProgressBar mortalityBar;

    @FXML
    private ProgressBar resistanceBar;

    @FXML
    private Button nextButton;

    private String[] selectedSymptoms;
    RespiratoryVirus virus = new RespiratoryVirus(0, 0, 0, 0);

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        // Initialization code if needed
    }

    /**
     * Starts the simulation with the configured virus.
     * Sets the parameters of the virus and loads the main simulation screen.
     */
    @FXML
    public void startSimulation() {
        virus.setAllparam(incubationSlider.getValue(), calculateInfectivity(), calculateMortality(), 30, getSelectedSymptoms(), getSelectedSymptoms());
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
        if (mainController == null) {
            System.out.println("Error");
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.showAndWait();
    }

    /**
     * Updates the simulation parameters based on the current selections.
     * Calculates and updates the progress bars for infectivity, mortality, and resistance.
     */
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

    /**
     * Gets the configured respiratory virus.
     *
     * @return the configured respiratory virus
     */
    public RespiratoryVirus getVirus() {
        return virus;
    }

    /**
     * Gets the selected symptoms based on the user's input.
     *
     * @return an array of selected symptoms
     */
    private String[] getSelectedSymptoms() {
        List<String> symptoms = new ArrayList<>();
        if (feverCheckBox.isSelected()) symptoms.add("Fever");
        if (coughCheckBox.isSelected()) symptoms.add("Cough");
        if (fatigueCheckBox.isSelected()) symptoms.add("Fatigue");
        if (headacheCheckBox.isSelected()) symptoms.add("Headache");
        if (soreThroatCheckBox.isSelected()) symptoms.add("Sore Throat");
        if (shortnessOfBreathCheckBox.isSelected()) symptoms.add("Shortness of Breath");
        if (pulmonaryAcidosisCheckBox.isSelected()) symptoms.add("*Pulmonary acidosis");
        if (cerebralEdemaCheckBox.isSelected()) symptoms.add("*Cerebral edema");
        return symptoms.toArray(new String[0]);
    }

    /**
     * Calculates the infectivity of the virus based on the selected symptoms and transmission routes.
     *
     * @return the calculated infectivity value
     */
    private double calculateInfectivity() {
        double infectivity = 0.0;
        if (airborneCheckBox.isSelected()) infectivity += 0.25;
        if (contactCheckBox.isSelected()) infectivity += 0.25;
        if (dropletsCheckBox.isSelected()) infectivity += 0.25;

        for (String symptom : selectedSymptoms) {
            switch (symptom) {
                case "Cough":
                    infectivity += 0.15;
                    break;
                case "Shortness of Breath":
                    infectivity += 0.1;
                    break;
                // Add additional cases for other symptoms if necessary
            }
        }

        infectivity *= (incubationSlider.getValue() / 14.0);
        return Math.min(infectivity, 1.0);
    }

    /**
     * Calculates the mortality of the virus based on the selected symptoms.
     *
     * @return the calculated mortality value
     */
    private double calculateMortality() {
        double mortality = 0.0;

        for (String symptom : selectedSymptoms) {
            switch (symptom) {
                case "Fever":
                    mortality += 0.15;
                    break;
                case "Fatigue":
                    mortality += 0.1;
                    break;
                case "Headache":
                    mortality += 0.05;
                    break;
                case "Sore Throat":
                    mortality += 0.1;
                    break;
                case "Shortness of Breath":
                    mortality += 0.1;
                    break;
                case "*Pulmonary acidosis":
                    mortality += 0.25;
                    break;
                case "*Cerebral edema":
                    mortality += 0.25;
                    break;
            }
        }

        return Math.min(mortality, 1.0);
    }

    /**
     * Calculates the resistance of the virus based on the selected resistances.
     *
     * @return the calculated resistance value
     */
    private double calculateResistance() {
        double resistance = 0.0;
        if (antibioticResistanceCheckBox.isSelected()) resistance += 0.333;
        if (heatResistanceCheckBox.isSelected()) resistance += 0.333;
        if (coldResistanceCheckBox.isSelected()) resistance += 0.333;

        return Math.min(resistance, 1.0);
    }
}
