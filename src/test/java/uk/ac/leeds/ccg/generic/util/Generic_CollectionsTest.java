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
package uk.ac.leeds.ccg.generic.util;

import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import uk.ac.leeds.ccg.generic.core.Generic_Strings;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_FilesTest;


/**
 * Tests for {@link Generic_Collections} class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class Generic_CollectionsTest {

    Generic_Environment env;

    public Generic_CollectionsTest() {
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

    @Nested
    class GetKeys_Map_GenericType {

        @BeforeEach
        void createAndInitialize() {
        }

        @Nested
        class WhenX {
 
            @BeforeEach
            void beforeEach() {
                System.out.println("Before each test method of the WhenX class");
            }
 
            @AfterEach
            void afterEach() {
                System.out.println("After each test method of the WhenX class");
            }
 
            @Test
            @DisplayName("Example test for GetKeys_Map_GenericType when X is true")
            void test2() {
                System.out.println("Example test for GetKeys_Map_GenericType when X is true");
            }
        }
    }

    /**
     * Test of getMax method, of class Generic_Collections.
     */
    @Test
    public void testAddToCount_BigInteger() {
        Map<Integer, BigInteger> count = new HashMap<>();
        Integer i = 0;
        count.put(i, BigInteger.ZERO);
        Generic_Collections.addToCount(count, i, BigInteger.TWO);
        BigInteger r = count.get(i);
        Assertions.assertTrue(r.compareTo(BigInteger.TWO) == 0);
        Generic_Collections.addToCount(count, i, BigInteger.TWO);
        r = count.get(i);
        Assertions.assertTrue(r.compareTo(BigInteger.valueOf(4)) == 0);
    }

//    /**
//     * Test of getMax method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMax() {
//        env.log("getMax", logID);
//        //env.log("getMax");
//        ArrayList<BigDecimal> l;
//        l = new ArrayList<>();
//        l.add(new BigDecimal(0.0d));
//        l.add(new BigDecimal(10.0d));
//        l.add(new BigDecimal(-10.0d));
//        BigDecimal expResult = new BigDecimal(10.0d);
//        BigDecimal result = Generic_Collections.getMax(l);
//        Assertions.assertEquals(expResult, result);
//    }

//    /**
//     * Test of getMax method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMin() {
//        env.log("getMin", logID);
//        //env.log("getMin");
//        ArrayList<BigDecimal> l;
//        l = new ArrayList<>();
//        l.add(new BigDecimal(0.0d));
//        l.add(new BigDecimal(10.0d));
//        l.add(new BigDecimal(-10.0d));
//        BigDecimal expResult = new BigDecimal(-10.0d);
//        BigDecimal result = Generic_Collections.getMin(l);
//        Assertions.assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getMax method, of class Generic_Collections.
//     */
//    @Test
//    public void testContainsValue() {
//        env.log("containsValue", logID);
//        //env.log("containsValue");
//        ArrayList<BigDecimal> l;
//        l = new ArrayList<>();
//        l.add(new BigDecimal(0.0d));
//        l.add(new BigDecimal(10.0d));
//        l.add(new BigDecimal(-10.0d));
//        BigDecimal b;
//        b = new BigDecimal(-10.0d);
//        Assertions.assertTrue(Generic_Collections.containsValue(l, b));
//        b = new BigDecimal(0.0d);
//        Assertions.assertTrue(Generic_Collections.containsValue(l, b));
//        b = new BigDecimal(1.0d);
//        Assertions.assertFalse(Generic_Collections.containsValue(l, b));
//    }

