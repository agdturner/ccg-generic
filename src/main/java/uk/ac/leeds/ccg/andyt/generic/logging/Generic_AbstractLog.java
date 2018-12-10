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

/**
 * Abstract class to be extended by any class requiring logging.
 */
public abstract class Generic_AbstractLog {

    /**
     * For logging
     */
    protected transient FileHandler _Logger_FileHandler;
    protected transient Logger _Logger;

    public void init_Logger(
            Level aLevel,
            File directory,
            String classname,
            String filename) {
        _Logger = Logger.getLogger(classname);
        try {
            if (_Logger_FileHandler != null) {
                _Logger.removeHandler(_Logger_FileHandler);
            }
            File logDirectory = new File(
                    directory.getCanonicalPath() +
                    System.getProperty("file.separator") + "logs");
            logDirectory.mkdirs();
            _Logger_FileHandler = new FileHandler(
                    logDirectory.getCanonicalPath() +
                    System.getProperty("file.separator") + filename);
        } catch (IOException aIOException) {
            aIOException.printStackTrace();
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        _Logger.addHandler(_Logger_FileHandler);
        _Logger.setLevel(aLevel);
        //_Logger.setLevel(Level.ALL);
        _Logger.exiting(
                classname,
                "init_Logger(Level,File,String)");
    }

    public void init_Logger(
            Level aLevel,
            File directory,
            String filename) {
        _Logger = Logger.getAnonymousLogger();
        try {
//            if (_Logger_FileHandler != null) {
//                _Logger.removeHandler(_Logger_FileHandler);
//            }
            File logDirectory = new File(
                    directory.getCanonicalPath() +
                    System.getProperty("file.separator") + "logs");
            logDirectory.mkdirs();
            _Logger_FileHandler = new FileHandler(
                    logDirectory.getCanonicalPath() +
                    System.getProperty("file.separator") + filename);
        } catch (IOException aIOException) {
            aIOException.printStackTrace();
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        _Logger.addHandler(_Logger_FileHandler);
        _Logger.setLevel(aLevel);
        //_Logger.setLevel(Level.ALL);
        _Logger.exiting(
                this.getClass().getCanonicalName(),
                "init_Logger(Level,File,String)");
    }

    public void init_Logger(
            Level aLevel,
            File directory) {
        String classname = this.getClass().getCanonicalName();
        String filename = classname + ".log";
        init_Logger(
                aLevel,
                directory,
                filename);
    }

    public void log(
            String aString) {
        System.out.println(aString);
        _Logger.log(
                _Logger.getLevel(),
                aString);
    }

    public void log(
            Level aLevel,
            String aString) {
        System.out.println(aString);
        _Logger.log(
                aLevel,
                aString);
    }
}
