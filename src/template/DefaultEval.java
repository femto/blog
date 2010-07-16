package template;


import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Iterator;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class DefaultEval extends ProcEval {
    private Element element;
    private DrymlTemplateHandler drymlTemplateHandler;
    private InvocationContext invocationContext;

    public DefaultEval(Element element, DrymlTemplateHandler drymlTemplateHandler, InvocationContext invocationContext) {
        super(element, drymlTemplateHandler, invocationContext);
        this.element = element;
        this.drymlTemplateHandler = drymlTemplateHandler;
        this.invocationContext = invocationContext;
    }

    public String call() {
       StringBuilder result = new StringBuilder();
        for (Iterator iterator = element.content().iterator(); iterator.hasNext();) {
            Node node = (Node) iterator.next();
            drymlTemplateHandler.handleNode(node, result, invocationContext);
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return call();
    }
}
