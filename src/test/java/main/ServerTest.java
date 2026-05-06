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
        String url = baseUrl() + "/tournaments/openTournaments";
        String[] body = rest.getForObject(url, String[].class);

        Assertions.assertNotNull(body);
        Assertions.assertTrue(body.length > 0, "Should have at least one available tournament");
        Assertions.assertTrue(
            java.util.Arrays.asList(body).contains("default"),
            "Default tournament should be listed as available"
        );
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
        Assertions.assertTrue(
            response.getBody().contains("TestBot"),
            "Response should confirm the robot name that was registered"
        );
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
