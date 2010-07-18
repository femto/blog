package template;

import junit.framework.TestCase;
import org.custommonkey.xmlunit.XMLTestCase;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
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
        //assertXMLEqual(new FileReader("test\\template\\testThisShadowing.dryml"), new StringReader(result.toString()));
    }

    public void testIf() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        Map rootContext = new HashMap();
        rootContext.put("this", "a");

        templateHandler.handle("test\\template\\testIf.dryml", result, rootContext);

        System.out.println(result.toString());
        //assertXMLEqual(new FileReader("test\\template\\testThisShadowing.dryml"), new StringReader(result.toString()));
    }

    public void testIfTrue() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        Map rootContext = new HashMap();
        rootContext.put("this", true);

        templateHandler.handle("test\\template\\testIfTrue.dryml", result, rootContext);

        assertEquals("true", result.toString().trim());
        //assertXMLEqual(new FileReader("test\\template\\testThisShadowing.dryml"), new StringReader(result.toString()));
    }

    public void testIfFalse() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        Map rootContext = new HashMap();
        rootContext.put("this", false);

        templateHandler.handle("test\\template\\testIfFalse.dryml", result, rootContext);

        //System.out.println(result.toString());
        //assertEquals("false", result.toString().trim());
        //assertXMLEqual(new FileReader("test\\template\\testThisShadowing.dryml"), new StringReader(result.toString()));
    }

    public void testBeanShell() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        Map rootContext = new HashMap();
        rootContext.put("this", false);

        templateHandler.handle("test\\template\\testBeanShell.dryml", result, rootContext);

        //assertEquals("37", result.toString().trim());
        //assertEquals("false", result.toString().trim());
        //assertXMLEqual(new FileReader("test\\template\\testThisShadowing.dryml"), new StringReader(result.toString()));
    }

    public void testBeanShellThrowException() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        Map rootContext = new HashMap();
        rootContext.put("this", false);

        try {
            templateHandler.handle("test\\template\\testBeanShellThrowException.dryml", result, rootContext);
        } catch (Exception e) {
            e.printStackTrace(); //exception expected
        }

        //assertEquals("37", result.toString().trim());
        //assertEquals("false", result.toString().trim());
        //assertXMLEqual(new FileReader("test\\template\\testThisShadowing.dryml"), new StringReader(result.toString()));
    }
}
