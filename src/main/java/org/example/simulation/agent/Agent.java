package org.example.simulation.agent;

import org.example.simulation.Grid;
import java.util.Random;

/**
 * Represents a general agent in the simulation.
 * It contains basic methods inherited by other agent classes.
 * This abstract class serves as a base class for other classes representing specific types of agents.
 */
public abstract class Agent {

    protected final int id; // Unique identifier for the agent
    protected final Grid grid; // Reference to grid the agent is in
    protected int posX, posY; // Position of the agent on the grid
    protected String healthCondition; // Health condition of the agent
    protected boolean isolated; // Whether is agent isolated, can move or spread epidemic

    /**
     * Constructor to initialize the agent with, ID, grid and position.
     *
     * @param id        Unique identifier for the agent
     * @param grid      The grid the agent is part of
     * @param posX      Initial X position of the agent
     * @param posY      Initial Y position of yhe agent
     */
    public Agent(int id, Grid grid, int posX, int posY) {
        this.id = id;
        this.grid = grid;
        this.posX = posX;
        this.posY = posY;
        this.healthCondition = "healthy";
        this.isolated = false;
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
     * Abstract method for defining the agent's behaviour in each simulation step.
     * Must be implemented by subclasses.
     */
    public abstract void step();
}
