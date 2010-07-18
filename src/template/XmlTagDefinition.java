package template;

import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class XmlTagDefinition extends TagDefinition {
    private Element tag;
    public static final List FORBIDDEN_ATTRS = Arrays.asList("for", "field", "with", "type");


    public XmlTagDefinition(Element tag) {
        this.tag = tag;

        //initialize attrs, params
        String attrsValue = tag.attributeValue("attrs");
        if (attrsValue != null) {
            List attrsList  = Arrays.asList(attrsValue.split(","));
            examineAttrsList(attrsList);
            attrs.addAll(attrsList);
        }
        treeWalk(tag);

    }

    private void examineAttrsList(List attrsList) {
        for (Iterator it = attrsList.iterator(); it.hasNext();) {
            String attrName = (String) it.next();
            if(FORBIDDEN_ATTRS.contains(attrName)) {
                throw new DrymlException("forbidden attrs of " + attrName + " in tag def of " + tag.getName());
            }
        }
    }

    public Element getTag() {
        return tag;
    }

    private void treeWalk(Element element) {
        for (int i = 0, size = element.nodeCount(); i < size; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                if ((ele.attributeValue("param") != null)) {

                    params.put(getParamName(ele), ele);
                    //System.out.println(params);
                }
                treeWalk((Element) node);
            } else {
                // do something....
            }
        }
    }

    private String getParamName(Element ele) {
        String paramName = ele.attributeValue("param");
        if ("true".equals(paramName) || "param".equals(paramName)) {
            if (ele.getName().startsWith("param-")) {

                paramName = ele.getName().substring("param-".length());
            } else {
                paramName = ele.getName();
            }
        }
        return paramName;
    }
}
