package server;

import org.springframework.web.bind.annotation.*;

import main.PlayerRegistration;
import main.PrisonersDilema;
import main.RemoteBot;
import main.Robot;
import main.RoundInfo;
import main.RoundRobinTournament;
import main.Tournament;
import java.lang.Thread;

import java.util.*;

@RestController
@RequestMapping("/tournaments")
public class TourneyController {

    private List<Tournament> tournaments = new ArrayList<>();
    private Set<String> startedTournaments = new HashSet<>();

    public TourneyController() {
        tournaments.add(new RoundRobinTournament("default", new PrisonersDilema(), new ArrayList<>()));
    }

    public void addTournament(String name, Tournament t) {
        tournaments.add(t);
    }

    @GetMapping("/openTournaments")
    public String[] getOpenTournaments() {
        return tournaments.stream()
                .filter(Tournament::isAvailable)
                .map(Tournament::getName)
                .toArray(String[]::new);
    }

    private void startIfClosed(Tournament t) {
        if (!t.isAvailable() && !startedTournaments.contains(t.getName())) {
            startedTournaments.add(t.getName());
            new Thread(() -> {
                while (!t.checkEnd()) {
                    t.playNextMatch();
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            }).start();
        }
    }

    @GetMapping("/closedTournaments")
    public String[] getClosedTournaments() {
        for (Tournament t : tournaments) {
            startIfClosed(t);
        }
        return tournaments.stream()
                .filter(t -> !t.isAvailable())
                .map(Tournament::getName)
                .toArray(String[]::new);
    }

    @GetMapping("/{name}/rounds")
    public List<RoundInfo> getRounds(@PathVariable String name) {
        for (Tournament t : tournaments) {
            if (t.getName().equals(name)) {
                return t.getRoundHistory();
            }
        }
        return new ArrayList<>();
    }

    @PostMapping("/register")
    public String register(@RequestBody PlayerRegistration req) {
        for (Tournament t : tournaments) {
            if (t.getName().equals(req.getTourneyName()) && t.isAvailable()) {
                Robot bot = new RemoteBot(req.getRobotName(), req.getIp(), req.getPortNum());
                t.addRobot(bot);
                startIfClosed(t);
                return "Registered " + req.getRobotName() + " to " + t.getName();
            }
        }
        return "Tournament not found or not available";
    }
}
