package template;

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

    private static Map tagDefinitions = new HashMap();
    private static Stack invocationStack = new Stack();

    static class TagDefinition {
        private Element tag;


        TagDefinition(Element tag) {
            this.tag = tag;
        }

        public Element getTag() {
            return tag;
        }
    }

    static class TagInvocation {
        private Element tag;

        TagInvocation(Element tag) {
            this.tag = tag;
        }

        public Element getTag() {
            return tag;
        }
    }

    static class InvocationContext {
        private TagDefinition tagDefinition;
        private TagInvocation tagInvocation;
        private Map attributes = new HashMap();
        private Map all_attributes = new HashMap();

        private Map parameters = new HashMap();
        private Map all_parameters = new HashMap();

        InvocationContext(TagDefinition tagDefinition, TagInvocation tagInvocation) {
            this.tagDefinition = tagDefinition;
            this.tagInvocation = tagInvocation;
        }

        public TagDefinition getTagDefinition() {
            return tagDefinition;
        }

        public TagInvocation getTagInvocation() {
            return tagInvocation;
        }

        public Map getAttributes() {
            return attributes;
        }

        public void setAttributes(Map attributes) {
            this.attributes = attributes;
        }

        public Map getAll_attributes() {
            return all_attributes;
        }

        public void setAll_attributes(Map all_attributes) {
            this.all_attributes = all_attributes;
        }

        public Map getParameters() {
            return parameters;
        }

        public void setParameters(Map parameters) {
            this.parameters = parameters;
        }

        public Map getAll_parameters() {
            return all_parameters;
        }

        public void setAll_parameters(Map all_parameters) {
            this.all_parameters = all_parameters;
        }
    }


    public static Document parse(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }


    public static void main(String[] args) throws Exception {
        File file = new File("webapps\\blog\\WEB-INF\\views\\posts\\index3.dryml");
        StringBuilder result = new StringBuilder();
        Document document = parse(file);
        Element root = document.getRootElement();
        //System.out.println(document.selectNodes("//foo/bar"));


        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Element tag = (Element) i.next();
            handleMeetingElement(tag, result);
            // do something
        }
        System.out.println(result.toString());
    }


    private static void handleInclude(Element tag) {

    }

    private static void handleDefTag(Element tag) {
        Attribute attribute = tag.attribute("tag");
        tagDefinitions.put(attribute.getValue(), new TagDefinition(tag));
    }

    //calling
    //preparing

    private static void handleMeetingElement(Element element, StringBuilder result) {
        //trying find out tagDefinition
        if ("def".equals(element.getName())) {
            handleDefTag(element);

        } else if ("include".equals(element.getName())) {
            handleInclude(element);
        } else if ("scriptlet".equals(element.getName())) {
            handleScriptlet(element);
        } else { //calling

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
                Map parameters = new HashMap();
                Map all_parameters = new HashMap();
                for (Iterator it = element.elementIterator(); it.hasNext();) {
                    Element ele = (Element) it.next();
                    if (ele.getName().indexOf("param-") != -1) {
                        String name = ele.getName().replace("param-", "");
                        //System.out.println(name);
                        parameters.put(name, ele); //or ele's content?
                        all_parameters.put(name, ele);
                    }
                }
                if (parameters.size() == 0) { //what about text?
                    parameters.put("default", element);
                    all_parameters.put("default", element);
                }
                invocationContext.setParameters(parameters);
                invocationContext.setAll_parameters(all_parameters);

                invocationStack.push(invocationContext); //maybe calculating the attrs and all_attrs?

                handleInvocation(invocationContext, result); //handle attribute
                invocationStack.pop();
            }
        }
    }

    private static void handleScriptlet(Element element) {

    }

    private static void handleDefaultTag(Element element, StringBuilder result) { //default invocation doesn't change invocationContext's attr & params
        result.append("<" + element.getName() + "");
        //handle attribute


        for (Iterator i = element.attributeIterator(); i.hasNext();) {
            Attribute attribute = (Attribute) i.next();
            if ("merge-attrs".equals(attribute.getName())) {
                //peek the invocation stack
                InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
                for (Iterator it = invocationContext.getAttributes().keySet().iterator(); it.hasNext();) {
                    String key = (String) it.next();
                    result.append(" " + key + "=" + invocationContext.getAttributes().get(key));

                }
            } else if ("param".equals(attribute.getName())) {
                String paramName = attribute.getValue();
                //System.out.println(attribute.getName() + "=" + attribute.getValue());
                if ("true".equals(paramName)) {
                    paramName = element.getName();
                }
                //System.out.println("paramName is " + paramName);
                InvocationContext invocationContext = (InvocationContext) invocationStack.peek();
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
                result.append(" " + attribute.getName() + "=" + attribute.getValue());
            }

        }

        result.append(">");
        //for element's body
        for (Iterator i = element.elementIterator(); i.hasNext();) {
            Element tag = (Element) i.next();
            handleMeetingElement(tag, result);
            // do something
        }
        result.append("</" + element.getName() + ">");
    }

    //really invocation

    private static void handleInvocation(InvocationContext invocationContext, StringBuilder result) {

        //handle tagDefinition's attrs
        TagDefinition tagDefinition = invocationContext.getTagDefinition();
        TagInvocation tagInvocation = invocationContext.getTagInvocation();

        //handle tagDefinition's body
        for (Iterator it = tagDefinition.tag.content().iterator(); it.hasNext();) {
            Node node = (Node) it.next();
            if (node instanceof DefaultText) {
                result.append(((Text) node).getText());
            } else if (node instanceof Element) {
                handleMeetingElement((Element) node, result);
            }

        }
    }

    private static void handleElement1(Element element, StringBuilder result) {
        if (tagDefinitions.get(element.getName()) == null) { //handle undefined element
            result.append("<" + element.getName() + "");
            //handle attribute

            for (Iterator i = element.attributeIterator(); i.hasNext();) {
                Attribute attribute = (Attribute) i.next();
                if ("merge-attrs".equals(attribute.getName())) {
                    //for each attr pass in, output the attr
                } else {
                    result.append(" " + attribute.getName() + "=" + attribute.getValue());
                }

            }

            result.append(">");
            handleMeetingElement(element, result);
            result.append("</" + element.getName() + ">");
        } else { //handle defined element
            Object tagDefinition = tagDefinitions.get(element.getName()); //invoke this
            TagInvocation tagInvocation = new TagInvocation(element);

            //merge invocation with definition


            InvocationContext invocationContext = new InvocationContext((TagDefinition) tagDefinition, tagInvocation);
            invocationStack.push(invocationContext);

            handleInvocation(invocationContext, result); //handle attribute
            invocationStack.pop();
        }

    }
}
