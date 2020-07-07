package land.llama.birthday;

import land.llama.birthday.domain.Citizen;
import land.llama.birthday.service.filter.CitizenFilter;
import land.llama.birthday.service.parser.CitizenParser;
import land.llama.birthday.service.selector.CitizenSelector;

import java.util.List;

/**
 * Parses, filters and selects citizens, which should receive email from King Tom I
 */
public class BirthdayNotifierImpl implements BirthdayNotifier {

    private final CitizenParser citizenParser;
    private final CitizenFilter citizenFilter;
    private final CitizenSelector citizenSelector;

    public BirthdayNotifierImpl(CitizenParser citizenParser, CitizenFilter citizenFilter, CitizenSelector citizenSelector) {
        this.citizenParser = citizenParser;
        this.citizenFilter = citizenFilter;
        this.citizenSelector = citizenSelector;
    }

    @Override
    public List<Citizen> findCitizensForWishes(List<String> citizenRawInput) {
        List<Citizen> citizens = citizenParser.parseCitizens(citizenRawInput);
        citizens = citizenFilter.filter(citizens);
        return citizenSelector.findCitizensWithBirthday(citizens);
    }

}
