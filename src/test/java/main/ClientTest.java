package main;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import client.*;

@SpringBootTest(classes = ClientApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void testRemoteBotGetAction() {
        String url = baseUrl() + "/robot/action";
        String action = rest.getForObject(url, String.class);

        assertNotNull(action);
        assertTrue(
            action.equals("S") || action.equals("D"),
            "Action must be S or D but was: " + action
        );
    }

    @Test
    void testRemoteBotGiveRecord() {
        RemoteBot bot = new RemoteBot("TestBot", "localhost", port);

        List<RoundInfo> history = List.of(
            new RoundInfo(1, "Roboto", "D", "D", 5, 5)
        );

        assertDoesNotThrow(() ->
            bot.giveRecord("OpponentBot", history)
        );
    }

    @Test
    void matchEndpointAcceptsJson() {
        String url = baseUrl() + "/robot/match";

        String json = """
        {
          "oppName": "Opponent",
          "history": [
            {
              "round": 1,
              "action1": "S",
              "action2": "D",
              "score1": 1,
              "score2": 2
            }
          ]
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<Void> response =
                rest.postForEntity(url, request, Void.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void matchEmptyHistory() {
        String url = baseUrl() + "/robot/match";

        String json = """
        {
          "oppName": "Opponent",
          "history": []
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<Void> response =
                rest.postForEntity(url, request, Void.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}