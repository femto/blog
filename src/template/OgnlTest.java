package template;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlRuntime;

import java.util.HashMap;
import java.util.Map;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class OgnlTest {
    public static void main(String[] args) throws Exception {
        //Map map =

        //Ognl.createDefaultContext(null, new MyClassResolver());

        Map map = new HashMap();

        
        Map result = Ognl.addDefaultContext(null, new MyClassResolver(), map);

        

        OgnlContext ognlContext = new OgnlContext();
        //ognlContext.getLastEvaluation().get

        //System.out.println(OgnlContext.DEFAULT_CLASS_RESOLVER);
        
        
        System.out.println(Ognl.getValue("post = \"posts\"", result, map));

        System.out.println("x");
    }

}
