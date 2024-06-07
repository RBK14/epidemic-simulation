package org.example.simulation;

import org.example.simulation.agent.Agent;
import org.example.simulation.agent.Athlete;
import org.example.simulation.agent.Citizen;
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
     * Attempts to kill an infected agent, based on the virus's mortality rate and agent's rounds after infection.
     *
     * @param agent The agent potential to kill
     */
    public void kill(Agent agent) {
        Random rand = new Random();

        // Citizens have a chance of dying if more than 3 rounds have passed since infection
        if (agent instanceof Citizen && ((Citizen) agent).getRoundsAfterInfection() > 3) {
            if (rand.nextDouble() < mortalityRate) {
                agent.setHealthCondition("dead");
                System.out.println("Citizen[" + agent.getId() + "] was killed by virus");
            }
        // Athletes have a chance of dying if more than 4 rounds have passed since infection
        // Their mortality rate is reduced by 25%
        } else if (agent instanceof Athlete && ((Athlete) agent).getRoundsAfterInfection() > 4) {
            if (rand.nextDouble() < 0.75 * mortalityRate) {
                agent.setHealthCondition("dead");
                System.out.println("Athlete[" + agent.getId() + "] has been killed by virus");
            }
        }
    }
}
