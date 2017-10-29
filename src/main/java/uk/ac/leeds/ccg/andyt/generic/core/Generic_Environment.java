/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.core;

import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_Files;

/**
 *
 * @author geoagdt
 */
public class Generic_Environment {

    public Generic_Files Files;
    public Generic_Strings Strings;

    protected Generic_Environment() {
    }

    public Generic_Files getFiles() {
        if (Files == null) {
//            Files = new Generic_Files();
        }
        return Files;
    }

    public Generic_Strings getStrings() {
        if (Strings == null) {
            Strings = new Generic_Strings();
        }
        return Strings;
    }

}
