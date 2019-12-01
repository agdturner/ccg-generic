/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.agdt.generic.io;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
//import static java.nio.file.StandardOpenOption.CREATE_NEW;
//import static java.nio.file.StandardOpenOption.DELETE_ON_CLOSE;
//import static java.nio.file.StandardOpenOption.DSYNC;
import static java.nio.file.StandardOpenOption.READ;
//import static java.nio.file.StandardOpenOption.SPARSE;
//import static java.nio.file.StandardOpenOption.SYNC;
//import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.io.UnsupportedEncodingException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Object;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Strings;
import uk.ac.leeds.ccg.agdt.generic.execution.Generic_Execution;

//import java.nio.file.S
//TODO http://java.sun.com/docs/books/tutorial/essential/io/legacy.html#mapping
// http://java.sun.com/docs/books/tutorial/essential/io/fileio.html
/**
 * Class of methods for helping with reading and writing (primarily) to/from
 * file.
 */
public class Generic_IO extends Generic_Object {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance.
     *
     * @param e Generic_Environment
     */
    public Generic_IO(Generic_Environment e) {
        super(e);
        archiveSeparator = Generic_Strings.symbol_underscore;
    }

    public String archiveSeparator;

    public String getArchiveSeparator() {
        return archiveSeparator;
    }

    /**
     * Recursively traverses a directory creating a set of File paths of files
     * (i.e.not directories).
     *
     * @param dir Path.
     * @return TreeSet.
     * @throws java.io.IOException If encountered.
     */
    public TreeSet<String> recursiveFileList(Path dir) throws IOException {
        TreeSet<String> r = new TreeSet<>();
        if (Files.isDirectory(dir)) {
            List<Path> l = getList(dir);
            for (int i = 0; i < l.size(); i++) {
                r.addAll(recursiveFileList(l.get(i)));
            }
            return r;
        } else {
            r.add(dir.toString());
            return r;
        }
    }

    /**
     * Recursively traverses a path creating a set of paths of files
     * (i.e.not directories) up to the specified depth.
     *
     * @param p Path
     * @param depth The depth beyond which directories are not traversed.
     * @return TreeSet
     * @throws java.io.IOException If encountered.
     */
    public TreeSet<String> recursiveFileList(Path p, int depth) 
            throws IOException {
        TreeSet<String> r = new TreeSet<>();
        if (depth != 0) {
            if (Files.isDirectory(p)) {
                List<Path> l = getList(p);
                for (int i = 0; i < l.size(); i++) {
                    r.addAll(recursiveFileList(l.get(i), depth - 1));
                }
                return r;
            } else {
                r.add(p.toString());
                return r;
            }
        }
        r.add(p.toString());
        return r;
    }

    /**
     * Writes Object o to File f.
     *
     * @param o Object to be written.
     * @param f File to write to.
     * @throws IOException If encountered.
     */
    public void writeObject(Object o, Path f) throws IOException {
        Files.createDirectories(f.getParent());
        try (ObjectOutputStream oos = new ObjectOutputStream(
                Files.newOutputStream(f, WRITE))) {
            oos.writeUnshared(o);
            oos.flush();
            oos.reset();
        }
    }

