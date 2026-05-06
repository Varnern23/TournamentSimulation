package server;

import org.springframework.web.bind.annotation.*;

import main.PlayerRegistration;
import main.PrisonersDilema;
import main.RemoteBot;
import main.Robot;
import main.RoundInfo;
import main.RoundRobinTournament;
import main.Tournament;

import java.util.*;

@RestController
@RequestMapping("/tournaments")
public class TourneyController {

    private List<Tournament> tournaments = new ArrayList<>();

    public TourneyController() {
        tournaments.add(new RoundRobinTournament("default", new PrisonersDilema(), new ArrayList<>()));
    }

    @GetMapping("/available")
    public List<String> getAvailableTournaments() {
        List<String> available = new ArrayList<>();
        for (Tournament t : tournaments) {
            if (t.isAvailable()) {
                available.add(t.getName());
            }
        }
        return available;
    }

    @GetMapping("/openTournaments")
    public String[] getOpenTournaments() {
        return tournaments.stream()
                .filter(Tournament::isAvailable)
                .map(Tournament::getName)
                .toArray(String[]::new);
    }

    @GetMapping("/closedTournaments")
    public String[] getClosedTournaments() {
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
                return "Registered " + req.getRobotName() + " to " + t.getName();
            }
        }
        return "Tournament not found or not available";
    }
}
