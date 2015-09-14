/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.leeds.ccg.andyt.generic.utilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * Class of static methods for copying Objects.
 */
public class Generic_StaticCopy {

    public static HashMap copy_Key_Vector_HashMap(
            HashMap aKey_Vector_Hashmap) {
        HashMap result = new HashMap();
        Vector aVector;
        //Vector bVector = new Vector();
        Object key;
        Iterator iterator = aKey_Vector_Hashmap.keySet().iterator();
        while (iterator.hasNext()){
            key = iterator.next();
            aVector = (Vector) aKey_Vector_Hashmap.get(key);
            /*
             * In profiling this was found to be very slow, so calling clone is
             * prefered :
             * for (int i = 0; i < aVector.size(); i ++ ){
             *    bVector.add(i,aVector.elementAt(i));
             * }
             * result.put(key, bVector);
             */
            result.put(key, aVector.clone());
        }
        return result;
    }
}
