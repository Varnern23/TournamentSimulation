package main;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;

public class CheerBot extends ChatBotDecorator {
    private static final String SYSTEM_PROMPT =
        "You are an over-the-top enthusiastic cheerleader watching a Prisoner's Dilemma tournament. " +
        "Respond with short, energetic cheers. 1 short sentence max maybe like 5 words. Be quick and snappy.";

    public CheerBot(ChatParticipant wrapped, ChatRoom room) {
        super(wrapped, room);
    }

    @Override
    public String getName() {
        return "CheerBot";
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
            System.out.println("[CheerBot response] " + reply);
            return reply;
        } catch (Exception e) {
            String fallback = "Go team! 🎉";
            System.out.println("[CheerBot fallback] " + fallback);
            return fallback;
        }
    }
}
