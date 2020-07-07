package land.llama.birthday.service.selector;

import land.llama.birthday.config.BirthdayConfig;
import land.llama.birthday.domain.Citizen;
import land.llama.birthday.util.WorkDaysUtils;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * When a citizen of Llamaland turns 100 years old the King would like to send them a personal email.
 * He would like to be notified who should be sent an email at least five weekdays in advance
 * (the King never works weekends). If there are "a lot" of people turning 100 years old on a particular day
 * (a term we now understand to mean more than 20 people) the King would like 10 weekdays notice.
 */
public class CitizenSelectorImpl implements CitizenSelector {

    private final Clock clock;
    private final int workdaysBeforeBirthday;
    private final int workdaysBeforeBirthdayPick;
    private final int pickThreshold;
    private final int ageToCelebrate;

    public CitizenSelectorImpl(Clock clock, BirthdayConfig birthdayConfig) {
        this.clock = clock;
        this.workdaysBeforeBirthday = birthdayConfig.getWorkdaysBeforeBirthday();
        this.workdaysBeforeBirthdayPick = birthdayConfig.getWorkdaysBeforeBirthdayPick();
        this.pickThreshold = birthdayConfig.getPickThreshold();
        this.ageToCelebrate = birthdayConfig.getAgeToCelebrate();
    }

    @Override
    public List<Citizen> findCitizensWithBirthday(List<Citizen> citizens) {
        LocalDate now = LocalDate.now(clock);
        if (!WorkDaysUtils.isWeekDay(now)) {
            throw new IllegalStateException("King does not work over weekend!");
        }
        LocalDate birthday = WorkDaysUtils.addWeekDays(now, workdaysBeforeBirthday);
        LocalDate birthdayPick = WorkDaysUtils.addWeekDays(now, workdaysBeforeBirthdayPick);
        Map<LocalDate, Long> birthdaysByDate = citizens.stream()
                .map(Citizen::getBirthday)
                .map(date -> date.plusYears(ageToCelebrate))
                .collect(groupingBy(identity(), counting()));
        long numberOfCitizens = calculateNumberOfCandidates(now, birthday, birthdaysByDate);
        long numberOfCitizensForPick = calculateNumberOfCandidates(now, birthdayPick, birthdaysByDate);
        List<Citizen> luckyCitizens = new ArrayList<>();
        if (numberOfCitizens < pickThreshold) {
            luckyCitizens.addAll(findCitizensHavingBirthday(now, birthday, citizens));
        }
        if (numberOfCitizensForPick >= pickThreshold) {
            luckyCitizens.addAll(findCitizensHavingBirthday(now, birthdayPick, citizens));
        }
        return luckyCitizens;
    }

    private long calculateNumberOfCandidates(LocalDate now, LocalDate birthday, Map<LocalDate, Long> birthdaysByDate) {
        long numberOfCitizens = birthdaysByDate.getOrDefault(birthday, 0L);
        if (now.getDayOfWeek() == DayOfWeek.FRIDAY) {
            numberOfCitizens += birthdaysByDate.getOrDefault(birthday.plusDays(1), 0L);
            numberOfCitizens += birthdaysByDate.getOrDefault(birthday.plusDays(2), 0L);
        }
        return numberOfCitizens;
    }

    private List<Citizen> findCitizensHavingBirthday(LocalDate now, LocalDate birthday, List<Citizen> allCitizens) {
        return allCitizens.stream()
                .filter(hasBirthday(now, birthday))
                .collect(toList());
    }

    private Predicate<Citizen> hasBirthday(LocalDate now, LocalDate birthday) {
        return citizen -> {
            LocalDate citizenBirthday = citizen.getBirthday().plusYears(ageToCelebrate);
            if (citizenBirthday.equals(birthday)) {
                return true;
            } else if (now.getDayOfWeek() == DayOfWeek.FRIDAY) {
                return citizenBirthday.equals(birthday.plusDays(1))
                        || citizenBirthday.equals(birthday.plusDays(2));
            }
            return false;
        };
    }

}
