//Not in original UML but just gives some nicer structure to allow robots to see all information they may need in history
package main;

public class RoundInfo {

    private int round;
    private String playerName;
    private String oppName;
    private String action1;
    private String action2;
    private int score1;
    private int score2;

    public RoundInfo() {}

    public RoundInfo(int round, String playerName, String oppName, String action1, String action2, int score1, int score2) {
        this.round = round;
        this.playerName = playerName;
        this.oppName = oppName;
        this.action1 = action1;
        this.action2 = action2;
        this.score1 = score1;
        this.score2 = score2;
    }


    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getOppName() {
        return oppName;
    }

    public void setOppName(String oppName) {
        this.oppName = oppName;
    }

    public String getAction1() {
        return action1;
    }

    public void setAction1(String action1) {
        this.action1 = action1;
    }

    public String getAction2() {
        return action2;
    }

    public void setAction2(String action2) {
        this.action2 = action2;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }
}