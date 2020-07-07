package land.llama.birthday.service.filter;

import land.llama.birthday.domain.Citizen;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class BlackListEmailFilter implements CitizenFilter {

    private final Set<String> blackListEmails;

    public BlackListEmailFilter(Set<String> blackListEmails) {
        this.blackListEmails = blackListEmails;
    }

    @Override
    public List<Citizen> filter(List<Citizen> citizens) {
        return citizens.stream()
                .filter(citizen -> !blackListEmails.contains(citizen.getEmail()))
                .collect(toList());
    }

}
