package land.llama.birthday.service.filter;

import land.llama.birthday.domain.Citizen;

import java.util.List;

/**
 * Allows to filter citizens based on criteria defined by implementation of this interface
 */
public interface CitizenFilter {

    List<Citizen> filter(List<Citizen> citizens);

}
