package template;

import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Iterator;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class ProcEval {
    private Element element;
    private DrymlTemplateHandler drymlTemplateHandler;
    private InvocationContext invocationContext;

    public ProcEval(Element element, DrymlTemplateHandler drymlTemplateHandler, InvocationContext invocationContext) {
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

    public String call(InvocationContext _invocationContext) {
       StringBuilder result = new StringBuilder();
        for (Iterator iterator = element.content().iterator(); iterator.hasNext();) {
            Node node = (Node) iterator.next();
            drymlTemplateHandler.handleNode(node, result, _invocationContext); //shadow instance variables
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return call();
    }

    public String toString(InvocationContext _invocationContext) {
        return call(_invocationContext);
    }

    public InvocationContext getInvocationContext() {
        return invocationContext;
    }
}
