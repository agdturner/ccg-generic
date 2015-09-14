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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Line2D;
import java.io.File;
import java.lang.Runnable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;
import uk.ac.leeds.ccg.andyt.generic.utilities.Generic_Execution;
import uk.ac.leeds.ccg.andyt.generic.utilities.Generic_StaticCollections;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * An implementation of <code>Generic_Plot<\code>
 *
 * If you run this class it will attempt to generate an Bar Chart Visualization
 * of some default data and write it out to file as a PNG.
 */
public class Generic_LineGraph extends Abstract_Generic_LineGraph {

    public Generic_LineGraph() {
    }

    public Generic_LineGraph(
            ExecutorService executorService,
            File file,
            String format,
            String title,
            int dataWidth,
            int dataHeight,
            String xAxisLabel,
            String yAxisLabel,
            boolean drawOriginLinesOnPlot, //Ignored
            int barGap,
            int xIncrement,
            BigDecimal yMax,
            BigDecimal yPin,
            BigDecimal yIncrement,
            int numberOfYAxisTicks,
            int decimalPlacePrecisionForCalculations,
            int decimalPlacePrecisionForDisplay,
            RoundingMode aRoundingMode) {
        setyMax(yMax);
        setNumberOfYAxisTicks(numberOfYAxisTicks);
        setyPin(yPin);
        setyIncrement(yIncrement);
        init(
                executorService,
                file,
                format,
                title,
                dataWidth,
                dataHeight,
                xAxisLabel,
                yAxisLabel,
                drawOriginLinesOnPlot,
                decimalPlacePrecisionForCalculations,
                decimalPlacePrecisionForDisplay,
                aRoundingMode);
    }

    @Override
    public void drawData() {
        Object[] data;
        data = getData();
        Object[] maps;
        BigDecimal minY;
        BigDecimal maxY;
        BigDecimal minX;
        BigDecimal maxX;
        maps = (Object[]) data[0];
        minY = (BigDecimal) data[1];
        maxY = (BigDecimal) data[2];
        minX = (BigDecimal) data[3];
        maxX = (BigDecimal) data[4];
        
        Color[] colours;
        colours = getColours();
        
        for (int i = 0; i < maps.length; i ++) {
            TreeMap<BigDecimal, BigDecimal> map;
            map = (TreeMap<BigDecimal, BigDecimal>) maps[i];
            drawMap(map, colours[i]);
        }
        
        
    }
    
    public void drawMap(
            TreeMap<BigDecimal, BigDecimal> map,
            Color c) {
        int row0 = 0;
        int col0 = 0;
        setPaint(c);
        boolean first = true;
        Iterator<BigDecimal> ite;
        ite = map.keySet().iterator();
        while (ite.hasNext()) {
            BigDecimal x;
            x = ite.next();
            BigDecimal y;
            y = map.get(x);
            int row = coordinateToScreenRow(
                    y);
            int col = coordinateToScreenCol(
                    x);
            if (first) {
                row0 = row;
                col0 = col;
                first = false;
            } else {
                //setPaint(c);
                Line2D line;
                line = new Line2D.Double(
                        col0, row0,
                        col, row);
                draw(line);
                row0 = row;
                col0 = col;
            }
        }
    }

    public Color[] getColours() {
        Color[] result;
        result = new Color[4];
        result[0] = Color.BLACK;
        result[1] = Color.BLUE;
        result[2] = Color.CYAN;
        result[3] = Color.GREEN;
        return result;
    }
        
    public static void main(String[] args) {
        Generic_Visualisation.getHeadlessEnvironment();

        /*
         * Initialise title and File to write image to
         */
        String title;
        File file;
        String format = "PNG";
        if (args.length != 2) {
            System.out.println(
                    "Expected 2 args:"
                    + " args[0] title;"
                    + " args[1] File."
                    + " Recieved " + args.length + " args.");
            // Use defaults
            title = "Example Line Graph";
            System.out.println("Use default title: " + title);
            file = new File(
                    new File(System.getProperty("user.dir")),
                    title.replace(" ", "_") + "." + format);
            System.out.println("Use default File: " + file.toString());
        } else {
            title = args[0];
            file = new File(args[1]);
        }
        int dataWidth = 500;
        int dataHeight = 250;
        String xAxisLabel = "X";
        String yAxisLabel = "Y";
        boolean drawOriginLinesOnPlot = true;
        int barGap = 1;
        int xIncrement = 1;
        int numberOfYAxisTicks = 11;
        BigDecimal yMax;
        yMax = null;
        BigDecimal yPin = BigDecimal.ZERO;
        BigDecimal yIncrement = BigDecimal.ONE;
        //int yAxisStartOfEndInterval = 60;
        int decimalPlacePrecisionForCalculations = 10;
        int decimalPlacePrecisionForDisplay = 3;
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Generic_LineGraph chart = new Generic_LineGraph(
                executorService,
                file,
                format,
                title,
                dataWidth,
                dataHeight,
                xAxisLabel,
                yAxisLabel,
                drawOriginLinesOnPlot,
                barGap,
                xIncrement,
                yMax,
                yPin,
                yIncrement,
                numberOfYAxisTicks,
                decimalPlacePrecisionForCalculations,
                decimalPlacePrecisionForDisplay,
                roundingMode);
        chart.setData(chart.getDefaultData());
        chart.run();
        Future future = chart.future;
        Generic_Execution.shutdownExecutorService(
                executorService, future, chart);
    }

