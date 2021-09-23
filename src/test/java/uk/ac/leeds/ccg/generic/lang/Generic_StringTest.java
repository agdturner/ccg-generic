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
package uk.ac.leeds.ccg.generic.lang;

import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested;

/**
 * Tests for {@link Generic_String} class.
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_StringTest {

    public Generic_StringTest() {
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

    /**
     * Test of getNoWhitespace method, of class Generic_String.
     */
    @Test
    public void testGetNoWhitespace() {
        System.out.println("getNoWhitespace");
        String s = " String \t contains various whitespace. \n";
        String expResult = "Stringcontainsvariouswhitespace.";
        String result = Generic_String.getNoWhitespace(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of getNoWhitespaceUpperCase method, of class Generic_String.
     */
    @Test
    public void testGetNoWhitespaceUpperCase() {
        System.out.println("getNoWhitespaceUpperCase");
        String s = " String \t contains various whitespace. \n";
        String expResult = "STRINGCONTAINSVARIOUSWHITESPACE.";
        String result = Generic_String.getNoWhitespaceUpperCase(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCapitalFirstLetter method, of class Generic_String.
     */
    @Test
    public void testGetCapitalFirstLetter() {
        System.out.println("getCapitalFirstLetter");
        String s = "without capital first letter.";
        String expResult = "Without capital first letter.";
        String result = Generic_String.getCapitalFirstLetter(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCount method, of class Generic_String.
     */
    @Test
    public void testGetCount() {
        System.out.println("getCount");
        String s;
        String s0;
        int expResult;
        int result;
        // Test 1
        s = "abcdefghijklmnopqrstuvwxyz";
        s0 = "a";
        expResult = 1;
        result = Generic_String.getCount(s, s0);
        assertEquals(expResult, result);
        // Test 2
        s = "aabcdefghijklmnopqrstuvwxyz";
        s0 = "a";
        expResult = 2;
        result = Generic_String.getCount(s, s0);
        assertEquals(expResult, result);
        // Test 3
        s = "Aabcdefghijklmnopqrstuvwxyz";
        s0 = "a";
        expResult = 1;
        result = Generic_String.getCount(s, s0);
        assertEquals(expResult, result);
        // Test 3
        s = "Aabcdefghijklmnopqrstuvwxyz";
        s0 = "a";
        expResult = 1;
        result = Generic_String.getCount(s, s0);
        assertEquals(expResult, result);
    }

}
