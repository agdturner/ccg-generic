/*
 * Copyright (C) 2019 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_Files;
import uk.ac.leeds.ccg.andyt.generic.time.Generic_DateTest;

/**
 *
 * @author geoagdt
 */
public class Generic_CollectionsTest {

    Generic_Environment env;
    int logID;

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

//    /**
//     * Test of getKeyFromValue method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetKeyFromValue() {
//        System.out.println("getKeyFromValue");
//        Map m = null;
//        Object value = null;
//        Object expResult = null;
//        Object result = Generic_Collections.getKeyFromValue(m, value);
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
//        BigDecimal intervalWidth = null;
//        TreeMap map = null;
//        MathContext mc = null;
//        Object[] expResult = null;
//        Object[] result = Generic_Collections.getIntervalCountsLabelsMins(min, intervalWidth, map, mc);
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
//        BigDecimal intervalWidth = null;
//        int interval = 0;
//        BigDecimal expResult = null;
//        BigDecimal result = Generic_Collections.getIntervalMin(min, intervalWidth, interval);
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
//        BigDecimal intervalMin = null;
//        BigDecimal intervalWidth = null;
//        BigDecimal expResult = null;
//        BigDecimal result = Generic_Collections.getIntervalMax(intervalMin, intervalWidth);
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
//        BigDecimal intervalWidth = null;
//        BigDecimal value = null;
//        MathContext mc = null;
//        int expResult = 0;
//        int result = Generic_Collections.getInterval(min, intervalWidth, value, mc);
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
//        Map map = null;
//        BigDecimal[] expResult = null;
//        BigDecimal[] result = Generic_Collections.getMinMaxBigDecimal(map);
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
//        Map map = null;
//        int[] expResult = null;
//        int[] result = Generic_Collections.getMinMaxInteger(map);
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
//        Set<Integer> keys0 = null;
//        Set<Integer> keys1 = null;
//        HashSet<Integer> expResult = null;
//        HashSet<Integer> result = Generic_Collections.getCompleteKeySet_HashSet(keys0, keys1);
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
//        //Generic_Collections.addToMap(null);
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
//        //Generic_Collections.addToMap(null);
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
//        //Generic_Collections.addToMap(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToMap method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToMap_3args_3() {
//        System.out.println("addToMap");
//        //Generic_Collections.addToMap(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToListIfDifferentFromLast method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToListIfDifferentFromLast() {
//        System.out.println("addToListIfDifferentFromLast");
//        //Generic_Collections.addToListIfDifferentFromLast(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToTreeMapIntegerInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToTreeMapIntegerInteger_3args() {
//        System.out.println("addToTreeMapIntegerInteger");
//        TreeMap<Integer, Integer> m = null;
//        Integer key = null;
//        Integer value = null;
//        Generic_Collections.addToTreeMapIntegerInteger(m, key, value);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToTreeMapIntegerInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToTreeMapIntegerInteger_TreeMap_TreeMap() {
//        System.out.println("addToTreeMapIntegerInteger");
//        TreeMap<Integer, Integer> updateIntegerIntegerCounter = null;
//        TreeMap<Integer, Integer> updateFromIntegerIntegerCounter = null;
//        Generic_Collections.addToTreeMapIntegerInteger(updateIntegerIntegerCounter, updateFromIntegerIntegerCounter);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToTreeMapValueLong method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToTreeMapValueLong_3args() {
//        System.out.println("addToTreeMapValueLong");
//        long expResult = 0L;
//        //long result = Generic_Collections.addToTreeMapValueLong(null);
//      //  assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToTreeMapValueInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToTreeMapValueInteger_3args() {
//        System.out.println("addToTreeMapValueInteger");
//        int expResult = 0;
//        //int result = Generic_Collections.addToTreeMapValueInteger(null);
//       // assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToTreeMapValueInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToTreeMapValueInteger_TreeMap_TreeMap() {
//        System.out.println("addToTreeMapValueInteger");
//        TreeMap expResult = null;
//        //TreeMap result = Generic_Collections.addToTreeMapValueInteger(null);
//       // assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setMaxValueTreeMapStringInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testSetMaxValueTreeMapStringInteger() {
//        System.out.println("setMaxValueTreeMapStringInteger");
//        TreeMap<String, Integer> m = null;
//        String k = "";
//        Integer v = null;
//        Generic_Collections.setMaxValueTreeMapStringInteger(m, k, v);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setMinValueTreeMapStringInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testSetMinValueTreeMapStringInteger() {
//        System.out.println("setMinValueTreeMapStringInteger");
//        TreeMap<String, Integer> m = null;
//        String k = "";
//        Integer v = null;
//        Generic_Collections.setMinValueTreeMapStringInteger(m, k, v);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToTreeMapValueBigInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToTreeMapValueBigInteger_3args() {
//        System.out.println("addToTreeMapValueBigInteger");
//       // Generic_Collections.addToTreeMapValueBigInteger(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToTreeMapValueBigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToTreeMapValueBigDecimal_3args() {
//        System.out.println("addToTreeMapValueBigDecimal");
//       // Generic_Collections.addToTreeMapValueBigDecimal(null);
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
//        HashSet set0 = null;
//        HashSet set1 = null;
//        long[] expResult = null;
//        long[] result = Generic_Collections.getCounts(set0, set1);
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
//       // Object[] result = Generic_Collections.getUnionAndCounts(null);
//       // assertArrayEquals(expResult, result);
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
//      //  Object[] result = Generic_Collections.getUnionAndUniques(null);
//      //  assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyValueBigInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyValueBigInteger() {
//        System.out.println("deepCopyValueBigInteger");
//        TreeMap expResult = null;
//        TreeMap result = Generic_Collections.deepCopyValueBigInteger(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyHashMapValueString method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyHashMapValueString() {
//        System.out.println("deepCopyHashMapValueString");
//        HashMap expResult = null;
//        HashMap result = Generic_Collections.deepCopyHashMapValueString(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyHashMapValueInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyHashMapValueInteger() {
//        System.out.println("deepCopyHashMapValueInteger");
//        HashMap expResult = null;
//        HashMap result = Generic_Collections.deepCopyHashMapValueInteger(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyTreeMapValueInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyTreeMapValueInteger() {
//        System.out.println("deepCopyTreeMapValueInteger");
//        TreeMap expResult = null;
//        TreeMap result = Generic_Collections.deepCopyTreeMapValueInteger(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyTreeMapValueBigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyTreeMapValueBigDecimal() {
//        System.out.println("deepCopyTreeMapValueBigDecimal");
//        TreeMap expResult = null;
//        TreeMap result = Generic_Collections.deepCopyTreeMapValueBigDecimal(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deepCopyTreeMapValueLong method, of class Generic_Collections.
//     */
//    @Test
//    public void testDeepCopyTreeMapValueLong() {
//        System.out.println("deepCopyTreeMapValueLong");
//        TreeMap expResult = null;
//        TreeMap result = Generic_Collections.deepCopyTreeMapValueLong(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToTreeMapValueLong method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToTreeMapValueLong_TreeMap_TreeMap() {
//        System.out.println("addToTreeMapValueLong");
//      //  Generic_Collections.addToTreeMapValueLong(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToTreeMapValueBigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToTreeMapValueBigDecimal_TreeMap_TreeMap() {
//        System.out.println("addToTreeMapValueBigDecimal");
//     //   Generic_Collections.addToTreeMapValueBigDecimal(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToTreeMapValueBigInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testAddToTreeMapValueBigInteger_TreeMap_TreeMap() {
//        System.out.println("addToTreeMapValueBigInteger");
//      //  Generic_Collections.addToTreeMapValueBigInteger(null);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMaxKey_Integer method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMaxKey_Integer() {
//        System.out.println("getMaxKey_Integer");
//        TreeMap m = null;
//        Integer i = null;
//        Integer expResult = null;
//        Integer result = Generic_Collections.getMaxKey_Integer(m, i);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMinKey_Integer method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMinKey_Integer() {
//        System.out.println("getMinKey_Integer");
//        TreeMap m = null;
//        Integer i = null;
//        Integer expResult = null;
//        Integer result = Generic_Collections.getMinKey_Integer(m, i);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMaxValue_BigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMaxValue_BigDecimal() {
//        System.out.println("getMaxValue_BigDecimal");
//        TreeMap m = null;
//        BigDecimal initialMax_BigDecimal = null;
//        BigDecimal expResult = null;
//        BigDecimal result = Generic_Collections.getMaxValue_BigDecimal(m, initialMax_BigDecimal);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMinValue_BigDecimal method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMinValue_BigDecimal() {
//        System.out.println("getMinValue_BigDecimal");
//        TreeMap m = null;
//        BigDecimal initialMin = null;
//        BigDecimal expResult = null;
//        BigDecimal result = Generic_Collections.getMinValue_BigDecimal(m, initialMin);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMaxValue_BigInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMaxValue_BigInteger() {
//        System.out.println("getMaxValue_BigInteger");
//        TreeMap m = null;
//        BigInteger initialMax = null;
//        BigInteger expResult = null;
//        BigInteger result = Generic_Collections.getMaxValue_BigInteger(m, initialMax);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMinValue_BigInteger method, of class Generic_Collections.
//     */
//    @Test
//    public void testGetMinValue_BigInteger() {
//        System.out.println("getMinValue_BigInteger");
//        TreeMap m = null;
//        BigInteger initialMin = null;
//        BigInteger expResult = null;
//        BigInteger result = Generic_Collections.getMinValue_BigInteger(m, initialMin);
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
     * Test of getMax method, of class Generic_Collections.
     */
    @Test
    public void testGetMax() {
        env.log("getMax", logID);
        //env.log("getMax");
        ArrayList<BigDecimal> l;
        l = new ArrayList<>();
        l.add(new BigDecimal(0.0d));
        l.add(new BigDecimal(10.0d));
        l.add(new BigDecimal(-10.0d));
        BigDecimal expResult = new BigDecimal(10.0d);
        BigDecimal result = Generic_Collections.getMax(l);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getMax method, of class Generic_Collections.
     */
    @Test
    public void testGetMin() {
        env.log("getMin", logID);
        //env.log("getMin");
        ArrayList<BigDecimal> l;
        l = new ArrayList<>();
        l.add(new BigDecimal(0.0d));
        l.add(new BigDecimal(10.0d));
        l.add(new BigDecimal(-10.0d));
        BigDecimal expResult = new BigDecimal(-10.0d);
        BigDecimal result = Generic_Collections.getMin(l);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getMax method, of class Generic_Collections.
     */
    @Test
    public void testContainsValue() {
        env.log("containsValue", logID);
        //env.log("containsValue");
        ArrayList<BigDecimal> l;
        l = new ArrayList<>();
        l.add(new BigDecimal(0.0d));
        l.add(new BigDecimal(10.0d));
        l.add(new BigDecimal(-10.0d));
        BigDecimal b;
        b = new BigDecimal(-10.0d);
        Assertions.assertTrue(Generic_Collections.containsValue(l, b));
        b = new BigDecimal(0.0d);
        Assertions.assertTrue(Generic_Collections.containsValue(l, b));
        b = new BigDecimal(1.0d);
        Assertions.assertFalse(Generic_Collections.containsValue(l, b));
    }
}
