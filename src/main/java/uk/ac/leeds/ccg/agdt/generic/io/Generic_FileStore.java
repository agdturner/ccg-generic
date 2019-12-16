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
package uk.ac.leeds.ccg.agdt.generic.io;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Strings;
import uk.ac.leeds.ccg.agdt.generic.math.Generic_Math;
import uk.ac.leeds.ccg.agdt.generic.util.Generic_Collections;

/**
 * For storing files on disk in file store - a form of data base where each file
 * is stored in a leaf directory. Leaf directories are found at level 0 of the
 * file store. The 1st leaf directory has the name 0, the 2nd leaf directory has
 * the name 1, the nth leaf directory has the name n where n is a positive
 * integer . A file store is comprised of a base directory in which there is a
 * root directory. The root directory indicates how many files are stored in the
 * file store using a range given in the directory name. The minimum of the
 * range is 0 and the maximum is a positive integer number. These two numbers
 * are separated with by {@link #SEP} e.g. "0_99". The root directory will
 * contain one or more subdirectories named in a similar style to the root
 * directory e.g. "0_9". The maximum number will be less than or equal to that
 * of the root directory. By comparing the range of numbers in the names of
 * directories in the root directory with the range of numbers in the names of
 * and subdirectory in the root directory it is possible to discern the range
 * for the file store. The range is a parameter that can be set when
 * initialising a file store. It controls how many subdirectories there can be
 * at each level, and ultimately this controls how many levels of directories
 * there are in the file store which is all dependent on the number of files
 * stored in the file store.
 *
 * Files are to be stored in the leaf directories. Each directory is given a
 * standardised name such that it is easy to find and infer the path to the leaf
 * directories.
 *
 * If range was set to 10, there would be at most 10 subdirectories in each
 * level of the file store.
 *
 * File stores are initialised with 3 levels and dynamically grow to store more
 * files. For range = 10 the initial tree can be represented as follows:
 *
 * {@code
 * Level
 * 2        - 1           - root
 *
 * 0        - 0_9         - 0_99
 * }
 *
 * For range = 10 and n = 100001 the tree can be represented as follows:
 *
 * {@code
 * Level
 * 6      - 5             - 4             - 3             - 2             - 1               - root
 *
 * 0      - 0_9           - 0_99          - 0_999         - 0_9999        - 0_99999        - 0_999999
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 7
 * 8
 * 9
 * 10     - 10_19
 * 11
 * 12
 * ...
 * 19
 * 20     - 20_29
 * ...
 * ...
 * 99     - 90_99
 * 100    - 100_109       - 100_199
 * ...
 * ...
 * ...
 * 999    - 990_999       - 900_999
 * 1000   - 1000_1009     - 1000_1099     - 1000_1999
 * ...
 * ...
 * ...
 * ...
 * 9999   - 9990_9999     - 9900_9999     - 9000_9999
 * 10000  - 10000_10009   - 10000_10099   - 10000_10999   - 10000_19999
 * ...
 * ...
 * ...
 * ...
 * ...
 * 99999  - 99990_99999   - 99900_99999   - 99000_99999   - 90000_99999
 * 100000 - 100000_100009 - 100000_100099 - 100000_100999 - 100000_109999 - 100000_199999
 * 100001
 * }
 *
 * File stores are used for logging and may be used to store other outputs from
 * different runs of a program. They can also be used to organise caches of data
 * from a running program to help with memory management.
 *
 * Although such a file store can store many files, there are limits depending
 * on the range value set. The theoretical limit is close to Long.MAX_VALUE /
 * range. But there can be no more than Integer.MAX_VALUE levels. Perhaps a
 * bigger restriction is the size of the storage element that holds the
 * directories and files indexed by the file store.
 *
 * There is scope for developing something very similar that can store many more
 * files in the same way. This idea along with other related develop ideas are
 * outlined below:
 * <ul>
 * <li>Refactor or develop additional code in order to store more than
 * Long.MAX_VALUE number of files where the identifiers of these files are
 * represented as {@link BigInteger} numbers. This might leverage some other
 * libraries which depend on the Generic library which contains this class - in
 * particular - Math - which provides utility for BigInteger and BigDecimal
 * arithmetic which might be useful in such a development. If the product of
 * this development were a new class, then this class could potentially be
 * stored in the Generic library although this might result in a cyclical
 * dependency albeit one using different versions (a form of recursive
 * enrichment). It might though be simpler to release this new class in another
 * library.</li>
 * <li>Add functionality for changing the range of a Generic_FileStore.</li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_FileStore implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Separates the smaller and larger numbers for the range of identifiers
     * stored in any directory.
     */
    protected static final String SEP = Generic_Strings.symbol_underscore;

    /**
     * For storing the base directory path of the file store.
     */
    protected final Generic_Path baseDir;

    /**
     * For storing the root directory path of the file store. This should be a
     * directory in baseDir which is otherwise empty.
     */
    protected Generic_Path root;

    /**
     * The name of the file store. Used to initialise baseDir.
     */
    protected final String name;

    /**
     * range stored as a long.
     */
    protected final long rangeL;

    /**
     * range stored as a BigInteger.
     */
    protected final BigInteger rangeBI;

    /**
     * The range for the next level.
     */
    protected long nextRange;

    /**
     * Stores the number of levels in the file store. For a new store, initially
     * this is 2 and increases by 1 each time the file store grows deeper. The
     * maximum number of files that can be stored in 2 levels is range * range.
     * With each subsequent level this number is increased by a factor of range.
     * With n levels and range r
     * {@code BigDecimal.valueOf(r).pow(n).longValueExact} files can be stored.
     * So, with range = 100 and 3 levels some 10 thousand files can be stored.
     * With range = 100 and 7 level some a million million files can be stored.
     * To calculate how many levels will be needed for a given number of files n
     * and a range r use {@link #getLevels(long, long)}.
     */
    protected int levels;

    /**
     * For storing the range for each level above the root level. This grows
     * with {@link #levels} as the file store grows. ranges[levels - 1] is
     * rangeL, ranges[levels -2] is rangeL * rangeL, etc...
     */
    protected ArrayList<Long> ranges;

    /**
     * For storing the number of directories at each level from the root level
     * up to the level before the leaf level. This grows with {@link #levels} as
     * the file store grows and is modified as new directories are added at each
     * level. dirCounts[0] is a count of the number of directories at the
     * rootLevel which is always 1; dirCounts[1] is a count of the number of
     * directories at level 1; etc...
     */
    protected ArrayList<Long> dirCounts;

    /**
     * For storing the paths to the directories (at each level including the
     * root level) in which nextID is to be stored. This grows with
     * {@link #levels} as the file store grows. If the file store grows wider it
     * also must be modified. lps[levels - 1] is the absolute path to the root
     * directory; lps[levels - 2] is the path to the directory in lps[levels -1]
     * of the current highest leaf; each other lps[levels -2] is either: the
     * path to the directory containing the current highest leaf directory; or,
     * it is another subdirectory in lps[levels - 1] that contains it; etc...
     */
    protected Generic_Path[] lps;

    /**
     * Stores the nextID. Initially set to 0.
     */
    protected long nextID;

    /**
     * Initialises a file store at {@code p} called {@code name} with 3 levels
     * allowing to store 100 files in each directory.
     *
     * @param p The path to where the file store will be initialised.
     * @param name The directory file name for the {@link #baseDir} of the file
     * store.
     * @throws IOException If encountered.
     */
    public Generic_FileStore(Path p, String name)
            throws IOException, Exception {
        this(p, name, (short) 100);
    }

    /**
     * Initialises a file store at {@code p} called {@code name} with 3 levels
     * allowing to store {@code range} number of files in each directory.
     *
     * @param p The path to where the file store will be initialised.
     * @param name The directory file name for the {@link #baseDir} of the file
     * store.
     * @param range The maximum number of directories in each level of the file
     * store.
     * @throws IOException If encountered.
     * @throws Exception If range is less than 0.
     */
    public Generic_FileStore(Path p, String name, short range)
            throws IOException, Exception {
        if (range < 0) {
            throw new Exception("Range cannot be < 0.");
        }
        baseDir = new Generic_Path(Paths.get(p.toString(), name));
        this.name = name;
        rangeL = range;
        rangeBI = BigInteger.valueOf(range);
        levels = 2;
        nextID = 0;
        lps = new Generic_Path[2];
        long l;
        //String fn;
        ranges = new ArrayList<>();
        BigInteger rBI;
        ranges.add(0, rangeBI.longValueExact());
        rBI = rangeBI.multiply(rangeBI);
        ranges.add(0, rBI.longValueExact());
        long u = 0L;
        l = rBI.subtract(BigInteger.ONE).longValueExact();
        nextRange = rBI.multiply(rangeBI).longValueExact();
        lps[0] = new Generic_Path(Paths.get(baseDir.s, getName(u, l)));
        l = rangeBI.subtract(BigInteger.ONE).longValueExact();
        lps[1] = new Generic_Path(Paths.get(lps[0].s, getName(u, l)));
        Files.createDirectories(Paths.get(lps[1].s, "0"));
        dirCounts = new ArrayList<>();
        dirCounts.add(1L);
        dirCounts.add(1L);
        root = lps[0];
    }

    /**
     * Initialises a file store at {@code p} for an existing file store.
     *
     * @param p The path of the existing file store base directory.
     * @throws IOException If encountered.
     * @throws Exception If the existing file store is problematic.
     */
    public Generic_FileStore(Path p) throws IOException, Exception {
        name = p.getFileName().toString();
        baseDir = new Generic_Path(p);
        if (!Files.isDirectory(baseDir.getPath())) {
            throw new Exception("Path " + p.toString() + " does not appear to "
                    + "be a file store as it does not contain one element that "
                    + "is a directory.");
        }
        List<Path> l = Generic_IO.getList(p);
        if (l.size() != 1) {
            throw new Exception("Path " + p.toString() + " does not appear to "
                    + "be a file store as it does not contain one element.");
        }
        root = new Generic_Path(l.get(0));
        String fn = root.getFileName().toString();
        if (!fn.contains(SEP)) {
            throw new Exception("Path " + p.toString() + " does not appear to "
                    + "be a file store as the directory it contains does not "
                    + "have " + SEP + " in it's filename.");
        }
        String[] split = fn.split(SEP);
        if (!split[0].equalsIgnoreCase("0")) {
            throw new Exception("Path " + p.toString() + " does not appear to "
                    + "be a file store as the name of the directory it contains"
                    + " does not start with \"0\".");
        }
        if (split.length != 2) {
            throw new Exception("Path " + p.toString() + " contains more than "
                    + "one \"" + SEP + "\" in the filename.");
        }
        try {
            long r;
            r = Long.valueOf(split[1]) + 1L;
            Path p2 = Generic_IO.getList(root.getPath()).get(0);
            fn = p2.getFileName().toString();
            if (!fn.contains(SEP)) {
                throw new Exception("Path " + p2.toString() + " does not have "
                        + SEP + " in it's filename.");
            }
            split = fn.split(SEP);
            if (split.length != 2) {
                throw new Exception("Path " + p2.toString() + " contains more "
                        + "than one \"" + SEP + "\" in the filename.");
            }
            long r2 = Long.valueOf(split[1]) - Long.valueOf(split[0]) + 1;
            if (r % r2 != 0) {
                throw new Exception("Invalid range difference for file store.");
            }
            rangeL = r / r2;
        } catch (NumberFormatException ex) {
            ex.printStackTrace(System.err);
            throw new Exception(ex.getMessage() + " setting rangeL.");
        }
        if (rangeL < 0 || rangeL > Short.MAX_VALUE) {
            throw new Exception("range < 0 or > Short.MAX_VALUE.");
        }
        rangeBI = BigInteger.valueOf(rangeL);
        testIntegrity();
        initLevelsAndNextID();
        ranges = getRanges(nextID, rangeL);
        initLPs();
        dirCounts = getDirCounts(nextID, rangeL);
        initNextRange();
    }

    /**
     * @return A String description of this.
     */
    @Override
    public String toString() {
        String r = "File store(baseDir=" + baseDir.s + ", root=" + root.s
                + ", name=" + name + ", range=" + rangeBI.toString()
                + ", nextRange=" + nextRange + ", levels=" + levels
                + ", ranges=(length=" + ranges.size()
                + ", ranges[0]=" + ranges.get(0);
        for (int i = 0; i < ranges.size(); i++) {
            r += ", ranges[" + i + "]=" + ranges.get(i);
        }
        r += "), dirCounts(length=" + dirCounts.size()
                + ", dirCounts[0]=" + dirCounts.get(0);
        for (int i = 0; i < dirCounts.size(); i++) {
            r += ", dirCounts[" + i + "]=" + dirCounts.get(i);
        }
        r += "), lps(length=" + lps.length
                + ", lps[0]=" + lps[0].s;
        for (int i = 0; i < lps.length; i++) {
            r += ", lps[" + i + "]=" + lps[i].s;
        }
        r += "), nextID=" + nextID + ")";
        return r;
    }

    /**
     * Initialises {@link #nextRange}.
     */
    protected final void initNextRange() {
        nextRange = BigInteger.valueOf(ranges.get(0)).multiply(rangeBI).longValueExact();
    }

    /**
     * Calculates and returns the number of levels needed for a file store with
     * range of {@code range} and {@code n} total number of files to store. If
     * the result is larger than {@link Generic_Math#SHORT_MAXVALUE} then this
     * would be too deep. In such a case then it may still be possible to store
     * all the files but only if {@code range} is increased.
     *
     * @param n the number of files
     * @param range the range of the file store
     * @return the number of levels needed for an file store with range of r and
     * n total number of files to store.
     */
    public static int getLevels(long n, long range) {
        int r = 0;
        if (n % range != 0) {
            r += 1;
        }
        while (n >= range) {
            n = n / range;
            r++;
        }
        if (r < 2) {
            r = 2;
        }
        return r;
    }

    /**
     * {@link #levels} is for storing the number of levels in the file store.
     * This is kept up to date as the file store grows. This method is a
     * convenience method for users that want to know how many levels will be
     * needed once the number of files stored reaches n. This effectively calls
     * {@link #getLevels(long, long)} defaulting range to rangeL.
     *
     * @param n The number of files.
     * @return The number of levels needed for this file store to store n total
     * number of files.
     */
    public int getLevels(long n) {
        return getLevels(n, rangeL);
    }

    /**
     * For initialising levels and nextID.
     *
     * @throws java.io.IOException If there encountered in
     * {@link #getHighestDir()}.
     */
    protected final void initLevelsAndNextID() throws IOException {
        Path p = findHighestLeaf();
        nextID = Long.valueOf(p.getFileName().toString());
        levels = p.getNameCount() - baseDir.getNameCount() - 1;
    }

    /**
     * @return {@link #levels}.
     */
    public final long getLevels() {
        return levels;
    }

    /**
     * @return {@link ranges} updated.
     */
    protected final ArrayList<Long> getRanges() {
        return ranges;
    }

    /**
     * Calculates and returns the ranges given the number of files to be stored
     * and the range. ranges[levels - 1] is range, ranges[levels -2] is range *
     * range, etc...
     *
     * @param n The number of files to be stored.
     * @param range The range.
     * @return {@link ranges} updated if levels has increased since it was last
     * updated.
     * @throws java.lang.Exception If n is too big for the range. If this is the
     * case then maybe try to specify a bigger range or look to implementing
     * something that can handle larger numbers of files as suggested in the
     * class comment documentation.
     */
    public static final ArrayList<Long> getRanges(long n, long range)
            throws Exception {
        int lvls = getLevels(n, range);
        if (lvls > Integer.MAX_VALUE) {
            throw new Exception("n too big for the range");
        }
        ArrayList<Long> r = new ArrayList<>();
        for (int l = 0; l < lvls; l++) {
            r.add(0, BigInteger.valueOf(range).pow(l + 1).longValueExact());
        }
        return r;
    }

    /**
     * Calculates the number of directories needed at each level to store n
     * files with range r. This first calculates the number of levels and the
     * ranges for each level.
     *
     * @param n The number of files to store.
     * @param range The range.
     * @return The number of directories needed at each level to store n files
     * with range range. The first element is the list is the number of
     * directories in the root directory.
     * @throws java.lang.Exception If n is too big for the range. If this is the
     * case then maybe try to specify a bigger range or look to implementing
     * something that can handle larger numbers of files as suggested in the
     * class comment documentation.
     */
    public static final ArrayList<Long> getDirCounts(long n, long range)
            throws Exception {
        ArrayList<Long> dirCounts = new ArrayList<>();
        long lvls = getLevels(n, range);
        ArrayList<Long> rngs = getRanges(n, range);
        /**
         * Can safely cast here as getLevels(long, long) already throws an
         * Exception if lvls is greater than Integer.MAX_VALUE
         */
        int li = (int) lvls;
        ArrayList<Integer> dirIndexes = getDirIndexes(n, li, rngs);
        for (int i = 0; i < dirIndexes.size(); i++) {
            dirCounts.add((long) dirIndexes.get(i) + 1L);
        }
        return dirCounts;
    }

    /**
     * Gets the dir indexes for the directories at each level greater than zero
     * for the element identified by id.
     *
     * @param id The identifier for which the dir indexes are returned.
     * @param levels The number of levels.
     * @param ranges The ranges at each level.
     * @return The dir indexes for the directories at each level greater than
     * zero for the element identified by id.
     */
    protected static ArrayList<Integer> getDirIndexes(long id, int levels,
            ArrayList<Long> ranges) {
        ArrayList<Integer> r = new ArrayList<>();
        for (int lvl = levels - 1; lvl >= 0; lvl--) {
            long id2 = id;
            long range = ranges.get(lvl);
            int c = 0;
            id2 -= range;
            while (id2 > 0) {
                id2 -= range;
                c++;
            }
            r.add(0, c);
        }
        return r;
    }

    /**
     * Gets the dir indexes for the directories at each level in the current
     * storage of the element identified by id.
     *
     * This may result in a runtime exception if n is too big for the range. If
     * this is the case then maybe try to specify a bigger range or look to
     * implementing something that can handle larger numbers of files as
     * suggested in the class comment documentation.
     *
     * @param id The identifier of the element to get the dirCounts for.
     * @return The dir counts at each level for the current storage of the
     * element identified by id.
     */
    protected final ArrayList<Integer> getDirIndexes(long id) {
        int li = levels;
        return getDirIndexes(id, li, ranges);
    }

    /**
     * @return a copy of {@link #lps}[0] this is the current highest leaf
     * directory of the file store.
     */
    public Generic_Path getPathNext() {
        return new Generic_Path(lps[0]);
    }
    
    

    /**
     * Calculates and returns the current path of the directory for storing the
     * element identified by id. For efficiency, this does not check if id is
     * less than or equal to {@link #nextID}, but it should be and if not then
     * what is returned might not be useful.
     *
     * @param id The identifier of the element for which the current path is
     * wanted.
     * @return The current path of the directory for storing the element
     * identified by id.
     */
    public Path getPath(long id) {
        Path[] paths = new Path[levels - 1];
        ArrayList<Integer> dirIndexes = getDirIndexes(id);
        Path p = root;
        for (int lvl = levels - 2; lvl >= 0; lvl--) {
            long range = ranges.get(lvl + 1);
            long l = range * dirIndexes.get(lvl);
            long u = l + range - 1L;
            paths[lvl] = Paths.get(p.toString(), getName(l, u));
            p = paths[lvl];
        }
        p = Paths.get(p.toString(), Long.toString(id));
        return p;
    }

    protected final String getName(long l, long u) {
        return Long.toString(l) + SEP + Long.toString(u);
    }

    /**
     * Initialises lps. This should be called when the directory structure grows
     * deeper. As the directory grows wider the appropriate paths in lps want
     * updating.
     */
    protected final void initLPs() {
        lps = new Generic_Path[levels];
        lps[0] = root;
        ArrayList<Integer> dirIndexes = getDirIndexes(nextID, levels, ranges);
        for (int lvl = 1; lvl < levels; lvl++) {
            long range = ranges.get(lvl);
            long l = range * dirIndexes.get(lvl);
            long u = l + range - 1L;
            lps[lvl] = new Generic_Path(Paths.get(lps[lvl - 1].s, getName(l, u)));
        }
    }

    /**
     * Serializes and writes o to
     * {@code Paths.get(getHighestLeaf().toString(), name)};
     *
     * @param o The Object to be serialised and written out.
     * @throws IOException If encountered.
     */
    public void add(Object o) throws IOException {
        Path p = Paths.get(getHighestLeaf().toString(), name);
        Generic_IO.writeObject(o, p);
    }

    /**
     * Deserializes an Object from file at
     * {@code Paths.get(getPath(id).toString(), name)}.
     *
     * @param id The identifier for the Object to be deserialized.
     * @return The deserialized Object.
     * @throws IOException If encountered.
     * @throws java.lang.ClassNotFoundException If for some reason the Object
     * cannot otherwise be deserialized.
     */
    public Object get(long id) throws IOException, ClassNotFoundException {
        Path p = Paths.get(getPath(id).toString(), name);
        return Generic_IO.readObject(p);
    }

    /**
     * Adds a new directory to the file store for storing item identified by
     * {@link #nextID}.
     *
     * @throws IOException If encountered.
     */
    public void addDir() throws IOException {
        nextID++;
        if (nextID % rangeL == 0) {
            // Grow
            if (nextID == ranges.get(0)) {
                // Grow deeper.
                ranges.add(0, nextRange);
                root = new Generic_Path(Paths.get(baseDir.s, getName(0L, nextRange - 1)));
                initNextRange();
                Files.createDirectory(root.getPath());
                //System.out.println(root.toString());
                Path target = Paths.get(root.s,
                        lps[0].getFileName().toString());
                Files.move(lps[0].getPath(), target);
                dirCounts.add(0, 1L);
                levels++;
                lps = new Generic_Path[levels];
                lps[0] = root;
                // Add width.
                int level = levels - 2;
                long range = ranges.get(level);
                long dirCount = dirCounts.get(level);
                // Add directories up to the new highest leaf
                long l = dirCount * range;
                for (int lvl = 1; lvl < levels; lvl++) {
                    long u = l + ranges.get(lvl) - 1;
                    Path p = Paths.get(lps[lvl - 1].s, getName(l, u));
                    Files.createDirectory(p);
                    //System.out.println(p.toString());
                    Generic_Collections.addToList(dirCounts, lvl, 1L);
                    lps[lvl] = new Generic_Path(p);
                }
            } else {
                // Add width as needed.
                for (int lvl = 1; lvl < levels; lvl++) {
                    long range = ranges.get(lvl);
                    if (nextID % range == 0) {
                        // Add a new directory.
                        long dirCount = dirCounts.get(lvl);
                        long l = dirCount * range;
                        long u = l + range - 1;
                        Path p = Paths.get(lps[lvl - 1].s, getName(l, u));
                        Files.createDirectory(p);
                        //System.out.println(p.toString());
                        Generic_Collections.addToList(dirCounts, lvl, 1L);
                        lps[lvl] = new Generic_Path(p);
                        // Add other new directories up to the new highest leaf
                        for (int lvl2 = lvl + 1; lvl2 < levels; lvl2++) {
                            u = l + ranges.get(lvl2) - 1;
                            p = Paths.get(lps[lvl2 - 1].s, getName(l, u));
                            Files.createDirectory(p);
                            //System.out.println(p.toString());
                            Generic_Collections.addToList(dirCounts, lvl2, 1L);
                            lps[lvl2] = new Generic_Path(p);
                        }
                        break;
                    }
                }
            }
        }
        // Add to the currentDir
        Path p = Files.createDirectory(
                Paths.get(lps[levels - 1].s, Long.toString(nextID)));
        //System.out.println(p.toString());
    }

    /**
     * Tests the integrity of the file store from its base directory.
     *
     * @return true if the integrity seems fine.
     * @throws java.io.IOException If the file store lacks integrity.
     */
    public final boolean testIntegrity() throws IOException {
        try (Stream<Path> paths = Files.walk(root.getPath())) {
            boolean ok;
            try {
                ok = paths.allMatch(path -> testPath(path));
            } catch (NumberFormatException e) {
                throw new IOException("Some paths are not OK as they contain "
                        + "leaf file names that cannot be converted to a "
                        + "Long.");
            }
            if (!ok) {
                throw new IOException("Some paths are not OK as they are not "
                        + "leaf files and are not not directories.");
            }
        }
        return true;
    }

    /**
     * @param p The path to test.
     * @return true if p is not a directory or if there is a directory at p and
     * the filename contains {@link #SEP} or can be readily converted to a Long.
     * @throws NumberFormatException if p is a directory, does not contain
     * {@link SEP} and cannot be readily converted to a Long.
     */
    protected final boolean testPath(Path p) {
        String fn = p.getFileName().toString();
        if (Files.isDirectory(p)) {
            if (fn.contains(SEP)) {
                return true;
            } else {
                Long.valueOf(fn);
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * @return The highest leaf directory for an initialised file store.
     * @throws IOException If encountered.
     */
    public Path getHighestLeaf() throws IOException {
        return getPath(nextID);
    }

    /**
     * @return The highest leaf directory for a non-initialised file store.
     * @throws IOException If encountered.
     */
    protected final Path findHighestLeaf() throws IOException {
        Path hd = getHighestDir();
        List<Path> l = Generic_IO.getList(hd);
        if (l.size() == 1) {
            return l.get(0);
        } else {
            TreeMap<Long, Path> s = new TreeMap<>();
            l.forEach((p) -> {
                s.put(Long.valueOf(p.getFileName().toString()), p);
            });
            return s.lastEntry().getValue();
        }
    }

    /**
     * @return The path to the highest leaf directory.
     * @throws IOException If encountered.
     */
    protected Path getHighestDir() throws IOException {
        Path p = getHighestDir0(baseDir.getPath());
        Path p2 = getHighestDir0(p);
        while (p.compareTo(p2) != 0) {
            p = p2;
            p2 = getHighestDir0(p2);
        }
        return p;
    }

    /**
     * @param p The directory to find the highest directory in.
     * @return The path to the highest directory in the directory at {@code p}.
     * @throws IOException If encountered.
     */
    protected Path getHighestDir0(Path p) throws IOException {
        List<Path> l = Generic_IO.getList(p);
        TreeMap<Long, Path> m = new TreeMap<>();
        l.forEach((p2) -> {
            String fn = p2.getFileName().toString();
            if (fn.contains(SEP)) {
                m.put(Long.valueOf(fn.split(SEP)[1]), p2);
            }
        });
        if (m.isEmpty()) {
            return p;
        }
        return m.lastEntry().getValue();
    }
}
