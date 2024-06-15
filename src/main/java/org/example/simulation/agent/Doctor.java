package org.example.simulation.agent;

import javafx.scene.paint.Color;
import org.example.simulation.Grid;
import org.example.simulation.Virus;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a doctor in the simulation.
 * Doctor has a unique movement method, can heal and vaccinate other agents.
 */
public class Doctor extends Agent {

    private final double healingProbability; // Probability of healing an infected agent
    private final double vaccinationProbability; // Probability of vaccination a healthy agent

    /**
     *  Constructor to initialize a doctor with an ID, grid and position.
     *  Doctor is immune by default
     *
     * @param id        Unique identifier for the doctor
     * @param grid      The grid the doctor is part of
     * @param posX      Initial X position of the doctor
     * @param posY      Initial Y position of yhe doctor
     */
    public Doctor(int id, Grid grid, int posX, int posY, Virus virus) {
        super(id, grid, posX, posY, virus);
        this.healthCondition = "immune";
        this.healingProbability = 0.5;
        this.vaccinationProbability = 0.4;
    }

    /**
     * Simulates the healing process.
     * Doctor can heal infected agent if there is any nearby.
     * Doctor can vaccinate healthy agent if there is any nearby.
     */
    public void heal() {
        Random rand = new Random();

        List<Agent> neighbours = new ArrayList<>();
        neighbours.addAll(grid.getAgentsAtPosition(posX, posY));
        neighbours.addAll(grid.getNeighbors(posX, posY));
        neighbours.remove(this);

        for (Agent neighbour : neighbours) {
            if (neighbour.getHealthCondition().equals("infected")) {
                if (rand.nextDouble() < healingProbability) {
                    neighbour.setHealthCondition("healthy");
                    System.out.println(neighbour.getClass().getSimpleName() + "[" + neighbour.getId()  + "] " + "has been healed by Doctor[" + this.id + "]");
                }
            } else if (neighbour.getHealthCondition().equals("healthy")) {
                if (rand.nextDouble() < vaccinationProbability) {
                    neighbour.setHealthCondition("immune");
                    System.out.println(neighbour.getClass().getSimpleName() + "[" + neighbour.getId()  + "] " + "has been vaccinated by Doctor[" + this.id + "]");
                }
            }
        }
    }

    /**
     * Moves the doctor to a new position on the grid.
     * Doctor moves towards the position of infected agent is there is any on the grid.
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
        System.out.println("Doctor[" + this.id + "]" + " moved to (" + posX + "," + posY + ") towards (" + targetX + "," + targetY + ")");
    }

    /**
     * Returns the color representing the doctor's presence on the simulation grid.
     *
     * @return the color DARKBLUE, representing a doctor agent
     */
    @Override
    public Color getColor() {
        return Color.DARKBLUE;
    }

    /**
     * Defines the doctor's behavior in each simulation step.
     */
    @Override
    public void step() {
        move();
        heal();
    }
}
