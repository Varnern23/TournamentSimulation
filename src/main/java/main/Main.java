package main;

import MVC.Model;
import MVC.ViewTransitionModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Model model = new Model();
        BorderPane root = new BorderPane();
        ViewTransitionModel viewTransitionModel = new ViewTransitionModel(root, model);

        viewTransitionModel.TournamentViewLoad();

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Tournament Spectator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
