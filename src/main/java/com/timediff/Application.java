package com.timediff;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.timediff.DateTimeUtils.validateInputString;

public class Application {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        List<Range<LocalTime>> primaryList = new ArrayList<>();
        List<Range<LocalTime>> secondaryList = new ArrayList<>();

        int input = 0;
        System.out.println("Welcome to time subtraction server.");
        System.out.println("Choose from the following options");
        while (input != 4) {
            System.out.println("1. Enter elements of first time range. Current Size: "+primaryList.size());
            System.out.println("2. Enter elements of second time range. Current Size: "+secondaryList.size());
            System.out.println("3. Compute");
            System.out.println("4. Exit");
            input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1:
                    System.out.println("Enter elements of first time range in the format- HH:MM - HH:MM e.g. 09:00-11:00");
                    LocalTime[] firstRange = validateInputString(scanner.nextLine());
                    if (firstRange!=null){
                    primaryList.add(Range.closed(firstRange[0], firstRange[1]));
                    }else{
                        System.out.println("Wrong input. Please try again");
                    }
                    break;


                case 2:
                    System.out.println("Enter elements of second time range in the format- HH:MM - HH:MM e.g. 09:00-11:00");
                    LocalTime[] secondRange = validateInputString(scanner.nextLine());
                    if (secondRange!=null){
                        secondaryList.add(Range.closed(secondRange[0], secondRange[1]));
                    }else{
                        System.out.println("Wrong input. Please try again");
                    }

                    break;

                case 3:
                    if ((primaryList.size()!=0)&&(secondaryList.size()!=0)) {
                        System.out.println("Computing subtraction of two ranges");
                        RangeSet<LocalTime> result = DateTimeUtils.subtractRanges(primaryList, secondaryList);
                        result.asRanges().forEach(x -> System.out.println("(" + x.lowerEndpoint() + "-" + x.upperEndpoint() + ")"));
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
}


