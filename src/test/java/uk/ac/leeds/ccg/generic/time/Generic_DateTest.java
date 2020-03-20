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

import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Strings;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_Files;
import uk.ac.leeds.ccg.generic.io.Generic_FilesTest;

/**
 * Tests for {@link Generic_Date} class.
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_DateTest {

    Generic_Environment env;

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
     * Test of addDays method, of class Generic_Date.
     */
    @Test
    public void testAddDays() {
        env.log("addDays");
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
        env.log("isSameDay");
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
        env.log("getDD");
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
        env.log("getYYYYMMDD");
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
        env.log("getYYYYMMDD");
        //System.out.println("getYYYYMMDD");
        String dateComponentDelimitter = "_";
        Generic_Date instance = new Generic_Date(env, 2017, 1, 9);
        env.log(instance.toString());
        String expResult = "2017_01_09";
        String result = instance.getYYYYMMDD(dateComponentDelimitter);
        Assertions.assertEquals(expResult, result);
    }

}