    @Override
    public Dimension draw() {
        drawOutline();
        drawTitle(getTitle());
        drawAxes();
        drawData();
        Dimension newDim = new Dimension(getImageWidth(), getImageHeight());
        return newDim;
    }

    public void drawAxes() {
        int yAxisExtraWidthLeft;
//        int yAxisExtraHeightTop = 0;
//        int yAxisExtraHeightBottom = 0;
        int xAxisExtraWidthLeft;
        int xAxisExtraWidthRight;
        int xAxisExtraHeightBottom;
        int scaleTickLength = getDefaultScaleTickLength();
        int scaleTickAndTextSeparation = getDefaultScaleTickAndTextSeparation();
        int partTitleGap = getDefaultPartTitleGap();
        int textHeight = getTextHeight();
        //int seperationDistanceOfAxisAndData = textHeight;
        int seperationDistanceOfAxisAndData = 2;
        // Draw Y axis
        int[] yAxisDimensions = drawYAxis(
                textHeight,
                scaleTickLength,
                scaleTickAndTextSeparation,
                partTitleGap,
                seperationDistanceOfAxisAndData);
        yAxisExtraWidthLeft = yAxisDimensions[0];

        int extraWidthLeft;
        extraWidthLeft = getExtraWidthLeft();
        int imageWidth;
        imageWidth = getImageWidth();
        int dataStartCol;
        dataStartCol = getDataStartCol();
        int dataEndCol;
        dataEndCol = getDataEndCol();
        int yAxisWidth;
        yAxisWidth = getyAxisWidth();
        if (yAxisExtraWidthLeft > extraWidthLeft) {
            int diff = yAxisExtraWidthLeft - extraWidthLeft;
            imageWidth += diff;
            dataStartCol += diff;
            dataEndCol += diff;
            extraWidthLeft = yAxisExtraWidthLeft;
            yAxisWidth += diff;
            //setExtraWidthLeft(yAxisExtraWidthLeft);
            setYAxisWidth(yAxisWidth);
        }
        //setYAxisWidth(yAxisExtraWidthLeft);
        setDataStartCol(dataStartCol);
        setDataEndCol(dataEndCol);
        // Draw X axis
        int[] xAxisDimensions = drawXAxis(
                textHeight,
                scaleTickLength,
                scaleTickAndTextSeparation,
                partTitleGap,
                seperationDistanceOfAxisAndData);
        xAxisExtraWidthLeft = xAxisDimensions[0];
        xAxisExtraWidthRight = xAxisDimensions[1];
        xAxisExtraHeightBottom = xAxisDimensions[2];
        if (xAxisExtraWidthLeft > extraWidthLeft) {
            int diff = xAxisExtraWidthLeft - extraWidthLeft;
            imageWidth += diff;
            dataStartCol += diff;
            dataEndCol += diff;
            extraWidthLeft = xAxisExtraWidthLeft;
            yAxisWidth += diff;
            setYAxisWidth(yAxisWidth);
//            setOriginCol();
        }
        setDataStartCol(dataStartCol);
        setDataEndCol(dataEndCol);
        int extraWidthRight;
        extraWidthRight = getExtraWidthRight();
        if (xAxisExtraWidthRight > extraWidthRight) {
            imageWidth += xAxisExtraWidthRight - extraWidthRight;
            extraWidthRight = xAxisExtraWidthRight;
            setExtraWidthRight(extraWidthRight);
        }
        setImageWidth(imageWidth);
        int xAxisHeight;
        xAxisHeight = getxAxisHeight();
        xAxisHeight += xAxisExtraHeightBottom;
        setxAxisHeight(xAxisHeight);
        int extraHeightBottom;
        extraHeightBottom = getExtraHeightBottom();
        int imageHeight;
        imageHeight = getImageHeight();
        if (xAxisExtraHeightBottom > extraHeightBottom) {
            int diff = xAxisExtraHeightBottom - extraHeightBottom;
            imageHeight += diff;
            setImageHeight(imageHeight);
            setExtraHeightBottom(xAxisExtraHeightBottom);
        }
    }

