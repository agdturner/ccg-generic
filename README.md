# generic
## Description
A generic Java library that has no dependencies.
  - It contains classes and methods that are commonly used in other Java libraries that the developer has developed.
  - The highlights are:
    - io
      - A package that provides a useful way of storing data in archives
        - data directories on a file system in an auto expanding subdirectory structure (for details see below). 
    - memory
      - a package that helps avoid and deal with OutOfMemoryErrors.
## Current Package Contents
Currently, the library contains 9 packages which are detailed in the Package Details section below:
1. core
2. execution
3. io
4. lang
5. logging
6. memory
7. time
8. util
9. visualisation
## Current Version
1.0-Snapshot.
The library should run on Java 8 or higher.
## Development Roadmap
- For 2.0
  - Each class will be refactored to:
    - Better use generics to further rationalise the code base
    - Standardise variable and method names
    - Utilise features in Java 8 especially those that utilise functional programming with lambda expressions and Streams.
    - Improve documentation
    
## Package Details 
### 1. core
Classes that are generally used.
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
Classes to do with scheduling and executing processes/threads. This package is poorly documented
Contents
  - Generic_EventListener
    - A Functional EventListener class with a single abstract method:
      - renderingComplete(Generic_RenderingCompleteEvent)
  - Generic_EventListenerImpl
  - Generic_Execution
  - Generic_Executor
  - Generic_ImageWriter
  - Generic_PropertyChangedListener
  - Generic_PropertyChangedListenerImpl
  - Generic_RenderingCompleteEvent
  - Generic_Runnable
  - Generic_ScheduledExecutor
  
### 4. io
The io package contains classes to handle things to do with input and output.
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
  - Generic_ReadCSV
    - A utility class for reading CSV format files.
    - The intension is to move this to a subpackage in the data package before moving it to another library. 
  - Generic_XMLDOMReader
    - A utility class for reading XML format files.
    - The intention is to move this to a subpackage in the data package before moving it to another library. 
### 5. lang
- The intention is to deprecate and remove the need for this package.
  - Current thinking is that everything it does can be done better using regular expressions and that it might be better to call approriate methods from Java directly rather than have and use the classes and methods in this package.
### 6. logging
Classes that are to do with logging.
- The utility and use of the classes in this package needs to be reviewed.
### 7. math
- The intention is to move this to another library. 
### 8. memory
A package of classes for testing fast access memory utilisation.
  - This is used to prevent and deal with OutOfMemory errors in running programs.
### 9. utilities
  - time
### 10. visualisation
- The intention is to move this to another library.
  - charts
