package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class ChatRoomTest {

    static class RecordingParticipant implements ChatParticipant {
        final List<ChatMessage> received = new ArrayList<>();
        private final String name;

        RecordingParticipant(String name) { this.name = name; }

        @Override public void receive(ChatMessage msg) { received.add(msg); }
        @Override public String getName() { return name; }
    }

    @Test
    void joinAddsParticipant() {
        ChatRoom room = new ChatRoom();
        RecordingParticipant p = new RecordingParticipant("P1");
        room.join(p);

        ChatMessage msg = new ChatMessage("sender", "hello", false);
        room.broadcast(msg);

        assertEquals(1, p.received.size());
        assertEquals(msg, p.received.get(0));
    }

    @Test
    void broadcastDeliversToAll() {
        ChatRoom room = new ChatRoom();
        RecordingParticipant p1 = new RecordingParticipant("P1");
        RecordingParticipant p2 = new RecordingParticipant("P2");
        room.join(p1);
        room.join(p2);

        room.broadcast(new ChatMessage("X", "test", false));

        assertEquals(1, p1.received.size());
        assertEquals(1, p2.received.size());
    }

    @Test
    void broadcastEmptyRoomNoException() {
        ChatRoom room = new ChatRoom();
        assertDoesNotThrow(() -> room.broadcast(new ChatMessage("X", "hi", false)));
    }

    @Test
    void multipleMessagesDeliveredInOrder() {
        ChatRoom room = new ChatRoom();
        RecordingParticipant p = new RecordingParticipant("P");
        room.join(p);

        room.broadcast(new ChatMessage("A", "first", false));
        room.broadcast(new ChatMessage("B", "second", false));

        assertEquals(2, p.received.size());
        assertEquals("first", p.received.get(0).text());
        assertEquals("second", p.received.get(1).text());
    }
}
