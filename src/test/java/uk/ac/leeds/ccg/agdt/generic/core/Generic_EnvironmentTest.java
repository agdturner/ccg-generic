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
package uk.ac.leeds.ccg.agdt.generic.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.agdt.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.agdt.generic.io.Generic_Files;

/**
 * Tests for {@link Generic_Environment} class.
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_EnvironmentTest {

    public Generic_EnvironmentTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

//    /**
//     * Test of initLog method, of class Generic_Environment.
//     */
//    @Test
//    public void testInitLog_String() throws Exception {
//        System.out.println("initLog");
//        String s = "";
//        Generic_Environment instance = null;
//        int expResult = 0;
//        int result = instance.initLog(s);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of initLog method, of class Generic_Environment.
//     */
//    @Test
//    public void testInitLog_String_String() throws Exception {
//        System.out.println("initLog");
//        String s = "";
//        String e = "";
//        Generic_Environment instance = null;
//        int expResult = 0;
//        int result = instance.initLog(s, e);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLogDir method, of class Generic_Environment.
//     */
//    @Test
//    public void testGetLogDir() throws Exception {
//        System.out.println("getLogDir");
//        String s = "";
//        Generic_Environment instance = null;
//        Path expResult = null;
//        Path result = instance.getLogDir(s);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of closeLogs method, of class Generic_Environment.
//     */
//    @Test
//    public void testCloseLogs() {
//        System.out.println("closeLogs");
//        Generic_Environment instance = null;
//        instance.closeLogs();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of closeLog method, of class Generic_Environment.
//     */
//    @Test
//    public void testCloseLog() {
//        System.out.println("closeLog");
//        int logID = 0;
//        Generic_Environment instance = null;
//        instance.closeLog(logID);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of log method, of class Generic_Environment.
//     */
//    @Test
//    public void testLog_String() {
//        System.out.println("log");
//        String s = "";
//        Generic_Environment instance = null;
//        instance.log(s);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of log method, of class Generic_Environment.
     */
    @Test
    public void testLog_String_boolean() {
        try {
            System.out.println("log");
            String s = "Hello World again";
            boolean println = false;
            Generic_Files files = new Generic_Files(new Generic_Defaults(
                    Paths.get(System.getProperty("user.home"),
                            Generic_Strings.s_data,
                            Generic_Strings.s_generic)));
            Generic_Environment instance = new Generic_Environment(files);
            instance.log(s, println);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

//    /**
//     * Test of log method, of class Generic_Environment.
//     */
//    @Test
//    public void testLog_String_int() {
//        System.out.println("log");
//        String s = "";
//        int logID = 0;
//        Generic_Environment instance = null;
//        instance.log(s, logID);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of log method, of class Generic_Environment.
//     */
//    @Test
//    public void testLog_3args_1() {
//        System.out.println("log");
//        String s = "";
//        int logID = 0;
//        boolean println = false;
//        Generic_Environment instance = null;
//        instance.log(s, logID, println);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of logStartTag method, of class Generic_Environment.
//     */
//    @Test
//    public void testLogStartTag_String() {
//        System.out.println("logStartTag");
//        String s = "";
//        Generic_Environment instance = null;
//        instance.logStartTag(s);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getStartTag method, of class Generic_Environment.
//     */
//    @Test
//    public void testGetStartTag() {
//        System.out.println("getStartTag");
//        String s = "";
//        Generic_Environment instance = null;
//        String expResult = "";
//        String result = instance.getStartTag(s);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of logStartTag method, of class Generic_Environment.
//     */
//    @Test
//    public void testLogStartTag_String_boolean() {
//        System.out.println("logStartTag");
//        String s = "";
//        boolean println = false;
//        Generic_Environment instance = null;
//        instance.logStartTag(s, println);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of logStartTag method, of class Generic_Environment.
//     */
//    @Test
//    public void testLogStartTag_String_int() {
//        System.out.println("logStartTag");
//        String s = "";
//        int logID = 0;
//        Generic_Environment instance = null;
//        instance.logStartTag(s, logID);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of logEndTag method, of class Generic_Environment.
//     */
//    @Test
//    public void testLogEndTag_String() {
//        System.out.println("logEndTag");
//        String s = "";
//        Generic_Environment instance = null;
//        instance.logEndTag(s);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEndTag method, of class Generic_Environment.
//     */
//    @Test
//    public void testGetEndTag() {
//        System.out.println("getEndTag");
//        String s = "";
//        Generic_Environment instance = null;
//        String expResult = "";
//        String result = instance.getEndTag(s);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of logEndTag method, of class Generic_Environment.
//     */
//    @Test
//    public void testLogEndTag_String_boolean() {
//        System.out.println("logEndTag");
//        String s = "";
//        boolean println = false;
//        Generic_Environment instance = null;
//        instance.logEndTag(s, println);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of logEndTag method, of class Generic_Environment.
//     */
//    @Test
//    public void testLogEndTag_String_int() {
//        System.out.println("logEndTag");
//        String s = "";
//        int logID = 0;
//        Generic_Environment instance = null;
//        instance.logEndTag(s, logID);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of log method, of class Generic_Environment.
//     */
//    @Test
//    public void testLog_Collection() {
//        System.out.println("log");
//        Collection<String> lines = null;
//        Generic_Environment instance = null;
//        instance.log(lines);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of log method, of class Generic_Environment.
//     */
//    @Test
//    public void testLog_3args_2() {
//        System.out.println("log");
//        Collection<String> lines = null;
//        int logID = 0;
//        boolean println = false;
//        Generic_Environment instance = null;
//        instance.log(lines, logID, println);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
