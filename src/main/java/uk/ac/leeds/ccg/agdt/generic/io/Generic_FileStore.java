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

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;
import uk.ac.leeds.ccg.agdt.generic.core.Generic_Strings;
import uk.ac.leeds.ccg.agdt.generic.math.Generic_Math;

/**
 * For storing data/files on disk. An file store is a form of data base where
 * each element is given a unique numerical index and is stored in a file at a
 * location given by the index known as a leaf file. Leaf files are found at
 * level 0 of the file store. The 1st element in the file store is indexed by
 * 0L, the 2nd element in the data base is indexed by 1L, the 3rd element in the
 * data base is indexed by 2L, and so on... A file store is comprised of a base
 * directory in which there are subdirectories at each level. Each subdirectory
 * may contain a further level of subdirectories, and so on down to level 0...
 * How many subdirectory levels there are and how many leaf directories are at
 * level 0 is determined by the range and the number of elements being stored in
 * the archive. Subdirectories are given standardised names such that it is easy
 * to find and infer the location of leaf directories at the end of the
 * directory tree at level 0.
 *
 * If range was set to 10, there would be at most 10 subdirectories in each
 * level (beyond level 0) of the archive. Storing Long.MAX_VALUE of elements in
 * such a archive requires x levels.
 *
 * Archives start with 3 levels and dynamically grow to store more elements. For
 * range = 10 the initial tree can be represented as follows:
 *
 * {@code
 * Level
 * 0        - 1           - 2
 *
 * 0        - 0_9         - 0_99
 * }
 *
 * For range = 10 and n = 100001 the tree can be represented as follows:
 *
 * {@code
 * Level
 * 0      - 1             - 2             - 3             - 4             -5            -6
 *
 * 0      - 0_9           - 0_99          - 0_999         - 0_9999        - 0_99999     - 0_999999
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
 * 9999   - 9990_9999     - 9900_9999     - 9000_9999     - 0_10000
 * 10000  - 10000_10009   - 10000_10099   - 10000_10999   - 10000_19999   - 0_99999
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
 * different runs of a program. They can also be used to cache data to help with
 * memory management.
 *
 * The limits with this implementation are that at most Long.MAX_VALUE of
 * elements can be stored in an archive. In cases where this is insufficient,
 * there is scope for developing some new code to handle more elements in a
 * similar way. This idea along with other related develop ideas are outlined
 * below:
 * <ul>
 * <li>Refactor or develop additional code in order to store more than
 * Long.MAX_VALUE number of elements where the identifiers of these elements are
 * represented as {@link BigInteger} numbers. This might leverage some other
 * libraries which depend on the Generic library which contains this class - in
 * particular - Math - which provides utility for BigInteger and BigDecimal
 * arithmetic which might be useful in such a development. If the product of
 * this development were a new class, then this class could potentially be
 * stored in the Generic library if dependencies are appropriately versioned and
 * care was taken not to create cyclical dependencies (a form of recursive
 * enrichment). Or the result could reside elsewhere. Where best to put any new
 * class will be considered further in due course.</li>
 * <li>Add a constructor to create a new Archive from an existing one
 * potentially with a different range.</li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_FileStore {

    private static final long serialVersionUID = 1L;

    /**
     * Separates the smallest and largest numbers for the identifiers stored in
     * any directory at level > 0.
     */
    protected static final String SEP = Generic_Strings.symbol_underscore;

    /**
     * For storing the base directory path of the archive.
     */
    protected final Path baseDir;

    /**
     * The name of the archive. Used to initialise baseDir.
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
     * Stores the number of levels in the archive. Initially this is 3 and
     * increases by 1 each time the archive grows deeper. The maximum number of
     * elements that can be stored in 3 levels is range * range. With each
     * subsequent level this number is increased by a factor of range. With n
     * levels and range r {@code BigDecimal.valueOf(r).pow(n-1).longValueExact}
     * elements can be stored. So, with range = 100 and 3 levels some 10
     * thousand elements can be stored. With range = 100 and 7 level some a
     * million million elements can be stored. To calculate how many levels will
     * be needed for a given number of elements n and a range r use
     * {@link #getLevels(long, long)}.
     */
    protected int levels;

    /**
     * For storing the range for each level greater than 0. This grows with
     * {@link #levels} as the archive grows.
     */
    protected ArrayList<Long> ranges;

    /**
     * For storing the number of directories at each level greater than 0. This
     * grows with {@link #levels} as the archive grows and is modified as new
     * directories are added at each level.
     */
    protected ArrayList<Integer> dirCounts;

    /**
     * For storing the paths to the directories (at each level greater than
     * zero) in which nextID is to be stored. This grows with {@link #levels} as
     * the archive grows.
     */
    protected Path[] lps;

    /**
     * The lower interval IDs for the directories at each level for the
     * {@link #nextID} (and {@link #lps}). This grows with {@link #levels} as
     * the archive grows.
     */
