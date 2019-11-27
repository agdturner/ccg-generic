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
package uk.ac.leeds.ccg.agdt.generic.memory;

/**
 * For getting information about computer memory.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_MemoryTest {

    /**
     * A reference to the runtime.
     */
    protected transient final Runtime runtime;

    /**
     * For storing a record of the maximum amount of memory available to the
     * JVM. By default a java process is given a maximum allowed amount of
     * memory so it is fail safe. A user can control how much this maximum in
     * the java run command options.
     */
    private final long MaxMemory;

    /**
     * Creates a new instance of Generic_TestMemory
     */
    public Generic_MemoryTest() {
        runtime = Runtime.getRuntime();
        MaxMemory = runtime.maxMemory();
    }

    /**
     * Creates a new instance of Generic_TestMemory using runtime.
     *
     * @param runtime runtime
     */
    public Generic_MemoryTest(Runtime runtime) {
        this.runtime = runtime;
        MaxMemory = this.runtime.maxMemory();
    }

    /**
     * For returning the total free memory.
     *
     * @return The TotalFreeMemory available as calculated from runtime.
     */
    public long getTotalFreeMemory() {
        long r;
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        r = freeMemory + (MaxMemory - allocatedMemory);
        return r;
    }
}
