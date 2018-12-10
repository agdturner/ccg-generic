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

    protected File DataDir;
    protected File InputDataDir;
    protected File GeneratedDataDir;
    protected File OutputDataDir;

    /**
     * Defaults DataDir to "data" in the users current working directory.
     */
    public Generic_Files() {
        this(System.getProperty("user.dir")
                + System.getProperty("file.separator") + "data");
    }

    public Generic_Files(String dataDirName) {
        setDataDirectory(dataDirName);
    }

    /**
     * Initialises a data directory with a name given by name.
     *
     * @param dataDirName
     */
    public final void setDataDirectory(String dataDirName) {
        DataDir = new File(dataDirName);
        if (!DataDir.exists()) {
            boolean successfulCreation;
            successfulCreation = DataDir.mkdirs();
            if (!successfulCreation) {
                throw new Error("The data directory " + DataDir + " was not "
                        + "created in " + this.getClass().getName() 
                        + ".setDataDirectory(String)");
            }
        }
    }

    /**
     * Set DataDir to dataDir.
     *
     * @param dataDir
     */
    public final void setDataDirectory(File dataDir) {
        DataDir = dataDir;
    }

    public File getDataDir() {
        return DataDir;
    }

    public File getInputDataDir(Generic_Strings strings) {
        if (InputDataDir == null) {
            InputDataDir = new File(getDataDir(), strings.s_input);
        }
        return InputDataDir;
    }

    public File getGeneratedDataDir(Generic_Strings strings) {
        if (GeneratedDataDir == null) {
            GeneratedDataDir = new File(getDataDir(), strings.s_generated);
        }
        return GeneratedDataDir;
    }

    public File getOutputDataDir(Generic_Strings strings) {
        if (OutputDataDir == null) {
            OutputDataDir = new File(getDataDir(), strings.s_output);
        }
        return OutputDataDir;
    }
}
