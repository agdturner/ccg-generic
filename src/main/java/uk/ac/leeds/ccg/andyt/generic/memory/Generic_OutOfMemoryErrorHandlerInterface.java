/*
 * Copyright (C) 2015 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.memory;

import java.io.IOException;

/**
 * An interface for handling OutOfMemoryErrors.
 */
public interface Generic_OutOfMemoryErrorHandlerInterface {

    //static final long serialVersionUID = 1L;
    /**
     * For a method that will try to ensure there is enough memory to continue.
     *
     * @return True if successful and false otherwise.
     * @throws java.io.IOException
     */
    boolean checkAndMaybeFreeMemory() throws IOException;

    /**
     * For a method that will initialise the MemoryReserve.
     */
    void initMemoryReserve();
}
