module org.example.simulation {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.simulation to javafx.fxml;

    exports org.example.simulation;
    exports org.example.simulation.agent;
}