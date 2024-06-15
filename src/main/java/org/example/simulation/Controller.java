package org.example.simulation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.example.simulation.agent.*;

/**
 * Controller class for managing the simulation GUI.
 * Handles user inputs, initializes the simulation, and updates the display.
 */
public class Controller {

    @FXML
    private Canvas canvas; // The canvas for drawing the grid
    @FXML
    private Label scoreLabel; // Label for displaying the score (percentage of dead civilians)
    @FXML
    private TextField mapSizeField; // TextField for inputting the map size
    @FXML
    private TextField civilNumField; // TextField for inputting the number of civilians
    @FXML
    private TextField medNumField; // TextField for inputting the number of medical staff
    @FXML
    private TextField policeNumField; // TextField for inputting the number of police officers
    @FXML
    private TextField infectedNumField; // TextField for inputting the number of initially infected agents
    @FXML
    private TextField transmissionField; // TextField for inputting the virus transmission rate
    @FXML
    private TextField mortalityField; // TextField for inputting the virus mortality rate

    private Timeline timeline; // Timeline for managing the simulation steps
    private static int sizeOfMap; // The size of the grid (map)
    private static double cellSize; // The size of each cell in the grid
    private Simulation simulation; // The simulation instance
    private static int numberOfInfected; // Counter for the number of dead and infected civilians

    /**
     * Checks if the simulation is currently running.
     * <p>
     * This method checks the status of the timeline object, which is responsible
     * for controlling the simulation steps. If the timeline is not null and its
     * status is `Animation.Status.RUNNING`, this indicates that the simulation
     * is currently active and running.
     *
     * @return true if the simulation timeline is running, false otherwise
     */
    @FXML
    private boolean isSimulationRunning() {
        return timeline != null && timeline.getStatus() == Animation.Status.RUNNING;
    }

    /**
     * Handles the start button click event.
     * Initializes and starts the simulation if it's not already running.
     */
    @FXML
    private void handleStartSimulation() {
        if (!isSimulationRunning()) {
            initializeSimulation();
            timeline.play();
        }
    }

    /**
     * Handles the stop button click event.
     * Stops the simulation timeline.
     */
    @FXML
    private void handleStopSimulation() {
        timeline.stop();
    }

    /**
     * Initializes the simulation with parameters from the GUI.
     */
    private void initializeSimulation() {
        int numberOfCivil;
        int numberOfMedic;
        int numberOfPolice;
        int numberOfInfected;
        double transmissionRate;
        double mortalityRate;

        // Retrieve initial values from the application
        // Total number of civilians
        try {
            sizeOfMap = Integer.parseInt(mapSizeField.getText());
            numberOfCivil = Integer.parseInt(civilNumField.getText());
            numberOfMedic = Integer.parseInt(medNumField.getText());
            numberOfPolice = Integer.parseInt(policeNumField.getText());
            numberOfInfected = Integer.parseInt(infectedNumField.getText());
            transmissionRate = Double.parseDouble(transmissionField.getText());
            mortalityRate = Double.parseDouble(mortalityField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Incorrect setup parameters");
            return;
        }

        // Calculate cell size
        cellSize = (double)580 / sizeOfMap;

        // Create virus and simulation
        Virus virus = new Virus(transmissionRate, mortalityRate);
        simulation = new Simulation(virus, sizeOfMap, numberOfCivil, numberOfMedic, numberOfPolice, numberOfInfected);

        // Initialize the simulation
        simulation.initialize();

        timeline = new Timeline(new KeyFrame(Duration.millis(750), e -> runSimulationStep()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        drawGrid();
    }

    /**
     * Advances the simulation by one step and updates the display.
     */
    private void runSimulationStep() {
        simulation.step();
        drawGrid();

        scoreLabel.setText(String.valueOf(numberOfInfected));
        numberOfInfected = 0;
    }

    /**
     * Draws the current state of the grid on the canvas.
     */
    private void drawGrid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Agent[][] map = new Agent[sizeOfMap][sizeOfMap];

        for (Agent agent : simulation.getPopulation()) {
            map[agent.getPosX()][agent.getPosY()] = agent;

            if (agent.getHealthCondition().equals("dead") || agent.getHealthCondition().equals("infected")) {
                numberOfInfected++;
            }
        }

        for (int i = 0; i < sizeOfMap; i++) {
            for (int j = 0; j < sizeOfMap; j++) {

                if (map[i][j] != null) {
                    gc.setFill(map[i][j].getColor());
                    gc.fillOval(i * cellSize, j * cellSize, cellSize, cellSize);
                }
            }
        }
    }
}
