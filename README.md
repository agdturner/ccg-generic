# [ccg-generic](https://github.com/agdturner/ccg-generic)

## Description
A [modularised](https://en.wikipedia.org/wiki/Java_Platform_Module_System) Java library only dependent on the [openJDK](https://openjdk.java.net/) and [ccg-io](https://github.com/agdturner/ccg-io) providing:
- [memory](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/memory) - a package for memory management that specifically helps avoid and handle instances of [OutOfMemoryError](https://cr.openjdk.java.net/~iris/se/15/latestSpec/api/java.base/java/lang/OutOfMemoryError.html). The handling generally entails copying data from the fast access memory (RAM) to the slow access memory (disk) and then clearing it from RAM - a process sometimes known as "swapping to disk". Application data stored on disk is typically organised using [IO_Cache](https://github.com/agdturner/ccg-io/blob/master/src/main/java/uk/ac/leeds/ccg/io/IO_Cache.java) data stores. Managing memory well so that algorithms remain efficient often involves knowing what parts of data are likely to be needed and when. This allows for proacive management rather then reactive management (which has to happen when a memory store is full and more memory on that store is being demanded). 
- Functionality that has been found commonly useful and was not available in what was the latest [openJDK](https://openjdk.java.net/) at the time.

(See below for [Details](#Details).)

## Latest versioned release
Developed and tested on [Java Development Kit, version 15](https://openjdk.java.net/projects/jdk/15/).
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-generic -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-generic</artifactId>
    <version>2.0</version>
</dependency>
```
[JAR](https://repo1.maven.org/maven2/io/github/agdturner/agdt-java-generic/2.0/agdt-java-generic-2.0.jar)

## Development plans/ideas
- There are no known issues or feature requests.
- Develop in an [agile](https://en.wikipedia.org/wiki/Agile_software_development) way.
- As the [OpenJDK](https://openjdk.java.net/) develops the functionality of this library may become redundant.

## Development history
### Changes from version 1.11 to version 2.0
The part of the library that provided a cache and some generic input and output utility methods was abstracted to [ccg-io](https://github.com/agdturner/ccg-io) which became a dependency for this.
### Origin
This code began development bundled together with lots of other code developed for an academic research project. Gradually, common code generated from other academic research projects formed into this library which is being rationalised.

## Contributions
- Welcome.

## LICENSE
- [APACHE LICENSE, VERSION 2.0](https://www.apache.org/licenses/LICENSE-2.0)

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

#### [Generic_Files](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/io/Generic_Files.java)
A class for helping to organise data Files.
- It is usual that for data processing tasks there is a data directory (dataDir).
- Input data are to be stored within dataDir in an input directory (inputDir).
- Generated data are to be stored within dataDir in a generated directory (generatedDir).
- Output data are to be stored within dataDir in an output directory (outputDir).

#### [Generic_IO](https://github.com/agdturner/agdt-java-generic/tree/master/src/main/java/uk/ac/leeds/ccg/generic/io/Generic_IO.java)
General Input/Output utility class mostly now just containing methods for setting the syntax of StreamTokenizers.


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
