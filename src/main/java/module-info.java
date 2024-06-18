/**
 * Module definition for the Virus Simulation application.
 * This module uses JavaFX for the graphical user interface.
 */
module VirusSimulationFX {
    // Requires the JavaFX controls module for UI controls
    requires javafx.controls;
    // Requires the JavaFX FXML module for FXML support
    requires javafx.fxml;

    /**
     * Opens the GUI package to JavaFX FXML for reflection.
     * This allows the FXML loader to access the package's classes.
     */
    opens GUI to javafx.fxml;

    // Exports the GUI package, making it accessible to other modules
    exports GUI;
    // Exports the population package, making it accessible to other modules
    exports population;
    // Exports the virus package, making it accessible to other modules
    exports virus;
}
