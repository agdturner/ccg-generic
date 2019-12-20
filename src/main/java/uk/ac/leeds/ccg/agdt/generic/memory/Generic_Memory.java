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

package uk.ac.leeds.ccg.agdt.generic.memory;

import java.io.IOException;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Environment;

/**
 * An interface for handling issues related to computer memory.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public interface Generic_Memory {

    /**
     * For a method that will try to ensure there is enough memory to continue.
     *
     * @return true iff the checks was successful.
     * @throws java.io.IOException if there is a problem swapping data to a 
     * cache.
     */
    boolean checkAndMaybeFreeMemory() throws IOException, Exception;

    /**
     * For a method that will initialise a MemoryReserve.
     * @param e Generic_Environment For logging.
     */
    void initMemoryReserve(Generic_Environment e);
}
