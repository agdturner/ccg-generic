/**
 * A component of a library for
 * <a href="http://www.geog.leeds.ac.uk/people/a.turner/projects/MoSeS">MoSeS</a>.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * Abstract class for a data record.
 */
public abstract class Generic_AbstractDataRecord implements Serializable, Comparable {

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.RecordID ^ (this.RecordID >>> 32));
        return hash;
    }

    /**
     * @return 8 (assumed to be the number of bits in a byte).
     */
    public static long getNumberOfBitsInByte() {
        return 8L;
    }
    /**
     * An individual sequential identifier.
     */
    protected long RecordID;

    /**
     * @return A copy of <code>this.RecordID</code>
     */
    public long getRecordID() {
        return this.RecordID;
    }

    /**
     * Initialise from rec.
     *
     * @param rec
     */
    protected void init(Generic_AbstractDataRecord rec) {
        this.RecordID = rec.RecordID;
    }

    /**
     * Initialise.
     */
    protected void init() {
        this.RecordID = Long.MIN_VALUE;
    }

    /**
     * @return A <code>String</code> description of this.
     */
    @Override
    public String toString() {
        String result = "RecordID " + this.RecordID;
        return result;
    }

    /**
     * @return A Comma Separated Version (CSV) <code>String</code> of the values
     * of the <code>Fields</code> of <code>this</code>.
     */
    public String toCSVString() {
        String result = "" + this.RecordID;
        return result;
    }

    /**
     * @return A Comma Separated Version (CSV) <code>String</code> of the names
     * of the Variables as returned in <code>toString()</code>.
     */
    public String toCSVStringFields() {
        // // Becasue this.getClass().getFields() is not guaranteed to return an
        // array in a particular order this is not used.
        // String result = new String();
        // Field[] tFields = this.getClass().getFields();
        // // Add name of first Field in tFields
        // result += tFields[ 0 ].getName();
        // // Add names of all but the first Field in tFields preceeded by a
        // comma
        // for ( int i0 = 1; i0 < tFields.length; i0 ++ ) {
        // result += "," + tFields[ i0 ];
        // }
        String result = "RecordID";
        return result;
        // String result = new String();
        // String[] toStringSplit = toString().split( "," );
        // String[] stringVariableNameAndValue;
        // stringVariableNameAndValue = toStringSplit[ 0 ].split( " " );
        // result += stringVariableNameAndValue[ 0 ];
        // for ( int i = 1; i < toStringSplit.length; i ++ ) {
        // stringVariableNameAndValue = toStringSplit[ i ].split( " " );
        // if ( stringVariableNameAndValue.length != 0 ) {
        // result += "," + stringVariableNameAndValue[ 0 ];
        // }
        // }
        // return result;
    }

    /**
     * Writes out:
     * <ul>
     * <li>this.RecordID as a <code>long</code></li>
     * <li>this.hashCode as a <code>int</code></li>
     * </ul>
     * to aRandomAccessFile.
     *
     * @param raf The <code>RandomAccessFile</code> written to.
     * @throws java.io.IOException
     */
    public void write(RandomAccessFile raf) throws IOException {
        raf.writeLong(this.RecordID);
    }

    /**
     * @param object
     * @return
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if ((object == null) || (object.getClass() != this.getClass())) {
            return false;
        }
        Generic_AbstractDataRecord rec = (Generic_AbstractDataRecord) object;
        return (this.RecordID == rec.RecordID);
    }

    /**
     * Method required by Comparable
     *
     * @param obj
     * @see java.lang.Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(Object obj) {
        if (obj instanceof Generic_AbstractDataRecord) {
            Generic_AbstractDataRecord rec = (Generic_AbstractDataRecord) obj;
            if (rec.RecordID == this.RecordID) {
                return 0;
            }
            if (rec.RecordID > this.RecordID) {
                return 1;
            }
        }
        return -1;
    }

    /**
     * @return The size (in <code>bytes</code>) of this as a <code>long</code>.
     *
     */
    public long getSizeInBytes() {
        return ((long) Long.SIZE) / getNumberOfBitsInByte();
    }
}
