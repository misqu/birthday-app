package land.llama.birthday;

import land.llama.birthday.domain.Citizen;
import land.llama.birthday.util.BirthdayUtils;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public final class TestDataUtils {

    private TestDataUtils() {
    }

    public static List<Citizen> testCitizensFromEmails(String... emails) {
        return Arrays.stream(emails)
                .map(email -> Citizen.builder()
                        .withEmail(email)
                        .build())
                .collect(toList());
    }

    public static List<Citizen> testCitizensFromBirthdays(String... birthdays) {
        return Arrays.stream(birthdays)
                .map(birthday -> Citizen.builder()
                        .withBirthday(BirthdayUtils.parseBirthday(birthday))
                        .build())
                .collect(toList());
    }

}
