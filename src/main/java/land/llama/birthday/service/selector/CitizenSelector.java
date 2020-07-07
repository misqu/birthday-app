package land.llama.birthday.service.selector;

import land.llama.birthday.domain.Citizen;

import java.util.List;

/**
 * Selects citizens, which should receive birthday wishes from King Tom I
 */
public interface CitizenSelector {

    List<Citizen> findCitizensWithBirthday(List<Citizen> citizens);

}