//    /**
//     * Test of getKeys method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetKeys_Map_Set() {
//        System.out.println("getKeys");
//        HashSet expResult = null;
//        HashSet result = Generic_Collections.getKeys(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntervalCountsLabelsMins method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetIntervalCountsLabelsMins() {
//        System.out.println("getIntervalCountsLabelsMins");
//        BigDecimal min = null;
//        BigDecimal w = null;
//        TreeMap map = null;
//        MathContext mc = null;
//        Object[] expResult = null;
//        Object[] result = Generic_Collections.getIntervalCountsLabelsMins(min, w, map, mc);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntervalMin method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetIntervalMin() {
//        System.out.println("getIntervalMin");
//        BigDecimal min = null;
//        BigDecimal w = null;
//        int interval = 0;
//        BigDecimal expResult = null;
//        BigDecimal result = Generic_Collections.getIntervalMin(min, w, interval);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntervalMax method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetIntervalMax() {
//        System.out.println("getIntervalMax");
//        BigDecimal min = null;
//        BigDecimal w = null;
//        BigDecimal expResult = null;
//        BigDecimal result = Generic_Collections.getIntervalMax(min, w);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getInterval method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetInterval() {
//        System.out.println("getInterval");
//        BigDecimal min = null;
//        BigDecimal w = null;
//        BigDecimal v = null;
//        MathContext mc = null;
//        int expResult = 0;
//        int result = Generic_Collections.getInterval(min, w, v, mc);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMinMaxBigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMinMaxBigDecimal() {
//        System.out.println("getMinMaxBigDecimal");
//        BigDecimal[] expResult = null;
//        BigDecimal[] result = Generic_Collections.getMinMaxBigDecimal(null);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMinMaxInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMinMaxInteger() {
//        System.out.println("getMinMaxInteger");
//        int[] expResult = null;
//        int[] result = Generic_Collections.getMinMaxInteger(null);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCompleteKeySet_HashSet method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetCompleteKeySet_HashSet() {
//        System.out.println("getCompleteKeySet_HashSet");
//        Set<Integer> s0 = null;
//        Set<Integer> s1 = null;
//        HashSet<Integer> expResult = null;
//        HashSet<Integer> result = Generic_Collections.getCompleteKeySet_HashSet(s0, s1);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUnion method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetUnion() {
//        System.out.println("getUnion");
//        HashSet expResult = null;
//        HashSet result = Generic_Collections.getUnion(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMap method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMap_3args_1() {
//        System.out.println("addToMap");
//        Generic_Collections.addToMap(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMap method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMap_4args() {
//        System.out.println("addToMap");
//        Generic_Collections.addToMap(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMap method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMap_3args_2() {
//        System.out.println("addToMap");
//        Generic_Collections.addToMap(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToListIfDifferentFromLast method, of class
//     * Generic_Collections.
//     */
//    @Test
//    public void testAddToListIfDifferentFromLast() {
//        System.out.println("addToListIfDifferentFromLast");
//        Generic_Collections.addToListIfDifferentFromLast(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMapInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMapInteger_3args() {
//        System.out.println("addToMapInteger");
//        Integer expResult = null;
//        Integer result = Generic_Collections.addToMapInteger(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMap method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMap_Map_Map() {
//        System.out.println("addToMap");
//        Generic_Collections.addToMap(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMapInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMapInteger_Map_Map() {
//        System.out.println("addToMapInteger");
//        Generic_Collections.addToMapInteger(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMapLong method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMapLong_3args() {
//        System.out.println("addToMapLong");
//        long expResult = 0L;
//        long result = Generic_Collections.addToMapLong(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setMaxValueInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testSetMaxValueInteger() {
//        System.out.println("setMaxValueInteger");
//        Generic_Collections.setMaxValueInteger(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setMinValueInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testSetMinValueInteger() {
//        System.out.println("setMinValueInteger");
//        Generic_Collections.setMinValueInteger(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMapBigInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMapBigInteger_3args() {
//        System.out.println("addToMapBigInteger");
//        BigInteger expResult = null;
//        BigInteger result = Generic_Collections.addToMapBigInteger(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMapBigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMapBigDecimal_3args() {
//        System.out.println("addToMapBigDecimal");
//        BigDecimal expResult = null;
//        BigDecimal result = Generic_Collections.addToMapBigDecimal(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCounts method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetCounts() {
//        System.out.println("getCounts");
//        HashSet s0 = null;
//        HashSet s1 = null;
//        long[] expResult = null;
//        long[] result = Generic_Collections.getCounts(s0, s1);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUnionAndCounts method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetUnionAndCounts() {
//        System.out.println("getUnionAndCounts");
//        Object[] expResult = null;
//        Object[] result = Generic_Collections.getUnionAndCounts(null);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUnionAndUniques method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetUnionAndUniques() {
//        System.out.println("getUnionAndUniques");
//        Object[] expResult = null;
//        Object[] result = Generic_Collections.getUnionAndUniques(null);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyTreeMapBigInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyTreeMapBigInteger() {
//        System.out.println("deepCopyTreeMapBigInteger");
//        TreeMap expResult = null;
//        TreeMap result = Generic_Collections.deepCopyTreeMapBigInteger(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyHashMapString method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyHashMapString() {
//        System.out.println("deepCopyHashMapString");
//        HashMap expResult = null;
//        HashMap result = Generic_Collections.deepCopyHashMapString(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyHashMapInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyHashMapInteger() {
//        System.out.println("deepCopyHashMapInteger");
//        HashMap expResult = null;
//        HashMap result = Generic_Collections.deepCopyHashMapInteger(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyTreeMapInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyTreeMapInteger() {
//        System.out.println("deepCopyTreeMapInteger");
//        TreeMap expResult = null;
//        TreeMap result = Generic_Collections.deepCopyTreeMapInteger(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyTreeMapBigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyTreeMapBigDecimal() {
//        System.out.println("deepCopyTreeMapBigDecimal");
//        TreeMap expResult = null;
//        TreeMap result = Generic_Collections.deepCopyTreeMapBigDecimal(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyTreeMapLong method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyTreeMapLong() {
//        System.out.println("deepCopyTreeMapLong");
//        TreeMap expResult = null;
//        TreeMap result = Generic_Collections.deepCopyTreeMapLong(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMapLong method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMapLong_Map_Map() {
//        System.out.println("addToMapLong");
//        Generic_Collections.addToMapLong(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMapBigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMapBigDecimal_Map_Map() {
//        System.out.println("addToMapBigDecimal");
//        Generic_Collections.addToMapBigDecimal(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMapBigInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMapBigInteger_Map_Map() {
//        System.out.println("addToMapBigInteger");
//        Generic_Collections.addToMapBigInteger(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFirstKey method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetFirstKey() {
//        System.out.println("getFirstKey");
//        Object expResult = null;
//        Object result = Generic_Collections.getFirstKey(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLastKey method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetLastKey() {
//        System.out.println("getLastKey");
//        Object expResult = null;
//        Object result = Generic_Collections.getLastKey(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMaxValueBigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMaxValueBigDecimal() {
//        System.out.println("getMaxValueBigDecimal");
//        BigDecimal expResult = null;
//        BigDecimal result = Generic_Collections.getMaxValueBigDecimal(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMinValueBigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMinValueBigDecimal() {
//        System.out.println("getMinValueBigDecimal");
//        BigDecimal expResult = null;
//        BigDecimal result = Generic_Collections.getMinValueBigDecimal(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMaxValueBigInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMaxValueBigInteger() {
//        System.out.println("getMaxValueBigInteger");
//        BigInteger expResult = null;
//        BigInteger result = Generic_Collections.getMaxValueBigInteger(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMinValueBigInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMinValueBigInteger() {
//        System.out.println("getMinValueBigInteger");
//        BigInteger expResult = null;
//        BigInteger result = Generic_Collections.getMinValueBigInteger(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of sortByValue method, of class Generic_Collections.
//     */
//    @Test
//    public void testSortByValue() {
//        System.out.println("sortByValue");
//        Map expResult = null;
//        Map result = Generic_Collections.sortByValue(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    /**
     * Test of sortByValue method, of class Generic_Collections.
     */
    @Test
    public void testConcatenate() {
        System.out.println("concatenate");
        // s0
        String[] s0 = new String[2];
        s0[0] = "Zero";
        s0[1] = "One";
        // s1
        String[] s1 = new String[1];
        s1[0] = "Two";
        // s1
        String[] s2 = new String[2];
        s2[0] = "Three";
        s2[1] = "Four";
        // expResult
        String[] expResult = new String[4];
        expResult[0] = "Zero";
        expResult[1] = "One";
        expResult[2] = "Two";
        expResult[3] = "Three";
        expResult[4] = "Four";
        // Result
        String[] result = Generic_Collections.concatenate(s0, s1, s2);
        int l = result.length;
        Assertions.assertTrue(l == expResult.length);
        for (int i = 0; i < l; i ++) {
            Assertions.assertTrue(expResult[i].equalsIgnoreCase(result[i]));
        }
    }
}
