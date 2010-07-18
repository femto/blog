package template;

import bsh.Interpreter;
import junit.framework.TestCase;

import java.util.Date;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class BeanShellTest extends TestCase {
    public static void main(String[] args) throws Exception {
        Interpreter i = new Interpreter();  // Construct an interpreter
        //i.set("foo", 5);                    // Set variables
        i.set("this", 5);

        Date date = (Date) i.get("date");    // retrieve a variable

// Eval a statement and get the result
        Object result = i.eval("this");
        System.out.println(result);

// Source an external script file
        //i.source("somefile.bsh");
    }
}
