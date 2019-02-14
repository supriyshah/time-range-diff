package com.timediff;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class DateTimeUtilsTest {

    @Test
    public void basicSuccessTest(){
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        Range<LocalTime> range1 = Range.closed(LocalTime.parse("09:00"), LocalTime.parse("10:00"));
        primaryList.add(range1);

        List<Range<LocalTime>> secondaryList = new ArrayList<>();
        Range<LocalTime> range2 = Range.closed(LocalTime.parse("09:00"), LocalTime.parse("09:30"));
        secondaryList.add(range2);

        Range<LocalTime> range3 = Range.openClosed(LocalTime.parse("09:30"), LocalTime.parse("10:00"));
        RangeSet<LocalTime> expected = TreeRangeSet.create();
        expected.add(range3);

        RangeSet<LocalTime> result = DateTimeUtils.subtractRanges(primaryList, secondaryList);
        assertEquals(result, expected);
    }

    @Test
    public void nullSuccessTest(){
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        Range<LocalTime> range1 = Range.closed(LocalTime.parse("09:00"), LocalTime.parse("10:00"));
        primaryList.add(range1);

        List<Range<LocalTime>> secondaryList = new ArrayList<>();
        Range<LocalTime> range2 = Range.closed(LocalTime.parse("09:00"), LocalTime.parse("10:30"));
        secondaryList.add(range2);

        RangeSet<LocalTime> result = DateTimeUtils.subtractRanges(primaryList, secondaryList);
        assertEquals(result.asRanges().size(),0);
    }

    @Test
    public void noOverlapSuccessTest(){
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        Range<LocalTime> range1 = Range.closed(LocalTime.parse("09:00"), LocalTime.parse("09:30"));
        primaryList.add(range1);

        List<Range<LocalTime>> secondaryList = new ArrayList<>();
        Range<LocalTime> range2 = Range.closed(LocalTime.parse("09:30"), LocalTime.parse("15:00"));
        secondaryList.add(range2);

        Range<LocalTime> range3 = Range.closedOpen(LocalTime.parse("09:00"), LocalTime.parse("09:30"));
        RangeSet<LocalTime> expected = TreeRangeSet.create();
        expected.add(range3);

        RangeSet<LocalTime> result = DateTimeUtils.subtractRanges(primaryList, secondaryList);
        assertEquals(result, expected);
    }


    @Test
    public void doubleMatchSuccessTest(){
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        Range<LocalTime> range1 = Range.closed(LocalTime.parse("09:00"), LocalTime.parse("09:30"));
        Range<LocalTime> range2 = Range.closed(LocalTime.parse("10:00"), LocalTime.parse("10:30"));
        primaryList.add(range1);
        primaryList.add(range2);

        List<Range<LocalTime>> secondaryList = new ArrayList<>();
        Range<LocalTime> range3 = Range.closed(LocalTime.parse("09:15"), LocalTime.parse("10:15"));
        secondaryList.add(range3);

        Range<LocalTime> range4 = Range.closedOpen(LocalTime.parse("09:00"), LocalTime.parse("09:15"));
        Range<LocalTime> range5 = Range.openClosed(LocalTime.parse("10:15"), LocalTime.parse("10:30"));
        RangeSet<LocalTime> expected = TreeRangeSet.create();
        expected.add(range4);
        expected.add(range5);

        RangeSet<LocalTime> result = DateTimeUtils.subtractRanges(primaryList, secondaryList);
        assertEquals(result, expected);
    }

    @Test
    public void toughSuccessTest(){
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        Range<LocalTime> range1 = Range.closed(LocalTime.parse("09:00"), LocalTime.parse("11:00"));
        Range<LocalTime> range2 = Range.closed(LocalTime.parse("13:00"), LocalTime.parse("15:00"));
        primaryList.add(range1);
        primaryList.add(range2);

        List<Range<LocalTime>> secondaryList = new ArrayList<>();
        Range<LocalTime> range3 = Range.closed(LocalTime.parse("09:00"), LocalTime.parse("09:15"));
        Range<LocalTime> range4 = Range.closed(LocalTime.parse("10:00"), LocalTime.parse("10:15"));
        Range<LocalTime> range5 = Range.closed(LocalTime.parse("12:30"), LocalTime.parse("16:00"));
        secondaryList.add(range3);
        secondaryList.add(range4);
        secondaryList.add(range5);

        Range<LocalTime> range6 = Range.open(LocalTime.parse("09:15"), LocalTime.parse("10:00"));
        Range<LocalTime> range7 = Range.openClosed(LocalTime.parse("10:15"), LocalTime.parse("11:00"));
        RangeSet<LocalTime> expected = TreeRangeSet.create();
        expected.add(range6);
        expected.add(range7);
        RangeSet<LocalTime> result = DateTimeUtils.subtractRanges(primaryList, secondaryList);
        assertEquals(result, expected);
    }


    @Test
    public void overlappingSuccessTest(){
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        Range<LocalTime> range1 = Range.closed(LocalTime.parse("09:00"), LocalTime.parse("11:00"));
        primaryList.add(range1);


        List<Range<LocalTime>> secondaryList = new ArrayList<>();
        Range<LocalTime> range3 = Range.closed(LocalTime.parse("08:15"), LocalTime.parse("09:30"));
        Range<LocalTime> range4 = Range.closed(LocalTime.parse("09:15"), LocalTime.parse("10:00"));
        secondaryList.add(range3);
        secondaryList.add(range4);

        Range<LocalTime> range6 = Range.openClosed(LocalTime.parse("10:00"), LocalTime.parse("11:00"));
        RangeSet<LocalTime> expected = TreeRangeSet.create();
        expected.add(range6);

        RangeSet<LocalTime> result = DateTimeUtils.subtractRanges(primaryList, secondaryList);
        assertEquals(result, expected);
    }

    @Test
    public void hmmInputTest(){

        String primary = "9:00-10:00";
        String secondary = "9:00-09:30";

        List<Range<LocalTime>> primaryList = new ArrayList<>();
        LocalTime[] primaryRange = DateTimeUtils.validateInputString(primary);
        Range<LocalTime> range1 = Range.closed(primaryRange[0], primaryRange[1]);
        primaryList.add(range1);

        List<Range<LocalTime>> secondaryList = new ArrayList<>();
        LocalTime[] secondaryRange = DateTimeUtils.validateInputString(secondary);
        Range<LocalTime> range2 = Range.closed(secondaryRange[0], secondaryRange[1]);
        secondaryList.add(range2);

        Range<LocalTime> range3 = Range.openClosed(LocalTime.parse("09:30"), LocalTime.parse("10:00"));
        RangeSet<LocalTime> expected = TreeRangeSet.create();
        expected.add(range3);

        RangeSet<LocalTime> result = DateTimeUtils.subtractRanges(primaryList, secondaryList);
        assertEquals(result, expected);
    }

    @Test
    public void invalidInput1Test(){
        String primary = "900-1000";
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        LocalTime[] primaryRange = DateTimeUtils.validateInputString(primary);
        assertNull(primaryRange);
    }

    @Test
    public void invalidInput2Test(){
        String primary = "900:1000";
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        LocalTime[] primaryRange = DateTimeUtils.validateInputString(primary);
        assertNull(primaryRange);
    }

    @Test
    public void validInputFormat(){
        String primary = "(09:00-10:00)";
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        LocalTime[] primaryRange = DateTimeUtils.validateInputString(primary);
        assertNotNull(primaryRange);
    }

    @Test
    public void randomTimeValueTest(){

        String primary = "11:17 - 08:59";
        String secondary = "0:11 -  9:39";

        List<Range<LocalTime>> primaryList = new ArrayList<>();
        LocalTime[] primaryRange = DateTimeUtils.validateInputString(primary);
        Range<LocalTime> range1 = Range.closed(primaryRange[0], primaryRange[1]);
        primaryList.add(range1);

        List<Range<LocalTime>> secondaryList = new ArrayList<>();
        LocalTime[] secondaryRange = DateTimeUtils.validateInputString(secondary);
        Range<LocalTime> range2 = Range.closed(secondaryRange[0], secondaryRange[1]);
        secondaryList.add(range2);

        Range<LocalTime> range3 = Range.openClosed(LocalTime.parse("09:39"), LocalTime.parse("11:17"));
        RangeSet<LocalTime> expected = TreeRangeSet.create();
        expected.add(range3);

        RangeSet<LocalTime> result = DateTimeUtils.subtractRanges(primaryList, secondaryList);
        assertEquals(result, expected);
    }

}
