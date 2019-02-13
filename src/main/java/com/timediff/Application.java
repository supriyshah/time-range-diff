package com.timediff;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        List<Range<LocalTime>> secondaryList = new ArrayList<>();

        int input = 0;

        while (input != 4) {
            System.out.println("Welcome to time subtraction server.");
            System.out.println("Choose from the following options");
            System.out.println("1. Enter elements of first time range");
            System.out.println("2. Enter elements of second time range");
            System.out.println("3. Compute and Exit");
            System.out.println("4. Exit");
            input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1:
                    System.out.println("Enter elements of first time range in the format- HH:MM - HH:MM e.g. 09:00-11:00");
                    String str1 = validateInput(scanner.nextLine());
                    if (!str1.equals(Boolean.FALSE.toString())){
                        String[] strArray = str1.trim().split("-");
                        primaryList.add(Range.open(LocalTime.parse(strArray[0]), LocalTime.parse(strArray[1])));
                    }else{
                        System.out.println("Wrong input. Please try again");
                    }
                    break;


                case 2:
                    System.out.println("Enter elements of second time range in the format- HH:MM - HH:MM e.g. 09:00-11:00");
                    String str2 = validateInput(scanner.nextLine());
                    if (!str2.equals(Boolean.FALSE.toString())){
                        String[] strArray = str2.trim().split("-");
                        secondaryList.add(Range.open(LocalTime.parse(strArray[0]), LocalTime.parse(strArray[1])));
                    }else{
                        System.out.println("Wrong input. Please try again");
                    }

                    break;

                case 3:
                    if ((primaryList.size()!=0)&&(secondaryList.size()!=0)) {
                        System.out.println("Computing subtraction of two ranges");
                        RangeSet<LocalTime> result = subtractRanges(primaryList, secondaryList);
                        result.asRanges().stream().forEach(x -> System.out.println("(" + x.lowerEndpoint() + "-" + x.upperEndpoint() + ")"));
                    }else{
                        System.out.println("Cannot compute. One or the other list is empty");
                    }
                    System.out.println("Would you like to continue (Y/N)?");
                    if (scanner.nextLine().toUpperCase().equals("N")){
                        System.out.println("Exiting. Thank You");
                        input = 4;
                    }
                    break;

                case 4:
                    System.out.println("Exiting. Thank You");
                    break;
                default:
                    System.out.println ("Wrong input");
                    break;
            }
        }
    }

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

    private static String validateInput(String str) throws DateTimeParseException {

//        str = str.trim().replace(" ",""); //Remove empty spaces within the string
//        String[] strArray = str.split("-");
//
//        String[] startTime = strArray[0].split(":");
//        String[] endTime = strArray[1].split(":");
//
//        if (startTime[0])
        return str;
  //1     return Boolean.TRUE.toString();
    }


}


