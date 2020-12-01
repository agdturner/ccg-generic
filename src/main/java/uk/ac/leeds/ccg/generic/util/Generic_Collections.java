/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.generic.util;

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
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import uk.ac.leeds.ccg.generic.math.Generic_Math;

/**
 * For processing and manipulating collections including Lists, Arrays, Sets and
 * Maps.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_Collections {

    /**
     * Create and return a HashSet of keys that map to the value v.
     *
     * @param <K> The type of keys in m.
     * @param <V> The type of values in m.
     * @param m The map for which the keys that map to v are returned.
     * @param v The value v for which the keys in m are returned.
     * @return A set of keys from m mapped to the value v.
     */
    public static <K, V> HashSet<K> getKeys(Map<K, V> m, V v) {
        HashSet<K> r = new HashSet<>();
        m.keySet().parallelStream().filter((k) -> (m.get(k).equals(v))).forEachOrdered((k) -> {
            r.add(k);
        });
        return r;
    }

    /**
     * @param <K> The type of keys in m.
     * @param <V> The type of values in m and things in vs.
     * @param m The map for which the keys that map to v are returned.
     * @param vs The set of values for which the keys in m are returned.
     * @return A set of keys from m mapped to the values in vs.
     */
    public static <K, V> HashSet<K> getKeys(Map<K, V> m, Set<V> vs) {
        HashSet<K> r = new HashSet<>();
        vs.parallelStream().forEach(v -> {
            r.addAll(getKeys(m, v));
        });
//        Iterator<V> ite = vs.iterator();
//        while (ite.hasNext()) {
//            V v = ite.next();
//            r.addAll(getKeys(m, v));
//        }
        return r;
    }

    /**
     *
     * @param min Min
     * @param w Interval width
     * @param map Map
     * @param mc MathContext
     * @return {@code Object[]} r of length 3 where:
     * <ul>
     * <li>r[0] = counts</li>
     * <li>r[1] = labels</li>
     * <li>r[2] = mins</li>
     * </ul>
     */
    public static Object[] getIntervalCountsLabelsMins(BigDecimal min,
            BigDecimal w, TreeMap<?, BigDecimal> map, MathContext mc) {
        Object[] r = new Object[3];
        TreeMap<Integer, Integer> counts = new TreeMap<>();
        TreeMap<Integer, String> labels = new TreeMap<>();
        TreeMap<Integer, BigDecimal> mins = new TreeMap<>();
        Iterator<BigDecimal> ite = map.values().iterator();
        while (ite.hasNext()) {
            BigDecimal v = ite.next();
            int interval;
            if (w.compareTo(BigDecimal.ZERO) == 0) {
                interval = 0;
            } else {
                interval = getInterval(min, w, v, mc);
            }
            //addToTreeMapIntegerInteger(counts, interval, 1);
            addToMapInteger(counts, interval, 1);
            if (!labels.containsKey(interval)) {
                BigDecimal imin = getIntervalMin(min, w, interval);
                BigDecimal intervalMax = getIntervalMax(imin, w);
                labels.put(interval, "" + imin + " - " + intervalMax);
                mins.put(interval, imin);
            }
        }
        r[0] = counts;
        r[1] = labels;
        r[2] = mins;
        return r;
    }

    /**
     * @param min Minimum
     * @param w Interval width
     * @param interval Interval
     * @return {@code min.add(new BigDecimal(interval).multiply(w))}
     */
    public static BigDecimal getIntervalMin(BigDecimal min, BigDecimal w,
            int interval) {
        return min.add(new BigDecimal(interval).multiply(w));
    }

    /**
     * @param min Minimum
     * @param w Interval width
     * @return {@code min.add(w)}
     */
    public static BigDecimal getIntervalMax(BigDecimal min, BigDecimal w) {
        return min.add(w);
    }

    /**
     * @param min Minimum
     * @param w Interval width
     * @param v Value
     * @param mc MathContext
     * @return {@code (v.subtract(min)).divide(w, mc).intValue()}
     */
    public static int getInterval(BigDecimal min, BigDecimal w, BigDecimal v,
            MathContext mc) {
        return (v.subtract(min)).divide(w, mc).intValue();
    }

    /**
     * @param <K> A generic key.
     * @param m Map
     * @return BigDecimal[] with the minimum and maximum values in m.
     */
    public static <K> BigDecimal[] getMinMaxBigDecimal(Map<K, BigDecimal> m) {
        BigDecimal[] r = new BigDecimal[2];
        Iterator<BigDecimal> ite = m.values().iterator();
        BigDecimal v = ite.next();
        r[0] = v;
        r[1] = v;
        while (ite.hasNext()) {
            v = ite.next();
            r[0] = r[0].min(v);
            r[1] = r[1].max(v);
        }
        return r;
    }

    /**
     * @param <K> A generic key.
     * @param m Map
     * @return int[] r containing the minimum and maximum values in m.
     */
    public static <K> int[] getMinMaxInteger(Map<K, Integer> m) {
        int[] r = new int[2];
        Iterator<Integer> ite = m.values().iterator();
        int v = ite.next();
        r[0] = v;
        r[1] = v;
        while (ite.hasNext()) {
            r[0] = Math.min(r[0], v);
            r[1] = Math.max(r[1], v);
        }
        return r;
    }

    /**
     * Get the union of {@code s0} and {@code s1}. Use
     * {@link #getUnion(java.util.Set, java.util.Set)} instead.
     *
     * @param s0 Set
     * @param s1 Set
     * @return a new {@code HashSet<Integer>} which is the union of elements in
     * {@code s0} and {@code s1}.
     */
    @Deprecated
    public static HashSet<Integer> getCompleteKeySet_HashSet(
            Set<Integer> s0, Set<Integer> s1) {
        HashSet<Integer> r = new HashSet<>();
        r.addAll(s0);
        r.addAll(s1);
        return r;
    }

    /**
     * Get the union of {@code s0} and {@code s1} as a
     * {@link java.util.HashSet}.
     *
     * @param <K> The type of thing in the sets to union and in the result
     * returned.
     * @param s0 Set
     * @param s1 Set
     * @return a new {@link java.util.HashSet} which contains all the unique
     * elements in {@code s0} and {@code s1}.
     */
    public static <K> HashSet<K> getUnion(Set<K> s0, Set<K> s1) {
        HashSet<K> r = new HashSet<>();
        r.addAll(s0);
        r.addAll(s1);
        return r;
    }

    /**
     * If m contains the key k, then v is added to the HashSet. Otherwise a new
     * HashSet is created and added to m using the key k and v is added to the
     * HashSet.
     *
     * @param <K> Key
     * @param <V> Value
     * @param m Map
     * @param k key
     * @param v value
     */
    public static <K, V> void addToMap(Map<K, Set<V>> m, K k, V v) {
        Set<V> s;
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
    public static <K, K2, V> void addToMap(Map<K, Map<K2, V>> m, K k, K2 k2,
            V v) {
        Map<K2, V> m2;
        if (m.containsKey(k)) {
            m2 = m.get(k);
        } else {
            m2 = new HashMap<>();
            m.put(k, m2);
        }
        m2.put(k2, v);
    }

    /**
     * Adds to a mapped number.If m does not already contain the key k then i is
     * mapped to k. Otherwise the value for k is obtained from m and i is added
     * to it using {@link Generic_Math#add(java.lang.Number, java.lang.Number)}.
     * This may result in infinite values being added to m or
     * ArithmeticExceptions being thrown all depending on the result of any
     * additions as calculated via
     * {@link Generic_Math#add(java.lang.Number, java.lang.Number)}.
     *
     * @param <K> Key
     * @param <V> Number
     * @param m The map that is to be added to.
     * @param k The key which value is added to or initialised.
     * @param v The amount to be added to the map.
     */
    public static <K, V extends Number> void addToCount(Map<K, V> m, K k, V v) {
        if (!m.containsKey(k)) {
            m.put(k, v);
        } else {
            m.put(k, Generic_Math.add(m.get(k), v));
        }
    }

    /**
     * Add {@code v} to the value of {@code l} at position {@code p}.
     *
     * @param <N> The Number to add.
     * @param l The list to add to.
     * @param pos The position in the list to add to.
     * @param v The value to add to the existing value in {@code l} at position
     * {@code p}.
     */
    public static <N extends Number> void addToList(List<N> l, int pos, N v) {
        N v0 = l.get(pos);
        l.remove(pos);
        l.add(pos, Generic_Math.add(v, v0));
    }

    /**
     * Adds v to the ArrayList in m indexed by k if the last element of m is not
     * already the value v. If m does not already contain k then a new ArrayList
     * of type V is created with v added to it and this is added to the map
     * using the key k.
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
     * Adds v to the value of m corresponding with k.If there is no such k in m
     * or the value m.get(k) is null then v is added to m addressed by k. This
     * does not check if the resulting value overflows. If this might happen
     * then perhaps use either:
     * <ul>
     * <li>{@link #addToMap(java.util.Map, java.lang.Object, java.lang.Object)}
     * instead which would in such a case throw an ArithmeticException.that
     * case.</li>
     * <li>{@link #addToMapBigInteger(java.util.Map, java.lang.Object, java.math.BigInteger)}
     * instead as BigIntegers do not overflow.</li>
     * </ul>
     *
     * @param <K> Key
     * @param m TreeMap
     * @param k key
     * @param v value
     * @return The resulting value in m mapped by k.
     * @deprecated Use {@link #addToCount(java.util.Map, java.lang.Object, java.lang.Number)}
     */
    @Deprecated
    public static <K> Integer addToMapInteger(Map<K, Integer> m, K k,
            Integer v) {
        Integer v0 = m.get(k);
        int r;
        if (v0 != null) {
            r = v0 + v;
        } else {
            r = v;
        }
        m.put(k, r);
        return r;
    }

    /**
     * This will add the values in uf to the values in u for any existing keys.
     * Where keys in u do not exist, the the numerical value of these in uf are
     * put in u. This may result in infinite numbers being values in u (where
     * the resulting addition is beyond the bounds of the type of V1). Existing
     * keys in u that are mapped to null values are removed. NaN values may also
     * be added to u if infinite values added are the opposite infinities.
     * ArithmeticExceptions might also be throws if NaN type values are
     * attempted to be added to types that cannot represent NaN.
     *
     * @param <K> The types of key in u and f.
     * @param <V1> The type of Number values in u.
     * @param <V2> The type of Number values in uf.
     * @param u The map to updated by adding to the values from uf.
     * @param uf The map to update u from by adding values.
     */
    public static <K, V1 extends Number, V2 extends Number> void addToCount2(
            Map<K, V1> u, Map<K, V2> uf) {
        if (uf != null) {
            uf.entrySet().forEach((entry) -> {
                K key = entry.getKey();
                V2 v2 = entry.getValue();
                V1 v1 = u.get(key);
                if (v1 != null) {
                    V1 v = Generic_Math.add2(v1, v2);
                    if (v != null) {
                        u.put(key, v);
                    }
                } else {
                    u.remove(key);
                }
            });
        }
    }

    /**
     * @param <K> The key type.
     * @param mapToAddTo The map to add to.
     * @param mapToAdd The mappings to add.
     * @deprecated Use {@link #addToCount(java.util.Map, java.util.Map)}
     */
    @Deprecated
    public static <K> void addToMapInteger(Map<K, Integer> mapToAddTo,
            Map<K, Integer> mapToAdd) {
        if (mapToAdd != null) {
            mapToAdd.entrySet().forEach((entry) -> {
                K k = entry.getKey();
                Integer v = entry.getValue();
                Integer v0 = mapToAddTo.get(k);
                if (v0 != null) {
                    mapToAddTo.put(k, v0 + v);
                } else {
                    mapToAddTo.put(k, v);
                }
            });
        }
    }

    /**
     * Adds v to the value of m corresponding with k.If there is no such k in m
     * or the value m.get(k) is null then v is added to m addressed by k. This
     * does not check if the resulting value overflows. If this might happen
     * then either check or instead use either:
     * <ul>
     * <li>{@link #addToMap(java.util.Map, java.lang.Object, java.lang.Object)}
     * - which would in such a case throw an ArithmeticException in the case of
     * a numerical overflow.</li>
     * <li>{@link #addToMapBigInteger(java.util.Map, java.lang.Object, java.math.BigInteger)}
     * - as BigIntegers do not overflow.</li>
     * </ul>
     *
     * @param <K> Key
     * @param m TreeMap
     * @param k key
     * @param v value
     * @return The resulting value in m mapped by k.
     * @deprecated Use {@link #addToCount(java.util.Map, java.lang.Object, java.lang.Number)}
     */
    @Deprecated
    public static <K> long addToMapLong(Map<K, Long> m, K k, Long v) {
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
     * Sets the value in map to the max of map.get(key) and value.
     *
     * @param <K> A generic key.
     * @param m Map
     * @param k key
     * @param v value
     */
    public static <K> void setMaxValueInteger(Map<K, Integer> m, K k,
            Integer v) {
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
     * @param <K> Generic key.
     * @param m Map
     * @param k key
     * @param v value
     */
    public static <K> void setMinValueInteger(Map<K, Integer> m, K k,
            Integer v) {
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
     * or the value m.get(k) is null then v is added to m addressed by k.
     *
     * @param <K> Key
     * @param m TreeMap
     * @param k key
     * @param v value
     * @return The resulting value in m mapped by k.
     * @deprecated use {@link #addToCount(java.util.Map, java.lang.Object, java.lang.Number)}
     */
    @Deprecated
    public static <K> BigInteger addToMapBigInteger(Map<K, BigInteger> m,
            K k, BigInteger v) {
        BigInteger v0 = m.get(k);
        BigInteger r;
        if (v0 != null) {
            r = v0.add(v);
        } else {
            r = v;
        }
        m.put(k, r);
        return r;
    }

    /**
     * Adds v to the value of m corresponding with k. If there is no such k in m
     * or the value m.get(k) is null then v is added to m addressed by k.
     *
     * @param <K> Key
     * @param m TreeMap
     * @param k key
     * @param v value
     * @return The resulting value in m mapped by k.
     * @deprecated use {@link #addToCount(java.util.Map, java.lang.Object, java.lang.Number)}
     */
    @Deprecated
    public static <K> BigDecimal addToMapBigDecimal(Map<K, BigDecimal> m,
            K k, BigDecimal v) {
        BigDecimal v0 = m.get(k);
        BigDecimal r;
        if (v0 != null) {
            r = v0.add(v);
        } else {
            r = v;
        }
        m.put(k, r);
        return r;
    }

    /**
     * Count: all values in {@code s0} and {@code s1}; values in {@code s1} that
     * are not in {@code s0}; and values in {@code s0} that are not in
     * {@code s1}.
     *
     * @param <T> The type.
     * @param s0 Set
     * @param s1 Set
     * @return long[3] r where:
     * <ul>
     * <li>r[0] = Count of how many values are in both {@code s0} and
     * {@code s1}</li>
     * <li>r[1] = Count of how many values are in {@code s1}, but not in
     * {@code s0}</li>
     * <li>r[2] = Count of how many values are in {@code s0}, but not in
     * {@code s1}</li>
     * </ul>
     */
    public static <T> long[] getCounts(Set<T> s0, Set<T> s1) {
        long[] r = new long[3];
        r[0] = 0;
        r[1] = 0;
        r[2] = 0;
        Iterator<T> ite = s1.iterator();
        while (ite.hasNext()) {
            if (s0.contains(ite.next())) {
                r[0]++;
            } else {
                r[1]++;
            }
        }
        ite = s0.iterator();
        while (ite.hasNext()) {
            if (!s1.contains(ite.next())) {
                r[2]++;
            }
        }
        return r;
    }

    /**
     * For all values in set1 we count how many values are in set0, and deduce
     * how many are not.Also we check how many values that are in set0 that are
     * not in set1.
     *
     * @param <T> Type
     * @param s0 HashSet
     * @param s1 HashSet
     * @return Object[2] result {@code
     * Object[0] = union set view of elements in both set0 and set1
     * Object[1] = counts
     * counts[0] = Count of how many values are in both set 0 and set 1;
     * counts[1] = Count of how many values are in set 1, but not in set 0;
     * counts[2] = Count of how many values are in set 0, but not in set 1;
     * }
     */
    public static <T> Object[] getUnionAndCounts(HashSet<T> s0, HashSet<T> s1) {
        Object[] r = new Object[2];
        HashSet<T> union = new HashSet<>();
        union.addAll(s1);
        union.retainAll(s0);
        long[] counts = new long[3];
        int unionSize = union.size();
        counts[0] = unionSize;
        counts[1] = s1.size() - unionSize;
        counts[2] = s0.size() - unionSize;
        r[0] = union;
        r[1] = counts;
        return r;
    }

    /**
     * For all values in s1 we count how many values are in s0, and deduce how
     * many are not.Also we check how many values that are in s0 that are not in
     * s1.
     *
     * @param <T> Type
     * @param s0 HashSet
     * @param s1 HashSet
     * @return Object[2] result {@code
     * Object[0] = union set view of elements in both set0 and set1
     * Object[1] = counts
     * counts[0] = Count of how many values are in both s0 and s1;
     * counts[1] = Count of how many values are in s1, but not in s0;
     * counts[2] = Count of how many values are in s0, but not in s1;
     * }
     */
    public static <T> Object[] getUnionAndUniques(HashSet<T> s0,
            HashSet<T> s1) {
        Object[] r = new Object[3];
        HashSet<T> union = new HashSet<>();
        union.addAll(s1);
        union.retainAll(s0);
        HashSet<T> set1unique = new HashSet<>();
        set1unique.addAll(s1);
        set1unique.removeAll(s0);
        HashSet<T> set0unique = new HashSet<>();
        set0unique.addAll(s0);
        set0unique.removeAll(s1);
        r[0] = union;
        r[1] = set1unique;
        r[2] = set0unique;
        return r;
    }

    public static <K> TreeMap<K, BigInteger> deepCopyTreeMapBigInteger(
            TreeMap<K, BigInteger> m) {
        TreeMap<K, BigInteger> r = new TreeMap<>();
        Iterator<K> ite = m.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            BigInteger vToCopy = m.get(k);
            BigInteger vCopy = new BigInteger(vToCopy.toString());
            r.put(k, vCopy);
        }
        return r;
    }

    public static <K> HashMap<K, String> deepCopyHashMapString(
            HashMap<K, String> m) {
        HashMap<K, String> r = new HashMap<>();
        Iterator<K> ite = m.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            r.put(k, m.get(k));
        }
        return r;
    }

    public static <K> HashMap<K, Integer> deepCopyHashMapInteger(
            HashMap<K, Integer> m) {
        HashMap<K, Integer> r = new HashMap<>();
        Iterator<K> ite = m.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            r.put(k, m.get(k));
        }
        return r;
    }

    public static <K, Integer> TreeMap<K, Integer> deepCopyTreeMapInteger(
            TreeMap<K, Integer> map) {
        TreeMap<K, Integer> r = new TreeMap<>();
        Iterator<K> ite = map.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            r.put(k, map.get(k));
        }
        return r;
    }

    public static <K> TreeMap<K, BigDecimal> deepCopyTreeMapBigDecimal(
            TreeMap<K, BigDecimal> m) {
        TreeMap<K, BigDecimal> r = new TreeMap<>();
        Iterator<K> ite = m.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            BigDecimal v0 = m.get(k);
            BigDecimal v1 = new BigDecimal(v0.toString());
            r.put(k, v1);
        }
        return r;
    }

    public static <K> TreeMap<K, Long> deepCopyTreeMapLong(
            TreeMap<K, Long> m) {
        TreeMap<K, Long> r = new TreeMap<>();
        Iterator<K> ite = m.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            Long v0 = m.get(k);
            Long v1 = v0;
            r.put(k, v1);
        }
        return r;
    }

    /**
     * @param <K> The key type.
     * @param mapToAddTo The map to add to.
     * @param mapToAdd The mappings to add.
     * @deprecated Use {@link #addToCount(java.util.Map, java.util.Map)}
     */
    @Deprecated
    public static <K> void addToMapLong(Map<K, Long> mapToAddTo,
            Map<K, Long> mapToAdd) {
        Iterator<K> ite = mapToAdd.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            Long v = mapToAdd.get(k);
            if (mapToAddTo.containsKey(k)) {
                mapToAddTo.put(k, v + mapToAddTo.get(k));
            } else {
                mapToAddTo.put(k, v);
            }
        }
    }

    public static <K, V extends Number> void addToCount(Map<K, V> u,
            Map<K, V> uf) {
        Iterator<K> ite = uf.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            V v = uf.get(k);
            if (u.containsKey(k)) {
                u.put(k, Generic_Math.add(v, u.get(k)));
            } else {
                u.put(k, v);
            }
        }
    }

    /**
     * @param <K> The key type.
     * @param mapToAddTo The map to add to.
     * @param mapToAdd The mappings to add.
     * @deprecated Use {@link #addToCount(java.util.Map, java.lang.Object, java.lang.Number)}
     */
    @Deprecated
    public static <K> void addToMapBigInteger(Map<K, BigInteger> mapToAddTo,
            Map<K, BigInteger> mapToAdd) {
        Iterator<K> ite = mapToAdd.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            BigInteger v = mapToAdd.get(k);
            if (mapToAddTo.containsKey(k)) {
                mapToAddTo.put(k, v.add(mapToAddTo.get(k)));
            } else {
                mapToAddTo.put(k, v);
            }
        }
    }

    /**
     * @param <K> Any type of key that is comparable.
     * @param <V> Any type of value.
     * @param m TreeMap with ordered Keys.
     * @param k A key which will be returned if m.isEmpty().
     * @return The first key in m or k if m.isEmpty().
     */
    public static <K, V> K getFirstKey(TreeMap<K, V> m, K k) {
        if (m.isEmpty()) {
            return k;
        } else {
            return m.firstKey();
        }
    }

    /**
     * @param <K> Any type of key that is comparable.
     * @param <V> Any type of value.
     * @param m TreeMap with ordered Keys.
     * @param k A key which will be returned if m.isEmpty().
     * @return The last key in m or k if m.isEmpty().
     */
    public static <K, V> K getLastKey(TreeMap<K, V> m, K k) {
        if (m.isEmpty()) {
            return k;
        } else {
            return m.lastKey();
        }
    }

    /**
     * @param <K> A generic key for m.
     * @param m The map to find the maximum of the values in.
     * @param x Equal to the minimum value that would be returned.
     * @return The maximum of the BigDecimal values in m and x, or a copy of x
     * if m is empty.
     */
    public static <K> BigDecimal getMaxValueBigDecimal(Map<K, BigDecimal> m,
            BigDecimal x) {
        BigDecimal r = new BigDecimal(x.toString());
        Iterator<BigDecimal> ite = m.values().iterator();
        while (ite.hasNext()) {
            r = r.max(ite.next());
        }
        return r;
    }

    /**
     * @param <K> A generic key for m.
     * @param m The map to find the minimum of the values in.
     * @param x Equal to the maximum value that would be returned.
     * @return The minimum of the BigDecimal values in m and x, or a copy of x
     * if m is empty.
     */
    public static <K> BigDecimal getMinValueBigDecimal(Map<K, BigDecimal> m,
            BigDecimal x) {
        BigDecimal r = new BigDecimal(x.toString());
        Iterator<BigDecimal> ite = m.values().iterator();
        while (ite.hasNext()) {
            r = r.min(ite.next());
        }
        return r;
    }

    /**
     * @param <K> A generic key for m.
     * @param m The map to find the maximum of the values in.
     * @param x Equal to the minimum value that would be returned.
     * @return The maximum of the BigDecimal values in m and x, or a copy of x
     * if m is empty.
     */
    public static <K> BigInteger getMaxValueBigInteger(Map<K, BigInteger> m,
            BigInteger x) {
        BigInteger r = new BigInteger(x.toString());
        Iterator<BigInteger> ite = m.values().iterator();
        while (ite.hasNext()) {
            r = r.max(ite.next());
        }
        return r;
    }

    /**
     * @param <K> A generic key for m.
     * @param m The map to find the minimum of the values in.
     * @param x Equal to the maximum value that would be returned.
     * @return The minimum of the BigInteger values in m and x, or a copy of x
     * if m is empty.
     */
    public static <K> BigInteger getMinValueBigInteger(TreeMap<K, BigInteger> m,
            BigInteger x) {
        BigInteger r = new BigInteger(x.toString());
        Iterator<BigInteger> ite = m.values().iterator();
        while (ite.hasNext()) {
            r = r.max(ite.next());
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
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
            Map<K, V> m) {
        Map<K, V> r = new LinkedHashMap<>();
        List<Map.Entry<K, V>> list = new LinkedList<>(m.entrySet());
        Collections.sort(list, (Map.Entry<K, V> o1, Map.Entry<K, V> o2)
                -> (o1.getValue()).compareTo(o2.getValue()));
        list.forEach((entry) -> {
            r.put(entry.getKey(), entry.getValue());
        });
        return r;
    }

    /**
     * For getting the maximum value in a collection of BigDecimals.
     *
     * @param c The collection of BigDecimals.
     * @return the maximum value in {@code c}
     */
    public static BigDecimal getMax(Collection<BigDecimal> c) {
        Optional<BigDecimal> o;
        o = c.stream().parallel().max(BigDecimal::compareTo);
        return o.get();
    }

    /**
     * For getting the maximum value in a collection of BigDecimals.
     *
     * @param c The collection of BigDecimals.
     * @return the maximum value in {@code c}
     */
    public static BigDecimal getMin(Collection<BigDecimal> c) {
        Optional<BigDecimal> o;
        o = c.stream().parallel().min(BigDecimal::compareTo);
        return o.get();
    }

    /**
     * A test if b is contained in c.
     *
     * @param c The collection tested.
     * @param b The value sought.
     * @return True iff b is in c.
     */
    public static boolean containsValue(Collection<BigDecimal> c, BigDecimal b) {
        return c.stream().parallel().anyMatch(v -> v.equals(b));
    }

    /**
     * Calculates and returns the sum of the sizes of all the sets in {@code m}
     * as an int.
     *
     * @param <K> Keys
     * @param <T> Types
     * @param m Map
     * @return The sum of the sizes of all the sets in {@code m}.
     */
    public static <K, T> int getCountInt(Map<K, Set<T>> m) {
        int r = 0;
        Iterator<Set<T>> ite = m.values().iterator();
        while (ite.hasNext()) {
            r += ite.next().size();
        }
        return r;
    }

    /**
     * Return the first key in the map which contains the set with {@code t} in.
     *
     * @param <K> The key type.
     * @param <T> The value type.
     * @param map The map.
     * @param t The value to find the key for.
     * @return The first key found.
     */
    public static <K, T> K getKey(Map<K, Set<T>> map, T t) {
        Set<Map.Entry<K, Set<T>>> mapEntrySet = map.entrySet();
        Iterator<Map.Entry<K, Set<T>>> ite = mapEntrySet.iterator();
        while (ite.hasNext()) {
            Map.Entry<K, Set<T>> entry = ite.next();
            K k = entry.getKey();
            if (entry.getValue().contains(t)) {
                return k;
            }
        }
        return null;
    }
}
