/*
 * Copyright (C) 2015 geoagdt.
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
package uk.ac.leeds.ccg.andyt.generic.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author geoagdt
 */
public class Generic_StaticTime {

    /**
     * @return Current clock date as yyyy-MM-dd format 
     */
    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println(cal.getTime());
            // Ouput "Fri Aug 14 11:18:14 BST 2015"

        String formatted = format1.format(cal.getTime());
        //System.out.println(formatted);
        return formatted;
            // Output "2015-08-14"
//        try {
//            System.out.println(format1.parse(formatted));
//            // Output "Fri Aug 14 00:00:00 BST 2015"
//        } catch (ParseException ex) {
//            Logger.getLogger(Generic_StaticTime.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
