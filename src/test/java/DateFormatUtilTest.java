import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateFormatUtilTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    void appleToDateTime() {
        long apple = 610639579343947008L;
        LocalDateTime time = LocalDateTime.of(2020, 5,8,17,6,19,343947008);
        LocalDateTime timeApple = DateFormatUtil.appleToDateTime(apple);
        assertEquals(time, timeApple);
    }

    @Test
    void dateTimeToApple() {
        long apple = 610639579000000000L;
        LocalDateTime time = LocalDateTime.parse("2020-05-08 17:06:19", formatter);
        assertEquals(apple, DateFormatUtil.dateTimeToApple(time));
    }
}
