package template;

import bsh.EvalError;
import bsh.Interpreter;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import org.dom4j.tree.DefaultText;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class BeanShellTagDefinition extends XmlTagDefinition implements ITagDefinition {
    //todo, attrs, parameters

    public BeanShellTagDefinition(Element tag) {
        super(tag);
        //when invoke, have access to all the local_varialbes
    }

    public void invoke(InvocationContext invocationContext, StringBuilder result) {
        //handle tagDefinition's attrs
        TagDefinition tagDefinition = invocationContext.getTagDefinition();
        TagInvocation tagInvocation = invocationContext.getTagInvocation();
        Map local_variables = invocationContext.getLocal_variables();

        for (Iterator it = tagDefinition.getAttrs().iterator(); it.hasNext();) {
            String attrName = (String) it.next();
            local_variables.put(attrName, invocationContext.getAll_attributes().get(attrName));
        }

        Interpreter i = new Interpreter();  // Construct an interpreter
        try {
            for (Iterator it = local_variables.keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                i.set(key, local_variables.get(key));
            }
            //need we set #this?
// Eval a statement and get the result
            result.append(i.eval("11"));

// Source an external script file
            //i.source("somefile.bsh");
        } catch (EvalError e) {
            throw new RuntimeException(e);
        }


        //handle tagDefinition's body, for different TagDefinition subclasses
//        if (tagDefinition != null) {
//            if (tagDefinition instanceof ITagDefinition) {
//                ((ITagDefinition) tagDefinition).invoke(invocationContext, result);
//            } else if (tagDefinition instanceof XmlTagDefinition) {
//                XmlTagDefinition xmlTagDefinition = (XmlTagDefinition) tagDefinition;
//                for (Iterator it = xmlTagDefinition.getTag().content().iterator(); it.hasNext();) {
//                    Node node = (Node) it.next();
//                    if (node instanceof DefaultText) {
//                        result.append(((Text) node).getText());
//                    } else if (node instanceof Element) {
//                        handleMeetingElement((Element) node, result, invocationContext);
//                    }
//
//                }
//            }
//        }
    }
}
