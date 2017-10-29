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

    String String_data;
    String String_generated;
    String String_input;
    String String_output;

    public Generic_Strings() {
    }

    public String getString_data() {
        if (String_data == null) {
            String_data = "data";
        }
        return String_data;
    }

    public String getString_generated() {
        if (String_generated == null) {
            String_generated = "generated";
        }
        return String_generated;
    }

    public String getString_input() {
        if (String_input == null) {
            String_input = "input";
        }
        return String_input;
    }

    public String getString_output() {
        if (String_output == null) {
            String_output = "output";
        }
        return String_output;
    }
}
