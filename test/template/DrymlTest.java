package template;

import junit.framework.TestCase;
import org.custommonkey.xmlunit.XMLTestCase;

import java.util.HashMap;
import java.util.Map;

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

    public void testDryml2() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        templateHandler.handle("test\\template\\test2.dryml", result, null);
        System.out.println(result.toString());
        //assertXMLEqual(result.toString(), );
    }

    public void testRootObject() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        Map rootContext = new HashMap();
        rootContext.put("this", "a");

        templateHandler.handle("test\\template\\testRoot.dryml", result, rootContext);
        System.out.println(result.toString());
        //assertXMLEqual(result.toString(), );
    }

    public void testThisShadowing() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        Map rootContext = new HashMap();
        rootContext.put("this", "a");

        templateHandler.handle("test\\template\\testThisShadowing.dryml", result, rootContext);
        System.out.println(result.toString());
        //assertXMLEqual(result.toString(), );
    }
}
