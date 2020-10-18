package com.alay.util.macos;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Apple time stamp is stored in SQLite as Integer, i.e. signed integer up to 8 bytes.
 * The high part is seconds since 2001-01-01 00:00:00 GMT.
 * And the low part is nanoseconds.
 */
public final class DateFormatUtil {
    private static final long APPLE_EPOCH = ZonedDateTime.of(2001, 1, 1, 0, 0, 0, 0, ZoneId.of("GMT"))
            .toEpochSecond();
    private static final long NANO_SECONDS = 1000000000L;

    public static LocalDateTime appleToDateTime(long appleFormat) {
        BigInteger[] split = BigInteger.valueOf(appleFormat)
                .divideAndRemainder(BigInteger.valueOf(NANO_SECONDS));
        split[0] = split[0].add(BigInteger.valueOf(APPLE_EPOCH));
        Instant instant = Instant.ofEpochSecond(split[0].intValueExact(), split[1].intValueExact());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static long dateTimeToApple(LocalDateTime time) {
        Instant instant = time.atZone(ZoneId.systemDefault()).toInstant();
        return (instant.getEpochSecond() - APPLE_EPOCH) * NANO_SECONDS + instant.getNano();
    }
}
