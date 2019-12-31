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

package uk.ac.leeds.ccg.generic.visualisation;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Object;
import uk.ac.leeds.ccg.generic.execution.Generic_Execution;

/**
 * A class for holding generic visualisation methods.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_Visualisation extends Generic_Object {

    public Generic_Visualisation(Generic_Environment e) {
        super(e);
    }

    /**
     * For loading a BufferedImage from a File.
     *
     * @param f File
     * @return BufferedImage
     * @throws java.io.IOException If encountered.
     */
    public BufferedImage loadImage(File f) throws IOException {
        try {
            return ImageIO.read(f);
        } catch (IIOException ex) {
            /**
             * This can happen because of too many open files. Try waiting for 2
             * seconds and then repeating...
             */
            env.log(ex.getMessage());
            Generic_Execution.waitSychronized(env, f, 2000L);
            return loadImage(f);
        }
    }

    /**
     * For saving a BufferedImage to a File.
     *
     * @param bi BufferedImage
     * @param format String
     * @param f File
     */
    public void saveImage(BufferedImage bi, String format, File f) {
        String m = this.getClass().getName() 
                + ".saveImage(BufferedImage,String,File) to " + f;
        env.logStartTag(m);
        if (bi != null) {
            try {
                ImageIO.write(bi, format, f);
            } catch (IOException ex) {
                /**
                 * This can happen because of too many open files. Try waiting
                 * for 2 seconds and then repeating...
                 */
                env.log(ex.getMessage());
                Generic_Execution.waitSychronized(env, f, 2000L);
                saveImage(bi, format, f);
            } finally {
                // Nothing needed here as IMAGEIO should deal with the stream.
            }
        }
        env.logEndTag(m);
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
    public Future saveImage(ExecutorService es, Object o, BufferedImage bi,
            long timeInMilliseconds, String format, File f) {
        if (es == null) {
            es = Executors.newSingleThreadExecutor();
        }
        ImageSaver is = new ImageSaver(this, o, bi, timeInMilliseconds, format, f);
        Future r = es.submit(is);
        return r;
    }

    public class ImageSaver implements Runnable {

        Generic_Visualisation v;
        Object o;
        BufferedImage bi;
        long timeInMilliseconds;
        String format;
        File f;

        public ImageSaver() {
        }

        public ImageSaver(Generic_Visualisation v, Object o, BufferedImage bi,
                long timeInMilliseconds, String format, File f) {
            this.v = v;
            this.o = o;
            this.bi = bi;
            this.timeInMilliseconds = timeInMilliseconds;
            this.format = format;
            this.f = f;
        }

        @Override
        public void run() {
            Generic_Execution.waitSychronized(v.env, o, timeInMilliseconds);
            v.saveImage(bi, format, f);
        }
    }

    /**
     * Sets a system property (System.setProperty("java.awt.headless", "true");)
     * and return a headless graphics environment and Toolkit of the system.
     *
     * @return Object[] r of size 2 where: r[0] = Toolkit.getDefaultToolkit();
     * r[1] = GraphicsEnvironment.getLocalGraphicsEnvironment().
     */
    public Object[] getHeadlessEnvironment() {
        Object[] r = new Object[2];
        System.setProperty("java.awt.headless", "true");
        Toolkit tk = Toolkit.getDefaultToolkit();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //logIfHeadless(ge);
        r[0] = tk;
        r[1] = ge;
        return r;
    }

    /**
     * Reports to std.out if ge is headless or not.
     *
     * @param ge GraphicsEnvironment
     */
    public void logIfHeadless(GraphicsEnvironment ge) {
        if (ge.isHeadlessInstance()) {
            env.log("GraphicsEnvironment is headless");
        } else {
            env.log("GraphicsEnvironment is not headless");
        }
    }

    /**
     *
     * @return new Font("Arial", Font.PLAIN, 12);
     */
    public static Font getDefaultFont() {
        return new Font("Arial", Font.PLAIN, 12);
    }
}
