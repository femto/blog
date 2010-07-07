package template;

import ognl.ClassResolver;

import java.util.Map;

/**
* # @copyright: please see original copyright notice
*
* @author: femto
*/
class MyClassResolver implements ClassResolver {
    public Class classForName(String s, Map map) throws ClassNotFoundException {
        System.out.println("resovling for class " + s);
        if(s.length() == 1 && 'A' <= s.charAt(0) && 'Z' >= s.charAt(0)) {
            return Class.forName("com.scooterframework.web.util." + s);
        }
        return Class.forName(s);
    }
}
