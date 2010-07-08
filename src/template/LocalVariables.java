package template;

import java.util.HashMap;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class LocalVariables extends HashMap {
    private LocalVariables parent;

    public LocalVariables() {
    }

    public LocalVariables(LocalVariables parent) {
        this.parent = parent;
    }

    public LocalVariables getParent() {
        return parent;
    }

    public void setParent(LocalVariables parent) {
        this.parent = parent;
    }

    @Override
    public Object get(Object key) {
        Object result = super.get(key);
        if (result == null) { //delegate to parent
            if(parent != null) {
                 return parent.get(key);
            }
            return result;
        }
        return result;
    }
}
