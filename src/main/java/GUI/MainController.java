package GUI;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import java.util.Random;

public class MainController {
    private static MainController instance;

    public MainController(){
        instance = this;
    }
    private Map map;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Polygon[] Map;
    private int circle_cnt = 0;
    @FXML
    private CheckBox redact;
    @FXML
    private Label label;
    @FXML
    private Pane myPane;
    @FXML
    private Rectangle field;

    @FXML
    private Label healthy;
    @FXML
    private Label sick;
    @FXML
    private Label dead;
    @FXML
    private Label healthyCountries;
    @FXML
    private Label InfectedCountries;


    @FXML
    public void oneMoreCircle(ActionEvent event) throws IOException {
        for (int i = 0; i < 50; i++) {
            if(circle_cnt>500)break;
            int x = (int) (field.getLayoutX() + 5 + Math.random() * (field.getWidth() - 10));
            int y = (int) (field.getLayoutY() + 5 + Math.random() * (field.getHeight() - 10));
            Circle circle = new Circle(x, y, 1, Color.RED);
            circle.setMouseTransparent(true);
            label.setText(String.valueOf(field.getLayoutX()) + "\n" + String.valueOf(field.getLayoutY()));
            scene = myPane.getScene();
            myPane.getChildren().add(circle);
            circle_cnt++;

        }
    }
    public static MainController getInstance() {
        return instance;
    }
    public void setEvents(){
        if(redact.isSelected()) {
            map.setMouseEvent("setGray", true);
            map.setMouseEvent("Pick", false);
        }else{
            map.setMouseEvent("setGray", false);
            map.setMouseEvent("Pick", true);
        }
    }
    public void setStatistics(int health, int sick, int dead){
        this.healthy.setText(String.valueOf(health));
        this.sick.setText(String.valueOf(sick));
        this.dead.setText(String.valueOf(dead));
    }
    public void nextStep(){
        map.pickedCountry.population.setInfected(map.pickedCountry.population.getInfected()+20);
        showInfected();
        setStatistics(map.pickedCountry.population.getPopulation()-map.pickedCountry.population.getInfected(),map.pickedCountry.population.getInfected(),map.pickedCountry.population.getCorpse());

    }
    //Creating the mouse event handle
    public void ShowMap(){


        map = new Map((int)myPane.getWidth(), (int)myPane.getHeight(),25);
        map.setMouseEvent("Pick",true);
        for(Polygon country:map.getCountries()){
            myPane.getChildren().add(country);
        }
    }
    private double[] generateRandomPointInPolygon(Polygon polygon) {
        double minX = polygon.getBoundsInParent().getMinX();
        double minY = polygon.getBoundsInParent().getMinY();
        double maxX = polygon.getBoundsInParent().getMaxX();
        double maxY = polygon.getBoundsInParent().getMaxY();

        while (true) {
            double x = minX + Math.random() * (maxX - minX);
            double y = minY + Math.random() * (maxY - minY);
            if (polygon.contains(x, y)) {
                return new double[]{x, y};
            }
        }
    }

    public void showInfected(){
        for (int i = 0; i < map.pickedCountry.population.getStepSick(); i++) {
            double[] xy = generateRandomPointInPolygon(map.pickedCountry.getArea());
            Circle circle = new Circle(xy[0], xy[1], 1, Color.RED);
            circle.setMouseTransparent(true);
            scene = myPane.getScene();
            myPane.getChildren().add(circle);
        }
    }

    @FXML
    void initialize(){


    }

}
