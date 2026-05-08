package main;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HumanParticipant implements ChatParticipant {
    private final String name;
    private final VBox chatLog;

    public HumanParticipant(String name, VBox chatLog) {
        this.name = name;
        this.chatLog = chatLog;
    }

    @Override
    public void receive(ChatMessage msg) {
        Platform.runLater(() ->
            chatLog.getChildren().add(new Label("[" + msg.senderName() + "]: " + msg.text()))
        );
    }

    @Override
    public String getName() {
        return name;
    }
}
