package MVC;

import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class TournamentCell extends ListCell<String> {

    private HBox container = new HBox(10);
    private Label nameLabel = new Label();
    private Label statusLabel = new Label();

    public TournamentCell() {
        container.setAlignment(Pos.CENTER_LEFT);
        container.getChildren().addAll(nameLabel, statusLabel);
    }

    @Override
    protected void updateItem(String tournament, boolean empty) {
        super.updateItem(tournament, empty);

        if (empty || tournament == null) {
            setGraphic(null);
        } else {
            nameLabel.setText(tournament);
            setGraphic(container);
        }
    }
}