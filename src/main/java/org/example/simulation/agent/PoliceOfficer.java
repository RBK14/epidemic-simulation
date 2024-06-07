package org.example.simulation.agent;

import org.example.simulation.Grid;
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
    public PoliceOfficer(int id, Grid grid, int posX, int posY) {
        super(id, grid, posX, posY);
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
            if (neighbour.getHealthCondition().equals("infected")) {
                neighbour.setIsolated(true);
                System.out.println("PoliceOfficer[" + this.id + "] imposed quarantine on " + neighbour.getClass().getSimpleName() + "[" + neighbour.getId() + "]");
            }
        }
    }

    /**
     * Moves the police officer to a new position on the grid.
     * Police officer moves towards the position of infected agent is there is any on the grid.
     */
    @Override
    public void move() {
        Agent closestInfected = grid.getClosestInfected(posX, posY);

        if (closestInfected == null) {
            super.move();
            return;
        }

        int targetX = closestInfected.getPosX();
        int targetY = closestInfected.getPosY();

        if (posX < targetX) posX++;
        else if (posX > targetX) posX--;

        if (posY < targetY) posY++;
        else if (posY > targetY) posY--;

        grid.moveAgent(this, posX, posY);
        System.out.println("PoliceOfficer[" + id + "]" + " moved to (" + posX + "," + posY + ") towards (" + targetX + "," + targetY + ")");
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
