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
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Objects;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;

/**
 *
 * @author geoagdt
 */
public class Generic_Date
        extends Generic_YearMonth
        implements Serializable {

    public LocalDate LD;

    public Generic_Date(Generic_Environment e) {
        super(e);
    }

    public Generic_Date(Generic_Date d) {
        this(d.e, d.LD);
    }

    public Generic_Date(Generic_Time t) {
        this(t.e, t.LDT.toLocalDate());
    }

    public Generic_Date(Generic_Environment e, LocalDate d) {
        super(e, YearMonth.from(d));
        LD = d;
    }

    public Generic_Date(Generic_Environment e, int year, int month, int day) {
        super(e, YearMonth.of(year, month));
        LD = LocalDate.of(year, month, day);
    }

    /**
     * Expects s to be of the form "YYYY-MM-DD"
     *
     * @param e
     * @param s
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
        LD = LocalDate.of(YM.getYear(), YM.getMonth(), new Integer(s2));
    }

    public void addDays(int days) {
        LD = LD.plusDays(days);
    }
    
    /**
     * Assume the year has 4 digits.
     *
     * @return
     */
    @Override
    public String getYYYY() {
        return Integer.toString(LD.getYear());
    }

    /**
     * The month always has 2 characters. 01 is January 02 is February ... 12 is
     * December
     *
     * @return
     */
    @Override
    public String getMM() {
        String result = "";
        int month = LD.getMonthValue();
        if (month < 10) {
            result = Strings.symbol_0;
        }
        result += Integer.toString(month);
        return result;
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
        String result;
        result = getYYYY();
        result += delimeter;
        result += getMM();
        return result;
    }
    
    /**
     * Returns true iff t is the same day as this.
     *
     * @param t
     * @return
     */
    public boolean isSameDay(Generic_Date t) {
        return LD.isEqual(t.LD);
    }

    public String getDD() {
        String result = "";
        int dayOfMonth = LD.getDayOfMonth();
        if (dayOfMonth < 10) {
            result += Strings.symbol_0;
        }
        result += Integer.toString(dayOfMonth);
        return result;
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
        String result;
        result = getYYYYMM(dateComponentDelimitter);
        result += dateComponentDelimitter;
        result += getDD();
        return result;
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

    @Override
    public int compareTo(Generic_YearMonth t) {
        Generic_Date d = (Generic_Date) t;
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
}
