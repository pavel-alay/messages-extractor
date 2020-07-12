package com.alay.util.macos;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Disabled("chat.db shall be copied out of ~/Library due to permissions.")
class MessagesExtractorTest {

    @Test
    void selectChats() throws SQLException, IOException {
        String dbLocation = System.getProperty("user.home") + "/Library/Messages/chat.db";

        MessagesExtractor messages = new MessagesExtractor(dbLocation);
        Map<String, List<Message>> chats = messages.getMessages();
        assertFalse(chats.isEmpty());
    }
}
