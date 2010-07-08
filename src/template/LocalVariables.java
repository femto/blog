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
}
