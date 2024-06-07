package org.example.simulation.agent;

import org.example.simulation.Grid;
import org.example.simulation.Virus;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents an athlete in the simulation.
 * Athletes can move more quickly and have a less chance of becoming infected and die from infection.
 */
public class Athlete extends Agent {

    private int roundsAfterInfection; // Counts the number of rounds passed since infection
    private final Virus virus; // A virus that the agent may become infected with

    /**
     * Constructor to initialize an athlete with an ID, grid, position, and virus information.
     *
     * @param id      Unique identifier for the athlete
     * @param grid    The grid the athlete is part of
     * @param posX    Initial X position of the athlete
     * @param posY    Initial Y position of the athlete
     * @param virus   The virus object containing transmission and mortality rate
     */
    public Athlete(int id, Grid grid, int posX, int posY, Virus virus) {
        super(id, grid, posX, posY);
        this.roundsAfterInfection = 0;
        this.virus = virus;
    }

    /**
     * Gets the number of rounds passed since the athlete was infected.
     *
     * @return The number of rounds passed since infection
     */
    public int getRoundsAfterInfection() {
        return roundsAfterInfection;
    }

    /**
     * Moves the athlete to a new random position on the grid.
     * Athletes can move up to two positions in any direction.
     */
    @Override
    public void move() {
        Random rand = new Random();

        int newX = posX + rand.nextInt(5) - 2;
        int newY = posY + rand.nextInt(5) - 2;

        boolean isPosCorrect = (newX >= 0 && newX < grid.getMapSize()) && (newY >= 0 && newY < grid.getMapSize());

        if ((!healthCondition.equals("dead") && !isolated) && isPosCorrect) {
            grid.moveAgent(this, newX, newY);
            System.out.println("Athlete[" + id + "]" + " moved to (" + newX + "," + newY + ")");
        }
    }

    /**
     * Simulates the infection process for the athlete.
     * Athlete can be infected if there are any infected agents nearby.
     */
    @SuppressWarnings("DuplicatedCode")
    public void checkInfection() {
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
                if (rand.nextDouble() < 0.75 * virus.getTransmissionRate()) {
                    this.healthCondition = "infected";
                    System.out.println("Athlete[" + id + "] has been infected by" + neighbour.getClass().getSimpleName() + "[" + neighbour.getId() + "]");
                }
            }
        }
    }

    /**
     * Defines the athlete's behavior in each simulation step.
     */
    @Override
    public void step() {
        move();
        checkInfection();

        if (healthCondition.equals("infected")) {
            this.roundsAfterInfection++;
        } else {
            this.roundsAfterInfection = 0;
        }
    }
}
