/**
 * A component of a library for
 * <a href="http://www.geog.leeds.ac.uk/people/a.turner/projects/MoSeS">MoSeS</a>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamTokenizer;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import uk.ac.leeds.ccg.andyt.generic.utilities.ErrorAndExceptionHandler;

/**
 * Class of static methods for helping with reading and writing (primarily)
 * to/from file.
 */
public class StaticIO {

    /** Creates a new instance of ObjectIO */
    public StaticIO() {
    }

    /**
     * Write object to file
     * @param object
     * @param file
     */
    public static void writeObject(
            Object object,
            File file) {
        try {
            ObjectOutputStream _ObjectOutputStream =
                    new ObjectOutputStream(
                    new BufferedOutputStream(
                    new FileOutputStream(file)));
            _ObjectOutputStream.writeObject(object);
            _ObjectOutputStream.flush();
            _ObjectOutputStream.close();
        } catch (IOException aIOException) {
            aIOException.printStackTrace();
            System.exit(ErrorAndExceptionHandler.IOException);
        }
    }

    /**
     * Read Object from File
     * @param file
     * @return 
     */
    public static Object readObject(
            File file) {
        Object result = null;
        if (file.length() != 0) {
            try {
                ObjectInputStream _ObjectInputStream =
                        new ObjectInputStream(
                        new BufferedInputStream(
                        new FileInputStream(file)));
                result = _ObjectInputStream.readObject();
                _ObjectInputStream.close();
            } catch (IOException aIOException) {
                aIOException.printStackTrace();
                System.exit(ErrorAndExceptionHandler.IOException);
            } catch (ClassNotFoundException aClassNotFoundException) {
                aClassNotFoundException.printStackTrace();
                System.exit(ErrorAndExceptionHandler.ClassNotFoundException);
            }
        }
        return result;
    }

