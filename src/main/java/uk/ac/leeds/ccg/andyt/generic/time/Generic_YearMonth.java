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

import java.time.YearMonth;
import java.util.Objects;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Object;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 * Holds a reference to a YearMonth and provides methods to compare and process
 * year-months.
 */
public class Generic_YearMonth extends Generic_Object implements Comparable {

    public YearMonth YM;

    public Generic_YearMonth(Generic_Environment e) {
        super(e);
        YM = YearMonth.now();
    }

    public Generic_YearMonth(Generic_Environment e, Generic_YearMonth t) {
        this(e, t.YM);
    }

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
        String[] split;
        split = s.split("-");
        int year = Integer.valueOf(split[0]);
        String s2;
        s2 = split[1];
        if (s2.startsWith("0")) {
            s2 = s2.substring(1);
        }
        int month = Integer.valueOf(s2);
        YM = YearMonth.of(year, month);
    }

    /**
     * Returns true iff t is the same day as this.
     *
     * @param t Generic_YearMonth
     * @return boolean
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
        String result = "";
        int month = YM.getMonthValue();
        if (month < 10) {
            result = Generic_Strings.symbol_0;
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
    public int compareTo(Object o) {
        if (o instanceof Generic_YearMonth) {
            return compareTo((Generic_YearMonth) o);
        }
        return -1;
    }

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
