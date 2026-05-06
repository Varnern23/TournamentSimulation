package server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import main.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class MockTournamentLoader implements CommandLineRunner {

    private final TourneyController server;

    public MockTournamentLoader(TourneyController server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Robot> listPlayers0 = new ArrayList<>();
        listPlayers0.add(new Robot1("Kevin Hart"));
        listPlayers0.add(new Robot2("Kratos"));
        listPlayers0.add(new Robot3("Clanker"));
        server.addTournament("AlmostFull", new RoundRobinTournament("AlmostFull", new PrisonersDilema(), listPlayers0));

        List<Robot> listPlayers1 = new ArrayList<>();
        listPlayers1.add(new Robot1("Ratchet"));
        listPlayers1.add(new Robot2("Larry David"));
        listPlayers1.add(new Robot1("James Bond"));
        listPlayers1.add(new Robot2("Yuno Miles"));
        server.addTournament("ClosedFull1", new RoundRobinTournament("ClosedFull1", new PrisonersDilema(), listPlayers1));

        List<Robot> listPlayers2 = new ArrayList<>();
        listPlayers2.add(new Robot1("NBA Centel"));
        listPlayers2.add(new Robot3("Ghetto Gronk"));
        listPlayers2.add(new Robot2("Lebron James"));
        listPlayers2.add(new Robot3("James Harden"));
        server.addTournament("ClosedFull2", new RoundRobinTournament("ClosedFull2", new PrisonersDilema(), listPlayers2));
        
        List<Robot> listPlayers3 = new ArrayList<>();
        listPlayers3.add(new Robot1("Harry Potter"));
        listPlayers3.add(new Robot2("50 Cent"));
        listPlayers3.add(new Robot1("Snoop Dogg"));
        listPlayers3.add(new Robot2("Notorious B.I.G."));
        server.addTournament("ClosedFull3", new RoundRobinTournament("ClosedFull3", new PrisonersDilema(), listPlayers3));
    }
}