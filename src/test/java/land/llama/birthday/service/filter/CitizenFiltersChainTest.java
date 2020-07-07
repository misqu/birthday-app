package land.llama.birthday.service.filter;

import land.llama.birthday.domain.Citizen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static land.llama.birthday.TestDataUtils.testCitizensFromEmails;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CitizenFiltersChainTest {

    private CitizenFilter duplicateEmailFilter;
    private CitizenFilter blackListEmailFilter;
    private List<Citizen> citizens;

    @BeforeEach
    void setUp() {
        this.duplicateEmailFilter = new DuplicateEmailFilter();
        this.blackListEmailFilter = new BlackListEmailFilter(Set.of("black@email.com"));
        this.citizens = testCitizensFromEmails("test@email.com", "black@email.com", "test@email.com");
    }

    @Test
    void filterWithNoFilter() {
        CitizenFilter filter = new CitizenFiltersChain();

        List<Citizen> filterResult = filter.filter(citizens);

        assertEquals(citizens, filterResult);
    }

    @Test
    void filterWithBlackListFilterOnly() {
        CitizenFilter filter = new CitizenFiltersChain(blackListEmailFilter);

        List<Citizen> filterResult = filter.filter(citizens);

        assertEquals(2, filterResult.size());
        assertEquals(citizens.get(0), filterResult.get(0));
        assertEquals(citizens.get(2), filterResult.get(1));
    }

    @Test
    void filterWithDuplicateEmailFilterOnly() {
        CitizenFilter filter = new CitizenFiltersChain(duplicateEmailFilter);

        List<Citizen> filterResult = filter.filter(citizens);

        assertEquals(1, filterResult.size());
        assertEquals(citizens.get(1), filterResult.get(0));
    }

    @Test
    void filterWithCombinedFiltersWhereDuplicateFirst() {
        CitizenFilter filter = new CitizenFiltersChain(duplicateEmailFilter, blackListEmailFilter);

        List<Citizen> filterResult = filter.filter(citizens);

        assertTrue(filterResult.isEmpty());
    }

    @Test
    void filterWithCombinedFiltersWhereBlackListFirst() {
        CitizenFilter filter = new CitizenFiltersChain(blackListEmailFilter, duplicateEmailFilter);

        List<Citizen> filterResult = filter.filter(citizens);

        assertTrue(filterResult.isEmpty());
    }

    @Test
    void filterWithCombinedFiltersForNoInput() {
        CitizenFilter filter = new CitizenFiltersChain(duplicateEmailFilter, blackListEmailFilter);

        List<Citizen> filterResult = filter.filter(emptyList());

        assertTrue(filterResult.isEmpty());
    }

}