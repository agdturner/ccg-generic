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
package uk.ac.leeds.ccg.generic.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Strings;

/**
 * Holds a reference to a LocalDateTime and provides methods to compare and
 * process times. It should not be confused with the identically named
 * {@link uk.ac.leeds.ccg.generic.util.Generic_Time} class.
 *
 * @author Andy Turner
 * @version 1.1
 */
public class Generic_Time extends Generic_Date {

    private static final long serialVersionUID = 1L;

    /**
     * The LocalDateTime.
     */
    public LocalDateTime LDT;

    /**
     * Create a new instance.
     *
     * @param e The Generic_Environment.
     */
    public Generic_Time(Generic_Environment e) {
        super(e);
    }

    /**
     * Create a new instance.
     *
     * @param t The Generic_Time.
     */
    public Generic_Time(Generic_Time t) {
        this(t.env, t.LDT);
    }

    /**
     * Create a new instance.
     *
     * @param d The Generic_Date.
     * @throws Exception If encountered.
     */
    public Generic_Time(Generic_Date d) throws Exception {
        this(d.getEnv(), LocalDateTime.of(d.LD, LocalTime.of(0, 0)));
    }

    /**
     * Create a new instance.
     *
     * @param d The Generic_Date.
     * @param t The LocalTime.
     * @throws Exception If encountered.
     */
    public Generic_Time(Generic_Date d, LocalTime t) throws Exception {
        this(d.getEnv(), LocalDateTime.of(d.LD, t));
    }

    /**
     * Create a new instance.
     *
     * @param e The Generic_Environment.
     * @param dt The LocalDateTime.
     */
    public Generic_Time(Generic_Environment e, LocalDateTime dt) {
        super(e, LocalDate.from(dt));
        LDT = dt;
    }

    /**
     * Create a new instance.
     *
     * @param e The Generic_Environment.
     * @param year The year.
     * @param month The month.
     * @param day The day.
     * @param hour The hour.
     * @param minute The minute.
     * @param second The second.
     */
    public Generic_Time(Generic_Environment e, int year, int month, int day,
            int hour, int minute, int second) {
        super(e, LocalDate.of(year, month, day));
        LDT = LocalDateTime.of(LD, LocalTime.of(hour, minute, second));
    }

    /**
     * Expects s to be of the form "YYYY-MM-DD" or "YYYY-MM-DDTHH:MM:SSZ"
     *
     * @param e Generic_Environment
     * @param s String
     */
    public Generic_Time(Generic_Environment e, String s) {
        this(e, s, Generic_Strings.symbol_minus, Generic_Strings.s_T,
                Generic_Strings.symbol_colon);
    }

    /**
     * Expects s to be of the form "YYYY-MM-DD" or "YYYY-MM-DDTHH:MM:SSZ"
     *
     * @param e Generic_Environment
     * @param s String expected in the form "YYYY-MM-DD"
     * @param dateDelimeter String
     * @param timedateSeparator String
     * @param timeDelimeter String
     */
    public Generic_Time(Generic_Environment e, String s, String dateDelimeter,
            String timedateSeparator, String timeDelimeter) {
        super(e, s);
        String[] splitT = s.split(timedateSeparator);
        int hour;
        int minute;
        int second;
        if (splitT.length == 2) {
            String s_0 = Generic_Strings.symbol_0;
            String[] split = splitT[1].split(timeDelimeter);
            String s0 = split[0];
            if (s0.startsWith(s_0)) {
                s0 = s0.substring(1);
            }
            hour = Integer.valueOf(s0);
            s0 = split[1];
            if (s0.startsWith(s_0)) {
                s0 = s0.substring(1);
            }
            minute = Integer.valueOf(s0);
            s0 = split[2];
            s0 = s0.substring(0, s0.length() - 1);
            if (s0.startsWith(s_0)) {
                s0 = s0.substring(1);
            }
            second = 0;
            if (s0.length() > 0) {
                second = Integer.valueOf(s0);
            }
        } else {
            hour = 0;
            minute = 0;
            second = 0;
        }
        setTime(hour, minute, second);
    }

    /**
     * Adds minutes to {@link #LDT} and re-initialises {@link #LD}.
     *
     * @param minutes The minutes to add.
     */
    public void addMinutes(int minutes) {
        LDT = LDT.plusMinutes(minutes);
        LD = LDT.toLocalDate();
    }

    /**
     * Adds hours to {@link #LDT} and re-initialises {@link #LD}.
     *
     * @param hours The hours to add.
     */
    public void addHours(int hours) {
        LDT = LDT.plusHours(hours);
        LD = LDT.toLocalDate();
    }

    /**
     * Adds days to {@link #LDT} and re-initialises {@link #LD}.
     *
     * @param days The hours to add.
     */
    @Override
    public void addDays(int days) {
        super.addDays(days);
        LDT = LDT.plusDays(days);
    }

    /**
     * Sets the time of {@link #LDT}.
     *
     * @param hour The hour to set.
     * @param minute The minute to set.
     * @param second The second to set.
     */
    public final void setTime(int hour, int minute, int second) {
        LDT = LocalDateTime.of(LD, LocalTime.of(hour, minute, second));
    }

    /**
     * @return A new Generic_Date created from this.
     */
    public Generic_Date getDate() {
        return new Generic_Date(this);
    }

