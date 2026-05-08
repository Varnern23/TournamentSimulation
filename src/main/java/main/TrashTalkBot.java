package main;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;

public class TrashTalkBot extends ChatBotDecorator {
    private static final String SYSTEM_PROMPT =
        "You are a sarcastic, trash-talking commentator watching a Prisoner's Dilemma tournament. " +
        "Mock the other chatters. 1 short sentences max mayble like 6 words total. Be quick and snappy.";

    public TrashTalkBot(ChatParticipant wrapped, ChatRoom room) {
        super(wrapped, room);
    }

    @Override
    public String getName() {
        return "TrashTalkBot";
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
            System.out.println("[TrashTalkBot response] " + reply);
            return reply;
        } catch (Exception e) {
            String fallback = "Is that really the best you've got?";
            System.out.println("[TrashTalkBot fallback] " + fallback);
            return fallback;
        }
    }
}
