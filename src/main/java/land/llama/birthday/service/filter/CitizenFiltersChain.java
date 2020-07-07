package land.llama.birthday.service.filter;

import land.llama.birthday.domain.Citizen;

import java.util.Arrays;
import java.util.List;

/**
 * Combines multiple CitizenFilters
 */
public class CitizenFiltersChain implements CitizenFilter {

    private final List<CitizenFilter> filters;

    public CitizenFiltersChain(CitizenFilter... filters) {
        this.filters = Arrays.asList(filters);
    }

    @Override
    public List<Citizen> filter(List<Citizen> citizens) {
        List<Citizen> result = citizens;
        for (CitizenFilter filter : filters) {
            result = filter.filter(result);
        }
        return result;
    }

}
