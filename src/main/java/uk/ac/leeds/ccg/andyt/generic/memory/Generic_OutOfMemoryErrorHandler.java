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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to be extended for memory management involving the controlled
 * swapping of parts of data from the fast access memory to files and the
 * handling of OutOfMemoryErrors should they be encountered.
 */
public abstract class Generic_OutOfMemoryErrorHandler
        implements Generic_OutOfMemoryErrorHandlerInterface, Serializable {

    /**
     * For tests on the available memory.
     */
    public transient Generic_TestMemory Generic_TestMemory;

    /**
     * For storing the default as to whether OutOfMemoryErrors are handled.
     */
    public boolean HOOME;

    /**
     * A default controlling the amount of memory that is reserved and cleared
     * for handling OutOfMemoryErrors should they be encountered.
     */
    public final int Default_Memory_Threshold = 6000000;//4000000;

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
     * identification and writing of parts of data to filespace.
     */
    protected transient int[] MemoryReserve;

    /**
     * A method which will try to swap any data.
     *
     * @return an indication if some data was swapped.
     * @throws java.io.IOException if there is a problem swapping data.
     */
    public abstract boolean swapDataAny() throws IOException ;

    /**
     * A method which will try to swap any data.
     *
     * @param handleOutOfMemoryError If true this will try to handle any
     * OutOfMemoryError encountered in attempting to swap any data.
     * @return an indication if some data was swapped.
     * @throws java.io.IOException if there is a problem swapping data.
     */
    public abstract boolean swapDataAny(boolean handleOutOfMemoryError) 
            throws IOException;

    /**
     * May initialise Generic_TestMemory and Generic_TestMemory.Runtime.
     *
     * @return Generic_TestMemory.Runtime
     */
    public Runtime getRuntime() {
        return getGeneric_TestMemory().Runtime;
    }

    /**
     * Initialises MemoryReserve as an array of size given by size.
     *
     * @param size Size that MemoryReserve is initialised to.
     */
    public final void initMemoryReserve(int size) {
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
    public void initMemoryReserve() {
        initMemoryReserve(Default_Memory_Threshold);
    }

    /**
     * Clears MemoryReserve by setting it to null and calling the garbage
     * collector. After this method is executed then no further method calls
     * will handle OutOfMemoryError until MemoryReserve is initialised again.
     * Generally, the assumption is that MemoryReserve is not null before this
     * is called.
     */
    public final void clearMemoryReserve() {
        //log(Runtime.getRuntime().freeMemory());
        this.MemoryReserve = null;
        //System.gc();
    }

    /**
     * For ensuring robustness in handling OutOfMemoryError and to try to
     * prevent them being thrown. If this method returns false then it may be
     * best to write all data to persistent memory and restart the JVM with a
     * increased heap size before attempting to continue.
     *
     * @return true if there is enough memory to continue and false otherwise.
     */
    @Override
    public abstract boolean checkAndMaybeFreeMemory();

    /**
     * For initialising and returning Generic_TestMemory.
     *
     * @return Generic_TestMemory (after default construction if null)
     */
    protected Generic_TestMemory getGeneric_TestMemory() {
        if (Generic_TestMemory == null) {
            Generic_TestMemory = new Generic_TestMemory();
        }
        return Generic_TestMemory;
    }

    /**
     * return getGeneric_TestMemory().getTotalFreeMemory();
     *
     * @return long
     */
    public long getTotalFreeMemory() {
        return getGeneric_TestMemory().getTotalFreeMemory();
    }

    /**
     * For initialising a File from String _String.
     *
     * @param file String
     * @return File
     */
    public File initFile(String file) {
        File result = new File(file);
        result.getParentFile().mkdirs();
        try {
            result.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Generic_OutOfMemoryErrorHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        checkAndMaybeFreeMemory();
        return result;
    }

    /**
     * For initialising a File.
     *
     * @param dir File
     * @param filename String
     * @return File
     */
    public File initFile(File dir, String filename) {
        File result = new File(dir, filename);
        dir.mkdirs();
        try {
            result.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Generic_OutOfMemoryErrorHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * For initialising a File Directory.
     *
     * @param parentFile File
     * @param s String name of directory.
     * @return File
     */
    public File initFileDirectory(File parentFile, String s) {
        File result = new File(parentFile, s);
        result.mkdirs();
        return result;
    }
}
