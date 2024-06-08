package GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import population.*;

import java.util.ArrayList;


public class Map {
    private int date;

    private Country world;
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

    }
    public void mapCreate( double hexRadius,double mapWidth,double mapHeight){

        double hexWidth = 1.5 * hexRadius;
        double hexHeight = Math.sqrt(3) * hexRadius;

        this.numCols = (int) ((int) mapWidth/hexWidth)+1;
        this.numRows = (int) ((int) mapHeight/hexHeight)+2;

        String[] settlements = {
                "Springfield", "Riverton", "Mapleton", "Greenville", "Fairview",
                "Lakeside", "Hillcrest", "Brookfield", "Woodland", "Pinehurst",
                "Rosedale", "Cedarville", "Milltown", "Elk Grove", "Ashford",
                "Sunnyvale", "Bridgewater", "Clearwater", "Kingswood", "Silverton",
                "Oakridge", "Westfield", "Eastwood", "Northgate", "Southport",
                "Havenwood", "Bloomfield", "Riverdale", "Highland", "Meadowbrook",
                "Kingsport", "Foxborough", "Greenfield", "Newcastle", "Lakewood",
                "Parkside", "Stonehaven", "Rosewood", "Harborview", "Huntington",
                "Edgewater", "Brentwood", "Copperfield", "Fernwood", "Oakwood",
                "Maplewood", "Pleasantville", "Redwood", "Birchwood", "Summerville",
                "Whispering Pines", "Granite Falls", "Chestnut Hill", "Sunnyside",
                "Windermere", "Silverlake", "Crestwood", "Pinecrest", "Shady Grove",
                "Golden Valley", "Mountain View", "Willow Creek", "Canyon Ridge",
                "Eagle Rock", "Heritage Oaks", "Crystal Springs", "Sunset Hills",
                "Falcon Heights", "Pebble Beach", "Bluewater", "Stonybrook", "Holly Springs",
                "Morningstar", "Riverbend", "Hillsborough", "Mystic Falls", "Willowbrook",
                "Summerfield", "Rocky Point", "Starwood", "Autumn Ridge", "Seaview",
                "Silver Creek", "Springdale", "Tanglewood", "Twin Lakes", "Copper Ridge",
                "Greenwood", "Ironwood", "Hidden Valley", "Ravenwood", "Wildwood",
                "Sunnydale", "Maple Grove", "Fairhaven", "Glenwood", "Pine Valley",
                "Sunrise", "Valley View", "Brighton", "Mountain Springs", "Seaside"
        };


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
                Country country = new Country(hexagon,  settlements[(row*col)%settlements.length],100+(int)(Math.random()*800),Math.random(),10+(int)(Math.random()*25),false,Math.random());
                country.setColor(getColor(row));
                if(row==0 || row>=(this.numRows-2) || col==0 || col>= this.numCols-1){
                    country.setIsCountry(false);
                    country.getArea().setFill(Color.GRAY);
                }
                quantityCountries+=1;
                country.row = row;
                country.col = col;
                mapRow.add(country);
            }
            countries.add(mapRow);
        }
        world = new Country("World");
        pickedCountry = world;
    }
    public void newInfected(Country infectedCountry){
        Country country = countries.get(infectedCountry.row).get(infectedCountry.col-1);
        if(country.getIsCountry() && !country.getIsInfected()){
            country.population.setInfected((int)Math.ceil(1+Math.random()*30));
            if(country.population.getInfected()>0)country.setIsInfected(date);
        }
        country = countries.get(infectedCountry.row).get(infectedCountry.col+1);
        if(country.getIsCountry() && !country.getIsInfected()){
            country.population.setInfected((int)Math.ceil(1+Math.random()*30));
            if(country.population.getInfected()>0)country.setIsInfected(date);
        }
        country = countries.get(infectedCountry.row+1).get(infectedCountry.col);
        if(country.getIsCountry() && !country.getIsInfected()){
            country.population.setInfected((int)Math.ceil(1+Math.random()*30));
            if(country.population.getInfected()>0)country.setIsInfected(date);
        }
        country = countries.get(infectedCountry.row-1).get(infectedCountry.col);
        if(country.getIsCountry() && !country.getIsInfected()){
            country.population.setInfected((int)Math.ceil(1+Math.random()*30));
            if(country.population.getInfected()>0)country.setIsInfected(date);
        }
        country = countries.get(infectedCountry.row+nearlyHex(infectedCountry.col)).get(infectedCountry.col-1);
        if(country.getIsCountry() && !country.getIsInfected()){
            country.population.setInfected((int)Math.ceil(1+Math.random()*30));
            if(country.population.getInfected()>0)country.setIsInfected(date);
        }
        country = countries.get(infectedCountry.row+nearlyHex(infectedCountry.col)).get(infectedCountry.col+1);
        if(country.getIsCountry() && !country.getIsInfected()){
            country.population.setInfected((int)Math.ceil(1+Math.random()*30));
            if(country.population.getInfected()>0)country.setIsInfected(date);
        }

    }
    public void dayZero(){
        date = 0;
    }
    public int getDate(){
        return date;
    }
    public void newDay(){
        date+=1;
    }
    private int nearlyHex(int col){
        if(col%2==1)return 1;
        else return -1;
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
                                    mainController.setStatistics();
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
                                    setPickedCountry(country);
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
    public void setPickedCountry(Country country){
        if (country.getIsCountry()) {
            if (pickedCountry != null) {
                pickedCountry.getArea().setStroke(Color.BLACK);
                pickedCountry.getArea().setStrokeWidth(1.5);
            }
            country.getArea().setStroke(Color.RED);
            country.getArea().setStrokeWidth(4);
            pickedCountry = country;
            mainController.setStatistics();

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
    public ArrayList<Country> getCountries(){
        ArrayList<Country> map = new ArrayList<>();
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                map.add(countries.get(row).get(col));
            }
        }
        return map;
    }
    public Country getWorld(){
        return world;
    }


}
class Country{
    private int dateInfected;
    private static int healthyCountries=0;
    private static int infectedCountries=0;
    private boolean isInfected;
    public int row;
    public int col;
    public Population population;

    private EventHandler<MouseEvent> eventSetGray;
    private EventHandler<MouseEvent> eventPick;
    private Paint color;
    private Polygon area;
    private boolean isCountry;
    public Country(Polygon hexagon,String countryName, int quantity, double stability, double averageTemperature, boolean borders, double medicalLevel){
        this.dateInfected = 0;
        healthyCountries+=1;
        this.isInfected = false;
        this.area = hexagon;
        this.isCountry=true;
        population = new Population(countryName, quantity, averageTemperature);
    }
    public Country(String countryName){
        this.isInfected = false;
        this.area = new Polygon();
        this.isCountry = false;
        this.population = new Population(countryName);
    }
    public Polygon getArea(){
        return area;
    }

    public void setIsCountry(boolean isCountry){

        this.isCountry = isCountry;
        if(isCountry){
            healthyCountries+=1;
            population.redactWorldPopulation(true);

        }else {
            healthyCountries -= 1;
            population.redactWorldPopulation(false);
        }
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
    public void setIsInfected(int date){
        this.isInfected=true;
        infectedCountries+=1;
        healthyCountries-=1;
        dateInfected = date;
    }
    public boolean getIsInfected(){
        return this.isInfected;
    }
    public int getHealthyCountries(){
        return healthyCountries;
    }
    public int getInfectedCountries(){
        return infectedCountries;
    }
    public void setZeroValues(){
        this.infectedCountries = 0;
        this.healthyCountries = 0;
    }
    public int getDateInfected(){
        return dateInfected;
    }
}