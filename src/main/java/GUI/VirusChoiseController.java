package GUI;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * VirusChoiseController class for handling the virus choice window.
 */
public class VirusChoiseController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Method to handle selection of Respiratory Virus.
     *
     * @param event the button click event
     * @throws IOException if there is an issue loading the FXML file
     */
    @FXML
    public void RespiratoryVirus(ActionEvent event) throws IOException {
        // Load the FXML file for setting up a respiratory virus
        FXMLLoader loader = new FXMLLoader(getClass().getResource("setupRespiratoryVirus.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to handle selection of Contact Virus.
     *
     * @param event the button click event
     * @throws IOException if there is an issue loading the FXML file
     */
    public void ContactVirus(ActionEvent event) throws IOException {
        // Load the FXML file for setting up a contact virus
        FXMLLoader loader = new FXMLLoader(getClass().getResource("setupContactVirus.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to handle selection of Foodborne Virus.
     *
     * @param event the button click event
     * @throws IOException if there is an issue loading the FXML file
     */
    public void FoodborneVirus(ActionEvent event) throws IOException {
        // Load the FXML file for setting up a foodborne virus
        FXMLLoader loader = new FXMLLoader(getClass().getResource("setupFoodborneVirus.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
