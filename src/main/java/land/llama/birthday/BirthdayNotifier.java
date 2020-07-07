package land.llama.birthday;

import land.llama.birthday.domain.Citizen;

import java.util.List;

/**
 * Returns list of citizens with incoming birthdays
 */
public interface BirthdayNotifier {

    List<Citizen> findCitizensForWishes(List<String> citizenRawInput);

}
