/**
 * Unit tests for Timestamp entity
 */
package com.shawnsofo.gilbarco.time.entity;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@RunWith(Parameterized.class)
public class TimestampDifferentClockTests {

    /**
     * The injected clock
     */
    private Clock clock;

    public TimestampDifferentClockTests(Clock clock) {
        this.clock = clock;
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        /**
         * tests with clocks from all available timezones
         */
        return ZoneId.getAvailableZoneIds().stream().map(zoneId -> new Object[] { Clock.system(ZoneId.of(zoneId)) })
                .collect(Collectors.toList());

    }

    private ZonedDateTime getTimestampNow() {
        return ZonedDateTime.parse(new Timestamp(this.clock).getTimestamp(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    /**
     * makes sure that the time is in the ISO8601 format
     */
    @Test
    public void testTimeFormat() {
        assertThatCode(() -> {
            this.getTimestampNow();
        }).doesNotThrowAnyException();
    }

    /**
     * compares the implementation of Timestamp to a standard timestamp
     * implementation (Joda time)
     * 
     * allows maximum 60 seconds different between the two implementations
     * 
     */
    @Test
    public void testCurrentTimeInEST() {
        DateTime expectedJoda = new DateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone("EST5EDT")));
        ZonedDateTime got = this.getTimestampNow();
        assertThat(got.getDayOfYear()).isEqualTo(expectedJoda.getDayOfYear());
        assertThat(got.getDayOfMonth()).isEqualTo(expectedJoda.getDayOfMonth());
        assertThat(got.getDayOfWeek().getValue()).isEqualTo(expectedJoda.getDayOfWeek());
        assertThat(got.getHour()).isEqualTo(expectedJoda.getHourOfDay());
        assertThat(got.getMinute()).isEqualTo(expectedJoda.getMinuteOfHour());
    }
}