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
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.File;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class Generic_Files {

    /**
     * Transient instance of {@link Generic_Strings}.
     */
    protected transient Generic_Strings Strings;

    /**
     * The base level Data directory.
     */
    protected File DataDir;

    /**
     * The input directory in {@link #DataDir}.
     */
    protected File InputDir;

    /**
     * The generated directory in {@link #DataDir}.
     */
    protected File GeneratedDir;

    /**
     * The output directory in {@link #DataDir}.
     */
    protected File OutputDir;

    /**
     * The log directory in {@link #OutputDir}.
     */
    protected File LogDir;

    public Generic_Files() {
        this(new Generic_Strings());
    }
    
    /**
     * Defaults DataDir to a directory {@link Generic_Strings#s_data} in the
     * users current working directory.
     *
     * @param s What {@link Strings} is set to.
     */
    public Generic_Files(Generic_Strings s) {
        Strings = s;
        DataDir = new File(System.getProperty("user.dir"), s.s_data);
    }

    /**
     * @param s What {@link #Strings} is set to.
     * @param dir What {@link #DataDir} is set to.
     */
    public Generic_Files(Generic_Strings s, File dir) {
        Strings = s;
        DataDir = dir;
    }

    /**
     * Sets {@link #DataDir} to {@code d} and sets
     * {@link #InputDir}, {@link #GeneratedDir} and {@link #OutputDir} to
     * {@code null}.
     *
     * @param d What {@link #DataDir} is set to.
     */
    public final void setDataDirectory(File d) {
        if (!d.exists()) {
            boolean successfulCreation;
            successfulCreation = d.mkdirs();
            if (!successfulCreation) {
                throw new Error("The data directory " + d + " was not "
                        + "created in " + this.getClass().getName()
                        + ".setDataDirectory(String)");
            }
        }
        DataDir = d;
        InputDir = null;
        GeneratedDir = null;
        OutputDir = null;
    }

    /**
     * @return {@link #Strings}.
     */
    public Generic_Strings getStrings() {
        return Strings;
    }

    /**
     * Set Strings to s.
     *
     * @param s What {@link #Strings} is set to.
     */
    protected void setStrings(Generic_Strings s) {
        this.Strings = s;
    }

    /**
     *
     * @return DataDir
     */
    public File getDataDir() {
        return DataDir;
    }

    /**
     *
     * @return InputDataDir If null then InputDataDir is set using {@code
     * InputDataDir = new File(getDataDir(), Strings.s_input);}
     */
    public File getInputDataDir() {
        if (InputDir == null) {
            InputDir = new File(getDataDir(), Strings.s_input);
            InputDir.mkdirs();
        }
        return InputDir;
    }

    /**
     *
     * @return GeneratedDataDir
     */
    public File getGeneratedDataDir() {
        if (GeneratedDir == null) {
            GeneratedDir = new File(getDataDir(), Strings.s_generated);
            GeneratedDir.mkdirs();
        }
        return GeneratedDir;
    }

    public File getOutputDataDir() {
        if (OutputDir == null) {
            OutputDir = new File(getDataDir(), Strings.s_output);
            OutputDir.mkdirs();
        }
        return OutputDir;
    }

    public File getLogDir() {
        if (LogDir == null) {
            LogDir = new File(getOutputDataDir(), Strings.s_log);
        }
        return LogDir;
    }
}
