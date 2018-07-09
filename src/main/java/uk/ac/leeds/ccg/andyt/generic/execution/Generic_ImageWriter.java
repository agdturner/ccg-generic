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

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andy Turner
 */
public class Generic_ImageWriter extends Generic_Executor {

    private final Set<Generic_EventListener> listeners;

    public Generic_ImageWriter() {
        listeners = new HashSet<>();
    }
    
    public static void main(String[] args) {
//                Generic_ImageWriter i = new Generic_ImageWriter();
//                Generic_EventListenerImpl listener = new Generic_EventListenerImpl();
//                i.addGeneric_EventListener(listener);
//                i.start();
        Generic_ImageWriter i = new Generic_ImageWriter();
        Generic_EventListenerImpl listener = new Generic_EventListenerImpl();
        i.addGeneric_EventListener(listener);
        i.start();
        }

    public void addGeneric_EventListener(Generic_EventListener listener) {
        this.listeners.add(listener);
    }

    public void removeGeneric_EventListener(Generic_EventListener listener) {
        this.listeners.remove(listener);
    }

    public void start() {
        run();
        notifyListenersOfRenderingComplete();
        run();
        notifyListenersOfRenderingComplete();
    }

    private void notifyListenersOfRenderingComplete() {
//        for (Generic_EventListener e : listeners) {
//            e.renderingComplete(new Generic_RenderingCompleteEvent(this));
//        }
        listeners.forEach((e) -> {
            e.renderingComplete(new Generic_RenderingCompleteEvent(this));
        });
    }
}
