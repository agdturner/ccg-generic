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

package uk.ac.leeds.ccg.generic.time;

import java.time.LocalDate;

/**
 * Used for representing a single continuous date period using two
 * {@link java.time.LocalDate} instances - {@link #start} and {@link #end}.
 *
 * @author Andy Turner
 * @version 1.1
 */
@Deprecated //Use java.time.Period.
public class Generic_LocalDateRange {

    private final LocalDate start;
    private final LocalDate end;

    /**
     * Create a new instance.
     * 
     * @param start Start of range.
     * @param end End of range.
     */
    public Generic_LocalDateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @return the start
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * @return the end
     */
    public LocalDate getEnd() {
        return end;
    }
    
    /**
     * @return the number of days between start and end. 
     */
    public int getDays(){
        return start.until(end).getDays();
    }

    
}
