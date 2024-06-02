package org.example.agent;

import org.example.simulation.Grid;

import java.util.Random;

public abstract class Agent {

    protected final int id;
    protected final Grid grid;
    protected int posX, posY;
    protected String healthCondition;
    protected boolean isolated;

    public Agent(int id, Grid grid, int posX, int positionY) {
        this.id = id;
        this.grid = grid;
        this.posX = posX;
        this.posY = positionY;
        this.healthCondition = "healthy";
        this.isolated = false;
    }

    public int getId() {
        return id;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public boolean isIsolated() {
        return isolated;
    }

    public void setIsolated(boolean isIsolated) {
        this.isolated = isIsolated;
    }

    public void move() {

        Random rand = new Random();

        int newX = posX + rand.nextInt(3) - 1;
        int newY = posY + rand.nextInt(3) - 1;

        boolean isPosCorrect = (newX >= 0 && newX < grid.getMapSize()) && (newY >= 0 && newY < grid.getMapSize());

        if (isPosCorrect && !isolated && !healthCondition.equals("dead")) {
            grid.moveAgent(this, newX, newY);
            System.out.println(this.getClass().getSimpleName() + "[" + id + "]" + " moved to (" + newX + "," + newY + ")");
        }
    }

    static boolean canInfect(Agent agent) {
        if (agent instanceof Citizen || agent instanceof Athlete) {
            return agent.getHealthCondition().equals("infected");
        } else {
            return false;
        }
    }

    public abstract void step();
}
