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
