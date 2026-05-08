package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class TournamentListenerTest {

    private RoundRobinTournament tournament;

    @BeforeEach
    void setUp() {
        tournament = new RoundRobinTournament("test", new PrisonersDilema(), new ArrayList<>());
    }

    @Test
    void listenerReceivesRoundCompleted() {
        RoundInfo[] received = {null};
        tournament.addTournamentListener(new TournamentListener() {
            public void onRoundCompleted(RoundInfo r) { received[0] = r; }
            public void onTournamentEnded(String w) {}
        });

        RoundInfo info = new RoundInfo(1, "P1", "P2", "S", "D", 0, 5);
        tournament.notifyRoundCompleted(info);

        assertEquals(info, received[0]);
    }

    @Test
    void listenerReceivesTournamentEnded() {
        String[] winner = {null};
        tournament.addTournamentListener(new TournamentListener() {
            public void onRoundCompleted(RoundInfo r) {}
            public void onTournamentEnded(String w) { winner[0] = w; }
        });

        tournament.notifyTournamentEnded("Robot1");

        assertEquals("Robot1", winner[0]);
    }

    @Test
    void removedListenerDoesNotReceiveEvents() {
        RoundInfo[] received = {null};
        TournamentListener listener = new TournamentListener() {
            public void onRoundCompleted(RoundInfo r) { received[0] = r; }
            public void onTournamentEnded(String w) {}
        };

        tournament.addTournamentListener(listener);
        tournament.removeTournamentListener(listener);
        tournament.notifyRoundCompleted(new RoundInfo(1, "P1", "P2", "S", "D", 0, 5));

        assertNull(received[0]);
    }

    @Test
    void multipleListenersAllNotified() {
        int[] count = {0};
        tournament.addTournamentListener(new TournamentListener() {
            public void onRoundCompleted(RoundInfo r) { count[0]++; }
            public void onTournamentEnded(String w) {}
        });
        tournament.addTournamentListener(new TournamentListener() {
            public void onRoundCompleted(RoundInfo r) { count[0]++; }
            public void onTournamentEnded(String w) {}
        });

        tournament.notifyRoundCompleted(new RoundInfo(1, "P1", "P2", "S", "D", 0, 5));

        assertEquals(2, count[0]);
    }
}
