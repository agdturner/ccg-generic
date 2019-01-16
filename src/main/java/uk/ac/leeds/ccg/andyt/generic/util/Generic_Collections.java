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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

/**
 * For processing and manipulating collections including Lists, Arrays, Sets and
 * Maps.
 */
public class Generic_Collections {

    /**
     * Returns a key in M that is mapped to the value input. If there are
     * multiple keys mapped to the value, this returns the first one that is
     * come across.
     *
     * @param m Map
     * @param value Object
     * @return Object
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
     * @param map Map
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
     * @param map Map
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

    /**
     * @param keys0 Set
     * @param keys1 Set
     * @return HashSet
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
     * @param <K> Key
     * @param <V> Value
     * @param m Map
     * @param k key
     * @param v value
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

    /**
     * If m contains the key k, then the key value pair (k2, v) are put in to
     * the value against k in m. If m does not contain the key k a new mapping
     * is put in m against k and the key value pair (k2, v) are put in the new
     * map.
     *
     * @param <K> Key
     * @param <K2> Key2
     * @param <V> Value
     * @param m Map
     * @param k key
     * @param k2 key2
     * @param v value
     */
    public static <K, K2, V> void addToMap(HashMap<K, HashMap<K2, V>> m, K k,
            K2 k2, V v) {
        HashMap<K2, V> m2;
        if (m.containsKey(k)) {
            m2 = m.get(k);
        } else {
            m2 = new HashMap<>();
            m.put(k, m2);
        }
        m2.put(k2, v);
    }

