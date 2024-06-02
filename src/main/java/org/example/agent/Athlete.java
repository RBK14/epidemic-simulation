package org.example.agent;

import org.example.simulation.Grid;
import org.example.simulation.Virus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Athlete extends Agent {

    private int roundsAfterInfection;
    private final Virus virus;

    public Athlete(int id, Grid grid, int posX, int posY, Virus virus) {
        super(id, grid, posX, posY);
        this.roundsAfterInfection = 0;
        this.virus = virus;
    }

    public int getRoundsAfterInfection() {
        return roundsAfterInfection;
    }

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
                if (rand.nextDouble() < 0.75 * virus.getTransmissionRate()) {
                    this.healthCondition = "infected";
                    System.out.println("Athlete[" + id + "] has been infected by" + neighbour.getClass().getSimpleName() + "[" + neighbour.getId() + "]");
                }
            }
        }
    }

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
