package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import server.*;

@SpringBootTest(classes = ServerApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void tournamentsAvailableEndpointResponds() {
        String url = baseUrl() + "/tournaments/available";
        String body = rest.getForObject(url, String.class);
        Assertions.assertNotNull(body);
    }

    @Test 
    void registerRemoteBotWorks() {
        String url = baseUrl() + "/tournaments/register";

        String json = """
        {
          "tourneyName": "default",
          "robotName": "TestBot",
          "ip": "localhost",
          "portNum": 9999
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response =
                rest.postForEntity(url, request, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().contains("Registered"));
    }

    @Test
    void registerWithBadDataStillResponds() {
        String url = baseUrl() + "/tournaments/register";

        String badJson = "{}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(badJson, headers);

        ResponseEntity<String> response =
                rest.postForEntity(url, request, String.class);

        Assertions.assertNotNull(response);
    }
}