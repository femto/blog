package template;

import java.util.HashMap;
import java.util.Map;

/**
* # @copyright: please see original copyright notice
*
* @author: femto
*/
public class InvocationContext {
    private TagDefinition tagDefinition;
    private TagInvocation tagInvocation;
    private Map attributes = new HashMap();
    private Map all_attributes = new HashMap();

    private Map parameters = new HashMap();
    private Map all_parameters = new HashMap();

    public InvocationContext(TagDefinition tagDefinition, TagInvocation tagInvocation) {
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