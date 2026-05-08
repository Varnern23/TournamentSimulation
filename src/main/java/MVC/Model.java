package MVC;
import org.springframework.web.client.RestClient;
import main.RoundInfo;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
	
	private StringProperty selectedTournament;
	private StringProperty serverIP;
	private StringProperty serverPortNum;
	private ObservableList<String> openTournaments = FXCollections.observableArrayList();
	private ObservableList<String> closedTournaments = FXCollections.observableArrayList();
	private ObservableList<RoundInfo> rounds = FXCollections.observableArrayList();
	private RestClient client;
	
	public Model() {
		this.selectedTournament = new SimpleStringProperty();
		this.serverIP = new SimpleStringProperty("");
		this.serverPortNum = new SimpleStringProperty("");
		this.client = RestClient.builder()
				.baseUrl("http://localhost:8080")
				.build();
	}
	
	public StringProperty selectedTournamentProperty() {
		return selectedTournament;
	}
	public StringProperty ipProperty() {
		return serverIP;
	}
	public StringProperty portNumProperty() {
		return serverPortNum;
	}
	public ObservableList<String> getOpenTournaments() {
		return openTournaments;
	}
	public ObservableList<String> getClosedTournaments() {
		return closedTournaments;
	}
	public ObservableList<RoundInfo> getRounds() {
		return rounds;
	}
	public void setSelectedTournament(String tournament) {
		this.selectedTournament.set(tournament);
	}
	public void setRounds(ObservableList<RoundInfo> rounds) {
		this.rounds = rounds;
	}
	public void setOpenTournaments(ObservableList<String> openTournaments) {
		this.openTournaments = openTournaments;
	}
	public void setClosedTournaments(ObservableList<String> closedTournaments) {
		this.closedTournaments = closedTournaments;
	}
	public void spectateTournament() {
		String baseURI = String.format("http://%s:%s", this.serverIP.get(), this.serverPortNum.get());
		String uri = baseURI + "/tournaments/" + selectedTournament.get() + "/rounds";
		new Thread(() -> {
			try {
				RoundInfo[] fetched = client.get()
						.uri(uri)
						.retrieve()
						.body(RoundInfo[].class);
				if (fetched != null) {
					Platform.runLater(() -> this.rounds.setAll(fetched));
				}
			} catch (Exception e) {
			}
		}).start();
	}
	public void connectToServer() {
		String baseURI = String.format("http://%s:%s", this.serverIP.get(), this.serverPortNum.get());
		new Thread(() -> {
			try {
				String[] open = client.get()
						.uri(baseURI + "/tournaments/openTournaments")
						.retrieve()
						.body(String[].class);
				String[] closed = client.get()
						.uri(baseURI + "/tournaments/closedTournaments")
						.retrieve()
						.body(String[].class);
				Platform.runLater(() -> {
					if (open != null) openTournaments.setAll(open);
					if (closed != null) closedTournaments.setAll(closed);
				});
			} catch (Exception e) {
			}
		}).start();
	}
	
}
