package template;

import bsh.EvalError;
import bsh.Interpreter;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultText;

import java.io.File;
import java.io.IOException;
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
    private File current_file = null;
    private Stack fileStack = new Stack();

    private Map ognlContext = null;

    public DrymlTemplateHandler(DrymlConfiguration drymlConfiguration) {
        this.drymlConfiguration = drymlConfiguration;

        initialize();
    }

    private void initialize() {
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

    public void handle(String f, StringBuilder result, Object root) {
        TopInvocationContext topInvocationContext = new TopInvocationContext();
        if (root != null) {
            topInvocationContext.getLocal_variables().putAll((Map) root);
        }
        invocationStack.push(topInvocationContext);

        this.handleInternal("webapps\\blog\\WEB-INF\\views\\application.dryml", result);
        this.handleInternal(f, result);
    }

    public void handleInternal(String f, StringBuilder result) {
        File originalFile = current_file;
        try {
            File file = new File(f);

            current_file = file;

            Document document = parse(file);
            Element root = document.getRootElement();
            //System.out.println(document.selectNodes("//foo/bar"));


            for (Iterator i = root.elementIterator(); i.hasNext();) {
                Element tag = (Element) i.next();
                handleMeetingElement(tag, result, null);
                // do something
            }

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } finally {
            current_file = originalFile;
        }
    }


    private void handleInclude(Element element, StringBuilder result) {
        try {
            String src = element.attributeValue("src");
            String file = current_file.getParentFile().getCanonicalPath() + File.separator + src + ".dryml";
            //current_file = new File(file);
            handleInternal(file, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleDefTag(Element tag) {
        Attribute attribute = tag.attribute("tag");
        //todo, may examine polymorphic tags ,type etc
        if ("true".equals(tag.attributeValue("polymorphic")) || "polymorphic".equals(tag.attributeValue("polymorphic"))) {
            tagDefinitions.put(attribute.getValue(), new PolymorphicTagDefinition(tag));
        } else if ("beanshell".equals(tag.attributeValue("type"))) {
            tagDefinitions.put(attribute.getValue(), new BeanShellTagDefinition(tag));
        } else {
            tagDefinitions.put(attribute.getValue(), new XmlTagDefinition(tag));
        }
    }

    //calling
    //preparing

    private void handleMeetingElement(Element element, StringBuilder result, InvocationContext invocationContext) {
        //trying find out tagDefinition
        if ("def".equals(element.getName())) {
            handleDefTag(element);

        } else if ("include".equals(element.getName())) {
            handleInclude(element, result);
        } else if ("scriptlet".equals(element.getName())) {
            handleScriptlet(element);
        } else { //calling
            if (invocationContext == null) {
                invocationContext = (InvocationContext) invocationStack.peek();
            }
            if (attrContains(element, "param")) {
                Attribute attribute = element.attribute("param");
                String paramName = attribute.getValue();
                if ("true".equals(attribute.getValue()) || "param".equals(attribute.getValue())) {
                    paramName = element.getName();
                }


                if (invocationContext.getAll_parameters().containsKey(paramName)) {
                    Object object = invocationContext.getAll_parameters().get(paramName);
                    result.append(object.toString());
                    return;
                }
            }

            if (tagDefinitions.get(element.getName()) == null) { //default tag
                handleDefaultTag(element, result, invocationContext); //nothing changed invocationContext
            } else { //handle defined element
                InvocationContext invocationContext2 = prelude(element, invocationContext);

                handleInvocation(invocationContext2, result); //handle attribute
                postlude();
            }
        }
    }

    private void postlude() {
        invocationStack.pop();
    }

    private InvocationContext prelude(Element element, InvocationContext context) {
        TagDefinition tagDefinition = (TagDefinition) tagDefinitions.get(element.getName());
        TagInvocation tagInvocation = new TagInvocation(element);
        InvocationContext invocationContext = new InvocationContext((TagDefinition) tagDefinition, tagInvocation);

        //preparing attributes
        Map attributes = new HashMap();
        Map all_attributes = new HashMap();

        for (Iterator iterator = tagInvocation.getTag().attributeIterator(); iterator.hasNext();) {
            Attribute attr = (Attribute) iterator.next();
            Object attrValue = getAttributeValue(attr, context);

            if (tagDefinition != null) { //or else, maybe <repeat>TagDefinition?
                //handle different TagDefinition subclasses
                List attrs = ((TagDefinition) tagDefinition).getAttrs();
                if (!attrs.contains(attr.getName())) { //tagInvocation
                    attributes.put(attr.getName(), attrValue);
                } else {
                    attributes.put(attr.getName(), attrValue);
                }
            }
            all_attributes.put(attr.getName(), attrValue); //always put all_attributes
        }

        invocationContext.setAttributes(attributes);
        invocationContext.setAll_attributes(all_attributes);

        //preparing parameters, all lazy evaluated
        Map parameters = new TagParameters();
        Map all_parameters = new TagParameters();

        //iterating over the calling tag, to see if any param-provided
        for (Iterator it = element.elementIterator(); it.hasNext();) {
            Element ele = (Element) it.next();
            if (ele.getName().indexOf("param-") != -1) {
                String name = ele.getName().substring("param-".length());
                //System.out.println(name);
                ProcEval procEval = new ProcEval(ele, this, context);
                if (!tagDefinition.getParams().containsKey(name)) {
                    parameters.put(name, procEval); //or ele's content?
                }
                all_parameters.put(name, procEval);
            }
        }
        if (all_parameters.size() == 0) { //what about text?
            DefaultEval defaultValue = new DefaultEval(element, this, context);
            //String defaultValueResult = defaultValue.toString();
            parameters.put("default", defaultValue);
            all_parameters.put("default", defaultValue);
        }
        invocationContext.setParameters(parameters);
        invocationContext.setAll_parameters(all_parameters);

        LocalVariables local_variables = new LocalVariables();
        local_variables.put("attributes", attributes);
        local_variables.put("all_attributes", all_attributes);
        local_variables.put("parameters", parameters);
        local_variables.put("all_parameters", all_parameters);
        if (invocationStack.size() > 0) {
            local_variables.setParent(((InvocationContext) invocationStack.peek()).getLocal_variables());
        }
        invocationContext.setLocal_variables(local_variables);

        //handle field, with etc
        if (element.attributeValue("field") != null) { //changing context
            //local_variables.put("this_parent", local_variables.get("this"));
            //local_variables.put("this_field", "xxx");

            local_variables.put("this", evalOgnl(element.attributeValue("field"), ognlContext, local_variables.getParent())); //evaluating in parent's context
        }
        //todo, cannot both field and for
        if (element.attributeValue("for") != null) { //changing context
            //local_variables.put("this_parent", local_variables.get("this"));
            //local_variables.put("this_field", "xxx");

            local_variables.put("this", evalOgnl(element.attributeValue("for"), ognlContext, local_variables.getParent())); //evaluating in parent's context
        }

        invocationStack.push(invocationContext); //maybe calculating the attrs and all_attrs?
        return invocationContext;
    }

    /**
     * dynamiclly evaluate attribute value
     *
     * @param attr
     * @param context
     * @return
     */
    private Object getAttributeValue(Attribute attr, InvocationContext invocationContext) {
        return eval(attr.getValue(), ognlContext, invocationContext.getLocal_variables());
    }

    private void handleScriptlet(Element element) {

    }

    private void handleDefaultTag(Element element, StringBuilder result, InvocationContext context) { //default invocation doesn't change invocationContext's attr & params

        if ("ognl-append".equals(element.getName())) {
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
                //System.out.println("...");
            } catch (OgnlException e) {
                throw new RuntimeException(e);
            }
            return;
        } else if ("beanshell-append".equals(element.getName())) {
            InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
            beanshell(element, invocationContext, result, true);
            return;
        } else if ("beanshell".equals(element.getName())) {
            InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
            beanshell(element, invocationContext, result, false);
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
            if (element.attributeValue("test") != null) {
                Object retValue = eval(element.attributeValue("test"), ognlContext, invocationContext.getLocal_variables());
                if (!(last_if = trueValue(retValue))) {
                    return;
                }
            } else {
                Object retValue = eval("ognl:this", ognlContext, invocationContext.getLocal_variables());
                if (!(last_if = trueValue(retValue))) {
                    return;
                }
            }
        } else if ("else".equals(element.getName())) {
            if (last_if) {
                return;
            }
        } else if ("repeat".equals(element.getName())) {

            prelude(element, context);
            //System.out.println("repeat");
            InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
            Object retValue = eval(element.attribute("with").getValue(), ognlContext, invocationContext.getLocal_variables());
            last_if = trueValue(retValue);
            if (last_if) {
                repeat(retValue, element, ognlContext, invocationContext.getLocal_variables(), result);
            } else { //doing nothing
            }
            //finally
            postlude();
            return;

        }

        List ifElseArray = Arrays.asList("if", "else");

        if (!ifElseArray.contains(element.getName())) {

            InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
            //checking attrs, to see if there is any "if", "repeat", "unless"
            if (attrContains(element, "if")) {
                String controlValue = element.attribute("if").getValue();
                //System.out.println("evaluating " + controlValue);
                Object retValue = eval(controlValue, ognlContext, invocationContext.getLocal_variables());
                if (!(last_if = trueValue(retValue))) {
                    return;
                }
            }

            //passing if, repeat, unless test
            //see if we have param attr defined
            //because we are predefined html tag, so we output all <start-tag> of this
            //if(!"else".equals(element.getName())) {
            result.append("<" + element.getName() + "");

            //todo: handle proper attributes handling
            Map attrs = new HashMap();
            for (Iterator i = element.attributeIterator(); i.hasNext();) {
                Attribute attribute = (Attribute) i.next();
                List skipAttributes = Arrays.asList("if", "unless", "repeat", "merge-attrs", "param");
                if (skipAttributes.contains(attribute.getName())) {
                    continue;
                } //skip if, repeat, unless, already tested

                if (attribute.getValue().startsWith("ognl:")) {
                    Object evalResult = eval(attribute.getValue(), ognlContext, invocationContext.getLocal_variables());
                    attrs.put(attribute.getName(), evalResult);
                } else {
                    attrs.put(attribute.getName(), attribute.getValue());
                }

            }

            if (attrContains(element, "param")) {

                String paramName = getParamName(element);
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


            }

            if (attrContains(element, "merge-attrs") || attrContains(element, "merge")) { //merging attrs
                attrs = mergeAttributes(attrs, invocationContext.getAttributes());
            }


            for (Iterator it = attrs.keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                result.append(nameValue(key, attrs.get(key)));
            }


            result.append(">");

        } //"if","else",ifElseArray

        //for element's body
        for (Iterator i = element.content().iterator(); i.hasNext();) {
            Node node = (Node) i.next();
            if (node instanceof Element) {
                //System.out.println(node);
                handleMeetingElement((Element) node, result, context);
            } else { //text
                result.append(node.getText());
            }

            // do something
        }

        if (!ifElseArray.contains(element.getName())) {
            result.append("</" + element.getName() + ">");
        }

    }

    private void beanshell(Element element, InvocationContext invocationContext, StringBuilder _result, boolean append) {
        Interpreter i = new Interpreter();  // Construct an interpreter
        try {

            for (Iterator it = invocationContext.getLocal_variables().keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                i.set(key, invocationContext.getLocal_variables().get(key));
            }
            i.set("context_this", eval("ognl:this", ognlContext, invocationContext.getLocal_variables()));

// Eval a statement and get the result

            Object objResult = i.eval(element.getText());
            if (append) {
                _result.append(objResult);
            }
        } catch (EvalError e) {
            throw new RuntimeException(e);
        }
    }

    private String getParamName(Element element) {
        String paramName = element.attributeValue("param");
        //System.out.println(attribute.getName() + "=" + attribute.getValue());
        if ("true".equals(paramName) || "param".equals(paramName)) {
            paramName = element.getName();
        }

        return paramName;
    }

    private Map mergeAttributes(Map attrs, Map map2) {
//        InvocationContext invocationContext;
//        invocationContext = (InvocationContext) invocationStack.peek();
        for (Iterator it = map2.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            if ("class".equals(key)) { //merging, todo suppress null handling
                attrs.put(key, attrs.get("class") + " " + map2.get(key));
            } else { //overriding
                attrs.put(key, map2.get(key).toString());
            }

        }
        return attrs;
    }

    private void repeat(Object retValue, Element element, Map ognlContext, Object root, StringBuilder result) {
        //we already test retValue outside of this
        try {
            if (retValue instanceof Collection) {
                int i = 0;
                for (Iterator it = ((Collection) retValue).iterator(); it.hasNext();) {
                    Object o = it.next();

                    Ognl.setValue(element.attribute("var").getValue(), ognlContext, root, o);
                    Ognl.setValue(element.attribute("var").getValue() + "_index", ognlContext, root, i);

                    //yields to body, with original context appended var etc. 
                    ProcEval procEval = (ProcEval) Ognl.getValue("parameters.fetch(\"default\")", ognlContext, root);
                    //wrap procEval invocationContext with some var, var_index
                    FakeInvocationContext fakeContext = new FakeInvocationContext(procEval.getInvocationContext());
                    fakeContext.getLocal_variables().put(element.attributeValue("var"), o);
                    fakeContext.getLocal_variables().put(element.attributeValue("var") + "_index", i);
                    fakeContext.getLocal_variables().put("even_odd", i % 2 == 0 ? "odd" : "even"); //because we start with i==0;...


                    result.append(procEval.call(fakeContext));

                    i++;

                }
            }
        } catch (OgnlException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean trueValue(Object retValue) {
        if (retValue == null) {
            return false;
        }
        if (retValue == Boolean.FALSE) {
            return false;
        }

        if (retValue instanceof Collection && ((Collection) retValue).isEmpty()) { //empty collection also considered false
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

        if (value.startsWith("ognl:")) {
            value = value.replace("ognl:", "");
            //System.out.println("getting value for " + value);
            return evalOgnl(value, context, root);
        }
        return value;
    }

    private Object evalOgnl(String value, Map context, Object root) {
        try {
            return Ognl.getValue(value, context, root);
        } catch (OgnlException e) {
            throw new RuntimeException(e);
        }
    }

    //really invocation

    private void handleInvocation(InvocationContext invocationContext, StringBuilder result) {

        //handle tagDefinition's attrs
        TagDefinition tagDefinition = invocationContext.getTagDefinition();
        TagInvocation tagInvocation = invocationContext.getTagInvocation();
        Map local_variables = invocationContext.getLocal_variables();

        for (Iterator it = tagDefinition.getAttrs().iterator(); it.hasNext();) {
            String attrName = (String) it.next();
            local_variables.put(attrName, invocationContext.getAll_attributes().get(attrName));
        }


        //handle tagDefinition's body, for different TagDefinition subclasses
        if (tagDefinition != null) {
            /*if(tagDefinition instanceof ITagDefinition) {
                ((ITagDefinition)tagDefinition).invoke(invocationContext, result);
            } else */
            if (tagDefinition instanceof XmlTagDefinition) {
                XmlTagDefinition xmlTagDefinition = (XmlTagDefinition) tagDefinition;
                for (Iterator it = xmlTagDefinition.getTag().content().iterator(); it.hasNext();) {
                    Node node = (Node) it.next();
                    if (node instanceof DefaultText) {
                        result.append(((Text) node).getText());
                    } else if (node instanceof Element) {
                        handleMeetingElement((Element) node, result, invocationContext);
                    }

                }
            }
        }
    }

    public void handleNode(Node node, StringBuilder result, InvocationContext invocationContext) {
        if (node instanceof Text) {
            result.append(node.getText());
        } else if (node instanceof CDATA) {
            result.append(node.getText());
        } else {
            handleMeetingElement((Element) node, result, invocationContext);
        }

    }
}