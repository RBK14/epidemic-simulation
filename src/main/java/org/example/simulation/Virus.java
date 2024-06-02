package org.example.simulation;

import org.example.agent.Agent;
import org.example.agent.Athlete;
import org.example.agent.Citizen;

import java.util.Random;

public class Virus {

    public final double transmissionRate;
    public final double mortalityRate;

    public Virus(double transmissionRate, double mortalityRate) {
        this.transmissionRate = transmissionRate;
        this.mortalityRate = mortalityRate;
    }

    public double getTransmissionRate() {
        return transmissionRate;
    }

    public void kill(Agent agent) {
        Random rand = new Random();

        if (agent instanceof Citizen && ((Citizen) agent).getRoundsAfterInfection() > 4) {
            if (rand.nextDouble() < mortalityRate) {
                agent.setHealthCondition("dead");
                System.out.println("Citizen[" + agent.getId() + "] was killed by virus");
            }
        } else if (agent instanceof Athlete && ((Athlete) agent).getRoundsAfterInfection() > 5) {
            if (rand.nextDouble() < 0.75 * mortalityRate) {
                agent.setHealthCondition("dead");
                System.out.println("Athlete[" + agent.getId() + "] has been killed by virus");
            }
        }
    }
}
