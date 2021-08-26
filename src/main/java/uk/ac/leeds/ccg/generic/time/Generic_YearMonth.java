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

import java.time.YearMonth;
import java.util.Objects;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Object;
import uk.ac.leeds.ccg.generic.core.Generic_Strings;

/**
 * Holds a reference to a {@link java.time.YearMonth} and provides methods to
 * compare and process year-months.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class Generic_YearMonth extends Generic_Object 
        implements Comparable<Generic_YearMonth> {

    private static final long serialVersionUID = 1L;

    /**
     * The YearMonth.
     */
    public YearMonth YM;

    /**
     * Create a new instance.
     * @param e The Generic_Environment.
     */
    public Generic_YearMonth(Generic_Environment e) {
        super(e);
        YM = YearMonth.now();
    }

    /**
     * Create a new instance.
     * @param e The Generic_Environment.
     * @param t The Generic_YearMonth to create from.
     */
    public Generic_YearMonth(Generic_Environment e, Generic_YearMonth t) {
        this(e, t.YM);
    }

    /**
     * Create a new instance.
     * @param e The Generic_Environment.
     * @param t The YearMonth to create from.
     */
    public Generic_YearMonth(Generic_Environment e, YearMonth t) {
        super(e);
        YM = t;
    }

    /**
     * Expects s to be of the form "YYYY-MM"
     *
     * @param e Generic_Environment
     * @param s String
     */
    public Generic_YearMonth(Generic_Environment e, String s) {
        super(e);
        String[] split = s.split("-");
        int year = Integer.valueOf(split[0]);
        String s2 = split[1];
        if (s2.startsWith("0")) {
            s2 = s2.substring(1);
        }
        int month = Integer.valueOf(s2);
        YM = YearMonth.of(year, month);
    }

    /**
     * Assumes the year has 4 digits!
     *
     * @return String
     */
    public String getYYYY() {
        return Integer.toString(YM.getYear());
    }

    /**
     * The month always has 2 characters. 01 is January 02 is February ... 12 is
     * December
     *
     * @return String
     */
    public String getMM() {
        String r = "";
        int month = YM.getMonthValue();
        if (month < 10) {
            r = Generic_Strings.symbol_0;
        }
        r += Integer.toString(month);
        return r;
    }

    /**
     * @return A String representation of this.
     */
    @Override
    public String toString() {
        return YM.toString();
    }

    /**
     * @return String representing year and month in YYYY-MM format
     */
    public String getYYYYMM() {
        return YM.toString();
    }

    /**
     * @param delimeter The delimeter.
     * @return String representing year and month in YYYY-MM format using the 
     * delimeter given.
     */
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

    /**
     * For comparisons.
     *
     * @param ym An instance to compare with. If {@code null} then a
     * {@link NullPointerException} is thrown.
     * @return -1, 0, 1 depending on if this is less than, the same as, or
     * greater than {@code o}.
     */
    @Override
    public int compareTo(Generic_YearMonth ym) {
        // This appears ugly, but it is a sensible way given the way Java is 
        // ensuring backwards compatability following the introduction of 
        // generics.
        if (ym instanceof Generic_Date && this instanceof Generic_Date) {
            Generic_Date ymd = (Generic_Date) ym;
            Generic_Date td = (Generic_Date) this;
            return td.compareTo(ymd);
        } else {
            int y = YM.getYear();
            int ty = ym.YM.getYear();
            if (y > ty) {
                return 1;
            } else {
                if (y < ty) {
                    return -1;
                } else {
                    int m = YM.getMonthValue();
                    int tm = ym.YM.getMonthValue();
                    if (m > tm) {
                        return 1;
                    } else {
                        if (m < tm) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        }
    }
}
