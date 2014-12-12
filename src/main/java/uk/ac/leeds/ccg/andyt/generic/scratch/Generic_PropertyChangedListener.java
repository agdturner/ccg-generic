/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.scratch;

import java.beans.PropertyChangeListener;

/**
 *
 * @author geoagdt
 */
public interface Generic_PropertyChangedListener extends PropertyChangeListener {
    
    public void renderingComplete(Generic_PropertyChangedListener e);
}
