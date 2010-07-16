package template;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* # @copyright: please see original copyright notice
*
* @author: femto
*/
//differnet subclasses, default: XmlTagDefinition, JavaTagDefinition, GroovyTagDefinition, CoreTagDefinition etc
public class TagDefinition {
    protected List attrs = new ArrayList();
    protected Map params = new HashMap();


    public List getAttrs() {
        return attrs;
    }

    public Map getParams() {
        return params;
    }

    public List getParamNames() {
        ArrayList result = new ArrayList();
        result.addAll(params.keySet());
        return result;
    }
}