    /**
     * Attempts to make a copy of input_File in _Output_Directory or as a file
     * in it if it is a .
     * @param input_File A File which is not a Directory to be copied
     * @param outputDirectory_File A File that is the Directory to copy to.
     */
    public static void copy(
            File input_File,
            File outputDirectory_File) {
        // String osName = System.getProperty( "os.name" );
        // if ( osName.equalsIgnoreCase( "UNIX" ) ) {
        // Runtime.getRuntime().exec( "cp " + _InputFile.toString() + " " +
        // _Output_File.toString() );
        // } else {
        // }
        if (!input_File.exists()) {
            System.err.println(
                    "!input_File.exists() in " +
                    StaticIO.class.getCanonicalName() +
                    ".copy(File(" + input_File + "),File(" + outputDirectory_File + "))");
            System.exit(ErrorAndExceptionHandler.IOException);
        }
        if (!outputDirectory_File.exists()) {
            outputDirectory_File.mkdirs();
        }
        File _Output_File = new File(outputDirectory_File, input_File.getName());
        if (_Output_File.exists()) {
            System.out.println(
                    "Overwriting File " + outputDirectory_File +
                    System.getProperty("file.separator") +
                    input_File.getName() + " in " +
                    StaticIO.class.getCanonicalName() +
                    ".copy(File(" + input_File + ")," +
                    "File(" + outputDirectory_File + "))");
        } else {
            try {
                _Output_File.createNewFile();
            } catch (IOException aIOException) {
                aIOException.printStackTrace();
                System.err.println(
                        "Unable to createNewFile in Directory " +
                        outputDirectory_File + " in " +
                        StaticIO.class.getCanonicalName() +
                        ".copy(File(" + input_File + "),File(" + outputDirectory_File + "))");
                System.exit(ErrorAndExceptionHandler.IOException);
            }
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(input_File));
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(_Output_File));
            // bufferSize should be power of 2 (e.g. Math.pow(2, 12)), but nothing too big.
            int bufferSize = 2048;
            long numberOfArrayReads = input_File.length() / bufferSize;
            long numberOfSingleReads = input_File.length() - (numberOfArrayReads * bufferSize);
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
        } catch (IOException aIOException) {
            aIOException.printStackTrace();
            System.exit(ErrorAndExceptionHandler.IOException);
        }
    }

    /**
     * Returns a newly created temporary _InputFile.
     * @return 
     */
    public static File createTempFile() {
        return createTempFile(null);
    }

    /**
     * Returns a newly created temporary _InputFile.
     *
     * @param parentDirectory
     *            . Default extension to nothing.
     * @return 
     */
    public static File createTempFile(File parentDirectory) {
        return createTempFile(parentDirectory, "", "");
    }

    /**
     * Returns a newly created temporary _InputFile.
     *
     * @param parentDirectory
     *            .
     * @param prefix
     *            If not 3 characters long, this will be padded with "x"
     *            characters.
     * @param suffix
     *            If null the _InputFile is appended with ".tmp". Default extension to
     *            nothing.
     * @return 
     */
    public static File createTempFile(
            File parentDirectory,
            String prefix,
            String suffix) {
        File file = null;
        while (prefix.length() < 3) {
            prefix = prefix + "x";
        }
        boolean abstractFileCreated = false;
        do {
            try {
                file = File.createTempFile(prefix + Long.toString(System.currentTimeMillis()), suffix,
                        parentDirectory);
                abstractFileCreated = true;
            } catch (IOException aIOException) {
                aIOException.printStackTrace();
                System.exit(ErrorAndExceptionHandler.IOException);
            }
        } while (!abstractFileCreated);
        file.deleteOnExit();
        return createNewFile(parentDirectory, file.getName());
    }

    /**
     * @return a File created by:
     * <code>
     * return createNewFile(new File(System.getProperty("user.dir")));
     * </code>
     */
    public static File createNewFile() {
        // return createNewFile(
        // new File( System.getProperty( "java.io.tmpdir" ) ) );
        // return createNewFile(
        // new File( System.getProperty( "user.home" ) ) );
        return createNewFile(
                new File(System.getProperty("user.dir")));
    }

    /**
     * @return a File created by:
     * <code>
     * return createNewFile(
     *         parentDirectory,
     *         new String(""),
     *         new String(""));
     * </code>
     * @param parentDirectory
     *            Default extension prefix and suffix nothing.
     */
    public static File createNewFile(File parentDirectory) {
        return createNewFile(
                parentDirectory, "", "");
    }

    /**
     * @return a File.
     * @param parentDirectory
     * @param prefix
     * @param suffix
     */
    public static File createNewFile(
            File parentDirectory,
            String prefix,
            String suffix) {
        File file;
        do {
            file = new File(
                    parentDirectory,
                    prefix + Long.toString(System.currentTimeMillis()) + suffix);
        } while (file.exists());
        try {
            if ((prefix + suffix).equalsIgnoreCase("")) {
                file.mkdir();
            } else {
                file.createNewFile();
            }
        } catch (IOException aIOException) {
            aIOException.printStackTrace();
            System.exit(ErrorAndExceptionHandler.IOException);
        }
        return file;
    }

    /**
     * Returns a newly created File which is a _InputFile if the filename. or a
     * directory.
     *
     * @param parentDirectory
     * @param filename
     * @return 
     */
    public static File createNewFile(
            File parentDirectory,
            String filename) {
        File file = new File(parentDirectory, filename);
        try {
            if (filename.length() > 4) {
                if (filename.charAt(filename.length() - 4) != '.') {
                    file.mkdir();
                } else {
                    file.createNewFile();
                }
            } else {
                file.mkdir();
            }
        } catch (IOException aIOException) {
            aIOException.printStackTrace();
            System.exit(ErrorAndExceptionHandler.IOException);
        }
        return file;
    }

    /**
     * Sets the syntax of aStreamTokenizer as follows:
     * <ul>
     * <li>aStreamTokenizer.resetSyntax();</li>
     * <li>aStreamTokenizer.wordChars( ',', ',' );</li>
     * <li>aStreamTokenizer.wordChars( '"', '"' );</li>
     * <li>aStreamTokenizer.wordChars( '0', '0' );</li>
     * <li>aStreamTokenizer.wordChars( '1', '1' );</li>
     * <li>aStreamTokenizer.wordChars( '2', '2' );</li>
     * <li>aStreamTokenizer.wordChars( '3', '3' );</li>
     * <li>aStreamTokenizer.wordChars( '4', '4' );</li>
     * <li>aStreamTokenizer.wordChars( '5', '5' );</li>
     * <li>aStreamTokenizer.wordChars( '6', '6' );</li>
     * <li>aStreamTokenizer.wordChars( '7', '7' );</li>
     * <li>aStreamTokenizer.wordChars( '8', '8' );</li>
     * <li>aStreamTokenizer.wordChars( '9', '9' );</li>
     * <li>aStreamTokenizer.wordChars( '.', '.' );</li>
     * <li>aStreamTokenizer.wordChars( '-', '-' );</li>
     * <li>aStreamTokenizer.wordChars( '+', '+' );</li>
     * <li>aStreamTokenizer.wordChars( 'a', 'z' );</li>
     * <li>aStreamTokenizer.wordChars( 'A', 'Z' );</li>
     * <li>aStreamTokenizer.wordChars( '\t', '\t' );</li>
     * <li>aStreamTokenizer.wordChars( ' ', ' ' );</li>
     * <li>aStreamTokenizer.eolIsSignificant( true );</li>
     * </ul>
     *
     * @param aStreamTokenizer
     *            <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax1(
            StreamTokenizer aStreamTokenizer) {
        aStreamTokenizer.resetSyntax();
        // aStreamTokenizer.parseNumbers();
        aStreamTokenizer.wordChars(',', ',');
        aStreamTokenizer.wordChars('"', '"');
        // aStreamTokenizer.whitespaceChars( '"', '"' );
        // aStreamTokenizer.wordChars( '0', '9' );
        aStreamTokenizer.wordChars('0', '0');
        aStreamTokenizer.wordChars('1', '1');
        aStreamTokenizer.wordChars('2', '2');
        aStreamTokenizer.wordChars('3', '3');
        aStreamTokenizer.wordChars('4', '4');
        aStreamTokenizer.wordChars('5', '5');
        aStreamTokenizer.wordChars('6', '6');
        aStreamTokenizer.wordChars('7', '7');
        aStreamTokenizer.wordChars('8', '8');
        aStreamTokenizer.wordChars('9', '9');
        aStreamTokenizer.wordChars('.', '.');
        aStreamTokenizer.wordChars('-', '-');
        aStreamTokenizer.wordChars('+', '+');
        aStreamTokenizer.wordChars('a', 'z');
        aStreamTokenizer.wordChars('A', 'Z');
        // char[] tab = new char[1];
        // tab[0] = '\t';
        aStreamTokenizer.wordChars('\t', '\t');
        aStreamTokenizer.wordChars(' ', ' ');
        // aStreamTokenizer.ordinaryChar( ' ' );
        aStreamTokenizer.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of aStreamTokenizer as follows:
     * <ul>
     * <li>aStreamTokenizer.resetSyntax();</li>
     * <li>aStreamTokenizer.wordChars( '"', '"' );</li>
     * <li>aStreamTokenizer.wordChars( '0', '0' );</li>
     * <li>aStreamTokenizer.wordChars( '1', '1' );</li>
     * <li>aStreamTokenizer.wordChars( '2', '2' );</li>
     * <li>aStreamTokenizer.wordChars( '3', '3' );</li>
     * <li>aStreamTokenizer.wordChars( '4', '4' );</li>
     * <li>aStreamTokenizer.wordChars( '5', '5' );</li>
     * <li>aStreamTokenizer.wordChars( '6', '6' );</li>
     * <li>aStreamTokenizer.wordChars( '7', '7' );</li>
     * <li>aStreamTokenizer.wordChars( '8', '8' );</li>
     * <li>aStreamTokenizer.wordChars( '9', '9' );</li>
     * <li>aStreamTokenizer.wordChars( '.', '.' );</li>
     * <li>aStreamTokenizer.wordChars( '-', '-' );</li>
     * <li>aStreamTokenizer.wordChars( '+', '+' );</li>
     * <li>aStreamTokenizer.wordChars( 'e', 'e' );</li>
     * <li>aStreamTokenizer.wordChars( 'E', 'E' );</li>
     * <li>aStreamTokenizer.wordChars( '\t', '\t' );</li>
     * <li>aStreamTokenizer.wordChars( ' ', ' ' );</li>
     * <li>aStreamTokenizer.eolIsSignificant( true );</li>
     * </ul>
     *
     * @param aStreamTokenizer
     *            <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax2(
            StreamTokenizer aStreamTokenizer) {
        aStreamTokenizer.resetSyntax();
        aStreamTokenizer.wordChars('"', '"');
        aStreamTokenizer.wordChars('0', '0');
        aStreamTokenizer.wordChars('1', '1');
        aStreamTokenizer.wordChars('2', '2');
        aStreamTokenizer.wordChars('3', '3');
        aStreamTokenizer.wordChars('4', '4');
        aStreamTokenizer.wordChars('5', '5');
        aStreamTokenizer.wordChars('6', '6');
        aStreamTokenizer.wordChars('7', '7');
        aStreamTokenizer.wordChars('8', '8');
        aStreamTokenizer.wordChars('9', '9');
        aStreamTokenizer.wordChars('.', '.');
        aStreamTokenizer.wordChars('-', '-');
        aStreamTokenizer.wordChars('+', '+');
        aStreamTokenizer.wordChars('e', 'e');
        aStreamTokenizer.wordChars('E', 'E');
        aStreamTokenizer.wordChars('\t', '\t');
        aStreamTokenizer.wordChars(' ', ' ');
        aStreamTokenizer.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of aStreamTokenizer as follows:
     * <ul>
     * <li>aStreamTokenizer.resetSyntax();</li>
     * <li>aStreamTokenizer.wordChars( ',', ',' );</li>
     * <li>aStreamTokenizer.wordChars( '"', '"' );</li>
     * <li>aStreamTokenizer.wordChars( '0', '0' );</li>
     * <li>aStreamTokenizer.wordChars( '1', '1' );</li>
     * <li>aStreamTokenizer.wordChars( '2', '2' );</li>
     * <li>aStreamTokenizer.wordChars( '3', '3' );</li>
     * <li>aStreamTokenizer.wordChars( '4', '4' );</li>
     * <li>aStreamTokenizer.wordChars( '5', '5' );</li>
     * <li>aStreamTokenizer.wordChars( '6', '6' );</li>
     * <li>aStreamTokenizer.wordChars( '7', '7' );</li>
     * <li>aStreamTokenizer.wordChars( '8', '8' );</li>
     * <li>aStreamTokenizer.wordChars( '9', '9' );</li>
     * <li>aStreamTokenizer.wordChars( '.', '.' );</li>
     * <li>aStreamTokenizer.wordChars( '-', '-' );</li>
     * <li>aStreamTokenizer.wordChars( '+', '+' );</li>
     * <li>aStreamTokenizer.wordChars( 'a', 'z' );</li>
     * <li>aStreamTokenizer.wordChars( 'A', 'Z' );</li>
     * <li>aStreamTokenizer.wordChars( '\t', '\t' );</li>
     * <li>aStreamTokenizer.wordChars( ' ', ' ' );</li>
     * <li>aStreamTokenizer.wordChars( '_', '_' );</li>
     * <li>aStreamTokenizer.eolIsSignificant( true );</li>
     * </ul>
     *
     * @param aStreamTokenizer
     *            <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax3(
            StreamTokenizer aStreamTokenizer) {
        aStreamTokenizer.resetSyntax();
        // aStreamTokenizer.parseNumbers();
        aStreamTokenizer.wordChars(',', ',');
        aStreamTokenizer.wordChars('"', '"');
        // aStreamTokenizer.whitespaceChars( '"', '"' );
        // aStreamTokenizer.wordChars( '0', '9' );
        aStreamTokenizer.wordChars('0', '0');
        aStreamTokenizer.wordChars('1', '1');
        aStreamTokenizer.wordChars('2', '2');
        aStreamTokenizer.wordChars('3', '3');
        aStreamTokenizer.wordChars('4', '4');
        aStreamTokenizer.wordChars('5', '5');
        aStreamTokenizer.wordChars('6', '6');
        aStreamTokenizer.wordChars('7', '7');
        aStreamTokenizer.wordChars('8', '8');
        aStreamTokenizer.wordChars('9', '9');
        aStreamTokenizer.wordChars('.', '.');
        aStreamTokenizer.wordChars('-', '-');
        aStreamTokenizer.wordChars('+', '+');
        aStreamTokenizer.wordChars('a', 'z');
        aStreamTokenizer.wordChars('A', 'Z');
        // char[] tab = new char[1];
        // tab[0] = '\t';
        aStreamTokenizer.wordChars('\t', '\t');
        aStreamTokenizer.wordChars(' ', ' ');
        aStreamTokenizer.wordChars('_', '_');
        // aStreamTokenizer.ordinaryChar( ' ' );
        aStreamTokenizer.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of aStreamTokenizer as follows:
     * <ul>
     * <li>aStreamTokenizer.resetSyntax();</li>
     * <li>aStreamTokenizer.wordChars( ',', ',' );</li>
     * <li>aStreamTokenizer.wordChars( '"', '"' );</li>
     * <li>aStreamTokenizer.wordChars( '0', '0' );</li>
     * <li>aStreamTokenizer.wordChars( '1', '1' );</li>
     * <li>aStreamTokenizer.wordChars( '2', '2' );</li>
     * <li>aStreamTokenizer.wordChars( '3', '3' );</li>
     * <li>aStreamTokenizer.wordChars( '4', '4' );</li>
     * <li>aStreamTokenizer.wordChars( '5', '5' );</li>
     * <li>aStreamTokenizer.wordChars( '6', '6' );</li>
     * <li>aStreamTokenizer.wordChars( '7', '7' );</li>
     * <li>aStreamTokenizer.wordChars( '8', '8' );</li>
     * <li>aStreamTokenizer.wordChars( '9', '9' );</li>
     * <li>aStreamTokenizer.wordChars( '.', '.' );</li>
     * <li>aStreamTokenizer.wordChars( '-', '-' );</li>
     * <li>aStreamTokenizer.wordChars( '+', '+' );</li>
     * <li>aStreamTokenizer.wordChars( 'a', 'z' );</li>
     * <li>aStreamTokenizer.wordChars( 'A', 'Z' );</li>
     * <li>aStreamTokenizer.eolIsSignificant( true );</li>
     * </ul>
     *
     * @param aStreamTokenizer
     *            <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax4(
            StreamTokenizer aStreamTokenizer) {
        aStreamTokenizer.resetSyntax();
        aStreamTokenizer.wordChars(',', ',');
        aStreamTokenizer.wordChars('"', '"');
        aStreamTokenizer.wordChars('0', '0');
        aStreamTokenizer.wordChars('1', '1');
        aStreamTokenizer.wordChars('2', '2');
        aStreamTokenizer.wordChars('3', '3');
        aStreamTokenizer.wordChars('4', '4');
        aStreamTokenizer.wordChars('5', '5');
        aStreamTokenizer.wordChars('6', '6');
        aStreamTokenizer.wordChars('7', '7');
        aStreamTokenizer.wordChars('8', '8');
        aStreamTokenizer.wordChars('9', '9');
        aStreamTokenizer.wordChars('.', '.');
        aStreamTokenizer.wordChars('-', '-');
        aStreamTokenizer.wordChars('+', '+');
        aStreamTokenizer.wordChars('a', 'z');
        aStreamTokenizer.wordChars('A', 'Z');
        aStreamTokenizer.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of aStreamTokenizer as follows:
     * <ul>
     * <li>aStreamTokenizer.resetSyntax();</li>
     * <li>aStreamTokenizer.wordChars( ',', ',' );</li>
     * <li>aStreamTokenizer.wordChars( '"', '"' );</li>
     * <li>aStreamTokenizer.wordChars( '0', '0' );</li>
     * <li>aStreamTokenizer.wordChars( '1', '1' );</li>
     * <li>aStreamTokenizer.wordChars( '2', '2' );</li>
     * <li>aStreamTokenizer.wordChars( '3', '3' );</li>
     * <li>aStreamTokenizer.wordChars( '4', '4' );</li>
     * <li>aStreamTokenizer.wordChars( '5', '5' );</li>
     * <li>aStreamTokenizer.wordChars( '6', '6' );</li>
     * <li>aStreamTokenizer.wordChars( '7', '7' );</li>
     * <li>aStreamTokenizer.wordChars( '8', '8' );</li>
     * <li>aStreamTokenizer.wordChars( '9', '9' );</li>
     * <li>aStreamTokenizer.wordChars( '.', '.' );</li>
     * <li>aStreamTokenizer.wordChars( '-', '-' );</li>
     * <li>aStreamTokenizer.wordChars( '+', '+' );</li>
     * <li>aStreamTokenizer.wordChars( 'a', 'z' );</li>
     * <li>aStreamTokenizer.wordChars( 'A', 'Z' );</li>
     * <li>aStreamTokenizer.wordChars( '\t', '\t' );</li>
     * <li>aStreamTokenizer.wordChars( ' ', ' ' );</li>
     * <li>aStreamTokenizer.wordChars( ':', ':' );</li>
     * <li>aStreamTokenizer.wordChars( '/', '/' );</li>
     * <li>aStreamTokenizer.wordChars( '_', '_' );</li>
     * </ul>
     *
     * @param aStreamTokenizer
     *            <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax5(
            StreamTokenizer aStreamTokenizer) {
        aStreamTokenizer.resetSyntax();
        // aStreamTokenizer.parseNumbers();
        aStreamTokenizer.wordChars(',', ',');
        aStreamTokenizer.wordChars('"', '"');
        // aStreamTokenizer.whitespaceChars( '"', '"' );
        // aStreamTokenizer.wordChars( '0', '9' );
        aStreamTokenizer.wordChars('0', '0');
        aStreamTokenizer.wordChars('1', '1');
        aStreamTokenizer.wordChars('2', '2');
        aStreamTokenizer.wordChars('3', '3');
        aStreamTokenizer.wordChars('4', '4');
        aStreamTokenizer.wordChars('5', '5');
        aStreamTokenizer.wordChars('6', '6');
        aStreamTokenizer.wordChars('7', '7');
        aStreamTokenizer.wordChars('8', '8');
        aStreamTokenizer.wordChars('9', '9');
        aStreamTokenizer.wordChars('.', '.');
        aStreamTokenizer.wordChars('-', '-');
        aStreamTokenizer.wordChars('+', '+');
        aStreamTokenizer.wordChars('a', 'z');
        aStreamTokenizer.wordChars('A', 'Z');
        // char[] tab = new char[1];
        // tab[0] = '\t';
        aStreamTokenizer.wordChars('\t', '\t');
        aStreamTokenizer.wordChars(' ', ' ');
        aStreamTokenizer.wordChars(':', ':');
        aStreamTokenizer.wordChars('/', '/');
        aStreamTokenizer.wordChars('_', '_');
        // aStreamTokenizer.ordinaryChar( ' ' );
        aStreamTokenizer.eolIsSignificant(true);
    }
}
