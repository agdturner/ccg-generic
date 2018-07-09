/*
 * Copyright (C) 2016 geoagdt.
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package uk.ac.leeds.ccg.andyt.generic.execution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andy Turner
 */
public class Generic_Executor implements Runnable {

    public Generic_ImageWriter imageWriter;

    public Generic_Executor() {
    }

    public Generic_Executor(Generic_ImageWriter i) {
    }

    public static void main(String[] args) {
        new Generic_Executor().run();
    }

    @Override
    public void run() {
        int runID = 0;
        int poolSize = 5;
        ExecutorService es;
        es = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < poolSize * 2; i++) {
            final Generic_Runnable r = new Generic_Runnable(runID);
            es.execute(r::start);
            runID++;
        }
        es.shutdown();
        try {
            // wait for them to finish for up to one minute.
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_Executor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
