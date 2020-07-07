package land.llama.birthday.util;

import java.time.LocalDate;

public final class WorkDaysUtils {

    private WorkDaysUtils() {
    }

    public static LocalDate addWeekDays(LocalDate fromDate, int weekDays) {
        LocalDate newDate = fromDate;
        int addedWeeksDays = 0;
        while (addedWeeksDays < weekDays) {
            newDate = newDate.plusDays(1);
            if (isWeekDay(newDate)) {
                ++addedWeeksDays;
            }
        }
        return newDate;
    }

    public static boolean isWeekDay(LocalDate date) {
        return switch (date.getDayOfWeek()) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> true;
            case SATURDAY, SUNDAY -> false;
        };
    }

}
