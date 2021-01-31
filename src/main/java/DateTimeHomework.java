import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

public class DateTimeHomework {

    private static final int BEGINNING_YEAR = 1900;
    private static final int END_YEAR = 2020;
    public static final String DATE_PATTERN = "MMM-yyyy";

    List<String> fridays13() {
        LocalDateTime beginning = LocalDateTime.of(2000, 1, 13, 0, 0);
        List<String> fridays13 = new ArrayList<>();
        do {
            if (beginning.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                fridays13.add(beginning.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
            }
        }
        while ((beginning = beginning.plusMonths(1)).isBefore(LocalDateTime.now()));
        return fridays13;
    }

    public List<YearMonth> endOnSundays() {
        LocalDateTime beginning = LocalDateTime.of(2000, 1, 1, 0, 0);
        List<YearMonth> endOnSundays = new ArrayList<>();
        do {
            beginning = beginning.plusDays(YearMonth.from(beginning).lengthOfMonth());
            if (beginning.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                endOnSundays.add(YearMonth.from(beginning));
                beginning = beginning.plusDays(1);
            }
            beginning = beginning.plusMonths(1);
        }
        while (beginning.isBefore(LocalDateTime.now()));
        return endOnSundays;
    }

    public List<Year> birthdaysOnSaturdays(LocalDate birthday) {
        List<Year> birthdaysOnSaturdays = new ArrayList<>();

        do {
            if (birthday.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                birthdaysOnSaturdays.add(Year.from(birthday));
            }
            birthday = birthday.plusYears(1);
        }
        while (birthday.isBefore(LocalDate.now()));
        return birthdaysOnSaturdays;
    }

    public List<MonthDay> daysNotWith24Hours(Year year) {
        return daysNotWith24Hours(year, ZoneId.systemDefault());
    }

    public List<MonthDay> daysNotWith24Hours(Year year, ZoneId zoneId) {
        List<MonthDay> daysNotWith24Hours = new ArrayList<>();
        ZonedDateTime currentDay = ZonedDateTime.of(
                LocalDateTime.of(year.get(ChronoField.YEAR), 1, 1, 0, 0), zoneId);
        ZonedDateTime nextDay = currentDay.plusDays(1);
        long daysInCurrentYear = ChronoUnit.DAYS.between(currentDay, currentDay.plusYears(1));
        for (int i = 0; i < daysInCurrentYear - 1; i++) {
            if (ChronoUnit.HOURS.between(currentDay, nextDay) != 24) {
                daysNotWith24Hours.add(MonthDay.from(currentDay));
            }
            currentDay = nextDay;
            nextDay = nextDay.plusDays(1);
        }
        return daysNotWith24Hours;
    }

    public List<ZoneId> zonesAlwaysClockShift(List<ZoneId> zones) {
        List<ZoneId> shiftZones = new ArrayList<>();

        for (ZoneId zone : zones) {
            if (zone.getRules().isFixedOffset()) {
                continue;
            }
            boolean isShifted = true;
            for (int i = BEGINNING_YEAR; i < END_YEAR; i++) {
                List<MonthDay> monthDays = daysNotWith24Hours(Year.of(i), zone);
                if (monthDays.isEmpty()) {
                    isShifted = false;
                    break;
                }
            }
            if (isShifted) {
                shiftZones.add(zone);
            }
        }
        return shiftZones;
    }

    public List<ZoneId> zonesNeverClockShift(List<ZoneId> zones) {
        List<ZoneId> shiftZones = new ArrayList<>();

        for (ZoneId zone : zones) {
            if (zone.getRules().isFixedOffset()) {
                shiftZones.add(zone);
                continue;
            }
            boolean isShifted = false;
            for (int i = BEGINNING_YEAR; i < END_YEAR; i++) {
                List<MonthDay> monthDays = daysNotWith24Hours(Year.of(i), zone);
                if (!monthDays.isEmpty()) {
                    isShifted = true;
                    break;
                }
            }
            if (!isShifted) {
                shiftZones.add(zone);
            }
        }
        return shiftZones;
    }

    public List<ZoneId> zonesChangedClockShiftRules(List<ZoneId> zones) {
        List<ZoneId> shiftZones = new ArrayList<>();

        for (ZoneId zone : zones) {
            boolean isShifted = false;
            boolean isNotShifted = false;
            for (int i = BEGINNING_YEAR; i < END_YEAR; i++) {
                List<MonthDay> monthDays = daysNotWith24Hours(Year.of(i), zone);
                if (!monthDays.isEmpty()) {
                    isShifted = true;
                }
                if (monthDays.isEmpty()) {
                    isNotShifted = true;
                }
                if (isShifted && isNotShifted) {
                    shiftZones.add(zone);
                    break;
                }
            }
        }
        return shiftZones;
    }
}
