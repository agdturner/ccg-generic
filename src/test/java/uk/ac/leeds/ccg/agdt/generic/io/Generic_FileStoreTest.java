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
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//        try {
//            Generic_Files files = new Generic_Files(new Generic_Defaults());
//            env = new Generic_Environment(files);
//            logID = env.initLog(this.getClass().getSimpleName());
//        } catch (IOException ex) {
//            Logger.getLogger(Generic_FilesTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @AfterEach
    public void tearDown() {
    }

//    /**
//     * Test of initialiseArchive method, of class Generic_IO.
//     */
//    @Test
//    public void testInitialiseArchive_File_long() {
//        try {
//            env.log("initialiseArchive", logID);
//            //System.out.println("initialiseArchive");
//            Path dir = Paths.get(env.files.getGeneratedDir().toString(),
//                    "testArchive");
//            long range = 10L;
//            Path expResult;
//            if (Files.exists(dir)) {
//                expResult = env.io.getArchiveHighestLeafFile(dir);
//                /**
//                 * Let us assume that dir is an archive directory. So the
//                 * archive already exists and an attempt to initialise an
//                 * archive using exists = false should result in an IOException.
//                 */
//                Assertions.assertThrows(IOException.class, () -> {
//                    env.io.initialiseArchive(dir, range, false);
//                });
//            } else {
//                /**
//                 * If the archive does not already exist then initialise it.
//                 */
//                env.io.initialiseArchive(dir, range, false);
//                expResult = env.io.getArchiveHighestLeafFile(dir);
//            }
//            /**
//             * So the archive should exist now and if we try to initialise with
//             * exists = false then we expect an IOException. This is included
//             * here for completeness.
//             */
//            Assertions.assertThrows(IOException.class, () -> {
//                Path result = env.io.initialiseArchive(dir, range, false);
//            });
//            /**
//             * Test initialising an existing archive.
//             */
//            Path result = env.io.initialiseArchive(dir, range, true);
//            Assertions.assertEquals(result, expResult);
//            /**
//             * Add some new leaf files to the archive and test again.
//             */
//            for (int i = 0; i < range; i++) {
//                for (int j = 0; j < range; j++) {
//                    for (int k = 0; k < range; k++) {
//                        for (int l = 0; l < range; l++) {
//                            env.io.addToArchive(dir, range);
//                        }
//                    }
//                    //env.io.testArchiveIntegrity(dir);
//                }
//                env.io.testArchiveIntegrity(dir);
//            }
//            expResult = env.io.getArchiveHighestLeafFile(dir);
//            result = env.io.initialiseArchive(dir, range, true);
//            Assertions.assertEquals(result, expResult);
//        } catch (IOException ex) {
//            ex.printStackTrace(System.err);
//            env.log(ex.getMessage());
//        }
//    }

    /**
     * Test of testArchiveIntegrity method, of class Generic_Archive.
     */
    @Test
    public void testTestArchiveIntegrity() throws Exception {
        try {
        System.out.println("testArchiveIntegrity");
        Path dir = Paths.get(System.getProperty("user.home"), 
                Generic_Strings.s_data, Generic_Strings.s_generic, 
                Generic_Strings.s_generated, "test");
        Path p = Files.createDirectories(dir);
        //Generic_Archive instance = new Generic_Archive(dir, 2, 3);
        //boolean result = instance.isArchiveOK(dir);
        //assertTrue(result);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

//    /**
//     * Test of getArchiveHighestLeaf method, of class Generic_Archive.
//     */
//    @Test
//    public void testGetArchiveHighestLeaf() throws Exception {
//        System.out.println("getArchiveHighestLeaf");
//        Path dir = null;
//        Generic_Archive instance = null;
//        long expResult = 0L;
//        long result = instance.getArchiveHighestLeaf(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArchiveRange method, of class Generic_Archive.
//     */
//    @Test
//    public void testGetArchiveRange() throws Exception {
//        System.out.println("getArchiveRange");
//        Path dir = null;
//        Generic_Archive instance = null;
//        long expResult = 0L;
//        long result = instance.getArchiveRange(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArchiveLeafFilesMap method, of class Generic_Archive.
//     */
//    @Test
//    public void testGetArchiveLeafFilesMap_Path() throws Exception {
//        System.out.println("getArchiveLeafFilesMap");
//        Path dir = null;
//        Generic_Archive instance = null;
//        TreeMap<Long, Path> expResult = null;
//        TreeMap<Long, Path> result = instance.getArchiveLeafFilesMap(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArchiveLeafFilesMap method, of class Generic_Archive.
//     */
//    @Test
//    public void testGetArchiveLeafFilesMap_3args() throws Exception {
//        System.out.println("getArchiveLeafFilesMap");
//        Path dir = null;
//        long minID = 0L;
//        long maxID = 0L;
//        Generic_Archive instance = null;
//        TreeMap<Long, Path> expResult = null;
//        TreeMap<Long, Path> result = instance.getArchiveLeafFilesMap(dir, minID, maxID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArchiveHighestLeafFile method, of class Generic_Archive.
//     */
//    @Test
//    public void testGetArchiveHighestLeafFile() throws Exception {
//        System.out.println("getArchiveHighestLeafFile");
//        Path dir = null;
//        Generic_Archive instance = null;
//        Path expResult = null;
//        Path result = instance.getArchiveHighestLeafFile(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of growArchiveBase method, of class Generic_Archive.
//     */
//    @Test
//    public void testGrowArchiveBase_Path_long() throws Exception {
//        System.out.println("growArchiveBase");
//        Path dir = null;
//        long range = 0L;
//        Generic_Archive instance = null;
//        Path expResult = null;
//        Path result = instance.growArchiveBase(dir, range);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of growArchiveBase method, of class Generic_Archive.
//     */
//    @Test
//    public void testGrowArchiveBase_3args() throws Exception {
//        System.out.println("growArchiveBase");
//        Path dir = null;
//        long range = 0L;
//        long next_ID = 0L;
//        Generic_Archive instance = null;
//        Path expResult = null;
//        Path result = instance.growArchiveBase(dir, range, next_ID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToArchive method, of class Generic_Archive.
//     */
//    @Test
//    public void testAddToArchive_Path_long() throws Exception {
//        System.out.println("addToArchive");
//        Path dir = null;
//        long range = 0L;
//        Generic_Archive instance = null;
//        Path expResult = null;
//        Path result = instance.addToArchive(dir, range);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of growArchiveBaseIfNecessary method, of class Generic_Archive.
//     */
//    @Test
//    public void testGrowArchiveBaseIfNecessary() throws Exception {
//        System.out.println("growArchiveBaseIfNecessary");
//        Path dir = null;
//        long range = 0L;
//        Path newHighestLeafDir = null;
//        Generic_Archive instance = null;
//        instance.growArchiveBaseIfNecessary(dir, range, newHighestLeafDir);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToArchive method, of class Generic_Archive.
//     */
//    @Test
//    public void testAddToArchive_3args() throws Exception {
//        System.out.println("addToArchive");
//        Path dir = null;
//        long range = 0L;
//        long next_ID = 0L;
//        Generic_Archive instance = null;
//        Path expResult = null;
//        Path result = instance.addToArchive(dir, range, next_ID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFileThatExists method, of class Generic_Archive.
//     */
//    @Test
//    public void testGetFileThatExists() throws Exception {
//        System.out.println("getFileThatExists");
//        Path dir = null;
//        String filename = "";
//        Generic_Archive instance = null;
//        Path expResult = null;
//        Path result = instance.getFileThatExists(dir, filename);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNumericallyOrderedFiles method, of class Generic_Archive.
//     */
//    @Test
//    public void testGetNumericallyOrderedFiles_List() {
//        System.out.println("getNumericallyOrderedFiles");
//        List<Path> ps = null;
//        Generic_Archive instance = null;
//        TreeMap<Long, Path> expResult = null;
//        TreeMap<Long, Path> result = instance.getNumericallyOrderedFiles(ps);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNumericallyOrderedFiles2 method, of class Generic_Archive.
//     */
//    @Test
//    public void testGetNumericallyOrderedFiles2() {
//        System.out.println("getNumericallyOrderedFiles2");
//        List<Path> files = null;
//        Generic_Archive instance = null;
//        TreeMap<Long, Path> expResult = null;
//        TreeMap<Long, Path> result = instance.getNumericallyOrderedFiles2(files);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNumericallyOrderedFiles method, of class Generic_Archive.
//     */
//    @Test
//    public void testGetNumericallyOrderedFiles_Path() throws Exception {
//        System.out.println("getNumericallyOrderedFiles");
//        Path dir = null;
//        Generic_Archive instance = null;
//        HashMap<Long, String> expResult = null;
//        HashMap<Long, String> result = instance.getNumericallyOrderedFiles(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getObjectDir method, of class Generic_Archive.
//     */
//    @Test
//    public void testGetObjectDir() {
//        System.out.println("getObjectDir");
//        Path dir = null;
//        long id = 0L;
//        long n = 0L;
//        long range = 0L;
//        Generic_Archive instance = null;
//        Path expResult = null;
//        Path result = instance.getObjectDir(dir, id, n, range);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
