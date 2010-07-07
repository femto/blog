package template;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultText;

import java.io.File;
import java.util.*;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class DrymlTemplateHandler {

    private DrymlConfiguration drymlConfiguration;

    private Map tagDefinitions = new HashMap();
    private Stack invocationStack = new Stack();
    private boolean last_if = true;

    private Map ognlContext = null;

    public DrymlTemplateHandler(DrymlConfiguration drymlConfiguration) {
        this.drymlConfiguration = drymlConfiguration;

        Map map = new OgnlContext();
        ognlContext = Ognl.addDefaultContext(null, drymlConfiguration.getClassResolver(), map);
    }


//    public DrymlTemplateHandler() {
//        Map map = new HashMap();
//
//        ognlContext = Ognl.addDefaultContext(null, new MyClassResolver(), map);
//    }


    public static Document parse(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }


    public static void main(String[] args) throws Exception {
        //StringBuilder result = new StringBuilder();
        //DrymlTemplateHandler templateHandler = new DrymlTemplateHandler();

        //templateHandler.handle("webapps\\blog\\WEB-INF\\views\\welcome\\sayit.dryml", result);
        //System.out.println(result.toString());
    }

    public void handle(String f, StringBuilder result) {
        this.handleInternal("webapps\\blog\\WEB-INF\\views\\application.dryml", result);
        this.handleInternal(f, result);
    }

    public void handleInternal(String f, StringBuilder result) {

        try {
            File file = new File(f);

            Document document = parse(file);
            Element root = document.getRootElement();
            //System.out.println(document.selectNodes("//foo/bar"));


            for (Iterator i = root.elementIterator(); i.hasNext();) {
                Element tag = (Element) i.next();
                handleMeetingElement(tag, result);
                // do something
            }

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }


    private static void handleInclude(Element tag) {

    }

    private void handleDefTag(Element tag) {
        Attribute attribute = tag.attribute("tag");
        tagDefinitions.put(attribute.getValue(), new TagDefinition(tag));
    }

    //calling
    //preparing

    private void handleMeetingElement(Element element, StringBuilder result) {
        //trying find out tagDefinition
        if ("def".equals(element.getName())) {
            handleDefTag(element);

        } else if ("include".equals(element.getName())) {
            handleInclude(element);
        } else if ("scriptlet".equals(element.getName())) {
            handleScriptlet(element);
        } else { //calling

            if (attrContains(element, "param")) {
                Attribute attribute = element.attribute("param");
                String paramName = attribute.getValue();
                if ("true".equals(attribute.getValue()) || "param".equals(attribute.getValue())) {
                    paramName = element.getName();
                }


                InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
                if (invocationContext.getAll_parameters().containsKey(paramName)) {
                    Object object = invocationContext.getAll_parameters().get(paramName);
                    result.append(object.toString());
                    return;
                }
            }

            if (tagDefinitions.get(element.getName()) == null) { //default tag
                handleDefaultTag(element, result); //nothing changed invocationContext
            } else { //handle defined element
                Object tagDefinition = tagDefinitions.get(element.getName());
                TagInvocation tagInvocation = new TagInvocation(element);
                InvocationContext invocationContext = new InvocationContext((TagDefinition) tagDefinition, tagInvocation);

                //preparing attributes
                Map attributes = new HashMap();
                Map all_attributes = new HashMap();

                for (Iterator iterator = tagInvocation.getTag().attributeIterator(); iterator.hasNext();) {
                    Attribute attr = (Attribute) iterator.next();
                    all_attributes.put(attr.getName(), attr.getValue());
                    Attribute attributeAttrs = ((TagDefinition) tagDefinition).getTag().attribute("attrs");
                    if (attributeAttrs != null) {
                        String attrs = attributeAttrs.getValue();
                        String[] attrs1 = attrs.split(",");
                        for (int i = 0; i < attrs1.length; i++) {
                            attrs1[i] = attrs1[i].trim();
                        }
                        if (!Arrays.asList(attrs1).contains(attr.getName())) {
                            attributes.put(attr.getName(), attr.getValue());
                        }
                    } else {
                        attributes.put(attr.getName(), attr.getValue());
                    }
                }

                invocationContext.setAttributes(attributes);
                invocationContext.setAll_attributes(all_attributes);

                //preparing parameters, all lazy evaluated
                Map parameters = new TagParameters();
                Map all_parameters = new TagParameters();
                for (Iterator it = element.elementIterator(); it.hasNext();) {
                    Element ele = (Element) it.next();
                    if (ele.getName().indexOf("param-") != -1) {
                        String name = ele.getName().replace("param-", "");
                        //System.out.println(name);
                        ProcEval procEval = new ProcEval(ele, this, invocationContext);
                        parameters.put(name, procEval); //or ele's content?
                        all_parameters.put(name, procEval);
                    }
                }
                if (all_parameters.size() == 0) { //what about text?
                    DefaultEval defaultValue = new DefaultEval(element, this, invocationContext);
                    //String defaultValueResult = defaultValue.toString();
                    parameters.put("default", defaultValue);
                    all_parameters.put("default", defaultValue);
                }
                invocationContext.setParameters(parameters);
                invocationContext.setAll_parameters(all_parameters);

                Map local_variables = new HashMap();
                local_variables.put("attributes", attributes);
                local_variables.put("all_attributes", all_attributes);
                local_variables.put("parameters", parameters);
                local_variables.put("all_parameters", all_parameters);
                invocationContext.setLocal_variables(local_variables);


                invocationStack.push(invocationContext); //maybe calculating the attrs and all_attrs?

                handleInvocation(invocationContext, result); //handle attribute
                invocationStack.pop();
            }
        }
    }

    private void handleScriptlet(Element element) {

    }

    private void handleDefaultTag(Element element, StringBuilder result) { //default invocation doesn't change invocationContext's attr & params

        if (element.getName().equals("ognl-append")) {
            try {
                InvocationContext invocationContext = (InvocationContext) invocationStack.peek();

                //System.out.println(Ognl.getValue(element.getText(), new HashMap(), invocationContext.getAll_attributes()));
                result.append(Ognl.getValue(element.getText(), ognlContext, invocationContext.getLocal_variables()));
            } catch (OgnlException e) {
                throw new RuntimeException(e);
            }
            return;
        } else if ("ognl".equals(element.getName())) {
            try {
                InvocationContext invocationContext = (InvocationContext) invocationStack.peek();

                //System.out.println(Ognl.getValue(element.getText(), new HashMap(), invocationContext.getAll_attributes()));
                Ognl.getValue(element.getText(), ognlContext, invocationContext.getLocal_variables());
                System.out.println("...");
            } catch (OgnlException e) {
                throw new RuntimeException(e);
            }
            return;
        } else if ("set".equals(element.getName())) {


            //System.out.println(element.getName());
            InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
            for (Iterator it = element.attributeIterator(); it.hasNext();) {
                Attribute attribute = (Attribute) it.next();
                invocationContext.getLocal_variables().put(attribute.getName(), eval(attribute.getValue(), ognlContext, invocationContext.getLocal_variables()));
            }
            return;
        } else if ("if".equals(element.getName())) {
            InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
            Object retValue = eval(element.attribute("test").getValue(), ognlContext, invocationContext.getLocal_variables());
            if (!(last_if = trueValue(retValue))) {
                return;
            }
        } else if ("else".equals(element.getName())) {
            if (last_if) {
                return;
            }
        } else if("repeat".equals(element.getName())) {
            //System.out.println("repeat");
            InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
            Object retValue = eval(element.attribute("with").getValue(), ognlContext, invocationContext.getLocal_variables());
            if (!(last_if = trueValue(retValue))) {
                return;
            }
            //  context_map do
            //    parameters.default
            //  end.join(join)

        }
        if (!"do".equals(element.getName())) {
            InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
            //checking attrs, to see if there is any "if", "repeat", "unless"
            if (attrContains(element, "if")) {
                String controlValue = element.attribute("if").getValue();
                System.out.println("evaluating " + controlValue);
                Object retValue = eval(controlValue, ognlContext, invocationContext.getLocal_variables());
                if (!(last_if = trueValue(retValue))) {
                    return;
                }
            }

            result.append("<" + element.getName() + "");

            for (Iterator i = element.attributeIterator(); i.hasNext();) {
                Attribute attribute = (Attribute) i.next();
                List skipAttributes = Arrays.asList("if", "unless", "repeat");
                if (skipAttributes.contains(attribute.getName())) {
                    continue;
                }
                if ("param".equals(attribute.getName())) {
                    String paramName = attribute.getValue();
                    if ("true".equals(paramName) || "param".equals(paramName)) {
                        paramName = element.getName();
                    }
                    result.append(nameValue("class", paramName));
                }
                if ("merge-attrs".equals(attribute.getName())) {
                    //peek the invocation stack
                    invocationContext = (InvocationContext) invocationStack.peek();
                    for (Iterator it = invocationContext.getAttributes().keySet().iterator(); it.hasNext();) {
                        String key = (String) it.next();
                        result.append(nameValue(key, invocationContext.getAttributes().get(key).toString()));

                    }
                } else if ("param".equals(attribute.getName())) {
                    String paramName = attribute.getValue();
                    //System.out.println(attribute.getName() + "=" + attribute.getValue());
                    if ("true".equals(paramName)) {
                        paramName = element.getName();
                    }
                    //System.out.println("paramName is " + paramName);
                    invocationContext = (InvocationContext) invocationStack.peek();
                    //System.out.println(invocationContext.getParameters().get(paramName));


                    //result.append()
                    Object value = invocationContext.getParameters().get(paramName);
                    if (value != null) {
                        result.append(">");
                        for (Iterator it = ((Element) value).content().iterator(); it.hasNext();) {
                            Node node = (Node) it.next();
                            result.append(node.getText());
                        }

                        result.append("</" + element.getName() + ">");
                        return;
                    } else { //normal path

                    }

                } else {
                    if (attribute.getValue().startsWith("ognl:")) {
                        Object evalResult = eval(attribute.getValue(), ognlContext, invocationContext.getLocal_variables());

                        result.append(nameValue(attribute.getName(), evalResult));
                    } else {
                        result.append(nameValue(attribute.getName(), attribute.getValue()));
                    }
                }

            }

            result.append(">");
        }
        //for element's body
        for (Iterator i = element.content().iterator(); i.hasNext();) {
            Node node = (Node) i.next();
            if (node instanceof Element) {
                //System.out.println(node);
                handleMeetingElement((Element) node, result);
            } else { //text
                result.append(node.getText());
            }

            // do something
        }

        if (!"do".equals(element.getName())) {
            result.append("</" + element.getName() + ">");
        }
    }

    private boolean trueValue(Object retValue) {
        if (retValue == null) {
            return false;
        }
        return true;
    }

    private boolean attrContains(Element element, String attrName) {
        for (Iterator it = element.attributeIterator(); it.hasNext();) {
            Attribute attribute = (Attribute) it.next();
            if (attribute.getName().equals(attrName)) {
                return true;
            }
        }
        return false;
    }

    private String nameValue(String name, String value) {
        return " " + name + "=\"" + value + "\"";
    }

    private String nameValue(String name, Object value) {
        if (value != null) {
            return nameValue(name, value.toString());
        } else {
            return nameValue(name, "null");
        }
    }

    private Object eval(String value, Map context, Object root) {
        try {
            if (value.startsWith("ognl:")) {
                value = value.replace("ognl:", "");
                //System.out.println("getting value for " + value);
                return Ognl.getValue(value, context, root);
            }
        } catch (OgnlException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    //really invocation

    private void handleInvocation(InvocationContext invocationContext, StringBuilder result) {

        Element element = invocationContext.getTagInvocation().getTag();


        //handle tagDefinition's attrs
        TagDefinition tagDefinition = invocationContext.getTagDefinition();
        TagInvocation tagInvocation = invocationContext.getTagInvocation();
        Map local_variables = invocationContext.getLocal_variables();

        for (Iterator it = tagDefinition.getTag().attributeIterator(); it.hasNext();) {
            Attribute attribute = (Attribute) it.next();
            if ("attrs".equals(attribute.getName())) {
                String[] attrs = attribute.getValue().split(",");
                for (int i = 0; i < attrs.length; i++) {
                    String attrName = attrs[i];
                    local_variables.put(attrName, invocationContext.getAll_attributes().get(attrName));
                }

            }
        }


        //handle tagDefinition's body
        for (Iterator it = tagDefinition.getTag().content().iterator(); it.hasNext();) {
            Node node = (Node) it.next();
            if (node instanceof DefaultText) {
                result.append(((Text) node).getText());
            } else if (node instanceof Element) {
                handleMeetingElement((Element) node, result);
            }

        }
    }

    public void handleNode(Node node, StringBuilder result) {
        if (node instanceof Text) {
            result.append(node.getText());
        } else {
            handleMeetingElement((Element) node, result);
        }

    }
}