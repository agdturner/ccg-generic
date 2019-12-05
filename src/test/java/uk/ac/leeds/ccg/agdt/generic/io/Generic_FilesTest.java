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
public class Generic_FilesTest {

    static Generic_Environment env;
    int logID;

    public Generic_FilesTest() {
    }

    @BeforeAll
    public static void setUpClass() {
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
     * Test of setDir method, of class Generic_Files.
     */
    @Test
    public void testSetDir() {
        try {
            env.log("setDir", logID);
            //System.out.println("setDir");
            Path d = Paths.get(env.files.getGeneratedDir().toString(), "test");
            Generic_Files instance = new Generic_Files(new Generic_Defaults(d));
            //instance.setDir(d);
            Path dir = instance.getDir();
            Assertions.assertEquals(d, dir);
            Assertions.assertTrue(Files.exists(d));
            Path genD = Paths.get(dir.toString(), Generic_Strings.s_generated);
            d = instance.getGeneratedDir();
            Assertions.assertEquals(genD, d);
            Assertions.assertTrue(Files.exists(genD));
            Path inputD = Paths.get(dir.toString(), Generic_Strings.s_input);
            d = instance.getInputDir();
            Assertions.assertEquals(inputD, d);
            Assertions.assertTrue(Files.exists(inputD));
            Path outputD = Paths.get(dir.toString(), Generic_Strings.s_output);
            d = instance.getOutputDir();
            Assertions.assertEquals(outputD, d);
            Assertions.assertTrue(Files.exists(outputD));
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            env.log(ex.getMessage());
        }
    }

//    /**
//     * Test of getDir method, of class Generic_Files.
//     */
//    @Test
//    public void testGetDir() {
//        try {
//            env.log("getDir", logID);
//            //System.out.println("getDir");
//            Generic_Files files = new Generic_Files(new Generic_Defaults(
//                    env.files.getDir()));
//            Path expResult = files.getDefaultDir();
//            Path result = files.getDir();
//            Assertions.assertEquals(expResult, result);
//        } catch (IOException ex) {
//            ex.printStackTrace(System.err);
//            env.log(ex.getMessage());
//        }
//    }

//    /**
//     * Test of getInputDir method, of class Generic_Files.
//     */
//    @Test
//    public void testGetInputDir() {
//        try {
//            env.log("getInputDir", logID);
//            //System.out.println("getInputDir");
//            Path expResult = Paths.get(env.files.getDefaultDir().toString(),
//                    Generic_Strings.s_input);
//            Path result = env.files.getInputDir();
//            Assertions.assertEquals(expResult, result);
//        } catch (IOException ex) {
//            ex.printStackTrace(System.err);
//            env.log(ex.getMessage());
//        }
//    }
//
//    /**
//     * Test of getGeneratedDir method, of class Generic_Files.
//     */
//    @Test
//    public void testGetGeneratedDir() {
//        try {
//            env.log("getGeneratedDir", logID);
//            //System.out.println("getGeneratedDir");
//            Path expResult = Paths.get(env.files.getDefaultDir().toString(),
//                    Generic_Strings.s_generated);
//            Path result = env.files.getGeneratedDir();
//            Assertions.assertEquals(expResult, result);
//        } catch (IOException ex) {
//            ex.printStackTrace(System.err);
//            env.log(ex.getMessage());
//        }
//
//    }
//
//    /**
//     * Test of getOutputDir method, of class Generic_Files.
//     */
//    @Test
//    public void testGetOutputDir() {
//        try {
//            env.log("getOutputDir", logID);
//            //System.out.println("getOutputDir");
//            Path expResult = Paths.get(env.files.getDefaultDir().toString(),
//                    Generic_Strings.s_output);
//            Path result = env.files.getOutputDir();
//            Assertions.assertEquals(expResult, result);
//        } catch (IOException ex) {
//            ex.printStackTrace(System.err);
//            env.log(ex.getMessage());
//        }
//    }
//
//    /**
//     * Test of getLogDir method, of class Generic_Files.
//     */
//    @Test
//    public void testGetLogDir() {
//        try {
//            env.log("getLogDir", logID);
//            //System.out.println("getLogDir");
//            Path expResult = Paths.get(env.files.getDefaultDir().toString(),
//                    Generic_Strings.s_output, Generic_Strings.s_log);
//            Path result = env.files.getLogDir();
//            Assertions.assertEquals(expResult, result);
//        } catch (IOException ex) {
//            ex.printStackTrace(System.err);
//            env.log(ex.getMessage());
//        }
//    }
}
