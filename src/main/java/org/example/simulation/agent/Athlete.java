package org.example.simulation.agent;

import org.example.simulation.Grid;
import org.example.simulation.Virus;
import java.util.Random;

/**
 * Represents an athlete in the simulation.
 * Athletes can move more quickly and have a less chance of becoming infected and die from infection.
 */
public class Athlete extends Agent {

    private int roundsAfterInfection; // Counts the number of rounds passed since infection

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
        super(id, grid, posX, posY, virus);
        this.roundsAfterInfection = 0;
        this.virus = virus;
    }

    /**
     * Moves the athlete to a new random position on the grid.
     * Athletes move towards the position of the nearest Doctor
     * Athletes can move up to two positions in any direction.
     */
    @Override
    public void move() {
        Agent closestDoctor = grid.getClosestDoctor(this.posX, this.posY);

        if (closestDoctor == null || this.getHealthCondition().equals("immune")) {
            Random rand = new Random();

            int newX = posX + rand.nextInt(5) - 2;
            int newY = posY + rand.nextInt(5) - 2;

            boolean isPosCorrect = (newX >= 0 && newX < grid.getMapSize()) && (newY >= 0 && newY < grid.getMapSize());

            if ((!healthCondition.equals("dead") && !isolated) && isPosCorrect) {
                grid.moveAgent(this, newX, newY);
                System.out.println("Athlete[" + id + "]" + " moved to (" + newX + "," + newY + ")");
            }
            return;
        }

        int targetX = closestDoctor.getPosX();
        int targetY = closestDoctor.getPosY();

        int newX;
        int newY;

        if (this.posX < targetX) newX = this.posX + 2;
        else if (this.posX > targetX) newX = this.posX - 2;
        else newX = this.posX;

        if (this.posY < targetY) newY = this.posY + 2;
        else if (this.posY > targetY) newY = this.posY - 2;
        else newY = this.posY;

        boolean isPosCorrect = (newX >= 0 && newX < grid.getMapSize()) && (newY >= 0 && newY < grid.getMapSize());

        if ((!healthCondition.equals("dead") && !isolated) && isPosCorrect) {
            grid.moveAgent(this, newX, newY);
            System.out.println("Athlete[" + id + "]" + " moved to (" + newX + "," + newY + ") towards (" + targetX + "," + targetY + ")");
        }
    }

    /**
     * Defines the athlete's behavior in each simulation step.
     */
    @Override
    public void step() {
        move();
        checkInfection(0.75);

        if (this.roundsAfterInfection > 0) {
            virus.kill(this, 0.75);
        }

        if (healthCondition.equals("infected")) {
            this.roundsAfterInfection++;
        } else {
            this.roundsAfterInfection = 0;
        }
    }
}
