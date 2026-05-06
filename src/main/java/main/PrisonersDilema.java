package main;

public class PrisonersDilema extends Game{

    private int currentRound;

    public PrisonersDilema() {
        this.currentRound = 1;
    }

    public RoundInfo run(Robot r1, Robot r2) {
    	
    	if (checkEnd() == true) {
    		
    	}

        String action1 = r1.getAction();
        notifyMoveListeners(action1);
        String action2 = r2.getAction();
        notifyMoveListeners(action2);

        int[] payoffs = calculatePayoff(action1, action2);

        int score1 = payoffs[0];
        int score2 = payoffs[1];

        RoundInfo info1 = new RoundInfo(
                currentRound, 
                r2.getName(),
                action1,
                action2, 
                score1,
                score2
        );

        RoundInfo info2 = new RoundInfo(
                currentRound, 
                r1.getName(),
                action2,
                action1, 
                score2, 
                score1
        );

        r1.record(currentRound, info1);
        r2.record(currentRound, info2);

        r1.increaseRoundScore(score1);
        r2.increaseRoundScore(score2);
        notifyScoreListeners(score1, score2);

        currentRound++;
        return info1;
    }

    private int[] calculatePayoff(String a1, String a2) {

        if (a1.equals("S") && a2.equals("S"))
            return new int[]{3, 3};

        if (a1.equals("S") && a2.equals("D"))
            return new int[]{0, 5};

        if (a1.equals("D") && a2.equals("S"))
            return new int[]{5, 0};

        return new int[]{0, 0}; 
    }


	@Override
	public int giveOutcome(Robot robot) {
		return robot.getRoundScore();
	}

	@Override
	public Boolean checkEnd() {
		if(currentRound >= 3) {
			return true;
		}
		else return false;
	}
    
}