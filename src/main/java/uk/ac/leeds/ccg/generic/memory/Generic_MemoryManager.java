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
package uk.ac.leeds.ccg.generic.memory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;

/**
 * A class to be extended for memory management involving the controlled
 * swapping of parts of data from the fast access memory to files and the
 * handling of OutOfMemoryErrors should they be encountered.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public abstract class Generic_MemoryManager implements Generic_Memory, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance.
     */
    public Generic_MemoryManager(){}
    
    /**
     * For tests on the available memory.
     */
    public transient Generic_MemoryTest MemoryTest;

    /**
     * For storing the default as to whether OutOfMemoryErrors are handled.
     */
    public boolean HOOME;

    /**
     * A default controlling the amount of memory that is reserved and cleared
     * for handling OutOfMemoryErrors should they be encountered.
     */
    protected static final int Default_Memory_Threshold = 6000000;//4000000;

    /**
     * Stores the amount of memory that is reserved and cleared for handling
     * OutOfMemoryErrors should they be encountered.
     */
    protected long Memory_Threshold;

    /**
     * For use when OutOfMemory handling is definitely not wanted. It is thought
     * better to have this rather than use a false value as and when as it
     * allows for finding those parts of the code that are actually used in this
     * regard.
     */
    public final boolean HOOMEF = false;

    /**
     * For use when OutOfMemory handling is definitely wanted. It is thought
     * better to have this rather than use a false value as and when as it
     * allows for finding those parts of the code that are actually used in this
     * regard.
     */
    public final boolean HOOMET = true;

    /**
     * Reserve memory that can be set to null and garbage collected so as to
     * provide room in memory for handling OutOfMemoryErrors involving the
     * identification and writing of data to disk.
     */
    protected transient int[] MemoryReserve;

    /**
     * A method which will try to cache any data.
     *
     * @return an indication if some data was cached.
     * @throws java.io.IOException IFF encountered.
     */
    public abstract boolean swapSomeData() throws IOException, Exception;

    /**
     * A method which will try to swap any data.
     *
     * @param hoome If true this will try to handle any OutOfMemoryError
     * encountered in attempting to swap any data.
     * @return an indication if some data was swapped.
     * @throws java.io.IOException IFF encountered.
     */
    public abstract boolean swapSomeData(boolean hoome) throws IOException, Exception;

    /**
     * This may initialise {@link #MemoryTest}.
     *
     * @return MemoryTest.runtime
     */
    public Runtime getRuntime() {
        return getMemoryTest().runtime;
    }

    /**
     * Initialises MemoryReserve as an array of size given by size.
     *
     * @param size Size that MemoryReserve is initialised to.
     * @param e Generic_Environment for logging.
     */
    public final void initMemoryReserve(int size, Generic_Environment e) {
        e.log("Initialising MemoryReserve size=" + size);
        if (MemoryReserve == null) {
            MemoryReserve = new int[size];
            Arrays.fill(MemoryReserve, Integer.MIN_VALUE);
        }
    }

    /**
     * @return this.MemoryReserve.
     */
    public int[] getMemoryReserve() {
        return MemoryReserve;
    }

    /**
     * this.MemoryReserve = r;
     *
     * @param r int[]
     */
    public void setMemoryReserve(int[] r) {
        this.MemoryReserve = r;
    }

    /**
     * Initialises MemoryReserve as an int[] of size Default_Memory_Threshold.
     * MemoryReserve is for use when an OutOfMemoryError is handled. This
     * involves clearMemoryReserve() which sets MemoryReserve to null providing
     * memory for swapping operations that move data from one location
     * (memory/RAM) to another location (disk). The swapping may involve
     * accounting the amount and details of the data swapped and the locations
     * and returning this information.
     */
    @Override
    public void initMemoryReserve(Generic_Environment e) {
        initMemoryReserve(Default_Memory_Threshold, e);
    }

    /**
     * Clears MemoryReserve by setting it to null and calling the garbage
     * collector.After this method is executed then no further method calls will
     * handle OutOfMemoryError until MemoryReserve is initialised again.
     * Generally, the assumption is that MemoryReserve is not null before this
     * is called.
     *
     * @param e Generic_Environment for logging.
     */
    public final void clearMemoryReserve(Generic_Environment e) {
        MemoryReserve = null;
        //System.gc();
        e.log("Cleared memory reserve TotalFreeMemory=" + getMemoryTest().getTotalFreeMemory());
    }

    /**
     * For ensuring robustness in handling OutOfMemoryError and to try to
     * prevent them being thrown.If this method returns false then it may be
     * best to write all data to persistent memory and restart the JVM with a
     * increased heap size before attempting to continue.
     *
     * @return true if there is enough memory to continue and false otherwise.
     * @throws java.lang.Exception This may occur.
     * @throws java.io.IOException This may occur.
     */
    @Override
    public abstract boolean checkAndMaybeFreeMemory() throws IOException,
            Exception;

    /**
     * For initialising and returning {@link #MemoryTest}.
     *
     * @return {@link #MemoryTest} (possibly after initialisation).
     */
    protected Generic_MemoryTest getMemoryTest() {
        if (MemoryTest == null) {
            MemoryTest = new Generic_MemoryTest();
        }
        return MemoryTest;
    }

    /**
     * @return The amount of free memory available.
     */
    public long getTotalFreeMemory() {
        return getMemoryTest().getTotalFreeMemory();
    }
}
