package com.example.paymentutil;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/*
 * @author Khan Hafizur Rahman
 * @since 3/1/22
 */
public class DateUtil {
    private DateUtil(){
    }

    private static final ZoneId ZONE_ID_DHAKA = ZoneId.of("Asia/Dhaka");
    private static final ZoneId ZONE_ID_UTC = ZoneId.of("UTC");

    public static final String TP_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter TP_DATE_TIMR_FORMATTER = DateTimeFormatter.ofPattern(TP_DATE_TIME_FORMAT);

    public static final String MW_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS Z";

    public static final DateTimeFormatter MW_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(MW_DATE_TIME_FORMAT);

    public static String formattedDateTime() {
        return MW_DATE_TIME_FORMATTER.format(getZoneDateTime());
    }

    private static ZonedDateTime getZoneDateTime() {
        return ZonedDateTime.ofInstant(Instant.now(), ZONE_ID_DHAKA);
    }

}
