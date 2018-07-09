/*
 * Copyright (C) 2018 geoagdt.
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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andy Turner
 */
public class Generic_ScheduledExecutor implements Runnable {

    public Generic_ScheduledExecutor se;
    ScheduledExecutorService ses;

    public static void main(String[] args) {
        Generic_ScheduledExecutor se;
        se = new Generic_ScheduledExecutor();
        se.run(args);
    }

    public void run(String[] args) {
        int n;
        n = 2;
        // Create a pool with n threads
        ses = Executors.newScheduledThreadPool(n);
        // Schedule
        int initialDelay;
        initialDelay = 1;
        long delay;
        delay = 3;
        ses.scheduleWithFixedDelay(this, initialDelay, delay, TimeUnit.SECONDS);
        try {
            // Awaits for termination for 10 seconds
//            ses.awaitTermination(10, TimeUnit.SECONDS);
//            ses.awaitTermination(20, TimeUnit.SECONDS);
            ses.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_ScheduledExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // shutdown now.
        ses.shutdownNow();
        System.out.println("Shutdown Complete");
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            System.out.println("Running " + i + "...");
            wait(this, (long) (Math.random() * 10000));
            System.out.println("... comnpleted " + i + ".");
        }
        System.out.println("Done");
    }

    public void wait(Object o, long timeDelay) {
        synchronized (o) {
            try {
                o.wait(timeDelay);
            } catch (InterruptedException ex) {
                Logger.getLogger(Generic_ScheduledExecutor.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Waited " + uk.ac.leeds.ccg.andyt.generic.utilities.Generic_Time.getTime(timeDelay) + ".");
        }
    }
}
