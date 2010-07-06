package template;

import ognl.ClassResolver;
import ognl.Ognl;
import ognl.OgnlContext;

import java.util.HashMap;
import java.util.Map;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class OgnlTest {
    public static void main(String[] args) {
        System.out.println("a" + null + "c");
    }
    public static void main2(String[] args) throws Exception {
        Map map =

        Ognl.createDefaultContext(null, new MyClassResolver());


        //System.out.println(OgnlContext.DEFAULT_CLASS_RESOLVER);
        
        System.out.println(Ognl.getValue("@Util@_stylesheet(\"a\")", map, map));
        Ognl.setValue("c", map, map, 3);
        System.out.println();
    }
    public static void test() {
        System.out.println("test");
    }

    private static class MyClassResolver implements ClassResolver {
        public Class classForName(String s, Map map) throws ClassNotFoundException {
            System.out.println(s);
            return Util.class;
        }
    }
}
