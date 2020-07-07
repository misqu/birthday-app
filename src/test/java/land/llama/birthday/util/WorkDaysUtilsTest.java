package land.llama.birthday.util;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static land.llama.birthday.util.WorkDaysUtils.addWeekDays;
import static land.llama.birthday.util.WorkDaysUtils.isWeekDay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkDaysUtilsTest {

    @Test
    void testAddWeekDays() {
        LocalDate date = LocalDate.of(2020, 7, 1);
        assertEquals(LocalDate.of(2020, 7, 1), addWeekDays(date, 0));
        assertEquals(LocalDate.of(2020, 7, 2), addWeekDays(date, 1));
        assertEquals(LocalDate.of(2020, 7, 3), addWeekDays(date, 2));
        assertEquals(LocalDate.of(2020, 7, 6), addWeekDays(date, 3));
        assertEquals(LocalDate.of(2020, 7, 7), addWeekDays(date, 4));
        assertEquals(LocalDate.of(2020, 7, 8), addWeekDays(date, 5));
        assertEquals(LocalDate.of(2020, 7, 9), addWeekDays(date, 6));
        assertEquals(LocalDate.of(2020, 7, 10), addWeekDays(date, 7));
        assertEquals(LocalDate.of(2020, 7, 13), addWeekDays(date, 8));
        assertEquals(LocalDate.of(2020, 7, 14), addWeekDays(date, 9));
        assertEquals(LocalDate.of(2020, 7, 15), addWeekDays(date, 10));
    }

    @Test
    void isWeekDayMonday() {
        LocalDate date = LocalDate.of(2020, 7, 6);
        assertEquals(DayOfWeek.MONDAY, date.getDayOfWeek());
        assertTrue(isWeekDay(date));
    }

    @Test
    void isWeekDayTuesday() {
        LocalDate date = LocalDate.of(2020, 7, 7);
        assertEquals(DayOfWeek.TUESDAY, date.getDayOfWeek());
        assertTrue(isWeekDay(date));
    }

    @Test
    void isWeekDayWednesday() {
        LocalDate date = LocalDate.of(2020, 7, 8);
        assertEquals(DayOfWeek.WEDNESDAY, date.getDayOfWeek());
        assertTrue(isWeekDay(date));
    }

    @Test
    void isWeekDayThursday() {
        LocalDate date = LocalDate.of(2020, 7, 9);
        assertEquals(DayOfWeek.THURSDAY, date.getDayOfWeek());
        assertTrue(isWeekDay(date));
    }

    @Test
    void isWeekDayFriday() {
        LocalDate date = LocalDate.of(2020, 7, 10);
        assertEquals(DayOfWeek.FRIDAY, date.getDayOfWeek());
        assertTrue(isWeekDay(date));
    }

    @Test
    void isWeekDaySaturday() {
        LocalDate date = LocalDate.of(2020, 7, 11);
        assertEquals(DayOfWeek.SATURDAY, date.getDayOfWeek());
        assertFalse(isWeekDay(date));
    }

    @Test
    void isWeekDaySunday() {
        LocalDate date = LocalDate.of(2020, 7, 12);
        assertEquals(DayOfWeek.SUNDAY, date.getDayOfWeek());
        assertFalse(isWeekDay(date));
    }

}
