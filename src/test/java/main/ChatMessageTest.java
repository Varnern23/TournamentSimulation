package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ChatMessageTest {

    @Test
    void constructionStoresFields() {
        ChatMessage msg = new ChatMessage("Alice", "Hello", false);
        assertEquals("Alice", msg.senderName());
        assertEquals("Hello", msg.text());
        assertFalse(msg.fromBot());
    }

    @Test
    void fromBotTrue() {
        ChatMessage msg = new ChatMessage("EchoBot", "Nice move!", true);
        assertTrue(msg.fromBot());
    }

    @Test
    void fromBotFalse() {
        ChatMessage msg = new ChatMessage("You", "Let's go", false);
        assertFalse(msg.fromBot());
    }

    @Test
    void equalityByValue() {
        ChatMessage a = new ChatMessage("X", "hi", false);
        ChatMessage b = new ChatMessage("X", "hi", false);
        assertEquals(a, b);
    }

    @Test
    void inequalityDifferentSender() {
        ChatMessage a = new ChatMessage("A", "hi", false);
        ChatMessage b = new ChatMessage("B", "hi", false);
        assertNotEquals(a, b);
    }
}
