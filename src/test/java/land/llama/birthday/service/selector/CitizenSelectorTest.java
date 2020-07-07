package land.llama.birthday.service.selector;

import land.llama.birthday.config.BirthdayConfig;
import land.llama.birthday.domain.Citizen;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static java.util.Collections.emptyList;
import static land.llama.birthday.TestDataUtils.testCitizensFromBirthdays;
import static land.llama.birthday.util.BirthdayUtils.parseBirthday;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CitizenSelectorTest {

    private CitizenSelector citizenSelector;

    @Test
    void findCitizensForBirthday() {
        citizenSelector = citizenSelectorForDate("06-07-2020");
        List<Citizen> citizens = testCitizensFromBirthdays(
                "12-07-1920",
                "13-07-1920",
                "14-07-1920");

        List<Citizen> result = citizenSelector.findCitizensWithBirthday(citizens);

        assertEquals(1, result.size());
        assertEqualsDate("13-07-1920", result.get(0));
    }

    @Test
    void findCitizensForBirthdayOverWeekend() {
        citizenSelector = citizenSelectorForDate("10-07-2020");
        List<Citizen> citizens = testCitizensFromBirthdays(
                "16-07-1920",
                "17-07-1920",
                "18-07-1920",
                "19-07-1920",
                "20-07-1920");

        List<Citizen> result = citizenSelector.findCitizensWithBirthday(citizens);

        assertEquals(3, result.size());
        assertEqualsDate("17-07-1920", result.get(0));
        assertEqualsDate("18-07-1920", result.get(1));
        assertEqualsDate("19-07-1920", result.get(2));
    }

    @Test
    void findCitizensForBirthdayPick() {
        citizenSelector = citizenSelectorForDate("06-07-2020");
        List<Citizen> citizens = testCitizensFromBirthdays(
                "19-07-1920",
                "20-07-1920",
                "20-07-1920",
                "20-07-1920",
                "20-07-1920",
                "21-07-1920");

        List<Citizen> result = citizenSelector.findCitizensWithBirthday(citizens);

        assertEquals(4, result.size());
        assertEqualsDate("20-07-1920", result.get(0));
        assertEqualsDate("20-07-1920", result.get(1));
        assertEqualsDate("20-07-1920", result.get(2));
        assertEqualsDate("20-07-1920", result.get(3));
    }

    @Test
    void findCitizensForBirthdayPickOverWeekend() {
        citizenSelector = citizenSelectorForDate("03-07-2020");
        List<Citizen> citizens = testCitizensFromBirthdays(
                "17-07-1920",
                "18-07-1920",
                "19-07-1920",
                "19-07-1920",
                "20-07-1920");

        List<Citizen> result = citizenSelector.findCitizensWithBirthday(citizens);

        assertEquals(4, result.size());
        assertEqualsDate("17-07-1920", result.get(0));
        assertEqualsDate("18-07-1920", result.get(1));
        assertEqualsDate("19-07-1920", result.get(2));
        assertEqualsDate("19-07-1920", result.get(3));
    }

    @Test
    void findCitizensForBirthdayAndPickBirthday() {
        citizenSelector = citizenSelectorForDate("06-07-2020");
        List<Citizen> citizens = testCitizensFromBirthdays(
                "13-07-1920",
                "20-07-1920",
                "20-07-1920",
                "20-07-1920",
                "20-07-1920");

        List<Citizen> result = citizenSelector.findCitizensWithBirthday(citizens);

        assertEquals(5, result.size());
        assertEqualsDate("13-07-1920", result.get(0));
        assertEqualsDate("20-07-1920", result.get(1));
        assertEqualsDate("20-07-1920", result.get(2));
        assertEqualsDate("20-07-1920", result.get(3));
        assertEqualsDate("20-07-1920", result.get(4));
    }

    @Test
    void findCitizensForBirthdayPickAlreadyReported() {
        citizenSelector = citizenSelectorForDate("06-07-2020");
        List<Citizen> citizens = testCitizensFromBirthdays(
                "13-07-1920",
                "13-07-1920",
                "13-07-1920",
                "13-07-1920");

        List<Citizen> result = citizenSelector.findCitizensWithBirthday(citizens);

        assertEquals(0, result.size());
    }

    @Test
    void findCitizensExecutedOverWeekend() {
        citizenSelector = citizenSelectorForDate("05-07-2020");
        List<Citizen> citizens = testCitizensFromBirthdays("05-07-1920");

        IllegalStateException exception = assertThrows(
                IllegalStateException.class, () -> citizenSelector.findCitizensWithBirthday(citizens));
        assertEquals("King does not work over weekend!", exception.getMessage());
    }

    @Test
    void findCitizensWithNoInputData() {
        citizenSelector = citizenSelectorForDate("06-07-2020");
        List<Citizen> citizens = emptyList();

        List<Citizen> result = citizenSelector.findCitizensWithBirthday(citizens);

        assertTrue(result.isEmpty());
    }

    private void assertEqualsDate(String date, Citizen citizen) {
        assertEquals(parseBirthday(date), citizen.getBirthday());
    }

    private CitizenSelector citizenSelectorForDate(String birthdayInput) {
        Instant instant = parseBirthday(birthdayInput)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        int testPickThreshold = 4;
        BirthdayConfig config = new BirthdayConfig(5, 10, testPickThreshold, 100);
        return new CitizenSelectorImpl(clock, config);
    }

}
