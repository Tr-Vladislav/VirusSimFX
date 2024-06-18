package GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import population.*;

import java.util.ArrayList;

/**
 * Class representing the map in the simulation.
 * This class handles the creation and management of the map,
 * including countries and their states.
 */
public class Map {
    // Variable to keep track of the current date or simulation time
    private final int minTemp = -20;
    private final int maxTemp = 40;
    private int date;

    // World representation in the simulation
    private Country world;
    // Main controller for the GUI application
    private static MainController mainController;
    // List of lists to store countries on the map
    private ArrayList<ArrayList<Country>> countries;
    // Total number of countries on the map
    private int quantityCountries;
    // Width of the map
    private int Width;
    // Height of the map
    private int Height;
    // Radius for the hexagonal tiles on the map
    private int Radius;
    // Number of columns of hexagonal tiles on the map
    private int numCols;
    // Number of rows of hexagonal tiles on the map
    private int numRows;
    // The country that is currently picked or selected
    public Country pickedCountry;

    /**
     * Constructor to initialize the map with specified dimensions and tile radius.
     *
     * @param Width  the width of the map
     * @param Height the height of the map
     * @param Radius the radius of the hexagonal tiles
     */
    public Map(int Width, int Height, int Radius) {
        mainController = MainController.getInstance();
        quantityCountries = 0;
        countries = new ArrayList<>();
        this.Width = Width;
        this.Height = Height;
        this.Radius = Radius;
        mapCreate(Radius, Width, Height);
    }

