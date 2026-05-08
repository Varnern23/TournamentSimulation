package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class ChatBotDecoratorTest {

    static class RecordingParticipant implements ChatParticipant {
        final List<ChatMessage> received = new ArrayList<>();
        private final String name;

        RecordingParticipant(String name) { this.name = name; }

        @Override public void receive(ChatMessage msg) { received.add(msg); }
        @Override public String getName() { return name; }
    }

    static class SyncEchoBot extends ChatBotDecorator {
        String lastReply = null;

        SyncEchoBot(ChatParticipant wrapped, ChatRoom room) {
            super(wrapped, room);
        }

        @Override
        public String getName() { return "SyncEchoBot"; }

        @Override
        protected String generateReply(ChatMessage msg) {
            lastReply = "Echo: " + msg.text();
            return lastReply;
        }
    }

    @Test
    void receiveChainsDelegateToWrapped() {
        RecordingParticipant inner = new RecordingParticipant("inner");
        ChatRoom room = new ChatRoom();
        SyncEchoBot bot = new SyncEchoBot(inner, room);
        room.join(bot);

        ChatMessage msg = new ChatMessage("User", "hello", false);
        bot.receive(msg);

        assertTrue(inner.received.contains(msg), "wrapped.receive() should have been called");
    }

    @Test
    void botDoesNotRespondToFromBotMessages() throws InterruptedException {
        RecordingParticipant inner = new RecordingParticipant("inner");
        ChatRoom room = new ChatRoom();
        SyncEchoBot bot = new SyncEchoBot(inner, room);
        room.join(bot);

        ChatMessage botMsg = new ChatMessage("AnotherBot", "I am a bot", true);
        bot.receive(botMsg);

        Thread.sleep(200);
        // inner only gets the original msg, no reply should be broadcast
        assertEquals(1, inner.received.size());
        assertNull(bot.lastReply);
    }

    @Test
    void botRespondsToHumanMessage() throws InterruptedException {
        RecordingParticipant inner = new RecordingParticipant("inner");
        ChatRoom room = new ChatRoom();
        SyncEchoBot bot = new SyncEchoBot(inner, room);
        room.join(bot);

        ChatMessage humanMsg = new ChatMessage("User", "nice game", false);
        bot.receive(humanMsg);

        Thread.sleep(300);
        // inner gets: original + reply broadcast
        assertTrue(inner.received.size() >= 2, "inner should receive original + bot reply");
        boolean hasReply = inner.received.stream()
            .anyMatch(m -> m.fromBot() && m.senderName().equals("SyncEchoBot"));
        assertTrue(hasReply, "A bot reply should have been broadcast");
    }

    @Test
    void echoBotLogsResponse() {
        RecordingParticipant inner = new RecordingParticipant("inner");
        ChatRoom room = new ChatRoom();
        EchoBot bot = new EchoBot(inner, room);
        room.join(bot);

        // Trigger generateReply directly to log output (ollama may be unavailable — fallback is tested)
        String reply = bot.generateReply(new ChatMessage("User", "test message", false));
        System.out.println("[ChatBotDecoratorTest] EchoBot reply: " + reply);
        assertNotNull(reply);
        assertFalse(reply.isBlank());
    }

    @Test
    void cheerBotLogsResponse() {
        RecordingParticipant inner = new RecordingParticipant("inner");
        ChatRoom room = new ChatRoom();
        CheerBot bot = new CheerBot(inner, room);
        room.join(bot);

        String reply = bot.generateReply(new ChatMessage("User", "amazing play!", false));
        System.out.println("[ChatBotDecoratorTest] CheerBot reply: " + reply);
        assertNotNull(reply);
        assertFalse(reply.isBlank());
    }

    @Test
    void trashTalkBotLogsResponse() {
        RecordingParticipant inner = new RecordingParticipant("inner");
        ChatRoom room = new ChatRoom();
        TrashTalkBot bot = new TrashTalkBot(inner, room);
        room.join(bot);

        String reply = bot.generateReply(new ChatMessage("User", "I cooperated!", false));
        System.out.println("[ChatBotDecoratorTest] TrashTalkBot reply: " + reply);
        assertNotNull(reply);
        assertFalse(reply.isBlank());
    }

    @Test
    void echoBotGetName() {
        EchoBot bot = new EchoBot(new RecordingParticipant("x"), new ChatRoom());
        assertEquals("EchoBot", bot.getName());
    }

    @Test
    void cheerBotGetName() {
        CheerBot bot = new CheerBot(new RecordingParticipant("x"), new ChatRoom());
        assertEquals("CheerBot", bot.getName());
    }

    @Test
    void trashTalkBotGetName() {
        TrashTalkBot bot = new TrashTalkBot(new RecordingParticipant("x"), new ChatRoom());
        assertEquals("TrashTalkBot", bot.getName());
    }

    @Test
    void blankReplyIsNotBroadcast() throws InterruptedException {
        RecordingParticipant inner = new RecordingParticipant("inner");
        ChatRoom room = new ChatRoom();
        ChatBotDecorator silentBot = new ChatBotDecorator(inner, room) {
            @Override public String getName() { return "Silent"; }
            @Override protected String generateReply(ChatMessage msg) { return "   "; }
        };
        room.join(silentBot);

        silentBot.receive(new ChatMessage("User", "hello", false));
        Thread.sleep(200);

        // inner gets only the original — blank reply must not be broadcast
        assertEquals(1, inner.received.size());
    }

    @Test
    void fullChainAllThreeBotsRespond() throws InterruptedException {
        RecordingParticipant bottom = new RecordingParticipant("bottom");
        ChatRoom room = new ChatRoom();
        ChatBotDecorator chain = new TrashTalkBot(
            new CheerBot(new EchoBot(bottom, room), room), room);
        room.join(chain);

        chain.receive(new ChatMessage("User", "good match", false));
        Thread.sleep(500);

        long botReplies = bottom.received.stream().filter(ChatMessage::fromBot).count();
        assertTrue(botReplies >= 3, "All three bots in the chain should reply: got " + botReplies);
    }

    @Test
    void humanParticipantGetName() {
        HumanParticipant p = new HumanParticipant("Viewer", null);
        assertEquals("Viewer", p.getName());
    }
}
