package main;

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
    void getActionReturnsMove() {
        String url = baseUrl() + "/robot/action";

        String response = rest.getForObject(url, String.class);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
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
              "action1": "A",
              "action2": "B",
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