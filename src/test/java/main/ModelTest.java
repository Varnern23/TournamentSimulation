package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import MVC.Model;
import main.RoundInfo;

public class ModelTest {

    Model model;

    @BeforeEach
    public void setup() {
        model = new Model();
    }

    @Test
    public void testSelectedTournamentPropertyUpdates() {
        model.setSelectedTournament("Spring Open");
        assertEquals("Spring Open", model.selectedTournamentProperty().get());
    }

    @Test
    public void testSelectedTournamentPropertyFiresListener() {
        String[] fired = {null};
        model.selectedTournamentProperty().addListener((obs, oldVal, newVal) -> fired[0] = newVal);

        model.setSelectedTournament("Winter Cup");

        assertEquals("Winter Cup", fired[0]);
    }

    @Test
    public void testOpenTournamentsSetAllUpdatesList() {
        ObservableList<String> original = model.getOpenTournaments();
        model.getOpenTournaments().setAll("t1", "t2", "t3");

        assertEquals(3, model.getOpenTournaments().size());
        assertTrue(model.getOpenTournaments().containsAll(FXCollections.observableArrayList("t1", "t2", "t3")));
    }

    @Test
    public void testClosedTournamentsSetAllUpdatesList() {
        model.getClosedTournaments().setAll("t4", "t5");

        assertEquals(2, model.getClosedTournaments().size());
        assertTrue(model.getClosedTournaments().containsAll(FXCollections.observableArrayList("t4", "t5")));
    }

    @Test
    public void testRoundsStartEmpty() {
        assertTrue(model.getRounds().isEmpty());
    }

    @Test
    public void testRoundsListFiresListenerOnAdd() {
        boolean[] fired = {false};
        model.getRounds().addListener((javafx.collections.ListChangeListener<RoundInfo>) change -> {
            while (change.next()) {
                if (change.wasAdded()) fired[0] = true;
            }
        });

        model.getRounds().add(new RoundInfo(1, "Player", "RobotA", "COOPERATE", "DEFECT", 0, 5));

        assertTrue(fired[0]);
    }

    @Test
    public void testRoundsClearedAfterClear() {
        model.getRounds().add(new RoundInfo(1, "Player", "RobotA", "COOPERATE", "DEFECT", 0, 5));
        model.getRounds().add(new RoundInfo(2, "Player", "RobotB", "DEFECT", "COOPERATE", 5, 0));
        assertEquals(2, model.getRounds().size());

        model.getRounds().clear();

        assertTrue(model.getRounds().isEmpty());
    }

    @Test
    public void testIpPropertyUpdates() {
        model.ipProperty().set("192.168.1.1");
        assertEquals("192.168.1.1", model.ipProperty().get());
    }

    @Test
    public void testPortPropertyUpdates() {
        model.portNumProperty().set("8080");
        assertEquals("8080", model.portNumProperty().get());
    }

    @Test
    public void testIpPropertyFiresListener() {
        String[] fired = {null};
        model.ipProperty().addListener((obs, oldVal, newVal) -> fired[0] = newVal);

        model.ipProperty().set("10.0.0.1");

        assertEquals("10.0.0.1", fired[0]);
    }

    @Test
    public void testPortPropertyFiresListener() {
        String[] fired = {null};
        model.portNumProperty().addListener((obs, oldVal, newVal) -> fired[0] = newVal);

        model.portNumProperty().set("9090");

        assertEquals("9090", fired[0]);
    }
}