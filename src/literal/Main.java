package literal;

import java.util.Map;

import static literal.collection.Map;
import static types.operators.o;


/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class Main {
    public static void main(String[] args) {
//        List<String> slist = List("a", "b", "c");
//
//        List<Integer> iList = List(1, 2, 3);
//
//        for (String elem : List("a", "java", "list")) {
//            System.out.println(elem);
//        }
//
//        String[] sArray = Array("a", "b", "c");
//
//        Integer[] iArray = Array(1, 2, 3);
//
//        for (String elem : Array("a", "b", "c")) {
//            System.out.println(elem);
//        }

        System.out.println(o(1, 2.0).getClass());

        Map map = Map(o("Faust", "Goethe"),
                o("Kosmos ", "Humboldt"),
                o("Monadologie", "Leibnitz"));
        System.out.println(map.size());


    }
}
