/*
 * Copyright (C) 2017 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.core;

import java.io.Serializable;

/**
 * An abstract serializable class. Instances hold a transient reference to a
 * {@link Generic_Environment} instance.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public abstract class Generic_Object implements Serializable {

    public transient Generic_Environment e;

    /**
     * Creates a new instance.
     */
    protected Generic_Object() {
    }

    /**
     * Creates a new instance.
     *
     * @param e The {@link Generic_Environment} that {@link e} is set to.
     */
    public Generic_Object(Generic_Environment e) {
        this.e = e;
    }
}
