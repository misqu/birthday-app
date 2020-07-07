package land.llama.birthday.service.selector;

import land.llama.birthday.domain.Citizen;

import java.util.List;

public interface CitizenSelector {

    List<Citizen> findCitizensWithBirthday(List<Citizen> citizens);

}
