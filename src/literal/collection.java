package literal;

import types.Tuple2;

import java.util.*;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class collection {

    public static <T> List<T> List(T... elems) {
        return Arrays.asList(elems);
    }

    public static <T> T[] Array(T... elems) {
        return elems;
    }

    public static <T> Set<T> Set(T... elems) {
        return new HashSet<T>(List(elems));
    }

    public static <K, V> Map<K, V> Map(Tuple2<K, V>... entries) {
        Map<K, V> map = new HashMap<K, V>();

        for (Tuple2<K, V> entry : entries) {
            map.put(entry._1, entry._2);
        }
        return map;
    }


}
