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
package uk.ac.leeds.ccg.agdt.generic.io;

import uk.ac.leeds.ccg.agdt.generic.io.Generic_Files;
import java.io.File;
import java.io.IOException;
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

    Generic_Environment env;
    int logID;

    public Generic_FilesTest() {
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
            Logger.getLogger(Generic_FilesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            File d = new File(env.files.getGeneratedDir(), "test");
            Generic_Files instance = new Generic_Files(d);
            instance.setDir(d);
            File dir = instance.getDir();
            Assertions.assertEquals(d, dir);
            Assertions.assertTrue(d.exists());
            File genD = new File(dir, Generic_Strings.s_generated);
            d = instance.getGeneratedDir();
            Assertions.assertEquals(genD, d);
            Assertions.assertTrue(genD.exists());
            File inputD = new File(dir, Generic_Strings.s_input);
            d = instance.getInputDir();
            Assertions.assertEquals(inputD, d);
            Assertions.assertTrue(inputD.exists());
            File outputD = new File(dir, Generic_Strings.s_output);
            d = instance.getOutputDir();
            Assertions.assertEquals(outputD, d);
            Assertions.assertTrue(outputD.exists());
        } catch (IOException ex) {
            Logger.getLogger(Generic_FilesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getDir method, of class Generic_Files.
     */
    @Test
    public void testGetDir() {
        try {
            env.log("getDir", logID);
            //System.out.println("getDir");
            Generic_Files instance = new Generic_Files(env.files.getDir());
            File expResult = Generic_Files.getDefaultDir();
            File result = instance.getDir();
            Assertions.assertEquals(expResult, result);
        } catch (IOException ex) {
            Logger.getLogger(Generic_FilesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getInputDir method, of class Generic_Files.
     */
    @Test
    public void testGetInputDir() {
        env.log("getInputDir", logID);
        //System.out.println("getInputDir");
        File expResult = Generic_Files.getDefaultDir();
        expResult = new File(expResult, Generic_Strings.s_input);
        File result = env.files.getInputDir();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getGeneratedDir method, of class Generic_Files.
     */
    @Test
    public void testGetGeneratedDir() {
        env.log("getGeneratedDir", logID);
        //System.out.println("getGeneratedDir");
        File expResult = Generic_Files.getDefaultDir();
        expResult = new File(expResult, Generic_Strings.s_generated);
        File result = env.files.getGeneratedDir();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getOutputDir method, of class Generic_Files.
     */
    @Test
    public void testGetOutputDir() {
        env.log("getOutputDir", logID);
        //System.out.println("getOutputDir");
        File expResult = Generic_Files.getDefaultDir();
        expResult = new File(expResult, Generic_Strings.s_output);
        File result = env.files.getOutputDir();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getLogDir method, of class Generic_Files.
     */
    @Test
    public void testGetLogDir() {
        env.log("getLogDir", logID);
        //System.out.println("getLogDir");
        File expResult = Generic_Files.getDefaultDir();
        expResult = new File(expResult, Generic_Strings.s_output);
        expResult = new File(expResult, Generic_Strings.s_log);
        File result = env.files.getLogDir();
        Assertions.assertEquals(expResult, result);
    }
}
