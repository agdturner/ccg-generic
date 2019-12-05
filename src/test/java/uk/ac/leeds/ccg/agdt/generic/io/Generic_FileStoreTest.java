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

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Strings;

/**
 * To test all the functionality of Generic_Archive.
 *
 * @author Andy Turner
 * @version 1.0.0
 *
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

//    /**
//     * Test of initNextRange method, of class Generic_FileStore.
//     */
//    @Test
//    public void testInitNextRange() {
//        System.out.println("initNextRange");
//        Generic_FileStore instance = null;
//        instance.initNextRange();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLevels method, of class Generic_FileStore.
//     */
//    @Test
//    public void testGetLevels_long_long() {
//        System.out.println("getLevels");
//        long n = 0L;
//        long range = 0L;
//        long expResult = 0L;
//        long result = Generic_FileStore.getLevels(n, range);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLevels method, of class Generic_FileStore.
//     */
//    @Test
//    public void testGetLevels_long() {
//        System.out.println("getLevels");
//        long n = 0L;
//        Generic_FileStore instance = null;
//        long expResult = 0L;
//        long result = instance.getLevels(n);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of initLevelsAndNextID method, of class Generic_FileStore.
//     */
//    @Test
//    public void testInitLevelsAndNextID() throws Exception {
//        System.out.println("initLevelsAndNextID");
//        Generic_FileStore instance = null;
//        instance.initLevelsAndNextID();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLevels method, of class Generic_FileStore.
//     */
//    @Test
//    public void testGetLevels_0args() {
//        System.out.println("getLevels");
//        Generic_FileStore instance = null;
//        long expResult = 0L;
//        long result = instance.getLevels();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRanges method, of class Generic_FileStore.
//     */
//    @Test
//    public void testGetRanges_0args() {
//        System.out.println("getRanges");
//        Generic_FileStore instance = null;
//        ArrayList<Long> expResult = null;
//        ArrayList<Long> result = instance.getRanges();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRanges method, of class Generic_FileStore.
//     */
//    @Test
//    public void testGetRanges_long_long() throws Exception {
//        System.out.println("getRanges");
//        long n = 0L;
//        long range = 0L;
//        ArrayList<Long> expResult = null;
//        ArrayList<Long> result = Generic_FileStore.getRanges(n, range);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDirCounts method, of class Generic_FileStore.
//     */
//    @Test
//    public void testGetDirCounts() throws Exception {
//        System.out.println("getDirCounts");
//        long n = 0L;
//        long range = 0L;
//        ArrayList<Integer> expResult = null;
//        ArrayList<Integer> result = Generic_FileStore.getDirCounts(n, range);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDirIndexes method, of class Generic_FileStore.
//     */
//    @Test
//    public void testGetDirIndexes_3args() {
//        System.out.println("getDirIndexes");
//        long id = 0L;
//        int levels = 0;
//        ArrayList<Long> ranges = null;
//        ArrayList<Integer> expResult = null;
//        ArrayList<Integer> result = Generic_FileStore.getDirIndexes(id, levels, ranges);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDirIndexes method, of class Generic_FileStore.
//     */
//    @Test
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
//    @Test
//    public void testGetPath_long() {
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
//     * Test of getPath method, of class Generic_FileStore.
//     */
//    @Test
//    public void testGetPath_long_int() {
//        System.out.println("getPath");
//        long id = 0L;
//        int lvl = 0;
//        Generic_FileStore instance = null;
//        Path expResult = null;
//        Path result = instance.getPath(id, lvl);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getName method, of class Generic_FileStore.
//     */
//    @Test
//    public void testGetName() {
//        System.out.println("getName");
//        long l = 0L;
//        long u = 0L;
//        Generic_FileStore instance = null;
//        String expResult = "";
//        String result = instance.getName(l, u);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of initLPs method, of class Generic_FileStore.
//     */
//    @Test
//    public void testInitLPs() {
//        System.out.println("initLPs");
//        Generic_FileStore instance = null;
//        instance.initLPs();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    /**
//     * Test of addToArchive method, of class Generic_FileStore.
//     */
//    @Test
//    public void testAddDir() throws Exception {
//        System.out.println("addDir");
//        Path p = Paths.get(System.getProperty("user.home"),
//                Generic_Strings.s_data,
//                Generic_Strings.s_generic);
//        String name = "test";
//        //if (false) {
//        if (true) {
//            // Delete old test archive if it exists.
//            Path d = Paths.get(p.toString(), name);
//            if (Files.exists(d)) {
//                try (Stream<Path> walk = Files.walk(d)) {
//                    walk.sorted(Comparator.reverseOrder())
//                            .map(Path::toFile)
//                            //.peek(System.out::println)
//                            .forEach(File::delete);
//                }
//            }
//        }
//        // Create new archive.
//        short range = 10;
//        Generic_FileStore a = new Generic_FileStore(p, name, range);
//
//        for (long l = 0; l < a.rangeBI.pow(3).add(BigInteger.ONE).longValueExact(); l++) {
//            //for (long l = 0; l < 11; l++) {
//            a.addDir();
//        }
//    }

    /**
     * Test of addToArchive method, of class Generic_FileStore.
     */
    @Test
    public void testAddDir2() throws Exception {
        System.out.println("addDir");
        Path p = Paths.get(System.getProperty("user.home"),
                Generic_Strings.s_data,
                Generic_Strings.s_generic);
        String name = "test2";
        if (true) {
            if (Files.exists(p)) {
                Generic_IO.delete(p, false);
            }
        }
        // Create new archive.
        short range = 10;
        Generic_FileStore a = new Generic_FileStore(p, name, range);

        for (long l = 0; l < a.rangeBI.pow(3).add(BigInteger.ONE).longValueExact(); l++) {
            //for (long l = 0; l < 11; l++) {
            a.addDir();
        }
        
        p = Paths.get(p.toString(), name);
        Generic_FileStore b = new Generic_FileStore(p);
        b.addDir();
        

    }

//    /**
//     * Test of testIntegrity method, of class Generic_FileStore.
//     */
//    @Test
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
//    @Test
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
//    @Test
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
//     * Test of getHighestDir method, of class Generic_FileStore.
//     */
//    @Test
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
//    @Test
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
