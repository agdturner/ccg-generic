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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andy Turner
 */
public class Generic_Runnable extends Thread implements Runnable {

    Generic_EventListenerImpl listener;
    int runID;

    public Generic_Runnable() {
        this(0);
    }

    public Generic_Runnable(int runID) {
        this.runID = runID;
    }

    public static void main(String[] args) {
        new Generic_Runnable(0).start();
    }

    @Override
    public void run() {
        long timeSleepInMillis = 10000;
        sleepABit(timeSleepInMillis);
        if (listener != null) {
            listener.renderingComplete(new Generic_RenderingCompleteEvent(this));
        }
        sleepABit(timeSleepInMillis * timeSleepInMillis);
    }

    protected void sleepABit(long timeSleepInMillis) {
        System.out.println("" + runID + " sleeping for " + timeSleepInMillis + " milliseconds...");
        try {
            Thread.sleep(timeSleepInMillis);
        } catch (InterruptedException ex) {
            Logger.getLogger(Generic_Runnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("..." + runID + " done sleeping");
    }
}
