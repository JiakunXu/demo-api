package com.example.demo.framework.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@ResourceLock("JODA_TIME_GLOBAL_STATE")
class DateUtilTest {
    private DateTimeZone previousZone;
    private final long instant = java.time.Instant.parse("2024-02-29T23:59:58Z").toEpochMilli();

    @BeforeEach
    void fixClockAndZone() {
        previousZone = DateTimeZone.getDefault();
        DateTimeZone.setDefault(DateTimeZone.UTC);
        DateTimeUtils.setCurrentMillisFixed(instant);
    }

    @AfterEach
    void restoreClockAndZone() {
        DateTimeUtils.setCurrentMillisSystem();
        DateTimeZone.setDefault(previousZone);
    }

    @Test
    void currentDateAndComponentsUseTheSameInstant() {
        assertThat(DateUtil.getNowDate()).isEqualTo("2024-02-29");
        assertThat(DateUtil.getNowDateTime()).isEqualTo("2024-02-29 23:59:58");
        assertThat(DateUtil.getNowDateTime("yyyy/MM/dd")).isEqualTo("2024/02/29");
        assertThat(DateUtil.getYear()).isEqualTo(2024);
        assertThat(DateUtil.getMonth()).isEqualTo(2);
        assertThat(DateUtil.getWeek()).isEqualTo(9);
        assertThat(DateUtil.getDayOfMonth()).isEqualTo(29);
        assertThat(DateUtil.getDayOfWeek()).isEqualTo(4);
    }

    @Test
    void parsesAndFormatsDefaultAndCustomPatterns() {
        Date date = new Date(instant);
        assertThat(DateUtil.getDate(date)).isEqualTo("2024-02-29");
        assertThat(DateUtil.getDateTime(date)).isEqualTo("2024-02-29 23:59:58");
        assertThat(DateUtil.getDateTime(date, "yyyy/MM/dd HH:mm")).isEqualTo("2024/02/29 23:59");
        assertThat(DateUtil.getDate("2024-02-29")).isEqualTo(Date.from(java.time.Instant.parse("2024-02-29T00:00:00Z")));
        assertThat(DateUtil.getDateTime("2024-02-29 23:59:58")).isEqualTo(date);
        assertThat(DateUtil.getDateTime("2024/02/29 23:59:58", "yyyy/MM/dd HH:mm:ss")).isEqualTo(date);
        assertThatThrownBy(() -> DateUtil.getDate("2023-02-29")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void additionCrossesLeapDayAndDoesNotMutateInput() {
        Date date = new Date(instant);
        assertThat(DateUtil.getDateTime(DateUtil.addDays(date, 1))).isEqualTo("2024-03-01 23:59:58");
        assertThat(DateUtil.getDateTime(DateUtil.addHours(date, 1))).isEqualTo("2024-03-01 00:59:58");
        assertThat(DateUtil.getDateTime(DateUtil.addMinutes(date, -1))).isEqualTo("2024-02-29 23:58:58");
        assertThat(DateUtil.getDateTime(DateUtil.addSeconds(date, 2))).isEqualTo("2024-03-01 00:00:00");
        assertThat(date.getTime()).isEqualTo(instant);
    }

    @Test
    void differencesPreserveSignAndCountWholeUnits() {
        Date start = new Date(instant);
        Date end = new Date(instant + 90061000);
        assertThat(DateUtil.getDaysBetween(start, end)).isEqualTo(1);
        assertThat(DateUtil.getHoursBetween(start, end)).isEqualTo(25);
        assertThat(DateUtil.getMinutesBetween(start, end)).isEqualTo(1501);
        assertThat(DateUtil.getSecondsBetween(start, end)).isEqualTo(90061);
        assertThat(DateUtil.getDaysBetween(end, start)).isEqualTo(-1);
        assertThat(DateUtil.getSecondsBetween(start, start)).isZero();
    }

    @Test
    void comparisonsAreStrictAtTheCurrentInstant() {
        assertThat(DateUtil.isAfterNow(new Date(instant + 1))).isTrue();
        assertThat(DateUtil.isBeforeNow(new Date(instant - 1))).isTrue();
        assertThat(DateUtil.isAfterNow(new Date(instant))).isFalse();
        assertThat(DateUtil.isBeforeNow(new Date(instant))).isFalse();
    }

    @Test
    void addingCalendarDayAcrossDstIsNotAddingTwentyFourHours() {
        DateTimeZone.setDefault(DateTimeZone.forID("America/New_York"));
        Date start = new DateTime(2024, 3, 9, 12, 0, DateTimeZone.getDefault()).toDate();
        Date nextDay = DateUtil.addDays(start, 1);
        assertThat(DateUtil.getDateTime(nextDay)).isEqualTo("2024-03-10 12:00:00");
        assertThat(DateUtil.getHoursBetween(start, nextDay)).isEqualTo(23);
        assertThat(DateUtil.getDaysBetween(start, nextDay)).isEqualTo(1);
    }
}
