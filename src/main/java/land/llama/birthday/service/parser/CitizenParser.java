package land.llama.birthday.service.parser;

import land.llama.birthday.domain.Citizen;

import java.util.List;

/**
 * Parses raw input, which defines citizens
 */
public interface CitizenParser {

    List<Citizen> parseCitizens(List<String> rawInput);

}
