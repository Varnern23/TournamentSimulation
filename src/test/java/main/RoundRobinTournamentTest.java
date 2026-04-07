package main;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoundRobinTournamentTest {

    private RoundRobinTournament tournament;
    private PrisonersDilema game;
    private List<Robot> players;

    @BeforeEach
    void setUp() {
        game = new PrisonersDilema();
        players = new ArrayList<>();
        players.add(new Robot1("Robot1"));
        players.add(new Robot2("Robot2"));
        players.add(new Robot3("Robot3"));
        tournament = new RoundRobinTournament("example",game, players);
    }

    @Test
    void testTournamentExecution() {
        while (tournament.checkEnd().equals("Tournament Ongoing")) {
            tournament.playNextMatch();
        }

        assertEquals("Tournament Over", tournament.checkEnd());

        for (Robot player : players) {
            assertTrue(player.getRoundScore() >= 0, player.getName() + " should have a valid score.");
        }
    }
}