package main;
import java.util.ArrayList;
import java.util.List;

public abstract class Game {
	
	ArrayList<String> actions = new ArrayList<String>();
	List<MoveListener> moveListeners;
	List<ScoreListener> scoreListeners;
	
	public Game() {
        actions = new ArrayList<>();
        moveListeners = new ArrayList<>();
        scoreListeners = new ArrayList<>();
    }

    public abstract int giveOutcome(Robot robot);

    public abstract void run(Robot r1, Robot r2);

    public abstract String checkEnd();


    public void addMoveListener(MoveListener listener) {
        moveListeners.add(listener);
    }

    public void removeMoveListener(MoveListener listener) {
        moveListeners.remove(listener);
    }

    public void addScoreListener(ScoreListener listener) {
        scoreListeners.add(listener);
    }

    public void removeScoreListener(ScoreListener listener) {
        scoreListeners.remove(listener);
    }

    protected void notifyMoveListeners(String action) {
        for (MoveListener listener : moveListeners) {
            listener.update(action);
        }
    }

    protected void notifyScoreListeners(int score1, int score2) {
        for (ScoreListener listener : scoreListeners) {
            listener.update(score1, score2);
        }
    }
}
