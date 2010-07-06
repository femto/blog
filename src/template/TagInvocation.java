package template;

import org.dom4j.Element;

/**
* # @copyright: please see original copyright notice
*
* @author: femto
*/
public class TagInvocation {
    private Element tag;

    public TagInvocation(Element tag) {
        this.tag = tag;
    }

    public Element getTag() {
        return tag;
    }
}
