package land.llama.birthday.service.parser;

import land.llama.birthday.domain.Citizen;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CitizenParserTest {

    private final CitizenParser citizenParser = new CitizenParserImpl();

    @Test
    void parseCitizen() {
        List<String> rawData = List.of(
                "Brown,Bobby,10-11-1950,bobby.brown@ilovellamaland.com",
                "O'Rourke,Betsy,28-02-1900,betsy@heyitsme.com",
                "Von Tappo,Alfredo,01-01-1920,alfie@vontappo.llama.land");

        List<Citizen> citizens = citizenParser.parseCitizens(rawData);

        assertEquals(3, citizens.size());
        assertEquals("Brown", citizens.get(0).getLastName());
        assertEquals("Bobby", citizens.get(0).getFirstName());
        assertEquals("bobby.brown@ilovellamaland.com", citizens.get(0).getEmail());
        assertEquals(LocalDate.of(1950, 11, 10), citizens.get(0).getBirthday());
        assertEquals("O'Rourke", citizens.get(1).getLastName());
        assertEquals("Betsy", citizens.get(1).getFirstName());
        assertEquals("betsy@heyitsme.com", citizens.get(1).getEmail());
        assertEquals(LocalDate.of(1900, 2, 28), citizens.get(1).getBirthday());
        assertEquals("Von Tappo", citizens.get(2).getLastName());
        assertEquals("Alfredo", citizens.get(2).getFirstName());
        assertEquals("alfie@vontappo.llama.land", citizens.get(2).getEmail());
        assertEquals(LocalDate.of(1920, 1, 1), citizens.get(2).getBirthday());
    }

    @Test
    void parseWrongValueDate() {
        List<String> rawData = List.of("Brown,Bobby,10-13-1950,bobby@llamaland.com");

        DateTimeParseException exception = assertThrows(
                DateTimeParseException.class, () -> citizenParser.parseCitizens(rawData));
        assertTrue(exception.getMessage().contains("Invalid value for MonthOfYear"));
    }

    @Test
    void parseWrongFormatDate() {
        List<String> rawData = List.of("Brown,Bobby,1950-01-01,bobby@llamaland.com");

        DateTimeParseException exception = assertThrows(
                DateTimeParseException.class, () -> citizenParser.parseCitizens(rawData));
        assertTrue(exception.getMessage().contains("could not be parsed"));
    }

    @Test
    void parseDataFormat() {
        List<String> rawData = List.of("Brown,Bobby,MiddleName,10-11-1950,bobby@llamaland.com");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> citizenParser.parseCitizens(rawData));
        assertEquals("Wrong number of properties for: " + rawData.get(0), exception.getMessage());
    }

    @Test
    void parseMissingBirthday() {
        List<String> rawData = List.of("Brown,Bobby, ,bobby@llamaland.com");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> citizenParser.parseCitizens(rawData));
        assertEquals("birthday can not be empty", exception.getMessage());
    }

    @Test
    void parseMissingEmail() {
        List<String> rawData = List.of("Brown,Bobby,10-11-1950, ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> citizenParser.parseCitizens(rawData));
        assertEquals("email can not be empty", exception.getMessage());
    }

    @Test
    void parseWithNoInput() {
        List<Citizen> citizens = citizenParser.parseCitizens(emptyList());

        assertTrue(citizens.isEmpty());
    }

}
