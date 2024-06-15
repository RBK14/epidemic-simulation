package org.example.simulation;

import org.example.simulation.agent.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the main simulation that manages the population, grid, and virus interactions.
 * Initializes the simulation, steps through each round, and prints the current state.
 */
public class Simulation {

    private final List<Agent> population; // List of agents participating in the simulation
    private final Grid grid; // The grid where agents move and interact
    private final Virus virus; // The virus that affects the agents
    private final int mapSize; // Size of the simulation grid
    private final int numOfCiv; // Number of civilians in the simulation
    private final int numOfMed; // Number of medical staff in the simulation
    private final int numOfPolice; // Number of police officers in the simulation
    private final int numOfInfected; // Initial number of infected agents

    /**
     * Constructor to initialize the simulation with specified parameters.
     *
     * @param virus         The virus used in the simulation
     * @param mapSize       The size of the simulation grid
     * @param numOfCiv      The number of civilians
     * @param numOfMed      The number of medical staff
     * @param numOfPolice   The number of police officers
     * @param numOfInfected The initial number of infected agents
     */
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

    /**
     * Gets the list of all agents in the simulation.
     *
     * @return The list of agents
     */
    public List<Agent> getPopulation() {
        return population;
    }

    /**
     * Prints an initialization message for a newly added agent.
     *
     * @param agent The agent being added to the population
     */
    public static void initializationMessage(Agent agent) {
        if (agent != null) {
            System.out.println(agent.getClass().getSimpleName() + "[" + agent.getId() + "] has been added to the population");
        } else {
            System.out.println("Failed to add agent to the population");
        }

    }

    /**
     * Initializes the simulation by populating the grid with agents.
     * Adds civilians, medical staff, and police officers to the grid.
     * Sets the initial number of infected agents.
     */
    public void initialize() {

        Random rand = new Random();

        // Adding civilians to the population list
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

        // Initialization of epidemic, changing agents' health condition from healthy to infected
        int infectedCounter = 0;
        while (infectedCounter < Math.min(numOfInfected, numOfCiv)) {
            int index = rand.nextInt(numOfCiv);
            if (population.get(index).getHealthCondition().equals("healthy")) {
                population.get(index).setHealthCondition("infected");
                infectedCounter++;
                System.out.println(population.get(index).getClass().getSimpleName() + "[" + population.get(index).getId() + "]" + " has been infected");
            }
        }

        // Adding medical staff to the population list
        for (int i = 0; i < numOfMed; i++) {
            int posX = rand.nextInt(mapSize);
            int posY = rand.nextInt(mapSize);

            if (i % 5 == 0) {
                Doctor doctor = new Doctor(numOfCiv + i, grid, posX, posY, virus);
                population.add(doctor);
                grid.addAgent(doctor, posX, posY);
                initializationMessage(doctor);
            } else {
                Nurse nurse = new Nurse(numOfCiv + i, grid, posX, posY, virus);
                population.add(nurse);
                grid.addAgent(nurse, posX, posY);
                initializationMessage(nurse);
            }
        }

        // Adding police officers to the population list
        for (int i = 0; i < numOfPolice; i++) {
            int posX = rand.nextInt(mapSize);
            int posY = rand.nextInt(mapSize);

            PoliceOfficer police = new PoliceOfficer(numOfCiv + numOfMed + i, grid, posX, posY, virus);
            population.add(police);
            grid.addAgent(police, posX, posY);
            initializationMessage(police);
        }
    }

    /**
     * Advances the simulation by one step.
     * Executes all possible actions that an agent can perform during a turn and applies the virus effect.
     */
    public void step() {
        for (Agent agent : population) {
            agent.step();
        }
        System.out.println();
    }

    /**
     * Prints the current state of the simulation grid.
     * Displays each agent's type and health condition.
     */
    @SuppressWarnings("unused")
    public void print() {
        String[][] map = new String[mapSize][mapSize];

        // Initialize the map with empty cells
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = ".";
            }
        }

        // Mark the positions of each agent on the map
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

        // Print the map
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
