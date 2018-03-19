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
package uk.ac.leeds.ccg.andyt.generic.core;

/**
 *
 * @author geoagdt
 */
public class Generic_Strings {

    protected String S_data;
    protected String S_generated;
    protected String S_input;
    protected String S_output;
    // Symbols
    public final String symbol_0 = "0";
    public final String symbol_1 = "1";
    public final String symbol_2 = "2";
    public final String symbol_3 = "3";
    public final String symbol_4 = "4";
    public final String symbol_5 = "5";
    public final String symbol_6 = "6";
    public final String symbol_7 = "7";
    public final String symbol_8 = "8";
    public final String symbol_9 = "9";
    public final String symbol_ampersand = "&";
    public final String symbol_backslash = "/";
    public final String symbol_colon = ":";
    public final String symbol_dot = ".";
    public final String symbol_equals = "=";
    public final String symbol_minus = "-";
    public final String symbol_questionmark = "?";
    public final String symbol_underscore = "_";
    // Empty String
    public final String emptyString = "";
    // For alphabet upper and lower case
    public final String s_T = "T";
    public final String s_Z = "Z";

    public Generic_Strings() {
    }

    public String getS_data() {
        if (S_data == null) {
            S_data = "data";
        }
        return S_data;
    }

    public String getS_generated() {
        if (S_generated == null) {
            S_generated = "generated";
        }
        return S_generated;
    }

    public String getS_input() {
        if (S_input == null) {
            S_input = "input";
        }
        return S_input;
    }

    public String getS_output() {
        if (S_output == null) {
            S_output = "output";
        }
        return S_output;
    }
}
