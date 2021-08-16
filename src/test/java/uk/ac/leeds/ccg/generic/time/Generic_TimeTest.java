/*
 * Copyright 2021 Centre for Computational Geography, University of Leeds.
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

import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Strings;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_Files;
import uk.ac.leeds.ccg.generic.io.Generic_FilesTest;

/**
 *
 * @author Andy Turner
 */
public class Generic_TimeTest {
    
    Generic_Environment env;

    public Generic_TimeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        try {
            env = new Generic_Environment(new Generic_Files(
                    new Generic_Defaults(Paths.get(
                            System.getProperty("user.home"),
                            Generic_Strings.s_data,
                            Generic_Strings.s_generic))));
        } catch (Exception ex) {
            Logger.getLogger(Generic_FilesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addMinutes method, of class Generic_Time.
     */
    @Test
    public void testAddMinutes() {
        System.out.println("addMinutes");
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int second;
        Generic_Time instance;
        Generic_Time t;
        // Test 1
        year = 2017;
        month = 11;
        day = 9;
        hour = 1;
        minute = 1;
        second = 1;
        int minutes = 60;
        instance = new Generic_Time(env, year, month, day, hour, minute, second);
        instance.addMinutes(minutes);
        Generic_Time expResult = new Generic_Time(env, year, month, day,
                hour + 1, minute, second);
        Assertions.assertTrue(expResult.compareTo(instance) == 0);
    }

    /**
     * Test of addHours method, of class Generic_Time.
     */
    @Test
    public void testAddHours() {
        System.out.println("addHours");
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int second;
        Generic_Time instance;
        Generic_Time t;
        // Test 1
        year = 2017;
        month = 11;
        day = 9;
        hour = 1;
        minute = 1;
        second = 1;
        int hours = 24;
        instance = new Generic_Time(env, year, month, day, hour, minute, second);
        instance.addHours(hours);
        Generic_Time expResult = new Generic_Time(env, year, month, day + 1, 
                hour, minute, second);
        Assertions.assertTrue(expResult.compareTo(instance) == 0);
    }

    /**
     * Test of addDays method, of class Generic_Time.
     */
    @Test
    public void testAddDays() {
        System.out.println("addDays");
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int second;
        Generic_Time instance;
        Generic_Time t;
        // Test 1
        year = 2017;
        month = 11;
        day = 9;
        hour = 1;
        minute = 1;
        second = 1;
        int days = 2;
        instance = new Generic_Time(env, year, month, day, hour, minute, second);
        instance.addDays(days);
        Generic_Time expResult = new Generic_Time(env, year, month, day + days, 
                hour, minute, second);
        Assertions.assertTrue(expResult.compareTo(instance) == 0);
    }

    /**
     * Test of setTime method, of class Generic_Time.
     */
    @Test
    public void testSetTime() {
        // No test.
//        System.out.println("setTime");
//        int hour = 0;
//        int minute = 0;
//        int second = 0;
//        Generic_Time instance = null;
//        instance.setTime(hour, minute, second);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getDate method, of class Generic_Time.
     */
    @Test
    public void testGetDate() {
        // No test.
//        System.out.println("getDate");
//        Generic_Time instance = null;
//        Generic_Date expResult = null;
//        Generic_Date result = instance.getDate();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of toFormattedString0 method, of class Generic_Time.
     */
    @Test
    public void testToFormattedString0() {
        // No test.
//        System.out.println("toFormattedString0");
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.toFormattedString0();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of toFormattedString1 method, of class Generic_Time.
     */
    @Test
    public void testToFormattedString1() {
        // No test.
//        System.out.println("toFormattedString1");
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.toFormattedString1();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of toFormattedString2 method, of class Generic_Time.
     */
    @Test
    public void testToFormattedString2() {
        // No test.
//        System.out.println("toFormattedString2");
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.toFormattedString2();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getYYYYMMDDHHMM method, of class Generic_Time.
     */
    @Test
    public void testGetYYYYMMDDHHMM() {
        // No test.
//        System.out.println("getYYYYMMDDHHMM");
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.getYYYYMMDDHHMM();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getDD method, of class Generic_Time.
     */
    @Test
    public void testGetDD() {
        // No test.
//        System.out.println("getDD");
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.getDD();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getHH method, of class Generic_Time.
     */
    @Test
    public void testGetHH() {
        // No test.
//        System.out.println("getHH");
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.getHH();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getMins method, of class Generic_Time.
     */
    @Test
    public void testGetMins() {
        // No test.
//        System.out.println("getMins");
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.getMins();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getSS method, of class Generic_Time.
     */
    @Test
    public void testGetSS() {
        // No test.
//        System.out.println("getSS");
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.getSS();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Generic_Time.
     */
    @Test
    public void testToString() {
        // No test.
//        System.out.println("toString");
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getYYYYMMDDHHMMSS method, of class Generic_Time.
     */
    @Test
    public void testGetYYYYMMDDHHMMSS_0args() {
        // No test.
//        System.out.println("getYYYYMMDDHHMMSS");
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.getYYYYMMDDHHMMSS();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getYYYYMMDDHHMMSS method, of class Generic_Time.
     */
    @Test
    public void testGetYYYYMMDDHHMMSS_4args() {
        // No test.
//        System.out.println("getYYYYMMDDHHMMSS");
//        String dateComponentDelimitter = "";
//        String dateTimeDivider = "";
//        String timeComponentDivider = "";
//        String resultEnding = "";
//        Generic_Time instance = null;
//        String expResult = "";
//        String result = instance.getYYYYMMDDHHMMSS(dateComponentDelimitter, dateTimeDivider, timeComponentDivider, resultEnding);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of isSameDay method, of class Generic_Time.
     */
    @Test
    public void testIsSameDay() {
        System.out.println("isSameDay");
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int second;
        Generic_Time instance;
        Generic_Time t;
        // Test 1
        year = 2017;
        month = 11;
        day = 9;
        hour = 1;
        minute = 1;
        second = 1;
        instance = new Generic_Time(env, year, month, day, hour, minute, second);
        Generic_Time c = new Generic_Time(env, year, month, day + 1, hour, minute, second);
        boolean result = instance.isSameDay(c);
        assertFalse(result);
    }

    /**
     * Test of equals method, of class Generic_Time.
     */
    @Test
    public void testEquals() {
        // No test.
//        System.out.println("equals");
//        Object o = null;
//        Generic_Time instance = null;
//        boolean expResult = false;
//        boolean result = instance.equals(o);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Generic_Time.
     */
    @Test
    public void testHashCode() {
        // No test.
//        System.out.println("hashCode");
//        Generic_Time instance = null;
//        int expResult = 0;
//        int result = instance.hashCode();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class Generic_Time.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int second;
        Generic_Time instance;
        Generic_Time t;
        // Test 1
        year = 2017;
        month = 11;
        day = 9;
        hour = 1;
        minute = 1;
        second = 1;
        instance = new Generic_Time(env, year, month, day, hour, minute, second);
        Generic_Time c = new Generic_Time(env, year, month, day + 1, hour, minute, second);
        int result = instance.compareTo(c);
        int expResult = -1;
        assertEquals(result, expResult);
    }

    /**
     * Test of differenceInMinutes method, of class Generic_Time.
     */
    @Test
    public void testDifferenceInMinutes() {
        // No test. But please implement a test.
//        System.out.println("differenceInMinutes");
//        Generic_Time t = null;
//        Generic_Time instance = null;
//        long expResult = 0L;
//        long result = instance.differenceInMinutes(t);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of differenceInHours method, of class Generic_Time.
     */
    @Test
    public void testDifferenceInHours() {
        // No test. But please implement a test.
//        System.out.println("differenceInHours");
//        Generic_Time t = null;
//        Generic_Time instance = null;
//        long expResult = 0L;
//        long result = instance.differenceInHours(t);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
