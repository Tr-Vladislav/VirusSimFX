package GUI;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private int circle_cnt = 0;

    @FXML
    private Label label;
    @FXML
    private AnchorPane myPane;
    @FXML
    private Rectangle field;
    @FXML
    public void oneMoreCircle(ActionEvent event) throws IOException {
        for (int i = 0; i < 50; i++) {
            if(circle_cnt>500)break;
            int x = (int) (field.getLayoutX() + 5 + Math.random() * (field.getWidth() - 10));
            int y = (int) (field.getLayoutY() + 5 + Math.random() * (field.getHeight() - 10));
            Circle circle = new Circle(x, y, 5, Color.RED);
            label.setText(String.valueOf(field.getLayoutX()) + "\n" + String.valueOf(field.getLayoutY()));
            scene = myPane.getScene();
            myPane.getChildren().add(circle);
            circle_cnt++;

        }
    }
    @FXML
    void initialize(){
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        //root = loader.load();
    }

}
