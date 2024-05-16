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
package uk.ac.leeds.ccg.generic.io;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.io.IO_Utilities;

/**
 * Contains convenient methods for primarily helping to read from and write to a
 * file system.
 */
public class Generic_IO extends IO_Utilities {

    /**
     * Create a new instance.
     */
    public Generic_IO(){}
            
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
    public static void setStreamTokenizerSyntax1(StreamTokenizer st) {
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
    private static void setWhitespaceAsWords(StreamTokenizer st) {
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
    public static void setStreamTokenizerSyntax2(StreamTokenizer st) {
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
    public static void setStreamTokenizerSyntax3(StreamTokenizer st) {
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
    public static void setStreamTokenizerSyntax4(StreamTokenizer st) {
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
    public static void setStreamTokenizerSyntax5(StreamTokenizer st) {
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
    public static void setStreamTokenizerSyntax6(StreamTokenizer st) {
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
    public static void setStreamTokenizerSyntax7(StreamTokenizer st) {
        setStreamTokenizerSyntax6(st);
        st.wordChars('<', '<');
        st.wordChars('>', '>');
    }

    /**
     * @param st <code>StreamTokenizer</code> that's syntax is set
     */
    public static void setStreamTokenizerSyntax8(StreamTokenizer st) {
        setStreamTokenizerSyntax7(st);
        st.wordChars('[', '[');
        st.wordChars(']', ']');
    }

    /**
     * @param st <code>StreamTokenizer</code> that's syntax is set
     */
    public static void setStreamTokenizerSyntax9(StreamTokenizer st) {
        setStreamTokenizerSyntax8(st);
        //st.wordChars(';', ';');
        st.wordChars(59, 59); // 59 is the ASCII code for ;
    }

    /**
     * Returns a newly created file in directory returned by
     * {@link Generic_Files#getGeneratedDir()}.
     *
     * @param files Used to get the path of the directory the generated file
     * will be stored in.
     * @return A path for a newly created file in directory returned by
     * {@link Generic_Files#getGeneratedDir()}.
     * @throws java.io.IOException If directory returned by
     * {@link Generic_Files#getGeneratedDir()} is not a directory (but this
     * should not happen).
     */
    public static Path createNewFile(Generic_Files files) throws IOException {
        return createNewFile(files.getGeneratedDir().getPath());
    }

    /**
     * Returns a newly created File in the directory dir.
     *
     * @param dir The directory in which the newly created file is created
     * @return The File created.
     * @throws java.io.IOException If dir exists and is not a directory.
     */
    public static Path createNewFile(Path dir) throws IOException {
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
    public static Path createNewFile(Path dir, String prefix, String suffix)
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
            String methodName = Generic_IO.class.getName()
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
    private static Path getNewFile(Path dir, String prefix, String suffix) {
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
