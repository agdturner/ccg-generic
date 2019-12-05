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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    
    /**
     * Test of addToArchive method, of class Generic_FileStore.
     */
    @Test
    public void test() {
        try {
            System.out.println("test");
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
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