//    protected ArrayList<Long> lIDs;
    /**
     * Stores the nextID. Initially set to 0.
     */
    protected long nextID;

    /**
     * Initialises a file store at Path p called name with 3 levels allowing to
     * store 100 elements in each directory.
     *
     * @param p The path to where the archive will be initialised.
     * @param name The name the archive will be given.
     * @throws IOException If encountered.
     */
    public Generic_FileStore(Path p, String name) 
            throws IOException, Exception {
        this(p, name, (short) 100);
    }

    /**
     * Initialises a file store at Path p called name with 3 levels allowing to
     * store range number of elements in each directory.
     *
     * @param p The path to where the archive will be initialised.
     * @param name The name the archive will be given.
     * @param range The parameter set for archive controlling the maximum number
     * of directories allowed at each level.
     * @throws IOException If encountered.
     * @throws Exception If range is less than 0.
     */
    public Generic_FileStore(Path p, String name, short range)
            throws IOException, Exception {
        if (range < 0) {
            throw new Exception("Range cannot be < 0.");
        }
        baseDir = Paths.get(p.toString(), name);
        this.name = name;
        rangeL = range;
        rangeBI = BigInteger.valueOf(range);
        levels = 3;
        nextID = 0;
        lps = new Path[2];
        long l;
        //String fn;
        ranges = new ArrayList<>();
        BigInteger rBI;
        ranges.add(rangeBI.longValueExact());
        rBI = rangeBI.multiply(rangeBI);
        ranges.add(rBI.longValueExact());
        long u = 0L;
        l = rBI.subtract(BigInteger.ONE).longValueExact();
        nextRange = rBI.multiply(rangeBI).longValueExact();
        lps[1] = Paths.get(baseDir.toString(), getName(u, l));
        l = rangeBI.subtract(BigInteger.ONE).longValueExact();
        lps[0] = Paths.get(lps[1].toString(), getName(u, l));
        Files.createDirectories(Paths.get(lps[0].toString(), "0"));
        dirCounts = new ArrayList<>();
        dirCounts.add(1);
        dirCounts.add(1);
    }

    /**
     * Initialises a file store at Path p for an existing file store.
     *
     * @param p The path of the existing file store.
     * @throws IOException If encountered.
     * @throws Exception If the existing file store is problematic.
     */
    public Generic_FileStore(Path p) throws IOException, Exception {
        name = p.getFileName().toString();
        List<Path> l = Generic_IO.getList(p);
        if (l.size() != 1) {
            throw new Exception("Path " + p.toString() + " does not appear to "
                    + "be a file store as it does not contain one element.");
        }
        baseDir = l.get(1);
        if (!Files.isDirectory(baseDir)) {
            throw new Exception("Path " + p.toString() + " does not appear to "
                    + "be a file store as it does not contain one element that "
                    + "is a directory.");
        }
        String fn = l.get(1).getFileName().toString();
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
            throw new Exception("Path " + p.toString() + " does not appear to "
                    + "be a file store as the name of the directory it contains"
                    + " more than one \"" + SEP + "\" in the filename.");
        }
        try {
            rangeL = Long.getLong(split[1]) + 1;
        } catch (NumberFormatException ex) {
            throw new Exception("Path " + p.toString() + " does not appear to "
                    + "be a file store as the name of the directory it contains"
                    + " does not end with a String that can be converted into a "
                    + "Long.");
        }
        if (rangeL < 0 || rangeL > Short.MAX_VALUE) {
            throw new Exception("Path " + p.toString() + " does not appear to "
                    + "be a file store as the name of the directory it contains"
                    + " does not end with a String that can be converted into a "
                    + "Long and which has a value greater than 0 and less than "
                    + "Short.MAX_VALUE.");
        }
        rangeBI = BigInteger.valueOf(rangeL);
        testIntegrity();
        initLevelsAndNextID();
        getRanges();
        initLPs();

