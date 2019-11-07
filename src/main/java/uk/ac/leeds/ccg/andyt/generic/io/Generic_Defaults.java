/*
 * Copyright (C) 2019 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.File;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class Generic_Defaults {
    
    public static File getHomeDir() {
        //return new File(System.getProperty("user.dir"));
        return new File("C:/Users/geoagdt/");
    }
    
    public static File getDataDir() {
        return new File(getHomeDir(), Generic_Strings.s_data);
    }

    public static File getSrcDir() {
        File r = new File(getHomeDir(), Generic_Strings.s_src);
        r = new File(r, Generic_Strings.s_java);
        return r;
    }
}
