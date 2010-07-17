package template;

import java.util.HashMap;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
//or extends LinkedHashMap?
public class TagParameters extends HashMap {

    @Override
    public Object get(Object key) {
        ProcEval result = (ProcEval) super.get(key);
        if (result != null) {
            return result.call();
        } else {
            return null;
        }
    }

    //only get, not evaluated
    public Object fetch(Object key) {
        return super.get(key);
    }
}
