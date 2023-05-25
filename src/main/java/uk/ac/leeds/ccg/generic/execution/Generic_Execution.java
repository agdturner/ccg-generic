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

package uk.ac.leeds.ccg.generic.execution;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Object;

/**
 * Generic utility class for execution handling.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_Execution extends Generic_Object {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance.
     * @param e The Generic_Environment.
     */
    public Generic_Execution(Generic_Environment e){
        super(e);        
    }
    
    /**
     * Default:
     * <ul>
     * <li>delay = 1000</li>
     * <li>maxWait 10</li>
     * </ul>
     * {@link #shutdownExecutorService(java.util.concurrent.ExecutorService,
     * java.util.concurrent.Future, java.lang.Object, long, long)}.
     *
     * @param es ExecutorService.
     * @param futures Set of Futures.
     * @param o Object.
     *
     * {
     * @see #shutdownExecutorService(java.util.concurrent.ExecutorService,
     * java.util.concurrent.Future, java.lang.Object, long, long)}
     */
    public void shutdownExecutorService(ExecutorService es,
            HashSet<Future<?>> futures, Object o) {
        long delay = 1000;
        long maxWait = 10;
        shutdownExecutorService(es, futures, o, delay, maxWait);
    }

    /**
     * @param es ExecutorService
     * @param futures Set of Futures
     * @param o Object
     * @param delay Number of milliseconds for loop to wait before testing if a
     * Future is returned.
     * @param maxWait Number of minutes to block shutdown after all futures
     * returned
     */
    public void shutdownExecutorService(ExecutorService es,
            HashSet<Future<?>> futures, Object o, long delay, long maxWait) {
        // What is still left to do from futures?
        Iterator<Future<?>> ite = futures.iterator();
        env.log("There are " + futures.size() + " jobs to check.");
        int doneJobsCounter = 0;
        int notDoneJobsCounter = 0;
        while (ite.hasNext()) {
            Future<?> f = ite.next();
            if (f.isDone()) {
                doneJobsCounter++;
            } else {
                notDoneJobsCounter++;
            }
        }
        env.log("There are " + doneJobsCounter + " jobs done.");
        env.log("There are " + notDoneJobsCounter + " jobs not done.");
        ite = futures.iterator();
        long counter = 0;
        while (ite.hasNext()) {
            Future<?> future = ite.next();
            while (!future.isDone()) {
                counter = checkFuture(o, counter, delay);
            }
        }
        env.log("Jobs done.");
        shutdownExecutorService(es, maxWait);
    }

    /**
     * Default delay 1000 and maxWait 10.
     *
     * @param es ExecutorService
     * @param future Future
     * @param o Object
     */
    public void shutdownExecutorService(ExecutorService es,
            Future<?> future, Object o) {
        long delay = 1000;
        long maxWait = 10;
        shutdownExecutorService(es, future, o, delay, maxWait);
    }

    /**
     *
     * @param o An Object used for synchronise waiting.
     * @param counter The counter to be updated and returned.
     * @param delay The time to wait for the job to complete.
     * @return An updated counter adding the time waited.
     */
    private long checkFuture(Object o, long counter, long delay) {
        env.log("Job not done waiting " + delay + " milliseconds having "
                + "already waited " + counter + " milliseconds.");
        counter += delay;
        waitSychronized(env, o, delay);
        return counter;
    }

    /**
     * @param es The ExecutorService to shutdown.
     * @param maxWait The maximum length of time to wait for shutdown.
     */
    private void shutdownExecutorService(ExecutorService es, long maxWait) {
        es.shutdown();
        try {
            boolean t = es.awaitTermination(maxWait, TimeUnit.MINUTES);
            env.log("All output terminated " + t + ".");
        } catch (InterruptedException ex) {
            env.log(ex.getMessage());
        }
        List<Runnable> unfinishedJobs = es.shutdownNow();
        if (unfinishedJobs.size() > 0) {
            env.log("There are " + unfinishedJobs.size() + " unfinshed jobs.");
        } else {
            env.log("All jobs finished.");
        }
    }

    /**
     * @param es ExecutorService
     * @param future Future
     * @param o Object
     * @param delay Number of milliseconds for loop to wait before testing if a
     * Future is returned.
     * @param maxWait Maximum time to wait before shutting down.
     */
    public void shutdownExecutorService(ExecutorService es,
            Future<?> future, Object o, long delay, long maxWait) {
        long counter = 0;
        while (!future.isDone()) {
            counter = checkFuture(o, counter, delay);
        }
        env.log("Job done.");
        shutdownExecutorService(es, maxWait);
    }

    /**
     * For delaying further execution of a program for {@code delay} number of
     * milliseconds.
     *
     * @param env For logging.
     * @param o The Object used to synchronize the delay.
     * @param delay Number of milliseconds to wait.
     */
    public static void waitSychronized(Generic_Environment env, Object o,
            long delay) {
        try {
            synchronized (o) {
                o.wait(delay);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
            env.log(ex.getMessage());
        }
    }
}
