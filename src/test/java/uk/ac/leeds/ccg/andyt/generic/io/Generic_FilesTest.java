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
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class Generic_FilesTest {

    Generic_Environment env;

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
        File dir = Generic_Defaults.getDefaultDir();
        try {
            env = new Generic_Environment(dir);
        } catch (IOException ex) {
            Logger.getLogger(Generic_FilesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        env.files.setEnv(env);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of setDir method, of class Generic_Files.
     */
    @Test
    public void testSetDir() {
        System.out.println("setDir");
        File d = new File(System.getProperty("user.dir"),
                Generic_Strings.s_data + "test");
        Generic_Files instance = new Generic_Files();
        instance.setEnv(env);
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
    }

    /**
     * Test of getDir method, of class Generic_Files.
     */
    @Test
    public void testGetDir() {
        System.out.println("getDir");
        Generic_Files instance = new Generic_Files();
        File expResult = new File(System.getProperty("user.dir"),
                Generic_Strings.s_data);
        File result = instance.getDir();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getDefaultDir method, of class Generic_Files.
     */
    @Test
    public void testGetDefaultDir() {
        System.out.println("getDefaultDir");
        File expResult = new File(System.getProperty("user.dir"),
                Generic_Strings.s_data);
        File result = Generic_Defaults.getDefaultDir();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getInputDir method, of class Generic_Files.
     */
    @Test
    public void testGetInputDir() {
        System.out.println("getInputDir");
        Generic_Files instance = new Generic_Files();
        File expResult = new File(System.getProperty("user.dir"),
                Generic_Strings.s_data);
        expResult = new File(expResult, Generic_Strings.s_input);
        File result = instance.getInputDir();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getGeneratedDir method, of class Generic_Files.
     */
    @Test
    public void testGetGeneratedDir() {
        System.out.println("getGeneratedDir");
        Generic_Files instance = new Generic_Files();
        File expResult = new File(System.getProperty("user.dir"),
                Generic_Strings.s_data);
        expResult = new File(expResult, Generic_Strings.s_generated);
        File result = instance.getGeneratedDir();
       Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getOutputDir method, of class Generic_Files.
     */
    @Test
    public void testGetOutputDir() {
        System.out.println("getOutputDir");
        Generic_Files instance = new Generic_Files();
        File expResult = new File(System.getProperty("user.dir"),
                Generic_Strings.s_data);
        expResult = new File(expResult, Generic_Strings.s_output);
        File result = instance.getOutputDir();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getLogDir method, of class Generic_Files.
     */
    @Test
    public void testGetLogDir() {
        System.out.println("getLogDir");
        Generic_Files instance = new Generic_Files();
        File expResult = new File(System.getProperty("user.dir"),
                Generic_Strings.s_data);
        expResult = new File(expResult, Generic_Strings.s_output);
        expResult = new File(expResult, Generic_Strings.s_log);
        File result = instance.getLogDir();
        Assertions.assertEquals(expResult, result);
    }
}
