package org.example.simulation.agent;

import org.example.simulation.Grid;
import org.example.simulation.Virus;

/**
 * Represents a citizen in the simulation.
 * Citizen is a basic agent's class in the simulation.
 */
public class Citizen extends Agent {

    private int roundsAfterInfection; // Counts the number of rounds passed since infection

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
        super(id, grid, posX, posY, virus);
        this.roundsAfterInfection = 0;
        this.virus = virus;
    }

    /**
     * Sets the number of rounds passed since the citizen was infected.
     *
     * @param roundsAfterInfection The number of rounds passed since infection
     */
    public void setRoundsAfterInfection(int roundsAfterInfection) {
        this.roundsAfterInfection = roundsAfterInfection;
    }

    /**
     * Gets the number of rounds passed since the citizen was infected
     *
     * @return The number of rounds passed since infection
     */
    public int getRoundsAfterInfection() {
        return roundsAfterInfection;
    }

    /**
     * Defines the citizen's behavior in each simulation step.
     */
    @Override
    public void step() {
        super.move();
        checkInfection(1);

        if (this.roundsAfterInfection > 0) {
            virus.kill(this, 1);
        }

        if (healthCondition.equals("infected")) {
            this.roundsAfterInfection++;
        }  else {
            this.roundsAfterInfection = 0;
        }
    }
}
