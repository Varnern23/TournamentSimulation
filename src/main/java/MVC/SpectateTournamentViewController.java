package MVC;
 
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.RoundInfo;
 
public class SpectateTournamentViewController {
 
    @FXML private Label tournamentNameLabel;
    @FXML private VBox roundsVBox;
 
    private Model model;
    private ViewTransitionModelInterface viewTransitionModel;
    private Timeline pollTimeline;

    public void setModel(Model model, ViewTransitionModelInterface viewTransitionModel) {
        this.model = model;
        this.viewTransitionModel = viewTransitionModel;

        tournamentNameLabel.textProperty().bind(model.selectedTournamentProperty());

        model.getRounds().addListener((javafx.collections.ListChangeListener<RoundInfo>) change -> {
            roundsVBox.getChildren().clear();
            for (RoundInfo round : model.getRounds()) {
                roundsVBox.getChildren().add(formatRound(round));
            }
        });

        pollTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> model.spectateTournament()));
        pollTimeline.setCycleCount(Timeline.INDEFINITE);
        pollTimeline.play();
    }
 
    private Label formatRound(RoundInfo round) {
        String text = String.format(
            "Round %d | %s played %s (%d pts)  vs  %s played %s (%d pts)",
            round.getRound(),
            round.getPlayerName(), round.getAction1(), round.getScore1(),
            round.getOppName(),    round.getAction2(), round.getScore2()
        );
        Label label = new Label(text);
        label.setWrapText(true);
        return label;
    }
 
    @FXML
    private void onBack() {
        if (pollTimeline != null) pollTimeline.stop();
        model.getRounds().clear();
        roundsVBox.getChildren().clear();
        viewTransitionModel.TournamentViewLoad();
    }
}
