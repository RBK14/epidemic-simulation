package org.example.simulation;

import org.example.simulation.agent.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {

    private final List<Agent> population;
    private final Grid grid;
    private final Virus virus;
    private final int mapSize;
    private final int numOfCiv;
    private final int numOfMed;
    private final int numOfPolice;
    private final int numOfInfected;

    public Simulation(Virus virus, int mapSize, int numOfCiv, int numOfMed, int numOfPolice, int numOfInfected) {
        this.population = new ArrayList<>();
        this.grid = new Grid(mapSize);
        this.virus = virus;
        this.mapSize = mapSize;
        this.numOfCiv = numOfCiv;
        this.numOfMed = numOfMed;
        this.numOfPolice = numOfPolice;
        this.numOfInfected = numOfInfected;
    }

    public List<Agent> getPopulation() {
        return population;
    }

    public static void initializationMessage(Agent agent) {
        System.out.println(agent.getClass().getSimpleName() + "[" + agent.getId() + "] has been added to the population");
    }

    public void initialize() {

        Random rand = new Random();

        // Adding civilians to the population list.
        for (int i = 0; i < numOfCiv; i++) {
            int posX = rand.nextInt(mapSize);
            int posY = rand.nextInt(mapSize);

            if (i % 4 == 0) {
                Athlete athlete = new Athlete(i, grid, posX, posY, virus);
                population.add(athlete);
                grid.addAgent(athlete, posX, posY);
                initializationMessage(athlete);
            } else {
                Citizen citizen = new Citizen(i, grid, posX, posY, virus);
                population.add(citizen);
                grid.addAgent(citizen, posX, posY);
                initializationMessage(citizen);
            }
        }

        // Initialization of epidemic, changing agents health condition from healthy to infected.
        int infectedCounter = 0;
        while (infectedCounter < Math.min(numOfInfected, numOfCiv)) {
            int index = rand.nextInt(numOfCiv);
            if (population.get(index).getHealthCondition().equals("healthy")) {
                population.get(index).setHealthCondition("infected");
                infectedCounter++;
                System.out.println(population.get(index).getClass().getSimpleName() + "[" + population.get(index).getId() + "]" + " has been infected");
            }
        }

        // Adding medical staff to the population list.
        for (int i = 0; i < numOfMed; i++) {
            int posX = rand.nextInt(mapSize);
            int posY = rand.nextInt(mapSize);

            if (i % 5 == 0) {
                Doctor doctor = new Doctor(numOfCiv + i, grid, posX, posY);
                population.add(doctor);
                grid.addAgent(doctor, posX, posY);
                initializationMessage(doctor);
            } else {
                Nurse nurse = new Nurse(numOfCiv + i, grid, posX, posY);
                population.add(nurse);
                grid.addAgent(nurse, posX, posY);
                initializationMessage(nurse);
            }
        }

        // Adding police officers to the population list.
        for (int i = 0; i < numOfPolice; i++) {
            int posX = rand.nextInt(mapSize);
            int posY = rand.nextInt(mapSize);

            PoliceOfficer police = new PoliceOfficer(numOfCiv + numOfMed + i, grid, posX, posY);
            population.add(police);
            grid.addAgent(police, posX, posY);
            initializationMessage(police);
        }
    }

    public void step() {
        for (Agent agent : population) {
            agent.step();
            virus.kill(agent);
        }
        System.out.println();
    }

    @SuppressWarnings("unused")
    public void print() {
        String[][] map = new String[mapSize][mapSize];

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = ".";
            }
        }

        for (Agent agent : population) {
            if (agent instanceof Citizen || agent instanceof Athlete) {
                switch (agent.getHealthCondition()) {
                    case "infected" -> map[agent.getPosX()][agent.getPosY()] = "I";
                    case "dead" -> map[agent.getPosX()][agent.getPosY()] = "X";
                    case "immune" -> map[agent.getPosX()][agent.getPosY()] = "P";
                    default -> map[agent.getPosX()][agent.getPosY()] = "H";
                }
            } else if (agent instanceof Doctor) {
                map[agent.getPosX()][agent.getPosY()] = "D";
            } else if (agent instanceof Nurse) {
                map[agent.getPosX()][agent.getPosY()] = "N";
            } else if (agent instanceof PoliceOfficer) {
                map[agent.getPosX()][agent.getPosY()] = "O";
            }

        }

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
