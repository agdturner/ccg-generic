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

import uk.ac.leeds.ccg.andyt.generic.io.Generic_Files;

/**
 * A class for constructing a generic environment object. Normally there is only
 * one such object in a running program. It is used to provide access to data
 * generically useful objects that are commonly wanted and used. The idea is
 * that there can be one main copy of such objects and all other things that
 * need them can simply hold a reference rather than another instance saving
 * memory.
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_Environment {

    /**
     * A sharable instance for convenience.
     */
    protected Generic_Files Files;

    /**
     * A sharable instance for convenience.
     */
    protected Generic_Strings Strings;

    /**
     * Creates a new instance.
     */
    protected Generic_Environment() {
    }

    /**
     * Creates a new instance.
     *
     * @param f What {@link #Files} is set to.
     * @param s What {@link #Strings} to set to.
     */
    public Generic_Environment(Generic_Files f, Generic_Strings s) {
        Files = f;
        Strings = s;
    }

    /**
     * If {@link #Files} is <code>null</code> then it is initialised via the
     * {@link Generic_Files#Generic_Files()}.
     *
     * @return {@link #Files}
     */
    public Generic_Files getFiles() {
        if (Files == null) {
            Files = new Generic_Files();
        }
        return Files;
    }

    /**
     * If {@link #Strings} is <code>null</code> then it is initialised using
     * {@link Generic_Strings#Generic_Strings()}.
     *
     * @return {@link #Strings}
     */
    public Generic_Strings getStrings() {
        if (Strings == null) {
            Strings = new Generic_Strings();
        }
        return Strings;
    }

}