    /**
     * Read Object from a file at p.
     *
     * @param p Path to a file be read from.
     * @return Object read from the file at p.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException
     */
    public Object readObject(Path p) throws IOException,
            ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                Files.newInputStream(p, READ))) {
            return ois.readUnshared();
        }
    }

    /**
     * Writes Object o to a file at Path p and logs the name of the Object written and
     * the path to stdout.
     *
     * @param o Object to be written.
     * @param p The Path of the file to write to.
     * @param name String for reporting.
     * @throws java.io.IOException If encountered.
     */
    public void writeObject(Object o, Path p, String name) throws IOException {
        writeObject(o, p);
        env.log("Written out " + name + " to " + p.toString());
    }

    /**
     * @param f A Path of a file to be copied.
     * @param d The Path of a directory to copy to.
     */
    private void copyFile(Path f, Path d) throws IOException {
        copyFile(f, d, f.getFileName().toString());
    }

    /**
     * @param f A Path of a file to be copied.
     * @param d The Path of a directory to copy to.
     * @param fn The name for the file that will be created in d.
     * @throws java.io.IOException IF IOException is encountered.
     */
    public void copyFile(Path f, Path d, String fn)
            throws IOException {
        if (!Files.exists(f)) {
            throw new IOException("File " + f + " does not exist in "
                    + this.getClass().getName() + ".copyFile(File,File,String)");
        }
        if (!Files.exists(d)) {
            Files.createDirectories(d);
        }
        Path p = Paths.get(d.toString(), fn);
        if (Files.exists(p)) {
            env.log("Overwriting File " + p + " in "
                    + this.getClass().getName() + ".copyFile(File,File,String)");
        } else {
            Files.createFile(p);
        }
        try (BufferedInputStream bis = getBufferedInputStream(f);
                BufferedOutputStream bos = getBufferedOutputStream(p)) {
            /**
             * bufferSize should be power of 2 (e.g. Math.pow(2, 12)), but
             * nothing too big.
             */
            int bufferSize = 2048;
            long length = Files.size(f);
            long nArrayReads =  length / bufferSize;
            long nSingleReads = length - (nArrayReads * bufferSize);
            byte[] b = new byte[bufferSize];
            for (int i = 0; i < nArrayReads; i++) {
                bis.read(b);
                bos.write(b);
            }
            for (int i = 0; i < nSingleReads; i++) {
                bos.write(bis.read());
            }
            bos.flush();
        }
    }
    
    /**
     * @param f File.
     * @return BufferedInputStream
     * @throws java.io.FileNotFoundException If the file exists but is a
     * directory rather than a regular file, does not exist but cannot be
     * created, or cannot be opened for any other reason.
     */
    public BufferedInputStream getBufferedInputStream(Path f)
            throws FileNotFoundException {
        return new BufferedInputStream(getFileInputStream(f));
    }

    /**
     * @param f File.
     * @return FileInputStream
     * @throws java.io.FileNotFoundException If the file exists but is a
     * directory rather than a regular file, does not exist but cannot be
     * created, or cannot be opened for any other reason.
     */
    public FileInputStream getFileInputStream(Path f) throws FileNotFoundException {
        FileInputStream r = null;
        try {
            r = new FileInputStream(f.toString());
        } catch (FileNotFoundException ex) {
            if (Files.exists(f)) {
                long wait = 2000L;
                fileWait(wait, f);
                return getFileInputStream(f, wait);
            } else {
                throw ex;
            }
        }
        return r;
    }

    protected void fileWait(long wait, Object o) {
        env.log("Maybe there are too many open files... "
                + "waiting for " + wait + " milliseconds...");
        Generic_Execution.waitSychronized(env, o, wait);
    }

    /**
     * @param f File.
     * @param wait Time in milliseconds to wait before trying to open the
     * FileInputStream again if it failed the first time (this may happen if
     * waiting for a file to be written).
     * @return FileInputStream
     * @throws java.io.FileNotFoundException If the file exists but is a
     * directory rather than a regular file, does not exist but cannot be
     * created, or cannot be opened for any other reason.
     */
    public FileInputStream getFileInputStream(Path f, long wait)
            throws FileNotFoundException {
        try {
            return new FileInputStream(f.toString());
        } catch (FileNotFoundException ex) {
            if (Files.exists(f)) {
                fileWait(wait, f);
                return getFileInputStream(f, wait * 2L);
            } else {
                throw ex;
            }
        }
    }

    /**
     * For getting a {@link BufferedOutputStream} to write to a file at {@code f}.
     *
     * @param f The {@link Path} of the file to be written.
     * @return A {@link BufferedOutputStream} for writing to {@code f}.
     * @throws java.io.FileNotFoundException If the file exists but is a
     * directory rather than a regular file, does not exist but cannot be
     * created, or cannot be opened for any other reason.
     */
    public BufferedOutputStream getBufferedOutputStream(Path f)
            throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(f.toString()));
    }

    /**
     * For getting a {@link BufferedWriter}.
     *
     * @param f The {@link Path} for a file to be written to.
     * @param append if true then file is appended to otherwise file is
     * overwritten.
     * @return A {@link BufferedWriter} for writing to {@code f}.
     * @throws java.io.IOException If one is encountered and not otherwise
     * handled.
     */
    public BufferedWriter getBufferedWriter(Path f, boolean append) 
            throws IOException {
        return new BufferedWriter(getPrintWriter(f, append));
    }

    /**
     * @param f The {@link Path} for a file to be written.
     * @return An {@link ObjectInputStream} for reading from a file at {@code f}
     * @throws java.io.IOException If encountered and not otherwise
     * handled.
     */
    public ObjectInputStream getObjectInputStream(Path f) throws IOException {
        return new ObjectInputStream(getBufferedInputStream(f));
    }

    /**
     * @param f The {@link Path} of the file to write.
     * @return An {@link ObjectOutputStream} for writing to a file at {@code f}.
     * @throws java.io.IOException If encountered and not handled.
     */
    public ObjectOutputStream getObjectOutputStream(Path f) throws IOException {
        return new ObjectOutputStream(getBufferedOutputStream(f));
    }
    
    public class CopyDir extends SimpleFileVisitor<Path> {
        private final Generic_IO io;
    private final Path sourceDir;
    private final Path targetDir;
 
    public CopyDir(Generic_IO io, Path sourceDir, Path targetDir) {
        this.io = io;
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
    }
 
    @Override
    public FileVisitResult visitFile(Path file,            BasicFileAttributes attributes) { 
        try {
            Path targetFile = targetDir.resolve(sourceDir.relativize(file));
            Files.copy(file, targetFile);
        } catch (IOException ex) {
            System.err.println(ex);
        } 
        return FileVisitResult.CONTINUE;
    }
 
    @Override
    public FileVisitResult preVisitDirectory(Path dir,
            BasicFileAttributes attributes) {
        try {
            Path newDir = targetDir.resolve(sourceDir.relativize(dir));
            Files.createDirectory(newDir);
        } catch (IOException ex) {
            System.err.println(ex);
        }
 
        return FileVisitResult.CONTINUE;
    }
 
}
    
    private void copyDirectory(Path dirToCopy, Path dirToCopyTo) throws IOException {
                Files.walkFileTree(dirToCopy, new CopyDir(this, dirToCopy, dirToCopyTo));
    }

    /**
     * @param fileOrDirToCopy File.
     * @param dirToCopyTo Directory.
     * @throws java.io.IOException If IOException encountered.
     */
    public void copy(Path fileOrDirToCopy, Path dirToCopyTo) throws IOException {
        Files.createDirectories(dirToCopyTo);
        if (!Files.isDirectory(dirToCopyTo)) {
            throw new IOException("Expecting File " + dirToCopyTo
                    + "To be a directory in "
                    + this.getClass().getName() + ".copy(File,File)");
        }
        if (Files.isRegularFile(fileOrDirToCopy)) {
            copyFile(fileOrDirToCopy, dirToCopyTo);
        } else {
            copyDirectory(fileOrDirToCopy, dirToCopyTo);
        }
    }

    /**
     * Attempts to delete file from the file system.This does not block other
 reads or writes to the files system while it is executing and it will
 only succeed in deleting empty directories.
     *
     * @param p The {@link Path} to a file or directory to be deleted.
     * @return long[] r where: r[0] is the number of directories
     * deleted; r[1] is the number of files deleted.
     * @throws java.io.IOException If encountered.
     */
    public long[] delete(Path p) throws IOException {
        long[] r = new long[2];
        r[0] = 0L;
        r[1] = 0L;
        if (Files.isDirectory(p)) {
            // Delete all the files it contains
            List<Path> l = getList(p);
            for (Path p2 : l) {
                long[] deleted = delete(p2);
                r[0] += deleted[0];
                r[1] += deleted[1];
            }
            // Delete the directory itself
            if (Files.deleteIfExists(p)) {
                r[0]++;
            } else {
                System.out.println("Not deleted " + p + " in "
                        + "Generic_IO.delete(Path)!");
            }
        } else {
            Files.deleteIfExists(p);
            r[1]++;
        }
        return r;
    }

    /**
     * @param f The Path of a file.
     * @return BufferedReader
     * @throws java.io.FileNotFoundException If the file exists but is a
     * directory rather than a regular file, does not exist but cannot be
     * created, or cannot be opened for any other reason.
     */
    public BufferedReader getBufferedReader(Path f)
            throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(getFileInputStream(f)));
    }

    /**
     * @param f File.
     * @param charsetName The name of a supported
     * {@link java.nio.charset.Charset charset} e.g. "UTF-8"
     * @return BufferedReader
     * @throws java.io.UnsupportedEncodingException If InputStreamReader cannot
     * be constructed from charsetName.
     * @throws java.io.FileNotFoundException If the file exists but is a
     * directory rather than a regular file, does not exist but cannot be
     * created, or cannot be opened for any other reason.
     */
    public BufferedReader getBufferedReader(Path f, String charsetName)
            throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(getFileInputStream(f),
                charsetName));
    }

    /**
     * Closes BufferedReader br.
     *
     * @param br BufferedReader
     */
    public void closeBufferedReader(BufferedReader br) {
        try {
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            env.log(ex.getMessage());
        }
    }

    /**
     * Closes BufferedReader br and returns a new BufferedReader to read f.
     *
     * @param br BufferedReader
     * @param f File
     * @return new BufferedReader to read f.
     * @throws FileNotFoundException If the file exists but is a directory
     * rather than a regular file, does not exist but cannot be created, or
     * cannot be opened for any other reason.
     */
    public BufferedReader closeAndGetBufferedReader(BufferedReader br, Path f)
            throws FileNotFoundException {
        closeBufferedReader(br);
        br = getBufferedReader(f);
        return br;
    }

    /**
     * Write s to a file at Path p using the default charset UTF-8. 
     * @param p
     * @param s
     * @throws IOException 
     */
    public void write(Path p, String s) throws IOException {
        // Convert the string to a  byte array.
        byte data[] = s.getBytes();
        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(p, CREATE, APPEND))) {
            out.write(data, 0, data.length);
        }
    }

    /**
     * @param f The File to write to.
     * @param append If true an existing file will be appended otherwise it will
     * be overwritten.
     * @return PrintWriter
     * @throws IOException If the file exists but is a directory rather than a
     * regular file, does not exist but cannot be created, or cannot be opened
     * for any other reason.
     */
    public PrintWriter getPrintWriter(Path f, boolean append)
            throws IOException {
        try {
            return new PrintWriter(new BufferedWriter(
                    new FileWriter(f.toString(), append)));
        } catch (FileNotFoundException ex) {
            if (Files.exists(f)) {
                long wait = 2000L;
                fileWait(wait, f);
                return getPrintWriter(f, append, wait);
            } else {
                throw ex;
            }
        }
    }

    /**
     * @param f The File to write to.
     * @param append If true an existing file will be appended otherwise it will
     * be overwritten.
     * @param wait Time in milliseconds to wait before trying to open the
     * FileInputStream again if it failed the first time (this may happen if
     * waiting for a file to be written).
     * @return PrintWriter
     * @throws IOException If the file exists but is a directory rather than a
     * regular file, does not exist but cannot be created, or cannot be opened
     * for any other reason.
     */
    public PrintWriter getPrintWriter(Path f, boolean append, long wait)
            throws IOException {
        try {
            return new PrintWriter(new BufferedWriter(new FileWriter(
                    f.toString(), append)));
        } catch (FileNotFoundException ex) {
            if (Files.exists(f)) {
                fileWait(wait, f);
                return getPrintWriter(f, append, wait * 2L);
            } else {
                throw ex;
            }
        }
    }

    /**
     *
     * @param depth int
     * @return String
     */
    public String getRelativeFilePath(int depth) {
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
    public String getRelativeFilePath(int depth, Path f) {
        return getRelativeFilePath(depth, f.toString());
    }

    /**
     * @param depth int.
     * @param path String.
     * @return path appended with depth number of "../"
     */
    public String getRelativeFilePath(int depth, String path) {
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
     * @throws java.io.IOException If IOException encountered.
     */
    public void skipline(StreamTokenizer st) throws IOException {
        int token = st.nextToken();
        while (token != StreamTokenizer.TT_EOL) {
            token = st.nextToken();
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
    public void setStreamTokenizerSyntaxNumbersAsWords1(
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
    public void setStreamTokenizerSyntax1(            StreamTokenizer st) {
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
        setWhitespaceAsWords(st);
        // st.ordinaryChar( ' ' );
        st.eolIsSignificant(true);
    }

    /**
     * @param st The StreamTokenizer to modify.
     */
    private void setWhitespaceAsWords(StreamTokenizer st) {
        st.wordChars('\t', '\t');
        st.wordChars(' ', ' ');
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
    public void setStreamTokenizerSyntax2(            StreamTokenizer st) {
        st.resetSyntax();
        st.wordChars('"', '"');
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('+', '+');
        st.wordChars('e', 'e');
        st.wordChars('E', 'E');
        setWhitespaceAsWords(st);
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
    public void setStreamTokenizerSyntax3(            StreamTokenizer st) {
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
        setWhitespaceAsWords(st);
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
    public void setStreamTokenizerSyntax4(            StreamTokenizer st) {
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
    public void setStreamTokenizerSyntax5(            StreamTokenizer st) {
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
        setWhitespaceAsWords(st);
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
    public void setStreamTokenizerSyntax6(            StreamTokenizer st) {
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
    public void setStreamTokenizerSyntax7(            StreamTokenizer st) {
        setStreamTokenizerSyntax6(st);
        st.wordChars('<', '<');
        st.wordChars('>', '>');
    }

    /**
     * @return File directory in which object with a_ID is (to be) stored within
     * dir.
     * @param dir File directory.
     * @param id The ID of the Object to be stored.
     * @param n The maximum number of objects to be stored.
     * @param range The number of objects stored per directory.
     */
    public Path getObjectDir(Path dir, long id, long n, long range) {
        long diff = range;
        while (n / (double) diff > 1) {
            //while (max_ID / diff > 1) {
            diff *= range;
        }
        long min = 0;
        PathLong minDir = getObjectDir(id, min, diff, dir);
        diff /= range;
        while (diff > 1L) {
            minDir = getObjectDir(id, minDir.l, diff, minDir.p);
            diff /= range;
        }
        return minDir.p;
    }

    /**
     * @param id The ID of the Object to be stored.
     * @param min long
     * @param diff long
     * @param p Parent directory.
     * @return Object[]
     */
    private PathLong getObjectDir(long id, long min, long diff, Path p) {
        PathLong r;
        long mint = min;
        long maxt = min + diff - 1;
        Path dir = null;
        boolean found = false;
        while (!found) {
            if (id >= mint && id <= maxt) {
                found = true;
                String dirname = "" + mint + archiveSeparator + maxt;
                dir = Paths.get(p.toString(), dirname);
            } else {
                mint += diff;
                maxt += diff;
            }
        }
        r = new PathLong(dir, mint);
        return r;

    }

    private class PathLong {

        Path p;
        long l;

        PathLong(Path p, long l) {
            this.p = p;
            this.l = l;
        }
    }

    /**
     * Initialises a directory in directory and returns result.
     *
     * @param dir File directory which is the base of this archive.
     * @param range For specifying the number of leafs in a directory and the
     * number of directories in any directory.
     * @param exists This is a declaration that the user knows whether or not
     * the archive already exists. If exist == true, then the archive is
     * expected to exist, if it does not, then an {@link IOException} is thrown.
     * However if it does already exist, it's form is tested using range. If the
     * form is fine, then the highest archive leaf is returned otherwise an
     * {@link IOException} is thrown.
     * @return File that is the highest archive leaf for a newly created
     * archive, or the highest archive leaf for an existing archive.
     * @throws java.io.IOException If exist == true, then the archive is
     * expected to exist and have the correct form, if it does not, then an
     * {@link IOException} is thrown.
     */
    public Path initialiseArchive(Path dir, long range, boolean exists)
            throws IOException {
        if (exists) {
            // Check it is an archive with the correct range.
            if (!Files.exists(dir)) {
                throw new IOException("Attempting to initialise an Archive "
                        + "that is supposed to exists in directory "
                        + dir + ", but such a directory does not exist.");
            }
            return testArchiveIntegrity(dir);
        } else {
            if (Files.exists(dir)) {
                if (Files.list(dir).count() > 0) {
                    throw new IOException("Attempting to initialise an "
                            + "Archive in a non-empty directory.");
                }
            }
            return initialiseArchive(dir, range);
        }
    }

    /**
     *
     * @param dir The base of the archive to test.
     * @return The archive highest leaf obtained from
     * {@link #getArchiveHighestLeafFile(java.nio.file.Path)}
     * @throws java.io.IOException If the archive lacks integrity.
     */
    public Path testArchiveIntegrity(Path dir) throws IOException {
        if (Files.list(dir).count() != 1) {
            throw new IOException("Directory " + dir + " contains more than "
                    + "one base level directory, so the archive does not have "
                    + "integrity.");
        }
        // File dir1 = dir0[0];
        boolean allPathsOK;
        try (Stream<Path> paths = Files.walk(dir)) {
            //paths.forEach(System.out::println);
            //paths.forEach(path -> testPathSystem.out.println(path));
//            paths.forEach(path -> {
//                testPath(path);
//            });
            allPathsOK = paths.allMatch(path -> testPath(path));
        } catch (IOException e) {
            throw e;
        }
        if (!allPathsOK) {
            throw new IOException("Some paths are not OK in "
                    + this.getClass().getName() + ".testArchiveIntegrity(File)");
        }
        Path hlf = getArchiveHighestLeafFile(dir);
        env.log("Archive at " + dir + " has integrity with a HighestLeaf File " + hlf);
        return hlf;
    }

    private boolean testPath(Path path) {
        String fn = path.getFileName().toString();
        if (fn.contains(archiveSeparator)) {
            //System.out.println(path);
            return true;
        } else {
            try {
                Long.valueOf(fn);
                //System.out.println(path);
                return true;
            } catch (NumberFormatException e) {
                System.out.println(fn);
                System.out.println("Integrity broken?");
                return false;
            }
        }
    }

    /**
     * Initialises a directory in directory and returns result.
     *
     * @param dir File directory.
     * @param range long
     * @return File
     */
    private Path initialiseArchive(Path dir, long range) throws IOException {
        Path r;
        String start = "0";
        long end = range - 1;
        r = Paths.get(dir.toString(), start + archiveSeparator + end, start);
        Files.createDirectories(r);
        return r;
    }

    /**
     * Initialises an Archive directory structure. The structure of the archive
     * is given by the range.
     *
     * @param dir File directory.
     * @param range long
     * @param n The maximum number of objects to be stored.
     * @throws IOException If attempting to initialise the archive in a
     * non-empty directory.
     */
    public void initialiseArchive(Path dir, long range, long n)
            throws IOException {
        initialiseArchive(dir, range, false);
        for (long l = 0L; l < n; l++) {
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
     * @param n The maximum number of objects to be stored.
     * @return TreeMap
     * @throws IOException If attempting to initialise the archive in a
     * non-empty directory.
     */
    public TreeMap<Long, Path> initialiseArchiveAndReturnFileMap(
            Path dir, long range, long n) throws IOException {
        initialiseArchive(dir, range, n);
        return getArchiveLeafFilesMap(dir, 0L, n);
    }

    /**
     *
     * @param dir File directory.
     * @return long
     * @throws java.io.IOException If encountered.
     */
    public long getArchiveHighestLeaf(Path dir) throws IOException {
        long r;
        List<Path> l = getList(dir);
        if (l.isEmpty()) {
            return -1L;
        } else {
            if (l.size() == 1) {
                Path p0 = l.get(0);
                if (p0.getFileName().toString().contains(archiveSeparator)) {
                    l = getList(p0);
                }
            }
            TreeMap<Long, Path> fs = getNumericallyOrderedFiles(l);
            Path lf = fs.lastEntry().getValue();
            String fn = lf.getFileName().toString();
            if (fn.contains(archiveSeparator)) {
                return getArchiveHighestLeaf(lf);
            } else {
                r = Long.valueOf(fn);
            }
        }
        return r;
    }

    /**
     *
     * @param dir File
     * @return long
     * @throws java.io.IOException If Encountered.
     */
    public long getArchiveRange(Path dir) throws IOException {
        Path highestLeaf_File = getArchiveHighestLeafFile(dir);
        String[] split = highestLeaf_File.getParent().getFileName().toString()
                .split(archiveSeparator);
        long min = Long.valueOf(split[0]);
        long max = Long.valueOf(split[1]);
        return max - min + 1;
    }

    /**
     * For returning all the leaf file elements in a branch of an archive as a
     * HashSet&lt;File&gt;.An archive has directories such as ./0_99 which
 store leaves such as ./0_99/0 and ./0_99/10. The archive may have
 considerable depth such that an archive leaf is stored for example in
 ./0_999999/0_9999/0_99/0. The leaves are the directories at the end of
 the tree branches that contain directories that do not have an underscore
 in the filename
     *
     * @param dir File directory.
     * @return a HashSet&lt;File&gt; containing all files in the directory. The
     * directory is regarded as a directory that possibly contains other
     * directories in a branching tree with paths that are expected to end
     * eventually in one or more files. All such files are returned in the
     * result. The result is null if the directory is empty.
     * @throws java.io.IOException If encountered.
     */
    public HashSet<Path> getArchiveLeafFilesSet(Path dir) throws IOException {
        HashSet<Path> r = new HashSet<>();
        List<Path> topLevelArchiveFiles = getList(dir);
        for (Path topLevelArchiveFile : topLevelArchiveFiles) {
            r.addAll(getArchiveLeafFilesSet0(topLevelArchiveFile));
        }
        return r;
    }

    /**
     *
     * @param f File.
     * @return HashSet
     */
    private HashSet<Path> getArchiveLeafFilesSet0(Path f) throws IOException {
        HashSet<Path> r = new HashSet<>();
        if (f.getFileName().toString().contains(archiveSeparator)) {
            List<Path> l = getList(f);
            for (Path file : l) {
                r.addAll(getArchiveLeafFilesSet0(file));
            }
        } else {
            r.add(f);
        }
        return r;
    }

    /**
     * Potentially slow and could be sped up by going through the file tree
     * branch by branch.
     *
     * @param dir Path to a directory..
     * @return TreeMap
     * @throws java.io.IOException
     */
    public TreeMap<Long, Path> getArchiveLeafFilesMap(Path dir) 
            throws IOException {
        TreeMap<Long, Path> r = new TreeMap<>();
        List<Path> topLevelArchiveFiles = getList(dir);
        for (Path f : topLevelArchiveFiles) {
            r.putAll(getArchiveLeafFilesMap0(f));
        }
        return r;
    }

    /**
     *
     * @param file File
     * @return TreeMap
     */
    private TreeMap<Long, Path> getArchiveLeafFilesMap0(Path file) 
            throws IOException {
        TreeMap<Long, Path> r = new TreeMap<>();
        if (file.getFileName().toString().contains(archiveSeparator)) {
            List<Path> files = getList(file);
            for (Path f : files) {
                r.putAll(getArchiveLeafFilesMap0(f));
            }
        } else {
            r.put(Long.valueOf(file.getFileName().toString()), file);
        }
        return r;
    }

    /**
     *
     * @param dir File.
     * @param minID long
     * @param maxID long
     * @return TreeMap
     * @throws java.io.IOException If encountered.
     */
    public TreeMap<Long, Path> getArchiveLeafFilesMap(Path dir,
            long minID, long maxID) throws IOException {
        TreeMap<Long, Path> r = new TreeMap<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path p : stream) {
                r.putAll(getArchiveLeafFilesMap0(p, minID, maxID));
            }
        }
        return r;
    }

    /**
     *
     * @param file
     * @param minID
     * @param maxID
     * @return TreeMap
     */
    private TreeMap<Long, Path> getArchiveLeafFilesMap0(
            Path file, long minID, long maxID) throws IOException {
        TreeMap<Long, Path> r = new TreeMap<>();
        if (file.getFileName().toString().contains(archiveSeparator)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(file)) {
                for (Path p : stream) {
                    r.putAll(getArchiveLeafFilesMap0(p, minID, maxID));
                }
            }
        } else {
            long id = Long.valueOf(file.getFileName().toString());
            if (id >= minID && id <= maxID) {
                r.put(Long.valueOf(file.getFileName().toString()), file);
            }
        }
        return r;
    }

    /**
     * @param dir The directory to list.
     * @return A list of files and directories in dir.
     * @throws IOException If encountered.
     */
    public List<Path> getList(Path dir) throws IOException {
        return Files.list(dir).collect(Collectors.toList());
    }

    /**
     *
     * @param dir File.
     * @return The highest numbered File
     * @throws java.io.IOException If encountered.
     */
    public Path getArchiveHighestLeafFile(Path dir) throws IOException {
        Path r;
        List<Path> l = getList(dir);
        if (l.isEmpty()) {
            return null;
        } else {
            TreeMap<Long, Path> ofiles = getNumericallyOrderedFiles2(l);
            Path f = ofiles.lastEntry().getValue();
            if (f.getFileName().toString().contains(archiveSeparator)) {
                return getArchiveHighestLeafFile(f);
            } else {
                r = f;
            }
        }
        return r;
    }

    /**
     * Expands the base of an Archive.
     *
     * @param dir The base directory File of the Archive to grow.
     * @param range The maximum number of directories in any directory.
     * @return File the new top of the archive directory.
     * @throws java.io.IOException Iff an IOException was encountered.
     */
    public Path growArchiveBase(Path dir, long range) throws IOException {
        List<Path> l = getList(dir);
        TreeMap<Long, Path> ofiles = getNumericallyOrderedFiles2(l);
        String dirName0 = ofiles.firstEntry().getValue().getFileName().toString();
        String[] split0 = dirName0.split(archiveSeparator);
        //long start0 = new Long(split0[0]).longValue();
        long end0 = Long.valueOf(split0[1]);
        long newRange = range * (end0 + 1L);
        // Create new top directory and move in existing files
        Path newTop0 = Paths.get(dir.toString(),
                "" + 0 + archiveSeparator + (newRange - 1L));
        Path newTop = Paths.get(newTop0.toString());
        Files.createDirectory(newTop0);
        for (int i = 0; i < l.size(); i++) {
            Path p = l.get(i);
            Path newPath = Paths.get(newTop.toString(), p.getFileName().toString());
            Files.move(p, newPath);
        }
        // Create new lower directories for next ID;
        Long next_ID = getArchiveHighestLeaf(dir);
        next_ID++;
        Path newHighestLeafDir = Paths.get(
                getObjectDir(newTop, next_ID, next_ID, range).toString(),
                "" + next_ID);
        Files.createDirectories(newHighestLeafDir);
        return newTop0;
    }

    /**
     *
     * @param dir The base directory File of the Archive to grow.
     * @param range The maximum number of directories in any directory.
     * @param next_ID The next_ID of the file that is added in the newly grown
     * archive.
     * @return The new file which is the top of the archive directory.
     * @throws java.io.IOException If encountered.
     */
    public Path growArchiveBase(Path dir, long range, long next_ID)
            throws IOException {
        List<Path> l = getList(dir);
        TreeMap<Long, Path> ofiles = getNumericallyOrderedFiles2(l);
        Path file0 = ofiles.firstEntry().getValue();
        String[] split0 = file0.getFileName().toString().split(archiveSeparator);
        //long start0 = new Long(split0[0]).longValue();
        long end0 = Long.valueOf(split0[1]);
        long newRange = range * (end0 + 1L);
        // Create new top directory and move in existing files
        Path newTop0 = Paths.get(dir.toString(),
                "" + 0 + archiveSeparator + (newRange - 1L));
        Path newTop = Paths.get(newTop0.toString());
        Files.createDirectories(newTop0);
        for (int i = 0; i < l.size(); i++) {
            Path p = l.get(i);
            Path newPath = Paths.get(newTop.toString(),
                    p.getFileName().toString());
            Files.move(p, newPath);
        }
        Path newHighestLeaf_Directory = Paths.get(
                getObjectDir(newTop, next_ID, next_ID, range).toString(),
                "" + next_ID);
        Files.createDirectories(newHighestLeaf_Directory);
        return newTop0;
    }

    /**
     *
     * @param dir The base directory File of the Archive to grow.
     * @param range The maximum number of directories in any directory.
     * @return The added directory path.
     * @throws java.io.IOException If there is an IOException.
     */
    public Path addToArchive(Path dir, long range) throws IOException {
        Long next_ID = getArchiveHighestLeaf(dir);
        next_ID++;
        Path newHighestLeafDir = Paths.get(
                getObjectDir(dir, next_ID, next_ID + 1, range).toString(),
                "" + next_ID);
        growArchiveBaseIfNecessary(dir, range, newHighestLeafDir);
        return newHighestLeafDir;
    }

    protected void growArchiveBaseIfNecessary(Path dir, long range,
            Path newHighestLeafDir) throws IOException {
        String[] split = newHighestLeafDir.getFileName().toString()
                .split(archiveSeparator);
        long startnew = Long.valueOf(split[0]);
        long endnew = Long.valueOf(split[1]);
        long rangenew = endnew - startnew;
        split = Files.list(dir).findAny().toString().split(archiveSeparator);
        long startold = Long.parseLong(split[0]);
        long endold = Long.parseLong(split[1]);
        long rangeold = endold - startold;
        if (rangenew > rangeold) {
            growArchiveBase(dir, range);
        }
        Files.createDirectories(newHighestLeafDir);
    }

    /**
     *
     * @param dir The base directory File of the Archive to grow.
     * @param range The maximum number of directories in any directory.
     * @param next_ID The next_ID of the file that is added in the newly grown
     * archive.
     * @return File
     * @throws java.io.IOException Iff an IOException was encountered.
     */
    public Path addToArchive(Path dir, long range, long next_ID) throws IOException {
        Path newHighestLeafDir = Paths.get(
                getObjectDir(dir, next_ID, next_ID + 1, range).toString(),
                "" + next_ID);
        growArchiveBaseIfNecessary(dir, range, newHighestLeafDir);
        return newHighestLeafDir;
    }

    /**
     * @param dir The directory in which the file should exist.
     * @param filename The name of the File which should exist.
     * @return The File with filename in directory or throws
     * FileNotFoundException if directory or the result does not exist.
     * @throws java.io.FileNotFoundException If dir does not exist or file with
     * filename in dir does not exist.
     */
    public Path getFileThatExists(Path dir, String filename)
            throws FileNotFoundException {
        String method = "getFile(File,String)";
        if (!Files.exists(dir)) {
            throw new FileNotFoundException(
                    "Directory " + dir + " does not exist in "
                    + Generic_IO.class
                            .getName() + "." + method + ".");
        }
        Path r = null;
        if (filename != null) {
            r = Paths.get(dir.toString(), filename);
            if (!Files.exists(r)) {
                throw new FileNotFoundException(
                        "File " + r + " does not exist in "
                        + Generic_IO.class
                                .getName() + "." + method + ".");
            }
        }
        return r;
    }

//    /**
//     * @param dir File.
//     * @param f File.
//     * @return The name of the file or directory in dir in the path of f.
//     */
//    public String getFilename(Path dir, Path f) {
//        int beginIndex = dir.toAbsolutePath().toString().length() + 1;
//        String fileSeparator = System.getProperty("file.separator");
//        /*
//         * A feature in Java means splitting strings with "\" does not work as
//         * might be expected and the regexp needs changing to "\\\\"
//         */
//        String regexp = "\\";
//        //System.out.println("regexp " + regexp);
//        if (fileSeparator.equals(regexp)) {
//            fileSeparator = "\\\\";
//        }
//        //System.out.println("fileSeparator " + fileSeparator);
//        //String newTopOfArchiveDirectoryName = (objectDirectoryFile.getAbsolutePath().substring(beginIndex)).split(System.getProperty("file.separator"))[0];
//        //String newTopOfArchiveDirectoryName = (objectDirectoryFile.getPath().substring(beginIndex)).split("\\")[0];
//        //String newTopOfArchiveDirectoryName = (objectDirectoryFile.getPath().substring(beginIndex)).split("/")[0];
//        return (f.toString().substring(beginIndex)).split(fileSeparator)[0];
//    }
    /**
     * @param ps The files are expected to be from an archive or to be
     * numerically ordered. These are specially names. Either they have a name
     * that can be resolved as a long. Or they have a name containing a
     * {@link Generic_Strings#symbol_underscore} with components that can be
     * resolved as long (e.g. 0_99, 100_199).
     * @return TreeMap
     */
    public TreeMap<Long, Path> getNumericallyOrderedFiles(List<Path> ps) {
        TreeMap<Long, Path> r = new TreeMap<>();
        ps.forEach((p) -> {
            String filename = p.getFileName().toString();
            long l;
            if (filename.contains(Generic_Strings.symbol_underscore)) {
                String[] split = filename.split(Generic_Strings.symbol_underscore);
                l = Long.valueOf(split[split.length - 1]);
            } else {
                l = Long.valueOf(filename);
            }
            r.put(l, p);
        });
        return r;
    }

    /**
     * If needed to order also by a number after the underscore then a new
     * method is needed.
     *
     * @param files File[]
     * @return TreeMap
     */
    public TreeMap<Long, Path> getNumericallyOrderedFiles2(List<Path> files) {
        TreeMap<Long, Path> r = new TreeMap<>();
        files.forEach((file) -> {
            String name = file.getFileName().toString();
            if (name.contains(archiveSeparator)) {
                String[] split = name.split(archiveSeparator);
                if (split.length <= 2) {
                    long start;
                    try {
                        start = Long.parseLong(split[0]);
                        if (split.length == 2) {
                            Long.parseLong(split[1]);
                        }
                        r.put(start, file);
                    } catch (NumberFormatException ex) {
                        // Ignore
                    }
                }
            } else {
                try {
                    r.put(Long.valueOf(name), file);
                } catch (NumberFormatException ex) {
                    // Ignore
                }
            }
        });
        return r;
    }

    /**
     * This calculates and returns a Map of files with keys where the first
     * represents the numerically ordered
     *
     * @param dir File.
     * @return HashMap
     * @throws java.io.IOException If encountered.
     */
    public HashMap<Long, String> getNumericallyOrderedFiles(Path dir)
            throws IOException {
        HashMap<Long, String> r = new HashMap<>();
        List<Path> l = getList(dir);
        for (int i = 0; i < l.size(); i++) {
            String name = l.get(i).getFileName().toString();
            long k;
            if (name.contains(archiveSeparator)) {
                k = Long.parseLong(name.split(archiveSeparator)[1]);
            } else {
                k = Long.valueOf(name);
            }
            r.put(k, name);
        }
        return r;
    }

    /**
     * Method to calculate the length of the file path.
     *
     * @param f Path for which the normalised path length is returned.
     * @return normalised path length.
     * @throws java.io.IOException If encountered.
     */
    public int getFilePathLength(Path f) throws IOException {
        String s = f.normalize().toString();
        return s.length();
    }

    /**
     *
     * @param f File.
     * @param dir File.
     * @return int
     * @throws java.io.IOException If encountered.
     */
    public int getFilePathLength(Path f, Path dir) throws IOException {
        int fl = getFilePathLength(f);
        int dl = getFilePathLength(dir);
        return fl - dl;
    }

    /**
     * Returns a newly created file in directory returned by
     * {@link Generic_Files#getGeneratedDir()}.
     *
     * @return A {@link File} for a newly created file in directory returned by
     * {@link Generic_Files#getGeneratedDir()}.
     * @throws java.io.IOException If directory returned by
     * {@link Generic_Files#getGeneratedDir()} is not a directory (but this
     * should not happen).
     */
    public Path createNewFile() throws IOException {
        return createNewFile(env.files.getGeneratedDir());
    }

    /**
     * Returns a newly created File in the directory dir.
     *
     * @param dir The directory in which the newly created file is created
     * @return The File created.
     * @throws java.io.IOException If dir exists and is not a directory.
     */
    public Path createNewFile(Path dir) throws IOException {
        return createNewFile(dir, "", "");
    }

    /**
     * Returns a newly created File (which may be a directory).
     *
     * @param dir The directory into which the new File is to be created.
     * @param prefix The first part of the filename.
     * @param suffix The last part of the filename.
     * @return The file of a newly created file in dir. The name of the file
     * will begin with prefix and end with suffix. If a file already exists with
     * a name which is just the prefix appended to the suffix, then a number is
     * inserted between these two parts of the filename. The first number tried
     * is 0, the number then increases by 1 each try.
     * @throws java.io.IOException If dir exists and is not a directory.
     */
    public Path createNewFile(Path dir, String prefix, String suffix)
            throws IOException {
        if (Files.exists(dir)) {
            if (!Files.isDirectory(dir)) {
                throw new IOException("Attempting to create a file in " + dir
                        + " but this is not a directory.");
            }
        } else {
            Files.createDirectories(dir);
        }
        Path r = null;
        try {
            if (prefix == null) {
                prefix = "";
            }
            if (suffix == null) {
                suffix = "";
            }
            do {
                r = getNewFile(dir, prefix, suffix);
            } while (!Files.exists(Files.createFile(r)));
        } catch (IOException ioe0) {
            String methodName = this.getClass().getName()
                    + ".createNewFile(Path,String,String)";
            if (r != null) {
                System.out.println("Path " + r.toString() + " in " + methodName);
            } else {
                System.out.println("Path null in " + methodName);
            }
            ioe0.printStackTrace(System.err);
        }
        return r;
    }

    /**
     * This attempts to return a new file in the directory {@code dir}. The file
     * will have a name starting {@code prefix} and ending {@code suffix}. If
     * such a file already exists then a n is inserted between the
     * {@code prefix} and {@code suffix}, where n is a positive long. Firstly n
     * = 0 is tried and if this file already exists then n = 1 is tried and so
     * on until a unique file is returned.
     *
     * @param dir The directory in which to return the File.
     * @param prefix The first part of the filename.
     * @param suffix The last part of the filename.
     * @return A File for a file which is thought not to exist.
     */
    private Path getNewFile(Path dir, String prefix, String suffix) {
        Path r;
        if (prefix.isEmpty() && suffix.isEmpty()) {
            long n = 0;
            do {
                r = Paths.get(dir.toString(), "" + n);
                n++;
            } while (Files.exists(r));
        } else {
            r = Paths.get(dir.toString(), prefix + suffix);
            if (Files.exists(r)) {
                long n = 0;
                do {
                    r = Paths.get(dir.toString(), prefix + n + suffix);
                    n++;
                } while (Files.exists(r));
            }
        }
        return r;
    }
}
