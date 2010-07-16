package template;

import junit.framework.TestCase;
import org.custommonkey.xmlunit.XMLTestCase;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class DrymlTest extends XMLTestCase {
    public void testDryml() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        templateHandler.handle("test\\template\\test.dryml", result, null);
        System.out.println(result.toString());
        //assertXMLEqual(result.toString(), );
    }

    public void testMergeAttrs() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        templateHandler.handle("test\\template\\test-merge-attrs.dryml", result, null);
        System.out.println(result.toString());
    }

    public void testDryml1() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        templateHandler.handle("test\\template\\test1.dryml", result, null);
        System.out.println(result.toString());
        //assertXMLEqual(result.toString(), );
    }
}
