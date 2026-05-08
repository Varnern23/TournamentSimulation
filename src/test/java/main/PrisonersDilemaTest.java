package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrisonersDilemaTest {

    private PrisonersDilema game;
    private Robot cooperator;
    private Robot defector;

    @BeforeEach
    void setUp() {
        game = new PrisonersDilema();
        cooperator = new Robot1("Coop");
        defector = new Robot2("Defect");
    }

    @Test
    void testBothCooperate() {
        RoundInfo info = game.run(cooperator, defector);
        // S vs D: cooperator gets 0, defector gets 5
        assertEquals(0, info.getScore1());
        assertEquals(5, info.getScore2());
    }

    @Test
    void testBothDefect() {
        game = new PrisonersDilema();
        RoundInfo info = game.run(defector, defector);
        assertEquals(0, info.getScore1());
        assertEquals(0, info.getScore2());
    }

    @Test
    void testDefectorVsCooperator() {
        RoundInfo info = game.run(defector, cooperator);
        // D vs S: defector gets 5, cooperator gets 0
        assertEquals(5, info.getScore1());
        assertEquals(0, info.getScore2());
    }

    @Test
    void testCooperatorVsCooperator() {
        RoundInfo info = game.run(cooperator, cooperator);
        assertEquals(3, info.getScore1());
        assertEquals(3, info.getScore2());
    }

    @Test
    void testRoundInfoHasCorrectPlayerNames() {
        RoundInfo info = game.run(cooperator, defector);
        assertEquals("Coop", info.getPlayerName());
        assertEquals("Defect", info.getOppName());
    }

    @Test
    void testRoundInfoHasCorrectActions() {
        RoundInfo info = game.run(cooperator, defector);
        assertEquals("S", info.getAction1());
        assertEquals("D", info.getAction2());
    }

    @Test
    void testCheckEndAfterTwoRounds() {
        game.run(cooperator, defector);
        game.run(cooperator, defector);
        assertFalse(game.checkEnd());
    }

    @Test
    void testCheckEndAfterThreeRounds() {
        game.run(cooperator, defector);
        game.run(cooperator, defector);
        game.run(cooperator, defector);
        assertTrue(game.checkEnd());
    }

    @Test
    void testScoresAccumulateOnRobots() {
        game.run(cooperator, defector); // coop gets 0, defect gets 5
        assertEquals(0, cooperator.getRoundScore());
        assertEquals(5, defector.getRoundScore());
    }

    @Test
    void testRoundNumberIncrements() {
        RoundInfo round1 = game.run(cooperator, defector);
        RoundInfo round2 = game.run(cooperator, defector);
        assertEquals(1, round1.getRound());
        assertEquals(2, round2.getRound());
    }
}
