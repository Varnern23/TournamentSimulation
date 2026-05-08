package MVC;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.ChatBotDecorator;
import main.ChatMessage;
import main.ChatRoom;
import main.CheerBot;
import main.EchoBot;
import main.HumanParticipant;
import main.RoundInfo;
import main.TrashTalkBot;

public class SpectateTournamentViewController {

    @FXML private Label tournamentNameLabel;
    @FXML private VBox roundsVBox;
    @FXML private VBox chatVBox;
    @FXML private TextField chatInputField;

    private Model model;
    private ViewTransitionModelInterface viewTransitionModel;
    private Timeline pollTimeline;
    private ChatRoom chatRoom;
    private HumanParticipant humanUser;

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

        chatRoom = new ChatRoom();
        humanUser = new HumanParticipant("You", chatVBox);
        ChatBotDecorator chain = new TrashTalkBot(
            new CheerBot(
                new EchoBot(humanUser, chatRoom),
            chatRoom),
        chatRoom);
        chatRoom.join(chain);
    }

    @FXML
    private void onSendChat() {
        String text = chatInputField.getText().trim();
        if (!text.isEmpty()) {
            chatRoom.broadcast(new ChatMessage("You", text, false));
            chatInputField.clear();
        }
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
        chatVBox.getChildren().clear();
        viewTransitionModel.TournamentViewLoad();
    }
}
