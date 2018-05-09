package com.shawnsofo.gilbarco.time.entity;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Timestamp {

    @JsonIgnore
    private final Clock clock;

    /**
     * creates a timestamp with calls default to 1
     */
    public Timestamp(Clock clock) {
        this.clock = clock;
        this.calls = 1L;
    }

    private final long calls;

    /**
     * returns Timestamp instance with the current time in EST as timestamp
     */
    public Timestamp(Long calls, Clock clock) {
        this.clock = clock;
        this.calls = Optional.ofNullable(calls).orElse(1L);
    }

    public Timestamp(Long calls) {
        this(calls, Clock.systemDefaultZone());
    }

    public long getCalls() {
        return this.calls;
    }

    /**
     * returns the current time in ISO 8601
     * 
     * also takes into account the Daylight saving time
     */
    @JsonProperty(access = Access.READ_ONLY)
    public String getTimestamp() {
        return Instant.now(clock).atZone(ZoneId.of("EST5EDT")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}