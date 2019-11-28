# Generic

https://github.com/agdturner/agdt-java-generic

A Java library with generally useful classes and methods.

Highlights:
1. Generic_Archive
https://github.com/agdturner/agdt-java-generic/blob/master/src/main/java/uk/ac/leeds/ccg/agdt/generic/io/Generic_Archive.java
A class of methods for storing data on disk in a well organised and extendable way. Such archives can be used to cache data to help free up fast access memory for data processing. They can also be used to store output from different runs of a program.
2. memory
https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/agdt/generic/memory
A package that helps avoid and deal with OutOfMemoryErrors. The avoidance generally entails caching data from the fast access memory of a machine to files and this tends to make use of a Generic_Archive for organising those files.

This code has been abstracted from numerous other libraries which now depend on this.

## Status, Current Version and platform requirements
Version 1.0.0 is in the process of being released. Ahead of this release the code is being reviewed and documentation improved.
Developed and tested on Java 11.

## Development Roadmap
### Version 1.0.x
- For bug fixes of 1.0 as required.
- These are to be released on a best effort basis.
### Version 1.1.0
- Standardise logging - Currently no logging framework is used, but it would probably be good to use one. This development is being considered.
### Version 1.2.0
- Potentially add other features. This development is being considered. If it gets underway then some details will be posted here.

## Dependencies
Please see the pom.xml for details.
There are no third party dependencies.
JUnit 5 is used for unit testing.

## Contributions
Please raise issues and submit feature requests in the usual way.
Contributions to developing this code are to be acknowledged as appropriate.

## LICENCE
Please see the standard Apache 2.0 open source LICENCE.


## Package details

### 1. core

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/core

#### Generic_Environment
A generic environment class for those things that most objects might want to access.

#### Generic_Object
A serializable class that holds a transient reference to a Generic_Environment instance (env).
- By extending this class any object holds a reference to env.

#### Generic_Strings
A class that holds commonly used Strings.


### 2. executionagdt

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/agdt/generic/execution

For handling processing/execution.

#### Generic_Execution
Generic utility class for execution handling.


### 3. io

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/agdt/generic/io

For handling input and output (IO).

#### Generic_Archive
A class of methods for storing data in archives on disk. An archive is a form of data base where each element is given a unique long numerical index and is stored in a file at a location given by the index known as a leaf file. The 1st element in the data base is indexed by 0L, the 2nd element in the data base is indexed by 1L, the 3rd element in the data base is indexed by 2L, and so on... An archive is comprised of a base directory (baseDir) in which there are subdirectories. Each subdirectory may contain a further layer of subdirectories and again each of these may also contain further subdirectories, and so on... How many subdirectories there are and how many leaf files are stored in the top level directories is determined by a single parameter (n). Subdirectories are given standardised names such that it is easy to find and infer the location of leafFiles.

If n was set to 10, there would be at most 10 subdirectories in baseDir and any subdirectories, and at most 10 leaf files in any subdirectory.

Archives may dynamically grow to store more elements.

Archives can be used to cache data and this can free up fast access memory for data processing.

They can also be used to store output from different runs of a program.

#### Generic_Defaults
A class for storing defaults used in IO.

#### Generic_Files
A class for helping to organise data Files.
- It is usual that for data processing tasks there is a data directory (dataDir).
- Input data are to be stored within dataDir in an input directory (inputDir).
- Generated data are to be stored within dataDir in a generated directory (generatedDir).
- Output data are to be stored within dataDir in an output directory (outputDir).

#### Generic_IO
General IO utility class for initialising things like BufferedReaders and for setting the syntax of StreamTokenizers.


### 4. lang

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/agdt/generic/lang

#### Generic_String
Utility methods for String transformations. Most of these are redundant and deprecated.


### 5. memory

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/agdt/generic/memory

#### Generic_MemoryManager
A class to be extended for memory management involving the caching data from the fast access memory to files and the handling of OutOfMemoryErrors should they be encountered.

#### Generic_Memory
An interface for memory management.

#### Generic_TestMemory
A class with methods that help in testing the avilablity and usage of computer memory.


### 6. time

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/agdt/generic/time

#### Generic_Date
Holds a reference to a LocalDate and provides methods to compare and process dates.

#### Generic_LocalDateRange
Simply holds a LocalDate start and a LocalDate end.

#### Generic_Time
Holds a reference to a LocalDateTime and provides methods to compare and process times.
- Not to be confused with: util.Generic_Time

#### Generic_YearMonth
Holds a reference to a YearMonth and provides methods to compare and process year-months.


### 7. util

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/agdt/generic/util

#### Generic_Collections
For processing and manipulating collections including Lists, Arrays, Sets and Maps.

#### Generic_Time
This predates java.time but was used in programs that ticked through time acting effectively like a clock. It also holds methods to help process dates and aggregate results to hours and months etc. Not to be confused with time.Generic_Time


### 8. visualisation

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/agdt/generic/visualisation

#### Generic_Visualisation
A class for holding generic visualisation methods.
