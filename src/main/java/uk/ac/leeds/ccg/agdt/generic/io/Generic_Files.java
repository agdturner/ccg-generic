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

package uk.ac.leeds.ccg.agdt.generic.io;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Strings;

/**
 * Generic Files, for 
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_Files implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The Generic defaults.
     */
    protected final Generic_Defaults defaults;
    
    /**
     * The base level directory. Set from {@link #defaults}.
     */
    protected Path dir;

    /**
     * The input directory in {@link #dir}.
     */
    protected Path inputDir;

    /**
     * The generated directory in {@link #dir}.
     */
    protected Path generatedDir;

    /**
     * The output directory in {@link #dir}.
     */
    protected Path outputDir;

    /**
     * The log directory in {@link #outputDir}.
     */
    protected Path logDir;

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
        dir = d;
        String m = "The directory " + dir.toString();
        if (Files.exists(dir)) {
            System.out.println("Warning: " + m + " already exists. Files "
                    + "therein may be overwritten or modified.");
        } else {
            Files.createDirectories(dir);
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
        dir = d;
        inputDir = null;
        generatedDir = null;
        outputDir = null;
    }

    /**
     * @return A copy of {@link #dir}
     */
    public Path getDir() {
        return Paths.get(dir.toString());
    }

    /**
     * @return If {@link #inputDir} is null then it is set using {@code
     * inputDir = Paths.get(dir.toString(), Generic_Strings.s_input);} then it 
     * is returned.
     * @throws java.io.IOException If encountered.
     */
    public Path getInputDir() throws IOException {
        if (inputDir == null) {
            inputDir = Paths.get(dir.toString(), Generic_Strings.s_input);
            Files.createDirectories(inputDir);
        }
        return inputDir;
    }

    /**
     * @return If {@link #generatedDir} is null then it is set using {@code
     * inputDir = Paths.get(dir.toString(), Generic_Strings.s_generated);} then it 
     * is returned.
     * @throws java.io.IOException If encountered.
     */
    public Path getGeneratedDir() throws IOException {
        if (generatedDir == null) {
            generatedDir = Paths.get(dir.toString(), Generic_Strings.s_generated);
            Files.createDirectories(generatedDir);
        }
        return generatedDir;
    }

    /**
     * @return If {@link #outputDir} is null then it is set using {@code
     * inputDir = Paths.get(dir.toString(), Generic_Strings.s_output);} then it 
     * is returned.
     * @throws java.io.IOException If encountered.
     */
    public Path getOutputDir() throws IOException {
        if (outputDir == null) {
            outputDir = Paths.get(dir.toString(), Generic_Strings.s_output);
            Files.createDirectories(outputDir);
        }
        return outputDir;
    }

    /**
     * @return If {@link #logDir} is null then it is set using {@code
     * inputDir = Paths.get(dir.toString(), Generic_Strings.s_log);} then it 
     * is returned.
     * @throws java.io.IOException If encountered.
     */
    public Path getLogDir() throws IOException {
        if (logDir == null) {
            logDir = Paths.get(dir.toString(), Generic_Strings.s_log);
            Files.createDirectories(logDir);
        }
        return logDir;
    }

}
