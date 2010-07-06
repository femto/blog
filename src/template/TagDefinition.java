package template;

import org.dom4j.Element;

/**
* # @copyright: please see original copyright notice
*
* @author: femto
*/
public class TagDefinition {
    private Element tag;


    public TagDefinition(Element tag) {
        this.tag = tag;
    }

    public Element getTag() {
        return tag;
    }
}
