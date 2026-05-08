package main;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private final List<ChatParticipant> participants = new ArrayList<>();

    public void join(ChatParticipant p) {
        participants.add(p);
    }

    public void broadcast(ChatMessage msg) {
        for (ChatParticipant p : participants) {
            p.receive(msg);
        }
    }
}
