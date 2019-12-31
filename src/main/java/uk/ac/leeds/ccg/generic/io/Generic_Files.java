/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.generic.io;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.generic.core.Generic_Strings;

/**
 * Generic Files, for
 *
 * @author Andy Turner
 * @version 1.0.1
 */
public class Generic_Files implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The Generic defaults.
     */
    public transient final Generic_Defaults defaults;

    /**
     * The base level directory. Set from {@link #defaults}.
     */
    protected Generic_Path dir;

    /**
     * The input directory in {@link #dir}.
     */
    protected Generic_Path inputDir;

    /**
     * The generated directory in {@link #dir}.
     */
    protected Generic_Path generatedDir;

    /**
     * The output directory in {@link #dir}.
     */
    protected Generic_Path outputDir;

    /**
     * The log directory in {@link #outputDir}.
     */
    protected Generic_Path logDir;

    /**
     * @param d What {@link #dir} is set to.
     * @throws java.io.IOException If the directory
     */
    public Generic_Files(Generic_Defaults d) throws IOException {
        defaults = d;
        initDir(d.getDir());
    }

    /**
     * @param d What {@link #dir} is set to.
     * @throws java.io.IOException If encountered.
     */
    public final void initDir(Path d) throws IOException {
        dir = new Generic_Path(d);
        String m = "The directory " + dir.s;
        if (Files.exists(dir.getPath())) {
            System.out.println("Warning: " + m + " already exists. Files "
                    + "therein may be overwritten or modified.");
        } else {
            Files.createDirectories(dir.getPath());
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
    public final void setDir(Path d) throws IOException {
        dir = new Generic_Path(d);
        inputDir = null;
        generatedDir = null;
        outputDir = null;
    }

    /**
     * @return A copy of {@link #dir}
     */
    public Generic_Path getDir() {
        return new Generic_Path(dir);
    }

    /**
     * @return If {@link #inputDir} is {@code null} then it is set using {@code
     * inputDir = new Generic_Path(Paths.get(dir.s, Generic_Strings.s_input));}
     * and the directory is created then it is returned. If {@link #inputDir} is
     * not {@code null} it is returned.
     * @throws java.io.IOException If encountered.
     */
    public Generic_Path getInputDir() throws IOException {
        if (inputDir == null) {
            inputDir = new Generic_Path(Paths.get(dir.s, Generic_Strings.s_input));
            Files.createDirectories(inputDir.getPath());
        }
        return inputDir;
    }

    /**
     * @return If {@link #generatedDir} is {@code null} then it is set using {@code
     * generatedDir = new Generic_Path(Paths.get(dir.s, Generic_Strings.s_generated));}
     * and the directory is created then it is returned. If
     * {@link #generatedDir} is not {@code null} it is returned.
     * @throws java.io.IOException If encountered.
     */
    public Generic_Path getGeneratedDir() throws IOException {
        if (generatedDir == null) {
            generatedDir = new Generic_Path(Paths.get(dir.s, Generic_Strings.s_generated));
            Files.createDirectories(generatedDir.getPath());
        }
        return generatedDir;
    }

    /**
     * @return If {@link #outputDir} is {@code null} then it is set using {@code
     * outputDir = new Generic_Path(Paths.get(dir.s, Generic_Strings.s_output));}
     * and the directory is created then it is returned. If {@link #outputDir}
     * is not {@code null} it is returned.
     * @throws java.io.IOException If encountered.
     */
    public Generic_Path getOutputDir() throws IOException {
        if (outputDir == null) {
            outputDir = new Generic_Path(Paths.get(dir.s, Generic_Strings.s_output));
            Files.createDirectories(outputDir.getPath());
        }
        return outputDir;
    }

    /**
     * @return If {@link #logDir} is {@code null} then it is set using {@code
     * logDir = new Generic_Path(Paths.get(dir.s, Generic_Strings.s_log));} and
     * the directory is created then it is returned. If {@link #logDir} is
     * not {@code null} it is returned.
     * @throws java.io.IOException If encountered.
     */
    public Path getLogDir() throws IOException {
        if (logDir == null) {
            logDir = new Generic_Path(Paths.get(dir.s, Generic_Strings.s_log));
            Files.createDirectories(logDir.getPath());
        }
        return logDir;
    }

}
