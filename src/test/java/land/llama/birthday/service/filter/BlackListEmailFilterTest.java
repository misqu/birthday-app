package land.llama.birthday.service.filter;

import land.llama.birthday.domain.Citizen;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static land.llama.birthday.TestDataUtils.testCitizensFromEmails;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlackListEmailFilterTest {

    @Test
    void filterWithNoBlackListMatch() {
        CitizenFilter citizenFilter = new BlackListEmailFilter(Set.of("noway@email.com"));
        List<Citizen> citizens = testCitizensFromEmails("test@email.com", "vip@email.com");

        List<Citizen> filterResult = citizenFilter.filter(citizens);

        assertEquals(citizens, filterResult);
    }

    @Test
    void filterWithSomeBlackListMatch() {
        CitizenFilter citizenFilter = new BlackListEmailFilter(Set.of("noway@email.com"));
        List<Citizen> citizens = testCitizensFromEmails("test@email.com", "noway@email.com");

        List<Citizen> filterResult = citizenFilter.filter(citizens);

        assertEquals(1, filterResult.size());
        assertEquals(citizens.get(0), filterResult.get(0));
    }

    @Test
    void filterWithAllBlackListMatch() {
        CitizenFilter citizenFilter = new BlackListEmailFilter(Set.of("noway@email.com", "block@email.com"));
        List<Citizen> citizens = testCitizensFromEmails("block@email.com", "noway@email.com");

        List<Citizen> filterResult = citizenFilter.filter(citizens);

        assertTrue(filterResult.isEmpty());
    }

    @Test
    void filterWithEmptyBlackList() {
        CitizenFilter citizenFilter = new BlackListEmailFilter(emptySet());
        List<Citizen> citizens = testCitizensFromEmails("test@email.com", "vip@email.com");

        List<Citizen> filterResult = citizenFilter.filter(citizens);

        assertEquals(citizens, filterResult);
    }

    @Test
    void filterWithEmptyInput() {
        CitizenFilter citizenFilter = new BlackListEmailFilter(Set.of("noway@email.com"));
        List<Citizen> citizens = Collections.emptyList();

        List<Citizen> filterResult = citizenFilter.filter(citizens);

        assertEquals(citizens, filterResult);
    }

}
