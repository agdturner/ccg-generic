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
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_Files;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;

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
    protected Generic_Files files;

    /**
     * A sharable instance of {@link Generic_Strings}.
     */
    protected Generic_Strings strings;

    /**
     * The logging level.
     */
    public Level level;

    /**
     * The number of directories or files in the archive where the logs are
     * stored.
     */
    protected int range;

    /**
     * A store of logs for writing output messages to. The keys are log IDs and
     * the values are PrintWriters.
     */
    protected transient final HashMap<Integer, PrintWriter> logs;

    /**
     * A lookup for checking if there is already a log with a given name.
     */
    protected transient final HashSet<String> logNamesInUse;

    /**
     * Creates a new instance. {@link #level} is defaulted to Level.FINE.
     * {@link #range} is defaulted to 100.
     *
     * @param d The directory that will be set as the data directory.
     */
    public Generic_Environment(File d) {
        this(new Generic_Files(new Generic_Strings(), d), Level.FINE, 100);
    }

    /**
     * Creates a new instance. {@link #range} is defaulted to 100.
     *
     * @param d The directory that will be set as the data directory.
     * @param l What {@link #level} is set to.
     */
    public Generic_Environment(File d, Level l) {
        this(new Generic_Files(new Generic_Strings(), d), l, 100);
    }

    /**
     * Creates a new instance.
     *
     * @param d The directory that will be set as the data directory.
     * @param l What {@link #level} is set to.
     * @param r What {@link #range} is set to.
     */
    public Generic_Environment(File d, Level l, int r) {
        this(new Generic_Files(new Generic_Strings(), d), l, r);
    }

    /**
     * Creates a new instance. {@link #level} is defaulted to Level.FINE.
     * {@link #range} is defaulted to 100.
     *
     * @param f What {@link #files} is set to.
     */
    public Generic_Environment(Generic_Files f) {
        this(f, Level.FINE, 100);
    }
    
    /**
     * Creates a new instance.
     *
     * @param f What {@link #files} is set to.
     * @param l What {@link #level} is set to.
     * @param r What {@link #range} is set to.
     */
    public Generic_Environment(Generic_Files f, Level l, int r) {
        strings = f.getStrings();
        files = f;
        level = l;
        range = r;
        logs = new HashMap<>();
        logNamesInUse = new HashSet<>();
        initLog("Main");
        log("LoggingLevel = " + level.getName(), true);
    }

    /**
     * Initialises a new log with name s and a default file extension ".txt".
     *
     * @param s The name of the log.
     * @return The ID of the log initialised.
     */
    public final int initLog(String s) {
        return initLog(s, ".txt");
    }

    /**
     * Initialises a new log with name s.
     *
     * @param s The name of the log.
     * @param e The file extension.
     * @return The ID of the log initialised.
     */
    public final int initLog(String s, String e) {
        if (logNamesInUse.contains(s)) {
            log("Warning log with name " + s + " is already in use. Another "
                    + "is being set up!", 0, true);
        } else {
            logNamesInUse.add(s);
        }
        int logID = logs.size();
        File dir;
        dir = getLogDir(s);
        PrintWriter pw;
        pw = Generic_IO.getPrintWriter(new File(dir, s + e), false);
        logs.put(logID, pw);
        return logID;
    }

    /**
     * Create and return a directory for a log file with name s.
     *
     * @param s The name of the log to be created.
     * @return A new directory for a log. If this is the first log with this
     * name, a new archive is set up. Otherwise an existing archive is used and
     * a new archive leaf is set up in this for use.
     */
    protected File getLogDir(String s) {
        File dir;
        dir = new File(files.getLogDir(), s);
        if (java.nio.file.Files.exists(dir.toPath())) {
            dir = Generic_IO.addToArchive(dir, range);
        } else {
            dir = Generic_IO.initialiseArchive(dir, range);
        }
        dir.mkdirs();
        return dir;
    }

    /**
     * Closes Logs.
     */
    protected void closeLogs() {
        logs.values().stream().forEach(pw -> {
            pw.close();
        });
    }

    /**
     * Closes Log with logID.
     *
     * @param logID The ID of the log to be closed.
     */
    public void closeLog(int logID) {
        logs.get(logID).close();
    }

    /**
     * @return {@link #files} initialising it first if it is {@code null}.
     */
    public Generic_Files getFiles() {
        if (files == null) {
            files = new Generic_Files(getStrings());
        }
        return files;
    }

    /**
     * @return {@link #strings} initialising it first if it is {@code null}.
     */
    public Generic_Strings getStrings() {
        if (strings == null) {
            strings = new Generic_Strings();
        }
        return strings;
    }

    /**
     * Writes s to a new line of the log indexed by 0 and prints s to std.out.
     *
     * @param s The message to log.
     */
    public final void log(String s) {
        log(s, 0, true);
    }

    /**
     * Writes s to a new line of the log indexed by 0 and prints it to std.out
     * if println is true.
     *
     * @param s The message to log.
     * @param println Iff true then s is also printed to std.out.
     */
    public final void log(String s, boolean println) {
        log(s, 0, println);
    }

    /**
     * Writes s to a new line of log with ID logID and prints it to std.out.
     *
     * @param s The message to log.
     * @param logID The ID of the log to write to.
     */
    public final void log(String s, int logID) {
        log(s, logID, true);
    }

    /**
     * Writes s to a new line of log with ID logID and prints it to std.out iff
     * println is true.
     *
     * @param s The message to log.
     * @param logID The ID of the log to write to.
     * @param println Iff true then s is also printed to std.out.
     */
    public final void log(String s, int logID, boolean println) {
        PrintWriter pw;
        pw = logs.get(logID);
        pw.println(s);
        pw.flush();
        if (println) {
            System.out.println(s);
        }
    }

    /**
     * For writing lines to a log file with logID = 0.
     *
     * @param lines The lines to write.
     */
    public void log(Collection<String> lines) {
        log(lines, 0, true);
    }
    
    /**
     * For writing lines to a log file.
     *
     * @param lines The lines to write.
     * @param logID The ID of the log to write to.
     * @param println Iff true then lines are also printed to std.out.
     */
    public void log(Collection<String> lines, int logID, boolean println) {
        Iterator<String> iteS;
        iteS = lines.iterator();
        while (iteS.hasNext()) {
            log(iteS.next(), logID);
        }
    }

}
