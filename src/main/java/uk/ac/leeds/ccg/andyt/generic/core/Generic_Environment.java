/*
 * Copyright (C) 2017 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_Files;

/**
 * A class for constructing a generic environment object. Normally there is only
 * one such object in a running program. It is used to provide access to objects
 * that are commonly wanted and used. The idea is that there can be one main
 * copy of such objects that are shared saving memory.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_Environment {

    /**
     * A sharable instance of {@link Generic_Files}.
     */
    protected Generic_Files Files;

    /**
     * A sharable instance of {@link Generic_Strings}.
     */
    protected Generic_Strings Strings;

    /**
     * Logging levels.
     */
    public static final int DEBUG_Level_FINEST = 0;
    public static final int DEBUG_Level_FINE = 1;
    public static final int DEBUG_Level_NORMAL = 2;
    
    /**
     * For writing output messages to.
     */
    private PrintWriter PrintWriterOut;

    /**
     * For writing error messages to.
     */
    private PrintWriter PrintWriterErr;
    
    /**
     * Creates a new instance.
     */
    protected Generic_Environment() {
    }

    /**
     * Creates a new instance.
     *
     * @param dataDir 
     */
    public Generic_Environment(File dataDir) {
        Strings = new Generic_Strings();
        Files = new Generic_Files(Strings, dataDir);
        init();
    }

    protected final void init(){
        File outDir = Files.getOutputDataDir();
        File f;
        f = new File(outDir, "Out.txt");
        try {
            PrintWriterOut = new PrintWriter(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Generic_Environment.class.getName()).log(Level.SEVERE, null, ex);
        }
        f = new File(outDir, "Err.txt");
        try {
            PrintWriterErr = new PrintWriter(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Generic_Environment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a new instance.
     *
     * @param files What {@link #Files} is set to.
     * @param s What {@link #Strings} to set to.
     */
    public Generic_Environment(Generic_Files files, Generic_Strings s) {
        Strings = s;
        Files = files;
        init();
    }

    /**
     * If {@link #Files} is <code>null</code> then it is initialised via the
     * {@link Generic_Files#Generic_Files()}.
     *
     * @return {@link #Files}
     */
    public Generic_Files getFiles() {
        if (Files == null) {
            Files = new Generic_Files(getStrings());
        }
        return Files;
    }

    /**
     * If {@link #Strings} is <code>null</code> then it is initialised using
     * {@link Generic_Strings#Generic_Strings()}.
     *
     * @return {@link #Strings}
     */
    public Generic_Strings getStrings() {
        if (Strings == null) {
            Strings = new Generic_Strings();
        }
        return Strings;
    }

    /**
     * Writes s to a new line of the output log and also prints it to std.out.
     *
     * @param s
     * @param println
     */
    public void logO(String s, boolean println) {
        if (PrintWriterOut != null) {
            PrintWriterOut.println(s);
        }
        if (println) {
            System.out.println(s);
        }
    }

    /**
     * Writes s to a new line of the error log and also prints it to std.err.
     *
     * @param s
     */
    public void logE(String s) {
        if (PrintWriterErr != null) {
            PrintWriterErr.println(s);
        }
        System.err.println(s);
    }
    
    /**
     * Writes s to a new line of the output log and error log and also prints it
     * to std.out.
     *
     * @param s
     */
    public void logEO(String s) {
        logO(s, false);
        logE(s);
    }

    /**
     * Writes {@code e.getStackTrace()} to the error log and also prints it to
     * std.err.
     *
     * @param e
     */
    public void logE(Exception e) {
        StackTraceElement[] st;
        st = e.getStackTrace();
        for (StackTraceElement st1 : st) {
            logE(st1.toString());
        }
    }

    /**
     * Writes e StackTrace to the error log and also prints it to std.err.
     *
     * @param e
     */
    public void logE(Error e) {
        StackTraceElement[] st;
        st = e.getStackTrace();
        for (StackTraceElement st1 : st) {
            logE(st1.toString());
        }
    }
    
}
