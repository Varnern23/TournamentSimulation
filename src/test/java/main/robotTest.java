package main;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

  class RobotTest {

    class TestRobot extends Robot {

        public TestRobot(String name) {
            super(name);
        }

        @Override
        public String getAction() {
            return "TEST_ACTION";
        }
    }

    @Test
    void testNameInitialization() {
        Robot r = new TestRobot("Robo1");
        assertEquals("Robo1", r.getName());
    }

    @Test
    void testRoundScoreStartsAtZero() {
        Robot r = new TestRobot("Robo1");
        assertEquals(0, r.getRoundScore());
    }

    @Test
    void testIncreaseRoundScore() {
        Robot r = new TestRobot("Robo1");

        r.increaseRoundScore(5);
        assertEquals(5, r.getRoundScore());

        r.increaseRoundScore(3);
        assertEquals(8, r.getRoundScore());
    }

    @Test
    void testGetActionImplementation() {
        Robot r = new TestRobot("Robo1");
        assertEquals("TEST_ACTION", r.getAction());
    }
    @Test
    void defaultGetActionImplementation() {
        Robot r = new Robot("Robo1");
        assertEquals("S", r.getAction());
    }
    @Test
    void robot3GetActionImplementation() {
        Robot r = new Robot("Robo1");
        assertTrue(r.getAction().equals("S") || r.getAction().equals("D"));
    }

    @Test
    void testRecordRound() {
        Robot r = new TestRobot("Robo1");

        RoundInfo info = new RoundInfo(1, "Roboto", "D", "D", 5, 5);
        r.record(1, info);

        assertEquals(info, r.getRoundInfo(1));
    }
}