# generic
## Description
A generic Java library that has no third party library dependencies.
  - It is generally for things that are commonly used in other Java libraries that the developer has developed.
  - The highlights are:
    - io
      - a package that has some useful way of storing data in archives
        - data directories on a file system in an auto expanding subdirectory structure (for details see below). 
    - math
      - a package that has some useful BigDecimal arithmetic.
    - memory
      - a package that helps avoid and deal with OutOfMemoryErrors.
## Current Package Contents
Currently, the library contains 10 main packages which are described in the Package Details section below:
1. core
2. data
3. execution
4. io
5. lang
6. logging
7. math
  - stats
  - primes
8. memory
9. utilities
  - time
10. visualisation
  - charts
## Current Version
Deprecated and unsupported!
  - This is essentially research software, but an attempt is being made to standardise the software development and evolve it following the principles of software carpentry advocated by the Software Sustainability Institute (https://www.software.ac.uk/programmes-events/carpentries/software-carpentry).
Currently the version of the library is known as Version 1.0-Snapshot. This is essentially still working towards a version 1.0.0 release.
The library should run on Java 8 or higher.
## Development Roadmap
The Roadmap for the development of Version 1.0.0 of the library is as follows and it is planed that this release will be available in 2018:
- For 1.0.0
  - The code base will be rationalised by:
    - Moving the data, math and visualisation packages to other libraries.
    - Deprectaing and removing the lang package.
  - Each class will be refactored to:
    - Better use generics to further rationalise the code base
    - Standardise variable and method names
    - Utilise features in Java 8 especially those that utilise functional programming with lambda expressions and Streams.
    - Improve documentation
      - The LICENSE has been changed to Apache 2.0, so all source files need updating to this license.
### Proposed Package Contents
Currently, the library contains 10 main packages which are described in the Package Details section below:
1. core
2. execution
3. io
4. logging
5. memory
6. util
  - time
    
## Package Details 
### 1. core
Classes that are generally used.
Contents
  - Generic_Environment
    - This holds
      - A Generic_Strings instance for sharing commonly used Strings.
      - A Generic_Files instance for getting commonly used Files.
  - Generic_ErrorAndExceptionHandler
    - A set of int codes for representing different types of Errors and Exceptions. 
  - Generic_Object
    - A serializable class that holds a transient reference to a Generic_Environment instance (Env).
    - By extending this class any object holds a reference to Env. It is concieved that Env is shared by all objects in order to share things in common for convenience and brevity an so that memory handling can be organised. 
  - Generic_Strings
    - A class that holds commonly used Strings.
### 2. data
Classes that are to do with data.
  - The intention is to move this package to a different library with a sole purpose of handling data.
Contents
  - Generic_Interval_long1
    - A Serializable, Comparable closed interval with upper and lower bounds stored as longs.
      - This is utilised mostly by classes in the visualisation package.
  - Generic_UKPostcode_Handler
    - A class for handling UK Postcodes.
    - The intention is to refactor this class and to move it to a different library with a sole purpose of handling postcode data.
  - Generic_XYNumericalData
    - This currently holds two values as BigDecimal values and is used by classes in the visualisation package.
    - The intention is to refactor this class and to move it to a different library with a sole purpose of visualisation.
    and x and y
### 3. execution
Classes to do with execution:
  - scheduling and executing Threads.
Contents
  - Generic_AgeConverter
    - A class for converting and classifying age groups.
    - The intention is to refactor this class and to move it to a different library.
  - Generic_EventListener
    - A Functional EventListener class with a single abstract method:
      - renderingComplete(Generic_RenderingCompleteEvent)
  - ...
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
