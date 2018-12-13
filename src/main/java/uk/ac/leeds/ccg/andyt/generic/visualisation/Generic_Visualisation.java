/**
 * Copyright 2012 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.visualisation;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_ErrorAndExceptionHandler;
import uk.ac.leeds.ccg.andyt.generic.logging.Generic_Log;
import uk.ac.leeds.ccg.andyt.generic.execution.Generic_Execution;

/**
 * A class for holding generic visualisation methods.
 */
public class Generic_Visualisation {

    /**
     * For loading a BufferedImage from a File.
     *
     * @param f File
     * @return BufferedImage
     */
    public static BufferedImage loadImage(File f) {
        BufferedImage bi = null;
        try {
            try {
                bi = ImageIO.read(f);
            } catch (IIOException e) {
                //} catch (FileNotFoundException e) {
                //} catch (IOException e) {
                //} catch (Exception e) {
                // This can happen because of too many open files.
                // Try waiting for a 2 seconds and then repeating...
                try {
                    synchronized (f) {
                        f.wait(2000L);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Generic_Visualisation.class.getName()).log(Level.SEVERE, null, ex);
                }
                return loadImage(f);
            }
        } catch (IOException e) {
            Generic_Log.LOGGER.log(
                    Generic_Log.Generic_DefaultLogLevel, //Level.ALL,
                    e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        return bi;
    }

    /**
     * For saving a BufferedImage to a File.
     *
     * @param bi BufferedImage
     * @param format String
     * @param f File
     */
    public static void saveImage(BufferedImage bi, String format, File f) {
        String m;
        m = "Generic_Visualisation.saveImage(BufferedImage,String,File)";
        System.out.println("<" + m + ">");
        if (bi != null) {
            try {
                System.out.println("File " + f.toString());
                ImageIO.write(bi, format, f);
            } catch (IIOException e) {
                System.err.println("Trying to handle " + e.getLocalizedMessage());
                System.err.println("Wait for 2 seconds then trying again to saveImage.");
                //e.printStackTrace(System.err);
                // This can happen because of too many open files.
                // Try waiting for 2 seconds and then repeating...
                try {
                    synchronized (bi) {
                        bi.wait(2000L);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Generic_Visualisation.class.getName()).log(Level.SEVERE, null, ex);
                }
                saveImage(bi, format, f);
                //} catch (FileNotFoundException e) {
                //} catch (IOException e) {
            } catch (Exception e) {
                System.err.println("Trying to handle " + e.getLocalizedMessage());
                System.err.println("Wait for 2 seconds then trying again to saveImage.");
                //e.printStackTrace(System.err);
                // This can happen because of too many open files.
                // Try waiting for 2 seconds and then repeating...
                try {
                    synchronized (bi) {
                        bi.wait(2000L);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Generic_Visualisation.class.getName()).log(Level.SEVERE, null, ex);
                }
                saveImage(bi, format, f);
            } finally {
                // There is nothing to go in here as IMAGEIO deals with the stream.
            }
        }
//        } catch (IOException e) {
//            Generic_Log.LOGGER.log(
//                    Generic_Log.Generic_DefaultLogLevel, //Level.ALL,
//                    e.getMessage());
//            String methodName = "saveImage(BufferedImage,String,File)";
//            System.err.println(e.getMessage());
//            System.err.println("Generic_Visualisation." + methodName);
//            e.printStackTrace(System.err);
//            System.exit(Generic_ErrorAndExceptionHandler.IOException);
//        }
        System.out.println("</" + m + ">");
    }

    /**
     * Saves image bi to File f in format after a timeInMilliseconds delay which
     * is hopefully long enough for all the graphics to be drawn.
     *
     * @param es ExecutorService
     * @param o Object 
     * @param timeInMilliseconds long
     * @param format String
     * @param bi BufferedImage
     * @param f File
     * @return Future
     */
    public static Future saveImage(ExecutorService es, Object o,
            BufferedImage bi, long timeInMilliseconds, String format, File f) {
        if (es == null) {
            es = Executors.newSingleThreadExecutor();
        }
        Future result;
        ImageSaver is = new ImageSaver(o, bi, timeInMilliseconds, format, f);
        result = es.submit(is);
        return result;
    }

    public static class ImageSaver implements Runnable {

        Object o;
        BufferedImage bi;
        long timeInMilliseconds;
        String format;
        File f;

        public ImageSaver() {
        }

        public ImageSaver(Object o, BufferedImage bi, long timeInMilliseconds,
                String format, File f) {
            this.o = o;
            this.bi = bi;
            this.timeInMilliseconds = timeInMilliseconds;
            this.format = format;
            this.f = f;
        }

        @Override
        public void run() {
            Generic_Execution.waitSychronized(o, timeInMilliseconds);
            Generic_Visualisation.saveImage(bi, format, f);
        }
    }

    /**
     * Sets a system property (System.setProperty("java.awt.headless", "true");)
     * and return a headless graphics environment and Toolkitof the system.
     *
     * @return Object[] r of size 2 where: r[0] = Toolkit.getDefaultToolkit();
     * r[1] = GraphicsEnvironment.getLocalGraphicsEnvironment().
     */
    public static Object[] getHeadlessEnvironment() {
        Object[] result = new Object[2];
        System.setProperty("java.awt.headless", "true");
        Toolkit tk = Toolkit.getDefaultToolkit();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //print_headless_check(ge);
        result[0] = tk;
        result[1] = ge;
        return result;
    }

    /**
     * Reports to std.out if ge is headless or not.
     * 
     * @param ge GraphicsEnvironment
     */
    public static void print_headless_check(GraphicsEnvironment ge) {
        boolean headless_check = ge.isHeadlessInstance();
        System.out.println("headless_check " + headless_check);
    }

    /**
     *
     * @return new Font("Arial", Font.PLAIN, 12);
     */
    public static Font getDefaultFont() {
        return new Font("Arial", Font.PLAIN, 12);
    }
}
