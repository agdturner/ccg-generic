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

//import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Strings;

/**
 * Generic Defaults. A class for holding IO defaults.
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_Defaults implements Serializable {

    private static final long serialVersionUID = 1L;
    
    protected final Generic_Path dir;
    
    /**
     * Defaults dir to the users home directory.
     */
    public Generic_Defaults() {
        this(Paths.get(System.getProperty("user.home")));
        //this(Paths.get(System.getProperty("user.dir")));
    }

    /**
     * @param dir Duplicated to initialise {@link #dir}. 
     */
    public Generic_Defaults(Path dir) {
        this.dir = new Generic_Path(Paths.get(dir.toString()));
    }
    
    /**
     * @return a copy of {@link #dir}. 
     */
    public Path getDir() {
        return Paths.get(dir.toString());
    }
    
    /**
     * @return {@code new File(dir, Generic_Strings.s_data)} 
     */
    public Path getDataDir() {
        return Paths.get(dir.toString(), Generic_Strings.s_data);
    }

    /**
     * @return {@code new File(dir, Generic_Strings.s_src)} 
     */
    public Path getSrcDir() {
        return Paths.get(dir.toString(), Generic_Strings.s_src);
    }
    
    /**
     * @return {@code new File(dir, Generic_Strings.s_project)} 
     */
    public Path getProjectDir() {
        return Paths.get(dir.toString(), Generic_Strings.s_project);
    }

}
