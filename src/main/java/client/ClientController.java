package client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.MatchInfo;
import main.Robot;
import main.RoundInfo;

@RestController
@RequestMapping("/robot")
public class ClientController {

    private Robot robot = new Robot();

    @GetMapping("/action")
    public String getAction() {
        return robot.getAction();
    }

    @PostMapping("/match")
    public void match(@RequestBody MatchInfo request) {
        if (request.history() != null) {
            for (RoundInfo r : request.history()) {
                robot.record(r.getRound(), r);
            }
        }
    }
}
