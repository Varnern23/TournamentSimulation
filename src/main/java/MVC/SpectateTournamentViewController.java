package MVC;
 
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import main.RoundInfo;
 
public class SpectateTournamentViewController {
 
    @FXML private Label tournamentNameLabel;
    @FXML private VBox roundsVBox;
 
    private Model model;
    private ViewTransitionModelInterface viewTransitionModel;
 
    public void setModel(Model model, ViewTransitionModelInterface viewTransitionModel) {
        this.model = model;
        this.viewTransitionModel = viewTransitionModel;
 
        tournamentNameLabel.textProperty().bind(model.selectedTournamentProperty());
 
        for (RoundInfo round : model.getRounds()) {
            roundsVBox.getChildren().add(formatRound(round));
        }
 
        model.getRounds().addListener((javafx.collections.ListChangeListener<RoundInfo>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (RoundInfo round : change.getAddedSubList()) {
                        javafx.application.Platform.runLater(() ->
                            roundsVBox.getChildren().add(formatRound(round))
                        );
                    }
                }
            }
        });
    }
 
    private Label formatRound(RoundInfo round) {
        String text = String.format(
            "Round %d | Opponent: %s | Your Action: %s | Opp Action: %s | Your Score: %d | Opp Score: %d",
            round.getRound(),
            round.getoppName(),
            round.getAction1(),
            round.getAction2(),
            round.getScore1(),
            round.getScore2()
        );
        Label label = new Label(text);
        label.setWrapText(true);
        return label;
    }
 
    @FXML
    private void onBack() {
        model.getRounds().clear();
        roundsVBox.getChildren().clear();
        viewTransitionModel.TournamentViewLoad();
    }
}