    /**
     * If m contain the key k, then v is added to the HashSet. Otherwise a new
     * HashSet is created and added to m using the key k and v is added to the
     * HashSet.
     *
     * @param <K> Key
     * @param <V> Value
     * @param m Map
     * @param k key
     * @param v value
     */
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
     * @param <K> Key
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
    }

    /**
     * Adds v to the ArrayList in m indexed by k.If such a list does not yet
     * exist it is created.
     *
     * @param <K> Key
     * @param <V> Value
     * @param m The map that is to be added to.
     * @param k The key which value is added to or initialised.
     * @param v The value to add to the list in map.
     */
    public static <K, V> void addToListIfDifferentFromLast(
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
     * @param m Map
     * @param key Integer
     * @param value Integer
     */
    @Deprecated
    public static void addToTreeMapIntegerInteger(
            TreeMap<Integer, Integer> m,
            Integer key,
            Integer value) {
        Integer currentValue = m.get(key);
        if (currentValue != null) {
            Integer newValue = currentValue + value;
            m.put(key, newValue);
        } else {
            m.put(key, value);
        }
    }

    /**
     * @param updateIntegerIntegerCounter TreeMap
     * @param updateFromIntegerIntegerCounter TreeMap
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
     * Adds value to the value at map.get(key) if it exists or puts the key,
     * value into map.
     *
     * @param <K> Key
     * @param m TreeMap
     * @param k key
     * @param v value
     * @return long
     */
    public static <K> long addToTreeMapValueLong(TreeMap<K, Long> m,
            K k, long v) {
        long r;
        Long v0 = m.get(k);
        if (v0 != null) {
            r = v0 + v;
        } else {
            r = v;
        }
        m.put(k, r);
        return r;
    }

    /**
     * Adds v to the value of m given by k if it exists or puts the v into m at
     * k.
     *
     * @param <K> Key
     * @param m TreeMap
     * @param k key
     * @param v value
     * @return The resulting value of adding v to m.
     */
    public static <K> int addToTreeMapValueInteger(TreeMap<K, Integer> m,
            K k, int v) {
        int r;
        Integer v0 = m.get(k);
        if (v0 != null) {
            r = v0 + v;
        } else {
            r = v;
        }
        m.put(k, r);
        return r;
    }

    /**
     * Adds value to the value at map.get(key) if it exists or puts the key,
     * value into map.
     *
     * @param <K> Key
     * @param map0 TreeMap
     * @param map1 TreeMap
     * @return TreeMap
     */
    public static <K> TreeMap<K, Integer> addToTreeMapValueInteger(
            TreeMap<K, Integer> map0, TreeMap<K, Integer> map1) {
        TreeMap<K, Integer> r;
        r = deepCopyTreeMapValueInteger(map0);
        Iterator<K> ite;
        ite = map1.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            Integer v = map1.get(k);
            Generic_Collections.addToTreeMapValueInteger(r, k, v);
        }
        return r;
    }

    /**
     * Sets the value in map to the max of map.get(key) and value.
     *
     * @param m TreeMap
     * @param k key
     * @param v value
     */
    public static void setMaxValueTreeMapStringInteger(
            TreeMap<String, Integer> m, String k, Integer v) {
        Integer v0 = m.get(k);
        if (v0 != null) {
            int v1 = Math.max(v0, v);
            if (!(v1 == v0)) {
                m.put(k, v1);
            }
        } else {
            m.put(k, v);
        }
    }

    /**
     * Sets the value in map to the min of map.get(key) and value.
     *
     * @param m TreeMap
     * @param k key
     * @param v value
     */
    public static void setMinValueTreeMapStringInteger(
            TreeMap<String, Integer> m, String k, Integer v) {
        Integer v0 = m.get(k);
        if (v0 != null) {
            Integer v1 = Math.min(v0, v);
            if (!(v1 == v0.intValue())) {
                m.put(k, v1);
            }
        } else {
            m.put(k, v);
        }
    }

    /**
     * Adds v to the value of m corresponding with k. If there is no such k in m
     * or the value m.get(k) is null or
     *
     * @param <K> Key
     * @param m TreeMap
     * @param k key
     * @param v value
     */
    public static <K> void addToTreeMapValueBigInteger(
            TreeMap<K, BigInteger> m, K k, BigInteger v) {
        BigInteger v0 = m.get(k);
        if (v0 != null) {
            BigInteger newValue = v0.add(v);
            m.put(k, newValue);
        } else {
            m.put(k, v);
        }
    }

    /**
     * Adds v to the value of m corresponding with k. If there is no such k in m
     * or the value m.get(k) is null or
     *
     * @param <K> Key
     * @param m TreeMap
     * @param k key
     * @param v value
     */
    public static <K> void addToTreeMapValueBigDecimal(
            TreeMap<K, BigDecimal> m, K k, BigDecimal v) {
        BigDecimal v0 = m.get(k);
        if (v0 != null) {
            BigDecimal newValue = v0.add(v);
            m.put(k, newValue);
        } else {
            m.put(k, v);
        }
    }

    /**
     * For all values in set1 we count how many values are in set0, and deduce
     * how many are not. Also we check how many values that are in set0 that are
     * not in set1.
     *
     * @param set0 HashSet
     * @param set1 HashSet
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
     * not in set1.
     *
     * @param <T> Type
     * @param set0 HashSet
     * @param set1 HashSet
     * @return Object[2] result {@code
     * Object[0] = union set view of elements in both set0 and set1
     * Object[1] = counts
     * counts[0] = Count of how many values are in both set 0 and set 1;
     * counts[1] = Count of how many values are in set 1, but not in set 0;
     * counts[2] = Count of how many values are in set 0, but not in set 1;
     * }
     */
    public static <T> Object[] getUnionAndCounts(HashSet<T> set0, HashSet<T> set1) {
        Object[] result;
        result = new Object[2];
        HashSet<T> union;
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
     * how many are not.Also we check how many values that are in set0 that are
     * not in set1.
     *
     * @param <T> Type
     * @param set0 HashSet
     * @param set1 HashSet
     * @return Object[2] result {@code
     * Object[0] = union set view of elements in both set0 and set1
     * Object[1] = counts
     * counts[0] = Count of how many values are in both set 0 and set 1;
     * counts[1] = Count of how many values are in set 1, but not in set 0;
     * counts[2] = Count of how many values are in set 0, but not in set 1;
     * }
     */
    public static <T> Object[] getUnionAndUniques(HashSet<T> set0, HashSet<T> set1) {
        Object[] result;
        result = new Object[3];
        HashSet<T> union;
        union = new HashSet<>();
        union.addAll(set1);
        union.retainAll(set0);
        HashSet<T> set1unique;
        set1unique = new HashSet<>();
        set1unique.addAll(set1);
        set1unique.removeAll(set0);
        HashSet<T> set0unique;
        set0unique = new HashSet<>();
        set0unique.addAll(set0);
        set0unique.removeAll(set1);
        result[0] = union;
        result[1] = set1unique;
        result[2] = set0unique;
        return result;
    }

    public static <K> TreeMap<K, BigInteger> deepCopyValueBigInteger(
            TreeMap<K, BigInteger> m) {
        TreeMap<K, BigInteger> r;
        r = new TreeMap<>();
        Iterator<K> ite;
        ite = m.keySet().iterator();
        K k;
        BigInteger vToCopy;
        BigInteger vCopy;
        while (ite.hasNext()) {
            k = ite.next();
            vToCopy = m.get(k);
            vCopy = new BigInteger(vToCopy.toString());
            r.put(k, vCopy);
        }
        return r;
    }

    public static <K> HashMap<K, String> deepCopyHashMapValueString(
            HashMap<K, String> m) {
        HashMap<K, String> r;
        r = new HashMap<>();
        Iterator<K> ite;
        ite = m.keySet().iterator();
        K k;
        while (ite.hasNext()) {
            k = ite.next();
            r.put(k, m.get(k));
        }
        return r;
    }

    public static <K> HashMap<K, Integer> deepCopyHashMapValueInteger(
            HashMap<K, Integer> m) {
        HashMap<K, Integer> r;
        r = new HashMap<>();
        Iterator<K> ite;
        ite = m.keySet().iterator();
        K k;
        while (ite.hasNext()) {
            k = ite.next();
            r.put(k, m.get(k));
        }
        return r;
    }

    public static <K, Integer> TreeMap<K, Integer> deepCopyTreeMapValueInteger(
            TreeMap<K, Integer> map) {
        TreeMap<K, Integer> r;
        r = new TreeMap<>();
        Iterator<K> ite;
        ite = map.keySet().iterator();
        K k;
        while (ite.hasNext()) {
            k = ite.next();
            r.put(k, map.get(k));
        }
        return r;
    }

    public static <K> TreeMap<K, BigDecimal> deepCopyTreeMapValueBigDecimal(
            TreeMap<K, BigDecimal> m) {
        TreeMap<K, BigDecimal> r;
        r = new TreeMap<>();
        Iterator<K> ite;
        ite = m.keySet().iterator();
        K k;
        BigDecimal v0;
        BigDecimal v1;
        while (ite.hasNext()) {
            k = ite.next();
            v0 = m.get(k);
            v1 = new BigDecimal(v0.toString());
            r.put(k, v1);
        }
        return r;
    }

    public static <K> TreeMap<K, Long> deepCopyTreeMapValueLong(
            TreeMap<K, Long> m) {
        TreeMap<K, Long> r;
        r = new TreeMap<>();
        Iterator<K> ite;
        ite = m.keySet().iterator();
        K k;
        Long v0;
        Long v1;
        while (ite.hasNext()) {
            k = ite.next();
            v0 = m.get(k);
            v1 = v0;
            r.put(k, v1);
        }
        return r;
    }

    public static <K> void addToTreeMapValueLong(
            TreeMap<K, Long> mapToAddTo,
            TreeMap<K, Long> mapToAdd) {
        Iterator<K> ite;
        ite = mapToAdd.keySet().iterator();
        K k;
        Long vToAdd;
        Long vToAddTo;
        while (ite.hasNext()) {
            k = ite.next();
            vToAdd = mapToAdd.get(k);
            if (mapToAddTo.containsKey(k)) {
                vToAddTo = mapToAddTo.get(k);
                mapToAddTo.put(k, vToAdd + vToAddTo);
            } else {
                mapToAddTo.put(k, vToAdd);
            }
        }
    }

    public static <K> void addToTreeMapValueBigDecimal(
            TreeMap<K, BigDecimal> mapToAddTo,
            TreeMap<K, BigDecimal> mapToAdd) {
        Iterator<K> ite;
        ite = mapToAdd.keySet().iterator();
        K k;
        BigDecimal vToAdd;
        BigDecimal vToAddTo;
        while (ite.hasNext()) {
            k = ite.next();
            vToAdd = mapToAdd.get(k);
            if (mapToAddTo.containsKey(k)) {
                vToAddTo = mapToAddTo.get(k);
                mapToAddTo.put(k, vToAdd.add(vToAddTo));
            } else {
                mapToAddTo.put(k, vToAdd);
            }
        }
    }

    public static <K> void addToTreeMapValueBigInteger(
            TreeMap<K, BigInteger> mapToAddTo,
            TreeMap<K, BigInteger> mapToAdd) {
        Iterator<K> ite;
        ite = mapToAdd.keySet().iterator();
        K k;
        BigInteger vToAdd;
        BigInteger vToAddTo;
        while (ite.hasNext()) {
            k = ite.next();
            vToAdd = mapToAdd.get(k);
            if (mapToAddTo.containsKey(k)) {
                vToAddTo = mapToAddTo.get(k);
                mapToAddTo.put(k, vToAdd.add(vToAddTo));
            } else {
                mapToAddTo.put(k, vToAdd);
            }
        }
    }

    /**
     * @param m TreeMap
     * @param i Integer
     * @return Integer
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
     * @param m TreeMap
     * @param i Integer
     * @return Integer
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
     * @param <K> KeyType
     * @param <V> ValueType
     * @param m The map that is to be ordered by it's values.
     * @return Map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> m) {
        Map<K, V> r;
        List<Map.Entry<K, V>> list;
        list = new LinkedList<>(m.entrySet());
        Collections.sort(list, (Map.Entry<K, V> o1, Map.Entry<K, V> o2) -> (o1.getValue()).compareTo(o2.getValue()));
        r = new LinkedHashMap<>();
        list.forEach((entry) -> {
            r.put(entry.getKey(), entry.getValue());
        });
        return r;
    }
    
    /**
     * For getting the maximum value in a collection of BigDecimal. 
     * @param c
     * @return the maximum value in {@code c} 
     */
    public static BigDecimal getMax(Collection<BigDecimal> c) {
        Optional<BigDecimal> o;
        o = c.stream().parallel().max(BigDecimal::compareTo);
        return o.get();
    }
    
    /**
     * For getting the maximum value in a collection of BigDecimal. 
     * @param c
     * @return the maximum value in {@code c} 
     */
    public static BigDecimal getMin(Collection<BigDecimal> c) {
        Optional<BigDecimal> o;
        o = c.stream().parallel().min(BigDecimal::compareTo);
        return o.get();
    }
    
    /**
     * @param c
     * @return True iff b is in c.
     */
    public static boolean containsValue(Collection<BigDecimal> c, BigDecimal b) {
        boolean r;
        r = c.stream().parallel().anyMatch(v -> v.equals(b));
        return r;
    }
}
