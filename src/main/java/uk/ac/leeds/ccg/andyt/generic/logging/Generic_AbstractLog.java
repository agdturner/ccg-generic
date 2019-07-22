/**
 * Copyright 2010 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.logging;

import uk.ac.leeds.ccg.andyt.generic.core.Generic_ErrorAndExceptionHandler;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
//import java.util.logging.LogRecord;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Object;

/**
 * Abstract class to be extended by any class requiring logging.
 */
public abstract class Generic_AbstractLog extends Generic_Object {

    /**
     * For logging
     */
    protected transient FileHandler fh;
    protected transient Logger logger;

    public void init_Logger(Level l, File dir, String classname, String filename) {
        logger = Logger.getLogger(classname);
        try {
            if (fh != null) {
                logger.removeHandler(fh);
            }
            File logDir = new File(dir.getCanonicalPath()
                    + System.getProperty("file.separator") + "logs");
            logDir.mkdirs();
            fh = new FileHandler(logDir.getCanonicalPath()
                    + System.getProperty("file.separator") + filename);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        logger.addHandler(fh);
        logger.setLevel(l);
        //_Logger.setLevel(Level.ALL);
        logger.exiting(classname, "init_Logger(Level,File,String,String)");
    }

    public void init_Logger(Level l, File directory, String filename) {
        logger = Logger.getAnonymousLogger();
        try {
            File logDir = new File(directory.getCanonicalPath()
                    + System.getProperty("file.separator") + "logs");
            logDir.mkdirs();
            fh = new FileHandler(logDir.getCanonicalPath()
                    + System.getProperty("file.separator") + filename);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        logger.addHandler(fh);
        logger.setLevel(l);
        //_Logger.setLevel(Level.ALL);
        logger.exiting(this.getClass().getCanonicalName(),
                "init_Logger(Level,File,String)");
    }

    public void init_Logger(Level l, File dir) {
        String classname = this.getClass().getCanonicalName();
        String filename = classname + ".log";
        init_Logger(l, dir, filename);
    }

    public void log(String s) {
        System.out.println(s);
        logger.log(logger.getLevel(), s);
    }

    public void log(Level l, String s) {
        System.out.println(s);
        logger.log(l, s);
    }
}
