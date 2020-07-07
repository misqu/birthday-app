package land.llama.birthday.service.filter;

import land.llama.birthday.domain.Citizen;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static land.llama.birthday.TestDataUtils.testCitizensFromEmails;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DuplicateEmailFilterTest {

    private final CitizenFilter filter = new DuplicateEmailFilter();

    @Test
    void filterForNoDuplicates() {
        List<Citizen> citizens = testCitizensFromEmails("test@email.com", "vip@email.com");

        List<Citizen> filterResult = filter.filter(citizens);

        assertEquals(2, filterResult.size());
    }

    @Test
    void filterForSomeDuplicates() {
        List<Citizen> citizens = testCitizensFromEmails("test@email.com", "vip@email.com", "test@email.com");

        List<Citizen> filterResult = filter.filter(citizens);

        assertEquals(1, filterResult.size());
        assertEquals(citizens.get(1), filterResult.get(0));
    }

    @Test
    void filterForAllDuplicates() {
        List<Citizen> citizens = testCitizensFromEmails(
                "test@email.com", "vip@email.com", "test@email.com", "vip@email.com");

        List<Citizen> filterResult = filter.filter(citizens);

        assertTrue(filterResult.isEmpty());
    }

    @Test
    void filterForEmptyInput() {
        List<Citizen> citizens = Collections.emptyList();

        List<Citizen> filterResult = filter.filter(citizens);

        assertTrue(filterResult.isEmpty());
    }

}
