/**
 * Copyright 2010 Centre for Computational Geography, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_ErrorAndExceptionHandler;
import uk.ac.leeds.ccg.andyt.generic.execution.Generic_Execution;
//import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

//import java.nio.file.S
//TODO http://java.sun.com/docs/books/tutorial/essential/io/legacy.html#mapping
// http://java.sun.com/docs/books/tutorial/essential/io/fileio.html
/**
 * Class of static methods for helping with reading and writing (primarily)
 * to/from file.
 */
public class Generic_IO {

    public static final String CLASSNAME = "Generic_StaticIO";

    /**
     * Creates a new instance of ObjectIO
     */
    public Generic_IO() {
    }

    /**
     * Recursively traverses a directory creating a set of File paths of files
     * (i.e. not directories).
     *
     * @param file File.
     * @return TreeSet.
     */
    public static TreeSet<String> recursiveFileList(File file) {
        TreeSet<String> r = new TreeSet<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                TreeSet<String> subresult = recursiveFileList(file1);
                r.addAll(subresult);
            }
            return r;
        } else {
            r.add(file.toString());
            return r;
        }
    }

    /**
     * Recursively traverses a directory creating a set of File paths of files
     * (i.e. not directories) up to the specified depth.
     *
     * @param file File
     * @param depth The depth beyond which directories are not traversed.
     * @return TreeSet
     */
    public static TreeSet<String> recursiveFileList(File file, int depth) {
        TreeSet<String> r = new TreeSet<>();
        if (depth != 0) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    TreeSet<String> subresult = recursiveFileList(f, depth - 1);
                    r.addAll(subresult);
                }
                return r;
            } else {
                r.add(file.toString());
                return r;
            }
        }
        r.add(file.toString());
        return r;
    }

    /**
     * Writes Object o to File f.
     *
     * @param o Object to be written.
     * @param f File to write to.
     */
    public static void writeObject(Object o, File f) {
        try {
            f.getParentFile().mkdirs();
            ObjectOutputStream oos;
            oos = getObjectOutputStream(f);
            oos.writeUnshared(o);
            oos.flush();
            oos.reset();
            oos.close();
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
    }

    /**
     * Read Object from File f.
     *
     * @param f File to be read from.
     * @return Object read from f.
     */
    public static Object readObject(File f) {
        Object r = null;
        if (f.length() != 0) {
            try {
                ObjectInputStream ois;
                ois = getObjectInputStream(f);
                r = ois.readUnshared();
                ois.close();
            } catch (IOException e) {
                System.err.print(e.getMessage());
                e.printStackTrace(System.err);
                System.exit(Generic_ErrorAndExceptionHandler.IOException);
            } catch (ClassNotFoundException e) {
                System.err.print(e.getMessage());
                e.printStackTrace(System.err);
                System.exit(Generic_ErrorAndExceptionHandler.ClassNotFoundException);
            }
        }
        return r;
    }

    /**
     * Writes Object o to File f and reports the name of the Object written and
     * the file to stdout.
     *
     * @param o Object to be written.
     * @param f File to write to.
     * @param name String for reporting.
     */
    public static void writeObject(Object o, File f, String name) {
        writeObject(o, f);
        System.out.println("Written out " + name + " to " + f);
    }

    /**
     * Read File into an ArrayList. The ArrayList will have a size equal to the
     * number of lines in the file and each element will have all the characters
     * in a line represented as Strings.
     *
     * @param f The file to be returned as a String.
     * @return ArrayList
     */
    @Deprecated
    public static ArrayList<String> readIntoArrayList_String(File f) {
        return readIntoArrayList_String(f, 1);
    }

    /**
     * Read File into an ArrayList. The ArrayList will have a size equal to the
     * number of lines in the file and each element will have all the characters
     * in a line represented as Strings.
     *
     * @param f The file to be returned as a String.
     * @param n The number of lines after the first is printed to std_out using
     * System.out.println(line). (n should not be equal to 0)
     * @return ArrayList
     */
    @Deprecated
    public static ArrayList<String> readIntoArrayList_String(File f, int n) {
        ArrayList<String> result = null;
        if (f.exists()) {
            try {
                BufferedReader br;
                StreamTokenizer st;
                br = getBufferedReader(f);
                if (br != null) {
                    result = new ArrayList<>();
                    st = new StreamTokenizer(br);
                    int token = st.nextToken();
                    st.eolIsSignificant(true);
                    String line = "";
                    int RecordID;
                    RecordID = 0;
                    boolean lastTokenBeforeEndOfFileIsEndOfLine = false;
                    while (!(token == StreamTokenizer.TT_EOF)) {
                        switch (token) {
                            case StreamTokenizer.TT_EOL:
                                result.add(line);
                                System.out.println(line);
                                line = "";
                                if (RecordID % (n + 1) == 0) {
                                    System.out.println(line);
                                }
                                RecordID++;
                                lastTokenBeforeEndOfFileIsEndOfLine = true;
                                break;
                            case StreamTokenizer.TT_WORD:
                                line += st.sval;
                                lastTokenBeforeEndOfFileIsEndOfLine = false;
                                break;
                            case StreamTokenizer.TT_NUMBER:
                                double number;
                                number = st.nval;
                                if (number == (long) number) {
                                    line += (long) st.nval;
                                } else {
                                    line += st.nval;
                                }
                                lastTokenBeforeEndOfFileIsEndOfLine = false;
                                break;
                            default:
                                if (token == 26 || token == 160) {
                                    // A type of space " ". It is unusual as st 
                                    // probably already set to parse space as
                                    // words.
                                    line += (char) token;
                                } else if (token == 13) {
                                    // These are returns or tabs or something...
                                    //line += (char) token;
                                } else {
                                    //line += (char) token;
                                }
//                                System.out.println("line so far " + line);
//                                System.out.println("Odd token " + token + " \"" + (char) token + "\" encountered.");
                                lastTokenBeforeEndOfFileIsEndOfLine = false;
                        }
                        token = st.nextToken();
                    }
                    if (lastTokenBeforeEndOfFileIsEndOfLine == false) {
                        result.add(line);
                    }
                    br.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Read File into an ArrayList. The ArrayList will have a size equal to the
     * number of lines in the file and each element will have all the characters
     * in a line represented as Strings.
     *
     * @param f The file to be returned as a String.
     * @param n The number of lines after the first is printed to std_out using
     * System.out.println(line).
     * @param firstLine The first line (counting from 0) that is included.
     * @param lastLine The last line (counting from 0) that is included.
     * @return ArrayList
     */
    @Deprecated
    public static ArrayList<String> readIntoArrayList_String(File f, int n,
            int firstLine, int lastLine) {
        ArrayList<String> result = null;
        if (f.exists()) {
            try {
                BufferedReader br;
                StreamTokenizer st;
                br = getBufferedReader(f);
                if (br != null) {
                    result = new ArrayList<>();
                    st = new StreamTokenizer(br);
                    int token = st.nextToken();
                    st.eolIsSignificant(true);
                    String line = "";
                    int RecordID;
                    RecordID = firstLine;
                    int lineNumber;
                    lineNumber = 0;
                    boolean lastTokenBeforeEndOfFileIsEndOfLine = false;
                    while (!(token == StreamTokenizer.TT_EOF)) {
                        switch (token) {
                            case StreamTokenizer.TT_EOL:
                                lineNumber++;
                                if (lineNumber >= firstLine) {
                                    result.add(line);
                                    System.out.println(line);
                                    line = "";
                                    if (RecordID % n == 0) {
                                        System.out.println(line);
                                    }
                                    RecordID++;
                                    lastTokenBeforeEndOfFileIsEndOfLine = true;
                                }
                                break;
                            case StreamTokenizer.TT_WORD:
                                line += st.sval;
                                lastTokenBeforeEndOfFileIsEndOfLine = false;
                                break;
                            case StreamTokenizer.TT_NUMBER:
                                double number;
                                number = st.nval;
                                if (number == (long) number) {
                                    line += (long) st.nval;
                                } else {
                                    line += st.nval;
                                }
                                lastTokenBeforeEndOfFileIsEndOfLine = false;
                                break;
                            default:
                                if (token == 26 || token == 160) {
                                    // A type of space " ". It is unusual as st 
                                    // probably already set to parse space as
                                    // words.
                                    line += (char) token;
                                } else if (token == 13) {
                                    // These are returns or tabs or something...
                                    //line += (char) token;
                                } else {
                                    //line += (char) token;
                                }
//                                System.out.println("line so far " + line);
//                                System.out.println("Odd token " + token + " \"" + (char) token + "\" encountered.");
                                lastTokenBeforeEndOfFileIsEndOfLine = false;
                        }
                        if (lineNumber < lastLine) {
                            token = st.nextToken();
                        } else {
                            break;
                        }
                    }
                    if (lastTokenBeforeEndOfFileIsEndOfLine == false) {
                        result.add(line);
                    }
                    br.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * @param f A File which is not a directory to be copied.
     * @param outDir The File directory to copy to.
     */
    private static void copyFile(File f, File outDir) {
        copyFile(f, outDir, f.getName());
    }

    /**
     * @param f A File which is not a directory to be copied.
     * @param outDir A File that is the directory to copy to.
     * @param outputFileName The name for the file that will be created in the
     * outDir.
     */
    public static void copyFile(File f, File outDir, String outputFileName) {
        if (!f.exists()) {
            System.err.println("!input_File.exists() in "
                    + Generic_IO.class.getCanonicalName()
                    + ".copy(File(" + f + "),File(" + outDir + "))");
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outf = new File(outDir, outputFileName);
        if (outf.exists()) {
            System.out.println("Overwriting File " + outf + " in "
                    + Generic_IO.class.getCanonicalName()
                    + ".copy(File(" + f + "),File(" + outDir + "))");
        } else {
            try {
                outf.createNewFile();
            } catch (IOException e) {
                System.err.print(e.getMessage());
                System.err.println("Unable to createNewFile " + outf + " in "
                        + Generic_IO.class.getCanonicalName()
                        + ".copy(File(" + f + "),File(" + outDir + "))");
                e.printStackTrace(System.err);
                System.exit(Generic_ErrorAndExceptionHandler.IOException);
            }
        }
        try {
            BufferedInputStream bis;
            bis = getBufferedInputStream(f);
            BufferedOutputStream bos;
            bos = getBufferedOutputStream(outf);
            // bufferSize should be power of 2 (e.g. Math.pow(2, 12)), but nothing too big.
            int bufferSize = 2048;
            long numberOfArrayReads = f.length() / bufferSize;
            long numberOfSingleReads = f.length() - (numberOfArrayReads * bufferSize);
            byte[] b = new byte[bufferSize];
            for (int i = 0; i < numberOfArrayReads; i++) {
                bis.read(b);
                bos.write(b);
            }
            for (int i = 0; i < numberOfSingleReads; i++) {
                bos.write(bis.read());
            }
            bos.flush();
            bos.close();
            bis.close();
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
    }

    /**
     * @param f File.
     * @return BufferedInputStream
     */
    public static BufferedInputStream getBufferedInputStream(File f) {
        BufferedInputStream result;
        FileInputStream fis;
        fis = getFileInputStream(f);
        result = new BufferedInputStream(fis);
        return result;
    }

    /**
     * @param f File.
     * @return FileInputStream
     */
    public static FileInputStream getFileInputStream(File f) {
        FileInputStream result = null;
        try {
            result = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            System.err.println("Trying to handle " + ex.getLocalizedMessage());
            System.err.println("Wait for 2 seconds then trying again to "
                    + "getFileInputStream(File " + f.toString() + ").");
            if (!f.exists()) {
                //ex.printStackTrace(System.err);
                Logger.getLogger(Generic_IO.class.getName()).log(
                        Level.SEVERE, null, ex);
                // null will be returned...
            } else {
                // This can happen because of too many open files.
                // Try waiting for 2 seconds and then repeating...
                long wait = 2000L;
                try {
                    synchronized (f) {
                        f.wait(wait);
                    }
                } catch (InterruptedException ex2) {
                    Logger.getLogger(Generic_IO.class.getName()).log(
                            Level.SEVERE, null, ex2);
                }
                return getFileInputStream(f, wait);
            }
//        } catch (IOException e) {
//            e.printStackTrace(System.err);
//            Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    /**
     * @param f File.
     * @param wait Time in milliseconds to wait before trying to open the
     * FileInputStream again if it failed the first time (this may happen if
     * waiting for a file to be written).
     * @return FileInputStream
     */
    public static FileInputStream getFileInputStream(File f, long wait) {
        FileInputStream result = null;
        try {
            result = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            System.err.println("Trying to handle " + ex.getLocalizedMessage());
            System.err.println("Wait for " + wait + " miliseconds then trying "
                    + "again to getBufferedReader(File " + f.toString()
                    + ", long).");
            if (!f.exists()) {
                //ex.printStackTrace(System.err);
                Logger.getLogger(Generic_IO.class.getName()).log(
                        Level.SEVERE, null, ex);
                // null will be returned...
            } else {
                // This can happen because of too many open files.
                // Try waiting for 2 seconds and then repeating...
                Generic_Execution.waitSychronized(f, wait);
                return getFileInputStream(f, wait * 2);
            }
//        } catch (IOException e) {
//            e.printStackTrace(System.err);
//            Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, 
//                null, e);
        }
        return result;
    }

    /**
     * @param f File.
     * @return FileOutputStream
     */
    public static FileOutputStream getFileOutputStream(File f) {
        FileOutputStream r = null;
        try {
            r = new FileOutputStream(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Generic_IO.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return r;
    }

    /**
     * @param f File.
     * @return BufferedOutputStream
     */
    public static BufferedOutputStream getBufferedOutputStream(File f) {
        BufferedOutputStream r;
        FileOutputStream fos;
        fos = getFileOutputStream(f);
        r = new BufferedOutputStream(fos);
        return r;
    }

    /**
     * Returns a Buffered PrintWriter.
     *
     * @param f File.
     * @param append if true then file is to be appended to otherwise file is to
     * be overwritten.
     * @return BufferedWriter
     */
    public static BufferedWriter getBufferedWriter(File f, boolean append) {
        PrintWriter pw;
        pw = getPrintWriter(f, append);
        return new BufferedWriter(pw);
    }

    /**
     * @param f File.
     * @return ObjectInputStream
     */
    public static ObjectInputStream getObjectInputStream(File f) {
        ObjectInputStream result = null;
        BufferedInputStream bis;
        bis = getBufferedInputStream(f);
        try {
            result = new ObjectInputStream(bis);
        } catch (IOException ex) {
            Logger.getLogger(Generic_IO.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * @param f File.
     * @return ObjectOutputStream
     */
    public static ObjectOutputStream getObjectOutputStream(File f) {
        ObjectOutputStream result = null;
        BufferedOutputStream bos;
        bos = getBufferedOutputStream(f);
        try {
            result = new ObjectOutputStream(bos);
        } catch (IOException ex) {
            Logger.getLogger(Generic_IO.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return result;
    }

    private static void copyDirectory(File dirToCopy, File dirToCopyTo) {
        try {
            if (!dirToCopyTo.mkdir()) {
                dirToCopyTo.mkdirs();
            }
            if (!dirToCopy.isDirectory()) {
                throw new IOException("Expecting File " + dirToCopy
                        + " To be a directory in "
                        + Generic_IO.class.getName()
                        + ".copyDirectory(File,File)");
            }
            if (!dirToCopyTo.isDirectory()) {
                throw new IOException("Expecting File " + dirToCopyTo
                        + " To be a directory in "
                        + Generic_IO.class.getName()
                        + ".copy(File,File)");
            }
            File copiedDirectory_File = new File(dirToCopyTo,
                    dirToCopy.getName());
            copiedDirectory_File.mkdir();
            File[] dirToCopyFiles = dirToCopy.listFiles();
            for (File f : dirToCopyFiles) {
                if (f.isFile()) {
                    copyFile(f, copiedDirectory_File);
                } else {
                    copyDirectory(f, copiedDirectory_File);
                }
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
    }

    /**
     * @param fileOrDirToCopy File.
     * @param dirToCopyTo Directory.
     */
    public static void copy(File fileOrDirToCopy, File dirToCopyTo) {
        try {
            if (!dirToCopyTo.mkdir()) {
                dirToCopyTo.mkdirs();
            }
            if (!dirToCopyTo.isDirectory()) {
                throw new IOException("Expecting File " + dirToCopyTo
                        + "To be a directory in "
                        + Generic_IO.class.getName()
                        + ".copy(File,File)");
            }
            if (fileOrDirToCopy.isFile()) {
                copyFile(fileOrDirToCopy, dirToCopyTo);
            } else {
                copyDirectory(fileOrDirToCopy, dirToCopyTo);
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
    }

    /**
     * @return true iff file exists and can be read
     * @param file File.
     */
    public static boolean fileExistsAndCanBeRead(File file) {
        if (file.exists()) {
            if (file.canRead()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to delete file from the file system. This does no block other
     * reads or writes to the files system while it is executing and it will
     * only succeed in deleting empty directories.
     *
     * @param file File.
     * @return long[] result where: result[0] is the number of directories
     * deleted; result[1] is the number of files deleted.
     */
    public static long[] delete(File file) {
        long[] result = new long[2];
        result[0] = 0L;
        result[1] = 0L;
        if (file.isDirectory()) {
            // Delete all the files it contains
            File[] files = file.listFiles();
            for (File file1 : files) {
                long[] deleted = delete(file1);
                result[0] += deleted[0];
                result[1] += deleted[1];
            }
            // Delete the directory itself
            file.delete();
            result[0]++;
        } else {
            file.delete();
            result[1]++;
        }
        return result;
    }

    /**
     * @param file File.
     * @return BufferedReader
     */
    public static BufferedReader getBufferedReader(File file) {
        BufferedReader result;
        result = new BufferedReader(
                new InputStreamReader(getFileInputStream(file)));
        return result;
    }

    /**
     * Closes BufferedReader br.
     *
     * @param br BufferedReader
     */
    public static void closeBufferedReader(BufferedReader br) {
        try {
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Closes BufferedReader br and returns a new BufferedReader to read f.
     * 
     * @param br BufferedReader
     * @param f File
     * @return new BufferedReader to read f.
     */
    public static BufferedReader closeAndGetBufferedReader(BufferedReader br, File f) {
        Generic_IO.closeBufferedReader(br);
        br = getBufferedReader(f);
        return br;
    }

    /**
     * @param file The file to write to.
     * @param append If true an existing file will be appended otherwise it will
     * be overwritten.
     * @return PrintWriter
     */
    public static PrintWriter getPrintWriter(File file, boolean append) {
        PrintWriter result = null;
        try {
            result = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(file, append)));
        } catch (FileNotFoundException e) {
            System.err.println("Trying to handle " + e.getLocalizedMessage());
            System.err.println("Wait for 2 seconds then trying again to "
                    + "Generic_StaticIO.getPrintWriter(File, boolean).");
            if (!file.exists()) {
                e.printStackTrace(System.err);
                Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, e);
                // null will be returned...
            } else {
                // This can happen because of too many open files.
                // Try waiting for 2 seconds and then repeating...
                try {
                    synchronized (file) {
                        file.wait(2000L);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, ex);
                }
                return getPrintWriter(file, append);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
            Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    /**
     *
     * @param f File.
     * @return int
     */
    public static int getFileDepth(File f) {
        return getFileDepth(f.getAbsolutePath());
    }

    /**
     *
     * @param absoluteFilePath path
     * @return absoluteFilePath.split("/").length
     */
    public static int getFileDepth(String absoluteFilePath) {
        int result;
        String[] s = absoluteFilePath.split("/");
        result = s.length;
        return result;
    }

    /**
     * <pre>{@code
     * String r = "";
     * for (int i = 0; i < depth; i++) {
     * r += "../";
     * }
     * return r;
     * }</pre>
     *
     * @param depth int
     * @return String
     */
    public static String getRelativeFilePath(int depth) {
        String r = "";
        for (int i = 0; i < depth; i++) {
            r += "../";
        }
        return r;
    }

    /**
     * @param depth int.
     * @param f File.
     * @return f.getPath() appended with depth number of "../"
     */
    public static String getRelativeFilePath(int depth, File f) {
        return getRelativeFilePath(depth, f.getPath());
    }

    /**
     * @param depth int.
     * @param path String.
     * @return path appended with depth number of "../"
     */
    public static String getRelativeFilePath(int depth, String path) {
        String r = path;
        for (int i = 0; i < depth; i++) {
            r += "../";
        }
        return r;
    }

    /**
     * Skips to the next token of StreamTokenizer.TT_EOL type in st.nextToken().
     *
     * @param st StreamTokenizer
     */
    public static void skipline(StreamTokenizer st) {
        int tokenType;
        try {
            tokenType = st.nextToken();
            while (tokenType != StreamTokenizer.TT_EOL) {
                tokenType = st.nextToken();
            }
        } catch (IOException ex) {
            Logger.getLogger(Generic_IO.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the syntax of st as follows:
     * <pre>{@code
     * <ul>
     * <li>st.wordChars( '0', '0' );</li>
     * <li>st.wordChars( '1', '1' );</li>
     * <li>st.wordChars( '2', '2' );</li>
     * <li>st.wordChars( '3', '3' );</li>
     * <li>st.wordChars( '4', '4' );</li>
     * <li>st.wordChars( '5', '5' );</li>
     * <li>st.wordChars( '6', '6' );</li>
     * <li>st.wordChars( '7', '7' );</li>
     * <li>st.wordChars( '8', '8' );</li>
     * <li>st.wordChars( '9', '9' );</li>
     * </ul>
     * }</pre>
     *
     * @param st <code>StreamTokenizer</code> that has syntax set.
     */
    public static void setStreamTokenizerSyntaxNumbersAsWords1(
            StreamTokenizer st) {
        // st.wordChars( '0', '9' );
        st.wordChars('0', '0');
        st.wordChars('1', '1');
        st.wordChars('2', '2');
        st.wordChars('3', '3');
        st.wordChars('4', '4');
        st.wordChars('5', '5');
        st.wordChars('6', '6');
        st.wordChars('7', '7');
        st.wordChars('8', '8');
        st.wordChars('9', '9');
    }

    /**
     * Sets the syntax of st as follows:
     * <pre>{@code
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars( ',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.eolIsSignificant( true );</li>
     * </ul>
     * }</pre>
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set.
     */
    public static void setStreamTokenizerSyntax1(
            StreamTokenizer st) {
        st.resetSyntax();
        // st.parseNumbers();
        st.wordChars(',', ',');
        st.wordChars('"', '"');
        // st.whitespaceChars( '"', '"' );
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('_', '_');
        st.wordChars('+', '+');
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        // char[] tab = new char[1];
        // tab[0] = '\t';
        st.wordChars('\t', '\t');
        st.wordChars(' ', ' ');
        // st.ordinaryChar( ' ' );
        st.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of st as follows:
     * <pre>{@code
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'e', 'e' );</li>
     * <li>st.wordChars( 'E', 'E' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.eolIsSignificant( true );</li>
     * </ul>
     * }</pre>
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax2(
            StreamTokenizer st) {
        st.resetSyntax();
        st.wordChars('"', '"');
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('+', '+');
        st.wordChars('e', 'e');
        st.wordChars('E', 'E');
        st.wordChars('\t', '\t');
        st.wordChars(' ', ' ');
        st.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of st as follows:
     * <pre>{@code
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars( ',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.wordChars( '_', '_' );</li>
     * <li>st.eolIsSignificant( true );</li>
     * </ul>
     * }</pre>
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax3(
            StreamTokenizer st) {
        st.resetSyntax();
        // st.parseNumbers();
        st.wordChars(',', ',');
        st.wordChars('"', '"');
        // st.whitespaceChars( '"', '"' );
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('+', '+');
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        // char[] tab = new char[1];
        // tab[0] = '\t';
        st.wordChars('\t', '\t');
        st.wordChars(' ', ' ');
        st.wordChars('_', '_');
        // st.ordinaryChar( ' ' );
        st.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of st as follows:
     * <pre>{@code
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars( ',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.eolIsSignificant( true );</li> </ul>
     * }</pre>
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax4(
            StreamTokenizer st) {
        st.resetSyntax();
        st.wordChars(',', ',');
        st.wordChars('"', '"');
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('+', '+');
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        st.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of st as follows:
     * <pre>{@code
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars(',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.wordChars( ':', ':' );</li>
     * <li>st.wordChars( '/', '/' );</li>
     * <li>st.wordChars( '_', '_' );</li>
     * </ul>
     * }</pre>
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax5(
            StreamTokenizer st) {
        st.resetSyntax();
        // st.parseNumbers();
        st.wordChars(',', ',');
        //st.ordinaryChar(',');
        st.wordChars('"', '"');
        // st.whitespaceChars( '"', '"' );
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('+', '+');
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        // char[] tab = new char[1];
        // tab[0] = '\t';
        st.wordChars('\t', '\t');
        st.wordChars(' ', ' ');
        st.wordChars(':', ':');
        st.wordChars('/', '/');
        st.wordChars('_', '_');
        // st.ordinaryChar( ' ' );
        st.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of st as follows:
     * <pre>{@code
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars(',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.wordChars( ':', ':' );</li>
     * <li>st.wordChars( '/', '/' );</li>
     * <li>st.wordChars( '_', '_' );</li>
     * </ul>
     * }</pre>
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax6(
            StreamTokenizer st) {
        setStreamTokenizerSyntax5(st);
        st.wordChars('&', '&');
        st.wordChars('(', '(');
        st.wordChars(')', ')');
        st.wordChars('?', '?');
        st.wordChars('\'', '\'');
        st.wordChars('*', '*');
        st.wordChars('\\', '\\');
        st.wordChars('/', '/');
        st.wordChars(';', ';');
        st.wordChars('%', '%');
        st.wordChars('"', '"');
        st.wordChars('£', '£');
        st.wordChars('|', '|');
        st.wordChars('@', '@');
        st.wordChars('=', '=');
        //st.wordChars('', '');
    }

    /**
     * Sets the syntax of st as follows:
     * <pre>{@code
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars(',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.wordChars( ':', ':' );</li>
     * <li>st.wordChars( '/', '/' );</li>
     * <li>st.wordChars( '_', '_' );</li>
     * </ul>
     * }</pre>
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax7(
            StreamTokenizer st) {
        setStreamTokenizerSyntax6(st);
        st.wordChars('<', '<');
        st.wordChars('>', '>');
    }

    /**
     * @return File directory in which object with a_ID is (to be) stored within
     * dir.
     * @param dir File directory.
     * @param aID The ID of the Object to be stored.
     * @param maxID The maximum number of objects to be stored.
     * @param range The number of objects stored per directory.
     */
    public static File getObjectDir(File dir, long aID, long maxID,
            long range) {
        long diff = range;
        while (maxID / (double) diff > 1) {
            //while (max_ID / diff > 1) {
            diff *= range;
        }
        long min = 0;
        Object[] minDir = getObjectDir(aID, min, diff, dir);
        diff /= range;
        while (diff > 1L) {
            minDir = getObjectDir(aID, (Long) minDir[1], diff, (File) minDir[0]);
            diff /= range;
        }
        return (File) minDir[0];
    }

    /**
     * @param aID The ID of the Object to be stored.
     * @param min long
     * @param diff long
     * @param parent Parent File.
     * @return Object[]
     */
    private static Object[] getObjectDir(long aID, long min, long diff,
            File parent) {
        Object[] r = new Object[2];
        long mint = min;
        long maxt = min + diff - 1;
        String dirname;
        File dir = null;
        boolean found = false;
        while (!found) {
            if (aID >= mint && aID <= maxt) {
                found = true;
                dirname = "" + mint + "_" + maxt;
                dir = new File(parent, dirname);
            } else {
                mint += diff;
                maxt += diff;
            }
        }
        r[0] = dir;
        r[1] = mint;
        return r;
    }

    /**
     * Initialises a directory in directory and returns result.
     *
     * @param dir File directory.
     * @param range long
     * @return File
     */
    public static File initialiseArchive(File dir, long range) {
        File result;
        long start = 0;
        long end = range - 1;
        result = new File(dir, "" + start + "_" + end + "/" + start);
        result.mkdirs();
        return result;
    }

    /**
     * Initialises an Archive directory structure. The structure of the archive
     * is given by the range.
     *
     * @param dir File directory.
     * @param range long
     * @param maxID long
     * @throws IOException If attempting to initialise the archive in a
     * non-empty directory.
     */
    public static void initialiseArchive(File dir, long range, long maxID)
            throws IOException {
        if (dir.exists()) {
            String[] list = dir.list();
            if (list != null) {
                if (list.length > 0) {
                    throw new IOException("Attempting to initialise Archive in "
                            + "non-empty directory");
                }
            }
        }
        initialiseArchive(dir, range);
        for (long l = 0L; l < maxID; l++) {
            addToArchive(dir, range);
        }
    }

    /**
     * Initialises an Archive directory structure and returns a TreeMap of the
     * Files in the Archive with numerical keys from 0 to maxID. The structure
     * of the archive is given by the range.
     *
     * @param dir File directory.
     * @param range long
     * @param maxID long
     * @return TreeMap
     * @throws IOException If attempting to initialise the archive in a
     * non-empty directory.
     */
    public static TreeMap<Long, File> initialiseArchiveAndReturnFileMap(
            File dir, long range, long maxID) throws IOException {
        initialiseArchive(dir, range, maxID);
        return getArchiveLeafFilesMap(dir, "_", 0L, maxID);
    }

    /**
     *
     * @param dir File directory.
     * @param underscore Expects "_".
     * @return long
     */
    public static long getArchiveHighestLeaf(File dir, String underscore) {
        long r;
        File[] archiveFiles = dir.listFiles();
        if (archiveFiles == null) {
            return -1L;
        }
        if (archiveFiles.length == 0) {
            return -1L;
        } else {
            TreeMap<Long, File> files;
            files = Generic_IO.getNumericallyOrderedFiles(archiveFiles, underscore);
            File last_File = files.lastEntry().getValue();
            if (last_File.getName().contains(underscore)) {
                return getArchiveHighestLeaf(last_File, underscore);
            } else {
                r = Long.valueOf(last_File.getName());
            }
        }
        return r;
    }

    /**
     *
     * @param dir File
     * @param underscore Expects "_".
     * @return long
     */
    public static long getArchiveRange(File dir, String underscore) {
        File highestLeaf_File = getArchiveHighestLeafFile(dir, underscore);
        String[] split = highestLeaf_File.getParentFile().getName().split(underscore);
        long min = Long.valueOf(split[0]);
        long max = Long.valueOf(split[1]);
        return max - min + 1;
    }

    /**
     * For returning all the leaf file elements in a branch of an archive as a
     * HashSet&lt;File&gt;. An archive has directories such as ./0_99 which
     * store leaves such as ./0_99/0 and ./0_99/10. The archive may have
     * considerable depth such that an archive leaf is stored for example in
     * ./0_999999/0_9999/0_99/0. The leaves are the directories at the end of
     * the tree branches that contain directories that do not have an underscore
     * in the filename
     *
     * @param dir File directory.
     * @param underscore Expects "_".
     * @return a HashSet&lt;File&gt; containing all files in the directory. The
     * directory is regarded as a directory that possibly contains other
     * directories in a branching tree with paths that are expected to end
     * eventually in one or more files. All such files are returned in the
     * result. The result is null if the directory is empty.
     */
    public static HashSet<File> getArchiveLeafFilesSet(File dir,
            String underscore) {
        HashSet<File> r = new HashSet<>();
        File[] topLevelArchiveFiles = dir.listFiles();
        for (File topLevelArchiveFile : topLevelArchiveFiles) {
            HashSet<File> s;
            s = getArchiveLeafFilesSet0(topLevelArchiveFile, underscore);
            r.addAll(s);
        }
        return r;
    }

    /**
     *
     * @param f File.
     * @param underscore Expects "_".
     * @return HashSet
     */
    private static HashSet<File> getArchiveLeafFilesSet0(File f,
            String underscore) {
        HashSet<File> r = new HashSet<>();
        if (f.getName().contains(underscore)) {
            File[] files = f.listFiles();
            for (File file : files) {
                HashSet<File> s;
                s = getArchiveLeafFilesSet0(file, underscore);
                r.addAll(s);
            }
        } else {
            r.add(f);
        }
        return r;
    }

    /**
     * Potentially slow and could be speeded up by going through the file tree
     * branch by branch.
     *
     * @param dir File.
     * @param underscore Expects "_".
     * @return TreeMap
     */
    public static TreeMap<Long, File> getArchiveLeafFilesMap(File dir,
            String underscore) {
        TreeMap<Long, File> r = new TreeMap<>();
        File[] topLevelArchiveFiles = dir.listFiles();
        for (File f : topLevelArchiveFiles) {
            TreeMap<Long, File> m = getArchiveLeafFilesMap0(f, underscore);
            r.putAll(m);
        }
        return r;
    }

    /**
     *
     * @param file File
     * @param underscore Expects "_".
     * @return TreeMap
     */
    private static TreeMap<Long, File> getArchiveLeafFilesMap0(File file,
            String underscore) {
        TreeMap<Long, File> result = new TreeMap<>();
        if (file.getName().contains(underscore)) {
            File[] files = file.listFiles();
            for (File f : files) {
                TreeMap<Long, File> subresult = getArchiveLeafFilesMap0(
                        f, underscore);
                result.putAll(subresult);
            }
        } else {
            result.put(Long.valueOf(file.getName()), file);
        }
        return result;
    }

    /**
     *
     * @param directory File.
     * @param underscore Expects "_".
     * @param minID long
     * @param maxID long
     * @return TreeMap
     */
    public static TreeMap<Long, File> getArchiveLeafFilesMap(
            File directory,
            String underscore,
            long minID,
            long maxID) {
        TreeMap<Long, File> result = new TreeMap<>();
        File[] topLevelArchiveFiles = directory.listFiles();
        for (File f : topLevelArchiveFiles) {
            TreeMap<Long, File> subresult = getArchiveLeafFilesMap0(
                    f, underscore, minID, maxID);
            result.putAll(subresult);
        }
        return result;
    }

    /**
     *
     * @param file
     * @param underscore Expects "_".
     * @param minID
     * @param maxID
     * @return TreeMap
     */
    private static TreeMap<Long, File> getArchiveLeafFilesMap0(
            File file, String underscore, long minID, long maxID) {
        TreeMap<Long, File> result = new TreeMap<>();
        if (file.getName().contains(underscore)) {
            File[] files = file.listFiles();
            for (File f : files) {
                TreeMap<Long, File> subresult = getArchiveLeafFilesMap0(
                        f, underscore, minID, maxID);
                result.putAll(subresult);
            }
        } else {
            long id = Long.valueOf(file.getName());
            if (id >= minID && id <= maxID) {
                result.put(Long.valueOf(file.getName()), file);
            }
        }
        return result;
    }

    /**
     *
     * @param dir File.
     * @param underscore Expects "_".
     * @return The highest numbered File
     */
    public static File getArchiveHighestLeafFile(File dir, String underscore) {
        File result;
        File[] archiveFiles = dir.listFiles();
        if (archiveFiles == null) {
            return null;
        }
        if (archiveFiles.length == 0) {
            return null;
        } else {
            TreeMap<Long, File> files;
            files = Generic_IO.getNumericallyOrderedFiles(archiveFiles, underscore);
            File last_File = files.lastEntry().getValue();
            if (last_File.getName().contains(underscore)) {
                return getArchiveHighestLeafFile(last_File, underscore);
            } else {
                result = last_File;
            }
        }
        return result;
    }

    /**
     *
     * @param dir File
     * @param range long
     * @return File
     */
    public static File growArchiveBase(File dir, long range) {
        String underscore = "_";
        File[] archiveFiles = dir.listFiles();
        TreeMap<Long, File> files;
        files = Generic_IO.getNumericallyOrderedFiles(archiveFiles, underscore);
        File first_File = files.firstEntry().getValue();
        String directoryName0 = getFilename(dir, first_File);
        String[] split0 = directoryName0.split(underscore);
        //long start0 = new Long(split0[0]).longValue();
        long end0 = Long.valueOf(split0[1]);
        long newRange = range * (end0 + 1L);
        // Create new top directory and move in existing files
        File newTop0 = new File(dir, "" + 0 + "_" + (newRange - 1L));
        File newTop = new File(newTop0.getPath());
        newTop0.mkdir();
        for (File archiveFile : archiveFiles) {
            File newPath = new File(newTop, archiveFile.getName());
            //archiveFile.renameTo(newPath);
            move(archiveFile, newPath);
        }
        // Create new lower directories for next ID;
        Long next_ID = getArchiveHighestLeaf(dir, underscore);
        next_ID++;
        File newHighestLeafDir = new File(
                getObjectDir(newTop, next_ID, next_ID, range), "" + next_ID);
        newHighestLeafDir.mkdirs();
        return newTop0;
    }

    public static File growArchiveBase(File dir, long range, long next_ID) {
        String underscore = "_";
        File[] archiveFiles = dir.listFiles();
        TreeMap<Long, File> files;
        files = Generic_IO.getNumericallyOrderedFiles(archiveFiles, underscore);
        File file0 = files.firstEntry().getValue();
        String dirName0 = getFilename(dir, file0);
        String[] split0 = dirName0.split(underscore);
        //long start0 = new Long(split0[0]).longValue();
        long end0 = Long.valueOf(split0[1]);
        long newRange = range * (end0 + 1L);
        // Create new top directory and move in existing files
        File newTop0 = new File(dir, "" + 0 + "_" + (newRange - 1L));
        File newTop = new File(newTop0.getPath());
        newTop0.mkdir();
        for (File archiveFile : archiveFiles) {
            File newPath = new File(newTop, archiveFile.getName());
            //archiveFile.renameTo(newPath);
            move(archiveFile, newPath);
        }
        File newHighestLeaf_Directory = new File(
                getObjectDir(newTop, next_ID, next_ID, range),
                "" + next_ID);
        newHighestLeaf_Directory.mkdirs();
        return newTop0;
    }

    /**
     *
     * @param origin File
     * @param destination File
     */
    public static void move(File origin, File destination) {
        if (origin.isDirectory()) {
            destination.mkdir();
            for (File file : origin.listFiles()) {
                move(file, new File(destination, file.getName()));
            }
            try {
                Files.delete(origin.toPath());
            } catch (IOException ex) {
                Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Files.move(
                        Paths.get(origin.getPath()),
                        Paths.get(destination.getPath()),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param dir File
     * @param range long
     * @return File
     */
    public static File addToArchive(File dir, long range) {
        String underscore = "_";
        Long next_ID = getArchiveHighestLeaf(dir, underscore);
        next_ID++;
        File newHighestLeaf_Directory = new File(
                getObjectDir(dir, next_ID, next_ID + 1, range),
                "" + next_ID);
        String filename = getFilename(dir, newHighestLeaf_Directory);
        // Test range
        String[] splitnew = filename.split(underscore);
        long startnew = Long.valueOf(splitnew[0]);
        long endnew = Long.valueOf(splitnew[1]);
        long rangenew = endnew - startnew;
        HashMap<Integer, String> filenames;
        filenames = getNumericallyOrderedFiles(dir, underscore);
        Iterator<Integer> ite = filenames.keySet().iterator();
        int index;
        String filename0 = "";
        while (ite.hasNext()) {
            index = ite.next();
            filename0 = filenames.get(index);
            if (filename0.contains(underscore)) {
                break;
            }
        }
        String[] splitold = filename0.split(underscore);
        long startold = Long.parseLong(splitold[0]);
        long endold = Long.parseLong(splitold[1]);
        long rangeold = endold - startold;
        if (rangenew > rangeold) {
            growArchiveBase(dir, range);
        }
        newHighestLeaf_Directory.mkdirs();
        return newHighestLeaf_Directory;
    }

    /**
     *
     * @param dir File.
     * @param range long
     * @param next_ID long
     * @return File
     */
    public static File addToArchive(File dir, long range, long next_ID) {
        String underscore = "_";
        File newHighestLeaf_Directory = new File(
                getObjectDir(dir, next_ID, next_ID + 1, range),
                "" + next_ID);
        String filename = getFilename(dir, newHighestLeaf_Directory);
        // Test range
        String[] splitnew = filename.split(underscore);
        long startnew = Long.valueOf(splitnew[0]);
        long endnew = Long.valueOf(splitnew[1]);
        long rangenew = endnew - startnew;
        String[] archiveFiles = dir.list();
        String[] splitold = archiveFiles[0].split(underscore);
        long startold = Long.valueOf(splitold[0]);
        long endold = Long.valueOf(splitold[1]);
        long rangeold = endold - startold;
        if (rangenew > rangeold) {
            growArchiveBase(dir, range, next_ID);
        }
        newHighestLeaf_Directory.mkdirs();
        return newHighestLeaf_Directory;
    }

    /**
     * @param dir File.
     * @param filename String.
     * @return The File with filename in directory or throws
     * FileNotFoundException if directory or the result does not exist.
     * @throws java.io.FileNotFoundException If dir does not exist or file with
     * filename in dir does not exist.
     */
    public static File getFileThatExists(File dir, String filename)
            throws FileNotFoundException {
        String method = "getFile(File,String)";
        if (!dir.exists()) {
            throw new FileNotFoundException(
                    "File " + dir + " does not exist in "
                    + Generic_IO.class.getName() + "." + method + ".");
        }
        File r = null;
        if (filename != null) {
            r = new File(dir, filename);
            if (!r.exists()) {
                throw new FileNotFoundException(
                        r + " does not exist in "
                        + Generic_IO.class.getName() + "." + method + ".");
            }
        }
        return r;
    }

    /**
     * @param dir File.
     * @param f File.
     * @return The name of the file or directory in dir in the path of f.
     */
    public static String getFilename(File dir, File f) {
        String result;
        int beginIndex = dir.getAbsolutePath().length() + 1;
        String fileSeparator = System.getProperty("file.separator");
        /*
         * A feature in Java means splitting strings with "\" does not work as
         * might be expected and the regexp needs changing to "\\\\"
         */
        String regexp = "\\";
        //System.out.println("regexp " + regexp);
        if (fileSeparator.equals(regexp)) {
            fileSeparator = "\\\\";
        }
        //System.out.println("fileSeparator " + fileSeparator);
        //String newTopOfArchiveDirectoryName = (objectDirectoryFile.getAbsolutePath().substring(beginIndex)).split(System.getProperty("file.separator"))[0];
        //String newTopOfArchiveDirectoryName = (objectDirectoryFile.getPath().substring(beginIndex)).split("\\")[0];
        //String newTopOfArchiveDirectoryName = (objectDirectoryFile.getPath().substring(beginIndex)).split("/")[0];
        result = (f.getPath().substring(beginIndex)).split(fileSeparator)[0];
        //System.out.println("result " + result);
        return result;
    }

    /**
     * @param files File[]
     * @return TreeMap
     */
    public static TreeMap<Long, File> getNumericallyOrderedFiles(
            File[] files) {
        TreeMap<Long, File> r = new TreeMap<>();
        for (File file : files) {
            r.put(Long.valueOf(file.getName()), file);
        }
        return r;
    }

    /**
     * If needed to order also by a number after the underscore then a new
     * method is needed.
     *
     * @param files File[]
     * @param underscore Expects "_".
     * @return TreeMap
     */
    public static TreeMap<Long, File> getNumericallyOrderedFiles(
            File[] files, String underscore) {
        TreeMap<Long, File> r = new TreeMap<>();
        String filename;
        String[] split;
        for (File file : files) {
            filename = file.getName();
            if (filename.contains(underscore)) {
                split = filename.split("_");
                if (split.length <= 2) {
                    long start;
                    try {
                        start = Long.parseLong(split[0]);
                        if (split.length == 2) {
                            Long.parseLong(split[1]);
                        }
                        r.put(start, file);
                    } catch (NumberFormatException e) {
                        // Ignore
                    }
                }
            } else {
                try {
                    long name = Long.valueOf(filename);
                    r.put(name, file);
                } catch (NumberFormatException e) {
                    // Ignore
                }
            }
        }
        return r;
    }

    /**
     * @param dir File.
     * @param underscore "_"
     * @return HashMap
     */
    public static HashMap<Integer, String> getNumericallyOrderedFiles(
            File dir, String underscore) {
        HashMap<Integer, String> r = new HashMap<>();
        int index = 0;
        String[] list = dir.list();
        String filename;
        String[] split;
        for (String list1 : list) {
            filename = list1;
            if (filename.contains(underscore)) {
                split = filename.split("_");
                if (split.length <= 2) {
                    try {
                        if (split.length == 2) {
                            Long.parseLong(split[1]);
                        }
                        r.put(index, filename);
                        index++;
                    } catch (NumberFormatException e) {
                        // Ignore
                    }
                }
            } else {
                try {
                    long name = Long.valueOf(filename);
                    r.put(index, filename);
                    index++;
                } catch (NumberFormatException e) {
                    // Ignore
                }
            }
        }
        return r;
    }

    /**
     * Method to calculate the length of the file path. The Windows 7 operating
     * systems has a technical restriction of 260 characters or less for file
     * paths. So a file path that is greater than 250 characters is a worry
     * especially if results are going to be zipped up and transferred to a
     * Windows 7 machine.
     *
     * @param f File.
     * @return int
     */
    public static int getFilePathLength(File f) {
        int result;
        String s;
        try {
            s = f.getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Returning absolute path as getCanonicalPath() resulted in IOException.");
            s = f.getAbsolutePath();
        }
        result = s.length();
        return result;
    }

    /**
     * Method to calculate the length of the file path. The Windows 7 operating
     * systems has a technical restriction of 260 characters or less for file
     * paths. So a file path that is greater than 250 characters is a worry
     * especially if results are going to be zipped up and transferred to a
     * Windows 7 machine.
     *
     * @param f File.
     * @param dir File.
     * @return int
     */
    public static int getFilePathLength(File f, File dir) {
        int fileFilePathLength;
        fileFilePathLength = getFilePathLength(f);
        int dirFilePathLength;
        dirFilePathLength = getFilePathLength(dir);
        return fileFilePathLength - dirFilePathLength;
    }

//    public static boolean isStandardFileName(File f){
//        return isStandardFileName(f.toString());
//    } 
}
