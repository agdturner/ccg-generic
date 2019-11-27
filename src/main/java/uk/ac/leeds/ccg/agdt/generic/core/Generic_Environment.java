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
package uk.ac.leeds.ccg.agdt.generic.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import uk.ac.leeds.ccg.agdt.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.agdt.generic.io.Generic_Files;
import uk.ac.leeds.ccg.agdt.generic.io.Generic_IO;

/**
 * A class for constructing a generic environment object. Normally there is only
 * one such object in a running program. It is to provide access to other
 * objects that are commonly used. The idea is that there only need by the one
 * copy of such objects saving memory, time and confusion.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_Environment {

    /**
     * A sharable instance of {@link Generic_Files}.
     */
    public final Generic_Files files;

    /**
     * A sharable instance of {@link Generic_IO}.
     */
    public final Generic_IO io;

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
     * The default Level for logging.
     */
    private static transient final Level DEFAULT_LEVEL = Level.FINE;

    /**
     * The default range in terms of the maximum number of files or directories
     * to store in a directory.
     */
    private static transient final int DEFAULT_RANGE = 100;

    /**
     * Creates a new instance. The directory is set by default to
     * {@link Generic_Defaults#getDataDir()}.
     *
     * @throws java.io.IOException If a log file was not initialised.
     */
    public Generic_Environment() throws IOException {
        this(new File(Generic_Defaults.getDataDir(), Generic_Strings.s_generic));
    }

    /**
     * Creates a new instance. {@link #level} is defaulted to Level.FINE. See
     * {@link Generic_Environment#Generic_Environment(File,Level)}.
     *
     * @param d The directory that will be set as the data directory.
     * @throws java.io.IOException If a log file was not initialised.
     */
    public Generic_Environment(File d) throws IOException {
        this(d, DEFAULT_LEVEL);
    }

    /**
     * Creates a new instance. {@link #range} is defaulted to 100. See
     * {@link Generic_Environment#Generic_Environment(File,Level,int)}.
     *
     * @param d The directory that will be set as the data directory.
     * @param l What {@link #level} is set to.
     * @throws java.io.IOException If a log file was not initialised.
     */
    public Generic_Environment(File d, Level l) throws IOException {
        this(d, l, DEFAULT_RANGE);
    }

    /**
     * Creates a new instance. {@link #files} is set using
     * {@code new Generic_Files(new Generic_Strings(d)}. See
     * {@link #Generic_Environment(File,Level,int)}.
     *
     * @param d The directory that will be set as the data directory.
     * @param l What {@link #level} is set to.
     * @param r What {@link #range} is set to.
     * @throws java.io.IOException If a log file was not initialised.
     */
    public Generic_Environment(File d, Level l, int r) throws IOException {
        this(new Generic_Files(d), l, r);
    }

    /**
     * Creates a new instance. {@link #level} is defaulted to Level.FINE.
     * {@link #range} is defaulted to 100. {@link #initLog(String)} or
     * {@link #initLog(String,String)} still needs to be called to set up the
     * log.
     *
     * @param f What {@link #files} is set to.
     * @throws java.io.IOException If a log file was not initialised.
     */
    public Generic_Environment(Generic_Files f) throws IOException {
        this(f, DEFAULT_LEVEL, DEFAULT_RANGE);
    }

    /**
     * Creates a new instance. {@link #range} is defaulted to 100. See
     * {@link Generic_Environment#Generic_Environment(Generic_Files,Level,int)}.
     * {@link #initLog(String)} or {@link #initLog(String,String)} still needs
     * to be called to set up the log.
     *
     * @param f What {@link #files} is set to.
     * @param l What {@link #level} is set to.
     * @throws java.io.IOException If a log file was not initialised.
     */
    public Generic_Environment(Generic_Files f, Level l) throws IOException {
        this(f, l, DEFAULT_RANGE);
    }

    /**
     * Creates a new instance: {@link #io} is initialised using
     * {@code new Generic_IO(this)}; {@link #logs} is initialised using
     * {@code new HashMap<>()}; {@link #logNamesInUse} is initialised using
     * {@code new HashSet<>()}. {@link #initLog(String)} or
     * {@link #initLog(String,String)} still needs to be called to set up the
     * log.
     *
     * @param f What {@link #files} is set to.
     * @param l What {@link #level} is set to.
     * @param r What {@link #range} is set to.
     * @throws java.io.IOException If a log file was not initialised.
     */
    public Generic_Environment(Generic_Files f, Level l, int r) throws IOException {
        files = f;
        io = new Generic_IO(this);
        level = l;
        range = r;
        logs = new HashMap<>();
        logNamesInUse = new HashSet<>();
        initLog(this.getClass().getSimpleName());
    }

    /**
     * Initialises a new log with name s and a default file extension
     * ".log.txt".
     *
     * @param s The name of the log.
     * @return The ID of the log initialised.
     * @throws java.io.IOException If a log file was not initialised.
     */
    public final int initLog(String s) throws IOException {
        return initLog(s, "_log.txt");
    }

    /**
     * Initialises a new log with name s.
     *
     * @param s The name of the log.
     * @param e The file extension.
     * @return The ID of the log initialised.
     * @throws java.io.IOException If a log file was not initialised.
     */
    public final int initLog(String s, String e) throws IOException {
        if (logNamesInUse.contains(s)) {
            log("Warning log with name " + s + " is already in use. Another "
                    + "is being set up!", 0, true);
        } else {
            logNamesInUse.add(s);
        }
        int logID = logs.size();
        File d = getLogDir(s);
        PrintWriter pw = io.getPrintWriter(new File(d, s + e), false);
        logs.put(logID, pw);
        log("LoggingLevel = " + level.getName(), true);
        return logID;
    }

    /**
     * Create and return a directory for a log file with name s.
     *
     * @param s The name of the log to be created.
     * @return A new directory for a log. If this is the first log with this
     * name, a new archive is set up. Otherwise an existing archive is used and
     * a new archive leaf is set up in this for use.
     * @throws java.io.IOException If a log file was not initialised.
     */
    protected File getLogDir(String s) throws IOException {
        File dir = new File(files.getLogDir(), s);
        if (Files.exists(dir.toPath())) {
//            File[] files0 = dir.listFiles();
//            if (files0.length == 0) {
//                dir = Generic_IO.initialiseArchive(dir, range);
//            }
//            if (files0.length == 1) {
//                if (files0[0].getName().contains(Generic_Strings.symbol_underscore)) {
//                    dir = files0[0];
//                }
//            }
            dir = io.addToArchive(dir, range);
        } else {
            dir = io.initialiseArchive(dir, range, false);
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

//    /**
//     * @return {@link #files} initialising it first if it is {@code null}.
//     */
//    public Generic_Files getFiles() {
//        if (files == null) {
//            files = new Generic_Files();
//        }
//        return files;
//    }
//
//    /**
//     * Sets {@link #files}.
//     *
//     * @param f What {@link #files} is set to.
//     */
//    public void setFiles(Generic_Files f) {
//        this.files = f;
//    }
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
        PrintWriter pw = logs.get(logID);
        pw.println(s);
        pw.flush();
        if (println) {
            System.out.println(s);
        }
    }

    /**
     * Prepends {@code s} with {@code "<"} and appends {@code s} with
     * {@code ">"} then writes the result to a new line of the log indexed by 0
     * and prints the result to std.out.
     *
     * @param s The message to log.
     */
    public final void logStartTag(String s) {
        log(getStartTag(s), 0, true);
    }

    /**
     * Prepends {@code s} with {@code "<"} and appends {@code s} with
     * {@code ">"} then returns the result.
     *
     * @param s The String to be wrapped as a start tag then logged.
     * @return {@code s} prepended with {@code "<"} and appended with
     * {@code ">"}
     */
    public String getStartTag(String s) {
        return "<" + s + ">";
    }

    /**
     * Prepends {@code s} with {@code "<"} and appends {@code s} with
     * {@code ">"} then writes the result to a new line of the log indexed by 0
     * and prints the result to std.out iff {@code println} is true.
     *
     * @param s The String to be wrapped as a start tag then logged.
     * @param println Iff true then the message is also printed to std.out.
     */
    public final void logStartTag(String s, boolean println) {
        log(getStartTag(s), 0, println);
    }

    /**
     * Prepends {@code s} with {@code "<"} and appends {@code s} with
     * {@code ">"} then writes the result to a new line of the log indexed by
     * {@code logID} and prints the message to std.out.
     *
     * @param s The String to be wrapped as a start tag then logged.
     * @param logID The ID of the log to write to.
     */
    public final void logStartTag(String s, int logID) {
        log(getStartTag(s), logID, true);
    }

    /**
     * Prepends {@code s} with {@code "</"} and appends {@code s} with
     * {@code ">"} then writes the result to a new line of the log indexed by 0
     * and prints the result to std.out.
     *
     * @param s The String to be wrapped as an end tag then logged.
     */
    public final void logEndTag(String s) {
        log(getEndTag(s), 0, true);
    }

    /**
     * Prepends {@code s} with {@code "</"} and appends {@code s} with
     * {@code ">"} then returns the result.
     *
     * @param s The String to be wrapped as an end tag then logged.
     * @return {@code s} prepended with {@code "</"} and appended with
     * {@code ">"}
     */
    public String getEndTag(String s) {
        return "</" + s + ">";
    }

    /**
     * Prepends {@code s} with {@code "</"} and appends {@code s} with
     * {@code ">"} then writes the result to a new line of the log indexed by 0
     * and prints the result to std.out iff {@code println} is true.
     *
     * @param s The String to be wrapped as a start tag then logged.
     * @param println Iff true then the message is also printed to std.out.
     */
    public final void logEndTag(String s, boolean println) {
        log(getEndTag(s), 0, println);
    }

    /**
     * Prepends {@code s} with {@code "</"} and appends {@code s} with
     * {@code ">"} then writes the result to a new line of the log indexed by
     * {@code logID} and prints the message to std.out.
     *
     * @param s The String to be wrapped as a start tag then logged.
     * @param logID The ID of the log to write to.
     */
    public final void logEndTag(String s, int logID) {
        log(getEndTag(s), logID, true);
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
