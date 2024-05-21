package GUI;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;

public class MainController {

    private int paneWidth=750;
    private int paneHeight=410;
    private static MainController instance;
    private ScheduledExecutorService scheduler;

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
    private Label world;
    @FXML
    private Button next;
    @FXML
    private Button startButton;
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
    private Label infectedCountries;

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
    public void setStatistics(){
        if(map.pickedCountry.population.getCountryName().equals("World")){
            this.healthy.setText(String.valueOf(map.pickedCountry.population.getWorldPopulation()-map.pickedCountry.population.getWorldInfected()));
            this.sick.setText(String.valueOf(map.pickedCountry.population.getWorldInfected()));
            this.dead.setText(String.valueOf(map.pickedCountry.population.getWorldCorpse()));
        }else {
            this.healthy.setText(String.valueOf(map.pickedCountry.population.getPopulation() - map.pickedCountry.population.getInfected()));
            this.sick.setText(String.valueOf(map.pickedCountry.population.getInfected()));
            this.dead.setText(String.valueOf(map.pickedCountry.population.getCorpse()));
        }
        this.healthyCountries.setText(String.valueOf(map.pickedCountry.getHealthyCountries()));
        this.infectedCountries.setText(String.valueOf(map.pickedCountry.getInfectedCountries()));
    }
    public void nextStep(){
        for(Country country: map.getCountries()){
            if(country.population.getInfected()+country.population.getInfected()*country.population.getInfections()<=country.population.getPopulation()) country.population.setInfected(country.population.getInfected()+(int)Math.ceil(country.population.getInfected()*country.population.getInfections()));
            else country.population.setInfected(country.population.getPopulation());
            if((double)(country.population.getInfected())/country.population.getPopulation() > 0.3) {
                map.newInfected(country);
            }
            showInfected(country);
        }
        if(map.pickedCountry.population.getWorldPopulation()==map.pickedCountry.population.getWorldInfected()){
            stopTimer();
        }

        setStatistics();
    }
    //Creating the mouse event handle
    public void ShowMap(){


        map = new Map(paneWidth, paneHeight,20);
        map.setMouseEvent("Pick",true);
        for(Country country:map.getCountries()){
            myPane.getChildren().add(country.getArea());
        }
        setStatistics();
    }
    public void setFirstInfectedCountry(){
        if(!map.pickedCountry.population.getCountryName().equals("World")){
            System.out.println("Start");
            map.pickedCountry.setIsInfected();
            map.pickedCountry.population.setInfected(1);
            setStatistics();
            startButton.setVisible(false);
            next.setVisible(true);
            startTimer();

        }
        else{
            System.out.println("Choose country");
        }
    }
    public void startTimer() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> Platform.runLater(() -> {
            nextStep();
        }), 0, 100, TimeUnit.MILLISECONDS);
    }

    public void stopTimer() {
        if (scheduler != null) {
            scheduler.shutdown();
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

    public void showInfected(Country country){
        for (int i = 0; i < country.population.getStepSick(); i++) {
            double[] xy = generateRandomPointInPolygon(country.getArea());
            Circle circle = new Circle(xy[0], xy[1], 1, Color.RED);
            circle.setMouseTransparent(true);
            scene = myPane.getScene();
            myPane.getChildren().add(circle);
        }
    }

    @FXML
    void initialize(){

        ShowMap();

        EventHandler<MouseEvent> worldPick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                map.pickedCountry.getArea().setStroke(Color.BLACK);
                map.pickedCountry.getArea().setStrokeWidth(1.5);
                map.pickedCountry = map.getWorld();
                setStatistics();
            }
        };
        world.addEventFilter(MouseEvent.MOUSE_CLICKED, worldPick);
    }

}
