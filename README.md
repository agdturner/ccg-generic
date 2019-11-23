# Generic

https://github.com/agdturner/agdt-java-generic

A Java library with generally useful classes and methods that have been abstracted from other libraries which now depend on this. Highlights:
1. Generic_Archive
https://github.com/agdturner/agdt-java-generic/blob/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/io/Generic_Archive.java
A class of methods for storing data on disk in a well organised and extendable way. Such archives can be used to cache data to help free up fast access memory for data processing. They can also be used to store output from different runs of a program.
2. memory
https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/memory
A package that helps avoid and deal with OutOfMemoryErrors. The avoidance generally entails caching data from the fast access memory of a machine to files and this tends to make use of a Generic_Archive.


## Package details

### 1. core

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/core

#### Generic_Environment
A generic environment class for those things that most objects might want to access.

#### Generic_ErrorAndExceptionHandler
A set of int codes for representing different types of Errors and Exceptions. 

#### Generic_Object
A serializable class that holds a transient reference to a Generic_Environment instance (env).
- By extending this class any object holds a reference to env.

#### Generic_Strings
A class that holds commonly used Strings.


### 2. execution

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/execution

For handling processing/execution.

#### Generic_Execution
Generic utility class for execution handling.


### 3. io

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/io

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

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/lang

#### Generic_String
Utility methods for String transformations. Most of these are redundant and deprecated.


### 5. memory

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/memory

#### Generic_MemoryManager
A class to be extended for memory management involving the caching data from the fast access memory to files and the handling of OutOfMemoryErrors should they be encountered.

#### Generic_Memory
An interface for memory management.

#### Generic_TestMemory
A class with methods that help in testing the avilablity and usage of computer memory.


### 6. time

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/time

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

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/util

#### Generic_Collections
For processing and manipulating collections including Lists, Arrays, Sets and Maps.

#### Generic_Time
This predates java.time but was used in programs that ticked through time acting effectively like a clock. It also holds methods to help process dates and aggregate results to hours and months etc. Not to be confused with time.Generic_Time


### 8. visualisation

https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/andyt/generic/visualisation

#### Generic_Visualisation
A class for holding generic visualisation methods.


## Current Version and platform requirements
Java 8 or higher.
The current development version is 1.0-Snapshot.

## Development Roadmap
### Version 1.0.0
- Standardise variable and method names.
- Remove deprecated methods.
- Review and develop unit tests.
- Proposed release date - undecided.

## Dependencies
There are no third party dependencies, but JUnit 5 is used for unit testing. Please see the pom.xml for details.

## Contributions
Please raise issues and submit pull requests in the usual way. Contributions will be acknowledged.

## LICENCE
Please see the standard Apache 2.0 open source LICENCE.
