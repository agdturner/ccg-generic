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
package uk.ac.leeds.ccg.generic.io;

import uk.ac.leeds.ccg.generic.io.Generic_Path;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_Files;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Strings;

/**
 * Tests for {@link Generic_Files} class.
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_FilesTest {

    static Generic_Environment env;

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
            env.log("setDir");
            //System.out.println("setDir");
            Generic_Path d = new Generic_Path(
                    Paths.get(env.files.getGeneratedDir().s, "test"));
            Generic_Files instance = new Generic_Files(new Generic_Defaults(d));
            //instance.setDir(d);
            Generic_Path dir = instance.getDir();
            Assertions.assertEquals(d, dir);
            Assertions.assertTrue(Files.exists(d.getPath()));
            Generic_Path genD = new Generic_Path(Paths.get(dir.s,
                    Generic_Strings.s_generated));
            d = instance.getGeneratedDir();
            Assertions.assertEquals(genD, d);
            Assertions.assertTrue(Files.exists(genD.getPath()));
            Generic_Path inputD = new Generic_Path(Paths.get(dir.s,
                    Generic_Strings.s_input));
            d = instance.getInputDir();
            Assertions.assertEquals(inputD, d);
            Assertions.assertTrue(Files.exists(inputD.getPath()));
            Generic_Path outputD = new Generic_Path(Paths.get(dir.s,
                    Generic_Strings.s_output));
            d = instance.getOutputDir();
            Assertions.assertEquals(outputD, d);
            Assertions.assertTrue(Files.exists(outputD.getPath()));
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            env.log(ex.getMessage());
        }
    }
}
