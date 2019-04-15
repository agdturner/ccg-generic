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

import java.io.File;
import java.util.logging.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Object;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_Files;

/**
 *
 * @author geoagdt
 */
public class Generic_DateTest extends Generic_Object {

    public Generic_DateTest() {
        //super();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    Generic_Environment e;

    @Before
    public void setUp() {
        // Init e
        String dataDir = System.getProperty("user.dir");
        System.out.println("user.dir " + dataDir);
        File testDir = new File(dataDir, "data");
        testDir = new File(testDir, "test");
        if (!testDir.exists()) {
            testDir.mkdirs();
        }
        Generic_Files f = new Generic_Files(testDir);
        //e = new Generic_Environment(testDir.getPath());
        e = new Generic_Environment(f, Level.ALL, 100);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addDays method, of class Generic_Date.
     */
    @Test
    public void testAddDays() {
        System.out.println("addDays");
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
        instance = new Generic_Date(e, year, month, dayOfMonth);
        instance.addDays(days);
        dayOfMonth = 10;
        t = new Generic_Date(e, year, month, dayOfMonth);
        expResult = true;
        result = instance.equals(t);
        assertEquals(expResult, result);
        // Test 2
        year = 2017;
        month = 9;
        dayOfMonth = 30;
        days = 1;
        instance = new Generic_Date(e, year, month, dayOfMonth);
        instance.addDays(days);
        year = 2017;
        month = 10;
        dayOfMonth = 1;
        t = new Generic_Date(e, year, month, dayOfMonth);
        expResult = true;
        result = instance.equals(t);
        assertEquals(expResult, result);
    }

    /**
     * Test of isSameDay method, of class Generic_Date.
     */
    @Test
    public void testIsSameDay() {
        System.out.println("isSameDay");
        Generic_Date t;
        Generic_Date instance;
        boolean expResult = true;
        boolean result;
        t = new Generic_Date(e, 2017, 11, 9);
        instance = new Generic_Date(e, 2017, 11, 9);
        result = instance.isSameDay(t);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDD method, of class Generic_Date.
     */
    @Test
    public void testGetDD() {
        System.out.println("getDD");
        Generic_Date t;
        String expResult;
        String result;
        // Test1
        t = new Generic_Date(e, 2017, 1, 9);
        result = t.getDD();
        expResult = "09";
        assertEquals(expResult, result);
    }

    /**
     * Test of getYYYYMMDD method, of class Generic_Date.
     */
    @Test
    public void testGetYYYYMMDD_0args() {
        System.out.println("getYYYYMMDD");
        Generic_Date instance = new Generic_Date(e, 2017, 1, 9);
        String expResult = "2017-01-09";
        String result = instance.getYYYYMMDD();
        assertEquals(expResult, result);
    }

    /**
     * Test of getYYYYMMDD method, of class Generic_Date.
     */
    @Test
    public void testGetYYYYMMDD_String() {
        System.out.println("getYYYYMMDD");
        String dateComponentDelimitter = "_";
        Generic_Date instance = new Generic_Date(e, 2017, 1, 9);
        String expResult = "2017_01_09";
        String result = instance.getYYYYMMDD(dateComponentDelimitter);
        assertEquals(expResult, result);
    }

}
