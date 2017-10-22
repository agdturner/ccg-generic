/*
 * Copyright (C) 2015 geoagdt.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package uk.ac.leeds.ccg.andyt.generic.memory;

public interface Generic_OutOfMemoryErrorHandlerInterface {

    //static final long serialVersionUID = 1L;
    /**
     * For a method that will try to ensure there is enough memory to continue.
     *
     * @param handleOutOfMemoryError If true then this method will try to handle
     * any OutOfMemoryError encountered whilst trying to ensure there is enough
     * memory to continue.
     * @return True if successful and false otherwise.
     */
    boolean tryToEnsureThereIsEnoughMemoryToContinue(
            boolean handleOutOfMemoryError);

    /**
     * For a method that will initialise the MemoryReserve.
     *
     * @param handleOutOfMemoryError If true then this method will try to handle
     * any OutOfMemoryError encountered whilst trying to initialise the
     * MemoryReserve.
     */
    void initMemoryReserve(boolean handleOutOfMemoryError);
}
