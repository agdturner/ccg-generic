/*
 * Copyright (C) 2017 geoagdt.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.File;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class Generic_Files {

    // For convenience
    public Generic_Strings Strings;

    protected File DataDir;
    protected File InputDataDir;
    protected File GeneratedDataDir;
    protected File OutputDataDir;

    protected Generic_Files() {
    }

    public Generic_Files(String dataDirName) {
        Strings = new Generic_Strings();
        setDataDirectory(dataDirName);
    }

    public Generic_Files(Generic_Strings strings, String dataDirName) {
        Strings = strings;
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
                throw new Error("The data directory was not created in "
                        + this.getClass().getName() + ".setDataDirectory(String)");
            }
        }
    }

    public File getDataDir() {
        return DataDir;
    }

    public File getInputDataDir() {
        if (InputDataDir == null) {
            InputDataDir = new File(
                    getDataDir(),
                    Strings.getString_input());
        }
        return InputDataDir;
    }
    
    public File getGeneratedDataDir() {
        if (GeneratedDataDir == null) {
            GeneratedDataDir = new File(
                    getDataDir(),
                    Strings.getString_generated());
        }
        return GeneratedDataDir;
    }

    public File getOutputDataDir() {
        if (OutputDataDir == null) {
            OutputDataDir = new File(
                    getDataDir(),
                    Strings.getString_output());
        }
        return OutputDataDir;
    }
}
