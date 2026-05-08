package main;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;

public class EchoBot extends ChatBotDecorator {
    private static final String SYSTEM_PROMPT =
        "You are a witty commentor/chatter watching a Prisoner's Dilemma tournament. " +
        "start each message with exactly was told to you in quotes. Respond with a sharp tournament-related quip. 1 short sentences max like as short as possible. Be quick and snappy.";

    public EchoBot(ChatParticipant wrapped, ChatRoom room) {
        super(wrapped, room);
    }

    @Override
    public String getName() {
        return "EchoBot";
    }

    @Override
    protected String generateReply(ChatMessage msg) {
        try {
            OllamaAPI api = new OllamaAPI("http://localhost:11434");
            api.setRequestTimeoutSeconds(50);
            OllamaResult result = api.generate(
                "gemma3:270m",
                SYSTEM_PROMPT + "\nUser said: " + msg.text(),
                false,
                new OptionsBuilder().build()
            );
            String reply = result.getResponse().trim();
            System.out.println("[EchoBot response] " + reply);
            return reply;
        } catch (Exception e) {
            String fallback = "You said: " + msg.text();
            System.out.println("[EchoBot fallback] " + fallback);
            return fallback;
        }
    }
}
