package org.example.simulation.agent;

import org.example.simulation.Grid;
import org.example.simulation.Virus;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a citizen in the simulation.
 * Citizen is a basic agent's class in the simulation.
 */
public class Citizen extends Agent {

    private int roundsAfterInfection; // Counts the number of rounds passed since infection
    private final Virus virus; // A virus that the agent may become infected with

    /**
     * Constructor to initialize a citizen with an ID, grid, position, and virus information.
     *
     * @param id        Unique identifier for the citizen
     * @param grid      The grid the citizen is part of
     * @param posX      Initial X position of the citizen
     * @param posY      Initial Y position of the citizen
     * @param virus     The virus object containing transmission and mortality rates
     */
    public Citizen(int id, Grid grid, int posX, int posY, Virus virus) {
        super(id, grid, posX, posY);
        this.roundsAfterInfection = 0;
        this.virus = virus;
    }

    public int getRoundsAfterInfection() {
        return roundsAfterInfection;
    }

    /**
     * Simulates the infection process for the citizen.
     * Citizen can be infected if there are any infected agents nearby.
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
                if (rand.nextDouble() < virus.getTransmissionRate()) {
                    this.healthCondition = "infected";
                    System.out.println("Citizen[" + id + "] has been infected by" + neighbour.getClass().getSimpleName() + "[" + neighbour.getId() + "]");
                }
            }
        }
    }

    /**
     * Defines the citizen's behavior in each simulation step.
     */
    @Override
    public void step() {
        super.move();
        checkInfection();

        if (healthCondition.equals("infected")) {
            this.roundsAfterInfection++;
        }  else {
            this.roundsAfterInfection = 0;
        }
    }
}
