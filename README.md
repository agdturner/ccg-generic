# agdt-generic
## Description
A Java library that has no other dependencies than Java (although for running unit tests there is a dependency on JUnit). It is generally for things that are commonly used in other Java developed by the developer and contains generally useful classes and methods.
The highlights are:
- io
  - A package that provides a useful way of storing data in archives
    - data directories on a file system in an auto expanding subdirectory structure (for details see below). 
- memory
  - a package that helps avoid and deal with OutOfMemoryErrors.
## Package Contents
The library contains 9 packages which are detailed in the Package Details section below:
1. core
2. execution
3. io
4. lang
5. logging
6. memory
7. time
8. util
9. visualisation
## Current Version and platform requirements
The library should run on Java 8 or higher.
Version 1.0.0 is available.
The current development version is 1.0-Snapshot.
## Development Roadmap
- Future releases on the 1.x versions will focus on improving documentation.
- For 2.x version
  - Each class will be refactored to:
    - Better use generics to further rationalise the code base
    - Standardise variable and method names
    - Utilise features in Java 8 especially those that utilise functional programming with lambda expressions and Streams.
    - Include any other generally useful code that best fits here rather than in any other library developed by the developer.
    
## Package Details 
### 1. core
Contents
  - Generic_Environment
    - This holds:
      - A Generic_Strings instance for sharing commonly used Strings.
      - A Generic_Files instance for getting commonly used Files.
  - Generic_ErrorAndExceptionHandler
    - A set of int codes for representing different types of Errors and Exceptions. 
  - Generic_Object
    - A serializable class that holds a transient reference to a Generic_Environment instance (Env).
    - By extending this class any object holds a reference to Env. It is concieved that Env is shared by all objects in order to share things in common for convenience and brevity an so that memory handling can be organised. 
  - Generic_Strings
    - A class that holds commonly used Strings.
### 2. execution
Contents
  - Generic_Execution
    - Generic utility class for execution handling.
### 3. io
Classes to handle things to do with input and output.
Contents
  - Generic_Files
    - A class for helping to organise data Files.
      - It is usual that for data processing tasks there is a data directory (DataDir).
      - Input data can be stored within DataDir in an input data directory (InputDataDir).
      - Generated data can be stored within DataDir in a generated data directory (GeneratedDataDir).
      - Output data can be stored within DataDir in an output data directory (OutputDataDir).
    - This class helps standardise the names for InputDataDir, GeneratedDataDir and OutputDataDir and provides methods to get Files within these.
  - Generic_IO
    - This class currently does a couple of different things:
      - It helps set up and get Files withing archives.
        - An archive is a form of data base where each element is given a unique long numerical index to be effectively stored in a File at a location given by the index known as a LeafFile. The 1st element in the data base is indexed by 0L, the 2nd element in the data base is indexed by 1L etc. An archive is comprised of a based File directory (BaseDir) in which there is one or more File directory - collectively known as subdirectories. Each subdirectory may contain a further layer of subdirectories and so on. There depth of each subdirectory is given by how many subdirectories it is in from the BaseDir. Subdirectories are given standardised names such that it is easy to find and infer the location of any LeafFile. The number of subdirectories in BaseDir, the number of subdirectories in any subdirectory, and the number of LeafFiles in any directory is controled by a parameter (NumberOfFilesPerDirectory).
          - For example, if NumberOfFilesPerDirectory was set to 10, there would be at most 10 subdirectories in BaseDir and any subdirectories, and at most 10 LeafFiles in any subdirectory.
        - Archives can dynamically grow to store more elements. As needed further depth is added to the directory structure as and when this is required.
        - There are two main utility of archives:
          1. As a cache for data used in a program. Data can easily be serialized and swapped out to a LeafFiles (cached) and then loaded back in as necessary. The reason for caching data is to free up fast access memory for a program when the available memory runs low.
          2. For storing log files where after each run of a program a new log file can be added to the archive.
### 4. lang
Contents
  - Generic_String
    - Some useful methods for transforming Strings.
    - Much of this can be improved using regular expressions.
### 5. logging
Contents
  - Generic_Log
    - A general abstract logging class.
### 6. memory
Contents
  - Generic_OutOfMemoryErrorHandler
    - A class to be extended for memory management involving the controlled swapping of parts of data from the fast access memory to files and the handling of OutOfMemoryErrors should they be encountered.
  - Generic_OutOfMemoryErrorHandlerInterface
    - An interface for handling OutOfMemoryErrors.
  - Generic_TestMemory
    - A class with methods that help in testing computer memory.
### 7. time
Contents
  - Generic_Date
    - Holds a reference to a LocalDate and provides methods to compare and process dates.
  - Generic_LocalDateRange
    - Simply holds a LocalDate start and a LocalDate end.
  - Generic_Time
    - Holds a reference to a LocalDateTime and provides methods to compare and process times.
    - Not to be confused with: util.Generic_Time
  - Generic_YearMonth
    - Holds a reference to a YearMonth and provides methods to compare and process year-months.
### 8. util
Contents
  - Generic_Collections
    - For processing and manipulating collections including Lists, Arrays, Sets and Maps.
  - Generic_Time
    - This predates java.time but was used in programs that ticked through time acting effectively like a clock. ~It also holds methods to help process dates and aggregate results to hours and months etc. Not to be confused with time.Generic_Time
### 9. visualisation
Contents
- Generic_Visualisation
  - A class for holding generic visualisation methods.
