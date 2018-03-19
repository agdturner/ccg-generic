/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
//

/**
 * Not to be confused with uk.ac.leeds.ccg.andyt.generic.utilities.time.Generic_Time
 * @author Andy Turner
 */
public class Generic_Time {

    public static final int SecondsInMinute = 60;
    public static final int MinutesInHour = 60;
    public static final int SecondsInHour = SecondsInMinute * MinutesInHour;
    public static final int MilliSecondsInSecond = 1000;
    public static final int MilliSecondsInHour = MilliSecondsInSecond * SecondsInHour;

    private int day;
    private int month;
    private int year;

    public Generic_Time() {
    }

    public Generic_Time(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static int getAgeInYears(Generic_Time t1, Generic_Time t0) {
        if (t1.month > t0.month) {
            return t1.year - t0.year;
        } else {
            if (t1.month == t0.month) {
                if (t1.day >= t0.day) {
                    return t1.year - t0.year;
                } else {
                    return t1.year - t0.year - 1;
                }
            } else {
                return t1.year - t0.year - 1;
            }
        }
    }

    public static void printTime(long millis) {
        String time;
        time = getTime(millis);
        System.out.println(time);
    }

    public static String getTime(long millis) {

        int MilliSecondsInDay = 24 * MilliSecondsInHour;
        int MilliSecondsInMinute = 60000;
        long millis2 = millis;
        int days = 0;
        while (millis2 > MilliSecondsInDay) {
            days++;
            millis2 -= MilliSecondsInDay;
        }
        int hours = 0;
        while (millis2 > MilliSecondsInHour) {
            hours++;
            millis2 -= MilliSecondsInHour;
        }
        int minutes = 0;
        while (millis2 > MilliSecondsInMinute) {
            minutes++;
            millis2 -= MilliSecondsInMinute;
        }
        int seconds = 0;
        while (millis2 > 1000) {
            seconds++;
            millis2 -= MilliSecondsInSecond;
        }
        String result;
        result = "" + days + " day(s) "
                + hours + " hour(s) "
                + minutes + " minute(s) "
                + seconds + " second(s) "
                + millis2 + " millisecond(s)";
        return result;
    }

    /**
     * @return * {@code
     * ArrayList<String> result;
     * result = new ArrayList<String>();
     * result.add("Jan");
     * result.add("Feb");
     * result.add("Mar");
     * result.add("Apr");
     * result.add("May");
     * result.add("Jun");
     * result.add("Jul");
     * result.add("Aug");
     * result.add("Sep");
     * result.add("Oct");
     * result.add("Nov");
     * result.add("Dec");
     * return result;
     * }
     */
    public static ArrayList<String> getMonths3Letters() {
        ArrayList<String> result;
        result = new ArrayList<>();
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

    public static int getMonth(
            String month,
            ArrayList<String> month3Letters) {
        return month3Letters.indexOf(month.substring(0, 3)) + 1;
    }

    public static String getMonth3Letters(
            String monthNumber) {
        if (monthNumber.equalsIgnoreCase("01")) {
            return "Jan";
        }
        if (monthNumber.equalsIgnoreCase("02")) {
            return "Feb";
        }
        if (monthNumber.equalsIgnoreCase("03")) {
            return "Mar";
        }
        if (monthNumber.equalsIgnoreCase("04")) {
            return "Apr";
        }
        if (monthNumber.equalsIgnoreCase("05")) {
            return "May";
        }
        if (monthNumber.equalsIgnoreCase("06")) {
            return "Jun";
        }
        if (monthNumber.equalsIgnoreCase("07")) {
            return "Jul";
        }
        if (monthNumber.equalsIgnoreCase("08")) {
            return "Aug";
        }
        if (monthNumber.equalsIgnoreCase("09")) {
            return "Sep";
        }
        if (monthNumber.equalsIgnoreCase("10")) {
            return "Oct";
        }
        if (monthNumber.equalsIgnoreCase("11")) {
            return "Nov";
        }
        if (monthNumber.equalsIgnoreCase("12")) {
            return "Dec";
        }
        return null;
    }

    public static String getMonth3Letters(
            int monthNumber) {
        if (monthNumber == 1) {
            return "Jan";
        }
        if (monthNumber == 2) {
            return "Feb";
        }
        if (monthNumber == 3) {
            return "Mar";
        }
        if (monthNumber == 4) {
            return "Apr";
        }
        if (monthNumber == 5) {
            return "May";
        }
        if (monthNumber == 6) {
            return "Jun";
        }
        if (monthNumber == 7) {
            return "Jul";
        }
        if (monthNumber == 8) {
            return "Aug";
        }
        if (monthNumber == 9) {
            return "Sep";
        }
        if (monthNumber == 10) {
            return "Oct";
        }
        if (monthNumber == 11) {
            return "Nov";
        }
        if (monthNumber == 12) {
            return "Dec";
        }
        return null;
    }

    public static String getMonthNumber(String month3Letters) {
        if (month3Letters.equalsIgnoreCase("Jan")) {
            return "01";
        }
        if (month3Letters.equalsIgnoreCase("Feb")) {
            return "02";
        }
        if (month3Letters.equalsIgnoreCase("Mar")) {
            return "03";
        }
        if (month3Letters.equalsIgnoreCase("Apr")) {
            return "04";
        }
        if (month3Letters.equalsIgnoreCase("May")) {
            return "05";
        }
        if (month3Letters.equalsIgnoreCase("Jun")) {
            return "06";
        }
        if (month3Letters.equalsIgnoreCase("Jul")) {
            return "07";
        }
        if (month3Letters.equalsIgnoreCase("Aug")) {
            return "08";
        }
        if (month3Letters.equalsIgnoreCase("Sep")) {
            return "09";
        }
        if (month3Letters.equalsIgnoreCase("Oct")) {
            return "10";
        }
        if (month3Letters.equalsIgnoreCase("Nov")) {
            return "11";
        }
        if (month3Letters.equalsIgnoreCase("Dec")) {
            return "12";
        }
        return null;
    }

    public static int getMonthInt(String month3Letters) {
        if (month3Letters.equalsIgnoreCase("Jan")) {
            return 1;
        }
        if (month3Letters.equalsIgnoreCase("Feb")) {
            return 2;
        }
        if (month3Letters.equalsIgnoreCase("Mar")) {
            return 3;
        }
        if (month3Letters.equalsIgnoreCase("Apr")) {
            return 4;
        }
        if (month3Letters.equalsIgnoreCase("May")) {
            return 5;
        }
        if (month3Letters.equalsIgnoreCase("Jun")) {
            return 6;
        }
        if (month3Letters.equalsIgnoreCase("Jul")) {
            return 7;
        }
        if (month3Letters.equalsIgnoreCase("Aug")) {
            return 8;
        }
        if (month3Letters.equalsIgnoreCase("Sep")) {
            return 9;
        }
        if (month3Letters.equalsIgnoreCase("Oct")) {
            return 10;
        }
        if (month3Letters.equalsIgnoreCase("Nov")) {
            return 11;
        }
        if (month3Letters.equalsIgnoreCase("Dec")) {
            return 12;
        }
        return -1;
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
        //cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    /**
     * @return Current clock date as yyyy-MM-dd HH:mm:ss format
     */
    public static String getDateAndTime() {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    /**
     * @return Current clock date as yyyy-MM-dd-HH format
     */
    public static String getDateAndTimeHourDir() {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd-HH");
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
