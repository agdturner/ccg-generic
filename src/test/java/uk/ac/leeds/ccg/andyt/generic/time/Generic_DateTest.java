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
package uk.ac.leeds.ccg.andyt.generic.time;

import uk.ac.leeds.ccg.agdt.generic.time.Generic_Date;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Strings;
import uk.ac.leeds.ccg.agdt.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.agdt.generic.io.Generic_Files;

/**
 *
 * @author geoagdt
 */
public class Generic_DateTest {

    Generic_Environment env;
    int logID;

    public Generic_DateTest() {
        //super();
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        File dir = Generic_Files.getDefaultDir();
        try {
            env = new Generic_Environment(dir);
            logID = env.initLog(this.getClass().getSimpleName());
        } catch (IOException ex) {
            Logger.getLogger(Generic_DateTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addDays method, of class Generic_Date.
     */
    @Test
    public void testAddDays() {
        env.log("addDays", logID);
        //System.out.println("addDays");
        int year;
        int month;
        int dayOfMonth;
        int days;
        Generic_Date instance;
        Generic_Date t;
        boolean expResult;
        boolean result;
        // Test 1
        year = 2017;
        month = 11;
        dayOfMonth = 9;
        days = 1;
        instance = new Generic_Date(env, year, month, dayOfMonth);
        env.log(instance.toString());
        env.log("addDays(" + days + ")");
        instance.addDays(days);
        env.log(instance.toString());
        dayOfMonth = 10;
        t = new Generic_Date(env, year, month, dayOfMonth);
        expResult = true;
        result = instance.equals(t);
        Assertions.assertEquals(expResult, result);
        // Test 2
        year = 2017;
        month = 9;
        dayOfMonth = 30;
        days = 1;
        instance = new Generic_Date(env, year, month, dayOfMonth);
        env.log(instance.toString());
        env.log("addDays(" + days + ")");
        instance.addDays(days);
        env.log(instance.toString());
        year = 2017;
        month = 10;
        dayOfMonth = 1;
        t = new Generic_Date(env, year, month, dayOfMonth);
        expResult = true;
        result = instance.equals(t);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of isSameDay method, of class Generic_Date.
     */
    @Test
    public void testIsSameDay() {
        env.log("isSameDay", logID);
        //System.out.println("isSameDay");
        Generic_Date t;
        Generic_Date instance;
        boolean expResult = true;
        boolean result;
        t = new Generic_Date(env, 2017, 11, 9);
        instance = new Generic_Date(env, 2017, 11, 9);
        env.log(instance.toString());
        result = instance.isSameDay(t);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getDD method, of class Generic_Date.
     */
    @Test
    public void testGetDD() {
        env.log("getDD", logID);
        //System.out.println("getDD");
        Generic_Date t;
        String expResult;
        String result;
        // Test1
        t = new Generic_Date(env, 2017, 1, 9);
        result = t.getDD();
        env.log(t.toString());
        expResult = "09";
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getYYYYMMDD method, of class Generic_Date.
     */
    @Test
    public void testGetYYYYMMDD_0args() {
        env.log("getYYYYMMDD", logID);
        //System.out.println("getYYYYMMDD");
        Generic_Date instance = new Generic_Date(env, 2017, 1, 9);
        env.log(instance.toString());
        String expResult = "2017-01-09";
        String result = instance.getYYYYMMDD();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getYYYYMMDD method, of class Generic_Date.
     */
    @Test
    public void testGetYYYYMMDD_String() {
        env.log("getYYYYMMDD", logID);
        //System.out.println("getYYYYMMDD");
        String dateComponentDelimitter = "_";
        Generic_Date instance = new Generic_Date(env, 2017, 1, 9);
        env.log(instance.toString());
        String expResult = "2017_01_09";
        String result = instance.getYYYYMMDD(dateComponentDelimitter);
        Assertions.assertEquals(expResult, result);
    }

}
