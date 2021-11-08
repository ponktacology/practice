package country.pvp.practice.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Messages {
    TEST("TEST", "Hello {player}"),
    PLAYER_JOINED_QUEUE("PLAYER_JOINED_QUEUE", "You have joined the {ranked} {queue} queue."),
    PLAYER_LEFT_QUEUE("PLAYER_LEFT_QUEUE", "You have left the queue.");

    private final String name;
    private final String value;

    public String get() {
        return value;
    }

    public String match(String placeholder, Object value) {
        return match(new MessagePattern(placeholder, value));
    }

    public String match(MessagePattern... patterns) {
        if (patterns == null) return value;

        String matched = value;

        for (MessagePattern pattern : patterns) {
            matched = pattern.translate(matched);
        }

        return matched;
    }
}
