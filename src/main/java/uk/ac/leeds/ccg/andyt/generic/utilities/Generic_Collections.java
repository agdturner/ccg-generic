/**
 * Copyright 2012 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.utilities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigInteger;

/**
 *
 * @author geoagdt
 */
public class Generic_Collections {

    /**
     * @param keys0 
     * @param keys1
     * @return Iterator&lt;Integer&gt; over the unified collection of keys0 and
     * keys1
     */
    public static HashSet<Integer> getCompleteKeySet_HashSet(
            Set<Integer> keys0,
            Set<Integer> keys1) {
        HashSet<Integer> result = new HashSet<Integer>();
        result.addAll(keys0);
        result.addAll(keys1);
        return result;
    }

    /**
     * Adds value to the value at a_TreeMapIntegerIntegerCounter.get(key) if it 
     * exists or puts the key, value into a_TreeMapIntegerIntegerCounter 
     * @param a_TreeMapIntegerIntegerCounter
     * @param key
     * @param value 
     */
    public static void addToTreeMapIntegerInteger(
            TreeMap<Integer, Integer> a_TreeMapIntegerIntegerCounter,
            Integer key,
            Integer value) {
        Integer currentValue = a_TreeMapIntegerIntegerCounter.get(key);
        if (currentValue != null) {
            Integer newValue = currentValue + value;
            a_TreeMapIntegerIntegerCounter.put(key, newValue);
        } else {
            a_TreeMapIntegerIntegerCounter.put(key, value);
        }
    }

    /**
     * Adds value to the value at a_TreeMapIntegerIntegerCounter.get(key) if it 
     * exists or puts the key, value into a_TreeMapIntegerIntegerCounter 
     * @param update_TreeMapIntegerIntegerCounter
     * @param a_TreeMapIntegerIntegerCounter
     * @param updateFrom_TreeMapIntegerIntegerCounter
     * @param key 
     */
    public static void addToTreeMapIntegerInteger(
            TreeMap<Integer, Integer> update_TreeMapIntegerIntegerCounter,
            TreeMap<Integer, Integer> updateFrom_TreeMapIntegerIntegerCounter) {
        if (updateFrom_TreeMapIntegerIntegerCounter != null) {
            Integer key;
            Integer value;
            for (Entry<Integer, Integer> entry : updateFrom_TreeMapIntegerIntegerCounter.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                Integer currentValue = update_TreeMapIntegerIntegerCounter.get(key);
                if (currentValue != null) {
                    Integer newValue = currentValue + value;
                    update_TreeMapIntegerIntegerCounter.put(key, newValue);
                } else {
                    update_TreeMapIntegerIntegerCounter.put(key, value);
                }
            }
        }
    }

    /**
     * Adds value to the value at a_IntegerLong_TreeMap.get(key) if it 
     * exists or puts the key, value into a_IntegerLong_TreeMap 
     * @param a_IntegerLong_TreeMap
     * @param key
     * @param value 
     */
    public static void addToTreeMapIntegerLong(
            TreeMap<Integer, Long> a_IntegerLong_TreeMap,
            Integer key,
            Long value) {
        Long currentValue = a_IntegerLong_TreeMap.get(key);
        if (currentValue != null) {
            Long newValue = currentValue + value;
            a_IntegerLong_TreeMap.put(key, newValue);
        } else {
            a_IntegerLong_TreeMap.put(key, value);
        }
    }
    
    /**
     * Adds value to the value at map.get(key) if it 
     * exists or puts the key, value into map.
     * @param map
     * @param key
     * @param value 
     */
    public static void addToTreeMapStringLong(
            TreeMap<String, Long> map,
            String key,
            Long value) {
        Long currentValue = map.get(key);
        if (currentValue != null) {
            Long newValue = currentValue + value;
            map.put(key, newValue);
        } else {
            map.put(key, value);
        }
    }
    
    /**
     * Adds value to the value at map.get(key) if it 
     * exists or puts the key, value into map.
     * @param map
     * @param key
     * @param value 
     */
    public static void addToTreeMapStringInteger(
            TreeMap<String, Integer> map,
            String key,
            Integer value) {
        Integer currentValue = map.get(key);
        if (currentValue != null) {
            Integer newValue = currentValue + value;
            map.put(key, newValue);
        } else {
            map.put(key, value);
        }
    }

    /**
     * Adds value to the value at a_IntegerBigInteger_TreeMap.get(key) if it 
     * exists or puts the key, value into a_IntegerBigInteger_TreeMap 
     * @param a_IntegerBigInteger_TreeMap
     * @param key
     * @param value 
     */
    public static void addToTreeMapIntegerBigInteger(
            TreeMap<Integer, BigInteger> a_IntegerBigInteger_TreeMap,
            Integer key,
            BigInteger value) {
        BigInteger currentValue = a_IntegerBigInteger_TreeMap.get(key);
        if (currentValue != null) {
            BigInteger newValue = currentValue.add(value);
            a_IntegerBigInteger_TreeMap.put(key, newValue);
        } else {
            a_IntegerBigInteger_TreeMap.put(key, value);
        }
    }

