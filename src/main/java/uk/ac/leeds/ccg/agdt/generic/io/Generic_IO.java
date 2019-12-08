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
import java.io.FileNotFoundException;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Object;

/**
 * Contains convenient methods for primarily helping to read from and write to a
 * file system.
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
    }

    /**
     * If dir is a directory this recursively goes through the contents and
     * creates an ArrayList of the paths of all files (not directories) to
     * return.
     *
     * @param dir The path to traverse.
     * @return An ArrayList of the paths of all files in dir and any
     * subdirectories.
     * @throws IOException If dir is not a directory and if otherwise
     * encountered.
     */
    public static List<Path> getFiles(Path dir) throws IOException {
        if (Files.isDirectory(dir)) {
            List<Path> r = new ArrayList<>();
            addFiles(dir, r);
            return r;
        } else {
            throw new IOException("Path " + dir.toString() + " is not a directory");
        }
    }

    /**
     * Recursively traverses a directory creating a set of File paths of files
     * (i.e. not directories).
     *
     * @param dir The path to add files to l from.
     * @param l The list to add to.
     * @throws java.io.IOException If encountered.
     */
    protected static void addFiles(Path dir, List<Path> l) throws IOException {
        try (DirectoryStream<Path> s = Files.newDirectoryStream(dir)) {
            for (Path p : s) {
                if (Files.isDirectory(p)) {
                    addFiles(p, l);
                } else {
                    l.add(p);
                }
            }
        }
    }

    /**
     * Writes Object o to a file at f.
     *
     * @param o Object to be written.
     * @param f File to write to.
     * @throws IOException If encountered.
     */
    public static void writeObject(Object o, Path f) throws IOException {
        //Files.createDirectories(f.getParent()); // Try to avoid this as it slows things down.
        try (ObjectOutputStream oos = new ObjectOutputStream(
                Files.newOutputStream(f, WRITE))) {
            oos.writeUnshared(o);
            oos.flush();
            oos.reset();
        }
    }

    /**
     * Read an Object from a file at p.
     *
     * @param p Path to a file be read from.
     * @return Object read from the file at p.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If for some reason the Object
     * cannot otherwise be deserialized.
     */
    public static Object readObject(Path p) throws IOException,
            ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                Files.newInputStream(p, READ))) {
            return ois.readUnshared();
        }
    }

    /**
     * Writes Object o to a file at p and logs the name of the Object written
     * and the path.
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
     * Copies a file from f to d.
     *
     * @param f A Path of a file to be copied.
     * @param d The Path of a directory to copy the file into.
     * @throws java.io.IOException If encountered.
     */
    protected void copyFile(Path f, Path d) throws IOException {
        copyFile(f, d, f.getFileName().toString());
    }

    /**
     * Copies a file from f to d renaming it to fn in the process. If there is
     * no directory at d then this is created.
     *
     * This might be improved using Files.copy(f, target, REPLACE_EXISTING) and
     * similar...
     *
     * @param f A Path of a file to be copied.
     * @param d The Path of a directory to copy to.
     * @param fn The name for the file that will be created in d.
     * @throws java.io.IOException If encountered.
     */
    public void copyFile(Path f, Path d, String fn)
            throws IOException {
        if (!Files.exists(f)) {
            throw new IOException("Path " + f + " is not to a file.");
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
            long nArrayReads = length / bufferSize;
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
            throws FileNotFoundException, IOException {
        return new BufferedInputStream(Files.newInputStream(f, READ));
    }

//    /**
//     * @param f File.
//     * @return FileInputStream
//     * @throws java.io.FileNotFoundException If the file exists but is a
//     * directory rather than a regular file, does not exist but cannot be
//     * created, or cannot be opened for any other reason.
//     */
//    public FileInputStream getFileInputStream(Path f) throws FileNotFoundException {
//        FileInputStream r = null;
//        try {
//            r = new FileInputStream(f.toFile());
//        } catch (FileNotFoundException ex) {
//            if (Files.exists(f)) {
//                long wait = 2000L;
//                fileWait(wait, f);
//                return getFileInputStream(f, wait);
//            } else {
//                throw ex;
//            }
//        }
//        return r;
//    }
//
//    protected void fileWait(long wait, Object o) {
//        env.log("Maybe there are too many open files... "
//                + "waiting for " + wait + " milliseconds...");
//        Generic_Execution.waitSychronized(env, o, wait);
//    }
//
//    /**
//     * @param f File.
//     * @param wait Time in milliseconds to wait before trying to open the
//     * FileInputStream again if it failed the first time (this may happen if
//     * waiting for a file to be written).
//     * @return FileInputStream
//     * @throws java.io.FileNotFoundException If the file exists but is a
//     * directory rather than a regular file, does not exist but cannot be
//     * created, or cannot be opened for any other reason.
//     */
//    public FileInputStream getFileInputStream(Path f, long wait)
//            throws FileNotFoundException {
//        new FileInputStream();
//        try {
//            return new FileInputStream(f.toFile());
//        } catch (FileNotFoundException ex) {
//            if (Files.exists(f)) {
//                fileWait(wait, f);
//                return getFileInputStream(f, wait * 2L);
//            } else {
//                throw ex;
//            }
//        }
//    }
    /**
     * For getting a {@link BufferedOutputStream} to write to a file at
     * {@code f}.
     *
     * @param f The {@link Path} of the file to be written.
     * @return A {@link BufferedOutputStream} for writing to {@code f}.
     * @throws java.io.IOException If the file exists but is a directory rather
     * than a regular file, does not exist but cannot be created, or cannot be
     * opened for any other reason.
     */
    public BufferedOutputStream getBufferedOutputStream(Path f)
            throws IOException {
        return new BufferedOutputStream(Files.newOutputStream(f, WRITE));
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
     * @throws java.io.IOException If encountered and not otherwise handled.
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

    /**
     * A class for recursively copying a directory.
     */
    private class CopyDir extends SimpleFileVisitor<Path> {

        private final Path sourceDir;
        private final Path targetDir;

        public CopyDir(Generic_IO io, Path sourceDir, Path targetDir) {
            this.sourceDir = sourceDir;
            this.targetDir = targetDir;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
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
     * Delete all files and directories in a directory.
     *
     * @param d The directory containing everything to delete.
     * @param log If true then deletions are logged.
     * @throws java.io.IOException If encountered and not logged.
     */
    public static void delete(Path d, boolean log) throws IOException {
        if (log) {
            try (Stream<Path> walk = Files.walk(d)) {
                walk.sorted(Comparator.reverseOrder())
                        .peek(System.out::println) // Log deletions to std.out.
                        .forEach(p -> {
                            try {
                                Files.deleteIfExists(p);
                            } catch (IOException ex) {
                                Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
            }
        } else {
            try (Stream<Path> walk = Files.walk(d)) {
                walk.sorted(Comparator.reverseOrder())
                        .forEach(p -> {
                            try {
                                Files.deleteIfExists(p);
                            } catch (IOException ex) {
                                Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
            }
        }
    }

    /**
     * @param f The Path of a file.
     * @return BufferedReader
     * @throws java.io.FileNotFoundException If the file exists but is a
     * directory rather than a regular file, does not exist but cannot be
     * created, or cannot be opened for any other reason.
     */
    public BufferedReader getBufferedReader(Path f)
            throws FileNotFoundException, IOException {
        return getBufferedReader(f, "UTF-8");
    }

    /**
     * @param f File.
     * @param charsetName The name of a supported
     * {@link java.nio.charset.Charset charset} e.g. "UTF-8"
     * @return BufferedReader
     * @throws java.io.UnsupportedEncodingException If InputStreamReader cannot
     * be constructed from charsetName.
     * @throws java.io.IOException If encountered.
     */
    public BufferedReader getBufferedReader(Path f, String charsetName)
            throws UnsupportedEncodingException, IOException {
        return new BufferedReader(new InputStreamReader(
                Files.newInputStream(f, READ), charsetName));
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
            throws FileNotFoundException, IOException {
        closeBufferedReader(br);
        br = getBufferedReader(f);
        return br;
    }

    /**
     * Write {@code s} to a file at {@code p}.
     *
     * @param p The path to the file to write to.
     * @param s The String to write.
     * @throws IOException If encountered.
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
        if (append) {
            return new PrintWriter(Files.newBufferedWriter(f, WRITE, CREATE,
                    APPEND));
        } else {
            return new PrintWriter(Files.newBufferedWriter(f, WRITE, CREATE));
        }
//        try {
//            return new PrintWriter(new BufferedWriter(f, append));
//        } catch (FileNotFoundException ex) {
//            if (Files.exists(f)) {
//                long wait = 2000L;
//                fileWait(wait, f);
//                return getPrintWriter(f, append, wait);
//            } else {
//                throw ex;
//            }
//        }
    }

//    /**
//     * @param f The File to write to.
//     * @param append If true an existing file will be appended otherwise it will
//     * be overwritten.
//     * @param wait Time in milliseconds to wait before trying to open the
//     * FileInputStream again if it failed the first time (this may happen if
//     * waiting for a file to be written).
//     * @return PrintWriter
//     * @throws IOException If the file exists but is a directory rather than a
//     * regular file, does not exist but cannot be created, or cannot be opened
//     * for any other reason.
//     */
//    public PrintWriter getPrintWriter(Path f, boolean append, long wait)
//            throws IOException {
//        try {
//            return new PrintWriter(new BufferedWriter(new FileWriter(
//                    f.toFile(), append)));
//        } catch (FileNotFoundException ex) {
//            if (Files.exists(f)) {
//                fileWait(wait, f);
//                return getPrintWriter(f, append, wait * 2L);
//            } else {
//                throw ex;
//            }
//        }
//    }
//
//    /**
//     *
//     * @param depth int
//     * @return String
//     */
//    public String getRelativeFilePath(int depth) {
//        String r = "";
//        for (int i = 0; i < depth; i++) {
//            r += "../";
//        }
//        return r;
//    }
//
//    /**
//     * @param depth int.
//     * @param f File.
//     * @return f.getPath() appended with depth number of "../"
//     */
//    public String getRelativeFilePath(int depth, Path f) {
//        return getRelativeFilePath(depth, f.toString());
//    }
//
//    /**
//     * @param depth int.
//     * @param path String.
//     * @return path appended with depth number of "../"
//     */
//    public String getRelativeFilePath(int depth, String path) {
//        String r = path;
//        for (int i = 0; i < depth; i++) {
//            r += "../";
//        }
//        return r;
//    }
//
//    /**
//     * Skips to the next token of StreamTokenizer.TT_EOL type in st.nextToken().
//     *
//     * @param st StreamTokenizer
//     * @throws java.io.IOException If IOException encountered.
//     */
//    public void skipline(StreamTokenizer st) throws IOException {
//        int token = st.nextToken();
//        while (token != StreamTokenizer.TT_EOL) {
//            token = st.nextToken();
//        }
//    }
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
    public void setStreamTokenizerSyntax1(StreamTokenizer st) {
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
    public void setStreamTokenizerSyntax2(StreamTokenizer st) {
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
    public void setStreamTokenizerSyntax3(StreamTokenizer st) {
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
    public void setStreamTokenizerSyntax4(StreamTokenizer st) {
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
    public void setStreamTokenizerSyntax5(StreamTokenizer st) {
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
    public void setStreamTokenizerSyntax6(StreamTokenizer st) {
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
    public void setStreamTokenizerSyntax7(StreamTokenizer st) {
        setStreamTokenizerSyntax6(st);
        st.wordChars('<', '<');
        st.wordChars('>', '>');
    }

    /**
     * @param dir The directory to list.
     * @return A list of files and directories in dir.
     * @throws IOException If encountered.
     */
    public static List<Path> getList(Path dir) throws IOException {
        try (Stream<Path> s = Files.list(dir)) {
            return s.collect(Collectors.toList());
        }
    }

//    /**
//     * @param dir File.
//     * @param f File.
//     * @return The name of the file or directory in dir in the path of f.
//     */
//    public String getFilename(Path dir, Path f) {
//        int beginIndex = dir.normalize().toString().length() + 1;
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

//    /**
//     *
//     * @param f File.
//     * @param dir File.
//     * @return int
//     * @throws java.io.IOException If encountered.
//     */
//    public int getFilePathLength(Path f, Path dir) throws IOException {
//        int fl = getFilePathLength(f);
//        int dl = getFilePathLength(dir);
//        return fl - dl;
//    }
    /**
     * Returns a newly created file in directory returned by
     * {@link Generic_Files#getGeneratedDir()}.
     *
     * @return A path for a newly created file in directory returned by
     * {@link Generic_Files#getGeneratedDir()}.
     * @throws java.io.IOException If directory returned by
     * {@link Generic_Files#getGeneratedDir()} is not a directory (but this
     * should not happen).
     */
    public Path createNewFile() throws IOException {
        return createNewFile(env.files.getGeneratedDir().getPath());
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
