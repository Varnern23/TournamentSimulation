package client;

import java.net.InetAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import main.PlayerRegistration;

@SpringBootApplication
public class ClientApp {

    @Value("${tournament.server.host:localhost}")
    private String serverHost;

    @Value("${tournament.server.port:8080}")
    private int serverPort;

    @Value("${tournament.name:AlmostFull}")
    private String tournamentName;

    @Value("${robot.name:RemoteRobot}")
    private String robotName;

    @Value("${server.port:9090}")
    private int clientPort;

    @Value("${robot.ip:}")
    private String robotIp;

    public static void main(String[] args) {
        SpringApplication.run(ClientApp.class, args);
    }

    @Bean
    public CommandLineRunner register() {
        return args -> {
            String clientIp = robotIp.isEmpty() ? InetAddress.getLocalHost().getHostAddress() : robotIp;

            PlayerRegistration reg = new PlayerRegistration();
            reg.setTourneyName(tournamentName);
            reg.setRobotName(robotName);
            reg.setIp(clientIp);
            reg.setPortNum(clientPort);

            RestClient client = RestClient.builder()
                    .baseUrl("http://" + serverHost + ":" + serverPort)
                    .build();

            try {
                String response = client.post()
                        .uri("/tournaments/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(reg)
                        .retrieve()
                        .body(String.class);
                System.out.println("Registration: " + response);
            } catch (Exception e) {
                System.out.println("Registration failed: " + e.getMessage());
            }
        };
    }

    // mvn spring-boot:run "-Dspring-boot.run.main-class=client.ClientApp" "-Dspring-boot.run.arguments=--server.port=9091 --robot.name=AnotherBot --tournament.name=AlmostFull"

}
