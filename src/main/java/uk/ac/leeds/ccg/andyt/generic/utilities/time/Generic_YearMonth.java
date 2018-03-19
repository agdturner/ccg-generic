/*
 * Copyright (C) 2017 geoagdt.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package uk.ac.leeds.ccg.andyt.generic.utilities.time;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Objects;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Object;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class Generic_YearMonth
        extends Generic_Object
        implements Serializable, Comparable<Generic_YearMonth> {

    // For convenience
    protected transient Generic_Strings Strings;

    public YearMonth YM;

    public Generic_YearMonth(Generic_Environment e) {
        super(e);
        Strings = e.getStrings();
        YM = YearMonth.now();
    }

    public Generic_YearMonth(Generic_Environment e, Generic_YearMonth t) {
        this(e, t.YM);
    }

    public Generic_YearMonth(Generic_Environment e, YearMonth t) {
        super(e);
        Strings = e.getStrings();
        YM = t;
    }

    /**
     * Expects s to be of the form "YYYY-MM"
     *
     * @param e
     * @param s
     */
    public Generic_YearMonth(Generic_Environment e, String s) {
        super(e);
        Strings = e.getStrings();
        String[] split;
        split = s.split("-");
        int year = new Integer(split[0]);
        String s2;
        s2 = split[1];
        if (s2.startsWith("0")) {
            s2 = s2.substring(1);
        }
        int month = new Integer(s2);
        YM = YearMonth.of(year, month);
    }

    /**
     * Returns true iff t is the same day as this.
     *
     * @param t
     * @return
     */
    public boolean isSameDay(Generic_YearMonth t) {
        if (YM.getMonthValue() == t.YM.getMonthValue()) {
            if (YM.getYear() == t.YM.getYear()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Assume the year has 4 digits.
     *
     * @return
     */
    public String getYYYY() {
        return Integer.toString(YM.getYear());
    }

    /**
     * The month always has 2 characters. 01 is January 02 is February ... 12 is
     * December
     *
     * @return
     */
    public String getMM() {
        String result = "";
        int month = YM.getMonthValue();
        if (month < 10) {
            result = Strings.symbol_0;
        }
        result += Integer.toString(month);
        return result;
    }

    @Override
    public String toString() {
        return YM.toString();
    }

    /**
     *
     * @return String representing year and month in YYYY-MM format
     */
    public String getYYYYMM() {
        return YM.toString();
    }

    public String getYYYYMM(String delimeter) {
        String result;
        result = getYYYY();
        result += delimeter;
        result += getMM();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Generic_YearMonth) {
            if (this == o) {
                return true;
            }
            Generic_YearMonth d;
            d = (Generic_YearMonth) o;
            return YM.equals(d.YM);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(YM);
        return hash;
    }

    @Override
    public int compareTo(Generic_YearMonth t) {
        int Year = YM.getYear();
        int tYear = t.YM.getYear();
        if (Year > tYear) {
            return 1;
        } else {
            if (Year < tYear) {
                return -1;
            } else {
                int Month = YM.getMonthValue();
                int tMonth = t.YM.getMonthValue();
                if (Month > tMonth) {
                    return 1;
                } else {
                    if (Month < tMonth) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }
}
