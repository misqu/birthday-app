package land.llama.birthday.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BirthdayUtilsTest {

    @Test
    void parseBirthday() {
        assertEquals(LocalDate.of(2020, 7, 6), BirthdayUtils.parseBirthday("06-07-2020"));
        assertEquals(LocalDate.of(2020, 12, 31), BirthdayUtils.parseBirthday("31-12-2020"));
        assertEquals(LocalDate.of(2020, 1, 1), BirthdayUtils.parseBirthday("01-01-2020"));
    }

}
