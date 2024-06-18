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

/**
 * MainController class for controlling the main application window.
 */
public class MainController {

    // Pane dimensions
    private int paneWidth = 750;
    private int paneHeight = 410;
    // Singleton instance of MainController
    private static MainController instance;
    // Scheduler for running tasks at fixed intervals
    private ScheduledExecutorService scheduler;

    /**
     * Constructor to initialize the instance.
     */
    public MainController() {
        instance = this;
    }

    // Map object representing the simulation map
    private Map map;
    // Counter for mutation
    private int mutationCnt = 0;
    // Counter for progress
    private int progressCnt = 0;
    // Previous world statistics
    private long previosWorldHealthy = 0;
    private long previosWorldInfected = 0;
    private long previosWorldCorpse = 0;

    // Stage and scene for the application
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Array of polygons representing the map
    private Polygon[] Map;
    // Counter for circles
    private int circle_cnt = 0;
    // Virus object representing the virus in the simulation
    public Virus virus;

    // FXML annotated UI components
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
    @FXML
    private Label virusType;
    @FXML
    private Label temperature;
    @FXML
    private Label resistance;

    /**
     * Get the singleton instance of MainController.
     *
     * @return the singleton instance of MainController
     */
    public static MainController getInstance() {
        return instance;
    }

    /**
     * Set events for map interaction.
     *
     * @param isRedact if true, set gray event; if false, set pick event
     */
    public void setEvents(boolean isRedact) {
        if (isRedact) {
            map.setMouseEvent("setGray", true);
            map.setMouseEvent("Pick", false);
        } else {
            map.setMouseEvent("setGray", false);
            map.setMouseEvent("Pick", true);
        }
    }

    /**
     * Update statistics displayed in the UI.
     */
    public void setStatistics() {
        if (map.pickedCountry.population.getCountryName().equals("World")) {
            this.healthy.setText(String.valueOf(map.pickedCountry.population.getWorldHealthy()));
            this.sick.setText(String.valueOf(map.pickedCountry.population.getWorldInfected()));
            this.dead.setText(String.valueOf(map.pickedCountry.population.getWorldCorpse()));
            if (map.pickedCountry.population.getWorldInfected() - previosWorldInfected > 0)
                this.infectedLabel.setText(String.valueOf(map.pickedCountry.population.getWorldInfected() - previosWorldInfected));
            else
                this.infectedLabel.setText(String.valueOf(map.pickedCountry.population.getWorldStepSick()));
            this.corpseLabel.setText(String.valueOf(map.pickedCountry.population.getWorldCorpse() - previosWorldCorpse));
        } else {
            this.healthy.setText(String.valueOf(map.pickedCountry.population.getHealthy()));
            this.sick.setText(String.valueOf(map.pickedCountry.population.getInfected()));
            this.dead.setText(String.valueOf(map.pickedCountry.population.getCorpse()));
            infectedLabel.setText(String.valueOf(map.pickedCountry.population.getStepSick()));
            corpseLabel.setText(String.valueOf(map.pickedCountry.population.getStepCorpse()));
        }
        this.healthyCountries.setText(String.valueOf(map.pickedCountry.getHealthyCountries()));
        this.infectedCountries.setText(String.valueOf(map.pickedCountry.getInfectedCountries()));
        healthyBar.setProgress(((double) map.pickedCountry.population.getWorldHealthy()) / map.pickedCountry.population.getWorldPopulation());
        infectedBar.setProgress((double) map.pickedCountry.population.getWorldInfected() / map.pickedCountry.population.getWorldPopulation());
        corpseBar.setProgress(((double) map.pickedCountry.population.getWorldCorpse()) / map.pickedCountry.population.getWorldPopulation());
        country.setText(map.pickedCountry.population.getCountryName());
        mortality.setProgress(virus.calculateMortality());
        infectivity.setProgress(virus.calculateInfectivity() * 10);
        String res = "";
        if(virus.getHeatResistance())res+='H';
        if(virus.getColdResistance())res+='C';
        resistance.setText(res);
        setPopulationStatistacs();
    }

    /**
     * Set population statistics text.
     */
    private void setPopulationStatistacs() {
        text.setText(map.pickedCountry.population.toString());
        temperature.setText(String.valueOf(((double)(int)(map.pickedCountry.population.getAverageTemperature()*10))/10)+"℃");
    }

