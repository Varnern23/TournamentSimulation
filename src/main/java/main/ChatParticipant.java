package main;

public interface ChatParticipant {
    void receive(ChatMessage msg);
    String getName();
}
