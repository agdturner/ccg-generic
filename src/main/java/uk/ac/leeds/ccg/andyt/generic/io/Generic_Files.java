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
     * The base level directory.
     */
    protected File dir;

    /**
     * The input directory in {@link #dir}.
     */
    protected File inputDir;

    /**
     * The generated directory in {@link #dir}.
     */
    protected File generatedDir;

    /**
     * The output directory in {@link #dir}.
     */
    protected File outputDir;

    /**
     * The log directory in {@link #outputDir}.
     */
    protected File logDir;

    /**
     * Defaults dir to what is returned from
      * {@link Generic_Files#getDefaultDir()}.
     */
    public Generic_Files() {
        this(getDefaultDir());
    }

    /**
     * @param dir What {@link #dir} is set to.
     */
    public Generic_Files(File dir) {
        this.dir = dir;
    }

    /**
     * Sets {@link #dir} to {@code d} and sets
     * {@link #inputDir}, {@link #generatedDir} and {@link #outputDir} to
     * {@code null}.
     *
     * @param d What {@link #dir} is set to.
     */
    public final void setDir(File d) {
        if (!d.exists()) {
            boolean successfulCreation;
            successfulCreation = d.mkdirs();
            if (!successfulCreation) {
                throw new Error("The directory " + d + " was not created in " 
                        + this.getClass().getName() + ".setDir(File)");
            }
        }
        dir = d;
        inputDir = null;
        generatedDir = null;
        outputDir = null;
    }

    /**
     *
     * @return dir
     */
    public File getDir() {
        return dir;
    }

    /**
     * {@code return new File(System.getProperty("user.dir"), "data");}
     *
     * @return A default directory called data in the user.dir.
     */
    public static File getDefaultDir() {
        return new File(System.getProperty("user.dir"), 
                Generic_Strings.s_Generic);
    }

    /**
     *
     * @return InputDir If null then InputDir is set using {@code
     * InputDir = new File(getDir(), Generic_Strings.s_input);}
     */
    public File getInputDir() {
        if (inputDir == null) {
            inputDir = new File(getDir(), Generic_Strings.s_input);
            inputDir.mkdirs();
        }
        return inputDir;
    }

    /**
     *
     * @return GeneratedDir
     */
    public File getGeneratedDir() {
        if (generatedDir == null) {
            generatedDir = new File(getDir(), Generic_Strings.s_generated);
            generatedDir.mkdirs();
        }
        return generatedDir;
    }

    public File getOutputDir() {
        if (outputDir == null) {
            outputDir = new File(getDir(), Generic_Strings.s_output);
            outputDir.mkdirs();
        }
        return outputDir;
    }

    public File getLogDir() {
        if (logDir == null) {
            logDir = new File(getOutputDir(), Generic_Strings.s_log);
        }
        return logDir;
    }
}