    /**
     * Adds value to the value at a_IntegerBigDecimal_TreeMap.get(key) if it 
     * exists or puts the key, value into a_IntegerBigDecimal_TreeMap 
     * @param a_IntegerBigDecimal_TreeMap
     * @param key
     * @param value 
     */
    public static void addToTreeMapIntegerBigDecimal(
            TreeMap<Integer, BigDecimal> a_IntegerBigDecimal_TreeMap,
            Integer key,
            BigDecimal value) {
        BigDecimal currentValue = a_IntegerBigDecimal_TreeMap.get(key);
        if (currentValue != null) {
            BigDecimal newValue = currentValue.add(value);
            a_IntegerBigDecimal_TreeMap.put(key, newValue);
        } else {
            a_IntegerBigDecimal_TreeMap.put(key, value);
        }
    }

    public static TreeMap<Integer, BigInteger> deepCopy_Integer_BigInteger(
            TreeMap<Integer, BigInteger> a_TreeMap) {
        TreeMap<Integer, BigInteger> result = new TreeMap<Integer, BigInteger>();
        Iterator<Integer> ite = a_TreeMap.keySet().iterator();
        Integer keyToCopy;
        Integer keyCopy;
        BigInteger valueToCopy;
        BigInteger valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = new Integer(keyToCopy);
            valueToCopy = a_TreeMap.get(keyToCopy);
            valueCopy = new BigInteger(valueToCopy.toString());
            result.put(keyCopy, valueCopy);
        }
        return result;
    }

    public static HashMap<Long,String> deepCopy_Long_String(
            HashMap<Long,String> aHashMap){
        HashMap<Long,String> result = new HashMap<Long,String>();
        Iterator<Long> ite = aHashMap.keySet().iterator();
        Long keyToCopy;
        Long keyCopy;
        String valueToCopy;
        String valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = new Long(keyToCopy);
            valueToCopy = aHashMap.get(keyToCopy);
            valueCopy = valueToCopy;
            result.put(keyCopy, valueCopy);
        }
        return result;
    }
    
    public static TreeMap<Integer, BigDecimal> deepCopy_Integer_BigDecimal(
            TreeMap<Integer, BigDecimal> a_TreeMap) {
        TreeMap<Integer, BigDecimal> result = new TreeMap<Integer, BigDecimal>();
        Iterator<Integer> ite = a_TreeMap.keySet().iterator();
        Integer keyToCopy;
        Integer keyCopy;
        BigDecimal valueToCopy;
        BigDecimal valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = new Integer(keyToCopy);
            valueToCopy = a_TreeMap.get(keyToCopy);
            valueCopy = new BigDecimal(valueToCopy.toString());
            result.put(keyCopy, valueCopy);
        }
        return result;
    }

    public static TreeMap<Integer, Long> deepCopy_Integer_Long(
            TreeMap<Integer, Long> a_TreeMap) {
        TreeMap<Integer, Long> result = new TreeMap<Integer, Long>();
        Iterator<Integer> ite = a_TreeMap.keySet().iterator();
        Integer keyToCopy;
        Integer keyCopy;
        Long valueToCopy;
        Long valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = new Integer(keyToCopy);
            valueToCopy = a_TreeMap.get(keyToCopy);
            valueCopy = new Long(valueToCopy);
            result.put(keyCopy, valueCopy);
        }
        return result;
    }

    public static void addToTreeMapIntegerLong(
            TreeMap<Integer, Long> a_TreeMapToAddTo,
            TreeMap<Integer, Long> a_TreeMapToAdd) {
        Iterator<Integer> ite = a_TreeMapToAdd.keySet().iterator();
        Integer key_Integer;
        Long value_Long;
        Long valueToAddTo_Long;
        while (ite.hasNext()) {
            key_Integer = ite.next();
            value_Long = a_TreeMapToAdd.get(key_Integer);
            if (a_TreeMapToAddTo.containsKey(key_Integer)) {
                valueToAddTo_Long = a_TreeMapToAddTo.get(key_Integer);
                a_TreeMapToAddTo.put(key_Integer, value_Long + valueToAddTo_Long);
            } else {
                a_TreeMapToAddTo.put(key_Integer, value_Long);
            }
        }
    }

    public static void addToTreeMapIntegerBigDecimal(
            TreeMap<Integer, BigDecimal> a_TreeMapToAddTo,
            TreeMap<Integer, BigDecimal> a_TreeMapToAdd) {
        Iterator<Integer> ite = a_TreeMapToAdd.keySet().iterator();
        Integer key_Integer;
        BigDecimal value_BigDecimal;
        BigDecimal valueToAddTo_BigDecimal;
        while (ite.hasNext()) {
            key_Integer = ite.next();
            value_BigDecimal = a_TreeMapToAdd.get(key_Integer);
            if (a_TreeMapToAddTo.containsKey(key_Integer)) {
                valueToAddTo_BigDecimal = a_TreeMapToAddTo.get(key_Integer);
                a_TreeMapToAddTo.put(key_Integer, value_BigDecimal.add(valueToAddTo_BigDecimal));
            } else {
                a_TreeMapToAddTo.put(key_Integer, value_BigDecimal);
            }
        }
    }

    public static void addToTreeMapIntegerBigInteger(
            TreeMap<Integer, BigInteger> a_TreeMapToAddTo,
            TreeMap<Integer, BigInteger> a_TreeMapToAdd) {
        Iterator<Integer> ite = a_TreeMapToAdd.keySet().iterator();
        Integer key_Integer;
        BigInteger value_BigInteger;
        BigInteger valueToAddTo_BigInteger;
        while (ite.hasNext()) {
            key_Integer = ite.next();
            value_BigInteger = a_TreeMapToAdd.get(key_Integer);
            if (a_TreeMapToAddTo.containsKey(key_Integer)) {
                valueToAddTo_BigInteger = a_TreeMapToAddTo.get(key_Integer);
                a_TreeMapToAddTo.put(key_Integer, value_BigInteger.add(valueToAddTo_BigInteger));
            } else {
                a_TreeMapToAddTo.put(key_Integer, value_BigInteger);
            }
        }
    }

    /**
     * Class<?> Means this can be any type
     * http://stackoverflow.com/questions/2208317/generic-map-of-generic-key-values-with-related-types
     * <V extends Object> is equivalent to V which is less verbose
     * Class<? extends Object> is equivalent to Class<?> which is less verbose
     * @param a_TreeMap
     * @param default_Integer
     * @return
     */
    public static Integer getMaxKey_Integer(
            //TreeMap<Integer,Class<?>> a_TreeMap,
            //TreeMap<Integer,Class<? extends Object>> a_TreeMap,
            TreeMap<Integer, ?> a_TreeMap,
            Integer default_Integer) {
        if (a_TreeMap.isEmpty()) {
            return default_Integer;
        } else {
            return a_TreeMap.lastKey();
        }
    }

    /**
     * Class<?> Means this can be any type
     * http://stackoverflow.com/questions/2208317/generic-map-of-generic-key-values-with-related-types
     * <V extends Object> is equivalent to V which is less verbose
     * Class<? extends Object> is equivalent to Class<?> which is less verbose
     * @param a_TreeMap
     * @param default_Integer
     * @return
     */
    public static Integer getMinKey_Integer(
            //TreeMap<Integer, Class<? extends Object>> a_TreeMap,
            TreeMap<Integer, ?> a_TreeMap,
            Integer default_Integer) {
        if (a_TreeMap.isEmpty()) {
            return default_Integer;
        } else {
            return a_TreeMap.lastKey();
        }
    }

    public static BigDecimal getMaxValue_BigDecimal(
            TreeMap<?, BigDecimal> a_TreeMap,
            BigDecimal initialMax_BigDecimal) {
        BigDecimal result = new BigDecimal(initialMax_BigDecimal.toString());
        Iterator<BigDecimal> iterator = a_TreeMap.values().iterator();
        BigDecimal value;
        while (iterator.hasNext()) {
            value = iterator.next();
            result = Generic_BigDecimal.max(
                    result,
                    value);
        }
        return result;
    }

    public static BigDecimal getMinValue_BigDecimal(
            TreeMap<?, BigDecimal> a_TreeMap,
            BigDecimal initialMin_BigDecimal) {
        BigDecimal result = new BigDecimal(initialMin_BigDecimal.toString());
        Iterator<BigDecimal> iterator = a_TreeMap.values().iterator();
        BigDecimal value;
        while (iterator.hasNext()) {
            value = iterator.next();
            result = Generic_BigDecimal.min(
                    result,
                    value);
        }
        return result;
    }

    public static BigInteger getMaxValue_BigInteger(
            TreeMap<?, BigInteger> a_TreeMap,
            BigInteger initialMax_BigInteger) {
        BigInteger result = new BigInteger(initialMax_BigInteger.toString());
        Iterator<BigInteger> iterator = a_TreeMap.values().iterator();
        BigInteger value;
        while (iterator.hasNext()) {
            value = iterator.next();
            result = Generic_BigInteger.max(
                    result,
                    value);
        }
        return result;
    }

    public static BigInteger getMinValue_BigInteger(
            TreeMap<?, BigInteger> a_TreeMap,
            BigInteger initialMin_BigInteger) {
        BigInteger result = new BigInteger(initialMin_BigInteger.toString());
        Iterator<BigInteger> iterator = a_TreeMap.values().iterator();
        BigInteger value;
        while (iterator.hasNext()) {
            value = iterator.next();
            result = Generic_BigInteger.min(
                    result,
                    value);
        }
        return result;
    }
}
