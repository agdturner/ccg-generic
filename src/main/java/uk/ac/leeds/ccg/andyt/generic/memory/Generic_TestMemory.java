/**
 * Copyright 2010 Centre for Computational Geography, University of Leeds.
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

/**
 * A class with methods that help in testing computer memory.
 */
public class Generic_TestMemory {

    /**
     * A reference to the Runtime.
     */
    protected transient Runtime Runtime;

    /**
     * For storing a record of the maximum amount of memory available to the
     * JVM. By default a java process is given a maximum allowed amount of
     * memory so it is failsafe. A user can control how much this maximum in
     * the java run command options.
     */
    private final long MaxMemory;

    /**
     * Creates a new instance of Generic_TestMemory
     */
    public Generic_TestMemory() {
        Runtime = Runtime.getRuntime();
        MaxMemory = Runtime.maxMemory();
    }

    /**
     * Creates a new instance of Generic_TestMemory using runtime.
     *
     * @param runtime
     */
    public Generic_TestMemory(Runtime runtime) {
        Runtime = runtime;
        MaxMemory = Runtime.maxMemory();
    }

    /**
     * For returning the total free memory.
     *
     * @return The TotalFreeMemory available as calculated from Runtime.
     */
    public long getTotalFreeMemory() {
        long result;
        long allocatedMemory = Runtime.totalMemory();
        long freeMemory = Runtime.freeMemory();
        result = freeMemory + (MaxMemory - allocatedMemory);
        return result;
    }
}
