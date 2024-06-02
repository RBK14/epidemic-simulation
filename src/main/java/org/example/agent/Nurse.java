package org.example.agent;

import org.example.simulation.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nurse extends Agent {

    public Nurse(int id, Grid grid, int posX, int posY) {
        super(id, grid, posX, posY);
        this.healthCondition = "immune";
    }

    public void vaccinate() {
        Random rand  = new Random();

        List<Agent> neighbours = new ArrayList<>();
        neighbours.addAll(grid.getAgentsAtPosition(posX, posY));
        neighbours.addAll(grid.getNeighbors(posX, posY));
        neighbours.remove(this);

        for (Agent neighbour : neighbours) {
            if (neighbour.getHealthCondition().equals("healthy")) {
                if (rand.nextDouble() < 0.4) {
                    neighbour.setHealthCondition("immune");
                    System.out.println(neighbour.getClass().getSimpleName() + "[" + neighbour.getId()  + "] " + "has been vaccinated by Nurse[" + this.id + "]");
                }
            }
        }
    }

    @Override
    public void step() {
        super.move();
        vaccinate();
    }
}