    /**
     * Method to create the map with hexagonal tiles.
     *
     * @param hexRadius the radius of the hexagonal tiles
     * @param mapWidth  the width of the map
     * @param mapHeight the height of the map
     */
    public void mapCreate(double hexRadius, double mapWidth, double mapHeight) {
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

        // Loop to create and initialize the hexagonal map tiles
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
                Country country = new Country(hexagon, settlements[(row * col) % settlements.length], 100 + (int) (Math.random() * 800), Math.random(), getTemperature(row), false, Math.random());
                country.setColor(getColor(row));
                if (row == 0 || row >= (this.numRows - 2) || col == 0 || col >= this.numCols - 1) {
                    country.setIsCountry(false);
                    country.getArea().setFill(Color.GRAY);
                }
                quantityCountries += 1;
                country.row = row;
                country.col = col;
                mapRow.add(country);
            }
            countries.add(mapRow);
        }
        world = new Country("World");
        pickedCountry = world;
    }

    /**
     * Method to infect neighboring countries.
     *
     * @param infectedCountry the country that is currently infected
     */
    public void newInfected(Country infectedCountry) {
        Country country = countries.get(infectedCountry.row).get(infectedCountry.col - 1);
        if (country.getIsCountry() && !country.getIsInfected() && !country.population.isBorders()) {
            country.population.setInfected((int) Math.ceil(1 + Math.random() * 30));
            if (country.population.getInfected() > 0) country.setIsInfected(date);
        }
        country = countries.get(infectedCountry.row).get(infectedCountry.col + 1);
        if (country.getIsCountry() && !country.getIsInfected() && !country.population.isBorders()) {
            country.population.setInfected((int) Math.ceil(1 + Math.random() * 30));
            if (country.population.getInfected() > 0) country.setIsInfected(date);
        }
        country = countries.get(infectedCountry.row + 1).get(infectedCountry.col);
        if (country.getIsCountry() && !country.getIsInfected() && !country.population.isBorders()) {
            country.population.setInfected((int) Math.ceil(1 + Math.random() * 30));
            if (country.population.getInfected() > 0) country.setIsInfected(date);
        }
        country = countries.get(infectedCountry.row - 1).get(infectedCountry.col);
        if (country.getIsCountry() && !country.getIsInfected() && !country.population.isBorders()) {
            country.population.setInfected((int) Math.ceil(1 + Math.random() * 30));
            if (country.population.getInfected() > 0) country.setIsInfected(date);
        }
        country = countries.get(infectedCountry.row + nearlyHex(infectedCountry.col)).get(infectedCountry.col - 1);
        if (country.getIsCountry() && !country.getIsInfected() && !country.population.isBorders()) {
            country.population.setInfected((int) Math.ceil(1 + Math.random() * 30));
            if (country.population.getInfected() > 0) country.setIsInfected(date);
        }
        country = countries.get(infectedCountry.row + nearlyHex(infectedCountry.col)).get(infectedCountry.col + 1);
        if (country.getIsCountry() && !country.getIsInfected() && !country.population.isBorders()) {
            country.population.setInfected((int) Math.ceil(1 + Math.random() * 30));
            if (country.population.getInfected() > 0) country.setIsInfected(date);
        }
    }


    /**
     * Initialize the simulation date to zero.
     */
    public void dayZero() {
        date = 0;
    }

    /**
     * Get the current simulation date.
     *
     * @return the current simulation date
     */
    public int getDate() {
        return date;
    }

    /**
     * Increment the simulation date by one day.
     */
    public void newDay() {
        date += 1;
    }

    /**
     * Determine the row offset for hexagonal tiles.
     *
     * @param col the column index
     * @return the row offset for the given column
     */
    private int nearlyHex(int col) {
        return col % 2 == 1 ? 1 : -1;
    }
    /**
     * Calculates the temperature at a specific row in a grid.
     *
     * @param row the row number for which the temperature is being calculated
     * @return the temperature at the specified row
     */
    private double getTemperature(int row){
        double temp = maxTemp-((double)row/numRows*(maxTemp-minTemp));
        return temp;
    }
    /**
     * Set mouse event handlers for countries.
     *
     * @param type    the type of event ("setGray" or "Pick")
     * @param isEvent whether the event should be enabled or disabled
     */
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

    /**
     * Set the picked country and highlight it.
     *
     * @param country the country to be picked
     */
    public void setPickedCountry(Country country) {
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

    /**
     * Get the color based on the row number.
     *
     * @param row the row number
     * @return the color for the given row
     */
    private Paint getColor(int row) {
        int hot_r = 250, hot_g = 152, hot_b = 82;
        int warm_r = 91, warm_g = 235, warm_b = 106;
        int cold_r = 100, cold_g = 205, cold_b = 250;

        int r, g, b;
        if (row < numRows / 2) {
            r = hot_r - Math.abs((hot_r - warm_r)) * row * 2 / numRows;
            g = hot_g + Math.abs((hot_g - warm_g)) * row * 2 / numRows;
            b = hot_b + Math.abs((hot_b - warm_b)) * row * 2 / numRows;
        } else {
            r = warm_r + Math.abs((warm_r - cold_r)) * row / numRows;
            g = warm_g - Math.abs((warm_g - cold_g)) * row / numRows;
            b = warm_b + Math.abs((warm_b - cold_b)) * row / numRows;
        }
        return Color.rgb(r, g, b);
    }

    /**
     * Create a hexagonal shape.
     *
     * @param x      the x-coordinate of the center
     * @param y      the y-coordinate of the center
     * @param radius the radius of the hexagon
     * @return the created hexagon
     */
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

    /**
     * Get a list of all countries on the map.
     *
     * @return the list of all countries
     */
    public ArrayList<Country> getCountries() {
        ArrayList<Country> map = new ArrayList<>();
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                map.add(countries.get(row).get(col));
            }
        }
        return map;
    }

    /**
     * Get the world country object.
     *
     * @return the world country object
     */
    public Country getWorld() {
        return world;
    }
}
/**
 * Class representing a country in the simulation.
 * This class handles the attributes and behaviors of a country,
 * including its population, infection status, and visual representation on the map.
 */
class Country {
    // Date when the country got infected
    private int dateInfected;
    // Static counter for healthy countries
    private static int healthyCountries = 0;
    // Static counter for infected countries
    private static int infectedCountries = 0;
    // Whether the country is infected
    private boolean isInfected;
    // Row on the map
    public int row;
    // Column on the map
    public int col;
    // Population of the country
    public Population population;

