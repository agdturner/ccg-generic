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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class Generic_IOTest {

    Generic_Environment env;
    int logID;

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
        try {
            Generic_Files files = new Generic_Files(new Generic_Defaults());
            env = new Generic_Environment(files);
            logID = env.initLog(this.getClass().getSimpleName());
        } catch (IOException ex) {
            Logger.getLogger(Generic_FilesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            env.log("writeObject", logID);
            String prefix = "Generic_Files";
            String suffix = Generic_Strings.symbol_dot + Generic_Strings.s_dat;
            Path f = null;
            try {
                f = getTestFile(prefix, suffix);
            } catch (IOException ex) {
                Logger.getLogger(Generic_IOTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            env.log("Test file for env.files " + f);
            Generic_IO instance = env.io;
            instance.writeObject(env.files, f);
            // Make sure it is a new file
            f = getNewTestFile(prefix, suffix);
            env.log("Test file for env.files " + f);
            instance.writeObject(env.files, f);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            env.log(ex.getMessage());
        }
    }

    public Path getNewTestFile(String prefix, String suffix) throws IOException {
        return Paths.get(env.files.getGeneratedDir().toString(), prefix + suffix);
    }

    public Path getTestFile(String prefix, String suffix) throws IOException {
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
        try {
            env.log("initialiseArchive", logID);
            //System.out.println("initialiseArchive");
            Path dir = Paths.get(env.files.getGeneratedDir().toString(),
                    "testArchive");
            long range = 10L;
            Path expResult;
            if (Files.exists(dir)) {
                expResult = env.io.getArchiveHighestLeafFile(dir);
                /**
                 * Let us assume that dir is an archive directory. So the
                 * archive already exists and an attempt to initialise an
                 * archive using exists = false should result in an IOException.
                 */
                Assertions.assertThrows(IOException.class, () -> {
                    env.io.initialiseArchive(dir, range, false);
                });
            } else {
                /**
                 * If the archive does not already exist then initialise it.
                 */
                env.io.initialiseArchive(dir, range, false);
                expResult = env.io.getArchiveHighestLeafFile(dir);
            }
            /**
             * So the archive should exist now and if we try to initialise with
             * exists = false then we expect an IOException. This is included
             * here for completeness.
             */
            Assertions.assertThrows(IOException.class, () -> {
                Path result = env.io.initialiseArchive(dir, range, false);
            });
            /**
             * Test initialising an existing archive.
             */
            Path result = env.io.initialiseArchive(dir, range, true);
            Assertions.assertEquals(result, expResult);
            /**
             * Add some new leaf files to the archive and test again.
             */
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
            expResult = env.io.getArchiveHighestLeafFile(dir);
            result = env.io.initialiseArchive(dir, range, true);
            Assertions.assertEquals(result, expResult);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            env.log(ex.getMessage());
        }
    }
    
    /**
     * Test of getFilePathLength method, of class Generic_IO.
     *
     * @throws java.io.IOException If encountered.
     */
    @Test
    public void testGetFilePathLength_File() throws IOException {
        env.log("getFilePathLength", logID);
        //System.out.println("getFilePathLength");
        Path f = env.files.getGeneratedDir();
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
        env.log("createNewFile", logID);
        try {
            Path dir = env.files.getGeneratedDir();
            String prefix = "test";
            String suffix = ".dat";
            Generic_IO instance = env.io;
            Path f = instance.createNewFile(dir, prefix, suffix);
            Assertions.assertTrue(Files.exists(f));
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            env.log(ex.getMessage());
        }
    }

}
