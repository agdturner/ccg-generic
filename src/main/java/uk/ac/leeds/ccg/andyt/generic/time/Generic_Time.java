/*
 * Copyright (C) 2017 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.time;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;

/**
 * Not to be confused with
 * uk.ac.leeds.ccg.andyt.generic.util.Generic_Time
 *
 * @author Andy Turner
 */
public class Generic_Time extends Generic_Date implements Serializable, Comparable {

    public LocalDateTime LDT;

    public Generic_Time(Generic_Environment e) {
        super(e);
    }

    public Generic_Time(Generic_Time t) {
        this(t.e, t.LDT);
    }

    public Generic_Time(Generic_Date d) {
        this(d.e, LocalDateTime.of(d.LD, LocalTime.of(0, 0)));
    }

    public Generic_Time(Generic_Date d, LocalTime t) {
        this(d.e, LocalDateTime.of(d.LD, t));
    }

    public Generic_Time(Generic_Environment e, LocalDateTime dt) {
        super(e, LocalDate.from(dt));
        LDT = dt;
    }

    public Generic_Time(Generic_Environment e, int year, int month, int day,
            int hour, int minute, int second) {
        super(e, LocalDate.of(year, month, day));
        LDT = LocalDateTime.of(LD, LocalTime.of(hour, minute, second));
    }

    /**
     * Expects s to be of the form "YYYY-MM-DD" or "YYYY-MM-DDTHH:MM:SSZ"
     *
     * @param e
     * @param s
     */
    public Generic_Time(Generic_Environment e, String s) {
        this(e, s, e.getStrings().symbol_minus, e.getStrings().s_T,
                e.getStrings().symbol_colon);
    }

    /**
     * Expects s to be of the form "YYYY-MM-DD" or "YYYY-MM-DDTHH:MM:SSZ"
     *
     * @param e
     * @param s
     * @param dateDelimeter
     * @param timedateSeparator
     * @param timeDelimeter
     */
    public Generic_Time(Generic_Environment e, String s, String dateDelimeter,
            String timedateSeparator, String timeDelimeter) {
        super(e, s);
        String[] splitT;
        splitT = s.split(timedateSeparator);
        //super(split[0]);
        String[] split;
        String s_0 = Strings.symbol_0;
        String s2;
        int hour;
        int minute;
        int second;
        if (splitT.length == 2) {
            split = splitT[1].split(timeDelimeter);
            s2 = split[0];
            if (s2.startsWith(s_0)) {
                s2 = s2.substring(1);
            }
            hour = new Integer(s2);
            s2 = split[1];
            if (s2.startsWith(s_0)) {
                s2 = s2.substring(1);
            }
            minute = new Integer(s2);
            s2 = split[2];
            s2 = s2.substring(0, s2.length() - 1);
            if (s2.startsWith(s_0)) {
                s2 = s2.substring(1);
            }
            second = 0;
            if (s2.length() > 0) {
                second = new Integer(s2);
            }
        } else {
            hour = 0;
            minute = 0;
            second = 0;
        }
        setTime(hour, minute, second);
    }

    public void addMinutes(int minutes) {
        LDT = LDT.plusMinutes(minutes);
        LD = LDT.toLocalDate();
    }

    public void addHours(int hours) {
        LDT = LDT.plusHours(hours);
        LD = LDT.toLocalDate();
    }

    @Override
    public void addDays(int days) {
        super.addDays(days);
        LDT = LDT.plusDays(days);
    }

    public final void setTime(int hour, int minute, int second) {
        LDT = LocalDateTime.of(LD, LocalTime.of(hour, minute, second));
    }

    public Generic_Date getDate() {
        Generic_Date result;
        result = new Generic_Date(this);
        return result;
    }

    /**
     *
     * @return YYYY-MM-DDTHH:MM:SSZ
     */
    public String toFormattedString0() {
        return getYYYYMMDDHHMMSS(
                Strings.symbol_minus,
                Strings.s_T,
                Strings.symbol_colon,
                Strings.s_Z);
    }

    /**
     *
     * @return YYYY-MM-DDTHH_MM_SSZ
     */
    public String toFormattedString1() {
        return getYYYYMMDDHHMMSS(
                Strings.symbol_minus,
                Strings.s_T,
                Strings.symbol_underscore,
                Strings.s_Z);
    }

    public String toFormattedString2() {
        return getYYYYMMDDHHMMSS(
                Strings.emptyString,
                Strings.emptyString,
                Strings.emptyString,
                Strings.emptyString);
    }

    public String getYYYYMMDDHHMM() {
        String result;
        result = getYYYY() + getMM() + getDD() + getHH() + getMins();
        return result;
    }

    @Override
    public String getDD() {
        String result = "";
        int dayOfMonth = LDT.getDayOfMonth();
        if (dayOfMonth < 10) {
            result += Strings.symbol_0;
        }
        result += Integer.toString(dayOfMonth);
        return result;
    }

    public String getHH() {
        String result = "";
        int hour = LDT.getHour();
        if (hour < 10) {
            result += Strings.symbol_0;
        }
        result += Integer.toString(hour);
        return result;
    }

    /**
     * So as not to confuse with Generic_YearMonth.getMM() this is called
     * getMins() instead of getMM();
     *
     * @return
     */
    public String getMins() {
        String result = "";
        int minute = LDT.getMinute();
        if (minute < 10) {
            result += Strings.symbol_0;
        }
        result += Integer.toString(minute);
        return result;
    }

    public String getSS() {
        String result = "";
        int second = LDT.getSecond();
        if (second < 10) {
            result += Strings.symbol_0;
        }
        result += Integer.toString(second);
        return result;
    }

    @Override
    public String toString() {
        return getYYYYMMDDHHMMSS();
    }

    public String getYYYYMMDDHHMMSS() {
        String result;
        result = super.toString();
        result += Strings.s_T;
        result += getHH();
        result += Strings.symbol_colon;
        result += getMins();
        result += Strings.symbol_colon;
        result += getSS();
        return result;
    }

    public String getYYYYMMDDHHMMSS(
            String dateComponentDelimitter,
            String dateTimeDivider,
            String timeComponentDivider,
            String resultEnding) {
        String result;
        result = getYYYYMMDD(dateComponentDelimitter);
        result += dateTimeDivider;
        result += getHH();
        result += timeComponentDivider;
        result += getMins();
        result += timeComponentDivider;
        result += getSS();
        result += resultEnding;
        return result;
    }

    /**
     * Returns true iff t is the same day as this.
     *
     * @param t
     * @return
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

    @Override
    public int compareTo(Object o) {
        if (o instanceof Generic_Time) {
            return compareTo((Generic_Time) o);
        }
        return super.compareTo(o);
    }

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
     * @param t
     * @return 
     */
    public long differenceInMinutes(Generic_Time t) {
        long result;
        result = ChronoUnit.MINUTES.between(LDT, t.LDT);
        return result;
    }
    
    /**
     * Returns a new time difference between this and t in minutes. If t is 
     * after this, then the answer is negative. 
     * @param t
     * @return 
     */
    public long differenceInHours(Generic_Time t) {
        long result;
        result = ChronoUnit.HOURS.between(LDT, t.LDT);
        
        if (result < 0) {
            int debug = 1;
        }
        
        return result;
    }
}
