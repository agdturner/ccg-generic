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
import java.time.Month;
import java.time.YearMonth;
import java.util.Objects;
import java.util.TreeSet;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Strings;

/**
 * Holds a reference to a LocalDate and provides methods to compare and process
 * dates.
 */
public class Generic_Date extends Generic_YearMonth {

    private static final long serialVersionUID = 1L;

    /**
     * The LocalData to store.
     */
    public LocalDate LD;

    /**
     * Create a new instance.
     *
     * @param e The Generic_Environment.
     */
    public Generic_Date(Generic_Environment e) {
        super(e);
    }

    /**
     * Create a new instance.
     *
     * @param d The Generic_Date.
     */
    public Generic_Date(Generic_Date d) {
        this(d.env, d.LD);
    }

    /**
     * Create a new instance.
     *
     * @param t The Generic_Time.
     */
    public Generic_Date(Generic_Time t) {
        this(t.env, t.LDT.toLocalDate());
    }

    /**
     * Create a new instance.
     *
     * @param e The Generic_Environment.
     * @param d The LocalDate.
     */
    public Generic_Date(Generic_Environment e, LocalDate d) {
        super(e, YearMonth.from(d));
        LD = LocalDate.from(d);
        //LD = LocalDate.of(d.getYear(), d.getMonthValue(), d.getDayOfMonth());
    }

    /**
     * Create a new instance.
     *
     * @param e The Generic_Environment.
     * @param year The year.
     * @param month The month.
     * @param day The day.
     */
    public Generic_Date(Generic_Environment e, int year, int month, int day) {
        super(e, YearMonth.of(year, month));
        LD = LocalDate.of(year, month, day);
    }

    /**
     * Create a new instance.
     *
     * @param e Generic_Environment
     * @param s String expected in the form "YYYY-MM-DD"
     */
    public Generic_Date(Generic_Environment e, String s) {
        super(e, s);
        String[] split;
        split = s.split("-");
        String s2;
        s2 = split[2];
        s2 = s2.substring(0, 2);
        if (s2.startsWith("0")) {
            s2 = s2.substring(1);
        }
        LD = LocalDate.of(YM.getYear(), YM.getMonth(), Integer.valueOf(s2));
    }

    /**
     * Adds days to {@link #LD}.
     *
     * @param days The number of days to add.
     */
    public void addDays(int days) {
        LD = LD.plusDays(days);
    }

    /**
     * Subtracts days to {@link #LD}.
     *
     * @param days The number of days to subtract.
     */
    public void minusDays(int days) {
        LD = LD.minusDays(days);
    }

    /**
     * Adds months to {@link #LD}.
     *
     * @param months The number of months to add.
     */
    public void addMonths(int months) {
        LD = LD.plusMonths(months);
    }

    /**
     * Subtracts months to {@link #LD}.
     *
     * @param months The number of months to subtract.
     */
    public void minusMonths(int months) {
        LD = LD.minusMonths(months);
    }

    /**
     * Adds years to {@link #LD}.
     *
     * @param years The number of years to add.
     */
    public void addYears(int years) {
        LD = LD.plusYears(years);
    }

    /**
     * Subtracts years to {@link #LD}.
     *
     * @param years The number of years to subtract.
     */
    public void minusYears(int years) {
        LD = LD.minusYears(years);
    }

    /**
     * Assume the year has 4 digits.
     *
     * @return String
     */
    @Override
    public String getYYYY() {
        return Integer.toString(LD.getYear());
    }

    /**
     * The month always has 2 characters. 01 is January 02 is February ... 12 is
     * December
     *
     * @return String MM.
     */
    @Override
    public String getMM() {
        String r = "";
        int month = LD.getMonthValue();
        if (month < 10) {
            r = Generic_Strings.symbol_0;
        }
        r += Integer.toString(month);
        return r;
    }

    /**
     *
     * @return String representing year and month in YYYY-MM format
     */
    @Override
    public String getYYYYMM() {
        return getYYYY() + "-" + getMM();
    }

    @Override
    public String getYYYYMM(String delimeter) {
        String r;
        r = getYYYY();
        r += delimeter;
        r += getMM();
        return r;
    }

    /**
     * Returns true iff t is the same day as this.
     *
     * @param t Generic_Date
     * @return LD.isEqual(t.LD)
     */
    public boolean isSameDay(Generic_Date t) {
        return LD.isEqual(t.LD);
    }

    /**
     * @return The days in the date 01 for January etc...
     */
    public String getDD() {
        String r = "";
        int dayOfMonth = LD.getDayOfMonth();
        if (dayOfMonth < 10) {
            r += Generic_Strings.symbol_0;
        }
        r += Integer.toString(dayOfMonth);
        return r;
    }

    /**
     * @return A String representation of this in YYYY-MM-DD format.
     */
    @Override
    public String toString() {
        return getYYYYMMDD();
    }

    /**
     * @return A String representation of this in YYYY-MM-DD format.
     */
    public String getYYYYMMDD() {
        return getYYYYMMDD("-");
    }

    /**
     * @param delimeter String used to separateComponents of the date.
     * @return A String representation of this in the format YYYY-MM-DD where
     * the - is replaced by dateComponentDelimitter.
     */
    public String getYYYYMMDD(String delimeter) {
        String r;
        r = getYYYYMM(delimeter);
        r += delimeter;
        r += getDD();
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Generic_Date) {
            if (this == o) {
                return true;
            }
            Generic_Date d;
            d = (Generic_Date) o;
            if (hashCode() == d.hashCode()) {
                return this.isSameDay(d);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(LD);
        return hash;
    }

    /**
     * For comparing another Generic_Date with this.
     *
     * @param d The Generic_Date to compare.
     * @return -1, 0, 1 depending on if this is less than, the same as, or
     * greater than {code o}.
     */
    public int compareTo(Generic_Date d) {
        // This appears ugly, but it is a sensible way given the way Java is 
        // ensuring backwards compatability following the introduction of 
        // generics.
        if (this instanceof Generic_Time && d instanceof Generic_Time) {
            return ((Generic_Time) this).compareTo((Generic_Time) d);
        }
        if (LD.isAfter(d.LD)) {
            return 1;
        } else {
            if (LD.isBefore(d.LD)) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    
    /**
     * @return The months of the year.
     */
    public static TreeSet<Month> getMonths() {
        TreeSet<Month> r = new TreeSet<>();
        r.add(Month.JANUARY);
        r.add(Month.FEBRUARY);
        r.add(Month.MARCH);
        r.add(Month.APRIL);
        r.add(Month.MAY);
        r.add(Month.JUNE);
        r.add(Month.JULY);
        r.add(Month.AUGUST);
        r.add(Month.SEPTEMBER);
        r.add(Month.OCTOBER);
        r.add(Month.NOVEMBER);
        r.add(Month.DECEMBER);
        return r;
    }
}
