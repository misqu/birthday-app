package land.llama.birthday;

import land.llama.birthday.config.BirthdayConfig;
import land.llama.birthday.domain.Citizen;
import land.llama.birthday.service.filter.BlackListEmailFilter;
import land.llama.birthday.service.filter.CitizenFilter;
import land.llama.birthday.service.filter.CitizenFiltersChain;
import land.llama.birthday.service.filter.DuplicateEmailFilter;
import land.llama.birthday.service.parser.CitizenParser;
import land.llama.birthday.service.parser.CitizenParserImpl;
import land.llama.birthday.service.selector.CitizenSelector;
import land.llama.birthday.service.selector.CitizenSelectorImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BirthdayApp {

    static final int WORK_DAYS_BEFORE_BIRTHDAY = 5;
    static final int WORK_DAYS_BEFORE_BIRTHDAY_PICK = 10;
    static final int PICK_THRESHOLD = 20;
    static final int AGE_TO_CELEBRATE = 100;

    public static BirthdayNotifier assembleNotifier(BirthdayConfig config, Clock clock, Set<String> blackListEmails) {
        CitizenParser citizenParser = new CitizenParserImpl();
        CitizenFilter citizenFilter = new CitizenFiltersChain(
                new BlackListEmailFilter(blackListEmails),
                new DuplicateEmailFilter());
        CitizenSelector citizenSelector = new CitizenSelectorImpl(clock, config);
        return new BirthdayNotifierImpl(citizenParser, citizenFilter, citizenSelector);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar birthday-app.jar pathToCitizenExtractFile pathToBlackListFile");
            System.exit(0);
        }
        List<String> citizenRawInput = null;
        try {
            citizenRawInput = Files.readAllLines(Path.of(args[0]));
        } catch (IOException e) {
            System.out.println("Can't parse citizen input file: " + e.getMessage());
            System.exit(0);
        }
        Set<String> blackListEmails = null;
        try {
            blackListEmails = new HashSet<>(Files.readAllLines(Path.of(args[1])));
        } catch (IOException e) {
            System.out.println("Can't parse black list input file: " + e.getMessage());
            System.exit(0);
        }
        BirthdayConfig config = new BirthdayConfig(WORK_DAYS_BEFORE_BIRTHDAY, WORK_DAYS_BEFORE_BIRTHDAY_PICK,
                PICK_THRESHOLD, AGE_TO_CELEBRATE);
        Clock clock = Clock.systemDefaultZone();
        BirthdayNotifier notifier = assembleNotifier(config, clock, blackListEmails);
        List<Citizen> luckyCitizens = notifier.findCitizensForWishes(citizenRawInput);
        System.out.println("Number of citizens found for incoming birthdays: " + luckyCitizens.size());
        luckyCitizens.forEach(System.out::println);
    }

}
