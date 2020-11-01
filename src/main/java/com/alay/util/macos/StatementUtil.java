package com.alay.util.macos;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

import static java.text.MessageFormat.format;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class StatementUtil {

    private static final String BASE_SELECT = "SELECT m.guid, c.chat_identifier, m.date, m.text " +
            "FROM message m INNER JOIN chat c ON c.ROWID = m.handle_id";

    /**
     * Build SELECT statement to fetch messages.
     *
     * @param chats shall not be null. Empty collection means no filter.
     * @param since can be null.
     * @return SELECT statement.
     */
    static String buildSelect(Collection<String> chats, LocalDateTime since) {
        StringBuilder statementBuilder = new StringBuilder(BASE_SELECT);
        if (since != null || isNotBlank(chats)) {
            statementBuilder.append(" WHERE");
            int overhead = 0;
            if (since != null) {
                statementBuilder
                        .append(" m.date > ")
                        .append(DateFormatUtil.dateTimeToApple(since))
                        .append(" AND");
                overhead = " AND".length();
            }
            if (isNotBlank(chats)) {
                for (String chat : chats) {
                    statementBuilder.append(format(" c.chat_identifier LIKE ''%{0}%'' OR", chat));
                }
                overhead = " OR".length();
            }
            statementBuilder.setLength(statementBuilder.length() - overhead);
        }

        statementBuilder.append(" ORDER BY m.date");
        return statementBuilder.toString();
    }

    private static boolean isNotBlank(Collection<String> collection) {
        return collection != null && !collection.isEmpty();
    }
}
