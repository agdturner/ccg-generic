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
 * The class holds a reference to: 1) A Generic_Strings instance for sharing
 * commonly used Strings. 2) A Generic_Files instance for getting commonly used
 * Files.
 */
public class Generic_Environment {

    protected Generic_Files Files;
    protected Generic_Strings Strings;

    protected Generic_Environment() {
    }

    public Generic_Environment(Generic_Files f, Generic_Strings s) {
        Files = f;
        Strings = s;
    }

    public Generic_Files getFiles() {
        if (Files == null) {
            Files = new Generic_Files();
        }
        return Files;
    }

    public Generic_Strings getStrings() {
        if (Strings == null) {
            Strings = new Generic_Strings();
        }
        return Strings;
    }

}
