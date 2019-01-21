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
    protected Generic_Files Files;

    /**
     * A sharable instance of {@link Generic_Strings}.
     */
    protected Generic_Strings Strings;

    /**
     * The logging level.
     */
    public Level LoggingLevel;

    /**
     * The number of directories or files in the archive where the logs are
     * stored.
     */
    protected int Range;

    /**
     * For writing output messages to.
     */
    protected transient final PrintWriter LOG;

    /**
     * Creates a new instance.
     *
     * @param d The directory that will be set as the data directory.
     * @param l What {@link #LoggingLevel} is set to.
     * @param r What {@link #Range} is set to.
     */
    public Generic_Environment(File d, Level l, int r) {
        this(new Generic_Files(new Generic_Strings(), d), l, r);
    }

    /**
     * Creates a new instance.
     *
     * @param f What {@link #Files} is set to.
     * @param level What {@link #LoggingLevel} is set to.
     * @param range What {@link #Range} is set to.
     */
    public Generic_Environment(Generic_Files f, Level level, int range) {
        Strings = f.getStrings();
        Files = f;
        File dir;
        dir = Files.getLogDir();
        if (dir.isDirectory()) {
            dir = Generic_IO.addToArchive(dir, Range);
        } else {
            dir = Generic_IO.initialiseArchive(dir, Range);
        }
        dir.mkdirs();
        LOG = Generic_IO.getPrintWriter(new File(dir, "log.txt"), false);
        log("LoggingLevel = " + LoggingLevel.getName(), true);
    }

    /**
     * Closes Log.
     *
     */
    protected void closeLog() {
        LOG.close();
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
     * @param println Iff true then s is printed to std.out as well as to
     * {@link #LOG}.
     */
    public final void log(String s, boolean println) {
        LOG.println(s);
        LOG.flush();
        if (println) {
            System.out.println(s);
        }
    }
}
