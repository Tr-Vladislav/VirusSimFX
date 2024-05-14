module org.example.virussimfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.virussimfx to javafx.fxml;
    exports org.example.virussimfx;
}