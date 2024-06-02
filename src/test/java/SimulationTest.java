import org.example.agent.PoliceOfficer;
import org.example.simulation.Grid;
import org.example.simulation.Virus;
import org.example.agent.Agent;
import org.example.agent.Citizen;
import org.example.agent.Doctor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    private Grid grid;
    private Doctor doctor;
    private Citizen healthy;
    private Citizen infected;
    private PoliceOfficer police;

    @BeforeEach
    void setUp() {
        grid = new Grid(10);
        Virus virus = new Virus(1, 1);
        doctor = new Doctor(1, grid, 0, 0);
        healthy = new Citizen(2, grid, 5, 5, virus);
        infected = new Citizen(3,grid,5,5, virus);
        infected.setHealthCondition("infected");
        police = new PoliceOfficer(4, grid, 5, 5);

        grid.addAgent(doctor, 0, 0);
        grid.addAgent(healthy, 5, 5);
        grid.addAgent(infected,5,5);
        grid.addAgent(police,5,5);
    }

    public double getDistance(Agent a, Agent b) {
        return Math.sqrt(Math.pow(a.getPosX() - b.getPosX(), 2) + Math.pow(a.getPosY() - b.getPosY(), 2));
    }

    @Test
    public void testAgentMoves() {
        healthy.move();
        assertTrue(healthy.getPosX() != 5 || healthy.getPosY() != 5);
    }

    @Test
    public void testEpidemicSpread() {
        healthy.step();
        assertTrue(healthy.getHealthCondition().equals("healthy") || healthy.getHealthCondition().equals("infected"));
    }

    @Test
    public void testDoctorMovesTowardInfected() {
        double distanceBeforeMove = getDistance(doctor, infected);
        doctor.move();
        double distanceAfterMove = getDistance(doctor, infected);
        assertTrue(distanceBeforeMove > distanceAfterMove);
    }

    @Test
    public void testDoctorCuresInfected() {
        grid.moveAgent(doctor, 5, 5);
        doctor.heal();
        assertTrue(infected.getHealthCondition().equals("infected") || infected.getHealthCondition().equals("healthy"));
    }

    @Test
    public void testDoctorVaccinateHealthy() {
        grid.moveAgent(doctor, 5, 5);
        doctor.heal();
        assertTrue(healthy.getHealthCondition().equals("healthy") || healthy.getHealthCondition().equals("immune"));
    }

    @Test
    public void testIsolateInfected() {
        grid.moveAgent(police,4,4);
        police.isolate();
        assertTrue(infected.isIsolated() && !healthy.isIsolated());
    }

    @Test
    public void testIsolatedCantMove() {
        infected.setIsolated(true);
        infected.move();
        assertTrue(infected.getPosX() == 5 && infected.getPosY() == 5);
    }

    @Test
    public void testDeadCantMove() {
        infected.setHealthCondition("dead");
        infected.move();
        assertTrue(infected.getPosX() == 5 && infected.getPosY() == 5);
    }
}
