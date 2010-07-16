package template;

import junit.framework.TestCase;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class DrymlTest extends TestCase {
    public void testDryml() throws Exception {
        DrymlConfiguration configuration = new DrymlConfiguration();
        configuration.setClassResolver(new MyClassResolver());
        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();

        StringBuilder result = new StringBuilder();
        templateHandler.handle("test\\template\\test.dryml", result, null);
        System.out.println(result.toString());
    }

//    public void testMergeAttrs() throws Exception {
//        DrymlConfiguration configuration = new DrymlConfiguration();
//        configuration.setClassResolver(new MyClassResolver());
//        DrymlTemplateHandler templateHandler = configuration.getDrymlTemplateHandler();
//
//        StringBuilder result = new StringBuilder();
//        templateHandler.handle("test\\template\\test-merge-attrs.dryml", result, null);
//        System.out.println(result.toString());
//    }
}
