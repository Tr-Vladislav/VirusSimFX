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
 * StartController class for controlling the start window.
 */
public class StartController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * The next method loads a new scene when the button is clicked.
     *
     * @param event the button click event
     * @throws IOException if there is an issue loading the FXML file
     */
    @FXML
    public void next(ActionEvent event) throws IOException {
        // Load the FXML file for virus choice
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Virus_choise.fxml"));
        root = loader.load();

        // Get the current stage and set the new scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
