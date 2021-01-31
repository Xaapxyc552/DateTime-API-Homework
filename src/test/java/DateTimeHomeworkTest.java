import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class DateTimeHomeworkTest {

    static final DateTimeHomework homework = new DateTimeHomework();

    @Test
    public void testFridays13() {
        List<String> list = homework.fridays13();
        Assert.assertEquals("Oct-2000", list.get(0));
        Assert.assertEquals("Apr-2001", list.get(1));
        Assert.assertEquals("Jul-2001", list.get(2));
        Assert.assertEquals("Sep-2002", list.get(3));
        Assert.assertEquals("Jan-2017", list.get(29));
        Assert.assertEquals("Dec-2019", list.get(34));
        Assert.assertEquals("Nov-2020", list.get(36));
    }

    @Test
    public void testEndOnSundays() {
        List<YearMonth> yearMonths = homework.endOnSundays();
        Assert.assertTrue(yearMonths.contains(YearMonth.of(2007, 12)));
        Assert.assertTrue(yearMonths.contains(YearMonth.of(2002, 2)));
        Assert.assertTrue(yearMonths.contains(YearMonth.of(2012, 4)));
        Assert.assertTrue(yearMonths.contains(YearMonth.of(2018, 12)));
        Assert.assertTrue(yearMonths.contains(YearMonth.of(2020, 4)));
    }


    @Test
    public void testBirthdaysOnSaturdays_5_may_1998() {
        List<Year> years = homework.birthdaysOnSaturdays(LocalDate.of(1998, 5, 5));
        Assert.assertEquals("2001", years.get(0).toString());
        Assert.assertEquals("2007", years.get(1).toString());
        Assert.assertEquals("2012", years.get(2).toString());
        Assert.assertEquals("2018", years.get(3).toString());
    }

    @Test
    public void testDaysNotWith24Hours_year1900() {
        List<MonthDay> monthDays2 = homework.daysNotWith24Hours(Year.of(1900));
        Assert.assertEquals(0, monthDays2.size());
    }

    @Test
    public void testDaysNotWith24Hours_year2006() {
        List<MonthDay> monthDays1 = homework.daysNotWith24Hours(Year.of(2006));
        Assert.assertTrue(monthDays1.contains(MonthDay.of(3,26)));
        Assert.assertTrue(monthDays1.contains(MonthDay.of(10,29)));
        Assert.assertEquals(2, monthDays1.size());

    }

    @Test
    public void testDaysNotWith24Hours_year2005() {
        List<MonthDay> monthDays = homework.daysNotWith24Hours(Year.of(2005));
        Assert.assertTrue(monthDays.contains(MonthDay.of(3,27)));
        Assert.assertTrue(monthDays.contains(MonthDay.of(10,30)));
        Assert.assertEquals(2, monthDays.size());
    }

    @Test
    public void zonesAlwaysClockShift() {
        ArrayList<ZoneId> zoneIds = new ArrayList<>();
        zoneIds.add(ZoneId.of("Europe/Kiev"));
        zoneIds.add(ZoneId.of("Europe/Moscow"));
        zoneIds.add(ZoneId.of("Japan"));
        zoneIds.add(ZoneId.of("Africa/Luanda"));
        List<ZoneId> alwaysShift = homework.zonesAlwaysClockShift(zoneIds);
        Assert.assertTrue(alwaysShift.isEmpty());
    }

    @Test
    public void testZonesNeverClockShift() {
        ArrayList<ZoneId> zoneIds = new ArrayList<>();
        zoneIds.add(ZoneId.of("Europe/Kiev"));
        zoneIds.add(ZoneId.of("Europe/Moscow"));
        zoneIds.add(ZoneId.of("Japan"));
        zoneIds.add(ZoneId.of("Africa/Luanda"));
        List<ZoneId> neverShift = homework.zonesNeverClockShift(zoneIds);
        Assert.assertTrue(neverShift.contains(ZoneId.of("Africa/Luanda")));
        Assert.assertEquals(1, neverShift.size());
    }

    @Test
    public void zonesChangedClockShiftRules() {
        ArrayList<ZoneId> zoneIds = new ArrayList<>();
        zoneIds.add(ZoneId.of("Europe/Kiev"));
        zoneIds.add(ZoneId.of("Europe/Moscow"));
        zoneIds.add(ZoneId.of("Japan"));
        zoneIds.add(ZoneId.of("Africa/Luanda"));
        List<ZoneId> sometimesShift = homework.zonesChangedClockShiftRules(zoneIds);
        Assert.assertTrue(sometimesShift.contains(ZoneId.of("Europe/Kiev")));
        Assert.assertTrue(sometimesShift.contains(ZoneId.of("Europe/Moscow")));
        Assert.assertTrue(sometimesShift.contains(ZoneId.of("Japan")));
        Assert.assertEquals(3, sometimesShift.size());
    }
}