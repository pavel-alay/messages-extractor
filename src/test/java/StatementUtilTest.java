import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatementUtilTest {

    @Test
    void buildSelectWithoutFilter() {
        String select = StatementUtil.buildSelect(Collections.emptyList(), null);
        String expected = "SELECT m.guid, c.chat_identifier, m.date, m.text FROM message m " +
                "INNER JOIN chat c ON c.ROWID = m.handle_id ORDER BY m.date";
        assertEquals(expected, select);
    }

    @Test
    void buildSelectWithSince() {
        String select = StatementUtil.buildSelect(Collections.emptyList(),
                LocalDate.of(2020, 2, 29).atStartOfDay());
        String expected = "SELECT m.guid, c.chat_identifier, m.date, m.text FROM message m " +
                "INNER JOIN chat c ON c.ROWID = m.handle_id WHERE m.date > 604616400000000000 ORDER BY m.date";
        assertEquals(expected, select);
    }

    @Test
    void buildSelectWithSinceAndChats() {
        String select = StatementUtil.buildSelect(Arrays.asList("Pizza", "Sushi"),
                LocalDate.of(2020, 2, 29).atStartOfDay());
        String expected = "SELECT m.guid, c.chat_identifier, m.date, m.text FROM message m " +
                "INNER JOIN chat c ON c.ROWID = m.handle_id WHERE m.date > 604616400000000000 " +
                "AND c.chat_identifier LIKE '%Pizza%' OR c.chat_identifier LIKE '%Sushi%' ORDER BY m.date";
        assertEquals(expected, select);
    }
}
