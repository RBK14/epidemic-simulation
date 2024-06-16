package org.example.simulation.agent;

import javafx.scene.paint.Color;
import org.example.simulation.Grid;
import org.example.simulation.Virus;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a police officer in the simulation.
 * Police officer has a unique movement method and can isolate the infected agent.
 */
public class PoliceOfficer extends Agent {

    /**
     * Constructor to initialize a police officer with an ID, grid, and position.
     * Police officer is immune by default.
     *
     * @param id        Unique identifier for the police officer
     * @param grid      The gird the police officer is part of
     * @param posX      Initial position X of the police officer
     * @param posY      Initial position Y of the police officer
     */
    public PoliceOfficer(int id, Grid grid, int posX, int posY, Virus virus) {
        super(id, grid, posX, posY, virus);
        this.healthCondition = "immune";
    }

    /**
     * Simulates the isolation process.
     * Police officer can isolate infected agent if there is any nearby.
     */
    public void isolate() {
        List<Agent> neighbours = new ArrayList<>();
        neighbours.addAll(grid.getAgentsAtPosition(posX, posY));
        neighbours.addAll(grid.getNeighbors(posX, posY));
        neighbours.remove(this);

        for (Agent neighbour : neighbours) {
            if (neighbour.getHealthCondition().equals("infected") && !neighbour.isIsolated()) {
                neighbour.setIsolated(true);
                System.out.println("PoliceOfficer[" + this.id + "] imposed quarantine on " + neighbour.getClass().getSimpleName() + "[" + neighbour.getId() + "]");
            }
        }
    }

    /**
     * Moves the police officer to a new position on the grid.
     * Police officer move towards the position of infected agent is there is any on the grid.
     */
    @Override
    public void move() {
        Agent closestInfected = grid.getClosestInfected(this.posX, this.posY);

        if (closestInfected == null) {
            super.move();
            return;
        }

        int targetX = closestInfected.getPosX();
        int targetY = closestInfected.getPosY();

        if (this.posX < targetX) this.posX++;
        else if (this.posX > targetX) this.posX--;

        if (this.posY < targetY) this.posY++;
        else if (this.posY > targetY) this.posY--;

        grid.moveAgent(this, posX, posY);
        System.out.println("PoliceOfficer[" + id + "]" + " moved to (" + posX + "," + posY + ") towards (" + targetX + "," + targetY + ")");
    }

    /**
     * Returns the color representing the police officer's presence on the simulation grid.
     *
     * @return the color YELLOW, representing a police officer agent
     */
    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    /**
     * Defines the police officer's behaviour in each simulation step.
     */
    @Override
    public void step() {
        move();
        isolate();
    }
}
