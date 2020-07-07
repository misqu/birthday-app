package land.llama.birthday;

import land.llama.birthday.domain.Citizen;

import java.util.List;

public interface BirthdayNotifier {

    List<Citizen> findCitizensForWishes(List<String> citizenRawInput);

}
