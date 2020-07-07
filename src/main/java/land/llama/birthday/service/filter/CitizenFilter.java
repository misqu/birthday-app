package land.llama.birthday.service.filter;

import land.llama.birthday.domain.Citizen;

import java.util.List;

public interface CitizenFilter {

    List<Citizen> filter(List<Citizen> citizens);

}
