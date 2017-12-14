/**
 * Copyright 2010 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.memory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * A class to be extended for memory management involving the controlled
 * swapping of parts of data from the fast access memory to the filespace and
 * the handling of OutOfMemoryErrors should they be encountered.
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
     */
    protected abstract boolean swapDataAny();

    /**
     * A method which will try to swap any data.
     *
     * @param handleOutOfMemoryError If true this will try to handle any
     * OutOfMemoryError encountered in attempting to swap any data.
     * @return an indication if some data was swapped.
     */
    public abstract boolean swapDataAny(boolean handleOutOfMemoryError);

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
    protected final void initMemoryReserve(int size) {
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
     * this.MemoryReserve = a_MemoryReserve;
     *
     * @param a_MemoryReserve
     */
    public void set_MemoryReserve(int[] a_MemoryReserve) {
        this.MemoryReserve = a_MemoryReserve;
    }

    /**
     * Initialises MemoryReserve.
     *
     * @param handleOutOfMemoryError If true then OutOfMemoryErrors are caught,
     * swap operations are initiated, then the method is re-called. If false
     * then OutOfMemoryErrors are caught and thrown.
     */
//    public abstract void initMemoryReserve(
//            boolean handleOutOfMemoryError);
    @Override
    public void initMemoryReserve(
            boolean handleOutOfMemoryError) {
        try {
            initMemoryReserve();
            checkAndMaybeFreeMemory();
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                if (Generic_OutOfMemoryErrorHandler.this.swapDataAny()) {
                    Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(HOOMEF);
                } else {
                    throw a_OutOfMemoryError;
                }
            } else {
                throw a_OutOfMemoryError;
            }
        }
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
    protected void initMemoryReserve() {
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
        System.gc();
    }

    /**
     * For ensuring robustness in handling OutOfMemoryError and to try to
     * prevent them being thrown. If this method returns false then it may be
     * best to write all data to persistent memory and restart the JVM with a
     * increased heap size before attempting to continue.
     *
     * @return true if there is enough memory to continue and false otherwise.
     */
    protected abstract boolean checkAndMaybeFreeMemory();

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
     * A method to ensure there is enough memory to continue.
     *
     * @param handleOutOfMemoryError
     * @return true if ensured there is enough memory to continue
     */
    @Override
    public abstract boolean checkAndMaybeFreeMemory(
            boolean handleOutOfMemoryError);

    /**
     * <code>
     * return getGeneric_TestMemory().getTotalFreeMemory();
     * </code>
     *
     * @return
     */
    protected long getTotalFreeMemory() {
        return getGeneric_TestMemory().getTotalFreeMemory();
    }

    /**
     * For returning the total free memory available to the JVM. This method
     * will try to swap data if handleOutOfMemory is true, but if it finds no
     * data to swap will throw an OutOfMemoryError.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public long getTotalFreeMemory(
            boolean handleOutOfMemoryError) {
        try {
            long result = getTotalFreeMemory();
            checkAndMaybeFreeMemory();
            return result;
        } catch (OutOfMemoryError _OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                boolean swapSuccess = Generic_OutOfMemoryErrorHandler.this.swapDataAny();
                if (!swapSuccess) {
                    throw new OutOfMemoryError();
                }
                Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(HOOMEF);
                return getTotalFreeMemory(
                        handleOutOfMemoryError);
            } else {
                throw _OutOfMemoryError;
            }
        }
    }

    /**
     * For initialising a String of length length.
     *
     * @param length
     * @param handleOutOfMemoryError
     * @return
     */
    public String initString(
            int length,
            boolean handleOutOfMemoryError) {
        try {
            String result = new String();
            for (int i = 0; i < length; i++) {
                result += "X";
            }
            checkAndMaybeFreeMemory();
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                if (Generic_OutOfMemoryErrorHandler.this.swapDataAny()) {
                    Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(HOOMEF);
                } else {
                    throw a_OutOfMemoryError;
                }
                return initString(
                        length,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    /**
     * @param a_String
     * @param b_String
     * @param handleOutOfMemoryError
     * @return String which is a_String appended to the end of b_String
     */
    public String initString(
            String a_String,
            String b_String,
            boolean handleOutOfMemoryError) {
        try {
            String result = a_String + b_String;
            checkAndMaybeFreeMemory();
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                if (Generic_OutOfMemoryErrorHandler.this.swapDataAny()) {
                    Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(HOOMEF);
                } else {
                    throw a_OutOfMemoryError;
                }
                return initString(
                        a_String,
                        b_String,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    /**
     * For initialising a File from String _String.
     *
     * @param a_String
     * @param handleOutOfMemoryError
     * @return
     * @throws java.io.IOException
     */
    public File initFile(
            String a_String,
            boolean handleOutOfMemoryError)
            throws IOException {
        try {
            File result = new File(a_String);
            result.getParentFile().mkdirs();
            result.createNewFile();
            checkAndMaybeFreeMemory();
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                if (Generic_OutOfMemoryErrorHandler.this.swapDataAny()) {
                    Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(HOOMEF);
                } else {
                    throw a_OutOfMemoryError;
                }
                Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(
                        handleOutOfMemoryError);
                return initFile(
                        a_String,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    /**
     * For initialising a File from File _File and String _String.
     *
     * @param a_File
     * @param a_String
     * @param handleOutOfMemoryError
     * @return
     * @throws java.io.IOException
     */
    public File initFile(
            File a_File,
            String a_String,
            boolean handleOutOfMemoryError)
            throws IOException {
        try {
            File result = new File(a_File, a_String);
            a_File.mkdirs();
            result.createNewFile();
            checkAndMaybeFreeMemory();
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                if (Generic_OutOfMemoryErrorHandler.this.swapDataAny()) {
                    Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(HOOMEF);
                } else {
                    throw a_OutOfMemoryError;
                }
                return initFile(
                        a_String,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    /**
     * For initialising a File Directory from File _ParentFile and String
     * _String.
     *
     * @param parentFile
     * @param string
     * @param handleOutOfMemoryError
     * @return
     * @throws java.io.IOException
     */
    public File initFileDirectory(
            File parentFile,
            String string,
            boolean handleOutOfMemoryError)
            throws IOException {
        try {
            File result = new File(parentFile, string);
            result.mkdirs();
            checkAndMaybeFreeMemory();
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                if (Generic_OutOfMemoryErrorHandler.this.swapDataAny()) {
                    Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(HOOMEF);
                } else {
                    throw a_OutOfMemoryError;
                }
                return initFileDirectory(
                        parentFile,
                        string,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    public String println(
            String a_String,
            String a_StringToDuplicateAndReturn,
            boolean handleOutOfMemoryError) {
        try {
            System.out.println(a_String);
            String result = a_StringToDuplicateAndReturn;
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                if (Generic_OutOfMemoryErrorHandler.this.swapDataAny()) {
                    Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(HOOMEF);
                } else {
                    throw a_OutOfMemoryError;
                }
                return println(
                        a_String,
                        a_StringToDuplicateAndReturn,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    public double sin(
            double value,
            boolean handleOutOfMemoryError) {
        try {
            return Math.sin(value);
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                if (Generic_OutOfMemoryErrorHandler.this.swapDataAny()) {
                    Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(HOOMEF);
                } else {
                    throw a_OutOfMemoryError;
                }
                Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(
                        handleOutOfMemoryError);
                return sin(
                        value,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    public String toString(
            double a_double,
            boolean handleOutOfMemoryError) {
        try {
            return Double.toString(a_double);
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                if (Generic_OutOfMemoryErrorHandler.this.swapDataAny()) {
                    Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(HOOMEF);
                } else {
                    throw a_OutOfMemoryError;
                }
                Generic_OutOfMemoryErrorHandler.this.initMemoryReserve(
                        handleOutOfMemoryError);
                return toString(
                        a_double,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }
}
