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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import virus.ContactVirus;
import virus.FoodborneVirus;
import virus.RespiratoryVirus;
import virus.Virus;

public class MainController {

    private int paneWidth=750;
    private int paneHeight=410;
    private static MainController instance;
    private ScheduledExecutorService scheduler;

    public MainController(){
        instance = this;
    }
    private Map map;
    private int mutationCnt = 0;
    private int progressCnt = 0;
    private long previosWorldHealthy = 0;
    private long previosWorldInfected = 0;
    private long previosWorldCorpse = 0;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Polygon[] Map;
    private int circle_cnt = 0;
    public Virus virus;
    @FXML
    private Label world;
    @FXML
    private Button startButton;
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
    @FXML
    private Label communicationLabel;
    @FXML
    private ProgressBar healthyBar;
    @FXML
    private ProgressBar infectedBar;
    @FXML
    private ProgressBar corpseBar;
    @FXML
    private Label country;
    @FXML
    private ToggleButton pause;
    @FXML
    private ToggleButton start1;
    @FXML
    private ToggleButton start2;
    @FXML
    private VBox symptoms;
    @FXML
    private ProgressBar mortality;
    @FXML
    private ProgressBar infectivity;
    @FXML
    private Label corpseLabel;
    @FXML
    private Label infectedLabel;
    @FXML
    private Label day;
    @FXML
    private Text text;

