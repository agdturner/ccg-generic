/**
 * A component of a library for
 * <a href="http://www.geog.leeds.ac.uk/people/a.turner/projects/MoSeS">MoSeS</a>.
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
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package uk.ac.leeds.ccg.andyt.generic.utilities;

/**
 * A class for handling errors and exceptions.
 */
public abstract class ErrorAndExceptionHandler {

    // Java Errors
    public static int Error = 1;
    // Third party Errors
    // Java Exceptions
    public static int Exception = 20;
    public static int IOException = 21;
    public static int FileNotFoundException = 22;
    public static int ClassNotFoundException = 23;
    public static int NumberFormatException = 24;
    // Third party Exceptions
    public static int MPIException = 30;
}