    @Override
    public Object[] getDefaultData() {
        Object[] result;
        result = new Object[5];
        Object[] maps;
        maps = new Object[2];
        TreeMap<BigDecimal, BigDecimal> map;
        map = new TreeMap<BigDecimal, BigDecimal>();
        map.put(new BigDecimal(0.0d), new BigDecimal(10.0d));
        map.put(new BigDecimal(6.0d), new BigDecimal(11.0d));
        map.put(new BigDecimal(12.0d), new BigDecimal(12.0d));
        map.put(new BigDecimal(18.0d), new BigDecimal(13.0d));
        map.put(new BigDecimal(24.0d), new BigDecimal(14.0d));
        map.put(new BigDecimal(27.0d), new BigDecimal(15.0d));
        map.put(new BigDecimal(30.0d), new BigDecimal(16.0d));
        map.put(new BigDecimal(33.0d), new BigDecimal(15.0d));
        map.put(new BigDecimal(36.0d), new BigDecimal(14.0d));
        map.put(new BigDecimal(39.0d), new BigDecimal(15.0d));
        map.put(new BigDecimal(42.0d), new BigDecimal(17.0d));
        map.put(new BigDecimal(45.0d), new BigDecimal(18.0d));
        map.put(new BigDecimal(48.0d), new BigDecimal(29.0d));
        map.put(new BigDecimal(49.0d), new BigDecimal(30.0d));
        map.put(new BigDecimal(50.0d), new BigDecimal(15.0d));
        map.put(new BigDecimal(51.0d), new BigDecimal(25.0d));
        map.put(new BigDecimal(52.0d), new BigDecimal(35.0d));
        map.put(new BigDecimal(53.0d), new BigDecimal(36.0d));
        map.put(new BigDecimal(54.0d), new BigDecimal(37.0d));
        maps[0] = map;
        TreeMap<BigDecimal, BigDecimal> map2;
        map2 = new TreeMap<BigDecimal, BigDecimal>();
        map2.put(new BigDecimal(0.0d), new BigDecimal(9.0d));
        map2.put(new BigDecimal(6.0d), new BigDecimal(10.0d));
        map2.put(new BigDecimal(12.0d), new BigDecimal(12.0d));
        map2.put(new BigDecimal(18.0d), new BigDecimal(14.0d));
        map2.put(new BigDecimal(24.0d), new BigDecimal(15.0d));
        map2.put(new BigDecimal(27.0d), new BigDecimal(16.0d));
        map2.put(new BigDecimal(30.0d), new BigDecimal(17.0d));
        map2.put(new BigDecimal(33.0d), new BigDecimal(18.0d));
        map2.put(new BigDecimal(36.0d), new BigDecimal(13.0d));
        map2.put(new BigDecimal(39.0d), new BigDecimal(16.0d));
        map2.put(new BigDecimal(42.0d), new BigDecimal(18.0d));
        map2.put(new BigDecimal(45.0d), new BigDecimal(19.0d));
        map2.put(new BigDecimal(48.0d), new BigDecimal(25.0d));
        map2.put(new BigDecimal(49.0d), new BigDecimal(31.0d));
        map2.put(new BigDecimal(50.0d), new BigDecimal(25.0d));
        map2.put(new BigDecimal(51.0d), new BigDecimal(25.0d));
        map2.put(new BigDecimal(52.0d), new BigDecimal(25.0d));
        map2.put(new BigDecimal(53.0d), new BigDecimal(37.0d));
        map2.put(new BigDecimal(54.0d), new BigDecimal(37.0d));
        maps[1] = map2;
        BigDecimal[] minMaxBigDecimal;
        minMaxBigDecimal = Generic_StaticCollections.getMinMaxBigDecimal(map);
        BigDecimal minY = minMaxBigDecimal[0];
        BigDecimal maxY = minMaxBigDecimal[1];
        BigDecimal minX = map.firstKey();
        BigDecimal maxX = map.lastKey();
        minMaxBigDecimal = Generic_StaticCollections.getMinMaxBigDecimal(map2);
        if (minY.compareTo(minMaxBigDecimal[0]) == 1) {
            minY = minMaxBigDecimal[0];
        }
        if (maxY.compareTo(minMaxBigDecimal[1]) == -1) {
            maxY = minMaxBigDecimal[1];
        }
        if (minX.compareTo(map2.firstKey()) == 1) {
            minX = map2.firstKey();
        }
        if (maxX.compareTo(map2.lastKey()) == -1) {
            maxX = map2.lastKey();
        }

        result[0] = maps;
        result[1] = minY;
        result[2] = maxY;
        result[3] = minX;
        result[4] = maxX;
        return result;
    }

    @Override
    public void drawTitle(String title) {
        super.drawTitle(title);
        int barHeight = Generic_BigDecimal.divideRoundIfNecessary(
                BigDecimal.valueOf(getAgeInterval()),
                getCellHeight(),
                0,
                getRoundingMode()).intValue();
        setExtraHeightTop(getExtraHeightTop() + barHeight);
    }
}