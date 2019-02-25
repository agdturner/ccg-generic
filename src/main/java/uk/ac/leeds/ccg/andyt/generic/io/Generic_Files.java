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
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Object;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class Generic_Files {

    /**
     * The base level Data directory.
     */
    protected File dataDir;

    /**
     * The input directory in {@link #dataDir}.
     */
    protected File inputDir;

    /**
     * The generated directory in {@link #dataDir}.
     */
    protected File generatedDir;

    /**
     * The output directory in {@link #dataDir}.
     */
    protected File outputDir;

    /**
     * The log directory in {@link #outputDir}.
     */
    protected File logDir;

    /**
     * Defaults dataDir to what is returned from
     * {@link Generic_Files#getDefaultDataDir()}.
     */
    public Generic_Files() {
        dataDir = getDefaultDataDir();
    }

    /**
     * @param dir What {@link #dataDir} is set to.
     */
    public Generic_Files(File dir) {
        dataDir = dir;
    }

    /**
     * Sets {@link #dataDir} to {@code d} and sets
     * {@link #inputDir}, {@link #generatedDir} and {@link #outputDir} to
     * {@code null}.
     *
     * @param d What {@link #dataDir} is set to.
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
        dataDir = d;
        inputDir = null;
        generatedDir = null;
        outputDir = null;
    }

    /**
     *
     * @return dataDir
     */
    public File getDataDir() {
        return dataDir;
    }

    /**
     * {@code return new File(System.getProperty("user.dir"), "data");}
     *
     * @return A default directory called data in the user.dir.
     */
    public static File getDefaultDataDir() {
        return new File(System.getProperty("user.dir"), Generic_Strings.s_DATA);
    }

    /**
     *
     * @return InputDataDir If null then InputDataDir is set using {@code
     * InputDataDir = new File(getDataDir(), Generic_Strings.s_input);}
     */
    public File getInputDataDir() {
        if (inputDir == null) {
            inputDir = new File(getDataDir(), Generic_Strings.s_input);
            inputDir.mkdirs();
        }
        return inputDir;
    }

    /**
     *
     * @return GeneratedDataDir
     */
    public File getGeneratedDataDir() {
        if (generatedDir == null) {
            generatedDir = new File(getDataDir(), Generic_Strings.s_generated);
            generatedDir.mkdirs();
        }
        return generatedDir;
    }

    public File getOutputDataDir() {
        if (outputDir == null) {
            outputDir = new File(getDataDir(), Generic_Strings.s_output);
            outputDir.mkdirs();
        }
        return outputDir;
    }

    public File getLogDir() {
        if (logDir == null) {
            logDir = new File(getOutputDataDir(), Generic_Strings.s_log);
        }
        return logDir;
    }
}
