package org.example.simulation;

import org.example.simulation.agent.Agent;
import java.util.Random;

/**
 * Representing a virus in the simulation.
 * Contains information about transmission and mortality rates and methods to handle infection.
 */
public class Virus {

    private final double transmissionRate; // Probability od transmitting the virus
    private final double mortalityRate; // Probability of dying from the virus

    /**
     * Constructor to initialize the virus with transmission and mortality rates.
     *
     * @param transmissionRate      Probability of transmitting the virus
     * @param mortalityRate         Probability of dying from the virus
     */
    public Virus(double transmissionRate, double mortalityRate) {
        this.transmissionRate = transmissionRate;
        this.mortalityRate = mortalityRate;
    }

    /**
     * Gets the transmission rate of the virus
     *
     * @return The transmission rate
     */
    public double getTransmissionRate() {
        return transmissionRate;
    }

    /**
     * Attempts to kill an infected agent, based on the virus's mortality rate.
     *
     * @param agent The agent potential to kill
     * @param mortalityFactor Factor which can decrease chance of agent's death
     */
    public void kill(Agent agent, double mortalityFactor) {
        Random rand = new Random();

        if (agent.getHealthCondition().equals("infected")) {
            if (rand.nextDouble() < mortalityFactor * mortalityRate) {
                agent.setHealthCondition("dead");
                System.out.println(agent.getClass().getSimpleName() + "[" + agent.getId() + "] was killed by virus");
            }
        }
    }
}
