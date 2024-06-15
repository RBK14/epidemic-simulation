package org.example.simulation;

import org.example.simulation.agent.Agent;
import org.example.simulation.agent.Doctor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the simulation grid where agents move and interact.
 * The grid manages the positions of agents and provides methods to manipulate them.
 */
public class Grid {

    private final int mapSize; // Size of the grid (map)
    List<Agent>[][] cells; // 2D array of lists of agents on each cell representing a position in the grid

    /**
     * Constructor to initialize the grid with a specified size.
     * Initializes each cell in the grid as an empty list of agents.
     *
     * @param mapSize The size of the grid
     */
    @SuppressWarnings("unchecked")
    public Grid(int mapSize) {
        this.mapSize = mapSize;

        cells = new ArrayList[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cells[i][j] = new ArrayList<>();
            }
        }
    }

    /**
     * Gets the size of the grid.
     *
     * @return The size of the grid
     */
    public int getMapSize() {
        return mapSize;
    }

    /**
     * Adds an agent to a specified position on the grid.
     *
     * @param agent The agent to add
     * @param x     The X coordinate of the agent's position
     * @param y     The Y coordinate of the agent's position
     */
    public void addAgent(Agent agent, int x, int y) {
        cells[x][y].add(agent);
    }

    /**
     * Moves an agent to a new position on the grid.
     * Updates the agent's coordinates and moves them to the new cell.
     *
     * @param agent The agent to move
     * @param newX  The new X coordinate of the agent's position
     * @param newY  The new Y coordinate of the agent's position
     */
    public void moveAgent(Agent agent, int newX, int newY) {
        if (agent == null) {
            return;
        }
        cells[agent.getPosX()][agent.getPosX()].remove(agent);
        agent.setPosX(newX);
        agent.setPosY(newY);
        cells[newX][newY].add(agent);
    }

    /**
     * Finds the closest infected agent relative to a specified position.
     *
     * @param x The X coordinate of the position
     * @param y The Y coordinate of the position
     * @return The closest infected agent, or null if none are found
     */
    public Agent getClosestInfected(int x, int y) {
        Agent closestInfected = null;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                for (Agent agent : cells[i][j]) {
                    if (agent.getHealthCondition().equals("infected")) {
                        double distance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestInfected = agent;
                        }
                    }
                }
            }
        }
        return closestInfected;
    }

    /**
     * Finds the closest Doctor relative to a specified position.
     *
     * @param x The X coordinate of the position
     * @param y The Y coordinate of the position
     * @return The closest Doctor, or null if none are found
     */
    public Agent getClosestDoctor(int x, int y) {
        Agent closestDoctor = null;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                for (Agent agent : cells[i][j]) {
                    if (agent instanceof Doctor) {
                        double distance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestDoctor = agent;
                        }
                    }
                }
            }
        }
        return closestDoctor;
    }

    /**
     * Gets all agents at a specified position on the grid.
     *
     * @param x The X coordinate of the position
     * @param y The Y coordinate of the position
     * @return A list of agents at the specified position
     */
    public List<Agent> getAgentsAtPosition(int x, int y) {
        return cells[x][y];
    }

    /**
     * Gets all neighboring agents around a specified position.
     * Includes agents in adjacent cells, excluding the cell itself.
     *
     * @param x The X coordinate of the position
     * @param y The Y coordinate of the position
     * @return A list of neighboring agents
     */
    public List<Agent> getNeighbors(int x, int y) {
        List<Agent> neighbors = new ArrayList<>();
        for (int i = Math.max(0, x - 1); i <= Math.min(mapSize - 1, x + 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(mapSize - 1, y + 1); j++) {
                if (x != i && y != j) {
                    neighbors.addAll(cells[i][j]);
                }
            }
        }
        return neighbors;
    }
}
