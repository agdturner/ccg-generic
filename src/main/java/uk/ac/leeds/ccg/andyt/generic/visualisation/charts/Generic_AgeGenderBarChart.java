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
import java.io.File;
import java.lang.Runnable;
import java.math.BigDecimal;
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
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * An implementation of
 * <code>Generic_AgeGenderBarChart<\code>
 *
 * If you run this class it will attempt to generate an Age by Gender
 * Population Bar Chart Visualization of some default data and display it on
 * screen.
 */
public class Generic_AgeGenderBarChart extends Abstract_Generic_AgeGenderPlot {

    public Generic_AgeGenderBarChart() {
    }

    public Generic_AgeGenderBarChart(
            ExecutorService executorService,
            File file,
            String format,
            String title,
            int dataWidth,
            int dataHeight,
            String xAxisLabel,
            String yAxisLabel,
            boolean drawOriginLinesOnPlot,
            int ageInterval,
            int startAgeOfEndYearInterval,
            int decimalPlacePrecisionForCalculations,
            int decimalPlacePrecisionForDisplay,
            RoundingMode aRoundingMode) {
        init(
                executorService,
                file,
                format,
                title,
                dataWidth,
                dataHeight,
                xAxisLabel,
                xAxisLabel,
                drawOriginLinesOnPlot,
                ageInterval,
                startAgeOfEndYearInterval,
                decimalPlacePrecisionForCalculations,
                decimalPlacePrecisionForDisplay,
                aRoundingMode);
    }

    @Override
    public void drawData() {
        drawBarChart(getData(), getAgeInterval());
    }

