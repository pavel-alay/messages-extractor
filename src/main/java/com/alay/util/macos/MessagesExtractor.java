package com.alay.util.macos;

import lombok.extern.java.Log;
import org.sqlite.SQLiteConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;

@Log
public class MessagesExtractor {

    private static final String JDBC_TEMPLATE = "jdbc:sqlite:{0}";

    private final String dbUrl;

    public MessagesExtractor(String dbPath) throws IOException {
        if (!Files.isReadable(Paths.get(dbPath))) {
            throw new IOException(format("Can''t read ''{0}''. Please, check if file exist and permissions are valid.", dbPath));
        }
        this.dbUrl = format(JDBC_TEMPLATE, dbPath);
    }

    public Map<String, List<Message>> getMessages(Collection<String> chats, LocalDateTime since) throws SQLException {
        return getMessages(StatementUtil.buildSelect(chats, since));
    }

    public Map<String, List<Message>> getMessages() throws SQLException {
        return getMessages(Collections.emptyList(), null);
    }

    private Map<String, List<Message>> getMessages(String statement) throws SQLException {
        try (Connection conn = getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(statement)) {

            Map<String, List<Message>> messages = new HashMap<>();
            while (rs.next()) {
                Message m = Message.builder()
                        .uuid(rs.getString(1))
                        .chat(rs.getString(2))
                        .date(rs.getLong(3))
                        .text(rs.getString(4))
                        .build();
                messages.computeIfAbsent(m.getChat(), k -> new ArrayList<>()).add(m);
            }
            return messages;
        }
    }

    private static Connection getConnection(String url) throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(true);
        return DriverManager.getConnection(url, config.toProperties());
    }
}
