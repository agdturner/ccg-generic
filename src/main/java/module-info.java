module uk.leeds.ccg.generic {
    requires transitive java.logging;
    requires java.desktop;
    exports uk.ac.leeds.ccg.generic.core;
    exports uk.ac.leeds.ccg.generic.execution;
    exports uk.ac.leeds.ccg.generic.io;
//    exports uk.ac.leeds.ccg.generic.lang;
    exports uk.ac.leeds.ccg.generic.math;
    exports uk.ac.leeds.ccg.generic.memory;
    exports uk.ac.leeds.ccg.generic.time;
//    exports uk.ac.leeds.ccg.generic.util;
    exports uk.ac.leeds.ccg.generic.visualisation;
//    opens uk.ac.leeds.ccg.generic.core;
//    opens uk.ac.leeds.ccg.generic.execution;
//    opens uk.ac.leeds.ccg.generic.io;
    opens uk.ac.leeds.ccg.generic.lang;
//    opens uk.ac.leeds.ccg.generic.math;
//    opens uk.ac.leeds.ccg.generic.memory;
//    opens uk.ac.leeds.ccg.generic.time;
    opens uk.ac.leeds.ccg.generic.util;
//    opens uk.ac.leeds.ccg.generic.visualisation;
}