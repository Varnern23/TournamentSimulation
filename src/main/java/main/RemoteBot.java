package main;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import java.util.ArrayList;

public class RemoteBot extends Robot{
	
	private String ip;
	private int portNum;
	private RestClient client;
	
	public RemoteBot(String name, String ip, int portNum) {
		super(name);
		this.ip = ip;
		this.portNum = portNum;
		this.client = RestClient.builder()
				.baseUrl("http://" + ip + ":" + portNum)
				.build();
	}

	@Override
	public void record(int round, RoundInfo info) {
		super.record(round, info);
		giveRecord(info.getOppName(), new ArrayList<>(history.values()));
	}

	@Override
	public String getAction() {
		try {
            return client.get()
                    .uri("/robot/action")
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            return "ERROR";
        }
	}
	
	public void giveRecord(String oppName, List<RoundInfo> history) {
		try {
            MatchInfo info = new MatchInfo(oppName, history);

            client.post()
                    .uri("/robot/match")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(info)
                    .retrieve()
                    .toBodilessEntity();

        } catch (Exception e) {
        	
        }
    }

}
