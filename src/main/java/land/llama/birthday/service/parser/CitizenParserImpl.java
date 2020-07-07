package land.llama.birthday.service.parser;

import land.llama.birthday.domain.Citizen;
import land.llama.birthday.util.BirthdayUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CitizenParserImpl implements CitizenParser {

    @Override
    public List<Citizen> parseCitizens(List<String> rawInput) {
        return rawInput.stream()
                .map(this::parseCitizen)
                .collect(toList());
    }

    private Citizen parseCitizen(String input) {
        String[] tokens = input.split(",");
        if (tokens.length != 4) {
            throw new IllegalArgumentException("Wrong number of properties for: " + input);
        }
        if (isNullOrEmpty(tokens[2].trim())) {
            throw new IllegalArgumentException("birthday can not be empty");
        }
        if (isNullOrEmpty(tokens[3].trim())) {
            throw new IllegalArgumentException("email can not be empty");
        }
        return Citizen.builder()
                .withLastName(tokens[0].trim())
                .withFirstName(tokens[1].trim())
                .withBirthday(BirthdayUtils.parseBirthday(tokens[2].trim()))
                .withEmail(tokens[3].trim())
                .build();
    }

    private boolean isNullOrEmpty(String token) {
        return token == null || "".equals(token);
    }

}
