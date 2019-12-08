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
package uk.ac.leeds.ccg.agdt.generic.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Strings;

/**
 * Tests for {@link Generic_FileStore} class.
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_FileStoreTest {

    //Generic_Environment env;
    //int logID;
    public Generic_FileStoreTest() {
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
     * Test creating a new file store adding to it and creating another file store 
     * based on the existing directory and adding to that.
     */
    @Test
    public void testFileStore1() throws IOException, Exception {
        //try {
            System.out.println("testFileStore1");
            // Set the path.
            Path p = Paths.get(System.getProperty("user.home"),
                    Generic_Strings.s_data,
                    Generic_Strings.s_generic);
            // Set the name for the file store which should be unique and not used 
            // by any other tests which are executed in parallel.
            String name = "test2";
            Path p2 = Paths.get(p.toString(), name);
            // Delete any existing file store.
            if (true) {
                if (Files.exists(p2)) {
                    Generic_IO.delete(p2, false);
                }
            }
            // Load a new file store.
            short range = 10;
            Generic_FileStore a = new Generic_FileStore(p, name, range);
            // Add 1001 directories.
            for (long l = 0; l < 1001; l++) {
                a.addDir();
            }
            // Details of data store
            System.out.println(a.toString());
            // Reload the existing file store.
            a = new Generic_FileStore(p2);
            // Details of data store
            System.out.println(a.toString());
            /**
             * If there are two file stores with the same baseDir then if one of
             * them changes the file store structure the other will have nextID,
             * lps, ranges and dirCounts that are inconsistent with what is
             * actually stored in the file system.
             */
            // Add another 1001 directories.
            for (long l = 0; l < 1001; l++) {
                a.addDir();
            }
            // Delete the file store.
            if (true) {
                if (Files.exists(p2)) {
                    Generic_IO.delete(p2, false);
                }
            }
//        } catch (Exception ex) {
//            ex.printStackTrace(System.err);
//            Assertions.assertTrue(false);
//        }
    }
    /**
     * Test of getLevels method, of class Generic_FileStore.
     */
    @Test
    public void testGetLevels_long_long() {
        System.out.println("getLevels");
        long n = 101L;
        long range = 10L;
        int expResult = 3;
        int result = Generic_FileStore.getLevels(n, range);
        Assertions.assertEquals(expResult, result);
        // Test 2
        n = 1001L;
        range = 10L;
        expResult = 4;
        result = Generic_FileStore.getLevels(n, range);
        Assertions.assertEquals(expResult, result);
        // Test 3
        n = 10001L;
        range = 10L;
        expResult = 5;
        result = Generic_FileStore.getLevels(n, range);
        Assertions.assertEquals(expResult, result);
        // Test 4
        n = 100001L;
        range = 10L;
        expResult = 6;
        result = Generic_FileStore.getLevels(n, range);
        Assertions.assertEquals(expResult, result);
        // Test 5
        n = 100001L;
        range = 100L;
        expResult = 3;
        result = Generic_FileStore.getLevels(n, range);
        Assertions.assertEquals(expResult, result);
        // Test 6
        n = 10000001L;
        range = 100L;
        expResult = 4;
        result = Generic_FileStore.getLevels(n, range);
        Assertions.assertEquals(expResult, result);
        // Test 6
        n = 12345678910L;
        range = 34L;
        expResult = 7;
        result = Generic_FileStore.getLevels(n, range);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getRanges method, of class Generic_FileStore.
     */
    @Test
    public void testGetRanges_long_long() throws Exception {
        System.out.println("getRanges");
        long n = 1001L;
        long range = 10L;
        ArrayList<Long> expResult = new ArrayList<>();
        expResult.add(10000L);
        expResult.add(1000L);
        expResult.add(100L);
        expResult.add(10L);
        ArrayList<Long> result;
        try {
            result = Generic_FileStore.getRanges(n, range);
            Assertions.assertArrayEquals(expResult.toArray(), result.toArray());
        } catch (Exception ex) {
            int debug = 1;
        }
        // Test 2
        n = 10001L;
        range = 10L;
        expResult = new ArrayList<>();
        expResult.add(100000L);
        expResult.add(10000L);
        expResult.add(1000L);
        expResult.add(100L);
        expResult.add(10L);
        result = Generic_FileStore.getRanges(n, range);
        Assertions.assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of getDirIndexes method, of class Generic_FileStore.
     */
    @Test
    public void testGetDirIndexes_3args() throws Exception {
        System.out.println("getDirIndexes");
        long id = 10001L;
        long range = 10L;
        int levels = Generic_FileStore.getLevels(id, range);;
        ArrayList<Long> ranges = Generic_FileStore.getRanges(id, range);
        ArrayList<Integer> expResult  = new ArrayList<>();
        expResult.add(0);
        expResult.add(1);
        expResult.add(10);
        expResult.add(100);
        expResult.add(1000);
        ArrayList<Integer> result = Generic_FileStore.getDirIndexes(id, levels, ranges);
        Assertions.assertArrayEquals(expResult.toArray(), result.toArray());
    }
//
//    /**
//     * Test of getDirIndexes method, of class Generic_FileStore.
//     */
//    @org.junit.Test
//    public void testGetDirIndexes_long() {
//        System.out.println("getDirIndexes");
//        long id = 0L;
//        Generic_FileStore instance = null;
//        ArrayList<Integer> expResult = null;
//        ArrayList<Integer> result = instance.getDirIndexes(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPath method, of class Generic_FileStore.
//     */
//    @org.junit.Test
//    public void testGetPath() {
//        System.out.println("getPath");
//        long id = 0L;
//        Generic_FileStore instance = null;
//        Path expResult = null;
//        Path result = instance.getPath(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of testIntegrity method, of class Generic_FileStore.
//     */
//    @org.junit.Test
//    public void testTestIntegrity() throws Exception {
//        System.out.println("testIntegrity");
//        Generic_FileStore instance = null;
//        boolean expResult = false;
//        boolean result = instance.testIntegrity();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of testPath method, of class Generic_FileStore.
//     */
//    @org.junit.Test
//    public void testTestPath() {
//        System.out.println("testPath");
//        Path p = null;
//        Generic_FileStore instance = null;
//        boolean expResult = false;
//        boolean result = instance.testPath(p);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getHighestLeaf method, of class Generic_FileStore.
//     */
//    @org.junit.Test
//    public void testGetHighestLeaf() throws Exception {
//        System.out.println("getHighestLeaf");
//        Generic_FileStore instance = null;
//        Path expResult = null;
//        Path result = instance.getHighestLeaf();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findHighestLeaf method, of class Generic_FileStore.
//     */
//    @org.junit.Test
//    public void testFindHighestLeaf() throws Exception {
//        System.out.println("findHighestLeaf");
//        Generic_FileStore instance = null;
//        Path expResult = null;
//        Path result = instance.findHighestLeaf();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getHighestDir method, of class Generic_FileStore.
//     */
//    @org.junit.Test
//    public void testGetHighestDir() throws Exception {
//        System.out.println("getHighestDir");
//        Generic_FileStore instance = null;
//        Path expResult = null;
//        Path result = instance.getHighestDir();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getHighestDir0 method, of class Generic_FileStore.
//     */
//    @org.junit.Test
//    public void testGetHighestDir0() throws Exception {
//        System.out.println("getHighestDir0");
//        Path p = null;
//        Generic_FileStore instance = null;
//        Path expResult = null;
//        Path result = instance.getHighestDir0(p);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