    /**
     * @return YYYY-MM-DDTHH:MM:SSZ
     */
    public String toFormattedString0() {
        return getYYYYMMDDHHMMSS(Generic_Strings.symbol_minus,
                Generic_Strings.s_T, Generic_Strings.symbol_colon,
                Generic_Strings.s_Z);
    }

    /**
     * @return YYYY-MM-DDTHH_MM_SSZ
     */
    public String toFormattedString1() {
        return getYYYYMMDDHHMMSS(Generic_Strings.symbol_minus,
                Generic_Strings.s_T, Generic_Strings.symbol_underscore,
                Generic_Strings.s_Z);
    }

    /**
     * @return YYYYMMDDHHMMSS
     */
    public String toFormattedString2() {
        return getYYYYMMDDHHMMSS(Generic_Strings.special_emptyString,
                Generic_Strings.special_emptyString,
                Generic_Strings.special_emptyString,
                Generic_Strings.special_emptyString);
    }

    /**
     * @return YYYYMMDDHHMM
     */
    public String getYYYYMMDDHHMM() {
        return getYYYY() + getMM() + getDD() + getHH() + getMins();
    }

    /**
     * @return 01 for day 1 etc...
     */
    @Override
    public String getDD() {
        String r = "";
        int dayOfMonth = LDT.getDayOfMonth();
        if (dayOfMonth < 10) {
            r += Generic_Strings.symbol_0;
        }
        r += Integer.toString(dayOfMonth);
        return r;
    }

    /**
     * @return 01 for hour 1 etc...
     */
    public String getHH() {
        String r = "";
        int hour = LDT.getHour();
        if (hour < 10) {
            r += Generic_Strings.symbol_0;
        }
        r += Integer.toString(hour);
        return r;
    }

    /**
     * So as not to confuse with Generic_YearMonth.getMM() this is called
     * getMins() instead of getMM();
     *
     * @return 01 for minute 1 etc...
     */
    public String getMins() {
        String r = "";
        int minute = LDT.getMinute();
        if (minute < 10) {
            r += Generic_Strings.symbol_0;
        }
        r += Integer.toString(minute);
        return r;
    }

    /**
     * @return 01 for second 1 etc...
     */
    public String getSS() {
        String r = "";
        int second = LDT.getSecond();
        if (second < 10) {
            r += Generic_Strings.symbol_0;
        }
        r += Integer.toString(second);
        return r;
    }

    /**
     * @return A String representation in YYYY-MM-DDTHH:MM:SS format.
     */
    @Override
    public String toString() {
        return getYYYYMMDDHHMMSS();
    }

    /**
     * @return A String representation in YYYY-MM-DDTHH:MM:SS format.
     */
    public String getYYYYMMDDHHMMSS() {
        String r;
        r = super.toString();
        r += Generic_Strings.s_T;
        r += getHH();
        r += Generic_Strings.symbol_colon;
        r += getMins();
        r += Generic_Strings.symbol_colon;
        r += getSS();
        return r;
    }

    /**
     * @param dateComponentDelimitter Delimitter for date components.
     * @param dateTimeDivider Delimitter to go between date and time components.
     * @param timeComponentDivider Delimitter for time components.
     * @param resultEnding A String added to the end of the result.
     * @return A String representation using dateComponentDelimitter to delimit
     * the date components, dateTimeDivider to divide the date and time
     * components, timeComponentDivider to divide the time components, and adds
     * resultEnding to the end of the resulting String.
     */
    public String getYYYYMMDDHHMMSS(
            String dateComponentDelimitter,
            String dateTimeDivider,
            String timeComponentDivider,
            String resultEnding) {
        String r;
        r = getYYYYMMDD(dateComponentDelimitter);
        r += dateTimeDivider;
        r += getHH();
        r += timeComponentDivider;
        r += getMins();
        r += timeComponentDivider;
        r += getSS();
        r += resultEnding;
        return r;
    }

    /**
     * Returns true iff t is the same day as this.
     *
     * @param t Generic_Date
     * @return boolean
     */
    @Override
    public boolean isSameDay(Generic_Date t) {
        return LDT.toLocalDate().isEqual(t.LD);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Generic_Time) {
            if (this == o) {
                return true;
            }
            Generic_Time t;
            t = (Generic_Time) o;
            if (hashCode() == t.hashCode()) {
                return LDT.equals(t.LDT);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(LDT);
        return hash;
    }

    /**
     * For comparing another Generic_Date with this.
     *
     * @param t The Generic_Time to compare.
     * @return -1, 0, 1 depending on if this is less than, the same as, or
     * greater than {code o}.
     */
    public int compareTo(Generic_Time t) {
        if (LDT.isAfter(t.LDT)) {
            return 1;
        } else {
            if (LDT.isBefore(t.LDT)) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Returns a new time difference between this and t in minutes. If t is
     * after this, then the answer is negative.
     *
     * @param t Generic_Time
     * @return long
     */
    public long differenceInMinutes(Generic_Time t) {
        long result;
        result = ChronoUnit.MINUTES.between(LDT, t.LDT);
        return result;
    }

    /**
     * Returns a new time difference between this and t in minutes. If t is
     * after this, then the answer is negative.
     *
     * @param t Generic_Time
     * @return long
     */
    public long differenceInHours(Generic_Time t) {
        long result;
        result = ChronoUnit.HOURS.between(LDT, t.LDT);
        return result;
    }
}
