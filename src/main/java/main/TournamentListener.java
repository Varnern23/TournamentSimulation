package main;

public interface TournamentListener {
    void onRoundCompleted(RoundInfo round);
    void onTournamentEnded(String winner);
}
