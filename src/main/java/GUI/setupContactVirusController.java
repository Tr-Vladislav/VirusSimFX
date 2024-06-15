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

/**
 * Controller class for setting up a ContactVirus in the simulation.
 * Manages the user interface components and handles interactions for setting up virus parameters.
 */
public class setupContactVirusController {
    // Checkboxes for various symptoms and resistances
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

    // Array of selected symptoms
    private String[] selectedSymptoms;
    // Virus object representing the contact virus
    ContactVirus virus = new ContactVirus(0, 0, 0, 0);

    /**
     * Initialize the controller.
     */
    @FXML
    public void initialize() {
    }

    /**
     * Start the simulation with the configured virus.
     * Sets the virus parameters and loads the main simulation scene.
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
     * Get the configured virus.
     *
     * @return the contact virus
     */
    public ContactVirus getVirus() {
        return virus;
    }

    /**
     * Update the simulation parameters based on user input.
     * Updates the progress bars for infectivity, mortality, and resistance.
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
     * Get the selected symptoms based on user input.
     *
     * @return an array of selected symptoms
     */
    private String[] getSelectedSymptoms() {
        List<String> symptoms = new ArrayList<>();
        if (feverCheckBox.isSelected()) symptoms.add("Fever");
        if (nauseaCheckBox.isSelected()) symptoms.add("Nausea");
        if (vomitingCheckBox.isSelected()) symptoms.add("Vomiting");
        if (rushCheckBox.isSelected()) symptoms.add("Rash");
        if (abdominalPainCheckBox.isSelected()) symptoms.add("Abdominal Pain");
        if (paralysisCheckBox.isSelected()) symptoms.add("*Paralysis");
        if (comaCheckBox.isSelected()) symptoms.add("*Coma");
        if (sepsisCheckBox.isSelected()) symptoms.add("*Sepsis");
        return symptoms.toArray(new String[0]);
    }

    /**
     * Calculate the infectivity of the virus based on selected symptoms and parameters.
     *
     * @return the infectivity value
     */
    private double calculateInfectivity() {
        double infectivity = 0.0;
        if (physicalContactCheckBox.isSelected()) infectivity += 0.25;
        if (bodilyFluidsCheckBox.isSelected()) infectivity += 0.25;
        if (contaminatedObjectsCheckBox.isSelected()) infectivity += 0.25;

        for (String symptom : selectedSymptoms) {
            switch (symptom) {
                case "Rash":
                    infectivity += 1;
                    break;
                case "Vomiting":
                    infectivity += 0.15;
                    break;
            }
        }

        infectivity *= (incubationSlider.getValue() / 14.0);
        return Math.min(infectivity, 1.0);
    }

    /**
     * Calculate the mortality of the virus based on selected symptoms.
     *
     * @return the mortality value
     */
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
            }
        }

        return Math.min(mortality, 1.0);
    }

    /**
     * Calculate the resistance of the virus based on selected resistances.
     *
     * @return the resistance value
     */
    private double calculateResistance() {
        double resistance = 0.0;
        if (antiviralDrugsResistanceCheckBox.isSelected()) resistance += 0.3;
        if (disinfectantsResistanceCheckBox.isSelected()) resistance += 0.3;
        if (pHchangesResistanceCheckBox.isSelected()) resistance += 0.3;

        return Math.min(resistance, 1.0);
    }
}
