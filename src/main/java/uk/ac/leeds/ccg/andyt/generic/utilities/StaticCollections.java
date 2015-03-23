/**
 * A component of a library for
 * <a href="http://www.geog.leeds.ac.uk/people/a.turner/projects/MoSeS">MoSeS</a>.
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
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package uk.ac.leeds.ccg.andyt.generic.utilities;

import java.util.HashSet;
import java.util.Random;
import java.util.Vector;

/**
 * A class for generic static methods that work on collections.
 */
public class StaticCollections {

    public static HashSet getRandomIndexes_HashSet(
            Vector aVector,
            int aNumberOfIndexes,
            Random aRandom) {
        HashSet tIndexesToSwap_HashSet = new HashSet();
            int aIndex;
            int count = 0;
        if (aNumberOfIndexes > aVector.size() / 2) {
            for (aIndex = 0; aIndex < aVector.size(); aIndex ++){
                tIndexesToSwap_HashSet.add(aIndex);
                count ++;
            }
            while (count != aNumberOfIndexes) {
                do {
                    aIndex = aRandom.nextInt(aVector.size());
                } while (!tIndexesToSwap_HashSet.remove(aIndex));
                count --;
            }
        } else {
            while (count < aNumberOfIndexes) {
                do {
                    aIndex = aRandom.nextInt(aVector.size());
                } while (!tIndexesToSwap_HashSet.add(aIndex));
                count++;
            }
        }
        return tIndexesToSwap_HashSet;
    }
}
