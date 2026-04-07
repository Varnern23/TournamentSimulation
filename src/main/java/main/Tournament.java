package main;
import java.util.List;

public abstract class Tournament {

    Game game;
    List<Robot> players;
    String tName;

    public Tournament(String tName, Game game, List<Robot> players) {
        this.tName = tName;
    	this.game = game;
        this.players = players;
    }

    public abstract Robot run(Robot r1, Robot r2);

    public abstract List<Robot> getBracket();

    public abstract String checkEnd();
    
    public String getName() {
    	return tName;
    }
    public void addRobot(Robot r) {
        players.add(r);
    }
    
    public abstract boolean isAvailable();
}
