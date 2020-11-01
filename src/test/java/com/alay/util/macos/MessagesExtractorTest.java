package com.alay.util.macos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Disabled("chat.db shall be copied out of ~/Library due to permissions.")
class MessagesExtractorTest {

    MessagesExtractor extractor;

    @BeforeEach
    void setup() throws IOException {
        String dbLocation = System.getProperty("user.home") + "/Library/Messages/chat.db";
        extractor = new MessagesExtractor(dbLocation);
    }

    @Test
    void selectAllChats() throws SQLException {
        Map<String, List<Message>> chats = extractor.getMessages();
        System.out.println(chats.size());
        assertFalse(chats.isEmpty());
    }

    @Test
    void selectChatsLastThreeDays() throws SQLException {
        Map<String, List<Message>> chats = extractor.getMessages(LocalDate.now().minusDays(3).atStartOfDay());
        System.out.println(chats.size());
        assertFalse(chats.isEmpty());
    }

    @Test
    void selectChatsLastYear() throws SQLException {
        Map<String, List<Message>> chats = extractor.getMessages(Arrays.asList("Google", "Apple"),
                LocalDate.now().minusYears(1).atStartOfDay());
        System.out.println(chats.size());
        assertFalse(chats.isEmpty());
    }

    @Test
    void selectChats() throws SQLException {
        Map<String, List<Message>> chats = extractor.getMessages(Arrays.asList("Google", "Apple"));
        System.out.println(chats.size());
        assertFalse(chats.isEmpty());
    }
}
