package org.example.simulation.agent;

import javafx.scene.paint.Color;
import org.example.simulation.Grid;
import org.example.simulation.Virus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a general agent in the simulation.
 * It contains basic methods inherited by other agent classes.
 * This abstract class serves as a base class for other classes representing specific types of agents.
 */
public abstract class Agent {

    /**
     * Unique identifier for the agent.
     */
    protected final int id;

    /**
     * Reference to the grid the agent is in.
     */
    protected final Grid grid;

    /**
     * The X coordinate of the agent's position on the grid.
     */
    protected int posX;

    /**
     * The Y coordinate of the agent's position on the grid.
     */
    protected int posY;

    /**
     * Health condition of the agent.
     */
    protected String healthCondition;

    /**
     *
     */
    protected Virus virus;

    /**
     * Indicates whether the agent is isolated and can move or spread the epidemic.
     */
    protected boolean isolated;

    /**
     * Constructor to initialize the agent with, ID, grid and position.
     *
     * @param id        Unique identifier for the agent
     * @param grid      The grid the agent is part of
     * @param posX      Initial X position of the agent
     * @param posY      Initial Y position of yhe agent
     */
    public Agent(int id, Grid grid, int posX, int posY, Virus virus) {
        this.id = id;
        this.grid = grid;
        this.posX = posX;
        this.posY = posY;
        this.healthCondition = "healthy";
        this.isolated = false;
        this.virus = virus;
    }

    /**
     * Gets the agent's ID.
     *
     * @return The agent's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the agent's X position.
     *
     * @return The agent's X position
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Sets the agent's X position.
     *
     * @param posX The new X position
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Gets the agent's Y position.
     *
     * @return The agent's Y position
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Sets the agent's Y position.
     *
     * @param posY The new Y position
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Gets the agent's health condition.
     *
     * @return The health condition
     */
    public String getHealthCondition() {
        return healthCondition;
    }

    /**
     * Sets the agent's health condition.
     *
     * @param healthCondition The new health condition
     */
    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    /**
     * Checks if the agent is isolated.
     *
     * @return True if the agent is isolated, false otherwise
     */
    public boolean isIsolated() {
        return isolated;
    }

    /**
     * Sets the agent's isolation status.
     *
     * @param isIsolated The new isolation status
     */
    public void setIsolated(boolean isIsolated) {
        this.isolated = isIsolated;
    }

    /**
     * Simulates the infection process for the athlete.
     * Agent can be infected if there are any infected agents nearby.
     *
     * @param transmissionFactor Factor which can decrease chance of infection
     */
    public void checkInfection(double transmissionFactor) {
        if (!healthCondition.equals("healthy")) {
            return;
        }

        Random rand = new Random();

        List<Agent> neighbours = new ArrayList<>();
        neighbours.addAll(grid.getAgentsAtPosition(posX, posY));
        neighbours.addAll(grid.getNeighbors(posX, posY));
        neighbours.remove(this);

        for (Agent neighbour : neighbours) {
            if (neighbour.getHealthCondition().equals("infected") && !neighbour.isIsolated()) {
                if (rand.nextDouble() < transmissionFactor * virus.getTransmissionRate()) {
                    this.healthCondition = "infected";
                    System.out.println("Athlete[" + id + "] has been infected by" + neighbour.getClass().getSimpleName() + "[" + neighbour.getId() + "]");
                }
            }
        }
    }

    /**
     * Basic method responsible for agents' movement.
     * Moves the agent by one position in random direction.
     * Method can be overridden in subclasses.
     */
    public void move() {

        Random rand = new Random();

        int newX = posX + rand.nextInt(3) - 1;
        int newY = posY + rand.nextInt(3) - 1;

        boolean isPosCorrect = (newX >= 0 && newX < grid.getMapSize()) && (newY >= 0 && newY < grid.getMapSize());

        if (isPosCorrect && !isolated && !healthCondition.equals("dead")) {
            grid.moveAgent(this, newX, newY);
            System.out.println(this.getClass().getSimpleName() + "[" + id + "]" + " moved to (" + newX + "," + newY + ")");
        }
    }

    /**
     * Returns the color representing the agent's health condition.
     * <p>
     * This method returns a specific color based on the agent's health condition:
     * - Black: if the agent is "dead"
     * - White: if the agent is "immune"
     * - Red: if the agent is "infected"
     * - Green: for any other health condition, which is assumed to be "healthy"
     * <p>
     * The colors are used to visually distinguish agents on the simulation grid.
     *
     * @return the color representing the agent's health condition
     */
    public Color getColor() {
        switch (this.getHealthCondition()) {
            case "dead" -> {
                return Color.BLACK;
            }
            case "immune" -> {
                return Color.WHITE;
            }
            case "infected" -> {
                return Color.RED;
            }
            default -> {
                return Color.GREEN;
            }
        }
    }

    /**
     * Abstract method for defining the agent's behaviour in each simulation step.
     * Must be implemented by subclasses.
     */
    public abstract void step();
}
