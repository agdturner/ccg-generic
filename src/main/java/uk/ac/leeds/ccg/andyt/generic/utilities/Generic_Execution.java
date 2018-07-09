/**
 * Copyright (C) 2010 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.utilities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * Generic utility class for execution handling.
 */
public class Generic_Execution {

    /**
     * Uses default delays
     *
     * @param executorService
     * @param futures
     * @param obj
     */
    public static void shutdownExecutorService(ExecutorService executorService,
            HashSet<Future> futures, Object obj) {
        long delay = 1000;
        long maxWait = 10;
        shutdownExecutorService(executorService, futures, obj, delay, maxWait);
    }

    /**
     * @param executorService
     * @param futures
     * @param obj
     * @param delay Number of milliseconds for loop to wait before testing if a
     * Future is returned.
     * @param maxWait Number of minutes to block shutdown after all futures
     * returned
     */
    public static void shutdownExecutorService(ExecutorService executorService,
            HashSet<Future> futures, Object obj, long delay, long maxWait) {
        // What is still left to do from futures?
        Iterator<Future> ite = futures.iterator();
        String m;
        m = "There are " + futures.size() + " jobs to check.";
        System.out.println(m);
        int doneJobsCounter = 0;
        int notDoneJobsCounter = 0;
        while (ite.hasNext()) {
            Future f = ite.next();
            if (f.isDone()) {
                doneJobsCounter++;
            } else {
                notDoneJobsCounter++;
            }
        }
        m = "There are " + doneJobsCounter + " jobs done.";
        System.out.println(m);
        m = "There are " + notDoneJobsCounter + " jobs not done.";
        System.out.println(m);
        ite = futures.iterator();
        while (ite.hasNext()) {
            Future future = ite.next();
            long counter = 0;
            while (!future.isDone()) {
                m = "Job not done waiting " + delay + " milliseconds having "
                        + "already waited " + counter + " milliseconds";
                System.out.println(m);
                counter += delay;
                //try {
                Generic_Execution.waitSychronized(obj, delay);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
            }
        }
        m = "Jobs done.";
        System.out.println(m);
        executorService.shutdown();
        try {
            boolean t;
            t = executorService.awaitTermination(maxWait, TimeUnit.MINUTES);
            m = "All output terminated " + t;
            System.out.println(m);
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_Execution.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        List<Runnable> unfinishedJobs = executorService.shutdownNow();
        if (unfinishedJobs.size() > 0) {
            m = "There are " + unfinishedJobs.size() + " unfinshed jobs";
            System.out.println(m);
        } else {
            m = "All jobs finished";
            System.out.println(m);
        }
    }

    /**
     *
     * @param executorService
     * @param future
     * @param obj
     */
    public static void shutdownExecutorService(ExecutorService executorService,
            Future future, Object obj) {
        long delay = 1000;
        long maxWait = 10;
        shutdownExecutorService(executorService, future, obj, delay, maxWait);
    }

    public static void shutdownExecutorService(ExecutorService executorService,
            Future future, Object obj, long delay, long maxWait) {
        long counter = 0;
        String m;
        while (!future.isDone()) {
            m = "Job not done waiting " + delay + " milliseconds having already"
                    + " waited " + counter + " milliseconds";
            System.out.println(m);
            counter += delay;
            //try {
            Generic_Execution.waitSychronized(obj, delay);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
        m = "Job done.";
        System.out.println(m);
        executorService.shutdown();
        try {
            boolean t;
            t = executorService.awaitTermination(maxWait, TimeUnit.MINUTES);
            m = "All output terminated " + t;
            System.out.println(m);
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_Execution.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        List<Runnable> unfinishedJobs = executorService.shutdownNow();
        if (unfinishedJobs.size() > 0) {
            m = "There were " + unfinishedJobs.size() + " unfinshed jobs";
            System.out.println(m);
        } else {
            m = "All jobs finished";
            System.out.println(m);
        }
    }

    public static void waitSychronized(Object obj, long timeInMilliseconds) {
        try {
            synchronized (obj) {
                obj.wait(timeInMilliseconds);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_Visualisation.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
}
