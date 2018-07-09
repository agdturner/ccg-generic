/**
 * Copyright 2012 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.visualisation.charts;

import java.awt.Rectangle;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.Future;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import uk.ac.leeds.ccg.andyt.generic.utilities.Generic_Execution;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Canvas;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Printable;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * This class acts holds a reference to a lightweight component for rendering.
 * In part it acts as an interface and is extended by other classes for
 * generating specific data plots.
 */
public class Generic_Plot extends Generic_AbstractPlot {

    public Generic_Canvas Canvas;
    public Future future;

    public Generic_Plot(){}
    
    public Generic_Plot(int ID) {
        super(ID);
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            File file = getFile();
            String format = getFormat();
            Canvas = new Generic_Canvas();
            Canvas.Plot = this;
            Canvas._Rectangle = new Rectangle(0, 0, getDataWidth(), getDataHeight());

//        Graphics2D g2 = (Graphics2D) Canvas.getGraphics();
//            setG2(g2);
//            //_Generic_Canvas.paint(g2);
//            Dimension dim = draw();
//            Canvas.setDimension(dim);
            PrinterJob pj = PrinterJob.getPrinterJob();
            Generic_Printable printable = new Generic_Printable(Canvas);
            pj.setPrintable(printable);

            String psMimeType = "application/postscript";

            FileOutputStream fos = null;
//        PrintService[] printServices = PrinterJob.lookupPrintServices();
            StreamPrintService streamPrintService = null;
            StreamPrintServiceFactory[] spsf;
            spsf = PrinterJob.lookupStreamPrintServices(psMimeType);
            File dir = file.getParentFile();
            dir.mkdirs();
            File psFile = new File(dir, file.getName() + ".ps");
            System.out.println("psFile " + psFile.toString());
            if (spsf.length > 0) {
                try {
                    psFile.createNewFile();
                    fos = new FileOutputStream(psFile);
                    streamPrintService = spsf[0].getPrintService(fos);
                    // streamPrintService can now be set as the service on a PrinterJob 
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace(System.err);
                }
            }

            try {
//            PrintRequestAttributeSet pras;
//            PrintService printService = printerJob.getPrintService();
//            PrintServiceAttributeSet psas = printService.getAttributes();
//            System.out.println(psas.toString());

//            printerJob.print();
//            System.out.println("pj.getJobName()" + printerJob.getJobName());
//            System.out.println("pj.isCancelled()" + printerJob.isCancelled());
//            printerJob.setPrintService(printServices[0]);
                pj.setPrintService(streamPrintService);
                PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
                printRequestAttributeSet.add(new Copies(1));
                pj.print(printRequestAttributeSet);
                setBufferedImage(Canvas.getBufferedImage());
//            Rectangle r = Canvas.getBounds();

//            Graphics2D g2 = (Graphics2D) Canvas.getGraphics();
//            setG2(g2);
//            //_Generic_Canvas.paint(g2);
//            Dimension dim = draw();
//            Canvas.setDimension(dim);
                //_Generic_Canvas.setDimension(Canvas.paintAndGetNewDimensions(g2));
                //_Generic_Canvas._Rectangle = new Rectangle(dim);
                //_Generic_Canvas.setSize(dataWidth, dataHeight);
                // Canvas.paint(g2);
                long arbitraryTimeInMillisecondsToWaitForRendering = 10000;
                future = Generic_Visualisation.saveImage(executorService,
                        this,
                        Canvas.getBufferedImage(),
                        arbitraryTimeInMillisecondsToWaitForRendering,
                        format,
                        file);

//            fileOutputStream.close();
//            printerJob.cancel();
//            psFile.delete();
////            System.out.println("pj.getJobName() " + printerJob.getJobName());
////            System.out.println("pj.isCancelled() " + printerJob.isCancelled());       
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//            e.printStackTrace(System.err);
            } catch (PrinterException e) {
                e.printStackTrace(System.err);
            } finally {
                //printerJob.cancel();
                try {
                    fos.close();
                    pj.cancel();
                    psFile.delete();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        } catch (OutOfMemoryError e) {
            long time;
            //time = 60000L; // 1 minute
            //time = 120000L;// 2 minutes
            time = 240000L;  // 4 minutes
            System.out.println("OutOfMemoryError1, waiting " + time / 1000 + " secs "
                    + "before trying Generic_Plot.run again...");
            Generic_Execution.waitSychronized(this, time); // wait a time
            System.out.println("...on we go.");
            run();
        }
    }

    /**
     * Implementations are expected to override this.
     *
     * @param data
     */
//    @Override
//    public void initialiseParameters(Object[] data) {
//    }
    @Override
    public void initialiseParameters(Object[] data) {
        setMaxX(new BigDecimal(((BigDecimal) data[1]).toString()));
        setMinX(new BigDecimal(((BigDecimal) data[2]).toString()));
        setMaxY(new BigDecimal(((BigDecimal) data[3]).toString()));
        setMinY(new BigDecimal(((BigDecimal) data[4]).toString()));
        setCellHeight();
        setCellWidth();
        setOriginRow();
        setOriginCol();
    }

    /**
     * Implementations are expected to override this.
     *
     * @return
     */
    @Override
    public Object[] getDefaultData() {
        return null;
    }

    /**
     * Implementations are expected to override this.
     */
    @Override
    public void drawData() {
    }

    /**
     * Implementations are expected to override this.
     */
    @Override
    public void setOriginCol() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Implementations are expected to override this.
     *
     * @param textHeight
     * @param scaleTickLength
     * @param scaleTickAndTextSeparation
     * @param seperationDistanceOfAxisAndData
     * @param partTitleGap
     * @return
     */
    @Override
    public int[] drawXAxis(
            int textHeight,
            int scaleTickLength,
            int scaleTickAndTextSeparation,
            int partTitleGap,
            int seperationDistanceOfAxisAndData) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    /**
     * Implementations are expected to override this.
     *
     * @param interval
     * @param textHeight
     * @param startOfEndInterval
     * @param scaleTickLength
     * @param scaleTickAndTextSeparation
     * @param partTitleGap
     * @param seperationDistanceOfAxisAndData
     * @return
     */
    @Override
    public int[] drawYAxis(
            int interval,
            int textHeight,
            int startOfEndInterval,
            int scaleTickLength,
            int scaleTickAndTextSeparation,
            int partTitleGap,
            int seperationDistanceOfAxisAndData) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }
}
