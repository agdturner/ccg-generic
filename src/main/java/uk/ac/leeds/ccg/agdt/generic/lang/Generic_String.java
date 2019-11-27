/*
 * Copyright 2010 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.agdt.generic.lang;

import java.util.Set;

/**
 * Some useful methods for transforming Strings.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_String {

    /**
     * Adds the numbers 0 to 9 inclusive to s.
     *
     * @param s
     */
    public static void addNumerals(Set<String> s) {
        for (int i = 0; i < 10; i++) {
            s.add(Integer.toString(i));
        }
    }

    /**
     * @param s A String a copy of which is returned with all white-space
     * removed.
     * @return A copy of s which is returned with all white-space removed.
     */
    public static String getNoWhitespace(String s) {
        return s.trim().replaceAll("\\s+", "");
    }

    /**
     * @param s A String a copy of which an upper case version is returned with
     * all white-space removed.
     * @return A copy of s which is returned in upper case and with all
     * white-space removed.
     */
    public static String getNoWhitespaceUpperCase(String s) {
        return getNoWhitespace(s).toUpperCase();
    }

    /**
     * For copying a String and capitalising the first letter.
     *
     * @param s An instance of a String that will be copied and returned with
     * the first letter capitalised.
     * @return {@code s} but with the first letter capitalised.
     */
    public static String getCapitalFirstLetter(String s) {
        // Special cases
        if (s.isEmpty()) {
            return "";
        }
        String firstletter = s.substring(0, 1);
        String firstletterCapital = firstletter.toUpperCase();
        // Special cases
        if (s.length() == 1) {
            return firstletterCapital;
        }
        String remainder = s.substring(1, s.length());
        return firstletterCapital + remainder;
    }

    /**
     *
     * Returns a count of the number of times s0 appears in s. If s is "sss" and
     * s0 is "ss" then count is 1. If s is "ssss" and s0 is "ss" then count is
     * 2.
     *
     * @param s The string for which instances of s0 are counted.
     * @param s0 The string for which the number of occurrences in s is
     * returned.
     * @return a count of the number of times {@code s0} appears in {@code s}.
     */
    public static int getCount(String s, String s0) {
        if (s.contains(s0)) {
            if (s.contentEquals(s0)) {
                return 1;
            }
            return s.length() - s.replace(s0, "").length();
        }
        return 0;
    }
}
