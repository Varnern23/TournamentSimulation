package main;
import java.util.HashMap;
public abstract class Robot {
	
	protected HashMap<Integer, RoundInfo> history = new HashMap<>();
	private String name = "";
	private int rScore;
	
	public Robot(String name) {
		this.name = name; 
		this.history = new HashMap<>();
		this.rScore = 0;
	}
	
	public String getName() {
        return name;
    }
	
    public int getRoundScore() {
    	return rScore;
    }
    
    public void increaseRoundScore(int num) {
    	rScore += num;
    }
    
    public abstract String getAction();
    
    public void record(int round, RoundInfo info) {
    	history.put(round, info);
    }
    public RoundInfo getRoundInfo(int round) {
        return history.get(round);
    }
}
	


