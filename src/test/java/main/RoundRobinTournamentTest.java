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
        while (tournament.checkEnd() == false) {
            tournament.playNextMatch();
        }

        assertEquals(true, tournament.checkEnd());

        for (Robot player : players) {
            assertTrue(player.getRoundScore() >= 0, player.getName() + " should have a valid score.");
        }
    }

    @Test
    void testIsAvailableWithThreePlayers() {
        assertTrue(tournament.isAvailable());
    }

    @Test
    void testAddRobotClosesTournament() {
        tournament.addRobot(new Robot1("Robot4"));
        assertFalse(tournament.isAvailable());
    }

    @Test
    void testAddedRobotParticipatesInMatches() {
        Robot1 bot = new Robot1("Robot4");
        tournament.addRobot(bot);

        while (!tournament.checkEnd()) {
            tournament.playNextMatch();
        }

        assertTrue(bot.getRoundScore() >= 0);
        assertFalse(tournament.getRoundHistory().isEmpty());
    }

    @Test
    void testRoundHistoryPopulatedAfterPlay() {
        tournament.addRobot(new Robot1("Robot4"));
        tournament.playNextMatch();
        assertFalse(tournament.getRoundHistory().isEmpty());
    }

    @Test
    void testCheckEndFalseBeforeAllMatches() {
        assertFalse(tournament.checkEnd());
    }
}