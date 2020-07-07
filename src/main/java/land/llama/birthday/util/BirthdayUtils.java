package land.llama.birthday.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class BirthdayUtils {

    private static final DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private BirthdayUtils() {
    }

    public static LocalDate parseBirthday(String birthday) {
        return LocalDate.parse(birthday, BIRTHDAY_FORMATTER);
    }

}
