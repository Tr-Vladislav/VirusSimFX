package GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import population.*;

import java.util.ArrayList;

public class Map {
    private static MainController mainController;
    private ArrayList<ArrayList<Country>> countries;
    private int quantityCountries;
    private int Width;
    private int Height;
    private int Radius;
    private int numCols;
    private int numRows;
    public Country pickedCountry;


    public Map(int Width, int Height,int Radius){
        mainController = MainController.getInstance();
        quantityCountries=0;
        countries = new ArrayList<>();
        this.Width=Width;
        this.Height = Height;
        this.Radius = Radius;
        mapCreate( Radius, Width, Height);
        pickedCountry = new Country(new Polygon(),"",0,0,0,0,false,0);
    }
    public void mapCreate( double hexRadius,double mapWidth,double mapHeight){

        double hexWidth = 1.5 * hexRadius;
        double hexHeight = Math.sqrt(3) * hexRadius;

        this.numCols = (int) ((int) mapWidth/hexWidth)+1;
        this.numRows = (int) ((int) mapHeight/hexHeight)+2;

        for (int row = 0; row < this.numRows; row++) {
            ArrayList<Country> mapRow = new ArrayList<>();
            for (int col = 0; col < this.numCols; col++) {
                double x = col * hexWidth;
                double y = row * hexHeight;
                if (col % 2 == 1) {
                    y += hexHeight / 2;
                }

                Polygon hexagon = createHexagon(x, y, hexRadius);
                hexagon.setFill(getColor(row));
                Country country = new Country(hexagon,String.valueOf(quantityCountries),Math.random(),(int)(Math.random()*800),Math.random(),10+(int)(Math.random()*25),false,Math.random());
                country.setColor(getColor(row));
                if(row==0 || row>=(this.numRows-2) || col==0 || col>= this.numCols-1){
                    country.setIsCountry(false);
                    country.getArea().setFill(Color.GRAY);
                }
                quantityCountries+=1;
                mapRow.add(country);
            }
            countries.add(mapRow);

        }
    }
    public void setMouseEvent(String type, boolean isEvent) {
        for (ArrayList<Country> countryCol : countries) {
            for (Country country : countryCol) {
                if (isEvent) {
                    if (type.equals("setGray")) {
                        if (country.getEventSetGray() == null) {
                            EventHandler<MouseEvent> eventSetGray = new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent e) {
                                    if (country.getIsCountry()) {
                                        country.getArea().setFill(Color.GRAY);
                                        country.setIsCountry(false);
                                    } else {
                                        country.getArea().setFill(country.getColor());
                                        country.setIsCountry(true);
                                    }
                                }
                            };
                            country.setEventSetGray(eventSetGray);
                        }
                        country.getArea().addEventFilter(MouseEvent.MOUSE_CLICKED, country.getEventSetGray());
                    } else if (type.equals("Pick")) {
                        if (country.getEventPick() == null) {
                            EventHandler<MouseEvent> eventPick = new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent e) {
                                    if (country.getIsCountry()) {
                                        if (pickedCountry != null) {
                                            pickedCountry.getArea().setStroke(Color.BLACK);
                                            pickedCountry.getArea().setStrokeWidth(1.5);
                                        }
                                        country.getArea().setStroke(Color.RED);
                                        country.getArea().setStrokeWidth(4);
                                        mainController.setStatistics(country.population.getPopulation()-country.population.getInfected(),country.population.getInfected(),country.population.getCorpse());

                                        pickedCountry = country;
                                    }
                                }
                            };
                            country.setEventPick(eventPick);
                        }
                        country.getArea().addEventFilter(MouseEvent.MOUSE_CLICKED, country.getEventPick());
                    }
                } else {
                    if (type.equals("setGray")) {
                        EventHandler<MouseEvent> eventSetGray = country.getEventSetGray();
                        if (eventSetGray != null) {
                            country.getArea().removeEventFilter(MouseEvent.MOUSE_CLICKED, eventSetGray);
                        }
                    } else if (type.equals("Pick")) {
                        EventHandler<MouseEvent> eventPick = country.getEventPick();
                        if (eventPick != null) {
                            pickedCountry.getArea().setStroke(Color.BLACK);
                            pickedCountry.getArea().setStrokeWidth(1.5);
                            country.getArea().removeEventFilter(MouseEvent.MOUSE_CLICKED, eventPick);
                        }
                    }
                }
            }
        }
    }
    private Paint getColor(int row){

        int hot_r=250,hot_g=152,hot_b=82;
        int warm_r=91, warm_g=235,warm_b=106;
        int cold_r=100,cold_g=205,cold_b=250;

        int r,g,b;
        if(row<numRows/2){
            r=hot_r-Math.abs((hot_r-warm_r))*row*2/numRows;
            g=hot_g+Math.abs((hot_g-warm_g))*row*2/numRows;
            b=hot_b+Math.abs((hot_b-warm_b))*row*2/numRows;
        }else{
            r=warm_r+Math.abs((warm_r-cold_r))*row/numRows;
            g=warm_g-Math.abs((warm_g-cold_g))*row/numRows;
            b=warm_b+Math.abs((warm_b-cold_b))*row/numRows;
        }
        return Color.rgb(r,g,b);
    }
    private Polygon createHexagon(double x, double y, double radius) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = 2.0 * Math.PI * i / 6;
            double xPos = x + radius * Math.cos(angle);
            double yPos = y + radius * Math.sin(angle);
            hexagon.getPoints().addAll(xPos, yPos);
        }
        hexagon.setStroke(Color.BLACK);
        hexagon.setStrokeWidth(1.5);
        return hexagon;
    }
    public ArrayList<Polygon> getCountries(){
        ArrayList<Polygon> map = new ArrayList<>();
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                map.add(countries.get(row).get(col).getArea());
            }
        }
        return map;
    }


}
class Country{
    public Population population;

    private EventHandler<MouseEvent> eventSetGray;
    private EventHandler<MouseEvent> eventPick;
    private Paint color;
    private Polygon area;
    private boolean isCountry;
    public Country(Polygon hexagon,String countryName,double populationDensity, int quantity, double stability, double averageTemperature, boolean borders, double medicalLevel){
        this.area = hexagon;
        this.isCountry=true;
        population = new Population(countryName,populationDensity,quantity, stability,averageTemperature, borders, medicalLevel);
    }
    public Polygon getArea(){
        return area;
    }
    public void setIsCountry(boolean isCountry){
        this.isCountry = isCountry;
    }
    public boolean getIsCountry(){
        return isCountry;
    }
    public void setColor(Paint color){
        this.color=color;
    }
    public Paint getColor(){
        return this.color;
    }
    public void setEventSetGray(EventHandler<MouseEvent> eventSetGray) {
        this.eventSetGray = eventSetGray;
    }

    public EventHandler<MouseEvent> getEventSetGray() {
        return eventSetGray;
    }

    public void setEventPick(EventHandler<MouseEvent> eventPick) {
        this.eventPick = eventPick;
    }

    public EventHandler<MouseEvent> getEventPick() {
        return eventPick;
    }
}