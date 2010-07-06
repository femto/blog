package template;

import ognl.Ognl;

import java.util.HashMap;
import java.util.Map;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class OgnlTest {
    public static void main(String[] args) throws Exception {
        Map map = new HashMap();
        map.put("a", "aaa");
        Ognl.addDefaultContext(map, map);
        Object expression = Ognl.parseExpression("a");
        //System.out.println(Ognl.getValue(expression,map, map));
        //System.out.println(Ognl.getValue("@java.lang.System@out.println(38)", new HashMap(), (Object) null));
        System.out.println(Ognl.getValue("@template.Util@stylesheets(\"1\")", new HashMap(), (Object)null));
        Ognl.setValue("c", map, map, 3);
        System.out.println();
    }
    public static void test() {
        System.out.println("test");
    }
}
