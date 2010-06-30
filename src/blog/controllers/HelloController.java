package blog.controllers;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-28
 * Time: 19:30:22
 * To change this template use File | Settings | File Templates.
 */
public class HelloController extends ApplicationController {
    public String index() {
        return html("haha");
    }
}