    public static MainController getInstance() {
        return instance;
    }
    public void setEvents(boolean isRedact){
        if(isRedact) {
            map.setMouseEvent("setGray", true);
            map.setMouseEvent("Pick", false);
        }else{
            map.setMouseEvent("setGray", false);
            map.setMouseEvent("Pick", true);
        }
    }
    public void setStatistics(){
        if(map.pickedCountry.population.getCountryName().equals("World")){
            this.healthy.setText(String.valueOf(map.pickedCountry.population.getWorldHealthy()));
            this.sick.setText(String.valueOf(map.pickedCountry.population.getWorldInfected()));
            this.dead.setText(String.valueOf(map.pickedCountry.population.getWorldCorpse()));
            if(map.pickedCountry.population.getWorldInfected()-previosWorldInfected>0)this.infectedLabel.setText(String.valueOf(map.pickedCountry.population.getWorldInfected() - previosWorldInfected));
            else this.infectedLabel.setText(String.valueOf(map.pickedCountry.population.getWorldStepSick()));
            this.corpseLabel.setText(String.valueOf(map.pickedCountry.population.getWorldCorpse()-previosWorldCorpse));

        }else {
            this.healthy.setText(String.valueOf(map.pickedCountry.population.getHealthy()));
            this.sick.setText(String.valueOf(map.pickedCountry.population.getInfected()));
            this.dead.setText(String.valueOf(map.pickedCountry.population.getCorpse()));
            infectedLabel.setText(String.valueOf(map.pickedCountry.population.getStepSick()));
            corpseLabel.setText(String.valueOf(map.pickedCountry.population.getStepCorpse()));
        }
        this.healthyCountries.setText(String.valueOf(map.pickedCountry.getHealthyCountries()));
        this.infectedCountries.setText(String.valueOf(map.pickedCountry.getInfectedCountries()));

        healthyBar.setProgress(((double)map.pickedCountry.population.getWorldHealthy())/map.pickedCountry.population.getWorldPopulation());
        infectedBar.setProgress((double)map.pickedCountry.population.getWorldInfected()/map.pickedCountry.population.getWorldPopulation());
        corpseBar.setProgress(((double)map.pickedCountry.population.getWorldCorpse())/map.pickedCountry.population.getWorldPopulation());
        country.setText(map.pickedCountry.population.getCountryName());
        mortality.setProgress(virus.calculateMortality());
        infectivity.setProgress(virus.calculateInfectivity()*10);
        setPopulationStatistacs();

    }
    private void setPopulationStatistacs(){
        text.setText(map.pickedCountry.population.toString());

    }
    public void repeatSimulation(){
        progressCnt = 0;
        map.dayZero();
        map.pickedCountry.population.setZeroValues();
        map.pickedCountry.setZeroValues();
        myPane.getChildren().clear();
        ShowMap();
        setEvents(true);
        map.pickedCountry.population.setWorldInfected(0);
        map.pickedCountry.population.setWorldCorpse(0);
        startButton.setText("Next");
        startButton.setVisible(true);


    }
    private void checkProgress(){
        if(map.pickedCountry.population.getWorldHealthy()==previosWorldHealthy &&
        map.pickedCountry.population.getWorldCorpse() == previosWorldCorpse &&
        map.pickedCountry.population.getWorldInfected() == previosWorldInfected){
            progressCnt+=1;
        }
        previosWorldHealthy = map.pickedCountry.population.getWorldHealthy();
        previosWorldCorpse = map.pickedCountry.population.getWorldCorpse();
        previosWorldInfected = map.pickedCountry.population.getWorldInfected();
    }
    public void nextStep(){

        for(Country country: map.getCountries()){
            if(country.population.getInfected()*virus.calculateInfectivity()<=country.population.getHealthy()) {
                country.population.setInfected(country.population.getInfected()+(int)Math.ceil(country.population.getInfected()*virus.calculateInfectivity()));
            }
            else country.population.setInfected(country.population.getInfected()+country.population.getHealthy());
            if((double)(country.population.getInfected())/country.population.getPopulation() > 0.3) {
                map.newInfected(country);
            }
            showInfected(country);
            if(country.population.getInfected()*virus.calculateMortality()/25<=country.population.getInfected() && map.getDate()-country.getDateInfected()>virus.getIncubationPeriod() && (int)(country.population.getInfected()*virus.calculateMortality()/25)>0){
                country.population.setCorpse(country.population.getCorpse()+(int)(country.population.getInfected()*virus.calculateMortality()/25));
            }
            else if(country.population.getInfected()*virus.calculateMortality()/25<=country.population.getInfected() && map.getDate()-country.getDateInfected()>10*virus.getIncubationPeriod()){
                country.population.setCorpse(country.population.getCorpse()+(int)Math.ceil(country.population.getInfected()*virus.calculateMortality()/25));
            }
            //else if(map.getDate()-country.getDateInfected()>virus.getIncubationPeriod())country.population.setCorpse(country.population.getCorpse()+country.population.getInfected());
            showCorpse(country);
        }

        if(virus.getMutationSpeed()<=mutationCnt){

            mutationCnt=0;
            virus.addActiveSymptom();
            setSymptoms();
        }
        mutationCnt+=1;
        newDay();
        setStatistics();
        checkProgress();
        if(map.pickedCountry.population.getWorldPopulation()==map.pickedCountry.population.getWorldCorpse() || progressCnt>5){
            stopTimer();
            Button button = new Button("Repeat");
            button.setFont(Font.font(18));
            button.setLayoutY(250);
            button.setLayoutX(350);

            button.addEventFilter(MouseEvent.MOUSE_CLICKED,  new EventHandler<MouseEvent>() { @Override
            public void handle(MouseEvent e) {
                repeatSimulation();
            }});
            myPane.getChildren().add(button);
        }
        map.pickedCountry.population.zeroWorldStepSick();
    }
    public void setVirus(RespiratoryVirus virus){
        this.virus = new RespiratoryVirus(1,1,1,1);
        this.virus = virus;
    }
    public void setVirus(ContactVirus virus){
        this.virus = new ContactVirus(1,1, 1, 1);
        this.virus = virus;
    }
    public void setVirus(FoodborneVirus virus){
        this.virus = new FoodborneVirus(1,1,1, 1);
        this.virus = virus;
    }
    //Creating the mouse event handle
    public void ShowMap(){


        map = new Map(paneWidth, paneHeight,20);
        map.setMouseEvent("Pick",true);
        for(Country country:map.getCountries()){
            myPane.getChildren().add(country.getArea());
        }
        setStatistics();
        communicationLabel.setText("Redact your map");
    }
    public boolean setFirstInfectedCountry(){
        if(!map.pickedCountry.population.getCountryName().equals("World")){
            map.pickedCountry.setIsInfected(map.getDate());
            map.pickedCountry.population.setInfected(1);
            setStatistics();
            startButton.setVisible(false);
            startTimerOnce();
            start1.setSelected(true);
            return true;
        }
        else{
            return false;
        }
    }
    private void newDay(){
        map.newDay();
        day.setText("Day: "+String.valueOf(map.getDate()));
    }
    private void setSymptoms(){
        symptoms.getChildren().clear();
        Label label1 = new Label("Symptoms: ");
        label1.setFont(Font.font(26));
        symptoms.getChildren().add(label1);
        for(String sick: virus.getActiveSymptoms()){
            Label label = new Label(sick);
            label.setFont(Font.font(16));
            symptoms.getChildren().add(label);
        }
    }
    public void startTimer(int period) {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> Platform.runLater(() -> {
            nextStep();
        }), 0, period, TimeUnit.MILLISECONDS);
    }
    public void startTimerOnce(){
        stopTimer();
        startTimer(300);
        start1.setSelected(true);
        start2.setSelected(false);
        pause.setSelected(false);
    }
    public void startTimerTwice(){
        stopTimer();
        startTimer(150);
        start2.setSelected(true);
        start1.setSelected(false);
        pause.setSelected(false);
    }
    public void stopTimerFunc(){
        stopTimer();
        pause.setSelected(true);
        start1.setSelected(false);
        start2.setSelected(false);
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
            myPane.getChildren().add(circle);
        }
    }
    public void showCorpse(Country country){
        for (int i = 0; i < country.population.getStepCorpse(); i++) {
            double[] xy = generateRandomPointInPolygon(country.getArea());
            Circle circle = new Circle(xy[0], xy[1], 1, Color.BLACK);
            circle.setMouseTransparent(true);
            myPane.getChildren().add(circle);
        }
        /*for(Node node :myPane.getChildren()){
            if (node instanceof Circle) {
                Circle circle = (Circle) node;
                // Выполняем необходимые операции с кругом
                circle.setFill(Color.BLACK); // Пример изменения цвета
            }
        }*/
    }
    public void redactEnd(){
        if(startButton.getText().equals("Next")) {
            startButton.setText("Start");
            communicationLabel.setText("Select the first infected country");
            setEvents(false);
        }
        else if(setFirstInfectedCountry()){
            communicationLabel.setText("Virus started from " + map.pickedCountry.population.getCountryName());
            startButton.setVisible(false);
        }
    }

    @FXML
    void initialize(){
        virus = new RespiratoryVirus(1,1,1, 1);
        ShowMap();
        setEvents(true);
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
        setSymptoms();
        map.dayZero();
    }

}
