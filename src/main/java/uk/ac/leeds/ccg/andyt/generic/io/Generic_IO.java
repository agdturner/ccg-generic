/**
 * Copyright 2010 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_ErrorAndExceptionHandler;

/**
 * Class of static methods for helping with reading and writing (primarily)
 * to/from file.
 */
public class Generic_IO {

    public static final String CLASSNAME = "Generic_IO";

    /**
     * Creates a new instance of ObjectIO
     */
    public Generic_IO() {
    }

    /**
     * Write object to file
     *
     * @param o
     * @param f
     */
    public static void writeObject(Object o, File f) {
        try {
            f.getParentFile().mkdirs();
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(f)));
            oos.writeUnshared(o);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
    }

    /**
     * Read Object from File
     *
     * @param f
     * @return
     */
    public static Object readObject(File f) {
        Object result = null;
        if (f.length() != 0) {
            try {
                ObjectInputStream ois;
                ois = new ObjectInputStream(
                        new BufferedInputStream(
                                new FileInputStream(f)));
                result = ois.readUnshared();
                ois.close();
            } catch (IOException e) {
                System.err.print(e.getMessage());
                e.printStackTrace(System.err);
                System.exit(Generic_ErrorAndExceptionHandler.IOException);
            } catch (ClassNotFoundException e) {
                System.err.print(e.getMessage());
                e.printStackTrace(System.err);
                System.exit(Generic_ErrorAndExceptionHandler.ClassNotFoundException);
            }
        }
        return result;
    }
}