    /**
     * Restart the simulation.
     */
    public void repeatSimulation() {
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

    /**
     * Check the simulation progress.
     */
    private void checkProgress() {
        if (map.pickedCountry.population.getWorldHealthy() == previosWorldHealthy &&
                map.pickedCountry.population.getWorldCorpse() == previosWorldCorpse &&
                map.pickedCountry.population.getWorldInfected() == previosWorldInfected) {
            progressCnt += 1;
        }
        previosWorldHealthy = map.pickedCountry.population.getWorldHealthy();
        previosWorldCorpse = map.pickedCountry.population.getWorldCorpse();
        previosWorldInfected = map.pickedCountry.population.getWorldInfected();
    }

    /**
     * Perform the next step in the simulation.
     */
    public void nextStep() {
        for (Country country : map.getCountries()) {
            country.population.simulateInfectionStep(virus);
            if (country.population.getInfected() * correctInfectivity(virus.calculateInfectivity(), country) <= country.population.getHealthy()) {
                country.population.setInfected(country.population.getInfected() + (int) Math.ceil(country.population.getInfected() * correctInfectivity(virus.calculateInfectivity(), country)));
            } else country.population.setInfected(country.population.getInfected() + country.population.getHealthy());
            if ((double) (country.population.getInfected()) / country.population.getPopulation() > 0.3 && !country.population.isBorders()) {
                map.newInfected(country);
            }
            showInfected(country);
            if (country.population.getInfected() * virus.calculateMortality() / 25 <= country.population.getInfected() && map.getDate() - country.getDateInfected() > virus.getIncubationPeriod() && (int) (country.population.getInfected() * virus.calculateMortality() / 25) > 0) {
                country.population.setCorpse(country.population.getCorpse() + (int) (country.population.getInfected() * virus.calculateMortality() / 25));
            } else if (country.population.getInfected() * virus.calculateMortality() / 25 <= country.population.getInfected() && map.getDate() - country.getDateInfected() > 10 * virus.getIncubationPeriod()) {
                country.population.setCorpse(country.population.getCorpse() + (int) Math.ceil(country.population.getInfected() * virus.calculateMortality() / 25));
            }
            showCorpse(country);
        }

        if (virus.getMutationSpeed() <= mutationCnt) {
            mutationCnt = 0;
            virus.addActiveSymptom((double) map.pickedCountry.population.getWorldHealthy() / map.pickedCountry.population.getWorldPopulation());
            setSymptoms();
        }
        mutationCnt += 1;
        newDay();
        setStatistics();
        checkProgress();
        if (map.pickedCountry.population.getWorldPopulation() == map.pickedCountry.population.getWorldCorpse() || progressCnt > 5) {
            stopTimer();
            Button button = new Button("Repeat");
            button.setFont(Font.font(18));
            button.setLayoutY(250);
            button.setLayoutX(350);

            Button home = new Button("home");
            home.setFont(Font.font(14));
            home.setLayoutY(300);
            home.setLayoutX(365);

            button.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    repeatSimulation();
                }
            });
            home.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    home();
                }
            });
            myPane.getChildren().add(button);
        }
        map.pickedCountry.population.zeroWorldStepSick();
    }

    /**
     * Return to the home screen.
     */
    private void home() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Virus_choise.fxml"));
        text.getScene().getWindow().hide();

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private double correctInfectivity(double infectivity, Country country){
        if(country.population.getAverageTemperature()>20 && !virus.getHeatResistance()){
            infectivity*=0.6;
        }
        if(country.population.getAverageTemperature()<0 && !virus.getColdResistance()){
            infectivity*=0.4;
        }
        return infectivity;
    }
    /**
     * Set the virus to a RespiratoryVirus.
     *
     * @param virus the RespiratoryVirus to set
     */
    public void setVirus(RespiratoryVirus virus) {
        this.virus = new RespiratoryVirus(1, 1, 1, 1);
        this.virus = virus;
        virusType.setText(virus.getVirusType() + " virus");
        setStatistics();
    }

    /**
     * Set the virus to a ContactVirus.
     *
     * @param virus the ContactVirus to set
     */
    public void setVirus(ContactVirus virus) {
        this.virus = new ContactVirus(1, 1, 1, 1);
        this.virus = virus;
        virusType.setText(virus.getVirusType() + " virus");
        setStatistics();
    }

    /**
     * Set the virus to a FoodborneVirus.
     *
     * @param virus the FoodborneVirus to set
     */
    public void setVirus(FoodborneVirus virus) {
        this.virus = new FoodborneVirus(1, 1, 1, 1);
        this.virus = virus;
        virusType.setText(virus.getVirusType() + " virus");
        setStatistics();
    }

    /**
     * Create the mouse event handle and show the map.
     */
    public void ShowMap() {
        map = new Map(paneWidth, paneHeight, 20);
        map.setMouseEvent("Pick", true);
        for (Country country : map.getCountries()) {
            myPane.getChildren().add(country.getArea());
        }
        setStatistics();
        communicationLabel.setText("Redact your map");
    }

    /**
     * Set the first infected country.
     *
     * @return true if the first infected country is set, false otherwise
     */
    public boolean setFirstInfectedCountry() {
        if (!map.pickedCountry.population.getCountryName().equals("World")) {
            map.pickedCountry.setIsInfected(map.getDate());
            map.pickedCountry.population.setInfected(1);
            if(map.pickedCountry.population.getAverageTemperature()>20)virus.setHeatResistance();
            if(map.pickedCountry.population.getAverageTemperature()<0)virus.setColdResistance();
            setStatistics();
            startButton.setVisible(false);
            startTimerOnce();
            start1.setSelected(true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Increment the simulation day.
     */
    private void newDay() {
        map.newDay();
        day.setText("Day: " + String.valueOf(map.getDate()));
    }

    /**
     * Set symptoms in the UI.
     */
    private void setSymptoms() {
        symptoms.getChildren().clear();
        Label label1 = new Label("Symptoms: ");
        label1.setFont(Font.font(26));
        symptoms.getChildren().add(label1);
        for (String sick : virus.getActiveSymptoms()) {
            Label label = new Label(sick);
            label.setFont(Font.font(16));
            symptoms.getChildren().add(label);
        }
    }

    /**
     * Start the simulation timer with a specified period.
     *
     * @param period the period in milliseconds between successive executions
     */
    public void startTimer(int period) {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> Platform.runLater(() -> {
            nextStep();
        }), 0, period, TimeUnit.MILLISECONDS);
    }

    /**
     * Start the simulation timer with a single speed.
     */
    public void startTimerOnce() {
        stopTimer();
        startTimer(300);
        start1.setSelected(true);
        start2.setSelected(false);
        pause.setSelected(false);
    }

    /**
     * Start the simulation timer with a double speed.
     */
    public void startTimerTwice() {
        stopTimer();
        startTimer(10);
        start2.setSelected(true);
        start1.setSelected(false);
        pause.setSelected(false);
    }

    /**
     * Pause the simulation timer.
     */
    public void stopTimerFunc() {
        stopTimer();
        pause.setSelected(true);
        start1.setSelected(false);
        start2.setSelected(false);
    }

    /**
     * Stop the simulation timer.
     */
    public void stopTimer() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    /**
     * Generate a random point inside a polygon.
     *
     * @param polygon the polygon to generate a point inside
     * @return an array containing the x and y coordinates of the random point
     */
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

    /**
     * Show infected individuals on the map.
     *
     * @param country the country to show infected individuals in
     */
    public void showInfected(Country country) {
        for (int i = 0; i < country.population.getStepSick(); i++) {
            double[] xy = generateRandomPointInPolygon(country.getArea());
            Circle circle = new Circle(xy[0], xy[1], 1, Color.RED);
            circle.setMouseTransparent(true);
            myPane.getChildren().add(circle);
        }
    }

    /**
     * Show deceased individuals on the map.
     *
     * @param country the country to show deceased individuals in
     */
    public void showCorpse(Country country) {
        for (int i = 0; i < country.population.getStepCorpse(); i++) {
            double[] xy = generateRandomPointInPolygon(country.getArea());
            Circle circle = new Circle(xy[0], xy[1], 1, Color.BLACK);
            circle.setMouseTransparent(true);
            myPane.getChildren().add(circle);
        }
    }

    /**
     * End the redaction phase.
     */
    public void redactEnd() {
        if (startButton.getText().equals("Next")) {
            startButton.setText("Start");
            communicationLabel.setText("Select the first infected country");
            setEvents(false);
        } else if (setFirstInfectedCountry()) {
            communicationLabel.setText("Virus started from " + map.pickedCountry.population.getCountryName());
            startButton.setVisible(false);
        }
    }

    /**
     * Initialize the controller.
     */
    @FXML
    void initialize() {
        virus = new RespiratoryVirus(1, 1, 1, 1);
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
