package main;

import org.testfx.assertions.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import MVC.Model;
import MVC.ViewTransitionModel;

@ExtendWith(ApplicationExtension.class)
public class TournamentListControllerTest {

    Model model;
    BorderPane root;
    ViewTransitionModel vm;

    @Start
    private void start(Stage stage) {
        model = new Model();
        root = new BorderPane();
        vm = new ViewTransitionModel(root, model);
        vm.TournamentViewLoad();

        stage.setScene(new Scene(root));
        stage.show();
    }


    private void checkIP(FxRobot robot, String expected) {
        Assertions.assertThat(robot.lookup("#ipField").queryAs(TextField.class)).hasText(expected);
    }

    private void setIP(FxRobot robot, String ip) {
        TextField field = robot.lookup("#ipField").queryAs(TextField.class);
        robot.interact(() -> { field.requestFocus(); field.setText(ip); });
    }

    private void checkPort(FxRobot robot, String expected) {
        Assertions.assertThat(robot.lookup("#portField").queryAs(TextField.class)).hasText(expected);
    }

    private void setPort(FxRobot robot, String port) {
        TextField field = robot.lookup("#portField").queryAs(TextField.class);
        robot.interact(() -> { field.requestFocus(); field.setText(port); });
    }

    @SuppressWarnings("unchecked")
    private ListView<String> getOpenList(FxRobot robot) {
        return (ListView<String>) robot.lookup("#openListView").queryAll().iterator().next();
    }

    @SuppressWarnings("unchecked")
    private ListView<String> getClosedList(FxRobot robot) {
        return (ListView<String>) robot.lookup("#closedListView").queryAll().iterator().next();
    }


    @Test
    public void testIPFieldStartsEmpty(FxRobot robot) {
        checkIP(robot, "");
        setIP(robot, "192.168.1.1");
        checkIP(robot, "192.168.1.1");
    }

    @Test
    public void testPortFieldStartsEmpty(FxRobot robot) {
        checkPort(robot, "");
        setPort(robot, "8080");
        checkPort(robot, "8080");
    }

    @Test
    public void testOpenTournamentsDisplayed(FxRobot robot) {
        ListView<String> openList = getOpenList(robot);
        Assertions.assertThat(openList).isEmpty();

        ObservableList<String> tournaments = FXCollections.observableArrayList("Spring Open", "Summer Slam");
        robot.interact(() -> model.getOpenTournaments().setAll(tournaments));
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertThat(openList).hasExactlyNumItems(tournaments.size());
    }

    @Test
    public void testClosedTournamentsDisplayed(FxRobot robot) {
        ListView<String> closedList = getClosedList(robot);
        Assertions.assertThat(closedList).isEmpty();

        ObservableList<String> tournaments = FXCollections.observableArrayList("Winter Cup", "Autumn League");
        robot.interact(() -> model.getClosedTournaments().setAll(tournaments));
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertThat(closedList).hasExactlyNumItems(tournaments.size());
    }

    @Test
    public void testSelectingOpenTournamentNavigatesToSpectate(FxRobot robot) {
        robot.interact(() -> model.getOpenTournaments().setAll("Spring Open"));
        WaitForAsyncUtils.waitForFxEvents();

        ListView<String> openList = getOpenList(robot);
        robot.interact(() -> openList.getSelectionModel().select(0));
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertThat(model.selectedTournamentProperty().get()).isEqualTo("Spring Open");
        Assertions.assertThat(robot.lookup("#roundTable")).isNotNull();
    }

    @Test
    public void testSelectingClosedTournamentNavigatesToSpectate(FxRobot robot) {
        robot.interact(() -> model.getClosedTournaments().setAll("Winter Cup"));
        WaitForAsyncUtils.waitForFxEvents();

        ListView<String> closedList = getClosedList(robot);
        robot.interact(() -> closedList.getSelectionModel().select(0));
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertThat(model.selectedTournamentProperty().get()).isEqualTo("Winter Cup");
        Assertions.assertThat(robot.lookup("#roundTable")).isNotNull();
    }
}


