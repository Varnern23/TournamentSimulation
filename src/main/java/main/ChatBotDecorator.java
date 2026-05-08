package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ChatBotDecorator implements ChatParticipant {
    protected final ChatParticipant wrapped;
    protected final ChatRoom room;

    private static final String LOG_FILE = "bot_responses.log";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ChatBotDecorator(ChatParticipant wrapped, ChatRoom room) {
        this.wrapped = wrapped;
        this.room = room;
    }

    @Override
    public void receive(ChatMessage msg) {
        wrapped.receive(msg);
        if (!msg.fromBot()) {
            new Thread(() -> {
                String reply = generateReply(msg);
                if (reply != null && !reply.isBlank()) {
                    logResponse(msg.text(), reply);
                    room.broadcast(new ChatMessage(getName(), reply, true));
                }
            }).start();
        }
    }

    private void logResponse(String input, String reply) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            pw.printf("[%s] %s%n  IN : %s%n  OUT: %s%n%n",
                LocalDateTime.now().format(FMT), getName(), input, reply);
        } catch (IOException e) {
            System.err.println("ChatBotDecorator: could not write to " + LOG_FILE + ": " + e.getMessage());
        }
    }

    protected abstract String generateReply(ChatMessage msg);
}
