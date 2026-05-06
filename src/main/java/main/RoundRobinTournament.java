package main;
import java.util.ArrayList;
import java.util.List;

public class RoundRobinTournament extends Tournament {

    private int currentMatchIndex;
    private List<Robot> bracket;

    public RoundRobinTournament(String name, Game game, List<Robot> players) {
        super(name, game, players);
        this.currentMatchIndex = 0;
        this.bracket = new ArrayList<>(players);
    }

    @Override
    public Robot run(Robot r1, Robot r2) {
        game.run(r1, r2);
        return game.giveOutcome(r1) > game.giveOutcome(r2) ? r1 : r2;
    }


    @Override
    public boolean checkEnd() {
        if (currentMatchIndex >= bracket.size() * (bracket.size() - 1)) {
        	for(Robot player : bracket) {
        		System.out.println(player.getName() + " " + player.getRoundScore());
        	}
            return true;
        }
        return false;
    }

    public void playNextMatch() {
        if (currentMatchIndex < bracket.size() * (bracket.size() - 1)) {
            int player1Index = currentMatchIndex / (bracket.size() - 1);
            int player2Index = currentMatchIndex % (bracket.size() - 1);

            if (player2Index >= player1Index) {
                player2Index++;
            }

            Robot r1 = bracket.get(player1Index);
            Robot r2 = bracket.get(player2Index);

            run(r1, r2);
            currentMatchIndex++;
        }
    }

	@Override
	public boolean isAvailable() {
		if (bracket.size() >= 4) {
            return false;
        }
        else {
            return true;
        }
	}
}