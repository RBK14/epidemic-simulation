package org.example.agent;

import org.example.simulation.Grid;
import org.example.simulation.Virus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Citizen extends Agent {

    private int roundsAfterInfection;
    private final Virus virus;

    public Citizen(int id, Grid grid, int posX, int posY, Virus virus) {
        super(id, grid, posX, posY);
        this.roundsAfterInfection = 0;
        this.virus = virus;
    }

    public int getRoundsAfterInfection() {
        return roundsAfterInfection;
    }

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
            if (canInfect(neighbour)) {
                if (rand.nextDouble() < virus.getTransmissionRate()) {
                    this.healthCondition = "infected";
                    System.out.println("Citizen[" + id + "] has been infected by" + neighbour.getClass().getSimpleName() + "[" + neighbour.getId() + "]");
                }
            }
        }
    }

    @Override
    public void step() {
        super.move();
        checkInfection();

        if (healthCondition.equals("infected")) {
            this.roundsAfterInfection++;
        }
    }
}
