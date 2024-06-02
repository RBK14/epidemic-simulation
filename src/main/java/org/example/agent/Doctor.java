package org.example.agent;

import org.example.simulation.Grid;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Doctor extends Agent {

    public Doctor(int id, Grid grid, int posX, int posY) {
        super(id, grid, posX, posY);
        this.healthCondition = "immune";
    }

    public void heal() {
        Random rand = new Random();

        List<Agent> neighbours = new ArrayList<>();
        neighbours.addAll(grid.getAgentsAtPosition(posX, posY));
        neighbours.addAll(grid.getNeighbors(posX, posY));
        neighbours.remove(this);

        for (Agent neighbour : neighbours) {
            if (neighbour.getHealthCondition().equals("infected")) {
                if (rand.nextDouble() < 0.5) {
                    neighbour.setHealthCondition("healthy");
                    System.out.println(neighbour.getClass().getSimpleName() + "[" + neighbour.getId()  + "] " + "has been healed by Doctor[" + this.id + "]");
                }
            } else if (neighbour.getHealthCondition().equals("healthy")) {
                if (rand.nextDouble() < 0.4) {
                    neighbour.setHealthCondition("immune");
                    System.out.println(neighbour.getClass().getSimpleName() + "[" + neighbour.getId()  + "] " + "has been vaccinated by Doctor[" + this.id + "]");
                }
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
        System.out.println("Doctor[" + this.id + "]" + " moved to (" + posX + "," + posY + ") towards (" + targetX + "," + targetY + ")");
    }

    @Override
    public void step() {
        move();
        heal();
    }
}
