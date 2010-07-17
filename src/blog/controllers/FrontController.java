package blog.controllers;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class FrontController extends ApplicationController {
    public String index() {
        return drymlHandle("front", "index");
    }
}
