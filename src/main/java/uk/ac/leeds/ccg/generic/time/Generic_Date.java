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
//import java.time.Month;
import java.time.YearMonth;
import java.util.Objects;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Strings;

/**
 * Holds a reference to a LocalDate and provides methods to compare and process
 * dates.
 */
public class Generic_Date extends Generic_YearMonth {

    private static final long serialVersionUID = 1L;

    public LocalDate LD;

    public Generic_Date(Generic_Environment e) {
        super(e);
    }

    public Generic_Date(Generic_Date d) {
        this(d.env, d.LD);
    }

    public Generic_Date(Generic_Time t) {
        this(t.env, t.LDT.toLocalDate());
    }

    public Generic_Date(Generic_Environment e, LocalDate d) {
        super(e, YearMonth.from(d));
        LD = LocalDate.from(d);
        //LD = LocalDate.of(d.getYear(), d.getMonthValue(), d.getDayOfMonth());
    }

    public Generic_Date(Generic_Environment e, int year, int month, int day) {
        super(e, YearMonth.of(year, month));
        LD = LocalDate.of(year, month, day);
    }

    /**
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

    public void addDays(int days) {
        LD = LD.plusDays(days);
    }

    public void minusDays(int days) {
        LD = LD.minusDays(days);
    }

    public void addMonths(int months) {
        LD = LD.plusMonths(months);
    }

    public void minusMonths(int months) {
        LD = LD.minusMonths(months);
    }

    public void addYears(int years) {
        LD = LD.plusYears(years);
    }

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

    public String getDD() {
        String r = "";
        int dayOfMonth = LD.getDayOfMonth();
        if (dayOfMonth < 10) {
            r += Generic_Strings.symbol_0;
        }
        r += Integer.toString(dayOfMonth);
        return r;
    }

    @Override
    public String toString() {
        return getYYYYMMDD();
    }

    /**
     * @return A String representation of this in the format YYYY-MM-DD.
     */
    public String getYYYYMMDD() {
        return getYYYYMMDD("-");
    }

    /**
     * @param dateComponentDelimitter String used to separateComponents of the
     * date.
     * @return A String representation of this in the format YYYY-MM-DD where
     * the - is replaced by dateComponentDelimitter.
     */
    public String getYYYYMMDD(String dateComponentDelimitter) {
        String r;
        r = getYYYYMM(dateComponentDelimitter);
        r += dateComponentDelimitter;
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

    public int compareTo(Generic_Date o) {
        if (this instanceof Generic_Time && o instanceof Generic_Time) {
            return ((Generic_Time) this).compareTo((Generic_Time) o);
        }

        if (LD.isAfter(o.LD)) {
            return 1;
        } else {
            if (LD.isBefore(o.LD)) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
