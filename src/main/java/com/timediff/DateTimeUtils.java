package com.timediff;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DateTimeUtils {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm");

    public static RangeSet<LocalTime> subtractRanges(List<Range<LocalTime>> firstRangeList, List<Range<LocalTime>> secondRangeList){

        RangeSet<LocalTime> result = TreeRangeSet.create();
        Iterator<Range<LocalTime>> firstIterator = firstRangeList.iterator();

        while (firstIterator.hasNext()){
            result.add(firstIterator.next());
            Iterator<Range<LocalTime>> secondIterator = secondRangeList.iterator();
            while (secondIterator.hasNext()){
                result.remove(secondIterator.next());
            }
        }
        return result;
    }

    /*
    Function to validate if input string can be converted to local time
     */
    public static LocalTime[] validateInputString (String input){

        input = input.replace("(","");
        input = input.replace(")","");
        input = input.replace(" ","");
        try {
            List<String> inputList = Arrays.asList(input.trim().split("-"));

            if (inputList.size() != 2){
                throw new IllegalArgumentException();
            }
            LocalTime[] localTimes = inputList.stream().map(x -> LocalTime.parse(x, TIME_FORMATTER))
                    .toArray(LocalTime[]::new);

            if (localTimes[0].isAfter(localTimes[1])){
                LocalTime temp = localTimes[1];
                localTimes[1] = localTimes[0];
                localTimes[0] = temp;
            }
            return localTimes;
        }catch (Exception e){
            return null;
        }
    }

}
