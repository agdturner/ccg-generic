/**
 * Copyright (C) Centre for Computational Geography, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.andyt.generic.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Andy Turner
 */
public class Generic_Collections {

    /**
     * Returns a key in M that is mapped to the value input. If there are
     * multiple keys mapped to the value, this returns the first one that is
     * come across.
     *
     * @param m
     * @param value
     * @return
     */
    public static Object getKeyFromValue(Map m, Object value) {
        for (Object o : m.keySet()) {
            if (m.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    public static Object[] getIntervalCountsLabelsMins(BigDecimal min,
            BigDecimal intervalWidth, TreeMap<?, BigDecimal> map,
            MathContext mc) {
        Object[] result;
        result = new Object[3];
        TreeMap<Integer, Integer> counts;
        counts = new TreeMap<>();
        TreeMap<Integer, String> labels;
        labels = new TreeMap<>();
        TreeMap<Integer, BigDecimal> mins;
        mins = new TreeMap<>();
        Iterator<BigDecimal> ite;
        ite = map.values().iterator();
        while (ite.hasNext()) {
            BigDecimal value = ite.next();
            int interval;
            if (intervalWidth.compareTo(BigDecimal.ZERO) == 0) {
                interval = 0;
            } else {
                interval = getInterval(min, intervalWidth, value, mc);
            }
            addToTreeMapIntegerInteger(counts, interval, 1);
            if (!labels.containsKey(interval)) {
                BigDecimal intervalMin;
                intervalMin = getIntervalMin(min, intervalWidth, interval);
                BigDecimal intervalMax;
                intervalMax = getIntervalMax(intervalMin, intervalWidth);
                labels.put(interval, "" + intervalMin + " - " + intervalMax);
                mins.put(interval, intervalMin);
            }
        }
        result[0] = counts;
        result[1] = labels;
        result[2] = mins;
        return result;
    }

    public static BigDecimal getIntervalMin(BigDecimal min,
            BigDecimal intervalWidth, int interval) {
        BigDecimal result;
        result = min.add(new BigDecimal(interval).multiply(intervalWidth));
        return result;
    }

    public static BigDecimal getIntervalMax(BigDecimal intervalMin,
            BigDecimal intervalWidth) {
        BigDecimal result;
        result = intervalMin.add(intervalWidth);
        return result;
    }

    public static int getInterval(BigDecimal min,
            BigDecimal intervalWidth, BigDecimal value, MathContext mc) {
        int result;
        result = (value.subtract(min)).divide(intervalWidth, mc).intValue();
        return result;
    }

    /**
     * @param map
     * @return The min and max values in map.
     */
    public static BigDecimal[] getMinMaxBigDecimal(Map<?, BigDecimal> map) {
        BigDecimal[] result;
        result = new BigDecimal[2];
        boolean firstValue;
        firstValue = true;
        Iterator<BigDecimal> ite;
        ite = map.values().iterator();
        while (ite.hasNext()) {
            BigDecimal value;
            value = ite.next();
            if (firstValue) {
                result[0] = value;
                result[1] = value;
                firstValue = false;
            } else {
                result[0] = result[0].min(value);
                result[1] = result[1].max(value);
            }
        }
        return result;
    }

    /**
     * @param map
     * @return The min and max values in map.
     */
    public static int[] getMinMaxInteger(Map<?, Integer> map) {
        int[] result;
        result = new int[2];
        boolean firstValue;
        firstValue = true;
        Iterator<Integer> ite;
        ite = map.values().iterator();
        while (ite.hasNext()) {
            int value;
            value = ite.next();
            if (firstValue) {
                result[0] = value;
                result[1] = value;
                firstValue = false;
            } else {
                result[0] = Math.min(result[0], value);
                result[1] = Math.max(result[1], value);
            }
        }
        return result;
    }

//    public static HashSet getRandomIndexes_HashSet(Vector v,
//            int aNumberOfIndexes, Random r) {
//        HashSet toSwap = new HashSet();
//        int aIndex;
//        int count = 0;
//        if (aNumberOfIndexes > v.size() / 2) {
//            for (aIndex = 0; aIndex < v.size(); aIndex++) {
//                toSwap.add(aIndex);
//                count++;
//            }
//            while (count != aNumberOfIndexes) {
//                do {
//                    aIndex = r.nextInt(v.size());
//                } while (!toSwap.remove(aIndex));
//                count--;
//            }
//        } else {
//            while (count < aNumberOfIndexes) {
//                do {
//                    aIndex = r.nextInt(v.size());
//                } while (!toSwap.add(aIndex));
//                count++;
//            }
//        }
//        return toSwap;
//    }

    /**
     * @param keys0
     * @param keys1
     * @return Iterator&lt;Integer&gt; over the unified collection of keys0 and
     * keys1
     */
    public static HashSet<Integer> getCompleteKeySet_HashSet(
            Set<Integer> keys0, Set<Integer> keys1) {
        HashSet<Integer> result = new HashSet<>();
        result.addAll(keys0);
        result.addAll(keys1);
        return result;
    }

    /**
     * If m contain the key k, then v is added to the HashSet. Otherwise a new
     * HashSet is created and added to m using the key k and v is added to the
     * HashSet.
     *
     * @param <K>
     * @param <V>
     * @param m
     * @param k
     * @param v
     */
    public static <K, V> void addToMap(HashMap<K, HashSet<V>> m, K k, V v) {
        HashSet<V> s;
        if (m.containsKey(k)) {
            s = m.get(k);
        } else {
            s = new HashSet<>();
            m.put(k, s);
        }
        s.add(v);
    }

    public static <K, V> void addToMap(TreeMap<K, HashSet<V>> m, K k, V v) {
        HashSet<V> s;
        if (m.containsKey(k)) {
            s = m.get(k);
        } else {
            s = new HashSet<>();
            m.put(k, s);
        }
        s.add(v);
    }

//    public static <K, V> void addToMap(Map<K, Set<V>> m, K k, V v) {
//        Set<V> s;
//        if (m.containsKey(k)) {
//            s = m.get(k);
//        } else {
//            s = new HashSet<>();
//            m.put(k, s);
//        }
//        s.add(v);
//    }
    /**
     * Adds to a integer counting map.
     *
     * @param <K>
     * @param m The map that is to be added to.
     * @param k The key which value is added to or initialised.
     * @param i The amount to be added to the map.
     */
    public static <K> void addToMap(Map<K, Integer> m, K k, Integer i) {
        if (!m.containsKey(k)) {
            m.put(k, 1);
        } else {
            m.put(k, m.get(k) + 1);
        }
//        Integer v = m.get(k);
//        if (v != null) {
//            m.put(k, (v + i));
//        } else {
//            m.put(k, i);
//        }
    }

    /**
     * Adds v to the ArrayList in m indexed by k.If such a list does not yet
     * exist it is created.
     *
     * @param <K>
     * @param <V>
     * @param m The map that is to be added to.
     * @param k The key which value is added to or initialised.
     * @param v The value to add to the list in map.
     */
    public static <K, V> void addToLisIfDifferentFromLast(
            Map<K, ArrayList<V>> m, K k, V v) {
        ArrayList<V> l;
        if (m.containsKey(k)) {
            l = m.get(k);
            if (l.size() > 1) {
                V v0 = l.get(l.size() - 1);
                if (!v.equals(v0)) {
                    l.add(v);
                }
            } else {
                l.add(v);
            }
        } else {
            l = new ArrayList<>();
            l.add(v);
            m.put(k, l);
        }
    }

    /**
     * Adds value to the value at a_TreeMapIntegerIntegerCounter.get(key) if it
     * exists or puts the key, value into a_TreeMapIntegerIntegerCounter
     *
     * @param aIntegerIntegerCounter
     * @param key
     * @param value
     */
    public static void addToTreeMapIntegerInteger(
            TreeMap<Integer, Integer> aIntegerIntegerCounter,
            Integer key,
            Integer value) {
        Integer currentValue = aIntegerIntegerCounter.get(key);
        if (currentValue != null) {
            Integer newValue = currentValue + value;
            aIntegerIntegerCounter.put(key, newValue);
        } else {
            aIntegerIntegerCounter.put(key, value);
        }
    }

    /**
     * Adds value to the value at a_TreeMapIntegerIntegerCounter.get(key) if it
     * exists or puts the key, value into a_TreeMapIntegerIntegerCounter
     *
     * @param updateIntegerIntegerCounter
     * @param updateFromIntegerIntegerCounter
     */
    public static void addToTreeMapIntegerInteger(
            TreeMap<Integer, Integer> updateIntegerIntegerCounter,
            TreeMap<Integer, Integer> updateFromIntegerIntegerCounter) {
        if (updateFromIntegerIntegerCounter != null) {
            Integer key;
            Integer value;
            for (Entry<Integer, Integer> entry : updateFromIntegerIntegerCounter.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                Integer currentValue = updateIntegerIntegerCounter.get(key);
                if (currentValue != null) {
                    Integer newValue = currentValue + value;
                    updateIntegerIntegerCounter.put(key, newValue);
                } else {
                    updateIntegerIntegerCounter.put(key, value);
                }
            }
        }
    }

    /**
     * Adds value to the value at a_IntegerLong_TreeMap.get(key) if it exists or
     * puts the key, value into a_IntegerLong_TreeMap
     *
     * @param map
     * @param key
     * @param value
     */
    public static void addToTreeMapIntegerLong(TreeMap<Integer, Long> map,
            Integer key, Long value) {
        Long currentValue = map.get(key);
        if (currentValue != null) {
            Long newValue = currentValue + value;
            map.put(key, newValue);
        } else {
            map.put(key, value);
        }
    }

    /**
     * Adds value to the value at map.get(key) if it exists or puts the key,
     * value into map.
     *
     * @param map
     * @param key
     * @param value
     */
    public static void addToTreeMapStringLong(TreeMap<String, Long> map,
            String key, Long value) {
        Long currentValue = map.get(key);
        if (currentValue != null) {
            Long newValue = currentValue + value;
            map.put(key, newValue);
        } else {
            map.put(key, value);
        }
    }

    /**
     * Adds value to the value at map.get(key) if it exists or puts the key,
     * value into map.
     *
     * @param map
     * @param key
     * @param value
     * @return
     */
    public static int addToTreeMapStringInteger(TreeMap<String, Integer> map,
            String key, Integer value) {
        int result;
        Integer currentValue = map.get(key);
        if (currentValue != null) {
            result = currentValue + value;
        } else {
            result = 1;
        }
        map.put(key, result);
        return result;
    }

    /**
     * Adds value to the value at map.get(key) if it exists or puts the key,
     * value into map.
     *
     * @param map0
     * @param map1
     * @return
     */
    public static TreeMap<String, Integer> addToTreeMapStringInteger(
            TreeMap<String, Integer> map0, TreeMap<String, Integer> map1) {
        TreeMap<String, Integer> result;
        result = deepCopyTreeMapStringInteger(map0);
        Iterator<String> ite;
        ite = map1.keySet().iterator();
        while (ite.hasNext()) {
            String key = ite.next();
            Integer value = map1.get(key);
            addToTreeMapStringInteger(result, key, value);
        }
        return result;
    }

    /**
     * Sets the value in map to the max of map.get(key) and value.
     *
     * @param map
     * @param key
     * @param value
     */
    public static void setMaxValueTreeMapStringInteger(
            TreeMap<String, Integer> map, String key, Integer value) {
        Integer currentValue = map.get(key);
        if (currentValue != null) {
            Integer newValue = Math.max(currentValue, value);
            if (!(newValue == currentValue.intValue())) {
                map.put(key, newValue);
            }
        } else {
            map.put(key, value);
        }
    }

    /**
     * Sets the value in map to the min of map.get(key) and value.
     *
     * @param map
     * @param key
     * @param value
     */
    public static void setMinValueTreeMapStringInteger(
            TreeMap<String, Integer> map, String key, Integer value) {
        Integer currentValue = map.get(key);
        if (currentValue != null) {
            Integer newValue = Math.min(currentValue, value);
            if (!(newValue == currentValue.intValue())) {
                map.put(key, newValue);
            }
        } else {
            map.put(key, value);
        }
    }

    /**
     * Adds value to the value at a_IntegerBigInteger_TreeMap.get(key) if it
     * exists or puts the key, value into a_IntegerBigInteger_TreeMap
     *
     * @param map
     * @param key
     * @param value
     */
    public static void addToTreeMapIntegerBigInteger(
            TreeMap<Integer, BigInteger> map, Integer key, BigInteger value) {
        BigInteger currentValue = map.get(key);
        if (currentValue != null) {
            BigInteger newValue = currentValue.add(value);
            map.put(key, newValue);
        } else {
            map.put(key, value);
        }
    }

    /**
     * Adds value to the value at a_IntegerBigDecimal_TreeMap.get(key) if it
     * exists or puts the key, value into a_IntegerBigDecimal_TreeMap
     *
     * @param map
     * @param key
     * @param value
     */
    public static void addToTreeMapIntegerBigDecimal(
            TreeMap<Integer, BigDecimal> map, Integer key, BigDecimal value) {
        BigDecimal currentValue = map.get(key);
        if (currentValue != null) {
            BigDecimal newValue = currentValue.add(value);
            map.put(key, newValue);
        } else {
            map.put(key, value);
        }
    }

    /**
     * For all values in set1 we count how many values are in set0, and deduce
     * how many are not. Also we check how many values that are in set0 that are
     * not in set1.
     *
     * @param set0
     * @param set1
     * @return long[3] result {@code
     * result[0] = Count of how many values are in both set 0 and set 1;
     * result[1] = Count of how many values are in set 1, but not in set 0;
     * result[2] = Count of how many values are in set 0, but not in set 1;
     * }
     */
    public static long[] getCounts(HashSet set0, HashSet set1) {
        long[] result;
        result = new long[3];
        result[0] = 0;
        result[1] = 0;
        result[2] = 0;
        Iterator ite;
        ite = set1.iterator();
        while (ite.hasNext()) {
            Object o = ite.next();
            if (set0.contains(o)) {
                result[0]++;
            } else {
                result[1]++;
            }
        }
        ite = set0.iterator();
        while (ite.hasNext()) {
            Object o = ite.next();
            if (!set1.contains(o)) {
                result[2]++;
            }
        }
        return result;
    }

    /**
     * For all values in set1 we count how many values are in set0, and deduce
     * how many are not.Also we check how many values that are in set0 that are
 not in set1.
     *
     * @param <K>
     * @param set0
     * @param set1
     * @return Object[2] result {@code
     * Object[0] = union set view of elements in both set0 and set1
     * Object[1] = counts
     * counts[0] = Count of how many values are in both set 0 and set 1;
     * counts[1] = Count of how many values are in set 1, but not in set 0;
     * counts[2] = Count of how many values are in set 0, but not in set 1;
     * }
     */
    public static <K> Object[] getUnionAndCounts(HashSet<K> set0, HashSet<K> set1) {
        Object[] result;
        result = new Object[2];
        HashSet<K> union;
        union = new HashSet<>();
        union.addAll(set1);
        union.retainAll(set0);
        long[] counts;
        counts = new long[3];
        int unionSize;
        unionSize = union.size();
        counts[0] = unionSize;
        counts[1] = set1.size() - unionSize;
        counts[2] = set0.size() - unionSize;
        result[0] = union;
        result[1] = counts;
        return result;
    }

    /**
     * For all values in set1 we count how many values are in set0, and deduce
     * how many are not. Also we check how many values that are in set0 that are
     * not in set1.
     *
     * @param set0
     * @param set1
     * @return Object[2] result {@code
     * Object[0] = union set view of elements in both set0 and set1
     * Object[1] = counts
     * counts[0] = Count of how many values are in both set 0 and set 1;
     * counts[1] = Count of how many values are in set 1, but not in set 0;
     * counts[2] = Count of how many values are in set 0, but not in set 1;
     * }
     */
    public static <K> Object[] getUnionAndUniques(HashSet<K> set0, HashSet<K> set1) {
        Object[] result;
        result = new Object[3];
        HashSet<K> union;
        union = new HashSet<>();
        union.addAll(set1);
        union.retainAll(set0);
        HashSet<K> set1unique;
        set1unique = new HashSet<>();
        set1unique.addAll(set1);
        set1unique.removeAll(set0);
        HashSet<K> set0unique;
        set0unique = new HashSet<>();
        set0unique.addAll(set0);
        set0unique.removeAll(set1);
        result[0] = union;
        result[1] = set1unique;
        result[2] = set0unique;
        return result;
    }

    public static TreeMap<Integer, BigInteger> deepCopy_Integer_BigInteger(
            TreeMap<Integer, BigInteger> map) {
        TreeMap<Integer, BigInteger> result = new TreeMap<>();
        Iterator<Integer> ite = map.keySet().iterator();
        Integer keyToCopy;
        Integer keyCopy;
        BigInteger valueToCopy;
        BigInteger valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = keyToCopy;
            valueToCopy = map.get(keyToCopy);
            valueCopy = new BigInteger(valueToCopy.toString());
            result.put(keyCopy, valueCopy);
        }
        return result;
    }

    public static HashMap<Long, String> deepCopy_Long_String(
            HashMap<Long, String> map) {
        HashMap<Long, String> result = new HashMap<>();
        Iterator<Long> ite = map.keySet().iterator();
        Long keyToCopy;
        Long keyCopy;
        String valueToCopy;
        String valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = keyToCopy;
            valueToCopy = map.get(keyToCopy);
            valueCopy = valueToCopy;
            result.put(keyCopy, valueCopy);
        }
        return result;
    }

    public static HashMap<String, String> deepCopyHashMapStringString(
            HashMap<String, String> map) {
        HashMap<String, String> result = new HashMap<>();
        Iterator<String> ite = map.keySet().iterator();
        String keyToCopy;
        String keyCopy;
        String valueToCopy;
        String valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = keyToCopy;
            valueToCopy = map.get(keyToCopy);
            valueCopy = valueToCopy;
            result.put(keyCopy, valueCopy);
        }
        return result;
    }

