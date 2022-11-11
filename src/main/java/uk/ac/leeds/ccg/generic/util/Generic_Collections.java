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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * For processing and manipulating collections including Lists, Arrays, Sets and
 * Maps.
 *
 * @author Andy Turner
 * @version 1.1
 */
public class Generic_Collections {

    /**
     * Create a new instance.
     */
    public Generic_Collections() {
    }

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
     * @param <K> A generic key type.
     * @param <V> A generic value type.
     * @param m Map
     * @return BigDecimal[] with the minimum and maximum values in m.
     */
    public static <K, V extends Comparable<V>> ArrayList<V> getMinMax(Map<K, V> m) {
        ArrayList<V> r = new ArrayList<>();
        Iterator<V> ite = m.values().iterator();
        if (ite.hasNext()) {
            V v = ite.next();
            r.set(0, v);
            r.set(1, v);
            while (ite.hasNext()) {
                v = ite.next();
                if (v.compareTo(r.get(0)) == -1) {
                    r.set(0, v);
                }
                if (v.compareTo(r.get(1)) == 1) {
                    r.set(1, v);
                }
            }
        }
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
     * For each key in putFrom if the key is not in putTo, then the value (the
     * map) in putFrom is put into putTo. If there are maps in both putFrom and
     * putTo for a given key then these maps are gone through putting all the
     * map keys and values from putFrom into putTo.
     *
     * @param <K> Key
     * @param <K2> Key2
     * @param <V> Value
     * @param m Map
     * @param k key
     * @param k2 key2
     * @param v value
     */
//    public static <K, K2, V> void putInMap(Map<K, Map<K2, V>> putTo,
//            Map<K, Map<K2, V>> putFrom) {
//        //putFrom.keySet().parallelStream().forEach(k -> {
//        putFrom.keySet().forEach(k -> {
//            if (putTo.containsKey(k)) {
//                putTo.get(k).putAll(putFrom.get(k));
//            } else {
//                putTo.put(k, putFrom.get(k));
//            }
//        });
//    }
    /**
     * For each key in putFrom if the key is not in putTo, then the value (the
     * map) in putFrom is put into putTo. If there are maps in both putFrom and
     * putTo for a given key then these maps are gone through putting all the
     * map keys and values from putFrom into putTo.
     *
     * @param <K> Key
     * @param <K2> Key2
     * @param <V2> Value2
     * @param <V> Value
     * @param putTo Map to put into.
     * @param putFrom Map to put from.
     */
    public static <K, K2, V2, V extends Map<K2, V2>> void putInMap(Map<K, V> putTo,
            Map<K, V> putFrom) {
        // The parallelStream operation seems to have a problem here!
        //putFrom.keySet().parallelStream().forEach(k -> {
        putFrom.keySet().forEach(k -> {
            if (putTo.containsKey(k)) {
                putTo.get(k).putAll(putFrom.get(k));
            } else {
                putTo.put(k, putFrom.get(k));
            }
        });
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
     * Adds the value to the count using key t.
     *
     * @param <T> The type of thing counted.
     * @param count The map containing the counts.
     * @param t The thing to count.
     * @param value The number of those things being added to count (must not be
     * null).
     */
    public <T> void addToCount(Map<T, Integer> count, T t, Integer value) {
        Integer c = count.get(t);
        if (c == null) {
            count.put(t, value);
        } else {
            count.put(t, value + c);
        }
    }

    /**
     * Adds the value to the count using key t.
     *
     * @param <T> The type of thing counted.
     * @param count The map containing the counts.
     * @param t The thing to count.
     * @param value The number of those things being added to count (must not be
     * null).
     */
    public <T> void addToCount(Map<T, Long> count, T t, Long value) {
        Long c = count.get(t);
        if (c == null) {
            count.put(t, value);
        } else {
            count.put(t, value + c);
        }
    }

    /**
     * Adds the value to the count using key t.
     *
     * @param <T> The type of thing counted.
     * @param count The map containing the counts.
     * @param t The thing to count.
     * @param value The number of those things being added to count (must not be
     * null).
     */
    public <T> void addToCount(Map<T, BigInteger> count, T t, BigInteger value) {
        BigInteger c = count.get(t);
        if (c == null) {
            count.put(t, value);
        } else {
            count.put(t, value.add(c));
        }
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

    /**
     * @param <K> Key.
     * @param m Map
     * @return A copy of m with all keys the same (not duplicated), but with all
     * values duplicated.
     */
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
     * For getting the maximum in a collection.
     *
     * @param <V> Value type.
     * @param c The collection.
     * @return The maximum in {@code c}
     */
    public static <V extends Comparable<V>> V getMax(Collection<V> c) {
        return c.stream().parallel().max(V::compareTo).get();
    }

    /**
     * For getting the minimum in a collection.
     *
     * @param <V> Value type.
     * @param c The collection.
     * @return The minimum in {@code c}
     */
    public static <V extends Comparable<V>> V getMin(Collection<V> c) {
        return c.stream().parallel().min(V::compareTo).get();
    }

    /**
     * A test if b is contained in c.
     *
     * @param c The collection tested.
     * @param b The value sought.
     * @return True iff b is in c.
     */
    public static <V> boolean containsValue(Collection<V> c, V b) {
        return c.stream().parallel().anyMatch(v -> v.equals(b));
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

    /**
     * Concatenate arrays of the same type.
     *
     * @param <T> The array type
     * @param first The first array.
     * @param others The other arrays.
     * @return The concatenated array.
     */
    public static <T> T[] concatenate(T[] first, T[]... others) {
        // Calculate the length
        long l = first.length;
        for (T[] array : others) {
            l += array.length;
        }
        if (l > Integer.MAX_VALUE) {
            throw new RuntimeException("Unable to concatenate arrays as the "
                    + "total length excedes " + Integer.MAX_VALUE);
        }
        int li = (int) l;
        T[] r = Arrays.copyOf(first, li);
        int offset = first.length;
        for (T[] array : others) {
            System.arraycopy(array, 0, r, offset, array.length);
            offset += array.length;
        }
        return r;
    }

    /**
     * Concatenate byte arrays.
     *
     * @param first The first array.
     * @param others The other arrays.
     * @return The concatenated array.
     */
    public static byte[] concatenate(byte[] first, byte[]... others) {
        // Calculate the length
        long l = first.length;
        for (byte[] array : others) {
            l += array.length;
        }
        if (l > Integer.MAX_VALUE) {
            throw new RuntimeException("Unable to concatenate arrays as the "
                    + "total length excedes " + Integer.MAX_VALUE);
        }
        int li = (int) l;
        byte[] r = Arrays.copyOf(first, li);
        int offset = first.length;
        for (byte[] array : others) {
            System.arraycopy(array, 0, r, offset, array.length);
            offset += array.length;
        }
        return r;
    }
}
