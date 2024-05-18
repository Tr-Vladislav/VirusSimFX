package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VirusSetupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox BronchitisCheckBox;

    @FXML
    private CheckBox ConvulsionsCheckBox;

    @FXML
    private CheckBox CoughCheckBox;

    @FXML
    private Slider DurationOfFeverSlider;

    @FXML
    private CheckBox FeverCheckBox;

    @FXML
    private CheckBox HallucinationsCheckBox;

    @FXML
    private CheckBox HeadacheCheckBox;

    @FXML
    private Slider IncubationPeriodSlider;

    @FXML
    private CheckBox PharyngitisCheckBox;

    @FXML
    private Slider TemperatureSlider;
    @FXML
    private Button StartButton;

    @FXML
    private CheckBox VomitCheckBox;

    @FXML
    void initialize() {
        StartButton.setOnAction(event ->{
            StartButton.getScene().getWindow().hide();

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
        });
    }

}
