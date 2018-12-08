# generic

A generic Java library. This has no other dependencies.

It is generally for things that are commonly used in other Java libraries that the developer has developed.

The library contains 10 main packages which are described below in turn:
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

1. core
Contents
  - Generic_Environment
    - This holds:
      - A Generic_Strings instance for sharing commonly used Strings.
      - A Generic_Files instance for getting commonly used Files.
  - Generic_ErrorAndExceptionHandler
    - A set of int codes for representing different types of Errors and Exceptions. 
  - Generic_Object
    - A serializable class that holds a reference to a Generic_Environment instance *env*.
    - By extending this class any object holds a reference to *env* which is usually expected to be shared by all objects to share things and for memory handling reasons 
  - Generic_Strings
    - A class that holds commonly used Strings.
    
2. Data
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
