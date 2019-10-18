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
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Object;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class Generic_IOTest {

    Generic_Environment env;

    public Generic_IOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        File dir = Generic_Defaults.getDefaultDir();
        try {
            env = new Generic_Environment(dir);
        } catch (IOException ex) {
            Logger.getLogger(Generic_IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        env.files.setEnv(env);
    }

    @AfterEach
    public void tearDown() {
    }

//    /**
//     * Test of recursiveFileList method, of class Generic_IO.
//     */
//    @Test
//    public void testRecursiveFileList_File() {
//        System.out.println("recursiveFileList");
//        File file = null;
//        Generic_IO instance = null;
//        TreeSet<String> expResult = null;
//        TreeSet<String> result = instance.recursiveFileList(file);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of recursiveFileList method, of class Generic_IO.
//     */
//    @Test
//    public void testRecursiveFileList_File_int() {
//        System.out.println("recursiveFileList");
//        File file = null;
//        int depth = 0;
//        Generic_IO instance = null;
//        TreeSet<String> expResult = null;
//        TreeSet<String> result = instance.recursiveFileList(file, depth);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of writeObject method, of class Generic_IO.
     */
    @Test
    public void testWriteObject_Object_File() {
        System.out.println("writeObject");
        Generic_Object o = env.files;
        String prefix = "Generic_Files";
        String suffix = Generic_Strings.symbol_dot + Generic_Strings.s_dat;
        File f = null;
        try {
            f = getTestFile(prefix, suffix);
        } catch (IOException ex) {
            Logger.getLogger(Generic_IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        env.log("Test file for env.files " + f);
        Generic_IO instance = env.io;
        instance.writeObject(o, f);
        // Make sure it is a new file
        f = getNewTestFile(prefix, suffix);
        env.log("Test file for env.files " + f);
        instance.writeObject(o, f);
    }

    public File getNewTestFile(String prefix, String suffix) {
        return new File(env.files.getGeneratedDir(), prefix + suffix);
    }

    public File getTestFile(String prefix, String suffix) throws IOException {
        return env.io.createNewFile(env.files.getGeneratedDir(), prefix, suffix);
    }

//    /**
//     * Test of readObject method, of class Generic_IO.
//     */
//    @Test
//    public void testReadObject() {
//        System.out.println("readObject");
//        File f = null;
//        Generic_IO instance = null;
//        Object expResult = null;
//        Object result = instance.readObject(f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of writeObject method, of class Generic_IO.
//     */
//    @Test
//    public void testWriteObject_3args() {
//        System.out.println("writeObject");
//        Object o = null;
//        File f = null;
//        String name = "";
//        Generic_IO instance = null;
//        instance.writeObject(o, f, name);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of readIntoArrayList_String method, of class Generic_IO.
//     */
//    @Test
//    public void testReadIntoArrayList_String_File() {
//        System.out.println("readIntoArrayList_String");
//        File f = null;
//        Generic_IO instance = null;
//        ArrayList<String> expResult = null;
//        ArrayList<String> result = instance.readIntoArrayList_String(f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of readIntoArrayList_String method, of class Generic_IO.
//     */
//    @Test
//    public void testReadIntoArrayList_String_File_int() {
//        System.out.println("readIntoArrayList_String");
//        File f = null;
//        int n = 0;
//        Generic_IO instance = null;
//        ArrayList<String> expResult = null;
//        ArrayList<String> result = instance.readIntoArrayList_String(f, n);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of readIntoArrayList_String method, of class Generic_IO.
//     */
//    @Test
//    public void testReadIntoArrayList_String_4args() {
//        System.out.println("readIntoArrayList_String");
//        File f = null;
//        int n = 0;
//        int firstLine = 0;
//        int lastLine = 0;
//        Generic_IO instance = null;
//        ArrayList<String> expResult = null;
//        ArrayList<String> result = instance.readIntoArrayList_String(f, n, firstLine, lastLine);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of copyFile method, of class Generic_IO.
//     */
//    @Test
//    public void testCopyFile() {
//        System.out.println("copyFile");
//        File f = null;
//        File d = null;
//        String outputFileName = "";
//        Generic_IO instance = null;
//        instance.copyFile(f, d, outputFileName);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getBufferedInputStream method, of class Generic_IO.
//     */
//    @Test
//    public void testGetBufferedInputStream() {
//        System.out.println("getBufferedInputStream");
//        File f = null;
//        Generic_IO instance = null;
//        BufferedInputStream expResult = null;
//        BufferedInputStream result = instance.getBufferedInputStream(f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFileInputStream method, of class Generic_IO.
//     */
//    @Test
//    public void testGetFileInputStream_File() {
//        System.out.println("getFileInputStream");
//        File f = null;
//        Generic_IO instance = null;
//        FileInputStream expResult = null;
//        FileInputStream result = instance.getFileInputStream(f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFileInputStream method, of class Generic_IO.
//     */
//    @Test
//    public void testGetFileInputStream_File_long() {
//        System.out.println("getFileInputStream");
//        File f = null;
//        long wait = 0L;
//        Generic_IO instance = null;
//        FileInputStream expResult = null;
//        FileInputStream result = instance.getFileInputStream(f, wait);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFileOutputStream method, of class Generic_IO.
//     */
//    @Test
//    public void testGetFileOutputStream() {
//        System.out.println("getFileOutputStream");
//        File f = null;
//        Generic_IO instance = null;
//        FileOutputStream expResult = null;
//        FileOutputStream result = instance.getFileOutputStream(f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getBufferedOutputStream method, of class Generic_IO.
//     */
//    @Test
//    public void testGetBufferedOutputStream() {
//        System.out.println("getBufferedOutputStream");
//        File f = null;
//        Generic_IO instance = null;
//        BufferedOutputStream expResult = null;
//        BufferedOutputStream result = instance.getBufferedOutputStream(f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getBufferedWriter method, of class Generic_IO.
//     */
//    @Test
//    public void testGetBufferedWriter() {
//        System.out.println("getBufferedWriter");
//        File f = null;
//        boolean append = false;
//        Generic_IO instance = null;
//        BufferedWriter expResult = null;
//        BufferedWriter result = instance.getBufferedWriter(f, append);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getObjectInputStream method, of class Generic_IO.
//     */
//    @Test
//    public void testGetObjectInputStream() {
//        System.out.println("getObjectInputStream");
//        File f = null;
//        Generic_IO instance = null;
//        ObjectInputStream expResult = null;
//        ObjectInputStream result = instance.getObjectInputStream(f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getObjectOutputStream method, of class Generic_IO.
//     */
//    @Test
//    public void testGetObjectOutputStream() {
//        System.out.println("getObjectOutputStream");
//        File f = null;
//        Generic_IO instance = null;
//        ObjectOutputStream expResult = null;
//        ObjectOutputStream result = instance.getObjectOutputStream(f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of copy method, of class Generic_IO.
//     */
//    @Test
//    public void testCopy() {
//        System.out.println("copy");
//        File fileOrDirToCopy = null;
//        File dirToCopyTo = null;
//        Generic_IO instance = null;
//        instance.copy(fileOrDirToCopy, dirToCopyTo);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of fileExistsAndCanBeRead method, of class Generic_IO.
//     */
//    @Test
//    public void testFileExistsAndCanBeRead() {
//        System.out.println("fileExistsAndCanBeRead");
//        File file = null;
//        Generic_IO instance = null;
//        boolean expResult = false;
//        boolean result = instance.fileExistsAndCanBeRead(file);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class Generic_IO.
//     */
//    @Test
//    public void testDelete() {
//        System.out.println("delete");
//        File file = null;
//        Generic_IO instance = null;
//        long[] expResult = null;
//        long[] result = instance.delete(file);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getBufferedReader method, of class Generic_IO.
//     */
//    @Test
//    public void testGetBufferedReader_File() {
//        System.out.println("getBufferedReader");
//        File f = null;
//        Generic_IO instance = null;
//        BufferedReader expResult = null;
//        BufferedReader result = instance.getBufferedReader(f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getBufferedReader method, of class Generic_IO.
//     */
//    @Test
//    public void testGetBufferedReader_File_String() throws Exception {
//        System.out.println("getBufferedReader");
//        File f = null;
//        String charsetName = "";
//        Generic_IO instance = null;
//        BufferedReader expResult = null;
//        BufferedReader result = instance.getBufferedReader(f, charsetName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of closeBufferedReader method, of class Generic_IO.
//     */
//    @Test
//    public void testCloseBufferedReader() {
//        System.out.println("closeBufferedReader");
//        BufferedReader br = null;
//        Generic_IO instance = null;
//        instance.closeBufferedReader(br);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of closeAndGetBufferedReader method, of class Generic_IO.
//     */
//    @Test
//    public void testCloseAndGetBufferedReader() {
//        System.out.println("closeAndGetBufferedReader");
//        BufferedReader br = null;
//        File f = null;
//        Generic_IO instance = null;
//        BufferedReader expResult = null;
//        BufferedReader result = instance.closeAndGetBufferedReader(br, f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPrintWriter method, of class Generic_IO.
//     */
//    @Test
//    public void testGetPrintWriter() {
//        System.out.println("getPrintWriter");
//        File f = null;
//        boolean append = false;
//        Generic_IO instance = null;
//        PrintWriter expResult = null;
//        PrintWriter result = instance.getPrintWriter(f, append);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFileDepth method, of class Generic_IO.
//     */
//    @Test
//    public void testGetFileDepth_File() {
//        System.out.println("getFileDepth");
//        File f = null;
//        Generic_IO instance = null;
//        int expResult = 0;
//        int result = instance.getFileDepth(f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFileDepth method, of class Generic_IO.
//     */
//    @Test
//    public void testGetFileDepth_String() {
//        System.out.println("getFileDepth");
//        String absoluteFilePath = "";
//        Generic_IO instance = null;
//        int expResult = 0;
//        int result = instance.getFileDepth(absoluteFilePath);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRelativeFilePath method, of class Generic_IO.
//     */
//    @Test
//    public void testGetRelativeFilePath_int() {
//        System.out.println("getRelativeFilePath");
//        int depth = 0;
//        Generic_IO instance = null;
//        String expResult = "";
//        String result = instance.getRelativeFilePath(depth);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRelativeFilePath method, of class Generic_IO.
//     */
//    @Test
//    public void testGetRelativeFilePath_int_File() {
//        System.out.println("getRelativeFilePath");
//        int depth = 0;
//        File f = null;
//        Generic_IO instance = null;
//        String expResult = "";
//        String result = instance.getRelativeFilePath(depth, f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRelativeFilePath method, of class Generic_IO.
//     */
//    @Test
//    public void testGetRelativeFilePath_int_String() {
//        System.out.println("getRelativeFilePath");
//        int depth = 0;
//        String path = "";
//        Generic_IO instance = null;
//        String expResult = "";
//        String result = instance.getRelativeFilePath(depth, path);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of skipline method, of class Generic_IO.
//     */
//    @Test
//    public void testSkipline_StreamTokenizer() {
//        System.out.println("skipline");
//        StreamTokenizer st = null;
//        Generic_IO instance = null;
//        instance.skipline(st);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of skipline method, of class Generic_IO.
//     */
//    @Test
//    public void testSkipline_BufferedReader() {
//        System.out.println("skipline");
//        BufferedReader br = null;
//        Generic_IO instance = null;
//        instance.skipline(br);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setStreamTokenizerSyntaxNumbersAsWords1 method, of class Generic_IO.
//     */
//    @Test
//    public void testSetStreamTokenizerSyntaxNumbersAsWords1() {
//        System.out.println("setStreamTokenizerSyntaxNumbersAsWords1");
//        StreamTokenizer st = null;
//        Generic_IO instance = null;
//        instance.setStreamTokenizerSyntaxNumbersAsWords1(st);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setStreamTokenizerSyntax1 method, of class Generic_IO.
//     */
//    @Test
//    public void testSetStreamTokenizerSyntax1() {
//        System.out.println("setStreamTokenizerSyntax1");
//        StreamTokenizer st = null;
//        Generic_IO instance = null;
//        instance.setStreamTokenizerSyntax1(st);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setStreamTokenizerSyntax2 method, of class Generic_IO.
//     */
//    @Test
//    public void testSetStreamTokenizerSyntax2() {
//        System.out.println("setStreamTokenizerSyntax2");
//        StreamTokenizer st = null;
//        Generic_IO instance = null;
//        instance.setStreamTokenizerSyntax2(st);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setStreamTokenizerSyntax3 method, of class Generic_IO.
//     */
//    @Test
//    public void testSetStreamTokenizerSyntax3() {
//        System.out.println("setStreamTokenizerSyntax3");
//        StreamTokenizer st = null;
//        Generic_IO instance = null;
//        instance.setStreamTokenizerSyntax3(st);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setStreamTokenizerSyntax4 method, of class Generic_IO.
//     */
//    @Test
//    public void testSetStreamTokenizerSyntax4() {
//        System.out.println("setStreamTokenizerSyntax4");
//        StreamTokenizer st = null;
//        Generic_IO instance = null;
//        instance.setStreamTokenizerSyntax4(st);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setStreamTokenizerSyntax5 method, of class Generic_IO.
//     */
//    @Test
//    public void testSetStreamTokenizerSyntax5() {
//        System.out.println("setStreamTokenizerSyntax5");
//        StreamTokenizer st = null;
//        Generic_IO instance = null;
//        instance.setStreamTokenizerSyntax5(st);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setStreamTokenizerSyntax6 method, of class Generic_IO.
//     */
//    @Test
//    public void testSetStreamTokenizerSyntax6() {
//        System.out.println("setStreamTokenizerSyntax6");
//        StreamTokenizer st = null;
//        Generic_IO instance = null;
//        instance.setStreamTokenizerSyntax6(st);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setStreamTokenizerSyntax7 method, of class Generic_IO.
//     */
//    @Test
//    public void testSetStreamTokenizerSyntax7() {
//        System.out.println("setStreamTokenizerSyntax7");
//        StreamTokenizer st = null;
//        Generic_IO instance = null;
//        instance.setStreamTokenizerSyntax7(st);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getObjectDir method, of class Generic_IO.
//     */
//    @Test
//    public void testGetObjectDir() {
//        System.out.println("getObjectDir");
//        File dir = null;
//        long id = 0L;
//        long n = 0L;
//        long range = 0L;
//        Generic_IO instance = null;
//        File expResult = null;
//        File result = instance.getObjectDir(dir, id, n, range);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of initialiseArchive method, of class Generic_IO.
     */
    @Test
    public void testInitialiseArchive_File_long() {
        System.out.println("initialiseArchive");
        File dir = new File(env.files.getGeneratedDir(), "testArchive");
        long range = 10L;
        File expResult;
        if (dir.exists()) {
            expResult = env.io.getArchiveHighestLeafFile(dir);
            /**
             * Let us assume that dir is an archive directory. So the archive
             * already exists and an attempt to initialise an archive using
             * exists = false should result in an IOException.
             */
            Assertions.assertThrows(IOException.class, () -> {
                env.io.initialiseArchive(dir, range, false);
            });
        } else {
            /**
             * If the archive does not already exist then initialise it.
             */
            try {
                env.io.initialiseArchive(dir, range, false);
            } catch (IOException ex) {
                Logger.getLogger(Generic_IOTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            expResult = env.io.getArchiveHighestLeafFile(dir);
        }
        /**
         * So the archive should exist now and if we try to initialise with
         * exists = false then we expect an IOException. This is included here
         * for completeness.
         */
        Assertions.assertThrows(IOException.class, () -> {
            File result = env.io.initialiseArchive(dir, range, false);
        });
        /**
         * Test initialising an existing archive.
         */
        try {
            File result = env.io.initialiseArchive(dir, range, true);
            Assertions.assertEquals(result, expResult);
        } catch (IOException ex) {
            Logger.getLogger(Generic_IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**
         * Add some new leaf files to the archive and test again.
         */
        try {
            for (int i = 0; i < range; i++) {
                for (int j = 0; j < range; j++) {
                    for (int k = 0; k < range; k++) {
                        for (int l = 0; l < range; l++) {
                            env.io.addToArchive(dir, range);
                        }
                    }
                    //env.io.testArchiveIntegrity(dir);
                }
                env.io.testArchiveIntegrity(dir);
            }
        } catch (IOException ex) {
            Logger.getLogger(Generic_IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        expResult = env.io.getArchiveHighestLeafFile(dir);
        try {
            File result = env.io.initialiseArchive(dir, range, true);
            Assertions.assertEquals(result, expResult);
        } catch (IOException ex) {
            Logger.getLogger(Generic_IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    /**
//     * Test of initialiseArchive method, of class Generic_IO.
//     */
//    @Test
//    public void testInitialiseArchive_3args() throws Exception {
//        System.out.println("initialiseArchive");
//        File dir = new File(env.files.getGeneratedDir(), "testArchive");
//        long range = 0L;
//        long n = 0L;
//        Generic_IO instance = null;
//        instance.initialiseArchive(dir, range, n);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of initialiseArchiveAndReturnFileMap method, of class Generic_IO.
//     */
//    @Test
//    public void testInitialiseArchiveAndReturnFileMap() throws Exception {
//        System.out.println("initialiseArchiveAndReturnFileMap");
//        File dir = null;
//        long range = 0L;
//        long n = 0L;
//        Generic_IO instance = null;
//        TreeMap<Long, File> expResult = null;
//        TreeMap<Long, File> result = instance.initialiseArchiveAndReturnFileMap(dir, range, n);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    /**
//     * Test of getArchiveHighestLeaf method, of class Generic_IO.
//     */
//    @Test
//    public void testGetArchiveHighestLeaf() {
//        System.out.println("getArchiveHighestLeaf");
//        File dir = null;
//        Generic_IO instance = null;
//        long expResult = 0L;
//        long result = instance.getArchiveHighestLeaf(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArchiveRange method, of class Generic_IO.
//     */
//    @Test
//    public void testGetArchiveRange() {
//        System.out.println("getArchiveRange");
//        File dir = null;
//        Generic_IO instance = null;
//        long expResult = 0L;
//        long result = instance.getArchiveRange(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArchiveLeafFilesSet method, of class Generic_IO.
//     */
//    @Test
//    public void testGetArchiveLeafFilesSet() {
//        System.out.println("getArchiveLeafFilesSet");
//        File dir = null;
//        Generic_IO instance = null;
//        HashSet<File> expResult = null;
//        HashSet<File> result = instance.getArchiveLeafFilesSet(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArchiveLeafFilesMap method, of class Generic_IO.
//     */
//    @Test
//    public void testGetArchiveLeafFilesMap_File() {
//        System.out.println("getArchiveLeafFilesMap");
//        File dir = null;
//        Generic_IO instance = null;
//        TreeMap<Long, File> expResult = null;
//        TreeMap<Long, File> result = instance.getArchiveLeafFilesMap(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArchiveLeafFilesMap method, of class Generic_IO.
//     */
//    @Test
//    public void testGetArchiveLeafFilesMap_3args() {
//        System.out.println("getArchiveLeafFilesMap");
//        File dir = null;
//        long minID = 0L;
//        long maxID = 0L;
//        Generic_IO instance = null;
//        TreeMap<Long, File> expResult = null;
//        TreeMap<Long, File> result = instance.getArchiveLeafFilesMap(dir, minID, maxID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArchiveHighestLeafFile method, of class Generic_IO.
//     */
//    @Test
//    public void testGetArchiveHighestLeafFile() {
//        System.out.println("getArchiveHighestLeafFile");
//        File dir = null;
//        Generic_IO instance = null;
//        File expResult = null;
//        File result = instance.getArchiveHighestLeafFile(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of growArchiveBase method, of class Generic_IO.
//     */
//    @Test
//    public void testGrowArchiveBase_File_long() {
//        System.out.println("growArchiveBase");
//        File dir = null;
//        long range = 0L;
//        Generic_IO instance = null;
//        File expResult = null;
//        File result = instance.growArchiveBase(dir, range);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of growArchiveBase method, of class Generic_IO.
//     */
//    @Test
//    public void testGrowArchiveBase_3args() {
//        System.out.println("growArchiveBase");
//        File dir = null;
//        long range = 0L;
//        long next_ID = 0L;
//        Generic_IO instance = null;
//        File expResult = null;
//        File result = instance.growArchiveBase(dir, range, next_ID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of move method, of class Generic_IO.
//     */
//    @Test
//    public void testMove() {
//        System.out.println("move");
//        File o = null;
//        File d = null;
//        Generic_IO instance = null;
//        instance.move(o, d);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToArchive method, of class Generic_IO.
//     */
//    @Test
//    public void testAddToArchive_File_long() {
//        System.out.println("addToArchive");
//        File dir = null;
//        long range = 0L;
//        Generic_IO instance = null;
//        File expResult = null;
//        File result = instance.addToArchive(dir, range);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addToArchive method, of class Generic_IO.
//     */
//    @Test
//    public void testAddToArchive_3args() {
//        System.out.println("addToArchive");
//        File dir = null;
//        long range = 0L;
//        long next_ID = 0L;
//        Generic_IO instance = null;
//        File expResult = null;
//        File result = instance.addToArchive(dir, range, next_ID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFileThatExists method, of class Generic_IO.
//     */
//    @Test
//    public void testGetFileThatExists() throws Exception {
//        System.out.println("getFileThatExists");
//        File dir = null;
//        String filename = "";
//        Generic_IO instance = null;
//        File expResult = null;
//        File result = instance.getFileThatExists(dir, filename);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFilename method, of class Generic_IO.
//     */
//    @Test
//    public void testGetFilename() {
//        System.out.println("getFilename");
//        File dir = null;
//        File f = null;
//        Generic_IO instance = null;
//        String expResult = "";
//        String result = instance.getFilename(dir, f);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNumericallyOrderedFiles method, of class Generic_IO.
//     */
//    @Test
//    public void testGetNumericallyOrderedFiles_FileArr() {
//        System.out.println("getNumericallyOrderedFiles");
//        File[] files = null;
//        Generic_IO instance = null;
//        TreeMap<Long, File> expResult = null;
//        TreeMap<Long, File> result = instance.getNumericallyOrderedFiles(files);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNumericallyOrderedFiles2 method, of class Generic_IO.
//     */
//    @Test
//    public void testGetNumericallyOrderedFiles2() {
//        System.out.println("getNumericallyOrderedFiles2");
//        File[] files = null;
//        Generic_IO instance = null;
//        TreeMap<Long, File> expResult = null;
//        TreeMap<Long, File> result = instance.getNumericallyOrderedFiles2(files);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNumericallyOrderedFiles method, of class Generic_IO.
//     */
//    @Test
//    public void testGetNumericallyOrderedFiles_File() {
//        System.out.println("getNumericallyOrderedFiles");
//        File dir = null;
//        Generic_IO instance = null;
//        HashMap<Integer, String> expResult = null;
//        HashMap<Integer, String> result = instance.getNumericallyOrderedFiles(dir);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getFilePathLength method, of class Generic_IO.
     */
    @Test
    public void testGetFilePathLength_File() {
        System.out.println("getFilePathLength");
        File f = env.files.getGeneratedDir();
        Generic_IO instance = env.io;
        int limit = 100;
        int result = instance.getFilePathLength(f);
        System.out.println(result);
        Assertions.assertTrue(result < limit);
    }

    /**
     * Test of getNumericallyOrderedFiles method, of class Generic_IO.
     */
    @Test
    public void testCreateNewFile() {
        System.out.println("createNewFile");
        File dir = env.files.getGeneratedDir();
        String prefix = "prefix";
        String suffix = "suffix";
        Generic_IO instance = env.io;
        File f = null;
        try {
            f = instance.createNewFile(dir, prefix, suffix);
        } catch (IOException ex) {
            Logger.getLogger(Generic_IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Assertions.assertTrue(f.exists());
    }

}
