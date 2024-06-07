package org.example.simulation;

import org.example.simulation.agent.Agent;
import org.example.simulation.agent.Athlete;
import org.example.simulation.agent.Citizen;

import java.util.Random;

public class Virus {

    private final double transmissionRate;
    private final double mortalityRate;

    public Virus(double transmissionRate, double mortalityRate) {
        this.transmissionRate = transmissionRate;
        this.mortalityRate = mortalityRate;
    }

    public double getTransmissionRate() {
        return transmissionRate;
    }

    public void kill(Agent agent) {
        Random rand = new Random();

        if (agent instanceof Citizen && ((Citizen) agent).getRoundsAfterInfection() > 3) {
            if (rand.nextDouble() < mortalityRate) {
                agent.setHealthCondition("dead");
                System.out.println("Citizen[" + agent.getId() + "] was killed by virus");
            }
        } else if (agent instanceof Athlete && ((Athlete) agent).getRoundsAfterInfection() > 4) {
            if (rand.nextDouble() < 0.75 * mortalityRate) {
                agent.setHealthCondition("dead");
                System.out.println("Athlete[" + agent.getId() + "] has been killed by virus");
            }
        }
    }
}