    public void drawBarChart(
            Object[] data,
            int ageInterval) {
        setPaint(Color.DARK_GRAY);
        RoundingMode roundingMode = getRoundingMode();
        BigDecimal cellWidth = getCellWidth();
        int originCol = getOriginCol();
        TreeMap<Long, BigDecimal> femaleAgeInYearsPopulationCount_TreeMap = (TreeMap<Long, BigDecimal>) data[0];
        TreeMap<Long, BigDecimal> maleAgeInYearsPopulationCount_TreeMap = (TreeMap<Long, BigDecimal>) data[1];
        Iterator<Map.Entry<Long, BigDecimal>> ite;
        Map.Entry<Long, BigDecimal> entry;
        Long age;
        BigDecimal population;
        int barGap = 4;
//        int barGapDiv2 = barGap / 2;
//        int barHeight = Generic_BigDecimal.divideRoundIfNecessary(
//                BigDecimal.valueOf(ageInterval),
//                getCellHeight(),
//                0,
//                roundingMode).intValueExact() - barGap;
        int barHeight;
        BigDecimal cellHeight = getCellHeight();
        if (cellHeight.compareTo(BigDecimal.ZERO) == 0) {
            barHeight = 1;
        } else {
            barHeight = Generic_BigDecimal.divideRoundIfNecessary(
                    BigDecimal.valueOf(ageInterval),
                    getCellHeight(),
                    0,
                    roundingMode).intValue() - barGap;
        }
        if (barHeight < 1) {
            barHeight = 1;
        }
        // Draw Female bars
        ite = femaleAgeInYearsPopulationCount_TreeMap.entrySet().iterator();
        while (ite.hasNext()) {
            entry = ite.next();
            age = entry.getKey();
            population = entry.getValue();
            int barWidth = Generic_BigDecimal.divideRoundIfNecessary(
                    population,
                    cellWidth,
                    0,
                    //roundingMode).intValueExact();
                    roundingMode).intValue();
//            int barTopRow = coordinateToScreenRow(
//                    BigDecimal.valueOf(age + ageInterval))
//                    + barGapDiv2;
            int barTopRow = coordinateToScreenRow(
                    BigDecimal.valueOf(age + ageInterval))
                    + barGap;
            setPaint(Color.DARK_GRAY);
//            Rectangle2D r2 = new Rectangle2D.Double(
//                    originCol,
//                    barTopRow,
//                    barWidth,
//                    barHeight);
            fillRect(
                    originCol,
                    barTopRow,
                    barWidth,
                    barHeight);
//            setPaint(Color.BLACK);
//            draw(r2);
        }
        // Draw Male bars
        ite = maleAgeInYearsPopulationCount_TreeMap.entrySet().iterator();
        while (ite.hasNext()) {
            entry = ite.next();
            age = entry.getKey();
            population = entry.getValue();
            int barWidth = Generic_BigDecimal.divideRoundIfNecessary(
                    population,
                    cellWidth,
                    0,
                    roundingMode).intValueExact();
//            int barTopRow = coordinateToScreenRow(
//                    BigDecimal.valueOf(age + ageInterval))
//                    + barGapDiv2;
            int barTopRow = coordinateToScreenRow(
                    BigDecimal.valueOf(age + ageInterval))
                    + barGap;
            setPaint(Color.LIGHT_GRAY);
//            Rectangle2D r2 = new Rectangle2D.Double(
//                    originCol - barWidth,
//                    barTopRow,
//                    barWidth,
//                    barHeight);
            fillRect(
                    originCol - barWidth,
                    barTopRow,
                    barWidth,
                    barHeight);
//            setPaint(Color.BLACK);
//            draw(r2);
        }
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
            title = "Age Gender Population Bar Chart";
            System.out.println("Use default title: " + title);
            file = new File(
                    new File(System.getProperty("user.dir")),
                    title.replace(" ", "_") + "." + format);
            System.out.println("Use default File: " + file.toString());
        } else {
            title = args[0];
            file = new File(args[1]);
        }
        int dataWidth = 250;
        int dataHeight = 500;
        String xAxisLabel = "Population";
        String yAxisLabel = "Age";
        boolean drawOriginLinesOnPlot = true;
        int ageInterval = 5;
        int startAgeOfEndYearInterval = 60;
        int decimalPlacePrecisionForCalculations = 10;
        int decimalPlacePrecisionForDisplay = 3;
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Generic_AgeGenderBarChart chart = new Generic_AgeGenderBarChart(
                executorService,
                file,
                format,
                title,
                dataWidth,
                dataHeight,
                xAxisLabel,
                yAxisLabel,
                drawOriginLinesOnPlot,
                ageInterval,
                startAgeOfEndYearInterval,
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
    public Object[] getDefaultData() {
        int femalePopAge0 = 10000;
        int malePopAge0 = 9900;
        int ageInterval = 5;
        int startAgeOfEndYearInterval = 60;
        return getDefaultData(
                femalePopAge0,
                malePopAge0,
                ageInterval,
                startAgeOfEndYearInterval);
    }

    /**
     *
     * @param femalePopAge0
     * @param malePopAge0
     * @param ageInterval
     * @param startAgeOfEndYearInterval
     * @return Object[] result: result[0] =
     * femaleAgeInYearsPopulationCount_TreeMap; result[1] =
     * maleAgeInYearsPopulationCount_TreeMap; result[2] = maxPop;
     */
    public static Object[] getDefaultData(
            int femalePopAge0,
            int malePopAge0,
            int ageInterval,
            int startAgeOfEndYearInterval) {
        Object[] result = new Object[3];
        Object[] data = getDefaultData(femalePopAge0, malePopAge0);
        TreeMap<Integer, BigDecimal> femaleAgeInYearsPopulationCount_TreeMap = new TreeMap<Integer, BigDecimal>();
        TreeMap<Integer, BigDecimal> maleAgeInYearsPopulationCount_TreeMap = new TreeMap<Integer, BigDecimal>();
        TreeMap<Integer, BigDecimal> singleYearFemaleAgeInYearsPopulationCount_TreeMap = (TreeMap<Integer, BigDecimal>) data[0];
        TreeMap<Integer, BigDecimal> singleYearMaleAgeInYearsPopulationCount_TreeMap = (TreeMap<Integer, BigDecimal>) data[1];
        Iterator<Integer> ite;
        Integer age;
        BigDecimal pop;
        BigDecimal maxPop = BigDecimal.ZERO;
        int ageGroup;
        BigDecimal popGroup;
        ageGroup = 0;
        popGroup = BigDecimal.ZERO;
        boolean reportedPenultimateGroup;
        reportedPenultimateGroup = false;
        ite = singleYearFemaleAgeInYearsPopulationCount_TreeMap.keySet().iterator();
        while (ite.hasNext()) {
            age = ite.next();
            pop = singleYearFemaleAgeInYearsPopulationCount_TreeMap.get(age);
            if (age.intValue() > startAgeOfEndYearInterval) {
                if (!reportedPenultimateGroup) {
                    femaleAgeInYearsPopulationCount_TreeMap.put(ageGroup, popGroup);
                    maxPop = maxPop.max(popGroup);
                    //ageGroup = ageGroup + ageInterval;
                    popGroup = new BigDecimal(pop.toString());
                    reportedPenultimateGroup = true;
                }
                ageGroup = startAgeOfEndYearInterval;
                popGroup = popGroup.add(pop);
                maxPop = maxPop.max(popGroup);
                femaleAgeInYearsPopulationCount_TreeMap.put(ageGroup, popGroup);
            } else {
                if (age > ageGroup + ageInterval) {
                    femaleAgeInYearsPopulationCount_TreeMap.put(ageGroup, popGroup);
                    maxPop = maxPop.max(popGroup);
                    ageGroup = ageGroup + ageInterval;
                    popGroup = new BigDecimal(pop.toString());
                } else {
                    popGroup = popGroup.add(pop);
                }
            }
        }
        ageGroup = 0;
        popGroup = BigDecimal.ZERO;
        reportedPenultimateGroup = false;
        ite = singleYearMaleAgeInYearsPopulationCount_TreeMap.keySet().iterator();
        while (ite.hasNext()) {
            age = ite.next();
            pop = singleYearMaleAgeInYearsPopulationCount_TreeMap.get(age);
            if (age.intValue() > startAgeOfEndYearInterval) {
                if (!reportedPenultimateGroup) {
                    maleAgeInYearsPopulationCount_TreeMap.put(ageGroup, popGroup);
                    maxPop = maxPop.max(popGroup);
                    //ageGroup = ageGroup + ageInterval;
                    popGroup = new BigDecimal(pop.toString());
                    reportedPenultimateGroup = true;
                }
                ageGroup = startAgeOfEndYearInterval;
                popGroup = popGroup.add(pop);
                maxPop = maxPop.max(popGroup);
                maleAgeInYearsPopulationCount_TreeMap.put(ageGroup, popGroup);
            } else {
                if (age > ageGroup + ageInterval) {
                    maleAgeInYearsPopulationCount_TreeMap.put(ageGroup, popGroup);
                    maxPop = maxPop.max(popGroup);
                    ageGroup = ageGroup + ageInterval;
                    popGroup = new BigDecimal(pop.toString());
                } else {
                    popGroup = popGroup.add(pop);
                }
            }
        }
        result[0] = femaleAgeInYearsPopulationCount_TreeMap;
        result[1] = maleAgeInYearsPopulationCount_TreeMap;
        result[2] = maxPop;
        return result;
    }

    private static Object[] getDefaultData(
            int femalePopAge0,
            int malePopAge0) {
        Object[] result = new Object[2];
        TreeMap<Integer, BigDecimal> femaleAgeInYearsPopulationCount_TreeMap = new TreeMap<Integer, BigDecimal>();
        TreeMap<Integer, BigDecimal> maleAgeInYearsPopulationCount_TreeMap = new TreeMap<Integer, BigDecimal>();
        BigDecimal population_BigDecimal;
        BigDecimal change_BigDecimal;
        int age;
        population_BigDecimal = new BigDecimal("" + femalePopAge0);
        change_BigDecimal = new BigDecimal("0.94");
        for (age = 0; age < 5; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.95");
        for (age = 5; age < 10; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.96");
        for (age = 10; age < 15; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.97");
        for (age = 15; age < 20; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.99");
        for (age = 20; age < 60; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.97");
        for (age = 60; age < 80; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.75");
        for (age = 80; age < 100; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        population_BigDecimal = new BigDecimal("" + malePopAge0);
        change_BigDecimal = new BigDecimal("0.93");
        for (age = 0; age < 5; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.94");
        for (age = 5; age < 10; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.95");
        for (age = 10; age < 15; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.96");
        for (age = 15; age < 20; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.98");
        for (age = 20; age < 60; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.7");
        for (age = 60; age < 70; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.5");
        for (age = 70; age < 100; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        //Integer maxAge = 99;
        //BigDecimal maxCount = new BigDecimal("" + Math.max(femalePopAge0, malePopAge0));
        result[0] = femaleAgeInYearsPopulationCount_TreeMap;
        result[1] = maleAgeInYearsPopulationCount_TreeMap;
        //result[2] = maxAge;
        //result[3] = maxCount;
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