    public static HashMap<String, Integer> deepCopyHashMapStringInteger(
            HashMap<String, Integer> map) {
        HashMap<String, Integer> result = new HashMap<>();
        Iterator<String> ite = map.keySet().iterator();
        String keyToCopy;
        String keyCopy;
        Integer valueToCopy;
        Integer valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = keyToCopy;
            valueToCopy = map.get(keyToCopy);
            valueCopy = valueToCopy;
            result.put(keyCopy, valueCopy);
        }
        return result;
    }

    public static TreeMap<String, Integer> deepCopyTreeMapStringInteger(
            TreeMap<String, Integer> map) {
        TreeMap<String, Integer> result;
        result = new TreeMap<>();
        Iterator<String> ite = map.keySet().iterator();
        String keyToCopy;
        String keyCopy;
        Integer valueToCopy;
        Integer valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = keyToCopy;
            valueToCopy = map.get(keyToCopy);
            valueCopy = valueToCopy;
            result.put(keyCopy, valueCopy);
        }
        return result;
    }

    public static TreeMap<Integer, BigDecimal> deepCopy_Integer_BigDecimal(
            TreeMap<Integer, BigDecimal> map) {
        TreeMap<Integer, BigDecimal> result = new TreeMap<>();
        Iterator<Integer> ite = map.keySet().iterator();
        Integer keyToCopy;
        Integer keyCopy;
        BigDecimal valueToCopy;
        BigDecimal valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = keyToCopy;
            valueToCopy = map.get(keyToCopy);
            valueCopy = new BigDecimal(valueToCopy.toString());
            result.put(keyCopy, valueCopy);
        }
        return result;
    }

    public static TreeMap<Integer, Long> deepCopy_Integer_Long(
            TreeMap<Integer, Long> map) {
        TreeMap<Integer, Long> result = new TreeMap<>();
        Iterator<Integer> ite = map.keySet().iterator();
        Integer keyToCopy;
        Integer keyCopy;
        Long valueToCopy;
        Long valueCopy;
        while (ite.hasNext()) {
            keyToCopy = ite.next();
            keyCopy = keyToCopy;
            valueToCopy = map.get(keyToCopy);
            valueCopy = valueToCopy;
            result.put(keyCopy, valueCopy);
        }
        return result;
    }

    public static void addToTreeMapIntegerLong(
            TreeMap<Integer, Long> mapToAddTo,
            TreeMap<Integer, Long> mapToAdd) {
        Iterator<Integer> ite = mapToAdd.keySet().iterator();
        Integer key_Integer;
        Long value_Long;
        Long valueToAddTo_Long;
        while (ite.hasNext()) {
            key_Integer = ite.next();
            value_Long = mapToAdd.get(key_Integer);
            if (mapToAddTo.containsKey(key_Integer)) {
                valueToAddTo_Long = mapToAddTo.get(key_Integer);
                mapToAddTo.put(key_Integer, value_Long + valueToAddTo_Long);
            } else {
                mapToAddTo.put(key_Integer, value_Long);
            }
        }
    }

    public static void addToTreeMapIntegerBigDecimal(
            TreeMap<Integer, BigDecimal> mapToAddTo,
            TreeMap<Integer, BigDecimal> mapToAdd) {
        Iterator<Integer> ite = mapToAdd.keySet().iterator();
        Integer key_Integer;
        BigDecimal value;
        BigDecimal valueToAddTo;
        while (ite.hasNext()) {
            key_Integer = ite.next();
            value = mapToAdd.get(key_Integer);
            if (mapToAddTo.containsKey(key_Integer)) {
                valueToAddTo = mapToAddTo.get(key_Integer);
                mapToAddTo.put(key_Integer, value.add(valueToAddTo));
            } else {
                mapToAddTo.put(key_Integer, value);
            }
        }
    }

    public static void addToTreeMapIntegerBigInteger(
            TreeMap<Integer, BigInteger> mapToAddTo,
            TreeMap<Integer, BigInteger> mapToAdd) {
        Iterator<Integer> ite = mapToAdd.keySet().iterator();
        Integer key_Integer;
        BigInteger value;
        BigInteger valueToAddTo;
        while (ite.hasNext()) {
            key_Integer = ite.next();
            value = mapToAdd.get(key_Integer);
            if (mapToAddTo.containsKey(key_Integer)) {
                valueToAddTo = mapToAddTo.get(key_Integer);
                mapToAddTo.put(key_Integer, value.add(valueToAddTo));
            } else {
                mapToAddTo.put(key_Integer, value);
            }
        }
    }

    /**
     * Class<?> Means this can be any type
     *
     * @param m
     * @param i
     * @return
     */
    public static Integer getMaxKey_Integer(TreeMap<Integer, ?> m,
            Integer i) {
        if (m.isEmpty()) {
            return i;
        } else {
            return m.lastKey();
        }
    }

    /**
     * Class<?> Means this can be any type
     *
     * @param m
     * @param i
     * @return
     */
    public static Integer getMinKey_Integer(TreeMap<Integer, ?> m,
            Integer i) {
        if (m.isEmpty()) {
            return i;
        } else {
            return m.lastKey();
        }
    }

    public static BigDecimal getMaxValue_BigDecimal(
            TreeMap<?, BigDecimal> m,
            BigDecimal initialMax_BigDecimal) {
        BigDecimal r = new BigDecimal(initialMax_BigDecimal.toString());
        Iterator<BigDecimal> iterator = m.values().iterator();
        BigDecimal v;
        while (iterator.hasNext()) {
            v = iterator.next();
            r = r.max(v);
        }
        return r;
    }

    public static BigDecimal getMinValue_BigDecimal(
            TreeMap<?, BigDecimal> m,
            BigDecimal initialMin) {
        BigDecimal result = new BigDecimal(initialMin.toString());
        Iterator<BigDecimal> ite = m.values().iterator();
        BigDecimal v;
        while (ite.hasNext()) {
            v = ite.next();
            result = result.min(v);
        }
        return result;
    }

    public static BigInteger getMaxValue_BigInteger(
            TreeMap<?, BigInteger> m,
            BigInteger initialMax) {
        BigInteger r = new BigInteger(initialMax.toString());
        Iterator<BigInteger> ite = m.values().iterator();
        BigInteger value;
        while (ite.hasNext()) {
            value = ite.next();
            r = r.max(value);
        }
        return r;
    }

    public static BigInteger getMinValue_BigInteger(
            TreeMap<?, BigInteger> m,
            BigInteger initialMin) {
        BigInteger r = new BigInteger(initialMin.toString());
        Iterator<BigInteger> ite = m.values().iterator();
        BigInteger value;
        while (ite.hasNext()) {
            value = ite.next();
            r = r.max(value);
        }
        return r;
    }

    /**
     * Returns a LinkedHashMap which is ordered in terms of the values in the
     * map m.
     *
     * @param <K>
     * @param <V>
     * @param m The map that is to be ordered by it's values.
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> m) {
        Map<K, V> result;
        List<Map.Entry<K, V>> list;
        list = new LinkedList<>(m.entrySet());
        Collections.sort(list, (Map.Entry<K, V> o1, Map.Entry<K, V> o2) -> (o1.getValue()).compareTo(o2.getValue()));
        result = new LinkedHashMap<>();
        list.forEach((entry) -> {
            result.put(entry.getKey(), entry.getValue());
        });
        return result;
    }
}