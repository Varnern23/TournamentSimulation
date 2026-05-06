package main;

import java.util.ArrayList;
import java.util.List;

public abstract class Tournament {

    Game game;
    List<Robot> players;
    String tName;
    private List<RoundInfo> roundHistory = new ArrayList<>();
    
    private List<TournamentListener> listeners = new ArrayList<>();

    public Tournament(String tName, Game game, List<Robot> players) {
        this.tName = tName;
        this.game = game;
        this.players = players;
    }

    public List<RoundInfo> getRoundHistory() {
        return new ArrayList<>(roundHistory);
    }
    public void addRound(RoundInfo round) {
        roundHistory.add(round);
    }
    public void addTournamentListener(TournamentListener listener) {
        listeners.add(listener);
    }

    public void removeTournamentListener(TournamentListener listener) {
        listeners.remove(listener);
    }
    public void playNextMatch() {
        if (!checkEnd()) {
            Robot r1 = players.get(0);
            Robot r2 = players.get(1);
        }
    }


    protected void notifyRoundCompleted(RoundInfo round) {
        for (TournamentListener l : listeners) {
            l.onRoundCompleted(round);
        }
    }

    protected void notifyTournamentEnded(String winner) {
        for (TournamentListener l : listeners) {
            l.onTournamentEnded(winner);
        }
    }


    public abstract Robot run(Robot r1, Robot r2);

    public abstract boolean checkEnd();


    public String getName() {
        return tName;
    }

    public void addRobot(Robot r) {
        players.add(r);
    }

    public abstract boolean isAvailable();
}

