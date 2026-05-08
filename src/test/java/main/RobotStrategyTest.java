package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RobotStrategyTest {

    @Test
    void robot1AlwaysCooperates() {
        Robot r = new Robot1("R1");
        assertEquals("S", r.getAction());
        assertEquals("S", r.getAction());
    }

    @Test
    void robot2AlwaysDefects() {
        Robot r = new Robot2("R2");
        assertEquals("D", r.getAction());
        assertEquals("D", r.getAction());
    }

    @Test
    void robot3ReturnsValidAction() {
        Robot r = new Robot3("R3");
        for (int i = 0; i < 20; i++) {
            String action = r.getAction();
            assertTrue(action.equals("S") || action.equals("D"),
                "Robot3 returned unexpected action: " + action);
        }
    }

    @Test
    void robot3IsNonDeterministic() {
        Robot r = new Robot3("R3");
        boolean sawS = false;
        boolean sawD = false;
        for (int i = 0; i < 100; i++) {
            String action = r.getAction();
            if (action.equals("S")) sawS = true;
            if (action.equals("D")) sawD = true;
            if (sawS && sawD) break;
        }
        assertTrue(sawS && sawD, "Robot3 should return both S and D over many calls");
    }
}
