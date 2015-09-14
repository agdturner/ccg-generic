/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author geoagdt
 */
public class Generic_Time {
    public static final int SecondsInMinute = 60;
    public static final int MinutesInHour = 60;
    public static final int SecondsInHour = SecondsInMinute * MinutesInHour;
    public static final int MilliSecondsInSecond = 1000;
    public static final int MilliSecondsInHour = MilliSecondsInSecond * SecondsInHour;
    
    public static void printTime(long millis) {
        int MilliSecondsInDay = 24 * MilliSecondsInHour;
        int MilliSecondsInMinute = 60000; 
        long millis2 = millis;
        int days = 0;
        while (millis2 > MilliSecondsInDay) {
            days ++;
            millis2 -= MilliSecondsInDay;
        }
        int hours = 0;
        while (millis2 > MilliSecondsInHour) {
            hours ++;
            millis2 -= MilliSecondsInHour;
        }
        int minutes = 0;
        while (millis2 > MilliSecondsInMinute) {
            minutes ++;
            millis2 -= MilliSecondsInMinute;
        }
        int seconds = 0;
        while (millis2 > 1000) {
            seconds ++;
            millis2 -= MilliSecondsInSecond;            
        }
        System.out.println("" + days + " day(s) " +
                hours + " hour(s) " +
                minutes + " minute(s) " +
                seconds + " second(s) " +
                millis2 + " millisecond(s)");
    }

    /**
     * @return
     * {@code
    ArrayList<String> result;
    result = new ArrayList<String>();
    result.add("Jan");
    result.add("Feb");
    result.add("Mar");
    result.add("Apr");
    result.add("May");
    result.add("Jun");
    result.add("Jul");
    result.add("Aug");
    result.add("Sep");
    result.add("Oct");
    result.add("Nov");
    result.add("Dec");
    return result;
    }
     */
    public static ArrayList<String> getMonths3Letters() {
        ArrayList<String> result;
        result = new ArrayList<String>();
        result.add("Jan");
        result.add("Feb");
        result.add("Mar");
        result.add("Apr");
        result.add("May");
        result.add("Jun");
        result.add("Jul");
        result.add("Aug");
        result.add("Sep");
        result.add("Oct");
        result.add("Nov");
        result.add("Dec");
        return result;
    }
    
    public static int getMonthDiff(
            int year0,
            int year1,
            int month0,
            int month1) {
       int result;
       result = (year1 - year0) * 12;
       result += month1 - month0;
       return result;
    }

    /**
     * @return Current clock date as yyyy-MM-dd format
     */
    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());
        return formatted;
    }
}
