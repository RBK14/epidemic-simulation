package org.example.simulation.agent;

import javafx.scene.paint.Color;
import org.example.simulation.Grid;
import org.example.simulation.Virus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a nurse in the simulation.
 * Nurse can vaccinate other agents.
 */
public class Nurse extends Agent {

    private final double vaccinationProbability; // Probability of vaccination a healthy agent

    /**
     * Constructor to initialize a nurse with an ID, grid, and position.
     * Nurse is immune by default.
     *
     * @param id        Unique identifier for the nurse
     * @param grid      The grid the nurse is part of
     * @param posX      Initial X position of the nurse
     * @param posY      Initial Y position of the nurse
     */
    public Nurse(int id, Grid grid, int posX, int posY, Virus virus) {
        super(id, grid, posX, posY, virus);
        this.healthCondition = "immune";
        this.vaccinationProbability = 0.4;
    }

    /**
     * Simulates the vaccination process.
     * Nurse can vaccinate healthy agents if there is any nearby.
     */
    public void vaccinate() {
        Random rand  = new Random();

        List<Agent> neighbours = new ArrayList<>();
        neighbours.addAll(grid.getAgentsAtPosition(posX, posY));
        neighbours.addAll(grid.getNeighbors(posX, posY));
        neighbours.remove(this);

        for (Agent neighbour : neighbours) {
            if (neighbour.getHealthCondition().equals("healthy")) {
                if (rand.nextDouble() < vaccinationProbability) {
                    neighbour.setHealthCondition("immune");
                    System.out.println(neighbour.getClass().getSimpleName() + "[" + neighbour.getId()  + "] " + "has been vaccinated by Nurse[" + this.id + "]");
                }
            }
        }
    }

    /**
     * Returns the color representing the nurse's presence on the simulation grid.
     *
     * @return the color CYAN, representing a nurse agent
     */
    @Override
    public Color getColor() {
        return Color.CYAN;
    }

    /**
     * Defines the nurse's behaviour in each simulation step.
     */
    @Override
    public void step() {
        super.move();
        vaccinate();
    }
}
