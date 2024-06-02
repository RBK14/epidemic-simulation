package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.agent.*;
import org.example.simulation.Simulation;
import org.example.simulation.Virus;

public class Controller {

    @FXML
    private Canvas canvas;
    @FXML
    private Label scoreLabel;
    @FXML
    private TextField mapSizeField;
    @FXML
    private TextField civilNumField;
    @FXML
    private TextField medNumField;
    @FXML
    private TextField policeNumField;
    @FXML
    private TextField infectedNumField;
    @FXML
    private TextField transmissionField;
    @FXML
    private TextField mortalityField;

    private Timeline timeline;
    private static int sizeOfMap;
    private static double cellSize;
    private Simulation simulation;
    private static boolean isRunning = false;
    private static int numberOfDeadAgents;
    private static int numberOfCivil;

    @FXML
    private void handleStartSimulation() {
        if (!isRunning) {
            initializeSimulation();
        }

        timeline.play();
    }

    @FXML
    private void handleStopSimulation() {
        timeline.stop();
    }

    private void initializeSimulation() {
        int numberOfMedic;
        int numberOfPolice;
        int numberOfInfected;
        double transmissionRate;
        double mortalityRate;

        // Pobranie wartości startowych z aplikacji
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

        // Obliczenie rozmiaru komorki
        cellSize = (double)580 / sizeOfMap;

        // Utworzenie wirusa i symulacji
        Virus virus = new Virus(transmissionRate, mortalityRate);
        simulation = new Simulation(virus, sizeOfMap, numberOfCivil, numberOfMedic, numberOfPolice, numberOfInfected);

        // Inicjalizacja symulacji
        simulation.initialize();

        timeline = new Timeline(new KeyFrame(Duration.millis(750), e -> runSimulationStep()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        isRunning = true;

        drawGrid();
    }

    private void runSimulationStep() {
        simulation.step();
        drawGrid();

        double score = ((double) numberOfDeadAgents / numberOfCivil) * 100;
        scoreLabel.setText(String.format("%.2f%%", score));
        numberOfDeadAgents = 0;
    }

    private void drawGrid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Agent[][] map = new Agent[sizeOfMap][sizeOfMap];

        for (Agent agent : simulation.getPopulation()) {
            map[agent.getPosX()][agent.getPosY()] = agent;

            if (agent.getHealthCondition().equals("dead")) {
                numberOfDeadAgents++;
            }
        }

        for (int i = 0; i < sizeOfMap; i++) {
            for (int j = 0; j < sizeOfMap; j++) {

                Agent agentOnField = map[i][j];

                if (agentOnField != null) {
                    if (agentOnField instanceof Citizen || agentOnField instanceof Athlete) {
                        switch (agentOnField.getHealthCondition()) {
                            case "dead" -> {
                                gc.setFill(Color.BLACK);
                                gc.fillOval(i * cellSize, j * cellSize, cellSize, cellSize);
                            }
                            case "immune" -> {
                                gc.setFill(Color.WHITE);
                                gc.fillOval(i * cellSize, j * cellSize, cellSize, cellSize);
                            }
                            case "healthy" -> {
                                gc.setFill(Color.GREEN);
                                gc.fillOval(i * cellSize, j * cellSize, cellSize, cellSize);
                            }
                            default -> {
                                gc.setFill(Color.RED);
                                gc.fillOval(i * cellSize, j * cellSize, cellSize, cellSize);
                            }
                        }
                    } else if (agentOnField instanceof Nurse) {
                        gc.setFill(Color.CYAN);
                        gc.fillOval(i * cellSize, j * cellSize, cellSize, cellSize);
                    } else if (agentOnField instanceof PoliceOfficer) {
                        gc.setFill(Color.YELLOW);
                        gc.fillOval(i * cellSize, j * cellSize, cellSize, cellSize);
                    } else if (agentOnField instanceof Doctor) {
                        gc.setFill(Color.DARKBLUE);
                        gc.fillOval(i * cellSize, j * cellSize, cellSize, cellSize);
                    }
                }
            }
        }
    }
}
