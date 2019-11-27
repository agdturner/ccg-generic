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
package uk.ac.leeds.ccg.agdt.generic.io;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class Generic_Files implements Serializable {

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
     * @param d What {@link #dir} is set to.
     * @throws java.io.IOException If the directory
     */
    public Generic_Files(File d) throws IOException {
        initDir(d);
    }

    /**
     * @return
     * {@code new File(Generic_Defaults.getDataDir(), Generic_Strings.s_generic)}
     */
    public static File getDefaultGenericDir() {
        return new File(Generic_Defaults.getDataDir(), Generic_Strings.s_generic);
    }

    /**
     * @return Default directory.
     */
    public static File getDefaultDir() {
        File dir = getDefaultGenericDir();
        dir = new File(dir, Generic_Strings.s_generic);
        return dir;
    }

    /**
     * For initialising {@link #dir} to {@code d} and reporting whether the
     * directory exists already or was successfully created. If it was not
     * successfully created this should throw an IOException.
     *
     * @param d
     * @throws java.io.IOException If {@link dir} cannot be set to {@code d}.
     */
    private void initDir(File d) throws IOException {
        dir = d;
        String m = "The directory " + d;
        if (d.exists()) {
            System.out.println("Warning: " + m + " already exists in "
                    + this.getClass().getName() + ".initDir(File). Generally "
                    + "this is fine, but data in " + getGeneratedDir() + " and "
                    + getOutputDir() + " may be overwritten or modified.");
        } else {
            boolean successfulCreation;
            successfulCreation = d.mkdirs();
            if (!successfulCreation) {
                throw new IOException(m + " does not exist and could not be "
                        + "created in " + this.getClass().getName()
                        + ".initDir(File)");
            }
            System.out.println(m + " was successfully created.");
        }
    }

    /**
     * Sets {@link #dir} to {@code d} and sets
     * {@link #inputDir}, {@link #generatedDir} and {@link #outputDir} to
     * {@code null}.
     *
     * @param d What {@link #dir} is set to.
     * @throws java.io.IOException If {@link dir} cannot be set to {@code d}.
     */
    public final void setDir(File d) throws IOException {
        initDir(d);
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
