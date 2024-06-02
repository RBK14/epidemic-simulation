package org.example.agent;

import org.example.simulation.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PoliceOfficer extends Agent {

    public PoliceOfficer(int id, Grid grid, int posX, int posY) {
        super(id, grid, posX, posY);
        this.healthCondition = "immune";
    }

    public void isolate() {
        Random rand = new Random();

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

    @Override
    @SuppressWarnings("DuplicatedCode")
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

    @Override
    public void step() {
        move();
        isolate();
    }
}
