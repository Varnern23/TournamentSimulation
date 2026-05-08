package main;

import org.testfx.assertions.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import MVC.Model;
import MVC.ViewTransitionModel;

@ExtendWith(ApplicationExtension.class)
public class SpectateControllerTest {

    Model model;
    BorderPane root;
    ViewTransitionModel vm;

    @Start
    private void start(Stage stage) {
        model = new Model();
        root = new BorderPane();
        vm = new ViewTransitionModel(root, model);
        model.setSelectedTournament("Spring Open 2026");
        vm.SpectateViewLoad();

        stage.setScene(new Scene(root));
        stage.show();
    }

    // --- Helpers ---

    private VBox getRoundsVBox(FxRobot robot) {
        return robot.lookup("#roundsVBox").queryAs(VBox.class);
    }

    private void addRound(FxRobot robot, RoundInfo round) {
        robot.interact(() -> model.getRounds().add(round));
        WaitForAsyncUtils.waitForFxEvents();
    }

    // --- Tests ---

    @Test
    public void testTournamentNameDisplayed(FxRobot robot) {
        Assertions.assertThat(robot.lookup("#tournamentNameLabel").queryAs(Label.class))
            .hasText("Spring Open 2026");
    }

    @Test
    public void testVBoxStartsEmpty(FxRobot robot) {
        Assertions.assertThat(getRoundsVBox(robot).getChildren()).isEmpty();
    }

    @Test
    public void testRoundAppearsAsLabelWhenAdded(FxRobot robot) {
        Assertions.assertThat(getRoundsVBox(robot).getChildren()).isEmpty();

        addRound(robot, new RoundInfo(1, "Player", "RobotA", "S", "D", 0, 5));

        Assertions.assertThat(getRoundsVBox(robot).getChildren()).hasSize(1);
    }

    @Test
    public void testRoundLabelContainsCorrectText(FxRobot robot) {
        addRound(robot, new RoundInfo(1, "Player", "RobotA", "S", "D", 0, 5));

        Label label = (Label) getRoundsVBox(robot).getChildren().get(0);
        Assertions.assertThat(label.getText()).contains("Round 1");
        Assertions.assertThat(label.getText()).contains("RobotA");
        Assertions.assertThat(label.getText()).contains("S");
        Assertions.assertThat(label.getText()).contains("D");
    }

    @Test
    public void testMultipleRoundsDisplayed(FxRobot robot) {
        robot.interact(() -> {
            model.getRounds().add(new RoundInfo(1, "Player", "RobotA", "S", "D", 0, 5));
            model.getRounds().add(new RoundInfo(2, "Player", "RobotA", "D", "D", 1, 1));
            model.getRounds().add(new RoundInfo(3, "Player", "RobotA", "S", "S", 3, 3));
        });
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertThat(getRoundsVBox(robot).getChildren()).hasSize(3);
    }

    @Test
    public void testBackButtonClearsRoundsAndReturnsToList(FxRobot robot) {
        addRound(robot, new RoundInfo(1, "Player", "RobotA", "S", "D", 0, 5));
        Assertions.assertThat(getRoundsVBox(robot).getChildren()).hasSize(1);

        robot.clickOn("Back");
        WaitForAsyncUtils.waitForFxEvents();

        // Rounds cleared on model
        Assertions.assertThat(model.getRounds()).isEmpty();
        // Tournament list view is now showing
        Assertions.assertThat(robot.lookup("#openListView").tryQuery().isPresent()).isTrue();
    }
}

