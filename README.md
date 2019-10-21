# agdt-java-generic

## Description
A Java library of generally useful functionality. A basic extension of what is available in Java 8 or higher. There are no other dependencies. The highlights are:
- Generic_Archive: A class that offers a set of methods for storing data in files (for details see below). 
- memory: A package that helps avoid and deal with OutOfMemoryErrors (by swapping data from the fast access memory of a machine to files using Generic_Archive).

## Package Contents
The library contains 9 packages which are detailed in the Package Details section below:
1. core
2. execution
3. io
4. lang
5. memory
6. time
7. util
8. visualisation

## Current Version and platform requirements
Java 8 or higher.
The current development version is 1.0-Snapshot.

## Development Roadmap
### Version 1.0.0
- Standardise variable and method names.
- Review and develop Unit Tests.
    
## Package Details 

### 1. core
#### Generic_Environment
For the generic environment to be shared by nearly all Objects.
#### Generic_ErrorAndExceptionHandler
A set of int codes for representing different types of Errors and Exceptions. 
#### Generic_Object
A serializable class that holds a transient reference to a Generic_Environment instance (env).
- By extending this class any object holds a reference to env.
#### Generic_Strings
A class that holds commonly used Strings.

### 2. execution
For handling processing/execution.
#### Generic_Execution
Generic utility class for execution handling.

### 3. io
For handling input and output (IO).
#### Generic_Archive
An archive is a form of data base where each element is given a unique long numerical index to be effectively stored in a File at a location given by the index known as a leafFile. The 1st element in the data base is indexed by 0L, the 2nd element in the data base is indexed by 1L, the 3rd element in the data base is indexed by 2L, and so on... An archive is comprised of a based directory in which there are subdirectories. Each subdirectory may contain a further layer of subdirectories and again each of these may also contain further subdirectories, and so on... How many subdirectories there are and how many level of these there are is determined by a parameter (n). Subdirectories are given standardised names such that it is easy to find and infer the location of leafFiles.
If n was set to 10, there would be at most 10 subdirectories in baseDir and any subdirectories, and at most 10 leafFiles in any subdirectory.
Archives dynamically grow to store more elements.
Archives can be used to cache and swap data and this can free up fast access memory for data processing.
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
General IO utility class

### 4. lang
#### Generic_String
Utility methods for String transformations.

### 5. memory
#### Generic_OutOfMemoryErrorHandler
A class to be extended for memory management involving the controlled swapping of parts of data from the fast access memory to files and the handling of OutOfMemoryErrors should they be encountered.
#### Generic_OutOfMemoryErrorHandlerInterface
An interface for handling OutOfMemoryErrors.
#### Generic_TestMemory
A class with methods that help in testing computer memory.

### 6. time
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
#### Generic_Collections
For processing and manipulating collections including Lists, Arrays, Sets and Maps.
#### Generic_Time
This predates java.time but was used in programs that ticked through time acting effectively like a clock. It also holds methods to help process dates and aggregate results to hours and months etc. Not to be confused with time.Generic_Time

### 8. visualisation
Generic_Visualisation
A class for holding generic visualisation methods.
