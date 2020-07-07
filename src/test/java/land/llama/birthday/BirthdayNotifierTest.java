package land.llama.birthday;

import land.llama.birthday.config.BirthdayConfig;
import land.llama.birthday.domain.Citizen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import static land.llama.birthday.BirthdayApp.AGE_TO_CELEBRATE;
import static land.llama.birthday.BirthdayApp.WORK_DAYS_BEFORE_BIRTHDAY;
import static land.llama.birthday.BirthdayApp.WORK_DAYS_BEFORE_BIRTHDAY_PICK;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BirthdayNotifierTest {

    private BirthdayNotifier application;

    @BeforeEach
    void setUp() {
        LocalDate executionDate = LocalDate.of(2020, 7, 6);
        int pickNumber = 3;
        BirthdayConfig config = new BirthdayConfig(
                WORK_DAYS_BEFORE_BIRTHDAY, WORK_DAYS_BEFORE_BIRTHDAY_PICK, pickNumber, AGE_TO_CELEBRATE);
        Instant instant = executionDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        Set<String> blackListEmails = Set.of("hateking@email.com", "dontlikeking@email.com");
        this.application = BirthdayApp.assembleNotifier(config, clock, blackListEmails);
    }

    @Test
    void findCitizensForWishes() {
        List<String> citizenRawInput = List.of(
                "Last1,First1,13-07-1920,duplicated@email.com",
                "Last2,First2,13-07-1920,test1@email.com",
                "Last3,First3,13-07-1920,hateking@email.com",
                "Last4,First4,13-07-1920,test2@email.com",
                "Last5,First5,20-07-1920,duplicated@email.com",
                "Last6,First6,20-07-1920,test3@email.com",
                "Last7,First7,20-07-1920,dontlikeking@email.com",
                "Last8,First8,20-07-1920,test4@email.com",
                "Last9,First9,20-07-1920,test5@email.com");

        List<Citizen> citizensToReceiveWishes = application.findCitizensForWishes(citizenRawInput);

        assertEquals(5, citizensToReceiveWishes.size());
        assertEquals("test1@email.com", citizensToReceiveWishes.get(0).getEmail());
        assertEquals("test2@email.com", citizensToReceiveWishes.get(1).getEmail());
        assertEquals("test3@email.com", citizensToReceiveWishes.get(2).getEmail());
        assertEquals("test4@email.com", citizensToReceiveWishes.get(3).getEmail());
        assertEquals("test5@email.com", citizensToReceiveWishes.get(4).getEmail());
    }

}