//        BigInteger rBI;
//        ranges.add(rangeBI.longValueExact());
//        rBI = rangeBI.multiply(rangeBI);
//        ranges.add(rBI.longValueExact());
//        long u = 0L;
//        l = rBI.subtract(BigInteger.ONE).longValueExact();
        nextRange = BigInteger.valueOf(ranges.get(ranges.size() - 1)).multiply(rangeBI).longValueExact();
        dirCounts = getDirCounts(nextID - 1L, rangeL);
    }

    public static void main(String[] args) {
        try {
            Path p = Paths.get(System.getProperty("user.home"),
                    Generic_Strings.s_data,
                    Generic_Strings.s_generic);
            String name = "test1";
            if (true) {
                //if (false) {
                // Delete old test archive if it exists.
                Path d = Paths.get(p.toString(), name);
                if (Files.exists(d)) {
                    try (Stream<Path> walk = Files.walk(d)) {
                        walk.sorted(Comparator.reverseOrder())
                                .map(Path::toFile)
                                //.peek(System.out::println)
                                .forEach(File::delete);
                    }
                }
            }
            // Create new archive.
            short range = 10;
            Generic_FileStore a = new Generic_FileStore(p, name, range);
            a.run();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void run() throws IOException {
        for (long l = 0; l < rangeBI.pow(3).add(BigInteger.ONE).longValueExact(); l++) {
            //for (long l = 0; l < 11; l++) {
            addToArchive();
        }
    }

    /**
     * Initialises nextRange.
     */
    protected final void initNextRange() {
        nextRange = BigInteger.valueOf(ranges.get(levels - 1)).multiply(rangeBI)
                .longValueExact();
    }

    /**
     * Calculates and returns the number of levels needed for an archive with
     * range of r and n total number of elements to store. If the result is
     * larger than {@link Generic_Math#SHORT_MAXVALUE} then this is probably too
     * deep - maybe try with a larger range...
     *
     * @param n the number of elements
     * @param range the range of the archive
     * @return the number of levels needed for an archive with range of r and n
     * total number of elements to store.
     */
    public static long getLevels(long n, long range) {
        long levels = 1;
        long l = n;
        while (l / range >= range) {
            l = l / range;
            levels++;
        }
        return levels;
    }

    /**
     * {@link #levels} is for storing the number of levels in the archive. This
     * is kept up to date as the archive grows. This method is a convenience
     * method for users that want to know how many levels will be needed once
     * the number of elements stored reaches n. This effectively calls
     * {@link #getLevels(long, long)} defaulting range to rangeL.
     *
     * @param n The number of elements.
     * @return The number of levels needed for this archive to store n total
     * number of elements.
     */
    public long getLevels(long n) {
        return getLevels(n, rangeL);
    }

    /**
     * For initialising levels and nextID.
     *
     * @throws java.io.IOException If there encountered in
     * {@link #getHighestDir()}.
     */
    protected final void initLevelsAndNextID() throws IOException {
        Path p = getHighestDir();
        nextID = Long.getLong(p.getFileName().toString()) + 1;
        levels = p.getNameCount() - baseDir.getNameCount();
    }

    /**
     * @return A copy of the current number of levels in the directory tree for
     * this archive.
     */
    public final long getLevels() {
        return levels;
    }

    /**
     * Updates if necessary and returns the ranges at each level.
     *
     * @return {@link ranges} updated if levels has increased since it was last
     * updated.
     */
    protected final ArrayList<Long> getRanges() {
        if (ranges == null) {
            ranges = new ArrayList<>();
        }
        ranges.add(nextRange);
        initNextRange();
        return ranges;
    }

    /**
     * Calculates and returns the ranges given the number of elements to be
     * stored and the range.
     *
     * @param n The number of elements to be stored.
     * @param range The range.
     * @return {@link ranges} updated if levels has increased since it was last
     * updated.
     * @throws java.lang.Exception If n is too big for the range. If this is the
     * case then maybe try to specify a bigger range or look to implementing
     * something that can handle larger numbers of elements as suggested in the
     * class comment documentation.
     */
    public static final ArrayList<Long> getRanges(long n, long range)
            throws Exception {
        long lvls = getLevels(n, range);
        if (lvls > Integer.MAX_VALUE) {
            throw new Exception("n too big for the range");
        }
        ArrayList<Long> r = new ArrayList<>();
        for (int l = 0; l < lvls; l++) {
            r.add(BigInteger.valueOf(range).pow(l).longValueExact());
        }
        return r;
    }

    /**
     * Calculates the number of directories needed at each level to store n
     * elements with range r. This first calculates the number of levels and the
     * ranges for each level.
     *
     * @param n The number of elements to store.
     * @param range The range.
     * @return The number of directories needed at each level to store n
     * elements with range r.
     * @throws java.lang.Exception If n is too big for the range. If this is the
     * case then maybe try to specify a bigger range or look to implementing
     * something that can handle larger numbers of elements as suggested in the
     * class comment documentation.
     */
    public static final ArrayList<Integer> getDirCounts(long n, long range)
            throws Exception {
        long lvls = getLevels(n, range);
        ArrayList<Long> rngs = getRanges(n, range);
        /**
         * Can safely cast here as getLevels(long, long) already throws an
         * Exception if lvls is greater than Integer.MAX_VALUE
         */
        int li = (int) lvls;
        return getDirIndexes(n, li, rngs);
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
        for (int lvl = levels - 2; lvl >= 0; lvl--) {
            int c = 0;
            while (id > 0) {
                id -= ranges.get(lvl);
                c++;
            }
            id += ranges.get(lvl);
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
     * implementing something that can handle larger numbers of elements as
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
     * Calculates and returns the current path of the directory for storing the
     * element identified by id. For efficiency, this does not check if id is
     * less than or equal to {@link #nextID}, but it should and if not then what
     * is returned might not be useful.
     *
     * @param id The identifier of the element for which the current path is
     * wanted.
     * @return The current path of the directory for storing the element
     * identified by id.
     */
    protected Path getPath(long id) {
        return getPath(id, 0);
    }

    /**
     * Calculates and returns the current path of the directory at level lvl for
     * storing the element identified by id.For efficiency, this does not check
     * if id is less than or equal to {@link #nextID}, but it should and if not
     * then what is returned might not be useful.
     *
     * @param id The identifier of the element for which the current path is
     * wanted.
     * @param lvl
     * @return The current path of the directory for storing the element
     * identified by id.
     */
    protected Path getPath(long id, int lvl) {
        Path[] paths = new Path[levels - 1];
        ArrayList<Integer> dirIndexes = getDirIndexes(id);
        Path p = lps[levels - 2];
        for (int lv = levels - 2; lv < lvl; lv--) {
            long range = ranges.get(lv);
            long l = (long) dirIndexes.get(lv) * range;
            long u = l + range - 1;
            paths[lv] = Paths.get(p.toString(), getName(u, l));
            p = paths[lv];
        }
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
        lps = new Path[levels - 1];
        long l = 0L;
        long range = ranges.get(levels - 2);
        long u = range - 1;
        lps[levels - 2] = Paths.get(baseDir.toString(), getName(l, u));
        for (int lvl = levels - 3; lvl >= 0; lvl--) {
            range = ranges.get(lvl);
            l = range * (long) dirCounts.get(lvl);
            u = l + range - 1L;
            lps[lvl] = Paths.get(lps[lvl + 1].toString(), getName(l, u));
        }
    }

    public void addToArchive() throws IOException {
        nextID++;
        if (nextID % rangeL == 0) {
            // Grow
            if (nextID == ranges.get(levels - 2)) {
                // Grow deeper.
                ranges.add(nextRange);
                Path target = Paths.get(baseDir.toString(), getName(0L, nextRange - 1));
                initNextRange();
                Files.createDirectory(target);
                System.out.println(target.toString());
                target = Paths.get(target.toString(),
                        lps[lps.length - 1].getFileName().toString());
                Files.move(lps[lps.length - 1], target);
                dirCounts.add(1);
                levels++;
                initLPs();
            }
            // Add width as needed.
            for (int lvl = levels - 2; lvl >= 0; lvl--) {
                long range = ranges.get(lvl);
                long dirCount = dirCounts.get(lvl);
                if (nextID % (range * dirCount) == 0) {
                    // Create a new directory
                    long l = dirCount * range;
                    long u = l + range - 1;
                    Path p = Paths.get(lps[lvl + 1].toString(), getName(l, u));
                    Files.createDirectory(p);
                    System.out.println(p.toString());
                    int c = dirCounts.get(lvl);
                    dirCounts.remove(lvl);
                    dirCounts.add(lvl, c + 1);
                    lps[lvl] = p;
                    // Create other new directories up to level 0
                    for (int lvl2 = lvl - 1; lvl2 >= 0; lvl2--) {
                        u = l + ranges.get(lvl2) - 1;
                        p = Paths.get(lps[lvl2 + 1].toString(), getName(l, u));
                        Files.createDirectory(p);
                        System.out.println(p.toString());
                        int c2 = dirCounts.get(lvl2);
                        dirCounts.remove(lvl2);
                        dirCounts.add(lvl2, c2 + 1);
                        lps[lvl2] = p;
                    }
                    break;
                }
            }
        }
        // Add to the currentDir
        Path p = Files.createDirectory(Paths.get(lps[0].toString(), Long.toString(nextID)));
        System.out.println(p.toString());
    }

    /**
     * Tests the integrity of the file store from its base directory.
     *
     * @return true if the integrity seems fine.
     * @throws java.io.IOException If the file store lacks integrity.
     */
    public final boolean testIntegrity() throws IOException {
        try (Stream<Path> paths = Files.walk(baseDir)) {
            boolean ok;
            try {
                ok = paths.allMatch(path -> testPath(path));
            } catch (NumberFormatException e) {
                throw new IOException("Some paths are not OK as they contain "
                        + "leaves file names that cannot be converted to a "
                        + "Long.");
            }
            if (!ok) {
                throw new IOException("Some paths are not OK as they are not "
                        + "leaf files and are not not directories.");
            }
        }
        return true;
    }

    protected final boolean testPath(Path p) {
        String fn = p.getFileName().toString();
        if (fn.contains(SEP)) {
            return Files.isDirectory(p);
        } else {
            if (Files.isDirectory(p)) {
                Long.valueOf(fn);
                return true;
            }
            return true;
        }
    }

    public Path getHighestLeaf() throws IOException {
        return getPath(nextID - 1L);
    }

    protected Path getHighestDir() throws IOException {
        Path p = getHighestDir0(baseDir);
        Path p2 = getHighestDir0(p);
        while (p.compareTo(p2) == 0) {
            p2 = getHighestDir0(p2);
        }
        return p;
    }

    /**
     *
     * @param p
     * @return
     * @throws IOException
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
