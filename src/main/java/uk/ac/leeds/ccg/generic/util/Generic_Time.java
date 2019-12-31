/*
 * Copyright 2019 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.leeds.ccg.generic.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This predates java.time. It was developed for dynamic models that ticked
 * through time. It has methods to help process dates and times and to aggregate
 * data by these. It should not be confused with the identically named
 * {@link uk.ac.leeds.ccg.generic.time.Generic_Time} class.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_Time {

    public static final int SecondsInMinute = 60;
    public static final int MinutesInHour = 60;
    public static final int SecondsInHour = SecondsInMinute * MinutesInHour;
    public static final int MilliSecondsInSecond = 1000;
    public static final int MilliSecondsInHour = MilliSecondsInSecond * SecondsInHour;

    /**
     * The day represented by this time.
     */
    private int day;

    /**
     * The month represented by this time.
     */
    private int month;
    
    /**
     * The year represented by this time.
     */
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

    /**
     * Converts millis to a String detailing the number of days, hours, minutes,
     * seconds and milliseconds in millis.
     *
     * @param millis The number of millisecnds to be reported in a String form
     * detailing the number of days, hours, minutes, seconds and milliseconds.
     * @return A String form of millis detailing the number of days, hours,
     * minutes, seconds and milliseconds for millis.
     */
    public static String getTime(long millis) {

        int MilliSecondsInDay = 24 * MilliSecondsInHour;
        int MilliSecondsInMinute = 60000;
        long millis2 = millis;
        int days = 0;
        while (millis2 >= MilliSecondsInDay) {
            days++;
            millis2 -= MilliSecondsInDay;
        }
        int hours = 0;
        while (millis2 >= MilliSecondsInHour) {
            hours++;
            millis2 -= MilliSecondsInHour;
        }
        int minutes = 0;
        while (millis2 >= MilliSecondsInMinute) {
            minutes++;
            millis2 -= MilliSecondsInMinute;
        }
        int seconds = 0;
        while (millis2 >= 1000) {
            seconds++;
            millis2 -= MilliSecondsInSecond;
        }
        return "" + days + " day(s) " + hours + " hour(s) "
                + minutes + " minute(s) " + seconds + " second(s) "
                + millis2 + " millisecond(s)";
    }

    /**
     * @return a list starting "Jan" and ending "Dec".
     */
    public static ArrayList<String> getMonths3Letters() {
        ArrayList<String> r;
        r = new ArrayList<>();
        r.add("Jan");
        r.add("Feb");
        r.add("Mar");
        r.add("Apr");
        r.add("May");
        r.add("Jun");
        r.add("Jul");
        r.add("Aug");
        r.add("Sep");
        r.add("Oct");
        r.add("Nov");
        r.add("Dec");
        return r;
    }

    /**
     * In the case where month3Letters is from {@link #getMonths3Letters()} and
     * month is "January" or "Jan" or "Janu" (etc.) then this will return 1.
     *
     * @param month A month e.g. "January" for which the index of a three letter
     * version + 1 is returned.
     * @param month3Letters For example see {@link #getMonths3Letters()}.
     * @return An int represent the month Jan = 1, Dec = 12.
     */
    public static int getMonth(String month, ArrayList<String> month3Letters) {
        return month3Letters.indexOf(month.substring(0, 3)) + 1;
    }

    /**
     * @param monthNumber "01" to "12" representing January to December.
     * @return "Jan" for monthNumber "01" ... "Dec" for monthNumber "12".
     */
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

    /**
     * @param monthNumber expected values 1 to 12 inclusive.
     * @return "Jan" for monthNumber = 1, ..., "Dec" for monthNumber = 12. For
     * any other value {@code null} is returned.
     */
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

    /**
     * @param month3Letters Expecting "Jan", to "Dec".
     * @return For month3Letters "Jan" return "01", ..., month3Letters "Dec"
     * return "12". For non recognised month3Letters return {@code null}.
     */
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

    /**
     * @param month3Letters Expecting "Jan", ..., "Dec".
     * @return 1 if month3Letters is "Jan", ..., 12 if month3Letters is "Dec"
     * and -1 otherwise.
     */
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

    /**
     * @param year0 The earlier times year.
     * @param year1 The latter times year.
     * @param month0 The earlier times month.
     * @param month1 The latter times month.
     * @return The difference in months calculated as year1, month1 subtract
     * year0, month0.
     */
    public static int getMonthDiff(int year0, int year1, int month0, int month1) {
        int r = (year1 - year0) * 12;
        r += month1 - month0;
        return r;
    }

    /**
     * @return Current clock date in yyyy-MM-dd format.
     */
    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    /**
     * @return Current clock date in yyyy-MM-dd HH:mm:ss format.
     */
    public static String getDateAndTime() {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    /**
     * @return Current clock date in yyyy-MM-dd-HH format.
     */
    public static String getDateAndTimeHourDir() {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd-HH");
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    /**
     * @return A copy of {@link #day}.
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets {@link #day} to day.
     *
     * @param day The value to set {@link #day} to.
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return A copy of {@link #month}.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets {@link #month} to month.
     *
     * @param month The value to set {@link #month} to.
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return A copy of {@link #year}.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets {@link #year} to year.
     *
     * @param year The value to set {@link #year} to.
     */
    public void setYear(int year) {
        this.year = year;
    }

}
