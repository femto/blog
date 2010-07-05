package types;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class operators {

    public static <T1> Tuple1<T1> o(T1 o1) {
        return new Tuple1<T1>(o1);
    }

    public static <T1, T2> Tuple2<T1, T2> o(T1 o1, T2 o2) {
        return new Tuple2<T1, T2>(o1, o2);
    }

}
