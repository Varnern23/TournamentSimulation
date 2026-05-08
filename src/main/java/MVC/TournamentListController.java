package MVC;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class TournamentListController {

    @FXML private TextField ipField;
    @FXML private TextField portField;
    @FXML private ListView<String> openListView;
    @FXML private ListView<String> closedListView;

    private Model model;
    private ViewTransitionModelInterface viewTransitionModel;
    private Timeline pollTimeline;

    public void setModel(Model model, ViewTransitionModelInterface viewTransitionModel) {
        this.model = model;
        this.viewTransitionModel = viewTransitionModel;
        openListView.setCellFactory(lv -> new TournamentCell());
        closedListView.setCellFactory(lv -> new TournamentCell());

        ipField.textProperty().bindBidirectional(model.ipProperty());
        portField.textProperty().bindBidirectional(model.portNumProperty());

        openListView.setItems(model.getOpenTournaments());
        closedListView.setItems(model.getClosedTournaments());

        openListView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal != null) {
                    model.setSelectedTournament(newVal);
                    closedListView.getSelectionModel().clearSelection();
                    viewTransitionModel.SpectateViewLoad();
                }
            }
        );

        closedListView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal != null) {
                    model.setSelectedTournament(newVal);
                    openListView.getSelectionModel().clearSelection();
                    viewTransitionModel.SpectateViewLoad();
                }
            }
        );

        pollTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> model.connectToServer()));
        pollTimeline.setCycleCount(Timeline.INDEFINITE);
        pollTimeline.play();
    }

    @FXML
    private void onEnter() {
        model.connectToServer();
    }
}
