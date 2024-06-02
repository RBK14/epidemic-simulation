package org.example.simulation;

import org.example.agent.Agent;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private final int mapSize;
    List<Agent>[][] cells;

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

    public int getMapSize() {
        return mapSize;
    }

    public void addAgent(Agent agent, int x, int y) {
        cells[x][y].add(agent);
    }

    public void moveAgent(Agent agent, int newX, int newY) {
        if (agent == null) {
            return;
        }
        cells[agent.getPosX()][agent.getPosX()].remove(agent);
        agent.setPosX(newX);
        agent.setPosY(newY);
        cells[newX][newY].add(agent);
    }

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

    public List<Agent> getAgentsAtPosition(int x, int y) {
        return cells[x][y];
    }

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
