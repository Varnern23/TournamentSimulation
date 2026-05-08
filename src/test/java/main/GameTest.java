package main;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GameTest {

    class TestGame extends Game {

        @Override
        public int giveOutcome(Robot robot) {
            return 1;
        }

        @Override
        public RoundInfo run(Robot r1, Robot r2) {
            notifyMoveListeners("TEST_MOVE");
            notifyScoreListeners(5, 3);
            return new RoundInfo(1, "TestPlayer", "Opponent", "TEST_MOVE", "TEST_MOVE", 5, 3);
        }

        @Override
        public Boolean checkEnd() {
            return true;
        }
    }

    class TestMoveListener implements MoveListener {
        String lastMove = null;

        @Override
        public void update(String action) {
            lastMove = action;
        }
    }

    class TestScoreListener implements ScoreListener {
        int score1 = -1;
        int score2 = -1;

        @Override
        public void update(int s1, int s2) {
            score1 = s1;
            score2 = s2;
        }
    }

    @Test
    void testAddMoveListener() {
        Game game = new TestGame();
        TestMoveListener listener = new TestMoveListener();

        game.addMoveListener(listener);
        ((TestGame) game).notifyMoveListeners("MOVE1");

        assertEquals("MOVE1", listener.lastMove);
    }

    @Test
    void testRemoveListeners() {
        Game game = new TestGame();
        TestMoveListener listener = new TestMoveListener();
        TestScoreListener listener2 = new TestScoreListener();
        
        game.addScoreListener(listener2);
        game.removeScoreListener(listener2);
        
        ((TestGame) game).notifyScoreListeners(5, 5);
        
        assertEquals(listener2.score1, -1);
        assertEquals(listener2.score2, -1);

        game.addMoveListener(listener);
        game.removeMoveListener(listener);

        ((TestGame) game).notifyMoveListeners("MOVE2");

        assertNull(listener.lastMove);
    }

    @Test
    void testScoreListenerNotification() {
        Game game = new TestGame();
        TestScoreListener listener = new TestScoreListener();

        game.addScoreListener(listener);
        ((TestGame) game).notifyScoreListeners(10, 8);

        assertEquals(10, listener.score1);
        assertEquals(8, listener.score2);
    }

    @Test
    void testRunTriggersListeners() {
        TestGame game = new TestGame();
        TestMoveListener moveListener = new TestMoveListener();
        TestScoreListener scoreListener = new TestScoreListener();

        game.addMoveListener(moveListener);
        game.addScoreListener(scoreListener);

        game.run(null, null);

        assertEquals("TEST_MOVE", moveListener.lastMove);
        assertEquals(5, scoreListener.score1);
        assertEquals(3, scoreListener.score2);
    }

    @Test
    void testCheckEnd() {
        Game game = new TestGame();
        assertEquals(true, game.checkEnd());
    }
}