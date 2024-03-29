/*
 * Copyright 2022 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.generic.core.id;

import java.io.Serializable;

/**
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class IDIntInt extends Object implements Serializable,
        Comparable<IDIntInt> {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the row.
     */
    protected int row;

    /**
     * For storing the column.
     */
    protected int col;

    /**
     * Create a new instance.
     */
    protected IDIntInt() {
    }

    /**
     * Create a new instance.
     *
     * @param i Used to initialise {@code this}.
     */
    public IDIntInt(IDIntInt i) {
        col = i.col;
        row = i.row;
    }

    /**
     * Create a new instance.
     *
     * @param row The row.
     * @param col The column.
     */
    public IDIntInt(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return col
     */
    public int getCol() {
        return col;
    }

    /**
     * @return a description of this
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[Row=" + row + ", Col=" + col + "]";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + row;
        hash = 97 * hash + col;
        return hash;
    }

    /**
     * Overrides equals in Object.
     *
     * @param o The Object to compare this with.
     * @return {@code true} if this is equal to {@code o} and {@code false}
     * otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (o.getClass() != getClass())) {
            return false;
        }
        IDIntInt i = (IDIntInt) o;
        return ((col == i.col)
                && (row == i.row));
    }

    /**
     * Method required by Comparable.
     *
     * @param i The instance to compare with.
     * @return -1, 0, 1 depending on whether this is less than, the same or
     * greater than {@code i}.
     */
    @Override
    public int compareTo(IDIntInt i) {
        if (i.row > row) {
            return 1;
        }
        if (i.row < row) {
            return -1;
        }
        if (i.col > col) {
            return 1;
        }
        if (i.col < col) {
            return -1;
        }
        return 0;
    }
}
