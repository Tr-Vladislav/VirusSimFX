package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The StartApplication class launches the JavaFX application.
 */
public class StartApplication extends Application {

    /**
     * The start method initializes the main stage and loads the FXML file.
     *
     * @param stage the primary stage for this application
     * @throws IOException if there is an issue loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Load the FXML file for the start window
            Parent root = FXMLLoader.load(getClass().getResource("start_window.fxml"));
            Scene scene = new Scene(root);

            // Set the scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // Print the stack trace in case of an exception
            e.printStackTrace();
        }
    }

    /**
     * The main method to launch the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }
}
