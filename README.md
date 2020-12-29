# [agdt-java-generic](https://github.com/agdturner/agdt-java-generic)

## Description
A [modularised](https://en.wikipedia.org/wiki/Java_Platform_Module_System) Java library only dependent on the [openJDK](https://openjdk.java.net/) providing functionality:
- For storing and organising data in a [file system](https://en.wikipedia.org/wiki/File_system).
- That has been found commonly useful and was not available in what was the latest [openJDK](https://openjdk.java.net/) at the time.

### Highlights
- [Generic_FileStore](https://github.com/agdturner/agdt-java-generic/blob/master/src/main/java/uk/ac/leeds/ccg/generic/io/Generic_FileStore.java)
A class that helps stores and retrieve files in a tree of directories in a well organised (easy to retrieve) and extendable (easy to store more) way. See [the section below on Generic_FileStore](#Generic_FileStore) for a more detailed description. 
- [memory](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/memory)
A package that helps avoid and handle an [OutOfMemoryError](https://cr.openjdk.java.net/~iris/se/15/latestSpec/api/java.base/java/lang/OutOfMemoryError.html) if thrown. The handling generally entails copying data from the fast access memory (RAM) to the slow access memory (Disk) and then clearing it from RAM. The data stored on disk is typically organised using a [Generic_FileStore](https://github.com/agdturner/agdt-java-generic/blob/master/src/main/java/uk/ac/leeds/ccg/generic/io/Generic_FileStore.java). Managing this so that algorithms remain efficient involves knowing what parts of data are likely to be needed and when. 

## Latest Versions
Developed and tested on [Java Development Kit, version 15](https://openjdk.java.net/projects/jdk/15/).
### Stable
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-generic -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-generic</artifactId>
    <version>1.7.3</version>
</dependency>
```
[JAR](https://repo1.maven.org/maven2/io/github/agdturner/agdt-java-generic/1.7.3/agdt-java-generic-1.7.3.jar)

### Development
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-generic -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-generic</artifactId>
    <version>1.8-SNAPSHOT</version>
</dependency>
```
[//]: # (Move to version history section if/when a new version and summary are added)
[//]: # (New to 1.8-SNAPSHOT:)

## Development plans/ideas
- There are no feature requests or issues reported as yet.
- Review some of the functionality to see if better alternatives are in [Java Development Kit, version 15](https://openjdk.java.net/projects/jdk/15/) and deprecate as appropriate.

## Contributions
- Welcome.

## LICENSE
- [APACHE LICENSE, VERSION 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Acknowledgements
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research grants have supported the development of this library.

## Package details

### 1. [core](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/core)

#### [Generic_Environment](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/core/Generic_Environment.java)
A generic environment class for those things that most objects might want to access.

#### [Generic_Object](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/core/Generic_Object.java)
A serializable class that holds a transient reference to a Generic_Environment instance (env).

#### [Generic_Strings](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/core/Generic_Strings.java)
A class that holds commonly used Strings.


### 2. [execution](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/execution)

#### [Generic_Execution](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/execution/Generic_Execution.java)
Generic utility class for process execution handling.


### 3. [io](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/io)

#### [Generic_Defaults](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/io/Generic_Defaults.java)
A class for holding Input/Output (IO) defaults.

#### [Generic_FileStore](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/io/Generic_FileStore.java)
For storing files on disk in file store - a form of data base where each file is stored in a leaf directory. Leaf directories are found at level 0 of the file store. The 1st leaf directory has the name 0, the 2nd leaf directory has the name 1, the nth leaf directory has the name n where n is a positive integer . A file store is comprised of a base directory in which there is a root directory. The root directory indicates how many files are stored in the file store using a range given in the directory name. The minimum of the range is 0 and the maximum is a positive integer number. These two numbers are separated with by {@link #SEP} e.g. "0_99". The root directory will contain one or more subdirectories named in a similar style to the root directory e.g. "0_9". The maximum number will be less than or equal to that of the root directory. By comparing the range of numbers in the names of directories in the root directory with the range of numbers in the names of and subdirectory in the root directory it is possible to discern the range for the file store. The range is a parameter that can be set when initialising a file store. It controls how many subdirectories there can be at each level, and ultimately this controls how many levels of directories there are in the file store which is all dependent on the number of files stored in the file store.

Files are to be stored in the leaf directories. Each directory is given a standardised name such that it is easy to find and infer the path to the leaf directories.

If range was set to 10, there would be at most 10 subdirectories in each level of the file store.

File stores are initialised with 3 levels and dynamically grow to store more files. For range = 10 the initial tree can be represented as follows:
```
Level
2        - 1           - root

0        - 0_9         - 0_99
```
For range = 10 and n = 100001 the tree can be represented as follows:
```
Level
6      - 5             - 4             - 3             - 2             - 1               - root

0      - 0_9           - 0_99          - 0_999         - 0_9999        - 0_99999        - 0_999999
1
2
3
4
5
6
7
8
9
10     - 10_19
11
12
...
19
20     - 20_29
...
...
99     - 90_99
100    - 100_109       - 100_199
...
...
...
999    - 990_999       - 900_999
1000   - 1000_1009     - 1000_1099     - 1000_1999
...
...
...
...
9999   - 9990_9999     - 9900_9999     - 9000_9999
10000  - 10000_10009   - 10000_10099   - 10000_10999   - 10000_19999
...
...
...
...
...
99999  - 99990_99999   - 99900_99999   - 99000_99999   - 90000_99999
100000 - 100000_100009 - 100000_100099 - 100000_100999 - 100000_109999 - 100000_199999
100001
```

File stores are used for logging and may be used to store other outputs from different runs of a program. They can also be used to organise caches of data from a running program to help with memory management.

Although such a file store can store many files, there are limits depending on the range value set. The theoretical limit is close to Long.MAX_VALUE / range. But there can be no more than Integer.MAX_VALUE levels. Perhaps a bigger restriction is the size of the storage element that holds the directories and files indexed by the file store.


#### [Generic_Files](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/io/Generic_Files.java)
A class for helping to organise data Files.
- It is usual that for data processing tasks there is a data directory (dataDir).
- Input data are to be stored within dataDir in an input directory (inputDir).
- Generated data are to be stored within dataDir in a generated directory (generatedDir).
- Output data are to be stored within dataDir in an output directory (outputDir).

#### [Generic_IO](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/io/Generic_IO.java)
General Input/Output utility class for initialising things like BufferedReaders and for setting the syntax of StreamTokenizers. 
Also for reading from files, writing to files and copying and moving files.


### 4. [lang](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/lang)

#### [Generic_String](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/lang/Generic_String.java)
Utility methods for String transformations.


### 5. [math](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/math)

#### [Generic_Math](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/math/Generic_Math.java)
Contains methods for adding two Numbers and testing if Numbers can be stored as other types of Number if not exactly, then within a specific error bound.


### 6. [memory](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/memory)

#### [Generic_MemoryManager](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/memory/Generic_MemoryManager.java)
A class to be extended for memory management involving caching data from the fast access memory to files and then clearing this from the fast access memory. Most methods handle OutOfMemoryErrors should they be encountered, but generally the aim is to avoid these by testing for available memory and caching data prior to running very low on memory.

#### [Generic_Memory](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/memory/Generic_Memory.java)
An interface for memory management which outlines the methods required.

#### [Generic_TestMemory](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/memory/Generic_TestMemory.java)
A class with methods that help in testing the availablity and usage of fast access memory.


### 7. [time](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/time)

#### [Generic_Date](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/time/Generic_Date.java)
Holds a reference to a LocalDate and provides methods to compare and process dates.

#### [Generic_LocalDateRange](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/time/Generic_LocalDateRange.java)
Simply holds a LocalDate start and a LocalDate end.

#### [Generic_Time](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/time/Generic_Time.java)
Holds a reference to a LocalDateTime and provides methods to compare and process times.
- Not to be confused with: [uk.ac.leeds.ccg.generic.util.Generic_Time](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/util/Generic_Time.java)

#### [Generic_YearMonth](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/time/Generic_YearMonth.java)
Holds a reference to a YearMonth and provides methods to compare and process year-months.


### 8. [util](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/util)

#### [Generic_Collections](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/util/Generic_Collections.java)
Contains methods for processing and manipulating collections including Lists, Arrays, Sets and Maps.

#### [Generic_Time](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/util/Generic_Time.java)
This pre-dates java.time and was used in programs that ticked through time acting effectively like a stop-watch type clock. It holds methods to help with processing dates and aggregating data by time periods (e.g. hours, months).
- Not to be confused with [uk.ac.leeds.ccg.generic.time.Generic_Time](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/time/Generic_Time.java)


### 9. [visualisation](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/visualisation)

#### [Generic_Visualisation](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/visualisation/Generic_Visualisation.java)
A class with methods for visualisation that will work in headless environments.
