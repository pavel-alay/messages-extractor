import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Message {

    private final UUID uuid;
    private final String text;
    private final String chat;
    private final LocalDateTime date;

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    static class MessageBuilder {
        private LocalDateTime date;
        private UUID uuid;

        public MessageBuilder date(long date) {
            this.date = DateFormatUtil.appleToDateTime(date);
            return this;
        }

        public MessageBuilder uuid(String uuid) {
            this.uuid = UUID.fromString(uuid);
            return this;
        }
    }
}