    // Event handler for mouse events when the country is set to gray
    private EventHandler<MouseEvent> eventSetGray;
    // Event handler for mouse events when the country is picked
    private EventHandler<MouseEvent> eventPick;
    // Color of the country on the map
    private Paint color;
    // Shape of the country on the map
    private Polygon area;
    // Whether the object is a country
    private boolean isCountry;

    /**
     * Constructor to create a country with various attributes.
     *
     * @param hexagon            the shape of the country on the map
     * @param countryName        the name of the country
     * @param quantity           the initial population size
     * @param stability          the stability level of the country
     * @param averageTemperature the average temperature in the country
     * @param borders            whether the country has borders
     * @param medicalLevel       the medical level in the country
     */
    public Country(Polygon hexagon, String countryName, int quantity, double stability, double averageTemperature, boolean borders, double medicalLevel) {
        this.dateInfected = 0;
        healthyCountries += 1;
        this.isInfected = false;
        this.area = hexagon;
        this.isCountry = true;
        population = new Population(countryName, quantity, averageTemperature);
    }

    /**
     * Constructor to create a country with just a name.
     *
     * @param countryName the name of the country
     */
    public Country(String countryName) {
        this.isInfected = false;
        this.area = new Polygon();
        this.isCountry = false;
        this.population = new Population(countryName);
    }

    /**
     * Get the shape of the country.
     *
     * @return the shape of the country
     */
    public Polygon getArea() {
        return area;
    }

    /**
     * Set whether the object is a country.
     *
     * @param isCountry whether the object is a country
     */
    public void setIsCountry(boolean isCountry) {
        this.isCountry = isCountry;
        if (isCountry) {
            healthyCountries += 1;
            population.redactWorldPopulation(true);
        } else {
            healthyCountries -= 1;
            population.redactWorldPopulation(false);
        }
    }

    /**
     * Get whether the object is a country.
     *
     * @return whether the object is a country
     */
    public boolean getIsCountry() {
        return isCountry;
    }

    /**
     * Set the color of the country.
     *
     * @param color the color to set
     */
    public void setColor(Paint color) {
        this.color = color;
    }

    /**
     * Get the color of the country.
     *
     * @return the color of the country
     */
    public Paint getColor() {
        return this.color;
    }

    /**
     * Set the event handler when the country is set to gray.
     *
     * @param eventSetGray the event handler to set
     */
    public void setEventSetGray(EventHandler<MouseEvent> eventSetGray) {
        this.eventSetGray = eventSetGray;
    }

    /**
     * Get the event handler when the country is set to gray.
     *
     * @return the event handler when the country is set to gray
     */
    public EventHandler<MouseEvent> getEventSetGray() {
        return eventSetGray;
    }

    /**
     * Set the event handler when the country is picked.
     *
     * @param eventPick the event handler to set
     */
    public void setEventPick(EventHandler<MouseEvent> eventPick) {
        this.eventPick = eventPick;
    }

    /**
     * Get the event handler when the country is picked.
     *
     * @return the event handler when the country is picked
     */
    public EventHandler<MouseEvent> getEventPick() {
        return eventPick;
    }

    /**
     * Set the country as infected.
     *
     * @param date the date when the country got infected
     */
    public void setIsInfected(int date) {
        this.isInfected = true;
        infectedCountries += 1;
        healthyCountries -= 1;
        dateInfected = date;
    }

    /**
     * Get whether the country is infected.
     *
     * @return whether the country is infected
     */
    public boolean getIsInfected() {
        return this.isInfected;
    }

    /**
     * Get the number of healthy countries.
     *
     * @return the number of healthy countries
     */
    public int getHealthyCountries() {
        return healthyCountries;
    }

    /**
     * Get the number of infected countries.
     *
     * @return the number of infected countries
     */
    public int getInfectedCountries() {
        return infectedCountries;
    }

    /**
     * Reset the counters for infected and healthy countries.
     */
    public void setZeroValues() {
        this.infectedCountries = 0;
        this.healthyCountries = 0;
    }

    /**
     * Get the date when the country got infected.
     *
     * @return the date when the country got infected
     */
    public int getDateInfected() {
        return dateInfected;
    }

}

