/*
 * Copyright 2020 Andy Turner, University of Leeds.
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

/**
 * Provides some general functionality.
 */
module uk.ac.leeds.ccg.generic {
    requires transitive java.logging;
    requires transitive java.desktop;
    
    requires transitive uk.ac.leeds.ccg.io;
    
    /**
     * Exports.
     */
    exports uk.ac.leeds.ccg.generic.core;
    exports uk.ac.leeds.ccg.generic.execution;
    exports uk.ac.leeds.ccg.generic.io;
    exports uk.ac.leeds.ccg.generic.lang;
    exports uk.ac.leeds.ccg.generic.math;
    exports uk.ac.leeds.ccg.generic.memory;
    exports uk.ac.leeds.ccg.generic.time;
    exports uk.ac.leeds.ccg.generic.util;
    exports uk.ac.leeds.ccg.generic.visualisation;
    opens uk.ac.leeds.ccg.generic.core;
    opens uk.ac.leeds.ccg.generic.execution;
    opens uk.ac.leeds.ccg.generic.io;
    opens uk.ac.leeds.ccg.generic.lang;
    opens uk.ac.leeds.ccg.generic.math;
    opens uk.ac.leeds.ccg.generic.memory;
    opens uk.ac.leeds.ccg.generic.time;
    opens uk.ac.leeds.ccg.generic.util;
    opens uk.ac.leeds.ccg.generic.visualisation;
}