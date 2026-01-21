package com.hawa.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class DateTimeUtil {

    public static String toIsoString(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toString() : null;
    }

    public static OffsetDateTime fromIsoString(String isoString) {
        return isoString != null ? OffsetDateTime.parse(isoString) : null;
    }

    public static LocalDateTime toUtc(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null
                ? offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime()
                : null;
    }

    public static Integer getOffsetMinutes(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null
                ? offsetDateTime.getOffset().getTotalSeconds() / 60
                : null;
    }

    public static OffsetDateTime reconstruct(LocalDateTime utcTime, Integer offsetMinutes) {
        if (utcTime == null || offsetMinutes == null) return null;
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(offsetMinutes * 60);
        return OffsetDateTime.of(utcTime, offset);
    }

    public static String formatHumanReadable(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        List<String> parts = new ArrayList<>();
        if (hours > 0) parts.add(hours + (hours == 1 ? " hour" : " hours"));
        if (minutes > 0) parts.add(minutes + (minutes == 1 ? " minute" : " minutes"));
        if (secs > 0) parts.add(secs + (secs == 1 ? " second" : " seconds"));

        if (parts.isEmpty()) return "0 seconds";

        if (parts.size() == 1) return parts.get(0);
        if (parts.size() == 2) return parts.get(0) + " and " + parts.get(1);

        return String.join(", ", parts.subList(0, parts.size() - 1))
                + " and " + parts.get(parts.size() - 1);
    }
}
