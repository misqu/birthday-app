package land.llama.birthday.service.filter;

import land.llama.birthday.domain.Citizen;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class DuplicateEmailFilter implements CitizenFilter {

    @Override
    public List<Citizen> filter(List<Citizen> citizens) {
        Set<String> duplicatedEmails = citizens.stream()
                .map(Citizen::getEmail)
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(toSet());
        return citizens.stream()
                .filter(citizen -> !duplicatedEmails.contains(citizen.getEmail()))
                .collect(toList());
    }

}
